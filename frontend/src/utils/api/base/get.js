import axios from 'axios';

const get = async (url) => {
  try {
    //console.log("get() url:", url);
    const response = await axios.get(url);
    //console.log("get() response:", response.data);
    return response;
  } catch (e) {
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