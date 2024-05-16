import React, { useEffect, useState } from 'react';
import { Layout, Button } from 'antd';
import NavBar from '../components/navBar';
import { getAllOrders } from '../service/order';
import OrderContent from '../components/orderContent';

const { Header, Content, Footer } = Layout;

const userInfo = {
  username: '玉皇大帝',
  avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
};

const OrderPage = () => {
  const [orders, setOrders] = useState([]);

  const getOrder = async () => {
    let orders = await getAllOrders();
    setOrders(orders);
  }

  useEffect(() => {
    getOrder();
  }, []);


  return (orders && 
    <Layout>
      <Header>
        <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <OrderContent 
          orderInfo={ orders } 
        />
      </Content>
      <Footer style={{ textAlign: 'center' }}>©2024</Footer>
    </Layout>
  );
};

export default OrderPage;