import AuthService from '@/services/AuthService';
import get from './base/get';
import BackEndServiceLocations from './backEndServiceLocations';

const ORDER_API_BASE_URL = BackEndServiceLocations.getLocation("ORDER_SERVICE") + "/order";

const orderServiceAPI = {
  
  getOrders: async () => {
    const orderResponse = await get(ORDER_API_BASE_URL + "/orders", {
      headers: {
        'Authorization': `Bearer ${await AuthService.getAuthToken()}`
      }
    });
    //console.log("getOrders() ", orderResponse.data);
    return orderResponse;
  },

};

export default orderServiceAPI;
