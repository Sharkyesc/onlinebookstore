import React, { useState } from 'react';
import { Layout, Form, Input, Button, Card } from 'antd';
import { useNavigate } from 'react-router-dom';
import { register } from '../service/register';
import NavBar from '../components/navBar';

const { Header, Content, Footer } = Layout;

const RegisterPage = () => {
    
    const unknownInfo = {
        username: '请登录',
        avatarSrc: 'https://img0.baidu.com/it/u=1849651366,4275781386&fm=253&fmt=auto&app=138&f=JPEG?w=585&h=500',
    };

    const [username, setUsername] = useState('');
    const [nickname, setNickname] = useState('');
    const [password, setPassword] = useState('');
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

    const handleRegister = async () => {
        try {
            const response = await register(username, nickname, password);
            if (response.ok) {
                alert('注册成功');
                navigate('/login');
            } else {
                alert('注册失败');
            }
        } catch (error) {
            alert('注册失败，请稍后再试');
        }
    };

    return (
        <Layout>
            <Header>
                <NavBar username={unknownInfo.username} avatarSrc={unknownInfo.avatarSrc} />
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
                            name="password"
                            rules={[{ required: true, message: '请输入你的密码!' }]}
                        >
                            <Input
                                type="password"
                                placeholder="密码"
                                onChange={handlePasswordChange}
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




