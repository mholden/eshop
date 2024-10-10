import { Alert, Platform, Pressable, SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import React from 'react';
import { useAuth } from '@/hooks/AuthProvider';

export default function AccountScreen() {

  const auth = useAuth();

  console.log("AccountScreen()");

  return (
    <SafeAreaView
      style={{
        paddingTop: Platform.OS === "android" ? 40 : 0,
        flex: 1,
        backgroundColor: "#6e8189",
      }}
    >
      <ScrollView 
        style={{
          backgroundColor: "#121212",
        }}
      >
        <View
          style={{
            flexDirection: "column",
            alignItems: "center",
            paddingTop: 30,
            //borderColor: 'white',
            //borderWidth: 1,  
          }}
        >
          <Pressable 
            style={{
              //alignItems: 'center',
              //justifyContent: 'center',
              paddingVertical: 12,
              paddingHorizontal: 32,
              borderRadius: 4,
              elevation: 3,
              //backgroundColor: '#2196F3',
              //backgroundColor: '#023E8A',
              backgroundColor: '#0077B6',
            }} 
            //onPress={() => Alert.alert('Log In Button pressed')}
            onPress={async () => {
              if (auth.isLoggedIn) {
                await auth.logout();
              } else {
                await auth.signInRedirect();
              }
            }}
            testID='login-logout-button'
          >
            {
              auth.isLoggedIn ? 
              (
                <Text 
                  style={{
                    fontSize: 16,
                    lineHeight: 21,
                    fontWeight: 'bold',
                    letterSpacing: 0.25,
                    color: 'white',
                  }}
                >
                  Log Out ({auth.userInfo.preferred_username})
                </Text>
              ) : (
                <Text 
                  style={{
                    fontSize: 16,
                    lineHeight: 21,
                    fontWeight: 'bold',
                    letterSpacing: 0.25,
                    color: 'white',
                  }}
                >
                  Log In
                </Text>
              )
            }
          </Pressable>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({});
