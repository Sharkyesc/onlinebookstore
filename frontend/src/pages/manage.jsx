import React, { useEffect, useState } from 'react';
import { Layout, Table, Button, message, Divider, Typography, Modal } from 'antd';
import NavBar from '../components/navBar';
import { getUsers, disableUser, enableUser } from '../service/user';

const { Header, Content, Footer } = Layout;
const { Title } = Typography;
const { confirm } = Modal;

const UserManagementPage = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const result = await getUsers();
      setUsers(result);
    } catch (error) {
      console.error('Failed to fetch users:', error);
    }
  };

  const handleDisable = async (userId) => {
    console.log(userId);
    const result = await disableUser(userId);
    if (result.ok) {
      message.success('用户已禁用');
      fetchUsers();
    } else {
      message.error('禁用用户失败');
    }
  };

  const handleEnable = async (userId) => {
    const result = await enableUser(userId);
    if (result.ok) {
      message.success('用户已解禁');
      fetchUsers();
    } else {
      message.error('解禁用户失败');
    }
  };

  const showDisableConfirm = (userId) => {
    confirm({
      title: '确认禁用这个用户吗？',
      content: '禁用后该用户将无法登录系统。',
      okText: '确认',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        handleDisable(userId);
      },
      onCancel() {
        console.log('取消');
      },
    });
  };

  const columns = [
    { title: '用户编号', dataIndex: 'user_id', key: 'id' },
    { title: '用户名', dataIndex: ['userAuth', 'username'], key: 'username' },
    { title: '昵称', dataIndex: 'nickname', key: 'nickname' },
    { title: '邮箱', dataIndex: 'email', key: 'email' },
    {
      title: '状态',
      dataIndex: 'enabled',
      key: 'enabled',
      render: (text, record) => 
        record.userAuth.username === 'admin' ? '管理员' : (record.enabled ? '启用' : '禁用'),
    },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <>
          {record.userAuth.username !== 'admin' && (
            <>
              {record.enabled ? (
                <Button danger onClick={() => showDisableConfirm(record.user_id)}>禁用</Button>
              ) : (
                <Button type="primary" onClick={() => handleEnable(record.user_id)}>解禁</Button>
              )}
            </>
          )}
        </>
      ),
    },
  ];

  return (
    <Layout>
      <Header>
        <NavBar />
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <Title level={2}>用户管理</Title>
        <Divider />
        <Table columns={columns} dataSource={users} rowKey="id" />
      </Content>
      <Footer style={{ textAlign: 'center' }}>©2024</Footer>
    </Layout>
  );
};

export default UserManagementPage;
