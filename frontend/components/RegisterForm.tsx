import * as React from 'react';
import { register } from '../src/api/auth';
import styles from './StyleForm.module.css';
import {Link} from "react-router-dom";

const roles = ['USER', 'ADMIN'];

export const RegisterForm: React.FC = () => {
    const [form, setForm] = React.useState({ username: '', password: '', role: 'ROLE_USER' });
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);
    const [success, setSuccess] = React.useState(false);

    function update<K extends keyof typeof form>(key: K, value: string) {
        setForm(f => ({ ...f, [key]: value }));
    }

    async function onSubmit(e: React.FormEvent) {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccess(false);
        try {
            const result = await register(form); // already parsed response
            if (result.token) localStorage.setItem('authToken', result.token);
            setSuccess(true);
            setForm({ username: '', password: '', role: 'ROLE_USER' });
        } catch (err: any) {
            setError(err.message || 'Registration failed');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className={styles.formContainer}>
        <form onSubmit={onSubmit} style={{ maxWidth: 360 }}>
            <h2 id='register'>Register</h2>
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
                    autoComplete="new-password"
                    onChange={e => update('password', e.target.value)}
                    required
                    minLength={6}
                />
            </label>
            <label>
                Role
                <select id='role' value={form.role} onChange={e => update('role', e.target.value)}>
                    {roles.map(r => (
                        <option key={r} value={r}>{r}</option>
                    ))}
                </select>
            </label>
            <button id='btn_submit' type="submit" disabled={loading}>
                {loading ? 'Submitting...' : 'Register'}
            </button>
            <Link to="/login" style={{ textAlign: 'center', marginTop: '1rem', display: 'block' }}>
                Login
            </Link>
            {error && <p className={styles.error}>{error}</p>}
            {success && <p className={styles.success}>Registered successfully.</p>}
        </form>
        </div>
    );
};
