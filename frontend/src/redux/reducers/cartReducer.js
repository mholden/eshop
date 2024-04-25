import { handleActions } from 'redux-actions';
import {
  fetchCartRequest,
  fetchCartSuccess,
  fetchCartError,
  setCartRequest,
  setCartSuccess,
  setCartError,
  addToCartRequest,
  addToCartSuccess,
  addToCartError,
  cartCheckoutRequest,
  cartCheckoutSuccess,
  cartCheckoutError,
  setCartAsyncSuccess,
} from '../actions/cartActions';

const defaultState = {
  data: [],
  isFetching: false,
  isSetting: false,
  isAdding: false,
  isCheckingOut: false,
  error: null,
};

export default handleActions(
  {
    [fetchCartRequest](state) {
      return {
        ...state,
        isFetching: true,
        error: null,
      };
    },
    [fetchCartSuccess](state, { payload }) {
      return {
        ...state,
        data: payload,
        isFetching: false,
        error: null,
      };
    },
    [fetchCartError](state, { payload }) {
      return {
        ...state,
        isFetching: false,
        error: payload,
      };
    },
    [setCartRequest](state) {
      return {
        ...state,
        isSetting: true,
        error: null,
      };
    },
    [setCartSuccess](state, { payload }) {
      return {
        ...state,
        data: payload,
        isSetting: false,
        error: null,
      };
    },
    [setCartError](state, { payload }) {
      return {
        ...state,
        isSetting: false,
        error: payload,
      };
    },
    [addToCartRequest](state) {
      return {
        ...state,
        isAdding: true,
        error: null,
      };
    },
    [addToCartSuccess](state, { payload }) {
      return {
        ...state,
        data: payload,
        isAdding: false,
        error: null,
      };
    },
    [addToCartError](state, { payload }) {
      return {
        ...state,
        isAdding: false,
        error: payload,
      };
    },
    [cartCheckoutRequest](state) {
      return {
        ...state,
        isCheckingOut: true,
        error: null,
      };
    },
    [cartCheckoutSuccess](state) {
      return {
        ...state,
        isCheckingOut: false,
        error: null,
      };
    },
    [cartCheckoutError](state, { payload }) {
      return {
        ...state,
        isCheckingOut: false,
        error: payload,
      };
    },
    [setCartAsyncSuccess](state, { payload }) {
      return {
        ...state,
        data: payload,
      };
    },
  },
  defaultState,
);
