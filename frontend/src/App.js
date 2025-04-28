import Login from './Login';
import Register from './Register';
import NotFound from './NotFound';
import './App.css';
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Home from './Home';
import Profile from './profile';
import AdminDashBoard from './AdminDashBoard';
import UserDashBoard from './UserDashBoard';
import { UserProvider } from './UserContext';
import UserMobilePlans from './UserMobilePlan';
import Calculate from './Calculate';
import Payment from './Payment';
import UserTvPlan from './UserTvPlan';
import UserWifiPlan from './UserWifiPlan';
import Logout from './Logout';
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
