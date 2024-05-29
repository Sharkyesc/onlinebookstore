import React, { useState } from 'react';
import { Layout, Form, Input, Button, Card, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { register } from '../service/register';
import NavBar from '../components/navBar';

const { Header, Content, Footer } = Layout;

const RegisterPage = () => {
    
    const [username, setUsername] = useState('');
    const [nickname, setNickname] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [email, setEmail] = useState('');
    const navigate = useNavigate();

    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    };

    const handleNicknameChange = (e) => {
        setNickname(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleConfirmPasswordChange = (e) => {
        setConfirmPassword(e.target.value);
    };

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };


    const handleRegister = async () => {
        if (password !== confirmPassword) {
            message.error('密码和重复密码不匹配');
            return;
        }

        try {
            const response = await register(username, nickname, email, password);
            if (response.ok) {
                message.success(response.message);
                navigate('/login');
            } else {
                message.error(response.message);
            }
        } catch (error) {
            message.error('注册失败，请稍后再试');
        }
    };


    return (
        <Layout>
            <Header>
                <NavBar />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Card>
                    <Form name="normal_register" className="register-form" initialValues={{ remember: true }} onFinish={handleRegister}>
                        <Form.Item
                            name="username"
                            rules={[{ required: true, message: '请输入你的用户名!' }]}
                        >
                            <Input
                                placeholder="用户名"
                                onChange={handleUsernameChange}
                            />
                        </Form.Item>
                        <Form.Item
                            name="nickname"
                            rules={[{ required: true, message: '请输入你的昵称!' }]}
                        >
                            <Input
                                placeholder="昵称"
                                onChange={handleNicknameChange}
                            />
                        </Form.Item>
                        <Form.Item
                            name="email"
                            rules={[{ required: true, message: '请输入你的邮箱!', type: 'email' }]}
                        >
                            <Input
                                placeholder="邮箱"
                                onChange={handleEmailChange}
                            />
                        </Form.Item>
                        <Form.Item
                            name="password"
                            rules={[{ required: true, message: '请输入你的密码!' }]}
                        >
                            <Input
                                type="password"
                                placeholder="密码"
                                onChange={handlePasswordChange}
                            />
                        </Form.Item>
                        <Form.Item
                            name="confirmPassword"
                            rules={[{ required: true, message: '请再次输入你的密码!' }]}
                        >
                            <Input
                                type="password"
                                placeholder="重复密码"
                                onChange={handleConfirmPasswordChange}
                            />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit" className="register-form-button">
                                注册
                            </Button>
                        </Form.Item>
                    </Form>
                </Card>
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default RegisterPage;




