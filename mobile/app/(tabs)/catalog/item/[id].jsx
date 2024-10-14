
import { router, useLocalSearchParams } from 'expo-router';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCatalogItem } from '@/data/redux/actions/catalogItemActions';
import { ActivityIndicator, Alert, Button, Dimensions, Image, Platform, Pressable, SafeAreaView, ScrollView, Text, View } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useAuth } from '@/hooks/AuthProvider';
import { addToCart } from '@/data/redux/actions/cartActions';

const CatalogItem = () => {
  const catalogItemId = useLocalSearchParams();
  const auth = useAuth();

  const { 
      catalogItem, catalogItemIsLoading, catalogItemError,
  } = useSelector(state => ({
      catalogItem: state.catalogItem.data,
      catalogItemIsLoading: state.catalogItem.isFetching,
      catalogItemError: state.catalogItem.error,
  }));

  const dispatch = useDispatch();

  useEffect(() => {
      dispatch(fetchCatalogItem(catalogItemId.id));
  }, [dispatch, catalogItemId.id]);

  const onAddToCart = async () => {
    if (!auth.isLoggedIn) {
      await auth.signInRedirect();
    } else {
      dispatch(addToCart(catalogItem));
    }
  };

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
        {catalogItemIsLoading ? 
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
            <>
            <View
              style={{
                //borderColor: 'white',
                //borderWidth: 1,  
              }}
            >
              <Pressable 
                style={{
                  //alignItems: 'center',
                  //justifyContent: 'center',
                  paddingVertical: 12,
                  //paddingHorizontal: 32,
                  borderRadius: 4,
                  elevation: 3,
                }} 
                //onPress={() => Alert.alert('Simple Button pressed')}
                onPress={() => router.back()}
                testID="cat-item-back-button"
              >
                <Ionicons name="chevron-back-outline" size={32} color="#6e8189" />
              </Pressable>
            </View>
            <View
              style={{
                flexDirection: "column",
                alignItems: "center",
                //paddingTop: 30,
                //borderColor: 'white',
                //borderWidth: 1,  
              }}
            >
              <Image
                  style={{ 
                    width: 200, 
                    height: 200, 
                    resizeMode: "contain", 
                    //borderColor: 'green',
                    //borderWidth: 1,  
                  }} 
                  //source={{ uri: item?.image }}
                  source={{ uri: `data:image/png;base64,${catalogItem.imageData}` }}
                />
              <Text style={{ color: "#6e8189", padding: 0, fontSize: 16, fontWeight: "bold" }}>
              {`
                ${catalogItem.name}
                $${catalogItem.price / 100} 
              `}
              </Text>
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
                onPress={onAddToCart}
                testID="cat-item-add-to-cart-button"
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
                Add To Cart
                </Text>
              </Pressable>
            </View>
          </>
        )}
      </ScrollView>
    </SafeAreaView>
  );
}

export default CatalogItem;
