import { PREFIX, post } from './common';

export async function register(username, nickname, email, password) {
    const url = `${PREFIX}/register`;
    let response;

    try {
        response = await post(url, { username, nickname, email, password });
        console.log(response);
        return response;
    } catch (e) {
        console.log(e);
        return { ok: false, message: '注册失败，请稍后再试' };
    }
}
