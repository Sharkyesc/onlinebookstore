import { DUMMY_RESPONSE, PREFIX, getJson, post } from "./common";

export async function addOrder(bookId) {
    const url = `${PREFIX}/orders/checkout`;
    let res;
    try {
        res = post(url, bookId);
    } catch (e) {
        console.log(e);
        res = DUMMY_RESPONSE;
    }
    return res;
}

export async function createOrder(cartItems) {
    const url = `${PREFIX}/orders/checkoutfromcart`;
    let res;
    try {
        res = post(url, cartItems);
    } catch (e) {
        console.log(e);
        res = DUMMY_RESPONSE;
    }
    return res;
}

export async function getAllOrders() {
    const url = `${PREFIX}/orders/all`;
    let orders;
    try {
        orders = await getJson(url);
    } catch (e) {
        console.log(e);
        orders = []
    }
    return orders;
}


export async function getOrders(bookName, startDate, endDate) {
    const url = new URL(`${PREFIX}/orders`);
    if (bookName) url.searchParams.append('bookName', bookName);
    if (startDate) url.searchParams.append('startDate', startDate);
    if (endDate) url.searchParams.append('endDate', endDate);
    let orders;

    try {
        orders = await getJson(url);
        return orders;
    } catch (error) {
        console.error('获取订单时出错:', error);
        return [];
    }
}