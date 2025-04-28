import './form.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from './UserContext';
import { useContext } from 'react';

export default function Login(){
    const[userName, setUserName]=useState("");
    const[password, setPassword]=useState("");
    const navigate = useNavigate();
    const ROLES = {
        USER: 'user',
        ADMIN: 'admin',
      };

      const {setUserDetails}=useContext(UserContext);

    const handleUserName = (event) =>{
        event.preventDefault();
        console.log(userName, password);
        setUserName(event.target.value);
        console.log("userName changed!!"+userName);
    }

    const handlePassword = (event)=>{
        setPassword(event.target.value);
        console.log("password changed!!"+password);
    }

    const onButtonClick= async(event)=>{
        event.preventDefault();
        console.log("login button is clicked!!");
        const loginData = {
            username: userName,
            password: password,
          };
          try {
            // Make a POST request to login endpoint
            // const response = await fetch(`${process.env.REACT_APP_API_URL}/auth/login`, {
              const response = await fetch(`http://localhost:8765/auth/login`, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(loginData),
            });
      
            if (response.ok) {
              // Retrieve the token from the response
              const token = await response.text();
              console.log('Token received:', token);
      
              // Store the token in sessionStorage
              sessionStorage.setItem('token', token);
      
              // Fetch user details with the token
              const userResponse = await fetch(
                `http://localhost:8765/auth/find/${userName}`,
                {
                  method: 'GET',
                  headers: {
                    Authorization: `Bearer ${token}`,
                  },
                }
              );
      
              if (userResponse.ok) {
                const userDetails = await userResponse.json();
                console.log('User details:', userDetails);
      
                setUserDetails({
                    username:userDetails.username,
                    email:userDetails.email,
                });
                // Navigate based on user role
                if (userDetails.role.toLowerCase() === ROLES.USER) {
                  navigate('/user');
                } else if (userDetails.role.toLowerCase() === ROLES.ADMIN) {
                  navigate('/admin');
                }
              } else {
                alert('Failed to fetch user details.');
              }
            } else {
              alert('Invalid credentials, please try again.');
            }
          } catch (error) {
            console.error('Login error:', error);
            alert('An error occurred during login. Please try again.');
          }
    }
    return (
        <div className='body' style={{ background: 'linear-gradient(-45deg,rgb(42, 70, 84),rgb(101, 63, 78))', height: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
       
        <div className="form-container">
            
            <form onSubmit={(e)=>onButtonClick(e)}>
                <h2>Login</h2>
                <div className="input-group">
                    <label htmlFor="identifier">Username or Email</label>
                    <input type="text" id="identifier" value={userName} onChange={(e)=>handleUserName(e)}placeholder="Enter your username" required></input>
                </div>
                <div className="input-group">
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" value={password} onChange={(e)=> handlePassword(e)} placeholder="Enter your password" required></input>
                </div>  

                <button type="submit">Login</button>
              </form>
              <div style={{color: 'black'}}> <p>First Time? <a href='/'>Register</a></p></div>

         </div>
         </div>
    )
}