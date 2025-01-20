#!/bin/bash

REPO_DIR=~/devel/hldn_eshop

function restartDocker() {
	echo "quitting docker desktop..."
	osascript -e 'quit app "Docker Desktop"'
	sleepWithUpdate 10
	echo "starting docker desktop..."
	open --background -a "Docker Desktop"
	sleepWithUpdate 10
}

function sleepWithUpdate() {
	TLEFT=$1
	echo -n "sleeping for ${TLEFT}..."
	while [ "$TLEFT" -gt 0 ] ; do
    sleep 1
    ((TLEFT--))
		echo -ne "\rsleeping for ${TLEFT}..."
  done
	echo -ne "\033[1K\r"
}

function testBackEnd() {
	cd "$REPO_DIR/backend/tools/test_eshop/TestEShop"

  echo "building back end tests..."
  mvn -DskipTests clean package

  echo "running back end tests..."
  java -jar target/test-eshop-0.0.1-SNAPSHOT.jar -q
}

function testWeb() {
	cd "$REPO_DIR/frontend"

	ENV=LOCAL
  sed -E -i '' "s|(env = ).*|\1\"${ENV}\";|g" src/utils/backEndServiceLocations.js

  echo "starting front end..."
  BROWSER=none npm run start > /tmp/frontend.logs 2>&1 &

  sleepWithUpdate 10

  echo "running front end tests..."
  npx playwright test --reporter=dot --workers 3

  echo "killing front end..."
  ps aux | grep -aE 'hldn_eshop.*frontend' | grep -v grep | awk '{print $2}' | xargs kill
}

function startBackEnd() {
	restartDocker

	cd $REPO_DIR

	frontend=$1
	case $frontend in
    "web")
			cp .env.local.web .env
      ;;
    "mobile")
			IP=`route get default | xargs -n1 ipconfig getifaddr`
			sed -E -i '' "s|(.*http://).*(:8090.*)|\1${IP}\2|g" .env.local.mobile
			cp .env.local.mobile .env
      ;;
  esac

  # start up the back end
  echo "starting the back end..."
  docker-compose up > /tmp/backend.logs 2>&1 &
	sleepWithUpdate 30
}

function stopBackEnd() {
	cd $REPO_DIR
	echo "stopping the back end..."
  docker-compose stop
}

############################################################################################################
#################################### test back end and web #################################################
############################################################################################################

function testBackEndAndWeb() {
	startBackEnd "web"
	testBackEnd
	testWeb
	stopBackEnd
}

############################################################################################################
######################################## test mobile #######################################################
############################################################################################################

MOBILE_SLEEP_TIME=60

function testIOS() {
  echo "starting ios..."
  npx expo run:ios --configuration Release > /tmp/ios.logs 2>&1 &
  PID=$!

  sleepWithUpdate ${MOBILE_SLEEP_TIME}

  echo "running mobile tests on ios..."
  maestro test tests/

  echo "killing ios (pid $PID)..."
  pkill -P $PID
  kill $PID
  xcrun simctl shutdown all # kill the ios simulator, too
}

function testAndroid() {
  echo "starting android..."
  npx expo run:android --variant release > /tmp/android.logs 2>&1 &
  PID=$!

  sleepWithUpdate ${MOBILE_SLEEP_TIME}

  echo "running mobile tests on android..."
  maestro test tests/

  echo "killing android (pid $PID)..."
  pkill -P $PID
  kill $PID
  adb emu kill # kill the android simulator, too
}

function testMobile() {
	startBackEnd "mobile"

	cd "$REPO_DIR/mobile"

	#rm -rf android/ ios/

  # kick off an eas build TODO: somehow check its success?
  #eas build --platform all --profile development --no-wait

  ENV=LOCAL
  IP=`route get default | xargs -n1 ipconfig getifaddr`
  sed -E -i '' "s|(env = ).*|\1\"${ENV}\";|g" data/api/backEndServiceLocations.js
  sed -E -i '' "s|(IP = ).*|\1\"${IP}\"|g" data/api/backEndServiceLocations.js

  testIOS
  testAndroid

	stopBackEnd
}

testBackEndAndWeb
testMobile
