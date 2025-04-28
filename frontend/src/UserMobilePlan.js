import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './UserMobilePlan.css';

const UserMobilePlan = () => {
  const [plans, setPlans] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [selectedPlan, setSelectedPlan] = useState(null);
  const [duration, setDuration] = useState('Monthly');
  const navigate = useNavigate();

  const fetchMobilePlans = async (page) => {
    const token = sessionStorage.getItem('token'); // Retrieve the token from sessionStorage
    if (!token) {
      alert('You must be logged in to access this data.');
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8765/plan/mobile/get/all?pageNumber=${page}&pageSize=5&sortBy=mobileId&direction=DESC`,
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
        alert('Failed to fetch mobile plans.');
      }
    } catch (error) {
      console.error('Error fetching mobile plans:', error);
    }
  };

  useEffect(() => {
    fetchMobilePlans(currentPage);
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

    // Navigate to the calculation page with the selected plan and duration
    navigate(`/bill?planType=mobile&planId=${selectedPlan.mobileId}&numberOfMonths=${duration}`, {
      state: {
        planId: selectedPlan.mobileId,
        numberOfMonths: duration,
        planType: 'mobile',
      },
    });
  };

  return (
    <div className="mobile-plans-container">
      <h2>Available Mobile Plans</h2>
      <div className="plans-list">
        {plans.map((plan) => (
          <div
            key={plan.mobileId}
            className={`plan-card ${selectedPlan?.mobileId === plan.mobileId ? 'selected' : ''}`}
          >
            <h3>{plan.planName}</h3>
            <p>Data: {plan.dataLimit}</p>
            <p>Calls: {plan.callMinutes} minutes</p>
            <p>Texts: {plan.textLimit}</p>
            <p>Price: ${plan.monthlyCost}</p>
            <button onClick={() => handlePlanSelect(plan)}>
              {selectedPlan?.mobileId === plan.mobileId ? 'Selected' : 'Select Plan'}
            </button>
          </div>
        ))}
      </div>

      {/* Duration Selection */}
      <div className="duration-selection">
        <label htmlFor="duration">Select Duration:</label>
        <select id="duration" value={duration} onChange={(e) => setDuration(e.target.value)}>
          <option value="Monthly">Monthly</option>
          <option value="Quarterly">Quarterly</option>
          <option value="Half-Yearly">Half-Yearly</option>
          <option value="Annually">Annually</option>
        </select>
      </div>

      {/* Pagination Controls */}
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

export default UserMobilePlan;
