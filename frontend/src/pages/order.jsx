import React, { useEffect, useState } from 'react';
import { Layout, Button } from 'antd';
import NavBar from '../components/navBar';
import { getAllOrders } from '../service/order';
import OrderContent from '../components/orderContent';

const { Header, Content, Footer } = Layout;


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
        <NavBar />
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