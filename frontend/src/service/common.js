export async function getJson(url) {
  let res = await fetch(url, { method: "GET", credentials: "include" });
  return res.json();
}

export async function get(url) {
  let res = await fetch(url, { method: "GET", credentials: "include" });

  const contentType = res.headers.get('content-type');
  let result;
  if (contentType && contentType.includes('application/json')) {
      result = await res.json();
  } else {
      result = await res.text();
  }

  return { ok: res.ok, message: result };

}

export async function put(url, data) {
  let opts = {
      method: "PUT",
      body: JSON.stringify(data),
      headers: {
          'Content-Type': 'application/json'
      },
      credentials: "include"
  };
  let res = await fetch(url, opts);
  
  if (res.status === 204 || res.headers.get('content-length') === '0') {
      return { ok: res.ok };
  }
  
  const contentType = res.headers.get('content-type');
  let result;
  if (contentType && contentType.includes('application/json')) {
      result = await res.json();
  } else {
      result = await res.text();
  }

  return { ok: res.ok, message: result };
}

export async function del(url, data) {
  let res = await fetch(url, { method: "DELETE", credentials: "include", body: JSON.stringify(data) });

  const contentType = res.headers.get('content-type');
  let result;
  if (contentType && contentType.includes('application/json')) {
      result = await res.json();
  } else {
      result = await res.text();
  }

  return { ok: res.ok, message: result };
}


export async function post(url, data) {
  let opts = {
      method: "POST",
      body: JSON.stringify(data),
      headers: {
          'Content-Type': 'application/json'
      },
      credentials: "include"
  };
  let res = await fetch(url, opts);

  if (res.status === 204 || res.headers.get('content-length') === '0') {
      return { ok: res.ok };
  }
  
  const contentType = res.headers.get('content-type');
  let result;
  if (contentType && contentType.includes('application/json')) {
      result = await res.json();
  } else {
      result = await res.text();
  }

  return { ok: res.ok, message: result };
}

export const BASEURL = 'http://localhost:8080';
export const PREFIX = `${BASEURL}/api`;
export const ADMINPREFIX = `${BASEURL}/admin`;
export const API_DOCS_URL = `${BASEURL}/api-docs`;
export const IMAGE_PREFIX = `${BASEURL}/images`;
export const DUMMY_RESPONSE = {
  ok: false,
  message: "网络错误！"
}