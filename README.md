# Moto-Navigator
This is a navigation based ongoing project. It contains different module.

## Moto-Navigator App
Moto Navigator App is a navigation app available for Android. It uses your phone’s internet connection (4G/3G/2G/EDGE or Wi-Fi, as available) and GPS connection to track your present location and show you the navigation path to the destination via WALK/CYCLE/CAR routes. It uses API from mapbox. You can also save the paths for offline later access.

## Why use Moto Navigator App

- **NO FEES:** Moto Navigator App uses your phone’s internet connection (4G/3G/2G/EDGE or Wi-Fi, as available) and GPS connection to track and show you the navigation path. You don’t have to pay for getting the path info.
- **OFFLINE ACCESS:** At anytime you can access your saved paths offline.

## Intended User

Moto Navigator App is customized for travellers.

## Features

- Get present location of the user.
- Get routes to destination via WALK/CYCLE/CAR paths.
- Can see the saved paths anytime.
- Get the saved paths link directly in user's home screen through widget.

## User Interface Mocks

Splash Screen | Login Screen | Map Screen1
:-:|:-:|:-:
![splash screen](https://cloud.githubusercontent.com/assets/13851773/21486618/7af27da6-cbdd-11e6-994a-95278597fb39.png) | ![login screen](https://cloud.githubusercontent.com/assets/13851773/21486616/7a8a8372-cbdd-11e6-8b08-09b759a596e9.png) | ![mapscreen1](https://cloud.githubusercontent.com/assets/13851773/21486613/7a890c0e-cbdd-11e6-92fe-55483c54105e.png)

Map Screen2 | Map Screen3 | Navigation Screen Portrait
:-:|:-:|:-:
![map screen2](https://cloud.githubusercontent.com/assets/13851773/21486615/7a8a7b5c-cbdd-11e6-80c1-dec5c33419a0.png) | ![mapscreen3](https://cloud.githubusercontent.com/assets/13851773/21486617/7a8c6d54-cbdd-11e6-94d5-8d214a93d955.png) | ![navigation screen portrait](https://cloud.githubusercontent.com/assets/13851773/21486621/7af3fb36-cbdd-11e6-9292-e6c9781fe082.png)

Navigation Screen Landscape | Widget Screen | Info Screen | Widget Screen
:-:|:-:|:-:|:-:
![navigation screen landscape](https://cloud.githubusercontent.com/assets/13851773/21486620/7af32986-cbdd-11e6-813a-d8213c6d29ca.png) | ![drive screen](https://cloud.githubusercontent.com/assets/13851773/21486614/7a8a5bae-cbdd-11e6-9f8d-bc55ede81911.png) | ![auto screen](https://cloud.githubusercontent.com/assets/13851773/21486612/7a87bb74-cbdd-11e6-9757-5ab99cd20164.png) | ![widget screen](https://cloud.githubusercontent.com/assets/13851773/21486619/7af2e246-cbdd-11e6-9949-ba1e43f77cae.png)

## Technologies used

- Mapbox SDK
- Firebase
- Google Play Services

## Development Setup
1. Go to the project repo and click the `Fork` button
2. Clone your forked repository : `git clone git@github.com:your_name/moto-navigator.git`
3. Open the project with Android Studio

# How to build

All dependencies are defined in ```app/build.gradle```.
Import the project in Android Studio or use Gradle in command line:

```
./gradlew assembleDebug
```

The result apk file will be placed in ```app/build/outputs/apk/```.

## Project License
```
Copyright (C) 2017 Anupam Das

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
## Other open source Licenses

### Android source code
```
Copyright (C) 2011 The Android Open Source Project
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```