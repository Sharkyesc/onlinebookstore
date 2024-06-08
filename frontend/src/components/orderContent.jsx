import React, { useState } from 'react';
import { Table, Button, Modal } from 'antd';
import { Link } from 'react-router-dom';
import moment from 'moment'


const OrderContent = ({ orderInfo }) => {

    const [modalVisible, setModalVisible] = useState(false);
    const [selectedOrder, setSelectedOrder] = useState(null);

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
          render: text => moment(text).format('YYYY-MM-DD HH:mm:ss'),
        },
        {
          title: '收货人',
          dataIndex: 'recipient',
          key: 'recipient',
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
          render: (text, record) => {
            const priceInYuan = record.totalPrice / 100;
            return `${priceInYuan.toFixed(2)} 元`;
          },
        },
        {
          title: '详情',
          key: 'action',
          render: (text, record) => (
            <Button type="link" onClick={() => handleViewDetails(record)}>查看详情</Button>
          ),
        },
      ];
  
  return (
    <div className="site-layout-content">
      <Table columns={columns} dataSource={orderInfo} />
      <div style={{ marginTop: 20 }}>
        <Link to="/home">
          <Button type="primary">继续购物</Button>
        </Link>
      </div>
      <Modal
        title="订单详情"
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        footer={null}
      >
        {selectedOrder && (
          <>
            <p>订单编号：{selectedOrder.orderId}</p>
            <p>订单时间：{moment(selectedOrder.orderTime).format('YYYY-MM-DD HH:mm:ss')}</p>
            <p>收货人：{selectedOrder.recipient}</p>
            <p>收货地址：{selectedOrder.destination}</p>
            <p>总价：{`${(selectedOrder.totalPrice / 100.00).toFixed(2)} 元`}</p>
            {selectedOrder.orderItems.map((item, index) => (
                <div key={index}>
                    <h4>订单项编号: {item.itemId}</h4>
                    <p>书籍: 《{item.bookName}》</p>
                    <p>数量: {item.quantity}</p>
                    <p>单项总价: {`${(item.price / 100.00).toFixed(2)} 元`}</p>
                </div>
            ))}

          </>
        )}
      </Modal>
    </div>
  );

};

export default OrderContent;


