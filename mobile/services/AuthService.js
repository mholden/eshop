import AsyncStorage from "@react-native-async-storage/async-storage";

//
// TODO: dont' use async storage - it's not secure
// try this: https://docs.expo.dev/guides/authentication/#storing-data
//
export default class AuthService {
    static async getAuthToken() {
        let authToken = null;
        try {
            authToken = await AsyncStorage.getItem('authToken');
        } catch (e) {
            console.log("getAuthToken() error",e)
        }
        return authToken;
    }

    static async setAuthToken(authToken) {
        try {
            await AsyncStorage.setItem('authToken', authToken);
        } catch (e) {
            console.log("setAuthToken() error",e)
        }
    }

    static async removeAuthToken() {
        try {
            await AsyncStorage.removeItem('authToken');
        } catch (e) {
            console.log("removeAuthToken() error",e)
        }
    }
}