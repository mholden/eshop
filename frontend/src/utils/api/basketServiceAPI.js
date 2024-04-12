import get from './base/get';
import AuthService from '../../services/AuthService';

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

};

export default basketServiceAPI;
