import AuthService from '@/services/AuthService';
import get from './base/get';
import post from './base/post';
import BackEndServiceLocations from './backEndServiceLocations';

const BASKET_API_BASE_URL = BackEndServiceLocations.getLocation("BASKET_SERVICE") + "/basket";

const basketServiceAPI = {
  
  getBasketItems: async () => {
    const basketResponse = await get(BASKET_API_BASE_URL + "/items", {
        headers: {
          'Authorization': `Bearer ${await AuthService.getAuthToken()}`
        }
    });
    return basketResponse;
  },

  setBasketItems: async (basketItems) => {
    const basketResponse = await post(BASKET_API_BASE_URL + "/items", basketItems, {
        headers: {
          'Authorization': `Bearer ${await AuthService.getAuthToken()}`
        },
    });
    //console.log("setBasketItems() returning:", basketResponse);
    return basketResponse;
  },

  checkout: async () => {
    const basketResponse = await post(BASKET_API_BASE_URL + "/checkout", { }, {
        headers: {
          'Authorization': `Bearer ${await AuthService.getAuthToken()}`
        }
    });
    return basketResponse;
  },

};

export default basketServiceAPI;
