## GitU - Github User's Information application
GitU is an application that built by Kotlin Programming Language and Android Studio as a place to search and read Github User's Information (Username, name, follower and following). 

- ### Features
  - #### Theme Settings : Allowed user to change theme of the application (Light and Dark mode)
  - #### Favorite List : Allowed user to save specific other github user's data as their's favorite
  - #### SearchBar : Allowed user to find user's specific data based on their username
 
- ### Technologies used
  - #### MVVM (Model-View-ViewModel) architecture pattern
  - #### ViewPager2 -> Allowed user to change page by simply swipe them (Follower and Following page)
  - #### Room -> on-device database to stored user's favorite list locally
  - #### Datastore -> Stored user's data preferences
  - #### Retrofit -> Data Networking
  - #### [Glide](https://github.com/bumptech/glide) -> Online Image loader dependency
 
- ### How to get started
  - #### Get your own official [Github API Key](https://docs.github.com/en/free-pro-team@latest/rest)
  - #### Add your own Github API Key to `  local.properties  `
    ````
    GITHUB_API_KEY=YOUR_OWN_API_KEY
    ````
    
- ### Preview
  <img alt="Main Screen" src="https://github.com/fidelcavell/GitU-app/assets/132121865/906ea2a8-0a7b-4612-b830-7607a72e3d97" width="24%" />
  <img alt="Detail Screen" src="https://github.com/fidelcavell/GitU-app/assets/132121865/069286ba-0af7-43a9-bca7-c5cbd42859b0" width="24%" />
  <img alt="Favorite List Screen" src="https://github.com/fidelcavell/GitU-app/assets/132121865/88a379cb-0ee2-47ed-b77d-a57d75fbf939" width="24%" />
  <img alt="Theme Settings Screen" src="https://github.com/fidelcavell/GitU-app/assets/132121865/eb1f116f-440e-4c52-b42c-89a30a5e75c0" width="24%" />
