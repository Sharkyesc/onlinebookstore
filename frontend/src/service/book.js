import { PREFIX, BASEURL, ADMINPREFIX, getJson, put, del, post, DUMMY_RESPONSE } from "./common";

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
    const url = `${PREFIX}/books/all`;
    let books;
    try {
        books = await getJson(url);
    } catch (e) {
        console.log(e);
        books = [];
    }
    return books;
}

export async function searchBooks(query, page = 0, size = 10) {
    const url = `${PREFIX}/books?search=${encodeURIComponent(query)}&page=${page}&size=${size}`;
    let books;
    try {
        books = await getJson(url);
        return books;
    } catch (error) {
        console.error('获取书籍列表时出错:', error);
        return { content: [], totalElements: 0 };
    }
}

export async function searchBookstoManage(query, page = 0, size = 10) {
    const url = `${ADMINPREFIX}/books?search=${encodeURIComponent(query)}&page=${page}&size=${size}`;
    let books;
    try {
        books = await getJson(url);
        return books;
    } catch (error) {
        console.error('获取书籍列表时出错:', error);
        return { content: [], totalElements: 0 };
    }
}

export async function updateBook(id, data) {
    const url = `${PREFIX}/books/${id}`;
    let response;
    try {
        response = await put(url, data);
    } catch (e) {
        console.log(e);
        response = DUMMY_RESPONSE;
    }
    return response;
}


export async function deleteBook(id) {
    const url = `${PREFIX}/books/${id}`;    
    let response;
    try {
        response = await del(url);
    } catch (e) {
        console.log(e);
        response = DUMMY_RESPONSE;
    }
    return response;
}

export async function addBook(data) {
    const url = `${PREFIX}/books`;
    let response;
    try {
        response = await post(url, data);
    } catch (e) {
        console.log(e);
        response = DUMMY_RESPONSE;
    }
    return response;
}

export async function getBookUrl(id) {
    const url = `${PREFIX}/books/url/${id}`;
    let res;
    try {
        res = await getJson(url, id);
    } catch (e) {
        console.log(e);
        res = DUMMY_RESPONSE;
    }
    return res;
}

export async function getBookAuthorByTitle(name) {
    const url = `${BASEURL}/author/${name}`;
    let res;
    try {
        res = await getJson(url, name);
    } catch (e) {
        console.log(e);
        res = DUMMY_RESPONSE;
    }
    return res;
}