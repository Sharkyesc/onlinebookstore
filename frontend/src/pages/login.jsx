import React, { useState } from 'react';
import { Layout, Form, Input, Button, Checkbox, Card, Flex, message } from 'antd';
import WindowWidth from '../utils/getWidth';  
import NavBar from '../components/navBar';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { login } from '../service/login';
import { useNavigate } from 'react-router-dom';

const { Header, Content, Footer } = Layout;

const LoginPage = () => {
    
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();
  
    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleLogin = async () => {
        try {
            console.log(username,password);
            const response = await login(username, password);
            if (response.ok) {
                message.success(response.message);
                sessionStorage.setItem("username", username);
                navigate('/home'); 
            } else {
                message.error(response.message);
            }
        } catch (error) {
            message.error('登录失败，请稍后再试');
        }
    };
  
    return (
        <Layout>
            <Header>
                <NavBar />
            </Header>
            <Content>
                <Card hoverable style={{ width: WindowWidth() * 0.8, margin: 'auto', marginTop: 20 }}>
            
                <Flex justify="space-between" align="center" style={{ gap: '30px' }} >
                    <img 
                        alt="Bookstore" 
                        src={'https://cdn.gtn9.com/10-02-2021/zkpNXy4TcRPG2xQfAWrY.jpg?x-oss-process=image/resize,w_1088'} 
                        style={{ width: '60%', borderRadius: 6 }} 
                    />


                    <Form 
                        name="normal_login" 
                        className="login-form" 
                        initialValues={{remember: true,}}
                        onFinish={handleLogin}
                        style={{ flex: 1, padding: '0 20px' }}
                    >
                        <h2 style={{ marginBottom: 20 }}>账号密码登录</h2>
                        <Form.Item
                            name="username"
                            rules={[
                              { 
                                required: true,
                                message: '请输入你的用户名!',
                              },
                            ]}
                        >
                            <Input 
                                prefix={<UserOutlined className="site-form-item-icon" />} 
                                placeholder="用户名" 
                                onChange={handleUsernameChange}
                            />
                        </Form.Item>
                        <Form.Item
                            name="password"
                            rules={[
                              {
                                required: true,
                                message: '请输入你的密码!',
                              },
                            ]}
                            style={{ marginBottom: 10 }}
                        >
                            <Input 
                                prefix={<LockOutlined className="site-form-item-icon" />} 
                                type="password" 
                                placeholder="密码" 
                                onChange={handlePasswordChange}
                            />
                        </Form.Item>
                        <Form.Item style={{ margin: 0, display: 'flex', justifyContent: 'flex-end' }}>
                            <a className="login-form-forgot" href="/">忘记密码</a>
                        </Form.Item>
                        <Form.Item name="remember" valuePropName="checked" >
                            <Checkbox>保持登录状态</Checkbox>
                        </Form.Item>
                        <Form.Item>
                            <Button 
                                type="primary" 
                                htmlType="submit" 
                                className="login-form-button" 
                                style={{ width: '100%' }}
                            > 登录</Button>
                            
                        </Form.Item>
                        <Form.Item style={{ margin: 0, display: 'flex', justifyContent: 'center' }}>
                            还没有账号？ <a href="/register">立即注册</a>
                        </Form.Item>
                    </Form>
                    </Flex>
                </Card>
                
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
  };
export default LoginPage;

