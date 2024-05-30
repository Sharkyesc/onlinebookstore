import React, { useState } from 'react';
import { Layout, DatePicker, Button, Table, Typography, Row, Col, Divider } from 'antd';
import NavBar from '../components/navBar';
import { getBookStatistics } from '../service/statistics';
import moment from 'moment';

const { Header, Content, Footer } = Layout;
const { RangePicker } = DatePicker;
const { Title } = Typography;

const StatisticsPage = () => {
    const [statistics, setStatistics] = useState([]);
    const [dateRange, setDateRange] = useState([null, null]);

    const handleDateChange = (dates) => {
        setDateRange(dates);
    };

    const handleFetchStatistics = async () => {
        const startDate = dateRange ? (dateRange[0] ? dateRange[0].format('YYYY-MM-DDTHH:mm:ss') : null) : null;
        const endDate = dateRange ? (dateRange[1] ? dateRange[1].format('YYYY-MM-DDTHH:mm:ss') : null) : null;
        const stats = await getBookStatistics(startDate, endDate);
        setStatistics(stats || []); 
    };

    const columns = [
        {
            title: '书名',
            dataIndex: 'bookTitle',
            key: 'bookTitle',
        },
        {
            title: '购买数量',
            dataIndex: 'totalQuantity',
            key: 'totalQuantity',
        },
        {
            title: '总价',
            dataIndex: 'totalPrice',
            key: 'totalPrice',
        },
    ];

    const totalBooks = statistics.reduce((acc, stat) => acc + stat.totalQuantity, 0);
    const totalPrice = statistics.reduce((acc, stat) => acc + stat.totalPrice, 0);

    return (
        <Layout>
            <Header>
                <NavBar />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Title level={2}>购书统计</Title>
                <Divider />
                <Row gutter={16} style={{ marginBottom: 20 }}>
                    <Col span={16}>
                        <RangePicker
                            style={{ width: '100%' }}
                            onChange={handleDateChange}
                            allowClear
                        />
                    </Col>
                    <Col span={8}>
                        <Button type="primary" onClick={handleFetchStatistics}>
                            统计
                        </Button>
                    </Col>
                </Row>
                <Table columns={columns} dataSource={statistics} rowKey="bookTitle" />
                <div style={{ marginTop: 10 }}>
                    <p>总购书数量: {totalBooks}本</p>
                    <p>总购书金额: {totalPrice}元</p>
                </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default StatisticsPage;
