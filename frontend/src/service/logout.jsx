import { PREFIX, get } from "./common";

export async function logout() {
    const url = `${PREFIX}/logout`;
    let result;
    result = await get(url);
    return result;
}