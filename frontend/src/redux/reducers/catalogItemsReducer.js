import { handleActions } from 'redux-actions';
import {
  fetchCatalogItemsRequest,
  fetchCatalogItemsSuccess,
  fetchCatalogItemsError,
} from '../actions/catalogItemsActions';

const defaultState = {
  data: [],
  isFetching: false,
  error: null,
};

export default handleActions(
  {
    [fetchCatalogItemsRequest](state) {
      return {
        ...state,
        isFetching: true,
        error: null,
      };
    },
    [fetchCatalogItemsSuccess](state, { payload }) {
      return {
        ...state,
        data: payload,
        isFetching: false,
        error: null,
      };
    },
    [fetchCatalogItemsError](state, { payload }) {
      return {
        ...state,
        isFetching: false,
        error: payload,
      };
    },
  },
  defaultState,
);
