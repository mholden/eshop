import { handleActions } from 'redux-actions';
import {
  addOrUpdateOrderSuccess,
  fetchOrdersRequest,
  fetchOrdersSuccess,
  fetchOrdersError,
} from '../actions/ordersActions';

const defaultState = {
  data: [],
  isFetching: false,
  error: null,
};

export default handleActions(
  {
    [addOrUpdateOrderSuccess](state, { payload }) {
      // TODO: use a hashmap for data
      for (let i = 0; i < state.data.length; i++) {
        if (state.data[i].id === payload.id) {
          state.data.splice(i, 1);
          break;
        }
      }
      state.data.unshift(payload);
      return {
        ...state,
      };
    },
    [fetchOrdersRequest](state) {
      return {
        ...state,
        isFetching: true,
        error: null,
      };
    },
    [fetchOrdersSuccess](state, { payload }) {
      return {
        ...state,
        data: payload,
        isFetching: false,
        error: null,
      };
    },
    [fetchOrdersError](state, { payload }) {
      return {
        ...state,
        isFetching: false,
        error: payload,
      };
    },
  },
  defaultState,
);
