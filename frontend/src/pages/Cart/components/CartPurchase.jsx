import React, { useState } from 'react';
import { Field, Form } from 'react-final-form';
import { Link, useHistory } from 'react-router-dom';
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
import { cartCheckout, setCheckoutOrderSuccess } from '../../../redux/actions/cartActions';
import { 
  StyledModal, 
  ModalHeader, 
  ModalCloseButton, 
  ModalTitle, 
  ModalBody, 
  ModalFooter 
} from '@/shared/components/Modal';

const CartPurchase = ({cartCheckoutOrder}) => {

  const [modal, setModal] = useState(false);
  const history = useHistory();

  const toggleModal = () => {
    setModal(prevState => !prevState);
  };

  const dispatch = useDispatch();

  const doCheckout = () => {
    dispatch(cartCheckout());
  };

  const ackCheckoutSuccess = (to) => {
    dispatch(setCheckoutOrderSuccess(null));
    history.push(to);
  }

  return (
    <div>
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
      <StyledModal
        show={cartCheckoutOrder !== null}
        /*onHide={toggleModal}*/
        color="primary"
      >
        {
          cartCheckoutOrder !== null && 
          (
            <>
            <ModalHeader>
              {/*<ModalCloseButton
                className="lnr lnr-cross"
                aria-label="close-btn"
                type="button"
                onClick={toggleModal}
              />*/}
              <ModalTitle>Thank you for your Order!</ModalTitle>
            </ModalHeader>
            <ModalBody>
              Your order confirmation number is {cartCheckoutOrder.id}
            </ModalBody>
            <ModalFooter>
              <Button onClick={() => ackCheckoutSuccess("/")}>Back to Shopping</Button>
              <Button onClick={() => ackCheckoutSuccess("/orders")}>View My Order</Button>
            </ModalFooter>
            </>
          )
        }
      </StyledModal>
    </div>
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
