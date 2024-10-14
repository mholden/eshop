import get from './base/get';
import BackEndServiceLocations from './backEndServiceLocations';

const CATALOG_API_BASE_URL = BackEndServiceLocations.getLocation("CATALOG_SERVICE") + "/catalog";
const CONTENT_API_BASE_URL = BackEndServiceLocations.getLocation("CONTENT_SERVICE") + "/content";

const catalogServiceAPI = {
  
  getCatalogItems: async (pageIndex = null, pageSize = null) => {
    let urlSuffix = "";
    if (pageIndex != null && pageSize != null) {
      urlSuffix = "?pageIndex=" + pageIndex + "&pageSize=" + pageSize;
    }
    //console.log("getCatalogItems() pageIndex",pageIndex,"pageSize",pageSize,"urlSuffix",urlSuffix);
    const catalogResponse = await get(CATALOG_API_BASE_URL + "/items" + urlSuffix);
    //console.log("getCatalogItems() ", catalogResponse.data);
    // TODO: make this a bulk request
    for (let i = 0; i < catalogResponse.data.length; i++) {
      catalogResponse.data[i].imageData = "";
      if (catalogResponse.data[i].imageId == null) {
        continue;
      }
      const contentResponse = await get(CONTENT_API_BASE_URL + "?contentId=" + catalogResponse.data[i].imageId);
      catalogResponse.data[i].imageData = contentResponse.data[0].data;
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
