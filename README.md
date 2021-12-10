# iTunesMusicSearch
A simple app that uses iTunes API to search for Music Tracks.

https://user-images.githubusercontent.com/66074842/145562735-2de5ecc6-ad0c-436a-ab8c-5d903ad44296.mp4

## About Architecture and Code
- Uses Flow and it's variant everywhere, no dependency on Livedata, making tests easy to read and understand
- MVVM
- Test Driven Development (TDD)
- SOLID Principles
- KISS and DRY
- Unit Tests
- End To End Test
- Material.io for UI Design

## Libraries Used
* Kotlin
  - Coroutines
  - Flow
* JetPack/Arch Libraries
  - Navigation
  - DataBinding
  - Room
* Dependency Injection
  - Hilt
* Third Party
  - Material Design Components
  - Retrofit and gson
  - Glide
* Tests
  - uses `runTest` from `kotlinx-coroutines-test:1.6.0-RC`
  - Junit
  - Mockito
  - Mockwebserver by okhttp3
  - Espresso
  - Other Required Android/Kotlin Libraries like kotlinx-coroutines-test, fragment-testing etc.

### More Info
see [scratchpad.txt](scratchpad.txt)
