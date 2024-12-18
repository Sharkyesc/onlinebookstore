import React, { useState } from 'react';
import { Layout, Input, Card, Typography, Divider, Spin, List, message } from 'antd';
import NavBar from '../components/navBar';
import { getBooksByTag } from '../service/book'; 

const { Header, Content, Footer } = Layout;
const { Search } = Input;
const { Title } = Typography;

const TagSearchPage = () => {
    const [tagName, setTagName] = useState('');
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSearch = async (value) => {
        setTagName(value);
        setLoading(true);
        setError('');
        try {
            const result = await getBooksByTag(value); 
            if (result && result.length > 0) {
                setBooks(result); 
            } else {
                setBooks([]); 
                message.warning('没有找到相关书籍');
            }
        } catch (error) {
            setError('查询失败，请重试');
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
                <Title level={2}>标签相关书籍查询</Title>
                <Divider />
                <Search
                    placeholder="请输入标签名称"
                    enterButton="查询"
                    size="large"
                    onSearch={handleSearch}
                    loading={loading}
                />
                
                {loading ? (
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
                                        <Title level={4}>{item.title}</Title>
                                        <p>作者：{item.author}</p>
                                        <p>{item.description}</p>
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

export default TagSearchPage;
