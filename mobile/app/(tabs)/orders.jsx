import { ActivityIndicator, Dimensions, Platform, Pressable, SafeAreaView, ScrollView, Text, View } from "react-native";
import { useAuth } from '@/hooks/AuthProvider';
import { useDispatch, useSelector } from "react-redux";
import { fetchOrders } from "@/data/redux/actions/ordersActions";
import { useEffect } from "react";

export default function OrdersScreen() {
    const auth = useAuth();

    const { 
      orders, ordersAreLoading, ordersError,
    } = useSelector(state => ({
        orders: state.orders.data,
        ordersAreLoading: state.orders.isFetching,
        ordersError: state.orders.error,
    }));
  
    const dispatch = useDispatch();
  
    useEffect(() => {
      if (auth.isLoggedIn) {
        dispatch(fetchOrders());
      }
    }, [dispatch, auth.isLoggedIn]);

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
            //borderColor: 'white',
            //borderWidth: 1,
          }}
        >
          {
            auth.isLoggedIn ? (
              ordersAreLoading ? 
              (
                <View
                  style={{
                    width: Dimensions.get('window').width,
                    height: Dimensions.get('window').height,
                    paddingTop: Dimensions.get('window').height / 3,
                    //borderColor: 'white',
                    //borderWidth: 1,
                  }}
                >
                  <ActivityIndicator size="large" /> 
                </View>
              ) : (
                <>
                  <View>
                    <Text 
                      style={{ 
                        color: "#6e8189", 
                        paddingTop: 25, 
                        paddingBottom: 15, 
                        fontSize: 18, 
                        fontWeight: "bold",
                        //borderColor: 'white',
                        //borderWidth: 1,
                      }}
                    >
                      Your Orders
                    </Text>
                  </View>
                  <View>
                    {
                      orders.map((o, i) => {
                        const date = new Date(o.creationTime);
                        //console.log(date.toLocaleDateString("en-US", {year: 'numeric', month: 'long', day: 'numeric'}));
                        return (
                          <Text 
                            key={o.id} 
                            style={{ 
                              color: "#6e8189", 
                              paddingTop: 15, 
                              paddingBottom: 15,
                              fontSize: 14,
                              //borderColor: 'white',
                              //borderWidth: 1,
                            }}
                            testID={`order-${i}`}
                          >
                            {date.toLocaleDateString("en-US", {year: 'numeric', month: 'long', day: 'numeric'})} (Order #{o.id}, {o.state})
                          </Text>
                        )
                      })
                    }
                  </View>
                </>
              )
            ) : ( // !auth.isLoggedIn
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
                    await auth.signInRedirect();
                  }}
                >
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
                </Pressable>
              </View>
            )
          }
        </ScrollView>
      </SafeAreaView>
  );
}
