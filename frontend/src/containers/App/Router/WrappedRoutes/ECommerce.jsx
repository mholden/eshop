import React from 'react';
import { Route, Switch } from 'react-router-dom';
import Catalog from '../../../ECommerce/Catalog/index';

const Ecommerce = () => (
  <Switch>
    <Route path="/e-commerce/catalog" component={Catalog} />
  </Switch>
);

export default Ecommerce;
