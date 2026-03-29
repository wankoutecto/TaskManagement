import { useState } from 'react';
import '../App.css';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

export default function Register(){
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [conPassword, setConPassword] = useState('');
    const [err, setErr] = useState('');
    const navigate = useNavigate();


    const userRegister = async(e) => {
        e.preventDefault();
        if(conPassword !== password){
            setErr("Password and Confirm password must match");
            return;
        }
       try {
         const res = await axios.post("http://localhost:8080/user/register",
             {email, password}
         );
         if(res.status === 200){
            setErr("successful register");
            setEmail('');
            setPassword('');
            setConPassword('');
            navigate("/login")
         }
       } catch (error) {
            console.error(error);
       }
    };

    return (
        <div className='registerBody'>
            <form className='registerForm' onSubmit={userRegister}>
                <h1>Sign up</h1>
                <label>Username</label>
                <br/>
                <input 
                type='text'
                value={email}
                onChange={(e) => {
                    setEmail(e.target.value);
                    setErr('');
                }}
                required
                ></input>
                <br/>
                <label>Password</label>
                <br/>
                <input 
                type='password'
                value={password}
                onChange={(e) => {
                    setPassword(e.target.value);
                    setErr('');
                }}
                 required
                ></input>
                <br/>
                <label>Confirm Password</label>
                <br/>
                <input 
                type='password'
                value={conPassword}
                onChange={(e) => {
                    setConPassword(e.target.value);
                    setErr('');
                }}
                 required
                ></input>
                <br />
                {err && <p>{err}</p>}
                <div className='align-button'>
                    <button style={{width:"100%"}}>Sign up</button>
                </div>
                <p>Already have an account? <Link to={"/login"}>Log in</Link> </p>
            </form>
        </div>
    );
}