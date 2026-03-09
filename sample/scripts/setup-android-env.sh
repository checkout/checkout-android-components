#!/bin/sh

# Download android cmd tools
ANDROID_SDK_ROOT=/tmp/android-sdk
mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools
mkdir /tmp/android-sdk-downloads
curl -sS https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip > android-cmd-tool.zip
unzip android-cmd-tool.zip -d ${ANDROID_SDK_ROOT}/cmdline-tools
mv ${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/latest
rm -rf /tmp/android-sdk-downloads

# Accept licenses
yes | /tmp/android-sdk/cmdline-tools/latest/bin/sdkmanager --sdk_root=/tmp/android-sdk --licenses

# setup environment variables
touch local.properties
echo "sdk.dir=$ANDROID_SDK_ROOT" >> local.properties
