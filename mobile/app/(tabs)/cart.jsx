import { ActivityIndicator, Alert, Dimensions, Modal, Platform, Pressable, SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import React, { useEffect } from 'react';
import { Row, Table } from 'react-native-table-component';
import { useAuth } from '@/hooks/AuthProvider';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCart, setCart, cartCheckout, setCheckoutOrderSuccess } from '@/data/redux/actions/cartActions';
import { router } from 'expo-router';

export default function CartScreen() {

  const auth = useAuth();

  const { 
    cartItems, cartItemsInProgress, cartCheckoutOrder, cartItemsError,
  } = useSelector(state => ({
    cartItems: state.cart.data,
    cartItemsInProgress: state.cart.isFetching || state.cart.isAdding || state.cart.isCheckingOut,
    cartCheckoutOrder: state.cart.checkoutOrder,
    cartItemsError: state.cart.error,
  }));

  const dispatch = useDispatch();

  useEffect(() => {
    if (auth.isLoggedIn) {
      dispatch(fetchCart());
    }
  }, [dispatch, auth.isLoggedIn]);

  const removeAll = () => {
    dispatch(setCart([]));
  }

  const doCheckout = () => {
    dispatch(cartCheckout());
  }

  const ackCheckoutSuccess = (to) => {
    dispatch(setCheckoutOrderSuccess(null));
    router.navigate(to);
  }

  //console.log("CartScreen() cartItems size",cartItems.length);

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
      {
        auth.isLoggedIn ? (
          cartCheckoutOrder ? (
            <Modal
              animationType="slide"
              transparent={true}
              visible={cartCheckoutOrder !== null}
              onRequestClose={() => {
                //Alert.alert('Modal has been closed.');
                ackCheckoutSuccess();
              }}
            >
                {/*<Pressable
                  style={[styles.button, styles.buttonClose]}
                  onPress={() => setModalVisible(!modalVisible)}>
                  <Text>Hide Modal</Text>
                </Pressable>*/}
                <View 
                  style={{
                    flex: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                    marginTop: 22,
                    //borderColor: 'white',
                    //borderWidth: 1,
                  }}
                >
                  <View 
                    style={{
                      margin: 20,
                      backgroundColor: 'white',
                      borderRadius: 20,
                      paddingLeft: 35,
                      paddingRight: 35,
                      paddingTop: 25,
                      paddingBottom: 15,
                      alignItems: 'center',
                      shadowColor: '#000',
                      shadowOffset: {
                        width: 0,
                        height: 2,
                      },
                      shadowOpacity: 0.25,
                      shadowRadius: 4,
                      elevation: 5,
                      //borderColor: 'green',
                      //borderWidth: 5,
                    }}
                    testID='cart-checkout-success-modal-body'
                  >
                    <Text 
                      style={{
                        fontSize: 16,
                        lineHeight: 21,
                        fontWeight: 'bold',
                        letterSpacing: 0.25,
                        color: 'black',
                        textAlign: 'center',
                        paddingBottom: 25
                      }}
                    > 
                      Thank you for your Order!{"\n"}{"\n"}
                      Your order confirmation number is {cartCheckoutOrder.id}
                    </Text>
                    <View style={{ flexDirection: 'row', gap: 25 }}>
                      <Pressable
                        style={{
                          borderRadius: 20,
                          padding: 12,
                          elevation: 2,
                          backgroundColor: '#0077B6',
                        }}
                        onPress={() => ackCheckoutSuccess("/")}
                        testID='cart-checkout-success-modal-back-to-shopping-button'
                      >
                        <Text style={{color: 'white'}}>Back to Shopping</Text>
                      </Pressable>
                      <Pressable
                        style={{
                          borderRadius: 20,
                          padding: 12,
                          elevation: 2,
                          backgroundColor: '#0077B6',
                        }}
                        onPress={() => ackCheckoutSuccess("/orders")}
                        testID='cart-checkout-success-modal-view-orders-button'
                      >
                        <Text style={{color: 'white'}}>View Order</Text>
                      </Pressable>
                    </View>
                  </View>
                </View>
            </Modal>
          ) : (
            cartItemsInProgress ? 
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
            ) : 
            (
              cartItems.length == 0 ? 
              (
                <Text 
                  style={{
                    fontSize: 16,
                    lineHeight: 21,
                    fontWeight: 'bold',
                    letterSpacing: 0.25,
                    color: 'white',
                  }}
                  testID='cart-no-items-message'
                >
                  Nothing in cart
                </Text>
              ) :
              (
              <>
                <View style={{
                    paddingTop: 15,
                    paddingBottom: 15,
                    flexDirection: 'row',
                    gap: 10,
                    justifyContent: 'space-between'
                    //flex: 1,
                    //borderColor: 'green',
                    //borderWidth: 1,  
                  }}
                >
                  <Pressable 
                    style={{
                      alignItems: 'center',
                      //justifyContent: 'center',
                      paddingVertical: 12,
                      paddingHorizontal: 32,
                      borderRadius: 4,
                      elevation: 3,
                      //backgroundColor: '#2196F3',
                      //backgroundColor: '#023E8A',
                      backgroundColor: '#0077B6',
                    }} 
                    onPress={removeAll}
                    testID='cart-remove-all-button'
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
                      Remove All
                    </Text>
                  </Pressable>
                  <Pressable 
                    style={{
                      alignItems: 'center',
                      //justifyContent: 'center',
                      paddingVertical: 12,
                      paddingHorizontal: 32,
                      borderRadius: 4,
                      elevation: 3,
                      //backgroundColor: '#2196F3',
                      //backgroundColor: '#023E8A',
                      backgroundColor: '#0077B6',
                    }} 
                    onPress={doCheckout}
                    testID='cart-checkout-button'
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
                      Checkout
                    </Text>
                  </Pressable>
                </View>
                <View 
                  style={{
                    //paddingTop: 25,
                    //borderColor: 'white',
                    //borderWidth: 1,  
                  }}
                  testID='cart-table'
                >
                    <Table borderStyle={{ borderWidth: 1, borderColor: '#6e8189' }}>
                        <Row
                            data={['Name', 'Quantity', 'Price', 'Total']}
                            widthArr={[140, 110, 90, 85]}
                            style={styles.head}
                            textStyle={styles.headText}
                        />
                    </Table>
                    <ScrollView 
                      style={{
                        //borderColor: 'white',
                        //borderWidth: 1,  
                      }}
                    >
                        <Table borderStyle={{ borderWidth: 1, borderColor: '#6e8189' }}>
                            {cartItems.map((ci, i) => (
                              //<View testID={`cart-item-${i}`}>
                                <Row
                                  key={i}
                                  data={[`${ci.catalogItem.name}`, 1, 
                                    `${ci.catalogItem.price / 100}`, `${ci.catalogItem.price / 100}`]}
                                  widthArr={[140, 110, 90, 85]}
                                  style={styles.rowSection}
                                  textStyle={styles.text}
                                />
                              //</View>
                            ))}
                        </Table>
                    </ScrollView>
                </View>
              </>
              )
            )
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

const styles = StyleSheet.create({
  container: { flex: 1, padding: 16, paddingTop: 30, backgroundColor: '#fff' },
  rowSection: { height: 60, backgroundColor: '#121212' },
  head: { height: 44, backgroundColor: '#121212' },
  headText: { fontSize: 20, fontWeight: 'bold' , textAlign: 'center', color: '#6e8189' },
  text: { margin: 6, fontSize: 16, fontWeight: 'bold' , textAlign: 'center',  color: "#6e8189"},
});
