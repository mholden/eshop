import { handleActions } from 'redux-actions';
import {
  fetchCatalogItemRequest,
  fetchCatalogItemSuccess,
  fetchCatalogItemError,
} from '../actions/catalogItemActions';

const defaultState = {
  data: {},
  isFetching: false,
  error: null,
};

export default handleActions(
  {
    [fetchCatalogItemRequest](state) {
      return {
        ...state,
        isFetching: true,
        error: null,
      };
    },
    [fetchCatalogItemSuccess](state, { payload }) {
      return {
        ...state,
        data: payload,
        isFetching: false,
        error: null,
      };
    },
    [fetchCatalogItemError](state, { payload }) {
      return {
        ...state,
        isFetching: false,
        error: payload,
      };
    },
  },
  defaultState,
);
