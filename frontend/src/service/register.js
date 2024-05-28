import { PREFIX, post } from './common';

export async function register(username, nickname, password) {
    const url = `${PREFIX}/register`;
    let response;

    try {
        response = await post(url, { username, nickname, password });
    } catch (e) {
        console.log(e);
        response = { success: false };  
    }
    return response;
}
