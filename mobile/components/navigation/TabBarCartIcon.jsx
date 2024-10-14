import React, { useEffect } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { TabBarIcon } from '@/components/navigation/TabBarIcon';
import { useAuth } from '@/hooks/AuthProvider';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCart } from '@/data/redux/actions/cartActions';

export function TabBarCartIcon({ color, focused }) {

  const auth = useAuth();

  const { 
    cartItems, cartItemsAreLoading, cartItemsError,
  } = useSelector(state => ({
    cartItems: state.cart.data,
    cartItemsAreLoading: state.cart.isFetching || state.cart.isAdding,
    cartItemsError: state.cart.error,
  }));

  const dispatch = useDispatch();

  useEffect(() => {
    if (auth.isLoggedIn) {
      dispatch(fetchCart());
    }
  }, [dispatch]);

  return (
      <View 
        style={{
          //borderColor: 'green',
          //borderWidth: 1,  
        }}
      >
        <TabBarIcon 
          name={focused ? 'cart' : 'cart-outline'} 
          color={color}
          style={{
            //borderColor: 'white',
            //borderWidth: 1,  
          }}
        />
        {
          auth.isLoggedIn && 
          (
            <View 
              style={{
                position: 'absolute', 
                height: 20,
                width: 20,
                //top: 50, 
                left: 7, 
                //right: 10, 
                bottom: 17, 
                justifyContent: 'center', 
                alignItems: 'center',
                //borderColor: 'white',
                //borderWidth: 1,  
              }}
            >
            {
              cartItemsAreLoading ? (
                <ActivityIndicator size="small" /> 
              ) : (
                <Text 
                  style={{ color: "white", fontSize: 12}} 
                  testID='cart-tab-number'
                >
                  {cartItems.length}
                </Text>
              )
            }
            </View>
          )
        }
      </View>
  );
}