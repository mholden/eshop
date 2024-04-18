import React from 'react';
import { Field, Form } from 'react-final-form';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import renderRadioButtonField from '@/shared/components/form/RadioButton';
import {
  FormButtonToolbar,
  FormContainer,
  FormGroup,
  FormGroupField,
  FormGroupLabel,
} from '@/shared/components/form/FormElements';
import { colorAdditional } from '@/utils/palette';
import { marginRight, paddingLeft } from '@/utils/directions';
import { Button } from '@/shared/components/Button';
import { useDispatch } from 'react-redux';
import { cartCheckout } from '../../../redux/actions/cartActions';

const CartPurchase = () => {

  const dispatch = useDispatch();

  const doCheckout = () => {
    dispatch(cartCheckout());
  };

  return (
    <Form onSubmit={() => {}} initialValues={{ delivery: 'russian_post' }}>
      {({ handleSubmit }) => (
        <CartDeliveriesForm onSubmit={handleSubmit}>
          <CartTotal>Total: $348.00</CartTotal>
          <FormButtonToolbar>
            <Button onClick={doCheckout}>Checkout</Button>
          </FormButtonToolbar>
        </CartDeliveriesForm>
      )}
    </Form>
  );
};

export default CartPurchase;

// region STYLES

const CartDeliveriesForm = styled(FormContainer)`
  margin-top: 20px;
  justify-content: right;
`;

const CartDelivery = styled.div`
  ${marginRight}: 50px;
  margin-bottom: 10px;

  &:last-child {
    ${marginRight}: 0;
  }

  & > label {
    margin-bottom: 0;
  }
  
`;

const CartDeliveryField = styled(FormGroupField)`
  flex-wrap: wrap;
`;

const CartTotal = styled.h4`
  width: 100%;
  font-weight: 700;
  margin-bottom: 5px;
  text-align: right;
`;

const CartDeliveryPrice = styled.p`
  font-size: 10px;
  line-height: 13px;
  margin: 0;
  ${paddingLeft}: 27px;
`;

const CartDeliveryTime = styled(CartDeliveryPrice)`
  color: ${colorAdditional};
  margin-bottom: 8px;
`;

// endregion
