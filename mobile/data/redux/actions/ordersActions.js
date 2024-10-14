import { createAction } from 'redux-actions';
import orderServiceAPI from '@/data/api/orderServiceAPI';

export const fetchOrdersRequest = createAction('FETCH_ORDERS_REQUEST');
export const fetchOrdersSuccess = createAction('FETCH_ORDERS_SUCCESS');
export const fetchOrdersError = createAction('FETCH_ORDERS_ERROR');

export const fetchOrders = () => async (dispatch) => {
  try {
    dispatch(fetchOrdersRequest());
    const { data } = await orderServiceAPI.getOrders();
    // TODO: do this on the back end (?)
    data.sort((a, b) => {
      return new Date(b.creationTime) - new Date(a.creationTime)
    });
    dispatch(fetchOrdersSuccess(data));
  } catch (e) {
    dispatch(fetchOrdersError(e.message));
  }
};

export const addOrUpdateOrderSuccess = createAction('ADD_OR_UPDATE_ORDER_SUCCESS');

export const addOrUpdateOrder = (order) => async (dispatch) => {
  dispatch(addOrUpdateOrderSuccess(order));
};