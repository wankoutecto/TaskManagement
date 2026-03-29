import { createContext, useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode"; 
const AuthContext = createContext();

export const AuthProvider = ({children}) => {
    const navigate = useNavigate();
    const [token, setToken] = useState(localStorage.getItem("token"));
    const [username, setUsername] =useState(useState(localStorage.getItem("username")));
    

    useEffect(() => {
        if(token){
            try {
                const {exp} = jwtDecode(token);
                if(Date.now() >= exp * 1000){
                    onLogout();
                } 
            } catch (error) {
                console.error("Token expired or Jwt decode failed", error);
                onLogout();
            }
        }
        
    }, [token]);

    const onLogin = (newToken, newUsername) => {
        localStorage.setItem('token', newToken);
        localStorage.setItem('username', newUsername);
        setToken(newToken);
        setUsername(newUsername);
    };

    const onLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        setToken(null);
        setUsername(null);
        navigate("/login");
    };

    return (
        <AuthContext.Provider value={{token, onLogin, onLogout, username}}>
            {children}
        </AuthContext.Provider>
    );
    
};

export const useAuth = () => useContext(AuthContext);