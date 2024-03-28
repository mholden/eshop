import { createAction } from 'redux-actions';
import catalogServiceAPI from '@/utils/api/catalogServiceAPI';
import { firstLetterToUpperCase } from '@/shared/helpers';

export const fetchCatalogItemsRequest = createAction('FETCH_CATALOG_ITEMS_REQUEST');
export const fetchCatalogItemsSuccess = createAction('FETCH_CATALOG_ITEMS_SUCCESS');
export const fetchCatalogItemsError = createAction('FETCH_CATALOG_ITEMS_ERROR');

export const fetchCatalogItems = () => async (dispatch) => {
  try {
    dispatch(fetchCatalogItemsRequest());
    const { data } = await catalogServiceAPI.getCatalogItems();
    dispatch(fetchCatalogItemsSuccess(data));
  } catch (e) {
    dispatch(fetchCatalogItemsError(firstLetterToUpperCase(e.response.data?.error || e.response?.statusText)));
  }
};
