Zemoga App Android Test

![alt text](https://miro.medium.com/max/1400/1*wOmAHDN_zKZJns9YDjtrMw.jpeg)

When starting the test app the first time, they load all the posts from the API, then the information is stored locally and with that information all the requested requirements are developed.

The test app has a main screen, in which two navigation options are shown: all posts and favorite posts, this by using the BottomNavigationView component.

The "All Posts" screen has a list of all the posts requested from the https://jsonplaceholder.typicode.com/posts api, each record has three options, one is to mark it as a favorite post, the other is to delete it and finally navigate to your detail.

The "favorite posts" screen has a list of all the favorite posts, each record has two options, one is to remove the favorite post mark, the other is to delete the record and finally navigate to its detail.

The "all posts" screen has a menu at the top with two options: delete all posts and add a post.

Both the screen in favorites and all posts, can be reloaded using swiperRefresh.

Architecture

The project is developed using Clean Architecture because following a good architecture helps in many ways, such as reducing development time, easy understanding, low maintenance, etc.

Writing clean, quality code for applications requires effort and experience. An app must not only meet UI and UX requirements, but also be easy to understand, flexible, testable, and maintainable.

Why Clean Architecture?

1. Separation of Concerns — Separation of code in different modules or sections with specific responsibilities making it easier for maintenance and further modification.
2. Loose coupling — flexible code anything can be easily be changed without changing the system
3. Easily Testable

Layers of Clean Architecture

The structure of the project has been divided into three layers:

Domain contains the application logic and business logic. In it we have domain classes, and also components that will have the logic such as Use Cases or Interactors. This module should NOT depend on the Android framework or third party dependencies. This module can be a Kotlin module.
Presentation is made up of the elements that allow us to display information to our user, receive data from it or from sensors. In addition to Android's own visual components (Activity and Fragment) and its UI system (XML files or Compose functions), if the MVVM pattern was implemented, with their respective ViewModels. This module is an Android module.
Data includes all network dependencies, in this case Retrofit, and data persistence through Room. In this layer, the repository pattern is used, which contains the implementation of the repositories and the data sources (data sources). This module is an Android module as well.

Third-party library

Dagger Hilt

Hilt is a dependency injection library for Android that allows you to reduce the repetitive work of manually injecting dependencies into your project.

Hilt provides a standard way to use dependency injection in your app by providing wrappers for each Android class in your project and managing their life cycles automatically. Hilt is based on the popular Dagger dependency injection library and benefits from the compile-time correctness, runtime performance, scalability, and Android Studio support it provides.

Retrofit

Retrofit is a type-safe REST client for Android, it provides a powerful framework for authenticating and interacting with APIs and sending network requests with OkHttp.

This library makes downloading JSON or XML data from a web API pretty easy. Once the data is downloaded, it is parsed into a data class that must be defined for each "resource" in the response.


Architecture Components Android

ViewModel

It allows you to store and manage UI-related data in a lifecycle-optimized way. The ViewModel class allows data to be preserved after configuration changes, such as screen rotations.

Advantages of ViewModel Component:

Helps in data management during configuration changes
Reduce UI bugs and crashes
Best practice for software design

LiveData

LiveData is an observable data container class. Unlike a regular observable, LiveData is lifecycle-optimized, which means that it honors the lifecycle of other app components, such as activities, fragments, or services. This optimization ensures that LiveData only updates app component observers that have an active lifecycle state.

Advantages of LiveData component:

UI is updated as per the appropriate change in the data
It removes the stopped or destroyed activities which reduce the chance of app crash
No memory leaks as LiveData is a lifecycle-aware component.

Navigation Component

They allow users to navigate through, in and out of the different pieces of content in the app. Android Jetpack's Navigation component allows you to implement navigation, from simple button clicks to more complex patterns, such as app bars and navigation side panels. The Navigation component also ensures a consistent and predictable user experience.

Advantages of Navigation Component:

Ease the transition through animated visualization
Supports deep linking
Handle fragment transactions
Support common as well as a complex navigation pattern

Room

Room's persistence library provides an abstraction layer for SQLite that enables seamless database access while harnessing the full power of SQLite.

Advantages of Room Component:

Reduce boilerplate code
Simplifies database access mechanism
Easy to implement migrations
Test-ability is high

Lifecycle-Aware Components

Details of the lifecycle state of an android component are stored in the Lifecycle class and it permits other components/objects to observe this state.

Advantages of Lifecycle-Aware Components:

Helps in creating organized application components
Ease in testing and maintenance of components
Less code requirement to executes tasks

View binding

View binding is a feature that allows you to more easily write code that interacts with views. Once view binding is enabled on a module, it generates a binding class for each XML layout file present in that module. An instance of a binding class contains direct references to all views that have a corresponding ID in the layout.

Advantages of view Binding Component:

Null safety: Since view binding creates direct references to views, there's no risk of a null pointer exception due to an invalid view ID. ...
Type safety: The fields in each binding class have types matching the views they reference in the XML file.

Coroutines

A coroutine is a concurrency design pattern that you can use in Android to simplify code that runs asynchronously.

Advantages of coroutines:

They are light-weight
Built-in cancellation support
Lower chances for memory leaks
Jetpack libraries provide coroutines support


