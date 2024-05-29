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

export async function getOrders() {
    const url = `${PREFIX}/orders`;
    let orders;
    try {
        orders = await getJson(url);
    } catch (e) {
        console.log(e);
        orders = []
    }
    return orders;
}