import { React, useState, useEffect } from 'react';
import NavBar from '../components/navBar'; 
import { Layout } from 'antd';
import { getCartItems } from '../service/cart';
import CartContent from '../components/cartContent';

const { Header, Content, Footer } = Layout;

const CartPage = () => {
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
                <NavBar />
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