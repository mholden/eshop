import axios from 'axios';

const post = async (url, body, params) => {
  try {
    //console.log("post() url:", url, "body:", body, "params:", params);
    const response = await axios.post(url, body, { ...params });
    //console.log("post() response:", response);
    return response;
  } catch (e) {
    if (!e || !e.response || e.response.status !== 401) throw e;
    localStorage.setItem('url', `${window.location.pathname}${window.location.search}`);
    try {
      return axios.post(url, body, { ...params });
    } catch (err) {
      return null;
    }
  }
};

export default post;
