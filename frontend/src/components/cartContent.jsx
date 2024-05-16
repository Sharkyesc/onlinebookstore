/*

import { React, useState, useEffect } from 'react';
import { InputNumber, Checkbox } from 'antd';
import { Input, Table, Space, Button, Modal } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';

const { Search } = Input;
const { confirm } = Modal;


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

const CartContent = () => {
    return (
        <div>
            <h1 style={{marginLeft: 10}}>购物车</h1>
            <Search placeholder="输入书名搜索" style={{ width: 200, marginBottom: 16, marginLeft: 10 }} />
            <Table columns={columns} dataSource={cartData} pagination={{ pageSize: 5 }} />
            {/*<div style={{ position: 'fixed', bottom: 0, right: '5%', width:'100vh' }}>
                <h2>总计：{calculateTotalPrice()} 点击结算</h2>
            </div>*/}
        </div>
    );
}

export default CartContent;

*/