import { PREFIX, post } from './common';

export async function register(username, password) {
    const url = `${PREFIX}/register`;
    let result;

    try {
        result = await post(url, { username, password });
    } catch (e) {
        console.log(e);
        result = { success: false };  
    }
    return result;
}
