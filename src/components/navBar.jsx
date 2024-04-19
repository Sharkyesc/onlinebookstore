import React from 'react';
import { Menu, Dropdown, Avatar, Input } from 'antd';
import { Link } from 'react-router-dom';
import { 
    HomeOutlined, 
    BookOutlined, 
    ShoppingCartOutlined, 
    OrderedListOutlined,
    DownOutlined,
    SearchOutlined
} from '@ant-design/icons';

const NavBar = ({ username, avatarSrc }) => {
  const menu = (
    <Menu>
      <Menu.Item key="profile">
        <Link to="/user">个人中心</Link>
      </Menu.Item>
      <Menu.Item key="passwd">修改密码</Menu.Item>
      <Menu.Item key="logout">退出登录</Menu.Item>
    </Menu>
  );

  return (
    <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
      <Menu.Item className="homeItem" key="1" icon={<HomeOutlined />} style={{backgroundColor: 'transparent'}}>
        <Link to="/">首页</Link>
      </Menu.Item>
      <Menu.Item className="homeItem" key="2" icon={<BookOutlined />} style={{backgroundColor: 'transparent'}}>
        书籍
      </Menu.Item>
      <Menu.Item className="homeItem" key="3" icon={<ShoppingCartOutlined />} style={{backgroundColor: 'transparent'}}>
        <Link to="/cart">购物车</Link>
      </Menu.Item>
      <Menu.Item className="homeItem" key="4" icon={<OrderedListOutlined />} style={{backgroundColor: 'transparent'}}>
        我的订单
      </Menu.Item> 
      <Menu.Item key="search" style={{ flexGrow: 1, backgroundColor:'transparent'}}>
        <Input
          placeholder="搜索"
          prefix={<SearchOutlined />}
          style={{ width: 700 }}
        />
      </Menu.Item>
      <Menu.Item key="profile" style={{ marginRight: 20, backgroundColor:'transparent' }}>
        <Dropdown overlay={menu} trigger={['click']}>
          <span className="ant-dropdown-link" onClick={(e) => e.preventDefault()}>
            {username}
            <Avatar src={avatarSrc} style={{ marginLeft: 6, marginRight: 4 }} />
            <DownOutlined />
          </span>
        </Dropdown>
      </Menu.Item>
    </Menu>
  );
};

export default NavBar;



