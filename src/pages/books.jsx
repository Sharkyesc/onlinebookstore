import React from 'react';
import BookDetail from '../components/bookDetails'; 
import NavBar from '../components/navBar'; 
import { useParams } from 'react-router-dom';
import { Layout } from 'antd';

const { Header, Content, Footer } = Layout;

const BookPage = () => {
    const userInfo = {
        username: '玉皇大帝',
        avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
    };
    
    const { id } = useParams();

    return (
        <Layout className="layout">
            <Header>
                <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
            </Header>
            <Content className="book-detail-container">
                <BookDetail
                    id={id}
                />
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default BookPage;