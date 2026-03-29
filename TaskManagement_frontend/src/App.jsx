import { useState } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import Register from './pages/Register';
import Login from './pages/Login';
import Task from './pages/Task';

function App() {
  return (
    <Routes>
      <Route path="/register" element={<Register />} />
      <Route path="/login" element={<Login />} />
      <Route path="/task" element={<Task />} />
      <Route path='/' element={<Navigate to="/task"/>} />
    </Routes>
  );
}

export default App;
