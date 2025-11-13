// API helpers for authentication-related operations
export interface RegisterRequest {
    username: string;
    password: string;
    role: string;
}

export interface AuthenticateRequest {
    username: string;
    password: string;
}

export interface AuthenticateResponse {
    token: string;
    username?: string;
    role?: string;
}

const BASE_URL = 'http://localhost:8080/sm-system/api/v1/auth';

async function handleResponse(response: Response): Promise<any> {
    if (!response.ok) {
        try{
            const errorData = await response.json();
            throw new Error(errorData.message || 'Request failed');
        } catch {
            throw new Error('Request failed');
        }
    }
    return response.json();
}

export async function register(data: RegisterRequest): Promise<AuthenticateResponse> {
    const response = await fetch(`${BASE_URL}/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });
    return handleResponse(response);
}

export async function authenticate(data: AuthenticateRequest): Promise<AuthenticateResponse> {
    const response = await fetch(`${BASE_URL}/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });
    return handleResponse(response);
}

// frontend/src/api/auth.ts
export async function login({ username, password }: { username: string; password: string }) {
    const response = await fetch(`${BASE_URL}/authenticate`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
    });
    if (!response.ok) {
        throw new Error('Login failed');
    }
    return await response.json(); // Should return { token: ... }
}
