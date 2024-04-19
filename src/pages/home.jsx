import React from 'react';
import getBookInfoById from '../utils/getBookInfo'; 
import NavBar from "../components/navBar";
import MyCarousel from "../components/carousel";
import BookCard from "../components/bookCard";
import { Layout } from 'antd';
const { Header, Content, Footer } = Layout;

function HomePage() {
    const userInfo = {
        username: '玉皇大帝',
        avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
    };
    
    const carouselImages = [
        'https://m.360buyimg.com/babel/jfs/t20270307/155902/8/41629/50401/65e96e13Ff109a1f0/93089ba3607bae4d.jpg',
        'https://m.360buyimg.com/babel/jfs/t20270318/104149/31/39027/196535/65f7ebb9F84b29e1b/ad1353ba5f80fcea.png',
        'https://m.360buyimg.com/babel/jfs/t20270227/176495/38/42878/102413/65dec930Fae1815a8/addf7b0af75856dc.jpg',
        'https://m.360buyimg.com/babel/jfs/t20270325/107272/6/42724/101609/6600ed0fF752e852d/1eb805f0fb7ed3f3.jpg',
    ];

    
    const books = [
        getBookInfoById(1),
        getBookInfoById(2),
        getBookInfoById(3),
        getBookInfoById(4),
        getBookInfoById(5),
        getBookInfoById(6),
        getBookInfoById(7),
        getBookInfoById(8),
        getBookInfoById(9),
        getBookInfoById(10),
        getBookInfoById(11),
        getBookInfoById(12),
        getBookInfoById(13),
        getBookInfoById(14),
        getBookInfoById(15),
    ];


    return (
        <Layout className="layout">
            <Header>
                <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <div className="site-layout-content">
                    <MyCarousel images={carouselImages} />
                    <h1>热门书籍</h1>
                    <div className="book-list" style={{ display: 'flex', flexWrap: 'wrap' }}>
                        {books.map((book, index) => (
                            <BookCard key={index} {...book} />
                        ))}
                    </div>
                </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};
export default HomePage;



