import BackEndServiceLocations from '../backEndServiceLocations';
import get from './base/get';

const CATALOG_API_BASE_URL = BackEndServiceLocations.getLocation("CATALOG_SERVICE") + "/catalog";
const CONTENT_API_BASE_URL = BackEndServiceLocations.getLocation("CONTENT_SERVICE") + "/content";

const catalogServiceAPI = {
  
  getCatalogItems: async () => {
    const catalogResponse = await get(CATALOG_API_BASE_URL + "/items");

    let urlSuffix = "";
    let imageMap = {};
    for (let i = 0; i < catalogResponse.data.length; i++) {
      catalogResponse.data[i].imageData = "";
      if (catalogResponse.data[i].imageId == null) {
        continue;
      }
      imageMap[catalogResponse.data[i].imageId] = i;
      if (urlSuffix.length === 0) {
        urlSuffix = "?contentId=" + catalogResponse.data[i].imageId;
      } else {
        urlSuffix += "&contentId=" + catalogResponse.data[i].imageId;
      }
    }
    //console.log("getCatalogItems() urlSuffix",urlSuffix);
    const contentResponse = await get(CONTENT_API_BASE_URL + urlSuffix);
    //console.log("getCatalogItems() contentResponse",contentResponse);
    for (let i = 0; i < contentResponse.data.length; i++) {
      catalogResponse.data[imageMap[contentResponse.data[i].id]].imageData = contentResponse.data[i].data;
    }
    //console.log("getCatalogItems() ", catalogResponse.data);
    return catalogResponse;
  },

  getCatalogItem: async (catalogItemId) => {
    const catalogResponse = await get(CATALOG_API_BASE_URL + "/item?catalogItemId=" + catalogItemId);
    catalogResponse.data.imageData = "";
    if (catalogResponse.data.imageId != null) {
      const contentResponse = await get(CONTENT_API_BASE_URL + "?contentId=" + catalogResponse.data.imageId);
      catalogResponse.data.imageData = contentResponse.data[0].data;
    }
    //console.log("getCatalogItem() ", catalogResponse.data);
    return catalogResponse;
  },
};

export default catalogServiceAPI;
