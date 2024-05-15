import { React, useState, useEffect } from 'react';
import BookDetail from '../components/bookDetails'; 
import NavBar from '../components/navBar'; 
import { getBookById } from '../service/book';
import { useParams } from 'react-router-dom';
import { Layout } from 'antd';

const { Header, Content, Footer } = Layout;

const BookPage = () => {
    const userInfo = {
        username: '玉皇大帝',
        avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
    };

    const [book, setBook] = useState(null);
    
    const { id } = useParams();

    const getBook = async () => {
        let book = await getBookById(id);
        setBook(book);
    }

    useEffect(() => {
        getBook();
    }, [id]);

    return (book &&
        <Layout>
            <Header>
                <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
            </Header>
            <Content className="book-detail-container">
                <BookDetail
                    bookInfo={book}
                />
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default BookPage;