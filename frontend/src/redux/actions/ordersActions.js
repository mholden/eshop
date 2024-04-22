import { createAction } from 'redux-actions';
import orderServiceAPI from '@/utils/api/orderServiceAPI';

export const fetchOrdersRequest = createAction('FETCH_ORDERS_REQUEST');
export const fetchOrdersSuccess = createAction('FETCH_ORDERS_SUCCESS');
export const fetchOrdersError = createAction('FETCH_ORDERS_ERROR');

export const fetchOrders = () => async (dispatch) => {
  try {
    dispatch(fetchOrdersRequest());
    const { data } = await orderServiceAPI.getOrders();
    dispatch(fetchOrdersSuccess(data));
  } catch (e) {
    dispatch(fetchOrdersError(e.message));
  }
};