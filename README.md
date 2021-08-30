# Show's challenge

The application was developed using MVVM with Clean Architecture, separated into four layers: **Data, Domain, Presentation and DI (dependency injection)**

<img src="/CA-MVVM.png" alt="MVVM with Clean Architecture"/>

  ## Layer Date:
  responsible for deciding which source the data should be retrieved from (the application uses the network and the SharedPreferences for the favorite list).

  ## Domain layer:
  contains the use cases of the application and is responsible for any future business rules to be implemented, contains the interfaces which the Data layer implements

  ## Presentation layer:
  responsible for how the data is presented on the mobile screen

  ## DI layer:
  responsible for the injection of the dependencies, using Hilt for this project.

## TODO list:

  - Insert Pin code authentication
  - Insert FingerPrint authentication
  - Insert Person searcher
  - Insert Instrumented tests

## Disclaimer:

  - The unit tests at this project is far from 100% unit tests coverage, it's just showing an example on how to make unit tests using MockK