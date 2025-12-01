import * as React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { login } from '../api/auth';
import styles from './LoginLogup.module.css';

type LoginResponse = {
    token?: string;
    username?: string;
}

export const LoginForm: React.FC = () => {
    const [form, setForm] = React.useState({ username: '', password: '' });
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);
    const [success, setSuccess] = React.useState(false);
    const navigate = useNavigate();

    function update<K extends keyof typeof form>(key: K, value: string) {
        setForm(f => ({ ...f, [key]: value }));
    }

    const BASE_URL = 'http://localhost:8080/sm-system';

    function deriveUsernameFromToken(token: string, explicit?: string): string {
        if (explicit) return explicit;
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            return payload.username || payload.sub || 'User';
        } catch {
            return 'User';
        }
    }

    async function onSubmit(e: React.FormEvent) {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccess(false);
        try {
            const result = await login(form);
            if (!result.token) {
                setError('Invalid login response');
                return '';
            }
            localStorage.setItem('authToken', result.token);
            const name = deriveUsernameFromToken(result.token, result.username);
            localStorage.setItem('username', name);
            setSuccess(true);
            setForm({ username: '', password: '' });

            // Fetch students data after login
            const studentListResponse = await fetch(`${BASE_URL}/students`, {
                headers: {
                    'Authorization': `Bearer ${result.token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (!studentListResponse.ok) {
                setError('Failed to load students.');
                return;
            }
            const data = await studentListResponse.json();
            navigate('/students', { state: { studentsData: data } });

        } catch (err: any) {
            setError(err.message || 'Login failed');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className={styles.formContainer}>
            <form onSubmit={onSubmit} className={styles.form}>
                <h2 id='login'>Login</h2>
                <label>
                    Username
                    <input
                        id='username'
                        type="text"
                        value={form.username}
                        autoComplete="username"
                        onChange={e => update('username', e.target.value)}
                        required
                    />
                </label>
                <label>
                    Password
                    <input
                        id='password'
                        type="password"
                        value={form.password}
                        autoComplete="current-password"
                        onChange={e => update('password', e.target.value)}
                        required
                    />
                </label>
                <button id='btn_login' type="submit" disabled={loading}>
                    {loading ? 'Logging in...' : 'Login'}
                </button>
                <Link to="/register" style={{ textAlign: 'center', marginTop: '1rem', display: 'block' }}>
                    Don't have an account? Register
                </Link>
                {error && <p className={styles.error}>{error}</p>}
                {success && <p className={styles.success}>Logged in successfully.</p>}
            </form>
        </div>
    );
};
