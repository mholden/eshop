#!/bin/bash

REPO_DIR=~/devel/hldn_eshop
MOBILE_REPO_DIR=~/devel/mobile_tests/AwesomeProject

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

############################################################################################################
#################################### test back end and web #################################################
############################################################################################################

function testBackEndAndWeb() {
	cd "$REPO_DIR/backend/tools/test_eshop/TestEShop"

	echo "building back end tests..."
	mvn -DskipTests clean package

	echo "running back end tests..."
	java -jar target/test-eshop-0.0.1-SNAPSHOT.jar -q

	cd "$REPO_DIR/frontend"

	echo "starting front end..."
	BROWSER=none npm run start > /tmp/frontend.logs 2>&1 &

	sleepWithUpdate 10

	echo "running front end tests..."
	npx playwright test --reporter=dot

	echo "killing front end..."
	ps aux | grep -aE 'hldn_eshop.*frontend' | grep -v grep | awk '{print $2}' | xargs kill
}

############################################################################################################
######################################## test mobile #######################################################
############################################################################################################

function testIOS() {
	echo "starting ios..."
	npx expo run:ios > /tmp/ios.logs 2>&1 &
	PID=$!

	sleepWithUpdate 45

	echo "running mobile tests on ios..."
	maestro test tests/

	echo "killing ios (pid $PID)..."
	pkill -P $PID
	kill $PID
	xcrun simctl shutdown all # kill the ios simulator, too
}

function testAndroid() {
	echo "starting android..."
	npx expo run:android > /tmp/android.logs 2>&1 &
	PID=$!

	sleepWithUpdate 90

	echo "running mobile tests on android..."
	maestro test tests/

	echo "killing android (pid $PID)..."
	pkill -P $PID
	kill $PID
	adb emu kill # kill the android simulator, too
}

function testMobile() {
	cd $MOBILE_REPO_DIR

	# set ip in IP.js file
	IP=eshop.hldn.live
	sed -E -i '' "s|(IP = ).*|\1\"${IP}\"|g" data/api/IP.js

	testIOS
	testAndroid
}

testBackEndAndWeb
testMobile
