import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import { paddingLeft } from '../../utils/directions';
import { changeMobileSidebarVisibility, changeSidebarVisibility } from '../../redux/actions/sidebarActions';
import {
  changeThemeToDark, changeThemeToLight,
} from '../../redux/actions/themeActions';
import Sidebar from './components/sidebar/Sidebar';
import Topbar from './topbar/Topbar';

const Layout = () => {

  const {
    customizer, sidebar,
  } = useSelector(state => ({
    customizer: state.customizer,
    sidebar: state.sidebar,
  }));

  const dispatch = useDispatch();

  const sidebarVisibility = () => {
    dispatch(changeSidebarVisibility());
  };

  const mobileSidebarVisibility = () => {
    dispatch(changeMobileSidebarVisibility());
  };

  const changeToDark = () => {
    dispatch(changeThemeToDark());
  };

  const changeToLight = () => {
    dispatch(changeThemeToLight());
  };

  return (
    <LayoutContainer
      collapse={sidebar.collapse}
      topNavigation={customizer.topNavigation}
    >
      <Topbar
          changeMobileSidebarVisibility={mobileSidebarVisibility}
          changeSidebarVisibility={sidebarVisibility}
      />
      <Sidebar
        sidebar={sidebar}
        changeToDark={changeToDark}
        changeToLight={changeToLight}
        changeMobileSidebarVisibility={mobileSidebarVisibility}
        topNavigation={customizer.topNavigation}
      />
    </LayoutContainer>
  );
};

export default Layout;

// region STYLES

const LayoutContainer = styled.div`
  & + div {
    ${props => props.collapse && `
      ${paddingLeft(props)}: 0;
    `};

    @media screen and (min-width: 576px) {
      ${props => props.collapse && `
        ${paddingLeft(props)}: 60px;
      `}

      ${props => props.topNavigation && `
         ${paddingLeft(props)}: 0;
      `}
    }
  }
`;

// endregion
