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
      <TopbarLogo to="/online_marketing_dashboard" />
    </TopbarLeft>
    <TopbarRight>
      {/* <TopbarSearchWrap>
        <TopbarSearch />
      </TopbarSearchWrap> */}
      <TopbarRightOver>
        {/* <TopbarNotification /> */}
        {/* <TopbarMail new /> */}
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
