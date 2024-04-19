import Keycloak from "keycloak-js";

// from: https://github.com/dasniko/keycloak-reactjs-demo/blob/main/src/services/UserService.js

const keycloakConfig = {
  url: "http://docker.for.mac.localhost:8090/auth",
  realm: "spring-cloud-gateway-realm",
  clientId: "spring-cloud-gateway-client",
};
  
const _kc = new Keycloak(keycloakConfig)

/**
 * Initializes Keycloak instance and calls the provided callback function if successfully authenticated.
 *
 * @param onAuthenticatedCallback
 */
const initKeycloak = (onAuthenticatedCallback) => {
  _kc.init({
    onLoad: 'check-sso',
  })
    .then((authenticated) => {
      if (!authenticated) {
        console.log("user is not authenticated..!");
      }
      onAuthenticatedCallback();
    })
    .catch(console.error);
};

const doLogin = _kc.login;

const doLogout = _kc.logout;

const getToken = () => _kc.token;

const getTokenParsed = () => _kc.tokenParsed;

const isLoggedIn = () => !!_kc.token;

const updateToken = (successCallback) =>
  _kc.updateToken(5)
    .then(successCallback)
    .catch(doLogin);

const getUsername = () => _kc.tokenParsed?.preferred_username;

const getUserInfo = () => _kc.userInfo;

const loadUserInfo = () => _kc.loadUserInfo();

const hasRole = (roles) => roles.some((role) => _kc.hasRealmRole(role));

const AuthService = {
  initKeycloak,
  doLogin,
  doLogout,
  isLoggedIn,
  getToken,
  getTokenParsed,
  updateToken,
  getUsername,
  getUserInfo,
  loadUserInfo,
  hasRole,
};

export default AuthService;