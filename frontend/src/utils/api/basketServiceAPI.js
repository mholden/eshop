import get from './base/get';
import AuthService from '../../services/AuthService';
import post from './base/post';

const BASKET_API_BASE_URL = "http://localhost:8080/basket";

const basketServiceAPI = {
  
  getBasketItems: async () => {
    const basketResponse = await get(BASKET_API_BASE_URL + "/items", {
        headers: {
          'Authorization': `Bearer ${AuthService.getToken()}`
        }
    });
    return basketResponse;
  },

  setBasketItems: async (basketItems) => {
    const basketResponse = await post(BASKET_API_BASE_URL + "/items", basketItems, {
        headers: {
          'Authorization': `Bearer ${AuthService.getToken()}`
        },
    });
    //console.log("setBasketItems() returning:", basketResponse);
    return basketResponse;
  },

  checkout: async () => {
    const basketResponse = await post(BASKET_API_BASE_URL + "/checkout", { }, {
        headers: {
          'Authorization': `Bearer ${AuthService.getToken()}`
        }
    });
    return basketResponse;
  },

};

export default basketServiceAPI;
