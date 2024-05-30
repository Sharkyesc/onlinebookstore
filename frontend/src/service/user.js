import { PREFIX, getJson, put } from "./common";

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

export async function getUserCompleteInfo() {
    const url = `${PREFIX}/usercompleteinfo`;
    let userInfo;
    try {
        userInfo = await getJson(url);
    } catch (e) {
        console.log(e);
        userInfo = { username: '', nickname: '', email: '', address: '', phonenumber: '', avatarSrc: '' };
    }
    return userInfo;
}


export async function updateUserInfo(userInfo) {
    const url = `${PREFIX}/updateuserinfo`;
    let response;
    try {
        response = await put(url, userInfo);
        if (response.ok) {
            const data = await response.json();
            return { success: true, data };
        } else {
            return { success: false };
        }
    } catch (error) {
        console.error('更新用户信息时出错:', error);
        return { success: false, error };
    }
};

export async function getUsers() {
    const url = `${PREFIX}/users`;
    const response = await getJson(url);
    return response;
}

export async function disableUser(userId) {
    const url = `${PREFIX}/users/${userId}/disable`;
    const response = await put(url);
    return response;
}

export async function enableUser(userId) {
    const url = `${PREFIX}/users/${userId}/enable`;
    const response = await put(url);
    return response;
}