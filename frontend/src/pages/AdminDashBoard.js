import React, { useState } from 'react';

import MobilePlans from './MobilePlans';
import TvPlans from './TvPlans';
import WifiPlans from './WifiPlans';

import '../styles/AdminDashBoard.css';

import User from './Users';
import Logout from './Logout';

/**
 * AdminDashBoard Component
 * 
 * Displays and controls different admin functionalities:
 * - View & manage Mobile, TV, and WiFi plans
 * - View all registered users
 * - Handle plan updates and deletions
 * - Support logout from dropdown
 */

export default function AdminDashBoard() {

  // Tracks which plan category is selected (via dropdown)
  const [selectedPlan, setSelectedPlan] = useState('mobile');

  // Tracks which admin operation tab is active (view, update, delete)
  const [activeTab, setActiveTab] = useState('services');

   /**
   * Handles dropdown change to dynamically switch the selected plan type.
   */
  const handleDropdownChange = (event) => {
    setSelectedPlan(event.target.value);
  };

  /**
   * Renders the appropriate plan or view based on dropdown selection.
   * Also passes the currently active tab (e.g., update/delete) to subcomponents.
   */
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

      {/* Top admin menu with tab-based actions */}
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

        {/* Dropdown to choose which service type to manage */}
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
