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
import SimpleLoader from '../../../shared/components/SimpleLoader';

//import cartList from './cart_list';

const OrdersCard = ({items, isLoading}) => {
  return (
    <Col md={12} lg={12}>
      <Card>
        {
        isLoading
        ? (
          <OrdersLoading>
            <SimpleLoader widthOrHeight={28} />
          </OrdersLoading>
        ) : (
          <CardBody>
            <OrdersTable bordered responsive>
              <thead>
                <tr>
                  <th>Order Id</th>
                  <th>Date</th>
                  <th>State</th>
                  <th aria-label="cart table" />
                </tr>
              </thead>
              <tbody>
                {items.map((oi, i) => (
                  <tr key={`index_${i + 1}`}>
                    <td>{oi.id}</td>
                    <td>{oi.creationTime}</td>
                    <td>{oi.state}</td>
                  </tr>
                ))}
              </tbody>
            </OrdersTable>
          </CardBody>
        )
        }
      </Card>
    </Col>
  );
};

OrdersCard.defaultProps = {
  items: [],
};

export default OrdersCard;

// region STYLES

const OrdersLoading = styled.div`
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

const OrdersTable = styled(Table)`
  
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
