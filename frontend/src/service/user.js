import { PREFIX, ADMINPREFIX, getJson, put, DUMMY_RESPONSE } from "./common";

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
    } catch (e) {
        console.log(e);
        response = DUMMY_RESPONSE;
    }
    return response;
};

export async function getUsers() {
    const url = `${ADMINPREFIX}/users`;
    let response;
    try {
        response = await getJson(url);
        if (response.status === 403) {
          console.error('访问被拒绝:', response);
          return [];
        }
    } catch (error) {
        console.error('获取用户信息时出错:', error);
        response = [];
    }
    return response;
}

export async function disableUser(userId) {
    const url = `${ADMINPREFIX}/users/${userId}/disable`;
    const response = await put(url);
    return response;
}

export async function enableUser(userId) {
    const url = `${ADMINPREFIX}/users/${userId}/enable`;
    const response = await put(url);
    return response;
}