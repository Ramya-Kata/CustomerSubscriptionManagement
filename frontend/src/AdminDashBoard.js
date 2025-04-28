import React, { useState } from 'react';
import MobilePlans from './MobilePlans';
import TvPlans from './TvPlans';
import WifiPlans from './WifiPlans';
import './AdminDashBoard.css';
import User from './Users';
import Logout from './Logout';

export default function AdminDashBoard() {
  const [selectedPlan, setSelectedPlan] = useState('mobile');
  const [activeTab, setActiveTab] = useState('services');

  const handleDropdownChange = (event) => {
    setSelectedPlan(event.target.value);
  };

  const renderContent = () => {
    switch (selectedPlan) {
      case 'mobile':
        return <MobilePlans activeTab={activeTab} />;
      case 'tv':
        return <TvPlans activeTab={activeTab} />;
      case 'wifi':
        return <WifiPlans activeTab={activeTab} />;
      case 'users':
        return <User />;
        case 'logout':
          return<Logout />
      default:
        return null;
    }
  };

  return (
    <div className="admin-dashboard">
      <header className="admin-header">
        <nav className="nav-menu">
          <button
            className={activeTab === 'services' ? 'active' : ''}
            onClick={() => setActiveTab('services')}
          >
            Services
          </button>
          <button
            className={activeTab === 'update' ? 'active' : ''}
            onClick={() => setActiveTab('update')}
          >
            Update
          </button>
          <button
            className={activeTab === 'delete' ? 'active' : ''}
            onClick={() => setActiveTab('delete')}
          >
            Delete
          </button>
        </nav>

        <h1 className="dashboard-title">Admin Dashboard</h1>

        <select
          value={selectedPlan}
          onChange={handleDropdownChange}
          className="plan-selector"
        >
          <option value="mobile">Mobile Plans</option>
          <option value="tv">TV Plans</option>
          <option value="wifi">WiFi Plans</option>
          <option value="users">User Details</option>
          <option value="logout">Logout</option>
        </select>
      </header>

      <main className="admin-content">{renderContent()}</main>
    </div>
  );
}
