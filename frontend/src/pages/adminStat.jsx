import React, { useState } from 'react';
import { Layout, DatePicker, Button, Typography, Row, Col, Divider } from 'antd';
import NavBar from '../components/navBar';
import { getBookSales, getUserPurchases } from '../service/statistics';
import { Column } from '@ant-design/charts';

const { Header, Content, Footer } = Layout;
const { RangePicker } = DatePicker;
const { Title } = Typography;

const AdminStatisticsPage = () => {
    const [bookSales, setBookSales] = useState([]);
    const [userPurchases, setUserPurchases] = useState([]);
    const [dateRange, setDateRange] = useState([null, null]);
    const [currentView, setCurrentView] = useState(null);

    const handleDateChange = (dates) => {
        setDateRange(dates);
    };

    const handleFetchBookSales = async () => {
        const startDate = dateRange ? (dateRange[0] ? dateRange[0].format('YYYY-MM-DDTHH:mm:ss') : null) : null;
        const endDate = dateRange ? (dateRange[1] ? dateRange[1].format('YYYY-MM-DDTHH:mm:ss') : null) : null;
        const sales = await getBookSales(startDate, endDate);
        setBookSales(sales);
        setUserPurchases([]); 
        setCurrentView('bookSales');
    };

    const handleFetchUserPurchases = async () => {
        const startDate = dateRange ? (dateRange[0] ? dateRange[0].format('YYYY-MM-DDTHH:mm:ss') : null) : null;
        const endDate = dateRange ? (dateRange[1] ? dateRange[1].format('YYYY-MM-DDTHH:mm:ss') : null) : null;
        const purchases = await getUserPurchases(startDate, endDate);
        setUserPurchases(purchases);
        setBookSales([]); 
        setCurrentView('userPurchases');
    };

    const bookSalesConfig = {
        data: bookSales,
        xField: 'bookTitle',
        yField: 'totalQuantity',
        label: {
            position: 'top',
            style: {
                fill: '#FFFFFF',
                opacity: 0.6,
            },
        },
        xAxis: {
            label: {
                autoHide: true,
                autoRotate: false,
            },
        },
        meta: {
            bookTitle: { alias: '书名' },
            totalQuantity: { alias: '销量' },
        },
    };

    const userPurchasesConfig = {
        data: userPurchases,
        xField: 'nickname',
        yField: 'totalAmount',
        label: {
            position: 'top',
            style: {
                fill: '#FFFFFF',
                opacity: 0.6,
            },
        },
        xAxis: {
            label: {
                autoHide: true,
                autoRotate: false,
            },
        },
        meta: {
            nickname: { alias: '用户昵称' },
            totalAmount: { alias: '总消费金额' },
        },
    };

    return (
        <Layout>
            <Header>
                <NavBar />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Title level={2}>统计</Title>
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
                        <Button type="primary" onClick={handleFetchBookSales}>
                            获取书籍销量统计
                        </Button>
                        <Button type="primary" onClick={handleFetchUserPurchases} style={{ marginLeft: 10 }}>
                            获取用户消费统计
                        </Button>
                    </Col>
                </Row>
                <Divider />
                {currentView === 'bookSales' && bookSales.length > 0 && (
                    <>
                        <Title level={3}>书籍销量统计</Title>
                        <Column {...bookSalesConfig} />
                    </>
                )}
                {currentView === 'userPurchases' && userPurchases.length > 0 && (
                    <>
                        <Title level={3}>用户消费统计</Title>
                        <Column {...userPurchasesConfig} />
                    </>
                )}
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default AdminStatisticsPage;
