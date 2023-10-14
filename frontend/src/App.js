import './App.css';
import React, { useState, useEffect } from 'react';
import AdminLogin from "./components/Admin/AdminLogin";
import UserView from "./components/User/UserView"
import AdminView from "./components/Admin/AdminView"
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { postData } from './services/apiService';

function App() {

  const [isAdmin, setIsAdmin] = useState(null);

  const authorize = async () => {
    try {
      const response = await postData('admin/authorize', null, localStorage.getItem('token'));
      return response.status === 200;
    } catch (error) {
      return false;
    }
  };

  useEffect(() => {
    const checkAdminStatus = async () => {
      const adminStatus = await authorize();
      if (isAdmin === null) {
        setIsAdmin(adminStatus);
      }
    };

    checkAdminStatus();
  }, [])


  const setAdminTrue = () => {
    setIsAdmin(true);
  }


  if (isAdmin === null) {
    return (
      <div className="loading-container">
        Loading...
      </div>
    )
  } else {
    return (
      <Router>
        <Routes>
          <Route path="/" element={<UserView />} />
          <Route path="/admin" element={<AdminLogin setAdminTrue={setAdminTrue} />} />
          {isAdmin ? (
            <Route path="/admin/dashboard" element={<AdminView />} />
          ) : (
            <Route path="/admin/dashboard" element={<Navigate to="/" />} />
          )}
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </Router>
    );
  }
}


export default App;
