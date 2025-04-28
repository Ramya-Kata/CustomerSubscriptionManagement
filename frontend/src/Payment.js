import { useLocation, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import './Payment.css';

const Payment = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { amount } = location.state || {}; // Receive the amount from the BillPage
  const [accountNumber, setAccountNumber] = useState('');
  const [responseMessage, setResponseMessage] = useState('');

  const handlePayment = async () => {
    if (!accountNumber) {
      alert('Please enter an account number.');
      return;
    }

    const token = sessionStorage.getItem('token'); // Retrieve the token from sessionStorage
    if (!token) {
      alert('You must be logged in to proceed with payment.');
      navigate('/login');
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8765/account/transfer?fromAccountNumber=${accountNumber}&amount=${amount}`,
        {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      if (response.ok) {
        const data = await response.text(); // Assuming the response is plain text
        setResponseMessage(data);
        alert(data); // Display the success message
        navigate('/user'); // Redirect to the dashboard or success page
      } else {
        alert('Payment failed. Please try again.');
      }
    } catch (error) {
      console.error('Error during payment:', error);
      alert('An error occurred. Please try again.');
    }
  };

  if (!amount) {
    return <p>Error: Missing payment details.</p>;
  }

  return (
    <div className="payment-page">
      <h2>Payment</h2>
      <p><strong>Total Amount:</strong> ${amount}</p>
      <div className="payment-form">
        <label htmlFor="accountNumber">Enter Account Number:</label>
        <input
          type="text"
          id="accountNumber"
          value={accountNumber}
          onChange={(e) => setAccountNumber(e.target.value)}
          placeholder="Enter your account number"
          required
        />
        <button className="subscribe-button" onClick={handlePayment}>
          Subscribe
        </button>
      </div>
      {responseMessage && <p className="response-message">{responseMessage}</p>}
    </div>
  );
};

export default Payment;
