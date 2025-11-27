import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { RegisterForm } from './components/RegisterForm';
import { LoginForm } from './components/LoginForm';
import { StudentsList } from './components/StudentsList';
import { StudentForm } from './components/AddStudentForm';

export function App(){
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<LoginForm />} />
                <Route path="/students" element={<StudentsList />} />
                <Route path="/register" element={<RegisterForm />} />
                <Route path="/add-student" element={<StudentForm />} />
            </Routes>
        </BrowserRouter>
    );
}