import { DarkTheme, DefaultTheme, ThemeProvider } from '@react-navigation/native';
import { useFonts } from 'expo-font';
import { Stack } from 'expo-router';
import * as SplashScreen from 'expo-splash-screen';
import { useEffect } from 'react';
import 'react-native-reanimated';
import { AuthProvider } from '@/hooks/AuthProvider'
import store from '@/data/redux/store';
import { Provider } from 'react-redux';
import AsyncChannelWrapper from '@/data/api/AsyncChannelWrapper';
import { PostHogProvider } from 'posthog-react-native'

import { useColorScheme } from '@/hooks/useColorScheme';

// Prevent the splash screen from auto-hiding before asset loading is complete.
SplashScreen.preventAutoHideAsync();

export default function RootLayout() {
  const colorScheme = useColorScheme();
  const [loaded] = useFonts({
    SpaceMono: require('../assets/fonts/SpaceMono-Regular.ttf'),
  });

  useEffect(() => {
    if (loaded) {
      SplashScreen.hideAsync();
    }
  }, [loaded]);

  if (!loaded) {
    return null;
  }

  return (
    <Provider store={store}>
      {/*<ThemeProvider value={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>*/}
      <ThemeProvider value={DarkTheme}>
        <PostHogProvider 
          apiKey={"phc_HFRqZBvNP1mbB249aF3LEPfQquwlBW9WHfwzsS5kT0e"} 
          options={{
            host: 'https://us.i.posthog.com', 
            enableSessionReplay: true,
            sessionReplayConfig: { // https://posthog.com/docs/session-replay/_snippets/react-native-installation
              maskAllTextInputs: false,
              maskAllImages: false,
              maskAllSandboxedViews: false,
            },
          }}
        > 
          <AuthProvider>
            <AsyncChannelWrapper/>
            <Stack>
              <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
              <Stack.Screen name="+not-found" />
            </Stack>
          </AuthProvider>
        </PostHogProvider>
      </ThemeProvider>
    </Provider>
  );
}
