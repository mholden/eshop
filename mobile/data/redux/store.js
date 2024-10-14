import { combineReducers, createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import {
  cartReducer,
  catalogItemReducer,
  catalogItemsReducer,
  ordersReducer,
} from '@/data/redux/reducers/index';

const reducer = combineReducers({
  catalogItem: catalogItemReducer,
  catalogItems: catalogItemsReducer,
  cart: cartReducer,
  orders: ordersReducer
});
const store = createStore(reducer, applyMiddleware(thunk));

//
// set this to make store available in browser console
// window.store.getState() will show all reducers
//
//window.store = store;

export default store;
