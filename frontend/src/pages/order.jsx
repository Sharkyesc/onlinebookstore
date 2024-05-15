import React, { useEffect, useState } from 'react';
import { Layout, Table, Button, Space, Typography, Divider } from 'antd';
import NavBar from '../components/navBar';
import $ from 'jquery';

const { Header, Content, Footer } = Layout;
const { Title } = Typography;

const userInfo = {
  username: '玉皇大帝',
  avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
};


const columns = [
  {
    title: '订单编号',
    dataIndex: 'orderNumber',
    key: 'orderNumber',
  },
  {
    title: '订单时间',
    dataIndex: 'orderTime',
    key: 'orderTime',
  },
  {
    title: '书名',
    dataIndex: 'bookName',
    key: 'bookName',
  },
  {
    title: '数量',
    dataIndex: 'quantity',
    key: 'quantity',
  },
  {
    title: '收货地址',
    dataIndex: 'shippingAddress',
    key: 'shippingAddress',
  },
  {
    title: '总价',
    dataIndex: 'totalPrice',
    key: 'totalPrice',
  },
];

const OrderPage = () => {
  const [orders, setOrders] = useState([]);
  
  fetch('http://localhost:8080/api/orders')
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.json();
  })
  .then(data => {
    setOrders(data);
  })
  .catch(error => {
    console.error('There was a problem with your fetch operation:', error);
  });


  return (
    <Layout>
        <Header>
            <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
        </Header>
        <Content style={{ padding: '0 50px' }}>
          <div className="site-layout-content">
            <Title level={2}>订单详情</Title>
            <Divider />
            <Table columns={columns} dataSource={orders} />
            <div style={{ marginTop: 20 }}>
              <Space>
                <Button type="primary">继续购物</Button>
              </Space>
            </div>
          </div>
        </Content>
        <Footer style={{ textAlign: 'center' }}>©2024</Footer>
    </Layout>
  );
};

export default OrderPage;



