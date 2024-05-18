import { message } from 'antd';
import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ component: Component }) => {
    const isLoggedIn = sessionStorage.getItem('user'); 

    if (!isLoggedIn) {
        message.warning("请先登录！");
        return <Navigate to="/login" />;
    }

    return <Component />;
};

export default ProtectedRoute;