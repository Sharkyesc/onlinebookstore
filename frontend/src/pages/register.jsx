import React from 'react';
import NavBar from "../components/navBar";
import RegisterForm from '../components/registerForm';
import { Layout } from 'antd';
const { Header, Content, Footer } = Layout;

function RegisterPage() {
    const userInfo = {
        username: '请登录',
        avatarSrc: 'https://img0.baidu.com/it/u=1849651366,4275781386&fm=253&fmt=auto&app=138&f=JPEG?w=585&h=500',
    };

    return (
        <Layout className="layout">
            <Header>
                <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <RegisterForm/>
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};
export default RegisterPage;



