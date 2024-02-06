# Offline Chat App

An Android application that showcases offline chat through the usage of RoomDB along with Jetpack Compose UI.

## Features

The Android app lets you:
- User Authentication - Register & Login using username and password.
- Chat History, where you can see all your conversations with other registered users.
- Messaging, where you can chat with the other registered users along with media (video & image) support.
- Echo Last Message, where the last message sent by the user will be echoed from the recipient end.
- Offline Support

## DB Schema

![dbSchema.png](https://github.com/avishekdas128/offline-chat-app/assets/43132866/f9d5ac80-0d6b-4967-8af0-cb9493cb93ed)

## Screenshots

[<img src="https://github.com/avishekdas128/offline-chat-app/assets/43132866/02f11b15-547d-4720-97f6-92de4625e743" align="left"
width="180"
hspace="1" vspace="1">](ss1.png)
[<img src="https://github.com/avishekdas128/offline-chat-app/assets/43132866/e5fca432-0b14-4fcc-9b6f-a60ba5013a0e" align="center"
width="180"
hspace="1" vspace="1">](ss2.png)
[<img src="https://github.com/avishekdas128/offline-chat-app/assets/43132866/02b29270-3efe-46f3-9d76-d0aace9a2565" align="left"
width="180"
hspace="1" vspace="1">](ss3.png)
[<img src="https://github.com/avishekdas128/offline-chat-app/assets/43132866/d8077f30-154f-4609-b20a-5375288cf481" align="left"
width="180"
hspace="1" vspace="1">](ss4.png)


## Tech stack
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Dagger-Hilt for dependency injection.
- JetPack
    - State - Notify domain layer data to views.
    - SharedFlows - Notify one-time events to the UI layer.
    - Lifecycle - Dispose of observing data when the lifecycle state changes.
    - ViewModel - UI-related data holder, lifecycle aware.
    - [Compose](https://developer.android.com/jetpack/compose) - Render UI.
- Architecture
    - MVVM Architecture (Model - View - ViewModel)
    - Repository pattern
    - data ~ domain ~ presentation
- [Glide - Compose](https://bumptech.github.io/glide/int/compose.html) - Loading Images for Jetpack Compose.
- [ExoPlayer](https://github.com/google/ExoPlayer) - Loading Video Playback
- [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) - For handling shared preferences
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room) - For offline caching of data
