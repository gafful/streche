# Streche


# Issues
- Running android tests from gutter says No task available


# Entities
- Question

# Next
Networking
koin
compose
Tests

Logging: napier, kermit


sst - single source of truth is repo? implemented app-wide?
db - class
api - interface


don't use tokens, they are deleted after 2 hours
max of 50 per call
get categories
choose difficulty
generate url: https://opentdb.com/api.php?amount=50&category=21









.
     - Grt  -Itf per entity - APIs/DBs per service not entity
view - vm - repo(eventModel -> eventRepo) - net/db
     - sh - shar - shared
T    - T  - T    - Mock
MA   -    - 

decisions should be made inside model - not viewmodel
btn model and repo, if model, repo is left with data access and retrieval nkoaa - good to me
model should have more info to know what to do, right? repo may not have some stuff
once model will be shared too, repo doesn't have much advantage
well, if business logic is model, then so be it, let everyone else be dumb
cos before u know it many others may have a mind of their own too

if it is so, then repo may not have to decide between api/db o, just let

use interfaces and prefer them to mocking! https://android.googlesource.com/platform/frameworks/support/+/androidx-main/docs/do_not_mock.md


vm - ui logic
commonMain and iosMain should deal with their apps only where there is specific code
and they call the commonMain
so 3 tests
androidApp
androidMain (viewModel)
commonMain (this is should take care of 50+% code/logic[data,repo<business-logic>]) separate datasource from logic!
iosApp
iosMain


so if viewModel is logic for view, then entityNameModel is logic for entity
this logic for entity contains the data from the repository and manips it
it doesn't concern itself with datasources? sounds good to me ...
has no state
only has access to repo and transforms it to diff objects for the viewModel


common - Database
androidShared - Database 


+
serialization
koin
compose

read https://tonisives.com/blog/2021/06/01/kotlin-multiplatform-app-architectures/ again

official kmm sample app, final branch

https://github.com/jarroyoesp/KotlinMultiplatform_MVVM says we can share viewModel o, ... can't test now tho
https://tonisives.com/blog/2021/06/01/kotlin-multiplatform-app-architectures/ too says it cannot be used
https://www.marcogomiero.com/posts/2020/kmm-shared-app-architecture/ agrees with jarroyoesp

kampkit: good. only left out repository... which kmm-prod-sample seems to have.

kmm-production-sample: can't tell how scaleable, barely uses any lib, still a good ref

Most platforms demonstrated
https://github.com/joreilly/PeopleInSpace - 1.4k
koin
compose
Dependencies.kt
repository
not much test

D-KMP: Declarative KMM, mmm,... wanna stick to our mvvimp things, no flutter-declarative things here.
https://github.com/dbaroncelli/D-KMP-sample - 412

Redux
https://github.com/dreipol/multiplatform-redux-sample - 66

