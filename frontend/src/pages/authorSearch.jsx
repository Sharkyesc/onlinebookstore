import React, { useState } from 'react';
import { Layout, Input, Card, Typography, Divider } from 'antd';
import NavBar from '../components/navBar';
import { getBookAuthorByTitle } from '../service/book';

const { Header, Content, Footer } = Layout;
const { Search } = Input;
const { Title } = Typography;

const BookAuthorSearchPage = () => {
    const [author, setAuthor] = useState('');
    const [bookName, setBookName] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSearch = async (value) => {
        setBookName(value);
        setLoading(true);
        try {
            const result = await getBookAuthorByTitle(value);
            setAuthor(result.message);
        } catch (error) {
            setAuthor('未找到相关作者');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Layout>
            <Header>
                <NavBar />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Title level={2}>作者查询</Title>
                <Divider />
                <Search
                    placeholder="请输入书名"
                    enterButton="查询"
                    size="large"
                    onSearch={handleSearch}
                    loading={loading}
                />
                {bookName && (
                    <Card style={{ marginTop: '20px', textAlign: 'center' }}>
                        <Title level={4}>书名：{bookName}</Title>
                        <p>{author}</p>
                    </Card>
                )}
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default BookAuthorSearchPage;
