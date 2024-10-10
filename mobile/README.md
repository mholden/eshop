# PROJECT SET UP

cd hldn_eshop
npx create-expo-app@latest mobile
cd mobile

https://docs.expo.dev/get-started/set-up-your-environment/ 

local build set up:
npx expo install expo-dev-client

local build:
npx expo run:ios
npx expo run:android

eas build set up:
npm install -g eas-cli # shouldn’t need to do this every time now that i’ve done it once and it’s global
eas login 
eas build:configure
do this for ios: https://docs.expo.dev/get-started/set-up-your-environment/?platform=ios&device=simulated&mode=development-build#adjust-your-build-profile 

eas build:
eas build —platform all —profile development

eas install:
-go to expo.dev, find the build, download the apk, drag and drop the apk to the ios simulator / android emulator to install it
-then npx expo start


# TESTING

for testing, build with release configurations:
npx expo run:ios --configuration Release
npx expo run:android --variant release

the release flag will prevent the expo dev menu / warnings / errors from interfering with automated tests

test:
cd mobile && maestro test tests/