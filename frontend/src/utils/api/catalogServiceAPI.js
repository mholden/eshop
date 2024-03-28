import get from './base/get';

const CATALOG_API_BASE_URL = "http://localhost:8080/catalog";
const CONTENT_API_BASE_URL = "http://localhost:8080/content";

const catalogServiceAPI = {
  getCatalogItems: async () => {
    const catalogItemsResponse = await get(CATALOG_API_BASE_URL + "/items");
    // TODO: make this a bulk request
    for (let i = 0; i < catalogItemsResponse.data.length; i++) {
      catalogItemsResponse.data[i].imageData = "";
      if (catalogItemsResponse.data[i].imageId == null) {
        continue;
      }
      const contentResponse = await get(CONTENT_API_BASE_URL + "?contentId=" + catalogItemsResponse.data[i].imageId);
      catalogItemsResponse.data[i].imageData = contentResponse.data.data;
    }
    //console.log("getCatalogItems() ", catalogItemsResponse.data);
    return catalogItemsResponse;
  },
};

export default catalogServiceAPI;
