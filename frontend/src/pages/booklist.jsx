import React, { useState, useEffect } from 'react';
import { Layout, Input, Card, Row, Col, List, Avatar, Divider, Typography, Pagination } from 'antd';
import { Link } from 'react-router-dom';
import { searchBooks } from '../service/book';
import NavBar from '../components/navBar';

const { Header, Content, Footer } = Layout;
const { Search } = Input;
const { Title } = Typography;

const BookListPage = () => {
    const [books, setBooks] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const [total, setTotal] = useState(0);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchBooks = async () => {
            const result = await searchBooks(searchTerm, currentPage - 1, pageSize);
            setBooks(result.content);
            setTotal(result.totalElements);
        };
        fetchBooks();
    }, [searchTerm, currentPage, pageSize]);

    const handleSearch = value => {
        setSearchTerm(value);
        setCurrentPage(1); 
    };

    const handlePageChange = (page, size) => {
        setCurrentPage(page);
        setPageSize(size);
    };

    return (
        <Layout>
            <Header>
                <NavBar />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Title level={2}>书库</Title>
                <Divider/>
                <Search
                    placeholder="请输入书名进行搜索"
                    size='large'
                    onSearch={handleSearch}
                />
                        <List
                            itemLayout="horizontal"
                            dataSource={books}
                            renderItem={book => (
                                <List.Item>
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
                        <Pagination
                            current={currentPage}
                            pageSize={pageSize}
                            total={total}
                            onChange={handlePageChange}
                            showSizeChanger
                            showQuickJumper
                            style={{ marginTop: '20px', textAlign: 'center' }}
                        />
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default BookListPage;
