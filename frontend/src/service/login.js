import { PREFIX, post, DUMMY_RESPONSE } from "./common";

export async function login(username, password) {
    const url = `${PREFIX}/login`;
    let response;

    try {
        response = await post(url, { username, password });
    } catch (e) {
        console.log(e);
        response = DUMMY_RESPONSE;
    }
    return response;
}


