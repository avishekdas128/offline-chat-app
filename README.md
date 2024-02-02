# Offline Chat App

An android application that showcases offline chat through usage of RoomDB along with Jetpack Compose UI.

## Features

The android app lets you:
- Users Authentication - Register & Login using username and password.
- Chat History, where you can see all your conversation with other registered users.
- Messaging, where you can chat the other registered user along with media (video & image) support.
- Echo Last Message, where the last message sent by the user will be echoed from the reciepients end.
- Offline Support

## Screenshots


## Tech stack
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Dagger-Hilt for dependency injection.
- JetPack
    - State - Notify domain layer data to views.
    - SharedFlows - Notify one time events to the UI layer.
    - Lifecycle - Dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - [Compose](https://developer.android.com/jetpack/compose) - Render UI.
- Architecture
    - MVVM Architecture (Model - View - ViewModel)
    - Repository pattern
    - data ~ domain ~ presentation
- [Coil](https://coil-kt.github.io/coil/compose/) - Loading Images for Jetpack Compose.
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room) - For offline caching of data
