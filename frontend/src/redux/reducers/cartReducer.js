import { handleActions } from 'redux-actions';
import {
  fetchCartRequest,
  fetchCartSuccess,
  fetchCartError,
} from '../actions/cartActions';

const defaultState = {
  data: {},
  isFetching: false,
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
  },
  defaultState,
);
