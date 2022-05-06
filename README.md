# issue3067
### What product is affected?
- Firebase Remote Config
### Description
- Issue 3067: [link](https://github.com/firebase/firebase-android-sdk/issues/3067)
### Prerequisite
- google-services.json
- [Charles proxy](https://www.charlesproxy.com/)
### Steps to reproduce issue
- Clone project
- [Setup](https://mdapp.medium.com/the-android-emulator-and-charles-proxy-a-love-story-595c23484e02) Charles proxy
- Link Charles proxy with Android emulator
  1. Menu bar > Proxy > Uncheck macOS Proxy
  2. Menu bar > Help > Local IP Address > Take note of your IP address
  3. Open Android emulator > More options menu (triple dot) > Settings > Manual proxy configuration > add noted IP address and port number 8888 > click Apply
- Start throttling with settings on [issue link](https://user-images.githubusercontent.com/25059578/138128159-9fd7f2df-318d-42aa-ad4c-dfdea22497d1.png)
- Run Android project (When re-running, issue is only encountered when fully stopping the app then running again)
- Check logs and see that fetch fails after 60 seconds
### Summary
- The set value for `setFetchTimeoutInSeconds` is being ignored, instead a default 60 seconds is being used.
