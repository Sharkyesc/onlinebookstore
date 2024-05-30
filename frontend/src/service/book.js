import { PREFIX, getJson } from "./common";

export async function getBookById(id) {
    const url = `${PREFIX}/books/${id}`;
    let book;
    try {
        book = await getJson(url);
    } catch (e) {
        console.log(e);
        book = null;
    }
    return book;
}

export async function getAllBooks() {
    const url = `${PREFIX}/books`;
    let books;
    try {
        books = await getJson(url);
    } catch (e) {
        console.log(e);
        books = [];
    }
    return books;
}

export async function searchBooks(query) {
    const url = `${PREFIX}/books?search=${encodeURIComponent(query)}`;
    let books;
    try {
        books = await getJson(url);
        return books;
    } catch (error) {
        console.error('获取书籍列表时出错:', error);
        return [];
    }
}