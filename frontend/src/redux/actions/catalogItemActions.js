import { createAction } from 'redux-actions';
import catalogServiceAPI from '@/utils/api/catalogServiceAPI';

export const fetchCatalogItemRequest = createAction('FETCH_CATALOG_ITEM_REQUEST');
export const fetchCatalogItemSuccess = createAction('FETCH_CATALOG_ITEM_SUCCESS');
export const fetchCatalogItemError = createAction('FETCH_CATALOG_ITEM_ERROR');

export const fetchCatalogItem = catalogItemId => async (dispatch) => {
  try {
    dispatch(fetchCatalogItemRequest());
    const { data } = await catalogServiceAPI.getCatalogItem(catalogItemId);
    dispatch(fetchCatalogItemSuccess(data));
  } catch (e) {
    dispatch(fetchCatalogItemError(e.message));
  }
};
