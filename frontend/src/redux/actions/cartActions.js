import { createAction } from 'redux-actions';
import basketServiceAPI from '@/utils/api/basketServiceAPI';

export const fetchCartRequest = createAction('FETCH_CART_REQUEST');
export const fetchCartSuccess = createAction('FETCH_CART_SUCCESS');
export const fetchCartError = createAction('FETCH_CART_ERROR');

export const fetchCart = () => async (dispatch) => {
  try {
    dispatch(fetchCartRequest());
    const { data } = await basketServiceAPI.getBasketItems();
    dispatch(fetchCartSuccess(data));
  } catch (e) {
    dispatch(fetchCartError(e.message));
  }
};
