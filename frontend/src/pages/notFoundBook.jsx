import React from 'react';
import NavBar from '../components/navBar'; 
import WindowWidth from '../utils/getWidth';
import { Button, Card, Layout, Row, Col } from 'antd';
import { ExclamationCircleOutlined } from '@ant-design/icons';

const { Header, Content, Footer } = Layout;

const NotFoundPage = () => {
    return (
        <Layout>
            <Header style={{ background: '#001529', padding: 0 }}>
                <NavBar />
            </Header>
            <Content style={{ padding: '50px 50px', minHeight: '80vh', backgroundColor: '#f0f2f5' }}>
                <Row justify="center" align="middle" style={{ height: '100%' }}>
                    <Col xs={24} sm={20} md={16} lg={12} xl={8}>
                        <Card hoverable style={{ width: '100%', textAlign: 'center' }}>
                            <ExclamationCircleOutlined style={{ fontSize: '48px', color: '#ff4d4f', marginBottom: '20px' }} />
                            <h1>书籍已下架</h1>
                            <p>你所访问的书籍已下架，无法查看详情。</p>
                            <p>点击下方按钮可返回上一页面。</p>
                            <Button 
                                type='primary' 
                                onClick={() => window.history.back()} 
                                size='large' 
                                className="back-button"
                                style={{ backgroundColor: '#000000', color: 'white', marginTop: '15px' }}
                            >
                                返回
                            </Button>
                        </Card>
                    </Col>
                </Row>
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default NotFoundPage;
