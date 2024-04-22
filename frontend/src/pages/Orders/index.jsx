import React, { useEffect } from 'react';
import { Container, Row } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { fetchOrders } from '../../redux/actions/ordersActions';
import ErrorAlert from '@/shared/components/ErrorAlert';
import OrdersCard from './components/OrdersCard';

const Orders = () => {

  const { 
    orders, ordersAreLoading, ordersError,
  } = useSelector(state => ({
      orders: state.orders.data,
      ordersAreLoading: state.orders.isFetching,
      ordersError: state.orders.error,
  }));

  const dispatch = useDispatch();

  useEffect(() => {
      dispatch(fetchOrders());
  }, [dispatch]);

  return (
    <Container>
      <Row>
        <ErrorAlert subTitle="Orders" error={ordersError} />
        <OrdersCard items={orders} isLoading={ordersAreLoading} />
      </Row>
    </Container>
  );
};

export default Orders;
