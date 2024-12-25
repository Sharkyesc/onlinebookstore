import React, { useState, useEffect } from 'react';
import { Layout, Input, Card, Typography, Divider, Spin, List, message } from 'antd';
import NavBar from '../components/navBar';
import { useQuery } from '@apollo/client';
import { GET_BOOK_BY_TITLE } from '../graphql/query';

const { Header, Content, Footer } = Layout;
const { Search } = Input;
const { Title } = Typography;

const BookByTitlePage = () => {
    const [title, setTitle] = useState('');
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    // GraphQL query
    const { loading: queryLoading, error: queryError, data } = useQuery(GET_BOOK_BY_TITLE, {
        skip: !title,  // Skip query if no title is provided
        variables: { title },
    });

    // Handle search input change
    const handleSearch = (value) => {
        setTitle(value);
        setBooks([]);  // Clear previous search results
    };

    // Update books when data from the query arrives
    useEffect(() => {
        if (data && data.bookByTitle) {
            setBooks([data.bookByTitle]);
        }
    }, [data]);  // Only run when `data` changes

    // Handle query errors
    useEffect(() => {
        if (queryError) {
            setError('查询失败，请重试');
        }
    }, [queryError]);  // Only run when `queryError` changes

    return (
        <Layout>
            <Header>
                <NavBar />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Title level={2}>书籍查询</Title>
                <Divider />
                <Search
                    placeholder="请输入书名"
                    enterButton="查询"
                    size="large"
                    onSearch={handleSearch}
                    loading={queryLoading}
                />

                {queryLoading ? (
                    <Spin size="large" style={{ marginTop: '20px' }} />
                ) : (
                    <div style={{ marginTop: '20px' }}>
                        {error && <p style={{ color: 'red' }}>{error}</p>}
                        <List
                            header={<div>相关书籍</div>}
                            bordered
                            dataSource={books}
                            renderItem={(item) => (
                                <List.Item>
                                    <Card style={{ width: '100%' }}>
                                        <Title level={4}>ID: {item.id}</Title>
                                        <Title level={4}>{item.title}</Title>
                                        <p>作者：{item.author}</p>
                                    </Card>
                                </List.Item>
                            )}
                        />
                    </div>
                )}
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default BookByTitlePage;
