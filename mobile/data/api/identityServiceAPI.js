import AuthService from '@/services/AuthService';
import BackEndServiceLocations from './backEndServiceLocations';
import get from './base/get';

// note: this must match what is in your back end application.json file jwt_issuer
export const IDENTITY_API_BASE_URL = BackEndServiceLocations.getLocation("IDENTITY_SERVICE") + "/auth/realms/spring-cloud-gateway-realm";

const identityServiceAPI = {
  
  getUserInfo: async () => {
    const identityResponse = await get(IDENTITY_API_BASE_URL + "/protocol/openid-connect/userinfo", {
        headers: {
          'Authorization': `Bearer ${await AuthService.getAuthToken()}`
        }
    });
    //console.log("getUserInfo() response",identityResponse.data);
    return identityResponse;
  },

};

export default identityServiceAPI;
