import React from 'react';
import PropTypes from 'prop-types';
import { ThemeProvider } from 'styled-components';
import { Provider, useSelector } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.min.css';
import { createTheme, ThemeProvider as MuiThemeProvider } from '@material-ui/core/styles';
import TimepickerStyles from '../../shared/components/form/date-pickers/timepickerStyles';
import GlobalStyles from './globalStyles';
import RechartStyles from './rechartStyles';
import NotificationStyles from './notificationStyles';
import CalendarStyles from './calendarStyles';
import { BrowserRouter, Switch, Route } from 'react-router-dom'
import store from './store';
import styled from 'styled-components';
import { colorBackgroundBody } from '../../utils/palette';
import { paddingLeft } from '../../utils/directions';
import Layout from '../Layout';
import Catalog from '../ECommerce/Catalog';
import CatalogItem from '../../pages/CatalogItem';
import Cart from '../../pages/Cart';
import { StompSessionProvider } from 'react-stomp-hooks';
import AsyncChannel from './asyncChannel';
import { AuthProvider } from 'react-oidc-context';
import { WebStorageStateStore } from 'oidc-client-ts';
import Orders from '../../pages/Orders';
import BackEndServiceLocations from '../../utils/backEndServiceLocations';
import { PostHogProvider} from 'posthog-js/react'
import posthog from 'posthog-js';

const ThemeComponent = ({
  children, 
}) => {
  const {
    mode, direction, shadow, border,
  } = useSelector(state => ({
    mode: state.theme.className,
    direction: state.rtl.direction,
    shadow: state.shadow.className,
    border: state.border.className,
  }));
  
  const theme = createTheme({
    palette: {
      type: mode,
    },
  });

  return (
    <MuiThemeProvider theme={theme}>
      <ThemeProvider
        theme={{
          mode,
          direction,
          shadow,
          border,
        }}
      >
        <GlobalStyles />
        <NotificationStyles />
        <RechartStyles />
        <TimepickerStyles />
        <CalendarStyles />
        {children}
      </ThemeProvider>
    </MuiThemeProvider>
  );
};

ThemeComponent.propTypes = {
  children: PropTypes.node.isRequired,
};

const ConnectedThemeComponent = ThemeComponent;

const oidcConfig = {
  authority: BackEndServiceLocations.getLocation("IDENTITY_SERVICE") + "/auth/realms/spring-cloud-gateway-realm",
  client_id: "spring-cloud-gateway-client",
  redirect_uri: "http://localhost:3000",
  post_logout_redirect_uri: "http://localhost:3000",
  userStore: new WebStorageStateStore({ store: window.localStorage }),
  onSigninCallback: (user) => {
    //console.log("onSigninCallback() user",user,"username",user.profile.preferred_username);
    posthog.identify(user.profile.preferred_username, { email: user.profile.email });
  }
  // ...
};

function App() {
  return (
    <Provider store={store}>
      <AuthProvider {...oidcConfig}>
        <StompSessionProvider url={BackEndServiceLocations.getLocation("NOTIFICATION_SERVICE") + "/notification/ws"}>
        <PostHogProvider 
          apiKey={"phc_HFRqZBvNP1mbB249aF3LEPfQquwlBW9WHfwzsS5kT0e"}
          options={{
            api_host: "https://us.i.posthog.com",
          }}
        >
          <AsyncChannel/>
          <BrowserRouter>
              <ConnectedThemeComponent>
                <Layout/>
                <ContainerWrap>
                  <Switch>
                    <Route exact path="/" component={Catalog}/>
                    <Route exact path="/catalog/item" component={CatalogItem}/>
                    <Route exact path="/cart" component={Cart}/>
                    <Route exact path="/orders" component={Orders}/>
                  </Switch>
                </ContainerWrap>
              </ConnectedThemeComponent>
          </BrowserRouter>
        </PostHogProvider>
        </StompSessionProvider>
      </AuthProvider>
    </Provider>
  );
}

// region STYLES

const ContainerWrap = styled.div`
  padding-top: 90px;
  min-height: 100vh;
  transition: padding-left 0.3s;

  ${paddingLeft}: 0;

  background: ${colorBackgroundBody};

  @media screen and (min-width: 576px) {
    ${paddingLeft}: 250px;
  }

  @media screen and (max-width: 576px) {
    padding-top: 150px;
  }
`;

// endregion

export default App;
