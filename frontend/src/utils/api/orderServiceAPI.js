import get from './base/get';
import { getAuthToken } from '../helpers';
import BackEndServiceLocations from '../backEndServiceLocations';

const ORDER_API_BASE_URL = BackEndServiceLocations.getLocation("ORDER_SERVICE") + "/order";

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
