import { React, useState } from 'react';
import { InputNumber } from 'antd';
import { Input, Table, Space, Button, Modal, Checkbox } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';
import { changeCartItemNumber, deleteCartItem } from '../service/cart';
import { debounce } from 'lodash';
import { createOrder } from '../service/order';

const { Search } = Input;
const { confirm } = Modal;


const CartContent = ({ cartData, onMutate }) => {
  
  const [selectedItems, setSelectedItems] = useState([]);

  const handleDelete = (id) => {
    deleteCartItem(id)
    .then(response => {
      alert(response.message); 
      onMutate();
    })
    .catch(error => console.error('There was a problem with deleting item from cart:', error));
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
    .then(response => {
      alert('Order created successfully');
      onMutate();
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
        dataIndex: 'coverSrc',
        key: 'coverSrc',
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
            onChange={(value) => handleQuantityChange(record.cartId, value)}
          />
        ),
      },
      {
        title: '金额',
        dataIndex: 'total',
        key: 'total',
        render: (text, record) => record.quantity * record.price, 
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
            <h1 style={{marginLeft: 10}}>购物车</h1>
            <Search placeholder="输入书名搜索" style={{ width: 200, marginBottom: 16, marginLeft: 10 }} />
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
