import React, { useState, useEffect } from 'react';

export default function MobilePlans({ activeTab }) {
  const [plans, setPlans] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [formValues, setFormValues] = useState({
    planName: '',
    dataLimit: '',
    callMinutes: 0,
    textLimit: 0,
    monthlyCost: 0.0,
    discount: 0.0,
  });

  const [planId, setPlanId] = useState('');

  useEffect(() => {
    if (activeTab === 'services') {
      fetchMobilePlans(currentPage);
    }
  }, [activeTab, currentPage]);

  const fetchMobilePlans = async (page) => {
    const token = sessionStorage.getItem('token');
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
            Authorization: `Bearer ${token}`,
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
      const response = await fetch(`http://localhost:8765/plan/mobile/update`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(formValues),
      });

      if (response.ok) {
        alert('Plan updated/added successfully!');
        fetchMobilePlans(currentPage); // Refresh the plans
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
      const response = await fetch(`http://localhost:8765/plan/mobile/delete/${id}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        alert(`Plan with ID: ${id} deleted successfully!`);
        setPlanId('');
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
          <h2>Mobile Plans</h2>
          <div className="services-table">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Plan Name</th>
                  <th>Data Limit</th>
                  <th>Call Minutes</th>
                  <th>Text Limit</th>
                  <th>Monthly Cost</th>
                  <th>Discount (%)</th>
                </tr>
              </thead>
              <tbody>
                {plans.map((plan) => (
                  <tr key={plan.mobileId}>
                    <td>{plan.mobileId}</td>
                    <td>{plan.planName}</td>
                    <td>{plan.dataLimit}</td>
                    <td>{plan.callMinutes}</td>
                    <td>{plan.textLimit}</td>
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
              <h2 className="update-plan-title">Update or Add Mobile Plan</h2>
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
                <label>Data Limit</label>
                <input
                  type="text"
                  name="dataLimit"
                  placeholder="Data Limit"
                  value={formValues.dataLimit}
                  onChange={handleInputChange}
                  required
                />
                <label>Call Minutes</label>
                <input
                  type="number"
                  name="callMinutes"
                  placeholder="Call Minutes"
                  value={formValues.callMinutes}
                  onChange={handleInputChange}
                  required
                />
                <label>Text Limit</label>
                <input
                  type="number"
                  name="textLimit"
                  placeholder="Text Limit"
                  value={formValues.textLimit}
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
          <h2 className="delete-plan-title">Delete a Mobile Plan</h2>
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
