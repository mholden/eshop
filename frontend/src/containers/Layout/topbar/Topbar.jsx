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
import AuthService from '../../../services/AuthService';

const Topbar = ({
  changeMobileSidebarVisibility,
  changeSidebarVisibility,
}) => (
  <TopbarContainer>
    <TopbarLeft>
      {/* <TopbarSidebarButton
        onClickMobile={changeMobileSidebarVisibility}
        onClickDesktop={changeSidebarVisibility}
      /> */}
      <TopbarLogo to="/" />
    </TopbarLeft>
    <TopbarRight>
      {/* <TopbarSearchWrap>
        <TopbarSearch />
      </TopbarSearchWrap> */}
      <TopbarRightOver>
        {/* <TopbarNotification /> */}
        {
          AuthService.isLoggedIn() && 
          <TopbarCart />
        }
        <TopbarProfile />
        {/* <TopbarLanguage /> */}
        {/* <TopbarWallet /> */}
      </TopbarRightOver>
    </TopbarRight>
  </TopbarContainer>
);

Topbar.propTypes = {
  changeMobileSidebarVisibility: PropTypes.func.isRequired,
  changeSidebarVisibility: PropTypes.func.isRequired,
};

export default Topbar;
