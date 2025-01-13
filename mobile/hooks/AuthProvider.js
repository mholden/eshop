import React, { useContext, useState } from "react";
import { Alert } from "react-native";
//import { authorize, revoke, logout as _logout } from "react-native-app-auth";
import AuthService from "../services/AuthService";
import identityServiceAPI, { IDENTITY_API_BASE_URL } from "../data/api/identityServiceAPI";
import * as WebBrowser from 'expo-web-browser';
import { makeRedirectUri, ResponseType, useAuthRequest, useAutoDiscovery } from 'expo-auth-session';
import * as AuthSession from 'expo-auth-session';
import { usePostHog } from "posthog-react-native";

const defaultAuthState = {
    isLoggedIn: false,
    isLoggingIn: false,
    accessToken: '',
    accessTokenExpirationDate: '',
    refreshToken: '',
    userInfo: {},
};

const defaultAuthContext = {
  ...defaultAuthState,
  signInRedirect : () => null,
  logout : () => null,
};

const authConfig = {
  issuer: IDENTITY_API_BASE_URL,
  clientId: "spring-cloud-gateway-client",
  //redirectUrl: 'my-app:/oauthredirect',
  //redirectUrl: 'myapp://redirect',
  redirectUrl: 'myapp',
  scopes: ['openid', 'profile'],
  //dangerouslyAllowInsecureHttpRequests: true, // for allowing http on android
  iosPrefersEphemeralSession: true,
  //additionalParameters: { prompt: 'login' },
};

WebBrowser.maybeCompleteAuthSession();

const AuthContext = React.createContext(defaultAuthContext);

export function useAuth() {
  return useContext(AuthContext);
}

export const AuthProvider = ({
  children
}) => {
  const [authState, setAuthState] = useState(defaultAuthState);
  const discovery = useAutoDiscovery(authConfig.issuer);
  const posthog = usePostHog();

  const [request, response, promptAsync] = useAuthRequest(
    {
      clientId: authConfig.clientId,
      redirectUri: makeRedirectUri({
        scheme: authConfig.redirectUrl,
        path: '(tabs)/account'
      }),
      //redirectUri: authConfig.redirectUrl,
      scopes: authConfig.scopes
    },
    discovery
  );

  console.log("AuthProvider() isLoggedIn",authState.isLoggedIn,"isLoggingIn",authState.isLoggingIn);
  console.log("AuthProvider() request",request,"response",response);

  const handleLoginResponse = (response) => {
    if (response.type == "success") {
      const exchangeFn = async (exchangeTokenReq) => {
        try {
          console.log("AuthProvider() calling exchangeCodeAsync");
          const exchangeTokenResponse = await AuthSession.exchangeCodeAsync(
            exchangeTokenReq,
            discovery
          );
          console.log("AuthProvider() exchangeTokenResponse",exchangeTokenResponse);
          const newAuthState = exchangeTokenResponse;
          await AuthService.setAuthToken(newAuthState.accessToken);
          console.log("AuthProvider() calling getUserInfo()");
          const userInfoResponse = await identityServiceAPI.getUserInfo();
          console.log("AuthProvider() userInfoResponse is", userInfoResponse);
          newAuthState.userInfo = userInfoResponse.data;
          newAuthState.isLoggedIn = true;
          newAuthState.isLoggingIn = false;
          setAuthState({
            ...newAuthState
          });
          console.log("doing posthog identify with username",newAuthState.userInfo.preferred_username,"email",newAuthState.userInfo.email);
          posthog.identify(newAuthState.userInfo.preferred_username, { email: newAuthState.userInfo.email });
        } catch (error) {
          console.error(error);
        }
      };
      exchangeFn({
        clientId: authConfig.clientId,
        code: response.params.code,
        redirectUri: makeRedirectUri({
          scheme: authConfig.redirectUrl,
          path: '(tabs)/account'
        }),
        extraParams: {
          code_verifier: request.codeVerifier,
        },
      });
    } 
  }

  if (authState.isLoggingIn && response) {
    handleLoginResponse(response);
    authState.isLoggingIn = false;
    setAuthState({
      ...authState,
    });
  }

  async function signInRedirect() {
    console.log("signInRedirect() START");
    await promptAsync({
      preferEphemeralSession: true,
      //showInRecents: true
    });
    authState.isLoggingIn = true;
    setAuthState({
      ...authState,
    });
    console.log("signInRedirect() DONE response is",response);
  }

  /*
  async function signInRedirect() {
    try {
      const newAuthState = await authorize({
        ...authConfig,
        connectionTimeoutSeconds: 5,
      });
      //console.log("newAuthState",newAuthState);
      await AuthService.setAuthToken(newAuthState.accessToken);
      const userInfoResponse = await identityServiceAPI.getUserInfo();
      newAuthState.userInfo = userInfoResponse.data;
      setAuthState({
        isLoggedIn: true,
        ...newAuthState,
      });
    } catch (error) {
      Alert.alert('Failed to log in', error.message);
    }
  }
  */

  async function logout() {
    console.log("logout() calling revoke");
    const revokeResponse = await AuthSession.revokeAsync(
      {
        clientId: authConfig.clientId,
        token: authState.refreshToken,
      },
      discovery
    );
    console.log("logout() revokeResponse",revokeResponse);
    if (revokeResponse) {
      await AuthService.removeAuthToken();
      setAuthState({
        ...defaultAuthState
      });
      posthog.reset();
    }
    /*await revoke(authConfig, {
      tokenToRevoke: authState.accessToken
    });
    await _logout(authConfig, {
      idToken: authState.idToken,
      postLogoutRedirectUrl: 'com.hldn.awesomeproject.auth:/callback',
    });
    await AuthService.removeAuthToken();
    setAuthState(defaultAuthState);*/
  }

  return (
      <AuthContext.Provider value={{
        ...authState,
        signInRedirect,
        logout
      }}>
          {children}
      </AuthContext.Provider>
  );
};