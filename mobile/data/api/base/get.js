import axios from 'axios';

const get = async (url, params) => {
  try {
    //console.log("get() url:", url, "params:", params);
    const response = await axios.get(url, { ...params });
    //console.log("get() response:", response.data);
    return response;
  } catch (e) {
    console.log("get() exception:", e.toJSON());
    if (!e || !e.response || e.response.status !== 401) throw e;
    localStorage.setItem('url', `${window.location.pathname}${window.location.search}`);
    try {
      return axios.get(url);
    } catch (err) {
      return null;
    }
  }
};

export default get;