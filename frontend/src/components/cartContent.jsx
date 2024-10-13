import { React, useState, useEffect } from 'react';
import { InputNumber, message } from 'antd';
import { Table, Space, Button, Modal, Checkbox } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';
import { changeCartItemNumber, deleteCartItem } from '../service/cart';
import { debounce } from 'lodash';
import { createOrder } from '../service/order';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
/* 
const { Search } = Input; */
const { confirm } = Modal;


const CartContent = ({ cartData, onMutate }) => {
  
  const [selectedItems, setSelectedItems] = useState([]);
  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = Stomp.over(socket);
    const username = sessionStorage.getItem('username');
    console.log("username:" + username);

    stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);

      stompClient.subscribe('/topic/order-status/' + username, function (message) {
        const orderStatus = message.body;
        alert('订单状态更新: ' + orderStatus);
      });
    });

    setStompClient(stompClient);

    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []);

  const handleDelete = async (id) => {
    try {
      const response = await deleteCartItem(id);
      if (response.ok) {
        message.success(response.message); 
        onMutate();
      } else {
        message.error(response.message);
      } 
    } catch(error) {
      console.error('There was a problem with deleting item from cart');
    }
  };

 
  
  const handleQuantityChange = debounce((id, value) => {
    changeCartItemNumber(id, value)
    .then(response => {
      alert(response.message); 
      onMutate();
    })
    .catch(error => console.error('There was a problem with changing number of item:', error));
  
  }, 300);

  const showDeleteConfirm = (Id) => {
    confirm({
      title: '确定从购物车删除这本书吗?',
      content: '删除后将无法恢复',
      okText: '是',
      okType: 'danger',
      cancelText: '否',
      onOk() {
        console.log('OK');
        handleDelete(Id);
      },
      onCancel() {
        console.log('Cancel');
      },
    });
  }

  const handleOrder = () => {
    const selectedCartItems = cartData.filter(item => selectedItems.includes(item.cartId));
    console.log(selectedCartItems);
    createOrder(selectedCartItems)
    .then(() => {
      alert("正在创建订单，请耐心等候");
    })
    .catch(error => console.error('There was a problem creating the order:', error));
  }
  
  const columns = [
      {
        title: '选择',
        dataIndex: 'cartId',
        key: 'select',
        render: (text, record) => (
          <Checkbox
            checked={selectedItems.includes(record.cartId)}
            onChange={e => {
              const { checked } = e.target;
              setSelectedItems(current => 
                checked ? [...current, record.cartId] : current.filter(id => id !== record.cartId)
              );
            }}
            
          />
        ),
      },
      {
        title: '封面',
        dataIndex: 'book.coverSrc',
        key: 'coverSrc',
        render: (text, record) => <img src={record.book.coverSrc} alt="Cover" style={{ width: 80 }} />,
      },
      {
        title: '标题',
        dataIndex: 'book.title',
        key: 'title',
        render: (text, record) => record.book.title,
      },
      {
        title: '单价',
        dataIndex: 'book.price',
        key: 'price',
        render: (text, record) => `${(record.book.price / 100.00).toFixed(2)} 元`,
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
            onChange={(value) => handleQuantityChange(record.cartId, value)}
          />
        ),
      },
      {
        title: '金额',
        dataIndex: 'total',
        key: 'total',
        render: (text, record) => `${(record.quantity * record.book.price / 100.00).toFixed(2)} 元`, 
      },
      {
        title: '操作',
        key: 'action',
        render: (record) => (
        <Space size="middle">
          <Button icon={<DeleteOutlined /> } type="link" onClick={() => showDeleteConfirm(record.cartId)}  danger>
            删除
          </Button>
        </Space>
        ),
      },
    ];

    return (
        <div>
            {/* 
            <Search placeholder="输入书名搜索" style={{ width: 200, marginBottom: 16, marginLeft: 10 }} /> */}
            <Table columns={columns} dataSource={cartData} pagination={{ pageSize: 10 }} />
            
            <Button type="primary" disabled={!selectedItems.length} style={{ marginTop: 16 }} onClick={handleOrder} >
              下单选中项
            </Button>
            {/*<div style={{ position: 'fixed', bottom: 0, right: '5%', width:'100vh' }}>
                <h2>总计：{calculateTotalPrice()} 点击结算</h2>
            </div>*/}

        </div>
    );
}

export default CartContent;
