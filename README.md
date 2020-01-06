# Flavoury
[![Build Status](https://travis-ci.com/mirceabucerzan/flavoury-android.svg?branch=master)](https://travis-ci.com/mirceabucerzan/flavoury-android)

Flavoury is an Android application that will allow users to search and discover cooking recipes, while offering a personalized experience, based on dietary preferences and food intolerances. It offers the option to sign in using a Google account, in order to enable remote user data persistence.

<div>
  <img align="center" src="sign_in.png" alt="Sign In screenshot" height="320" width="160">
</div>

## Features
#### Completed
* Sign In
* Onboarding

#### Roadmap
* Recipe Search and Discovery
* Recipe Bookmarking
* App Settings

## Set Up
This project is dependent on Firebase and Google Sign In. For obvious reasons, the real Firebase configuration file (`google-services.json`) was not checked in.

[A dummy config file exists in the `mock/` source set in the `sign_in` feature module in order for CI builds to work.]

In order to get things working, you will need to:
1. [Create a Firebase project and associate the app with it](https://firebase.google.com/docs/android/setup);
2. [Configure a Google API Console project](https://developers.google.com/identity/sign-in/android/start-integrating#configure_a_project);
3. Add your Google Web application type client ID to `~/.gradle/gradle.properties` with the key `GOOGLE_OAUTH_2_WEB_CLIENT_ID` (or as an environment variable).

## Project Structure
Flavoury is written entirely in Kotlin and uses the Gradle build system. The app is currently made up of four modules, each belonging to one of three levels:
1. App module (`app`): links all features together, while orchestrating the navigation between them;
2. Feature modules (`sign_in`, `onboarding`): correspond with full-screen and coherent user facing functionality ("vertical slice");
3. Library modules (`core`): code shared across some or all features ("horizontal slice"); do not depend on any feature or app modules.

<div>
  <img align="center" src="project_structure.png" alt="Project structure image" height="300" width="200">
</div>

## Architecture
When deciding on what approach to take, I've followed the recommendations in the [Guide to app architecture](https://developer.android.com/jetpack/docs/guide). Making use of the [Android Architecture Components](https://developer.android.com/topic/libraries/architecture), I've kept logic away from Activities and Fragments and moved it to [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel).

Data was observed through [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), while using the [Data Binding Library](https://developer.android.com/topic/libraries/data-binding) to bind UI elements to the respective data sources.

Data operations are handled by the Repository layer. A thin domain layer rests between the presentation and data layers, which handles business logic off the Main (UI) thread, making use of [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) for asynchronous work. See `UseCase.kt` from the `core` module for details.

Navigation inside feature modules was handled using the [Navigation Component](https://developer.android.com/guide/navigation). Each feature is therefore single-Activity.

<div>
  <img align="center" src="clean_architecture.png" alt="Clean Architecture image" height="270" width="270">
</div>

## Work Management and Issue Tracking
A Kanban-style [Trello board](https://trello.com/b/PTm6Xphe/flavoury-app) was used for organizing all project work.

## UI/UX
Sketch, together with the [Material Theme Editor Plugin](https://material.io/resources/theme-editor/), were used for creating the UX flow and UI design for the app. All related work can be found in [the following repo](https://github.com/mirceabucerzan/flavoury-design).

## Contributing
Currently not open for contributions; this might change in the future, after the app is feature complete.

## License
[GNU AGPLv3](LICENSE.txt).
