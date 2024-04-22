import get from './base/get';
import { getAuthToken } from '../helpers';

const ORDER_API_BASE_URL = "http://localhost:8080/order";

const orderServiceAPI = {
  
  getOrders: async () => {
    const orderResponse = await get(ORDER_API_BASE_URL + "/orders", {
        headers: {
          'Authorization': `Bearer ${getAuthToken()}`
        }
    });
    return orderResponse;
  },

};

export default orderServiceAPI;
