import btlogo from "./bt_2019_logo.png"; 
import { useNavigate } from 'react-router-dom';
import './Home.css'
export default function Home(){
    const navigate = useNavigate();
    return (
        <div className="body">
        <div className="home">
            <img src={btlogo} alt="BT Logo" className="logo"/>
            <div>
            <h1>Welcome to BT Subscription</h1>
            <h3>Login To Select Your Subscription Plan</h3>
            <p>We offer flexible subscription plans to fit your needs. Choose from our range of options, from basic to premium, and enjoy the best features tailored for you!</p>
              <div className="buttons">
                <button onClick={() =>navigate('/login')}>Login</button>
                <button onClick={() =>navigate('/register')}>Register</button>  
                </div>
                </div>     
        </div>
        </div>
        )
}