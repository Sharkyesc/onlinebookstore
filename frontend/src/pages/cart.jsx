import { React, useState, useEffect } from 'react';
import NavBar from '../components/navBar'; 
import { Layout } from 'antd';
import { getCartItems } from '../service/cart';
import CartContent from '../components/cartContent';

const { Header, Content, Footer } = Layout;

const CartPage = () => {
    const userInfo = {
        username: '玉皇大帝',
        avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
    };

    const [cartData, setCartData] = useState([]);

    const getCartData = async () => {
        let cartData = await getCartItems();
        setCartData(cartData);
    }

    useEffect(() => {
        getCartData();
    }, []);

  
    return ( cartData && 
        <Layout className="layout">
            <Header>
                <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
            </Header>
            <Content className="shoppingcart-container" style={{width: '90%', marginLeft: '5%'}} >
                <CartContent 
                  cartData={ cartData } onMutate={getCartData}
                />
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default CartPage;