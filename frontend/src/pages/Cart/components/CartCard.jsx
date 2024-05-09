import React from 'react';
import { Col } from 'react-bootstrap';
import styled from 'styled-components';
import DeleteForeverIcon from 'mdi-react/DeleteForeverIcon';
import {
  Card, CardBody, CardTitleWrap, CardTitle,
} from '@/shared/components/Card';
import { colorAdditional, colorRedHover } from '@/utils/palette';
import { left, paddingLeft, paddingRight } from '@/utils/directions';
import { Table } from '@/shared/components/TableElements';
import CartPurchase from './CartPurchase';
import SimpleLoader from '../../../shared/components/SimpleLoader';

//import cartList from './cart_list';

const CartCard = ({items, cartCheckoutOrder, shouldSpin, removeAllCartItems}) => {
  return (
    <Col md={12} lg={12}>
      <Card>
        {
        shouldSpin
        ? (
          <CartLoading>
            <SimpleLoader widthOrHeight={28} />
          </CartLoading>
        ) : (
          <CardBody>
            <CartTable data-testid='cart-table' bordered responsive>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Name</th>
                  <th>Quantity</th>
                  <th>Price</th>
                  <th>Total</th>
                  <th>
                    <CartTableButton data-testid='cart-table-remove-all-button' type="button" onClick={removeAllCartItems}>
                      <DeleteForeverIcon /> Remove All
                    </CartTableButton>
                  </th>
                  <th aria-label="cart table" />
                </tr>
              </thead>
              <tbody>
                {items.map((ci, i) => (
                  <tr data-testid={`cart-table-item-${i}`} key={`index_${i + 1}`}>
                    <td>{i + 1}</td>
                    <td>
                      <CartPreviewImageWrap>
                        <img src={`data:image/png;base64,${ci.catalogItem.imageData}`} alt="product_preview" />
                      </CartPreviewImageWrap>
                      <span data-testid={`cart-table-item-${i}-name`}>{ci.catalogItem.name}</span>
                    </td>
                    <td data-testid={`cart-table-item-${i}-quantity`}>1</td>
                    <td data-testid={`cart-table-item-${i}-price`}>${ci.catalogItem.price / 100}</td>
                    <td data-testid={`cart-table-item-${i}-total`}>${ci.catalogItem.price / 100}</td>
                    <td>
                      <CartTableButton data-testid={`cart-table-item-${i}-remove-button`} type="button">
                        <DeleteForeverIcon /> Remove
                      </CartTableButton>
                    </td>
                  </tr>
                ))}
              </tbody>
            </CartTable>
            <CartPurchase onSubmit cartCheckoutOrder={cartCheckoutOrder} />
          </CardBody>
        )
        }
      </Card>
    </Col>
  );
};

CartCard.defaultProps = {
  items: [],
};

export default CartCard;

// region STYLES

const CartLoading = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const CartPreviewImageWrap = styled.span`
  width: 50px;
  height: 45px;
  border: 1px solid #f0f0f0;
  display: inline-block;
  overflow: hidden;
  text-align: center;
  padding: 5px;
  position: absolute;
  top: 8px;

  & + span {
    ${paddingLeft}: 60px;
    display: block;
    min-width: 400px;
  }

  img {
    height: 100%;
    width: auto;
    max-width: 100%;
  }
`;

const CartTableButton = styled.button`
  background: transparent;
  border: none;
  line-height: 14px;
  ${paddingLeft}: 20px;
  ${paddingRight}: 0;
  padding-top: 0;
  padding-bottom: 0;
  position: relative;
  color: ${colorAdditional};
  cursor: pointer;
  transition: all 0.3s;

  svg {
    height: 16px;
    width: 16px;
    position: absolute;
    top: -2px;
    ${left}: 0;
    fill: ${colorAdditional};
    transition: all 0.3s;
  }

  &:hover {
    color: ${colorRedHover};

    svg {
      fill: ${colorRedHover};
    }
  }
`;

const CartTable = styled(Table)`
  
  tbody td {
    padding: 20px 10px;
    position: relative;
  }
`;

const CartSubTotal = styled.h5`
  text-align: ${left};
  margin-top: 20px;
  font-weight: 700;
`;

// endregion
