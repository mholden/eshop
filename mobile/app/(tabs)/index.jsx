import { Image, StyleSheet, Platform, View, Pressable, SafeAreaView, ScrollView, Text, ActivityIndicator, Dimensions } from 'react-native';
import { Link } from 'expo-router';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCatalogItems } from '@/data/redux/actions/catalogItemsActions';

export default function HomeScreen() {

  const { 
    catalogItems, catalogItemsAreLoading, catalogItemsError,
  } = useSelector(state => ({
    catalogItems: state.catalogItems.data,
    catalogItemsAreLoading: state.catalogItems.isFetching,
    catalogItemsError: state.catalogItems.error,
  }));

  const dispatch = useDispatch();
  
  useEffect(() => {
    dispatch(fetchCatalogItems(0, 8)); // hardcoding this for now
  }, [dispatch]);

  return (
    <>
      <SafeAreaView
        style={{
          paddingTop: Platform.OS === "android" ? 40 : 0,
          flex: 1,
          backgroundColor: "#6e8189",
          //borderColor: 'white',
          //borderWidth: 1,
        }}
      >
        <ScrollView 
          style={{
            backgroundColor: "#121212",
            //borderColor: 'white',
            //borderWidth: 1,
          }}
        >
          {catalogItemsAreLoading ? 
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
            <View
              style={{
                flexDirection: "row",
                alignItems: "center",
                flexWrap: "wrap",
                //borderColor: 'white',
                //borderWidth: 1,
              }}
            >
              {catalogItems.map((item, index) => (
                <View key={index} testID={`cat-item-${index}-link`}>
                  <Link 
                    href={`(tabs)/catalog/item/${item.id}`}
                    //href="(tabs)/catalogitem"
                    asChild
                  >
                    <Pressable
                      style={{
                        marginVertical: 10,
                        flexDirection: "column",
                        alignItems: "center",
                        width: 400, 
                        height: 220, 
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
                        source={{ uri: `data:image/png;base64,${item.imageData}` }}
                      />
                      <Text style={{ color: "#6e8189", padding: 0, fontSize: 12, fontWeight: "bold" }}>
                        {item.name}
                      </Text>
                    </Pressable>
                  </Link>
                </View>
              ))}
            </View>
          )}
        </ScrollView>
      </SafeAreaView>
    </>
  );
}

const styles = StyleSheet.create({});
