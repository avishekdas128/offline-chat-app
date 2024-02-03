# Offline Chat App

An Android application that showcases offline chat through the usage of RoomDB along with Jetpack Compose UI.

## Features

The Android app lets you:
- User Authentication - Register & Login using username and password.
- Chat History, where you can see all your conversations with other registered users.
- Messaging, where you can chat with the other registered users along with media (video & image) support.
- Echo Last Message, where the last message sent by the user will be echoed from the recipient end.
- Offline Support

## Screenshots


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
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room) - For offline caching of data
