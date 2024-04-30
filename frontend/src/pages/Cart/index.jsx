import React, { useEffect, useState } from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import CartCard from './components/CartCard';
import { useDispatch, useSelector } from 'react-redux';
import SimpleLoader from '../../shared/components/SimpleLoader';
import styled from 'styled-components';
import ErrorAlert from '@/shared/components/ErrorAlert';
import { fetchCart, setCart } from '../../redux/actions/cartActions';

const Cart = () => {

  const { 
    cartItems, cartItemsInProgress, cartCheckoutOrder, cartItemsError,
  } = useSelector(state => ({
    cartItems: state.cart.data,
    cartItemsInProgress: state.cart.isFetching || state.cart.isAdding || state.cart.isCheckingOut,
    cartCheckoutOrder: state.cart.checkoutOrder,
    cartItemsError: state.cart.error,
  }));

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchCart());
  }, [dispatch]);

  const removeAllCartItems = () => {
    dispatch(setCart([]));
  };

  return (
    <Container>
      <Row>
        <ErrorAlert subTitle="Cart" error={cartItemsError} />
        <CartCard items={cartItems} cartCheckoutOrder={cartCheckoutOrder} shouldSpin={cartItemsInProgress} removeAllCartItems={removeAllCartItems} />
      </Row>
    </Container>
  );
};

export default Cart;
