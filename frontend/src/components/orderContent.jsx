import React from 'react';
import WindowWidth from '../utils/getWidth';  
import { Card, Button, Table } from 'antd';
import { addCartItem } from '../service/cart';

const OrderContent = ({ bookInfo }) => {

  
  const columns = [
    {
      dataIndex: 'attribute',
      key: 'attribute',
    },
    {
      dataIndex: 'content',
      key: 'content',
    },
  ];

  const data = [
    {
      key: '1',
      attribute: '书名',
      content: bookInfo.title,
    },
    {
      key: '2',
      attribute: '作者',
      content: bookInfo.author,
    },
    {
      key: '3',
      attribute: '出版社',
      content: bookInfo.press,
    },
    {
      key: '4',
      attribute: '出版时间',
      content: bookInfo.pubTime,
    },
    {
      key: '5',
      attribute: '价格',
      content: `${bookInfo.price} 元`,
    },
    {
      key: '6',
      attribute: '库存',
      content: `${bookInfo.stocks} 件`,
    },
  ];

  const handleBuy = () => {
    const orderData = { 
        orderNumber: 5, orderTime: "2024-05-04", bookName: bookInfo.title, quantity: 1, shippingAddress: "China", totalPrice: bookInfo.price 
    };

    fetch('http://localhost:8080/api/orders/checkout', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(orderData)
    })
    .then(response => response.json())
    .then(data => {
      alert(data.message); 
    })
    .catch(error => console.error('There was a problem with your fetch operation:', error));
  }

  const handleAddtoCart = () => {
    addCartItem(bookInfo.id)
      .then(response => {
        alert(response.message); 
      })
      .catch(error => console.error('There was a problem with adding item to cart:', error));
  }
  
  return (
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
  );

};

export default OrderContent;


