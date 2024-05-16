import React, { useEffect, useState } from 'react';
import { Layout, Table, Button, Typography, Divider, Modal } from 'antd';
import NavBar from '../components/navBar';
import { Link } from 'react-router-dom';
import { getAllOrders } from '../service/order';

const { Header, Content, Footer } = Layout;
const { Title } = Typography;

const userInfo = {
  username: '玉皇大帝',
  avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
};

const OrderPage = () => {
  const [orders, setOrders] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState(null);

  const getOrder = async () => {
    let orders = await getAllOrders();
    setOrders(orders);
  }

  useEffect(() => {
    getOrder();
  }, []);

  const handleViewDetails = (order) => {
    setSelectedOrder(order);
    setModalVisible(true);
  };

  const columns = [
    {
      title: '订单编号',
      dataIndex: 'orderId',
      key: 'orderId',
    },
    {
      title: '订单时间',
      dataIndex: 'orderTime',
      key: 'orderTime',
    },
    {
      title: '收货地址',
      dataIndex: 'destination',
      key: 'destination',
    },
    {
      title: '总价',
      dataIndex: 'totalPrice',
      key: 'totalPrice',
    },
    {
      title: '详情',
      key: 'action',
      render: (text, record) => (
        <Button type="link" onClick={() => handleViewDetails(record)}>查看详情</Button>
      ),
    },
  ];


  return (orders && 
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
            <Link to="/home">
              <Button type="primary">继续购物</Button>
            </Link>
          </div>
          <Modal
            title="订单详情"
            visible={modalVisible}
            onCancel={() => setModalVisible(false)}
            footer={null}
          >
            {/* 在这里渲染订单详情，使用 selectedOrder */}
            {selectedOrder && (
              <>
                <p>订单编号：{selectedOrder.orderNumber}</p>
                <p>订单时间：{selectedOrder.orderTime}</p>
                <p>收货地址：{selectedOrder.shippingAddress}</p>
                <p>总价：{selectedOrder.totalPrice}</p>
                {/* 在这里渲染订单中的书籍详情 */}
                {/* 这里根据 selectedOrder 中的书籍信息渲染 */}
                {/* 例如：selectedOrder.books.map(book => (<p>{book.name} - {book.quantity} - {book.price}</p>)) */}
              </>
            )}
          </Modal>
        </div>
      </Content>
      <Footer style={{ textAlign: 'center' }}>©2024</Footer>
    </Layout>
  );
};

export default OrderPage;