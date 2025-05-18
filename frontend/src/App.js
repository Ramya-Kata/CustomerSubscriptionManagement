import Login from './pages/Login';
import Register from './pages/Register';
import NotFound from './pages/NotFound';
import './styles/App.css';
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Home from './pages/Home';
import Profile from './pages/profile';
import AdminDashBoard from './pages/AdminDashBoard';
import UserDashBoard from './pages/UserDashBoard';
import { UserProvider } from './contexts/UserContext';
import UserMobilePlans from './pages/UserMobilePlan';
import Calculate from './pages/Calculate';
import Payment from './pages/Payment';
import UserTvPlan from './pages/UserTvPlan';
import UserWifiPlan from './pages/UserWifiPlan';
import Logout from './pages/Logout';
function App() {
  return (
    <UserProvider>
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<Home />}></Route>
     <Route path='/login' element={<Login />}></Route>
     <Route path='/register' element={<Register />}></Route>
     <Route path='/profile' element={<Profile />}></Route>
     <Route path='/admin' element={<AdminDashBoard />}> </Route>
     <Route path='/user' element={<UserDashBoard />}></Route>
     <Route path='/user/mobile' element={<UserMobilePlans/>}></Route>
     <Route path='/user/tv' element={<UserTvPlan/>}></Route>
     <Route path='/bill' element={<Calculate />}></Route>
     <Route path='/user/wifi' element={<UserWifiPlan />}></Route>
     <Route path='/payment' element={<Payment />}></Route>
     <Route path='/logout' element={<Logout />}></Route>
     <Route path='*' element={<NotFound />}></Route>

    </Routes>
    </BrowserRouter>
    </UserProvider>
  );
}

export default App;
