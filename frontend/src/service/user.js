import { PREFIX, getJson } from "./common";

export async function getUserInfo() {
    const url = `${PREFIX}/userinfo`;
    let userInfo;
    try {
        userInfo = await getJson(url);
    } catch (e) {
        console.log(e);
        userInfo = { avatarSrc: '', nickname: '' };
    }
    return userInfo;
}
