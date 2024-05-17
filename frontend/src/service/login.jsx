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


/*



export async function register({ username, password, email, code }) {
  const url = `${PREFIX}/register`;
  try {
    let result;
    result = await post(url, { username, password, email });
    alert("注册成功！");
    location.reload();
  } catch (e) {
    console.log(e);
    alert(e);
  }
}

export async function checkAuth() {
  const url = `${PREFIX}/check`;
  let result;
  try {
    result = await get(url);
    return result;
  } catch (e) {
    location.href = "/login";
    alert(e);
  }
}

*/