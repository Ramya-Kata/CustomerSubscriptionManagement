import { useLocation, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './Calculate.css';


const Calculate = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { planId, numberOfMonths, planType } = location.state || {};
  const [calculationResult, setCalculationResult] = useState(null);
  useEffect(() => {
    const fetchCalculation = async () => {
      const token = sessionStorage.getItem('token');
      if (!token) {
        alert('You must be logged in to access this data.');
        navigate('/login');
        return;
      }

      try {
        const response = await fetch(
          `http://localhost:8765/calculate/proxy/amount?planType=${planType}&planId=${planId}&numberOfMonths=${numberOfMonths}`,
          {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          }
        );

        if (response.ok) {
          const data = await response.json();
          setCalculationResult(data);
        } else {
          alert('Failed to fetch calculation.');
        }
      } catch (error) {
        console.error('Error fetching calculation:', error);
      }
    };

    if (planId && numberOfMonths && planType) {
      fetchCalculation();
    }
  }, [planId, numberOfMonths, planType, navigate]);

  const handlePayment = () => {
    console.log("Payment Started");
    console.log()
    navigate(`/payment?amount=${calculationResult.totalAmount}`  ,
        {
            state: {
                amount: calculationResult.totalAmount,
            },
          }

    )
  };

  if (!planId || !numberOfMonths || !planType) {
    return <p>Error: Missing required data.</p>;
  }

  return (
    <div className="calculate-container">
      <h2>Final Bill: </h2>
      {calculationResult ? (
        <div className="results-card">
          <p><strong>Base Amount:</strong> ${calculationResult.baseAmount}</p>
          <p><strong>Number of Months:</strong> {calculationResult.numberOfMonths}</p>
          <p><strong>Discount:</strong> {calculationResult.discountPercentage}%</p>
          <p><strong>Tax:</strong> ${calculationResult.tax}</p>
          <p><strong>Total Amount:</strong> ${calculationResult.totalAmount}</p>
          <button className="pay-now-button" onClick={handlePayment}>
            Pay Now
          </button>
        </div>
      ) : (
        <p className="loading-text">Loading...</p>
      )}
    </div>
  );
};

export default Calculate;
