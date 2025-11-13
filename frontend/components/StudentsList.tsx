import * as React from 'react';
import { useLocation } from 'react-router-dom';

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
    id: number; // omitted from display
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
    const students: Student[] = state?.studentsData || [];

    if (!students.length) {
        return <p style={{ padding: '2rem' }}>No students data available.</p>;
    }

    return (
        <div style={{ padding: '2rem' }}>
            <h2>Students List</h2>
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
                                    {addr
                                        ? `${addr.street}, ${addr.city} ${addr.zipCode}, ${addr.country}`
                                        : ''}
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
    );
};

// simple shared cell styles
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
