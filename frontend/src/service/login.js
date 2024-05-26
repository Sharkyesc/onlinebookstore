import { PREFIX, post, DUMMY_RESPONSE } from "./common";

export async function login(username, password) {
    const url = `${PREFIX}/login`;
    let result;

    try {
        result = await post(url, { username, password });
    } catch (e) {
        console.log(e);
        result = DUMMY_RESPONSE;
    }
    return result;
}


