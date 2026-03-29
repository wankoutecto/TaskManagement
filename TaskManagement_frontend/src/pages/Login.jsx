import { useState } from 'react';
import '../App.css';
import { useAuth } from '../AuthContext';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';


export default function Login(){
    const {onLogin, onLogout} = useAuth();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const userLogin = async(e) => {
        e.preventDefault();
        try {
            const res = await axios.post("http://localhost:8080/user/login", 
                {email, password}
            );
            if(res.status === 200){
                onLogin(res.data.data, email);
                navigate('/task');
            }   
        } catch (error) {
            console.error("An error occurred: " + error);
        }
    }

    return (
        <div className='registerBody'>
            <form className='registerForm' onSubmit={userLogin}>
                <h1>Log in</h1>
                <div>
                    <label>Username</label>
                    <br/>
                    <input 
                        type='text' 
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <br/>
                    <label>Password</label>
                    <br/>
                    <input 
                        type='password' 
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className='align-button'>
                    <button type='submit' style={{width:"100%"}}>Login</button>
                </div>
                <p>Don't have an account? <Link to={"/register"} >Sign up</Link> </p>
                
            </form>
        </div>
    );
}