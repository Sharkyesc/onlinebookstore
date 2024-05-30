import React, { useEffect, useState } from 'react';
import { Layout, Input, DatePicker, Row, Col, Divider, Typography } from 'antd';
import NavBar from '../components/navBar';
import { getOrders } from '../service/order';
import OrderContent from '../components/orderContent';

const { Header, Content, Footer } = Layout;
const { RangePicker } = DatePicker;
const { Search } = Input;
const { Title } = Typography;

const OrderPage = () => {

  const [orders, setOrders] = useState([]);
  const [filteredOrders, setFilteredOrders] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [dateRange, setDateRange] = useState([null, null]);

  const fetchOrders = async (bookName, startDate, endDate) => {
    let orders = await getOrders(bookName, startDate, endDate);
    setOrders(orders);
    setFilteredOrders(orders);
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  const handleSearch = (value) => {
    setSearchTerm(value);
    fetchOrders(value, dateRange ? (dateRange[0] ? dateRange[0].format('YYYY-MM-DDTHH:mm:ss') : null) : null, dateRange ? (dateRange[1] ? dateRange[1].format('YYYY-MM-DDTHH:mm:ss') : null) : null);
  };

  const handleDateChange = (dates) => {
    setDateRange(dates);
    fetchOrders(searchTerm, dates ? (dates[0] ? dates[0].format('YYYY-MM-DDTHH:mm:ss') : null) : null, dates ? (dates[1] ? dates[1].format('YYYY-MM-DDTHH:mm:ss') : null) : null);
  };

  return (orders && 
    <Layout>
      <Header>
        <NavBar />
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <Title level={2}>订单详情</Title>
        <Divider />
        <Row gutter={16} style={{ marginBottom: 20 }}>
          <Col span={8}>
            <Search
              placeholder="按书名搜索"
              onSearch={handleSearch}
              enterButton
            />
          </Col>
          <Col span={16}>
            <RangePicker
              style={{ width: '100%' }}
              onChange={handleDateChange}
              allowClear
            />
          </Col>
        </Row>
        <OrderContent orderInfo={filteredOrders} />
      </Content>
      <Footer style={{ textAlign: 'center' }}>©2024</Footer>
    </Layout>
  );
};

export default OrderPage;