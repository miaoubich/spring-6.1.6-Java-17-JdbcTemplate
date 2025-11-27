// TypeScript
const BASE_URL = 'http://localhost:8080/sm-system';

export async function fetchStudents(): Promise<any[] | null> {
    const token = localStorage.getItem('authToken');
    const resp = await fetch(`${BASE_URL}/students`, {
        headers: {
            'Authorization': `Bearer ${token || ''}`,
            'Accept': 'application/json'
        }
    });
    if (!resp.ok) {
        console.error('Failed to load students', resp.status);
        return null;
    }
    return await resp.json();
}

export async function createStudent(payload: any) {
    const token = localStorage.getItem('authToken');
    const resp = await fetch(`${BASE_URL}/students`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token || ''}`,
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(payload)
    });
    if (!resp.ok) {
        const txt = await resp.text().catch(() => '');
        console.error('Create failed', resp.status, txt);
        return null;
    }
    return await resp.json();
}
