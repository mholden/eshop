import React from 'react';
import PropTypes from 'prop-types';
import TopbarProfile from '../components/topbar/TopbarProfile';
//import TopbarSearch from '../components/topbar/TopbarSearch';
import {
  TopbarContainer,
  TopbarLeft,
  TopbarLogo,
  TopbarRight,
  TopbarRightOver,
  //TopbarSearchWrap,
} from '../components/topbar/BasicTopbarComponents';
import TopbarCart from '../components/topbar/TopbarCart';
import { useAuth } from 'react-oidc-context';
import TopbarOrders from '../components/topbar/TopbarOrders';
import HomeOutlineIcon from 'mdi-react/HomeOutlineIcon';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { colorText } from '@/utils/palette';

const Topbar = ({
  changeMobileSidebarVisibility,
  changeSidebarVisibility,
}) => {

  const auth = useAuth();

  return(
    <TopbarContainer>
      <TopbarLeft>
        {/* <TopbarSidebarButton
          onClickMobile={changeMobileSidebarVisibility}
          onClickDesktop={changeSidebarVisibility}
        /> 
        <TopbarLogo to="/" /> */}
        <TopbarHomeButton data-testid='home-button' to="/">
            <HomeOutlineIcon />
        </TopbarHomeButton>
      </TopbarLeft>
      <TopbarRight>
        {/* <TopbarSearchWrap>
          <TopbarSearch />
        </TopbarSearchWrap> */}
        <TopbarRightOver>
          {/* <TopbarNotification /> */}
          {
            auth.isAuthenticated &&
            (
            <>
              <TopbarCart/>
              <TopbarOrders/>
            </>
            )
          }
          <TopbarProfile />
          {/* <TopbarLanguage /> */}
          {/* <TopbarWallet /> */}
        </TopbarRightOver>
      </TopbarRight>
    </TopbarContainer>
  );
};

Topbar.propTypes = {
  changeMobileSidebarVisibility: PropTypes.func.isRequired,
  changeSidebarVisibility: PropTypes.func.isRequired,
};

export default Topbar;

const TopbarHomeButton = styled(Link)`
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
