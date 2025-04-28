import './form.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Register(){
    const[userName, setUserName]= useState("");
    const[password, setPassword]=useState("");
    const[email, setEmail] = useState("");
    const navigate = useNavigate();

    const handleUserName = (e) => {
        e.preventDefault();
        
        setUserName(e.target.value);
        console.log(userName);
    };

    const handlePassword = (e)=>{
        e.preventDefault();
        setPassword(e.target.value);
        console.log(userName);
       
    };

    const handleEmail = (e)=>{
        e.preventDefault();
        setEmail(e.target.value);
        console.log( email);
    }

    const onButtonClick = async(e) => {
        e.preventDefault();
        console.log("Register Button is clicked..!!");
        const userData = {
            username: userName,
            password: password,
            email: email,
          };
      
          try {
            console.log("API URL:", process.env.REACT_APP_API_URL);
            // const response = await fetch(`${process.env.REACT_APP_API_URL}/auth/register`, { -> it didnt work because .env is not loaded and getting undefined when logged, shoud go through this 
              const response = await fetch(`http://localhost:8765/auth/register`, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(userData),
            });
            if (response.ok) {
                alert("User registered successfully! Please log in to access available plans.");
                navigate('/');
              } else {
                const errorData = await response.json();
                alert(`Registration failed: ${errorData.message || "Unknown error occurred"}`);
              }
            } catch (error) {
              alert(`Error: ${error.message}`);
            }
        
    };
    return (
        <div className='body' style={{ background: 'linear-gradient(135deg,rgba(17, 29, 57, 0.81),rgb(68, 38, 78))', height: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <div className="form-container">
            <div>
            <form onSubmit={(e)=>onButtonClick(e)}>
                <h2>Register</h2>
                <div className="input-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" value={userName} onChange={(e)=>handleUserName(e)}placeholder="Choose a username" required></input>
                </div>
                <div className="input-group">
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" value={password} onChange={(e)=> handlePassword(e)} placeholder="Choose a password" required></input>
                </div>  
                <div className="input-group">
                    <label htmlFor="email">email</label>
                    <input type="email" id="email" value={email} onChange={(e)=> handleEmail(e)} placeholder="Enter your Email" required></input>
                </div> 
                <button type="submit">Register</button>
              </form>
            <div style={{color: 'black'}}> <p>Have an Account? <a href='/'>Login</a></p></div>
        </div>
        </div>
        </div>
    )
}