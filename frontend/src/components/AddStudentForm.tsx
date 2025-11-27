// frontend/src/components/AddStudentForm.tsx
import * as React from 'react';
import { fetchStudents, createStudent } from '@api/students';
import styles from './AddStudent.module.css';
import { useNavigate } from 'react-router-dom';

const BASE_URL = 'http://localhost:8080/sm-system';

type StudentFormState = {
    studentNumber: string;
    firstName: string;
    lastName: string;
    dateOfBirth: string;
    gender: 'MALE' | 'FEMALE' | '';
    contactInfoRequest: {
        email: string;
        phoneNumber: string;
        addressRequest: {
            street: string;
            city: string;
            zipCode: string;
            country: string;
        };
    };
    academicInfoRequest: {
        enrollmentDate: string;
        program: string;
        department: string;
        yearLevel: number | '';
        studentStatus:
            | 'APPLICANT'
            | 'ADMITTED'
            | 'ENROLLED'
            | 'LEAVE_OF_ABSENCE'
            | 'PROBATION'
            | 'SUSPENDED'
            | 'WITHDRAWN'
            | 'GRADUATED'
            | 'DISMISSED'
            | '';
        gpa: number | '';
    };
};

const initialState: StudentFormState = {
    studentNumber: '',
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: '',
    contactInfoRequest: {
        email: '',
        phoneNumber: '',
        addressRequest: { street: '', city: '', zipCode: '', country: '' }
    },
    academicInfoRequest: {
        enrollmentDate: '',
        program: '',
        department: '',
        yearLevel: '',
        studentStatus: '',
        gpa: ''
    }
};

export const StudentForm: React.FC = () => {
    const [form, setForm] = React.useState<StudentFormState>(initialState);
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);
    const [success, setSuccess] = React.useState(false);
    const navigate = useNavigate();

    function update(path: string, value: string) {
        setForm(prev => {
            const parts = path.split('.');
            const copy: any = { ...prev };
            let cursor = copy;
            for (let i = 0; i < parts.length - 1; i++) {
                cursor[parts[i]] = { ...cursor[parts[i]] };
                cursor = cursor[parts[i]];
            }
            const key = parts[parts.length - 1];
            if (key === 'yearLevel' || key === 'gpa') {
                cursor[key] = value === '' ? '' : Number(value);
            } else {
                cursor[key] = value;
            }
            return copy;
        });
    }

    async function onSubmit(e: React.FormEvent) {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccess(false);
        try {
            const payload = {
                studentNumber: form.studentNumber,
                firstName: form.firstName,
                lastName: form.lastName,
                dateOfBirth: form.dateOfBirth,
                gender: form.gender,
                contactInfoRequest: {
                    email: form.contactInfoRequest.email,
                    phoneNumber: form.contactInfoRequest.phoneNumber,
                    addressRequest: { ...form.contactInfoRequest.addressRequest }
                },
                academicInfoRequest: {
                    enrollmentDate: form.academicInfoRequest.enrollmentDate,
                    program: form.academicInfoRequest.program,
                    department: form.academicInfoRequest.department,
                    yearLevel:
                        form.academicInfoRequest.yearLevel === ''
                            ? null
                            : form.academicInfoRequest.yearLevel,
                    studentStatus: form.academicInfoRequest.studentStatus,
                    gpa:
                        form.academicInfoRequest.gpa === ''
                            ? null
                            : form.academicInfoRequest.gpa
                }
            };

            console.log('Payload:', payload);
            const result = await createStudent(payload);

            if (result) {
                const token = localStorage.getItem('authToken');
                const resp = await fetch(`${BASE_URL}/students`, {
                    headers: {
                        Authorization: `Bearer ${token || ''}`,
                        'Content-Type': 'application/json'
                    }
                });
                const studentsData = await resp.json();
                setSuccess(true);
                setForm(initialState);
                navigate('/students', { state: { studentsData } });
            } else {
                setError('Failed to create student');
            }
        } catch (err: any) {
            setError(err?.message || 'Failed');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className={styles.formContainer}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', maxWidth: 600 }}>
                <h2 style={{ margin: 0 }}>Add Student</h2>
                <button
                    type="button"
                    onClick={async () => {
                        const studentsData = await fetchStudents();
                        navigate('/students', { state: { studentsData } });
                    }}
                    style={{
                        background: '#555',
                        color: '#fff',
                        border: 'none',
                        padding: '6px 14px',
                        borderRadius: 4,
                        cursor: 'pointer',
                        fontSize: '0.85rem'
                    }}
                >
                    Back to Students
                </button>
            </div>

            <form onSubmit={onSubmit} style={{ maxWidth: 600 }}>
                <h2>Add Student</h2>

                <fieldset>
                    <legend>Basic Info</legend>
                    <label>
                        Student Number
                        <input
                            type="text"
                            value={form.studentNumber}
                            onChange={e => update('studentNumber', e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        First Name
                        <input
                            type="text"
                            value={form.firstName}
                            onChange={e => update('firstName', e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Last Name
                        <input
                            type="text"
                            value={form.lastName}
                            onChange={e => update('lastName', e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Date Of Birth
                        <input
                            type="date"
                            value={form.dateOfBirth}
                            onChange={e => update('dateOfBirth', e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Gender
                        <select
                            value={form.gender}
                            onChange={e => update('gender', e.target.value)}
                            required
                        >
                            <option value="">Select...</option>
                            <option value="MALE">MALE</option>
                            <option value="FEMALE">FEMALE</option>
                        </select>
                    </label>
                </fieldset>

                <fieldset>
                    <legend>Contact Info</legend>
                    <label>
                        Email
                        <input
                            type="email"
                            value={form.contactInfoRequest.email}
                            onChange={e => update('contactInfoRequest.email', e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Phone Number
                        <input
                            type="tel"
                            value={form.contactInfoRequest.phoneNumber}
                            onChange={e =>
                                update('contactInfoRequest.phoneNumber', e.target.value)
                            }
                            required
                        />
                    </label>
                    <label>
                        Street
                        <input
                            type="text"
                            value={form.contactInfoRequest.addressRequest.street}
                            onChange={e =>
                                update('contactInfoRequest.addressRequest.street', e.target.value)
                            }
                            required
                        />
                    </label>
                    <label>
                        City
                        <input
                            type="text"
                            value={form.contactInfoRequest.addressRequest.city}
                            onChange={e =>
                                update('contactInfoRequest.addressRequest.city', e.target.value)
                            }
                            required
                        />
                    </label>
                    <label>
                        Zip Code
                        <input
                            type="text"
                            value={form.contactInfoRequest.addressRequest.zipCode}
                            onChange={e =>
                                update('contactInfoRequest.addressRequest.zipCode', e.target.value)
                            }
                            required
                        />
                    </label>
                    <label>
                        Country
                        <input
                            type="text"
                            value={form.contactInfoRequest.addressRequest.country}
                            onChange={e =>
                                update('contactInfoRequest.addressRequest.country', e.target.value)
                            }
                            required
                        />
                    </label>
                </fieldset>

                <fieldset>
                    <legend>Academic Info</legend>
                    <label>
                        Enrollment Date
                        <input
                            type="date"
                            value={form.academicInfoRequest.enrollmentDate}
                            onChange={e =>
                                update('academicInfoRequest.enrollmentDate', e.target.value)
                            }
                            required
                        />
                    </label>
                    <label>
                        Program
                        <input
                            type="text"
                            value={form.academicInfoRequest.program}
                            onChange={e => update('academicInfoRequest.program', e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Department
                        <input
                            type="text"
                            value={form.academicInfoRequest.department}
                            onChange={e =>
                                update('academicInfoRequest.department', e.target.value)
                            }
                            required
                        />
                    </label>
                    <label>
                        Year Level
                        <input
                            type="number"
                            min={1}
                            value={form.academicInfoRequest.yearLevel as number | ''}
                            onChange={e =>
                                update('academicInfoRequest.yearLevel', e.target.value)
                            }
                            required
                        />
                    </label>
                    <label>
                        Status
                        <select
                            value={form.academicInfoRequest.studentStatus}
                            onChange={e =>
                                update('academicInfoRequest.studentStatus', e.target.value)
                            }
                            required
                        >
                            <option value="">Select...</option>
                            <option value="APPLICANT">APPLICANT</option>
                            <option value="ADMITTED">ADMITTED</option>
                            <option value="ENROLLED">ENROLLED</option>
                            <option value="LEAVE_OF_ABSENCE">LEAVE OF ABSENCE</option>
                            <option value="PROBATION">PROBATION</option>
                            <option value="SUSPENDED">SUSPENDED</option>
                            <option value="WITHDRAWN">WITHDRAWN</option>
                            <option value="GRADUATED">GRADUATED</option>
                            <option value="DISMISSED">DISMISSED</option>
                        </select>
                    </label>
                    <label>
                        GPA
                        <input
                            type="number"
                            step="0.01"
                            min={0}
                            max={5}
                            value={form.academicInfoRequest.gpa as number | ''}
                            onChange={e => update('academicInfoRequest.gpa', e.target.value)}
                            required
                        />
                    </label>
                </fieldset>

                <button type="submit" disabled={loading}>
                    {loading ? 'Saving...' : 'Create Student'}
                </button>
                {error && <p className={styles.error}>{error}</p>}
                {success && <p style={{ color: 'green' }}>Student created.</p>}
            </form>
        </div>
    );
};
