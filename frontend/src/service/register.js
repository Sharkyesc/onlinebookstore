import { PREFIX, post } from './common';

export async function register(username, nickname, email, password) {
    const url = `${PREFIX}/register`;
    let response;
    let message;

    try {
        response = await post(url, { username, nickname, email, password });
        const text = await response.text();  
        if (response.ok) {
            message = text;
            return { success: true, message };
        } else {
            message = text;
            return { success: false, message };
        }
    } catch (e) {
        console.log(e);
        return { success: false, message: '注册失败，请稍后再试' };
    }
}
