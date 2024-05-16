import { DUMMY_RESPONSE, PREFIX, getJson, post } from "./common";

export async function addOrder(orderInfo) {
    const url = `${PREFIX}/orders/checkout`;
    let res;
    try {
        res = post(url, orderInfo);
    } catch (e) {
        console.log(e);
        res = DUMMY_RESPONSE;
    }
    return res;
}

export async function getAllOrders() {
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