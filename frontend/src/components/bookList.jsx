import React from 'react';
import { List, Button, Avatar } from 'antd';
import { Link } from 'react-router-dom';

const BookList = ({ books, onEdit, onDelete }) => (
    <List
        itemLayout="horizontal"
        dataSource={books}
        renderItem={book => (
            <List.Item
                actions={[
                    <Button type="link" onClick={() => onEdit(book)}>编辑</Button>,
                    <Button type="link" danger onClick={() => onDelete(book.id)}>删除</Button>
                ]}
            >
                <List.Item.Meta
                    avatar={
                        <Link to={`/book/${book.id}`}>
                            <Avatar src={book.coverSrc} shape="square" size={64} />
                        </Link>
                    }
                    title={
                        <Link to={`/book/${book.id}`}>
                            {book.title}
                        </Link>
                    }
                    description={`作者: ${book.author} | ISBN: ${book.isbn} | 库存量: ${book.stocks}`}
                />
            </List.Item>
        )}
    />
);

export default BookList;
