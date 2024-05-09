import React, { useEffect, useState } from 'react';
import { Collapse } from 'react-bootstrap';
import styled from 'styled-components';
import CartOutlineIcon from 'mdi-react/CartOutlineIcon';
import { TopbarBack, TopbarButton, TopbarButtonNewLabel, TopbarNumberAttribute } from './BasicTopbarComponents';
import {
  TopbarCollapse,
  TopbarCollapseContent,
  TopbarCollapseTitleWrap,
  TopbarCollapseItem,
  TopbarCollapseImageWrap,
  TopbarCollapseMessage,
  TopbarCollapseName,
  TopbarCollapseDate,
  TopbarCollapseLink,
  TopbarCollapseTitle,
  TopbarCollapseButton,
} from './CollapseTopbarComponents';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCart } from '@/redux/actions/cartActions';
import SimpleLoader from '../../../../shared/components/SimpleLoader';
import { Link } from 'react-router-dom';
import { colorAccent } from '@/utils/palette';
import { colorHover, colorLightGray, colorText } from '../../../../utils/palette';

const notifications = [
  {
    id: 0,
    ava: `${process.env.PUBLIC_URL}/img/topbar/ava.png`,
    name: 'Cristopher Changer',
    message: 'Good but communication was good e..',
    date: '09:02',
  },
  {
    id: 1,
    ava: `${process.env.PUBLIC_URL}/img/topbar/ava2.png`,
    name: 'Sveta Narry',
    message: 'It is a long established fact that a read..',
    date: '09:00',
  },
  {
    id: 2,
    ava: `${process.env.PUBLIC_URL}/img/topbar/ava3.png`,
    name: 'Lory McQueen',
    message: 'There are many variations of passages..',
    date: '08:43',
  },
  {
    id: 3,
    ava: `${process.env.PUBLIC_URL}/img/topbar/ava2.png`,
    name: 'Cristopher Changer',
    message: 'Yas sent you an invitation to join proje..',
    date: '08:43',
  },
];

const TopbarCart = () => {

  const { 
    cartItems, cartItemsAreLoading, cartItemsError,
  } = useSelector(state => ({
    cartItems: state.cart.data,
    cartItemsAreLoading: state.cart.isFetching || state.cart.isAdding,
    cartItemsError: state.cart.error,
  }));

  const [isCollapsed, setIsCollapsed] = useState(false);

  const collapseMail = () => {
    setIsCollapsed(!isCollapsed);
  };

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchCart());
  }, [dispatch]);

  return (
    <TopbarCollapse>
      <TopbarCartButton data-testid='cart-button' to="/cart">
        {
          cartItemsAreLoading
          ? (
            <CartLoading>
              <SimpleLoader widthOrHeight={8} />
            </CartLoading>
            )
          : (
            <TopbarNumberAttribute data-testid='topbar-cart-number'>
              {cartItems.length}
            </TopbarNumberAttribute>
          )
        }
        <CartOutlineIcon />
        {/*<TopbarButtonNewLabel>
          <span />
        </TopbarButtonNewLabel>*/}
      </TopbarCartButton>
      {isCollapsed && (
        <TopbarBack
          aria-label="topbar__back"
          type="button"
          onClick={collapseMail}
        />
      )}
      <Collapse
        in={isCollapsed}
      >
        <TopbarCollapseContent>
          <TopbarCollapseTitleWrap>
            <TopbarCollapseTitle>Unread messages</TopbarCollapseTitle>
            <TopbarCollapseButton type="button">Mark all as read</TopbarCollapseButton>
          </TopbarCollapseTitleWrap>
          {notifications.map(notification => (
            <TopbarCollapseItem key={notification.id}>
              <TopbarCollapseImageWrap>
                <img src={notification.ava} alt="" />
              </TopbarCollapseImageWrap>
              <TopbarCollapseName>{notification.name}</TopbarCollapseName>
              <TopbarMailCollapseMessage>{notification.message}</TopbarMailCollapseMessage>
              <TopbarCollapseDate>{notification.date}</TopbarCollapseDate>
            </TopbarCollapseItem>
          ))}
          <TopbarCollapseLink to="/mail" onClick={collapseMail}>
            See all messages
          </TopbarCollapseLink>
        </TopbarCollapseContent>
      </Collapse>
    </TopbarCollapse>
  );
};

export default TopbarCart;

// region STYLES

const TopbarMailCollapseMessage = styled(TopbarCollapseMessage)`
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
`;

const TopbarMailButton = styled(TopbarButton)`

  @media screen and (max-width: 640px) {
    right: 5px !important;
  }
`;

const TopbarCartButton = styled(Link)`
  display: block;
  padding: 10px;
  text-transform: uppercase;
  color: ${colorText};
  transition: 0.3s;
  text-align: center;
  font-weight: 500;
  font-size: 10px;
  line-height: 16px;

  &:hover {
    color: ${colorText}-hover;
  }
`;

const CartLoading = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

// endregion
