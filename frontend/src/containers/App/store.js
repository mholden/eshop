import { combineReducers, createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import {
  themeReducer,
  rtlReducer,
  roundBordersReducer,
  blocksShadowsReducer,
  customizerReducer,
  sidebarReducer,
  catalogItemsReducer,
  catalogItemReducer,
  cartReducer,
  ordersReducer,
} from '../../redux/reducers/index';

const reducer = combineReducers({
  theme: themeReducer,
  rtl: rtlReducer,
  border: roundBordersReducer,
  shadow: blocksShadowsReducer,
  customizer: customizerReducer,
  sidebar: sidebarReducer,
  catalogItems: catalogItemsReducer,
  catalogItem: catalogItemReducer,
  cart: cartReducer,
  orders: ordersReducer,
});
const store = createStore(reducer, applyMiddleware(thunk));

export default store;
