import React from 'react';
import InvoiceIcon from 'mdi-react/InvoiceIcon';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { colorText } from '@/utils/palette';

const TopbarOrders = () => {
    return (
        <TopbarOrdersButton data-testid='orders-button' to="/orders">
            <InvoiceIcon />
        </TopbarOrdersButton>
    );
}

export default TopbarOrders;

// region STYLES

const TopbarOrdersButton = styled(Link)`
  display: block;
  padding: 10px;
  text-transform: uppercase;
  color: ${colorText};
  transition: 0.3s;
  text-align: center;
  font-weight: 500;
  font-size: 10px;
  line-height: 42px;

  &:hover {
    color: ${colorText}-hover;
  }
`;

// endregion