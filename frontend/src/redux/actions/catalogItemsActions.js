import { createAction } from 'redux-actions';
import catalogServiceAPI from '@/utils/api/catalogServiceAPI';

export const fetchCatalogItemsRequest = createAction('FETCH_CATALOG_ITEMS_REQUEST');
export const fetchCatalogItemsSuccess = createAction('FETCH_CATALOG_ITEMS_SUCCESS');
export const fetchCatalogItemsError = createAction('FETCH_CATALOG_ITEMS_ERROR');

export const fetchCatalogItems = (pageIndex, pageSize) => async (dispatch) => {
  try {
    dispatch(fetchCatalogItemsRequest());
    const { data } = await catalogServiceAPI.getCatalogItems(pageIndex, pageSize);
    dispatch(fetchCatalogItemsSuccess(data));
  } catch (e) {
    dispatch(fetchCatalogItemsError(e.message));
  }
};
