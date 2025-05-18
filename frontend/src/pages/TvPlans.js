import React, { useState, useEffect } from 'react';

export default function TvPlans({ activeTab }) {
  const [plans, setPlans] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [formValues, setFormValues] = useState({
    planName: '',
    premiumChannels: 0,
    monthlyCost: 0.0,
    discount: 0.0,
  });

  const [planId, setPlanId] = useState('');

  useEffect(() => {
    if (activeTab === 'services') {
      fetchTvPlans(currentPage);
    }
  }, [activeTab, currentPage]);

  const fetchTvPlans = async (page) => {
    const token = sessionStorage.getItem('token');

    if (!token) {
      alert('You must be logged in to access this data.');
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8765/plan/tv/get/all?pageNumber=${page}&pageSize=5&sortBy=tvId&direction=DESC`,
        {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.ok) {
        const data = await response.json();
        setPlans(data.content);
        setTotalPages(data.totalPages);
      } else {
        alert('Failed to fetch TV plans.');
      }
    } catch (error) {
      console.error('Error fetching TV plans:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleUpdate = async () => {
    const token = sessionStorage.getItem('token');
    if (!token) {
      alert('You must be logged in to update a plan.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8765/plan/tv/update`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(formValues),
      });

      if (response.ok) {
        alert('Plan updated/added successfully!');
        fetchTvPlans(currentPage); // Refresh the plans
      } else {
        alert('Failed to update/add plan.');
      }
    } catch (error) {
      console.error('Error updating plan:', error);
    }
  };

  const handleDelete = async (id) => {
    const token = sessionStorage.getItem('token');
    if (!token) {
      alert('You must be logged in to delete a plan.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8765/plan/tv/delete/${id}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        alert(`Plan with ID: ${id} deleted successfully!`);
        setPlanId(''); // Clear the input field after deletion
      } else {
        alert('Failed to delete plan.');
      }
    } catch (error) {
      console.error('Error deleting plan:', error);
    }
  };

  const handlePageChange = (page) => {
    if (page >= 0 && page < totalPages) {
      setCurrentPage(page);
    }
  };

  return (
    <div>
      {activeTab === 'services' && (
        <div className="services-container">
          <h2>TV Plans</h2>
          <div className="services-table">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Plan Name</th>
                  <th>Premium Channels</th>
                  <th>Monthly Cost</th>
                  <th>Discount (%)</th>
                </tr>
              </thead>
              <tbody>
                {plans.map((plan) => (
                  <tr key={plan.tvId}>
                    <td>{plan.tvId}</td>
                    <td>{plan.planName}</td>
                    <td>{plan.premiumChannels}</td>
                    <td>{plan.monthlyCost}</td>
                    <td>{plan.discount}</td>
                  </tr>
                ))}
              </tbody>
            </table>
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
        </div>
      )}

      {activeTab === 'update' && (
        <div>
          <form>
            <div className="update-plan-container">
              <h2 className="update-plan-title">Update or Add TV Plan</h2>
              <form className="update-plan-form">
                <label>Plan Name</label>
                <input
                  type="text"
                  name="planName"
                  placeholder="Plan Name"
                  value={formValues.planName}
                  onChange={handleInputChange}
                  required
                />
                <label>Premium Channels</label>
                <input
                  type="number"
                  name="premiumChannels"
                  placeholder="Premium Channels"
                  value={formValues.premiumChannels}
                  onChange={handleInputChange}
                  required
                />
                <label>Monthly Cost</label>
                <input
                  type="number"
                  name="monthlyCost"
                  placeholder="Monthly Cost"
                  value={formValues.monthlyCost}
                  onChange={handleInputChange}
                  required
                />
                <label>Discount (%)</label>
                <input
                  type="number"
                  name="discount"
                  placeholder="Discount (%)"
                  value={formValues.discount}
                  onChange={handleInputChange}
                  required
                />
                <button type="button" className="save-button" onClick={handleUpdate}>
                  Save
                </button>
              </form>
            </div>
          </form>
        </div>
      )}

      {activeTab === 'delete' && (
        <div className="delete-plan-container">
          <h2 className="delete-plan-title">Delete a TV Plan</h2>
          <form className="delete-plan-form">
            <label>Enter Plan ID</label>
            <input
              type="number"
              name="planId"
              className="delete-plan-input"
              placeholder="Enter Plan ID"
              value={planId}
              onChange={(e) => setPlanId(e.target.value)}
              required
            />
            <button
              type="button"
              className="delete-icon-button"
              onClick={() => handleDelete(planId)}
              disabled={!planId}
              title="Delete Plan"
            >
              &#x1F5D1; {/* Trash bin icon */}
            </button>
          </form>
        </div>
      )}
    </div>
  );
}
