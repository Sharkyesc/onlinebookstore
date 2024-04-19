import React, {useState} from 'react';
import NavBar from '../components/navBar'; 
import { Layout, InputNumber, Checkbox } from 'antd';
import { Input, Table, Space, Button, Modal } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';
import getCartInfo from '../utils/getCartInfo';

const { Header, Content, Footer } = Layout;
const { Search } = Input;
const { confirm } = Modal;

const CartPage = () => {
    const userInfo = {
        username: '玉皇大帝',
        avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
    };

    const [cartData, setCartData] = useState(() => {
      const initialData = getCartInfo(); 
      return initialData.map(item => ({
        ...item,
        total: `￥${(parseFloat(item.price.replace('￥', '')) * item.quantity).toFixed(2)}`,
        selected: false,
      }));
    });
  
    const handleQuantityChange = (productId, quantity) => {
      const updatedCartData = cartData.map(item => {
          if (item.id === productId) {
              item.quantity = quantity;
              item.total = `￥${(parseFloat(item.price.replace('￥', '')) * quantity).toFixed(2)}`;
            
          }
          return item;
      });
      setCartData(updatedCartData);
    };

    const handleCheckboxChange = (productId, checked) => {
      const updatedCartData = cartData.map(item => {
        if (item.id === productId) {
          item.selected = checked;
        }
        return item;
      });
      setCartData(updatedCartData);
    };
    
    const handleDelete = (productId) => {
      const updatedCartData = cartData.filter(item => item.id !== productId);
      setCartData(updatedCartData);
    };
    
    const showDeleteConfirm = (productId) => {
      confirm({
        title: '确定从购物车删除这本书吗?',
        content: '删除后将无法恢复',
        okText: '是',
        okType: 'danger',
        cancelText: '否',
        onOk() {
          console.log('OK');
          handleDelete(productId);
        },
        onCancel() {
          console.log('Cancel');
        },
      });
    }


    const calculateTotalPrice = () => {
      let totalPrice = 0;
      cartData.forEach(item => {
        if (item.selected) {
          totalPrice += parseFloat(item.price.replace('￥', '')) * item.quantity;
        }
      });
      return `￥${totalPrice.toFixed(2)}`;
    };
    
    const columns = [
    {
      dataIndex: 'selected',
      key: 'selected',
      render: (selected, record) => (
        <Checkbox checked={selected} onChange={(e) => handleCheckboxChange(record.id, e.target.checked)} />
      ),
    },
    {
      title: '封面',
      dataIndex: 'coverSrc',
      key: 'cover',
      render: (coverSrc) => <img src={coverSrc} alt="Cover" style={{ width: 80 }} />,
    },
    {
      title: '标题',
      dataIndex: 'title',
      key: 'title',
    },
    {
      title: '单价',
      dataIndex: 'price',
      key: 'price',
    },
    {
      title: '数量',
      dataIndex: 'quantity',
      key: 'quantity',
      render: (text, record) => (
        <InputNumber
            min={1}
            max={record.stocks}
            defaultValue={text}
            onChange={(value) => handleQuantityChange(record.id, value)}
        />
      ),
    },
    {
      title: '金额',
      dataIndex: 'total',
      key: 'total',
    },
    {
      title: '操作',
      key: 'action',
      render: (record) => (
      <Space size="middle">
        <Button icon={<DeleteOutlined /> } type="link" onClick={() => showDeleteConfirm(record.id)}  danger>
          删除
        </Button>
      </Space>
      ),
    },
  ];
  
    return (
        <Layout className="layout">
            <Header>
                <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
            </Header>
            <Content className="shoppingcart-container" style={{width: '90%', marginLeft: '5%'}} >
                <div>
                    <h1 style={{marginLeft: 10}}>购物车</h1>
                    <Search placeholder="输入书名搜索" style={{ width: 200, marginBottom: 16, marginLeft: 10 }} />
                    <Table columns={columns} dataSource={cartData} pagination={{ pageSize: 5 }} />
                    {/*<div style={{ position: 'fixed', bottom: 0, right: '5%', width:'100vh' }}>
                        <h2>总计：{calculateTotalPrice()} 点击结算</h2>
                    </div>*/}
                </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default CartPage;