import React from 'react';
import { Card } from 'antd';
import { useNavigate } from 'react-router-dom';

const { Meta } = Card;

const BookCard = ({ id, coverSrc, title, price }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/book/${id}`);
  };

  return (
    <Card
      hoverable
      style={{ width: 250, marginBottom: 5, marginRight: 2 }}
      cover={<img alt="Book Cover" src={coverSrc} style={{ height: 250 }} />}
      onClick={handleClick}
    >
      <Meta title={title} description={`价格：${(price / 100.00).toFixed(2)} 元`} />
    </Card>
  );
};

export default BookCard;
