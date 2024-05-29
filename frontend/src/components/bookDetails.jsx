import React from 'react';
import WindowWidth from '../utils/getWidth';  
import { Card, Button, Table } from 'antd';
import '../css/bookDetails.css';
import { addCartItem } from '../service/cart';
import { addOrder } from '../service/order';

const BookDetail = ({ bookInfo }) => {

  
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
    addOrder(bookInfo.id)
      .then(response => {
        alert(response.message);
      })
      .catch(error => console.error('There was a problem with creating order:', error));
  }

  const handleAddtoCart = () => {
    addCartItem(bookInfo.id)
      .then(response => {
        alert(response.message); 
      })
      .catch(error => console.error('There was a problem with adding item to cart:', error));
  }
  
  return (
    <div className="book-card">
      <Card hoverable style={{ width: WindowWidth() * 0.95 }}>
        <Button type='primary' onClick={() => window.history.back()} size='large' className="back-button"
          style={{backgroundColor: '#000000', color: 'white', marginBottom: 15, marginLeft: '3%'}}>返回</Button>
        <div className="book-content">
          <div className="book-image">
            <img alt="Book Cover" src={bookInfo.coverSrc} style={{ width: '100%' }} />
          </div>
          <div className="book-info">
            <Table columns={columns} dataSource={data} pagination={false} showHeader={false}/>
          </div>
        </div>
        <div className="book-description">
          <h3>内容简介：</h3>
          <p style={{ whiteSpace: 'pre-line' }}>{bookInfo.description}</p>          
        </div>
        <div className="button-container">
            <Button type="primary" onClick={handleAddtoCart} style={{ marginRight: 10, backgroundColor: '#000000', color: 'white' }}>加入购物车</Button>
            <Button type="primary" onClick={handleBuy} style={{ backgroundColor: '#000000', color: 'white' }}>立即购买</Button>
        </div>
      </Card>
    </div>
  );

};

export default BookDetail;


