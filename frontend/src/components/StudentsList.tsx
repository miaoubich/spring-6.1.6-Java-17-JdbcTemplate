// TypeScript
import * as React from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import { fetchStudents } from '@api/students';

interface Address {
    street: string;
    city: string;
    zipCode: string;
    country: string;
}

interface ContactInfo {
    email: string;
    phoneNumber: string;
    addressResponse: Address;
}

interface AcademicInfo {
    enrollmentDate: string;
    program: string;
    department: string;
    yearLevel: number;
    studentStatus: string;
    gpa: number;
}

interface Student {
    id: number;
    studentNumber: string;
    firstName: string;
    lastName: string;
    dateOfBirth: string;
    gender: string;
    contactInfoResponse: ContactInfo;
    academicInfoResponse: AcademicInfo;
    createdAt: string;
    updatedAt: string | null;
}

export const StudentsList: React.FC = () => {
    const { state } = useLocation();
    const navigate = useNavigate();

    const [students, setStudents] = React.useState<Student[]>(state?.studentsData || []);
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);

    const username = React.useMemo(() => {
        const stored = localStorage.getItem('username');
        if (stored) return stored;
        const token = localStorage.getItem('authToken');
        if (!token) return '';
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            return payload.username || payload.sub || '';
        } catch {
            return '';
        }
    }, []);

    React.useEffect(() => {
        let mounted = true;
        async function load() {
            if (state?.studentsData && Array.isArray(state.studentsData)) {
                setStudents(state.studentsData);
                return;
            }
            setLoading(true);
            setError(null);
            const data = await fetchStudents();
            if (!mounted) return;
            if (data) setStudents(data as Student[]);
            else setError('Failed to load students.');
            setLoading(false);
        }
        load();
        return () => {
            mounted = false;
        };
    }, [state]);

    const logout = () => {
        localStorage.removeItem('authToken');
        localStorage.removeItem('username');
        navigate('/login');
    };

    const Navbar: React.FC = () => (
        <nav style={nav}>
            <div style={navLeft}>
                <span style={brand}>SM System</span>
                <Link to="/students" style={navLink}>Students</Link>
                <Link to="/add-student" style={navLink}>Add Student</Link>
            </div>
            <div style={navRight}>
                {username && <span style={userText}>Logged in as: {username}</span>}
                <button onClick={logout} style={navLogoutBtn}>Logout</button>
            </div>
        </nav>
    );

    if (loading) {
        return (
            <div>
                <Navbar />
                <div style={{ padding: '2rem' }}>
                    <p>Loading...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div>
                <Navbar />
                <div style={{ padding: '2rem' }}>
                    <p style={{ color: '#c62828' }}>{error}</p>
                </div>
            </div>
        );
    }

    if (!students.length) {
        return (
            <div>
                <Navbar />
                <div style={{ padding: '2rem' }}>
                    <h2 style={{ marginTop: 0 }}>Students List</h2>
                    <p>No students data available.</p>
                </div>
            </div>
        );
    }

    return (
        <div>
            <Navbar />
            <div style={{ padding: '2rem' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.75rem' }}>
                    <h2 style={{ margin: 0 }}>Students List</h2>
                    <button
                        onClick={() => navigate('/add-student')}
                        style={{
                            background: '#1976d2',
                            color: '#fff',
                            border: 'none',
                            padding: '6px 14px',
                            borderRadius: 4,
                            cursor: 'pointer',
                            fontSize: '0.85rem'
                        }}
                    >
                        Add Student
                    </button>
                </div>

                <div style={{ overflowX: 'auto' }}>
                    <table style={{ borderCollapse: 'collapse', width: '100%', fontSize: '0.9rem' }}>
                        <thead>
                        <tr style={{ background: '#f0f0f0' }}>
                            <th style={th}>Student Number</th>
                            <th style={th}>Name</th>
                            <th style={th}>DOB</th>
                            <th style={th}>Gender</th>
                            <th style={th}>Email</th>
                            <th style={th}>Phone</th>
                            <th style={th}>Address</th>
                            <th style={th}>Program</th>
                            <th style={th}>Department</th>
                            <th style={th}>Year</th>
                            <th style={th}>Status</th>
                            <th style={th}>GPA</th>
                            <th style={th}>Created At</th>
                        </tr>
                        </thead>
                        <tbody>
                        {students.map(s => {
                            const addr = s.contactInfoResponse?.addressResponse;
                            const acad = s.academicInfoResponse;
                            return (
                                <tr key={s.studentNumber}>
                                    <td style={td}>{s.studentNumber}</td>
                                    <td style={td}>{s.firstName} {s.lastName}</td>
                                    <td style={td}>{s.dateOfBirth}</td>
                                    <td style={td}>{s.gender}</td>
                                    <td style={td}>{s.contactInfoResponse?.email}</td>
                                    <td style={td}>{s.contactInfoResponse?.phoneNumber}</td>
                                    <td style={td}>
                                        {addr ? `${addr.street}, ${addr.city} ${addr.zipCode}, ${addr.country}` : ''}
                                    </td>
                                    <td style={td}>{acad?.program}</td>
                                    <td style={td}>{acad?.department}</td>
                                    <td style={td}>{acad?.yearLevel}</td>
                                    <td style={td}>{acad?.studentStatus}</td>
                                    <td style={td}>{acad?.gpa}</td>
                                    <td style={td}>{new Date(s.createdAt).toLocaleString()}</td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

const th: React.CSSProperties = {
    padding: '8px 10px',
    border: '1px solid #ccc',
    textAlign: 'left',
    whiteSpace: 'nowrap'
};

const td: React.CSSProperties = {
    padding: '6px 10px',
    border: '1px solid #ddd',
    verticalAlign: 'top'
};

const nav: React.CSSProperties = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: '10px 16px',
    background: '#0d47a1',
    color: '#fff',
    position: 'sticky',
    top: 0,
    zIndex: 1000
};

const navLeft: React.CSSProperties = {
    display: 'flex',
    alignItems: 'center',
    gap: 12
};

const navRight: React.CSSProperties = {
    display: 'flex',
    alignItems: 'center',
    gap: 12
};

const brand: React.CSSProperties = {
    fontWeight: 600,
    letterSpacing: 0.3
};

const navLink: React.CSSProperties = {
    color: '#fff',
    textDecoration: 'none',
    padding: '6px 8px',
    borderRadius: 4
};

const userText: React.CSSProperties = {
    fontSize: '0.85rem',
    opacity: 0.9
};

const navLogoutBtn: React.CSSProperties = {
    background: '#c62828',
    color: '#fff',
    border: 'none',
    padding: '6px 12px',
    borderRadius: 4,
    cursor: 'pointer',
    fontSize: '0.85rem'
};

const logoutBtn: React.CSSProperties = {
    background: '#c62828',
    color: '#fff',
    border: 'none',
    padding: '6px 14px',
    borderRadius: 4,
    cursor: 'pointer',
    fontSize: '0.85rem'
};
