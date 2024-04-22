import { handleActions } from 'redux-actions';
import {
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
