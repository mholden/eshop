import { createAction } from 'redux-actions';
import basketServiceAPI from '@/utils/api/basketServiceAPI';
import catalogServiceAPI from '../../utils/api/catalogServiceAPI';

export const fetchCartRequest = createAction('FETCH_CART_REQUEST');
export const fetchCartSuccess = createAction('FETCH_CART_SUCCESS');
export const fetchCartError = createAction('FETCH_CART_ERROR');

export const fetchCart = () => async (dispatch) => {
  try {
    dispatch(fetchCartRequest());
    const { data } = await basketServiceAPI.getBasketItems();
    // TODO: make this a bulk request
    for (let i = 0; i < data.length; i++) {
      const catalogItem = await catalogServiceAPI.getCatalogItem(data[i].catalogItemId);
      data[i].catalogItem = catalogItem.data;
    }
    dispatch(fetchCartSuccess(data));
  } catch (e) {
    dispatch(fetchCartError(e.message));
  }
};

export const setCartRequest = createAction('SET_CART_REQUEST');
export const setCartSuccess = createAction('SET_CART_SUCCESS');
export const setCartError = createAction('SET_CART_ERROR');

export const setCart = (cart) => async (dispatch) => {
  try {
    dispatch(setCartRequest());
    await basketServiceAPI.setBasketItems(cart);
    dispatch(setCartSuccess(cart));
  } catch (e) {
    dispatch(setCartError(e.message));
  }
};

export const addToCartRequest = createAction('SET_CART_REQUEST');
//export const addToCartRequestDone = createAction('SET_CART_REQUEST_DONE');
export const addToCartSuccess = createAction('SET_CART_SUCCESS');
export const addToCartError = createAction('SET_CART_ERROR');

export const addToCart = (catalogItem) => async (dispatch) => {
  try {
    dispatch(addToCartRequest());
    const { data } = await basketServiceAPI.getBasketItems();
    data.push({catalogItemId: catalogItem.id});
    const newdata  = await basketServiceAPI.setBasketItems(data);
    //console.log("addToCart newdata:",newdata.data);
    // TODO: make this a bulk request
    // TODO: should not need to reload all of these
    for (let i = 0; i < newdata.data.length; i++) {
      const catalogItem = await catalogServiceAPI.getCatalogItem(newdata.data[i].catalogItemId);
      newdata.data[i].catalogItem = catalogItem.data;
    }
    dispatch(addToCartSuccess(newdata.data));
    //dispatch(addToCartRequestDone(newdata.data));
  } catch (e) {
    dispatch(addToCartError(e.message));
  }
};

export const cartCheckoutRequest = createAction('CART_CHECKOUT_REQUEST');
export const cartCheckoutSuccess = createAction('CART_CHECKOUT_SUCCESS');
export const cartCheckoutError = createAction('CART_CHECKOUT_ERROR');

export const cartCheckout = () => async (dispatch) => {
  try {
    dispatch(cartCheckoutRequest());
    await basketServiceAPI.checkout();
    dispatch(cartCheckoutSuccess());
  } catch (e) {
    dispatch(cartCheckoutError(e.message));
  }
};

export const setCheckoutOrderSuccess = createAction('SET_CHECKOUT_ORDER_SUCCESS');

export const setCheckoutOrder = (order) => async (dispatch) => {
  dispatch(setCheckoutOrderSuccess(order));
};

export const setCartAsyncSuccess = createAction('SET_CART_ASYNC_SUCCESS');

export const setCartAsync = (cart) => async (dispatch) => {
  dispatch(setCartAsyncSuccess(cart));
};