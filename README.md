# Show's challenge

The application was developed using MVVM with Clean Architecture, separated into four layers: **Data, Domain, Presentation and DI (dependency injection)**


  ## How to run:
  There is a jobsity.apk at root folder, just download it and install at your device.


<img src="/CA-MVVM.png" alt="MVVM with Clean Architecture"/>

  ## Layer Date:
  Responsible for deciding which source the data should be retrieved from (the application uses the network and the SharedPreferences for the favorite list).

  ## Domain layer:
  Contains the use cases of the application and is responsible for any future business rules to be implemented, contains the interfaces which the Data layer implements

  ## Presentation layer:
  Responsible for how the data is presented on the mobile screen

  ## DI layer:
  Responsible for the injection of the dependencies, using Hilt for this project.

## Used Libraries:
  - <a href="https://github.com/mockk/mockk" target="_blank">MockK</a>
  - <a href="https://github.com/bumptech/glide" target="_blank">Glide</a>
  - <a href="https://github.com/airbnb/lottie-android" target="_blank">Lottie Airbnb</a>
  - <a href="https://developer.android.com/guide/navigation" target="_blank">Navigation</a>
  - <a href="https://developer.android.com/training/dependency-injection/hilt-android" target="_blank">Hilt</a>
  - <a href="https://github.com/square/okhttp" target="_blank">OkHttp</a>
  - <a href="https://developer.android.com/kotlin/coroutines" target="_blank">Coroutines</a>
  - <a href="https://github.com/square/retrofit" target="_blank">Retrofit</a>
  - <a href="https://github.com/google/gson" target="_blank">Gson</a>
  - <a href="https://developer.android.com/jetpack/androidx/releases/appcompat" target="_blank">App Compat</a>


## TODO list:

  - Insert Pin code authentication
  - Insert FingerPrint authentication
  - Insert Person searcher
  - Insert Instrumented tests

## Disclaimer:

  - The unit tests at this project is far from 100% unit tests coverage, it's just showing an example on how to make unit tests using MockK
