import get from './base/get';

//const CATALOG_API_BASE_URL = "http://localhost:8080/catalog";
//const CONTENT_API_BASE_URL = "http://localhost:8080/content";
const CATALOG_API_BASE_URL = "http://eshop.hldn.live:8080/catalog";
const CONTENT_API_BASE_URL = "http://eshop.hldn.live:8080/content";

const catalogServiceAPI = {
  
  getCatalogItems: async () => {
    const catalogResponse = await get(CATALOG_API_BASE_URL + "/items");
    // TODO: make this a bulk request
    for (let i = 0; i < catalogResponse.data.length; i++) {
      catalogResponse.data[i].imageData = "";
      if (catalogResponse.data[i].imageId == null) {
        continue;
      }
      const contentResponse = await get(CONTENT_API_BASE_URL + "?contentId=" + catalogResponse.data[i].imageId);
      catalogResponse.data[i].imageData = contentResponse.data.data;
    }
    //console.log("getCatalogItems() ", catalogResponse.data);
    return catalogResponse;
  },

  getCatalogItem: async (catalogItemId) => {
    const catalogResponse = await get(CATALOG_API_BASE_URL + "/item?catalogItemId=" + catalogItemId);
    catalogResponse.data.imageData = "";
    if (catalogResponse.data.imageId != null) {
      const contentResponse = await get(CONTENT_API_BASE_URL + "?contentId=" + catalogResponse.data.imageId);
      catalogResponse.data.imageData = contentResponse.data.data;
    }
    //console.log("getCatalogItem() ", catalogResponse.data);
    return catalogResponse;
  },
};

export default catalogServiceAPI;
