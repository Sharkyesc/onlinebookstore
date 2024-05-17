import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ component: Component }) => {
    const isLoggedIn = sessionStorage.getItem('user'); 

    if (!isLoggedIn) {
        alert("请先登录！");
        return <Navigate to="/login" replace />;
    }

    return <Component />;
};

export default ProtectedRoute;