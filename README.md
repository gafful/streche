# Streche
- Encourage knowledge sharing
- Encourage documentation
- Encourage learning
- We make learning fun

# Features
- Create question
- Ideas: https://dev.to/amyoulton/build-a-trivia-game-using-a-free-api-w-javascript-kmn
- Add riddles.... search riddles API
- Review existing mobile apps

# ToDo
- Source for questions from credible sources: Ghana History twitter, organisations, ...

# Issues

# Next
compose
splash-setup-trivia
business logic w/ tests
- get categories
- save selected categor(ies) = db/prefs
- initdb with selection
- fetch question
- record score

# Question
Does UNIQUE make a column indexed?
answered_on TEXT date
-- Index on public_id? or the fields I'll be searching by. eg: where text = ...?
-- store a hash of the question
-- or index the question: could be useful for searching later on.
How does sealed work
return flow from ktor?
stateflow vs livedata


# Trivia APIs
opentdb.com
-------------
don't use tokens, they are deleted after 2 hours
max of 50 per call
get categories
choose difficulty
generate url: https://opentdb.com/api.php?amount=50&category=21

https://trivia.willfry.co.uk/example
------------------------------------
has incorrect answers
4,614 approved questions, 12,132 pending questions.
needs an API key to review

https://jservice.io/
--------------------
156K trivia
no wrong answers
too american/european!
{"id":144712,"answer":"Romeo and Juliet",
"question":"Shakespeare wrote about these 2, \"Never was a story of more woe\"",
"value":200,"airdate":"2012-10-30T12:00:00.000Z","created_at":"2015-01-22T02:31:38.881Z",
"updated_at":"2015-01-22T02:31:38.881Z","category_id":16605,"game_id":4002,"invalid_count":null,
"category":{"id":16605,"title":"literary lovers","created_at":"2014-02-14T02:50:19.408Z",
"updated_at":"2014-02-14T02:50:19.408Z","clues_count":11}}

https://api-ninjas.com/pricing
------------------------------
50Kpm
No wrong answers
can suggest new APIs to be added

rapidapi.com trivia APIs || https://english.api.rakuten.net/twinword/api/word-quiz
--------------------------
https://rapidapi.com/divad12/api/numbers-1/
free

https://rapidapi.com/twinword/api/word-quiz/pricing
10kpm for free

https://rapidapi.com/webknox/api/trivia-knowledge-facts/pricing
5perday for free

https://fungenerators.com/api/trivia/#apidoc
---------------------------------------------
1kpm for free
no wrong answers
api key required

https://www.triviacafe.com/trivia-questions/geography
-----------------------------------------------------
No API? web only?

https://www.newseum.org/newsmania/
----------------------------------
American museum

http://daydetails.com/events.php?month=3&day=4&year=2005
---------------------------------------------------------
Enter a date and get info of various kinds

https://github.com/jgoralcz/trivia
----------------------------------
Fixed 5.2k trivia
how do we add more?
mostly US

https://quizapi.io/
-------------------
Software/programming related
this can't be fun? can it? leave for them nerds tho, they may find it fun or educative!


https://trivia.fyi/list/
https://aws.amazon.com/blogs/compute/building-a-serverless-multiplayer-game-that-scales/
https://www.twinword.com/api/word-quiz.php
https://quizbash.com/
https://www.sporcle.com/
https://www.funtrivia.com/
continue from page 3 of "trivia api" google search



# Technical
Logging: napier, kermit
sst - single source of truth is repo? implemented app-wide?
db - class
api - interface

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

# Database primary key
https://www.cybertec-postgresql.com/en/uuid-serial-or-identity-columns-for-postgresql-auto-generated-primary-keys/
sequences are more efficient than uuids
uuid - 128-bit number
maximum for integer, which is 2147483647
With bigint, you are certain to never exceed the maximum of 9223372036854775807: even if you insert 
10000 rows per second without any pause, you have almost 30 million years before you reach the limit.

Should I use bigint or uuid for an auto-generated primary key?
My advice is to use a sequence unless you use database sharding or have some other reason to 
generate primary keys in a “decentralized” fashion (outside a single database).

The advantages of bigint are clear:

    bigint uses only eight bytes, while uuid uses 16
    fetching a value from a sequence is cheaper than calculating a UUID

https://stackoverflow.com/questions/59572749/using-uuid-for-primary-key-using-room-with-android
If centralized, use int, or long,
