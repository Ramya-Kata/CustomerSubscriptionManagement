import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './UserMobilePlan.css'; // Reusing the same CSS

const UserWifiPlan = () => {
  const [plans, setPlans] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [selectedPlan, setSelectedPlan] = useState(null);
  const [duration, setDuration] = useState('Monthly');
  const navigate = useNavigate();

  const fetchWifiPlans = async (page) => {
    const token = sessionStorage.getItem('token'); // Retrieve the token from sessionStorage
    if (!token) {
      alert('You must be logged in to access this data.');
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8765/plan/wifi/get/all?pageNumber=${page}&pageSize=5&sortBy=id&direction=DESC`,
        {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`, // Include the token in the Authorization header
          },
        }
      );

      if (response.ok) {
        const data = await response.json();
        setPlans(data.content);
        setTotalPages(data.totalPages);
      } else {
        alert('Failed to fetch WiFi plans.');
      }
    } catch (error) {
      console.error('Error fetching WiFi plans:', error);
    }
  };

  useEffect(() => {
    fetchWifiPlans(currentPage);
  }, [currentPage]);

  const handlePlanSelect = (plan) => {
    setSelectedPlan(plan);
  };

  const handlePageChange = (page) => {
    if (page >= 0 && page < totalPages) {
      setCurrentPage(page);
    }
  };

  const handleProceed = () => {
    if (!selectedPlan) {
      alert('Please select a plan and duration before proceeding.');
      return;
    }

    navigate(`/bill?planType=wifi&planId=${selectedPlan.id}&numberOfMonths=${duration}`, {
      state: {
        planId: selectedPlan.id,
        numberOfMonths: duration,
        planType: 'wifi',
      },
    });
  };

  return (
    <div className="mobile-plans-container">
      <h2>Available WiFi Plans</h2>
      <div className="plans-list">
        {plans.map((plan) => (
          <div
            key={plan.id}
            className={`plan-card ${selectedPlan?.id === plan.id ? 'selected' : ''}`}
          >
            <h3>{plan.planName}</h3>
            <p>Data Limit: {plan.dataLimit}</p>
            <p>Speed: {plan.speed} Mbps</p>
            <p>Price: ${plan.monthlyCost}</p>
            <button onClick={() => handlePlanSelect(plan)}>
              {selectedPlan?.id === plan.id ? 'Selected' : 'Select Plan'}
            </button>
          </div>
        ))}
      </div>

      <div className="duration-selection">
        <label htmlFor="duration">Select Duration:</label>
        <select id="duration" value={duration} onChange={(e) => setDuration(e.target.value)}>
          <option value="Monthly">Monthly</option>
          <option value="Quarterly">Quarterly</option>
          <option value="Half-Yearly">Half-Yearly</option>
          <option value="Annually">Annually</option>
        </select>
      </div>

      <div className="pagination">
        <button
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage === 0}
        >
          &lt;
        </button>
        {Array.from({ length: totalPages }, (_, index) => (
          <button
            key={index}
            className={index === currentPage ? 'active' : ''}
            onClick={() => handlePageChange(index)}
          >
            {index + 1}
          </button>
        ))}
        <button
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage === totalPages - 1}
        >
          &gt;
        </button>
      </div>
      
      <div className="button-container">
  {/* Proceed Button */}
  <button className="proceed-button" onClick={() => navigate('/user')}>
    Go Back
  </button>
  <button className="proceed-button" onClick={handleProceed}>
    Proceed
  </button>
  
</div>
    </div>
  );
};

export default UserWifiPlan;
