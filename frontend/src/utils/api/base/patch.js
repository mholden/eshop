import axios from 'axios';

export default async (url, body, params) => {
  try {
    return await axios.patch(url, body, {  ...params });
  } catch (e) {
    if (!e || !e.response || e.response.status !== 401) throw e;
    localStorage.setItem('url', `${window.location.pathname}${window.location.search}`);
    try {
      return axios.patch(url, body, { ...params });
    } catch (err) {
      return null;
    }
  }
};
