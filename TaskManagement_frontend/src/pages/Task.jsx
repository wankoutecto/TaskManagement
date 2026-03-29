import { useEffect, useState } from "react";
import '../App.css';
import { useAuth } from '../AuthContext';
import axios from "axios";
import { isTokenValid } from './isTokenValid';


export default function Task(){
    const {token, onLogout} = useAuth();
    const [title, setTitle] = useState('');
    const [description, setDes] = useState('');
    const [dueDate, setDue] = useState('');
    const [taskList, setTaskList] = useState([]);
    const [format, setFormat] = useState(true);
    const [update, setUpdate] = useState(0);

    useEffect(() => {
        const fetchTasks = async() => {
            try {
                const encodeFalse = encodeURIComponent(false);
                const res = await axios.get(`http://localhost:8080/task/all/${encodeFalse}`, 
                    {headers: {Authorization: `Bearer ${token}`}});
                if(res.status === 200){
                    setTaskList(res.data.data);
                    console.log("Successful fetch task");
                }
            } catch (error) {
                if(!isTokenValid(token)){
                    onLogout();
                }
                console.error("can't fetch task. ", error);
            }
        };
        fetchTasks();
    }, [update])

    const ifFormat = (dueDate) => {
        if(/^\d{4}-\d{2}-\d{2}/.test(dueDate)){
            return true;
        }
        setFormat(false);
        return false;
    }

    const addTask = async(e) => {
        e.preventDefault();
        if(!ifFormat(dueDate)) return;
        try {
            const res = await axios.post("http://localhost:8080/task/add", 
                {title, description, dueDate}, 
                {headers: {Authorization: `Bearer ${token}`}}
            );
            if(res.status === 200){
                console.log("successfully added");
                setTitle('');
                setDes('');
                setDue('');
                setFormat(true);
                setUpdate(prev => prev + 1);
                return;
            }
        } catch (error) {
            if(error.status === 409 || error.status === 403){
                console.log(token);
                onLogout();
            }else{
                console.error("An error occurred");
                return;
            }
        }    
    }
    return(
        <div className="task-body">
            <div className="task-container-left">
                <form onSubmit={addTask} className="task-form">
                <h2>Add Task</h2>
                <div className="align-label-input">
                    <label>Title</label>
                    <input
                        type="text"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </div>
                <div className="align-label-input">
                    <label>Description</label>
                    <input
                        type="text"
                        value={description}
                        onChange={(e) => setDes(e.target.value)}
                        required
                    />
                </div>
                <div className="align-label-input">
                <label>Due Date</label>
                    <input
                        type="text"
                        value={dueDate}
                        onChange={(e) => {
                            setDue(e.target.value);
                            setFormat(true);
                        }}
                        placeholder="YYYY-MM-DD"
                        required
                    /> 
                </div>
                {!format && <p>please enter due date in "yyyy-mm-dd"</p>}
                <div className="align-button">
                    <button type="submit" style={{width:"100%"}}>Submit</button>
                </div>
                
                </form>
                
                
            </div>
            <div className="task-container-right">
                <div className="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Description</th>
                                <th>Due Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {taskList.map((task, id) => (
                            <tr key={id}>
                                <td>{task.title}</td>
                                <td>{task.description}</td>
                                <td>{task.dueDate}</td>
                            </tr>
                        ))}
                        </tbody>   
                    </table>
                </div>
            </div>
            <div>
                <button onClick={onLogout}>Logout</button>
            </div>
        </div>        
    );
}