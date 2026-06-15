# Star Wars Explorer
In a galaxy far far away.. I got a fun challenge to build an app from scratch with vanilla Android tools which is very different from my day-to-day work, which was hard but also refreshing. The app is not complete and there's quite a few gaps (see Known Issues / TODOs), but I've done what I could with limited time and think it's a great conversation starter.

My focus was mostly on the structure of the codebase and how it would scale with new features. Inspiration was taken from the official Android documentation. 

You can follow the journey of development through the commits. It gives an understanding of how I approached the assignment. Note: don't mind the size and quality of the commits, this is not reflective of regular day-to-day work. 

## Architecture Choice
I've opted for a standard MVVM (Model-View-ViewModel) structure, which is the default for Android development today. My focus was:
*   Clear separation of concerns: Each layer has a specific responsibility.
*   Scalability: Modular design allows for easy expansion.
*   Testability: Implementation details are abstracted, making unit testing straightforward.
*   Readability: Easy to navigate and understand.

### Data Layer
*   `StarWarsRepository`: A single repository manages all data fetching and caching. Note: Given the scope for the assignment, a single repository seemed engouh. In a larger application, I would split it into specific repositories (e.g., `CharacterRepository`, `FilmsRepository`, `PlanetRepository`). 
*   `StarWarsEndpoint`: The models are based on the JSON schemas I found with the API.

### Domain Layer
The domain layer utilizes Use Cases to orchestrate data from the repository, tailored to the specific needs of each screen.
*   Reactive Updates: Use cases return a `Flow`, allowing for incremental UI updates. For example, the UI can immediately display character names and later update with film details as they are fetched. This approach makes loading much faster.
*   Primary Use Cases:
    *   `FetchCharacterPageUseCase`: Combines character data with their respective films.
    *   `FetchCharacterDetailsUseCase`: Retrieves character details along with their homeworld (Planet) data.

### Presentation Layer
The UI is built using an MVVM pattern:
*   Navigation: Implemented via `NavHost` within `MainActivity`.
*   Each feature / screen has a Unidirectional Data Flow: e.g. View (`CharacterListScreen`) <--> ViewModel (`CharacterListViewModel`) <--> Model (`CharacterListUiState`)

## Known Issues / TODOs
*   Error Handling is very basic should be improved by:
    *   Custom Error object for different cases like no network, timeout, api errors. 
    *   User-friendly specific error messages in the shape of an empty state, dialogs, Snackbar.
    *   Offer ways to retry the request (e.g. retry button in Snackbar, dialog or empty state).
    *   Implementing automatic recovery for network failures (e.g. respond to connectivity changes).
*   Loading Handling is very basic, should differentiate based on what is loading. (e.g. initial page, next page, character page or metadata like films / homeworld)
*   Search Feature: Not finished, currently displays dummy data and requires integration with the remote search endpoint.
*   No Localization (hardcoded strings)
*   CharacterListViewModel: we currently assume that the order of pages is always correct in the UiState, it would be better to manage the order of pages / characters based on the nextPageUrl.
*   FetchCharacterPageUseCaseImpl:
    *   We currently loop the film data requests one by one, it would be much faster to parallelize it.
    *   The intermittent updates can be improved by adding some debounce and emitting the films in a batch to prevent jumping text.
*   No tests have been implemented, just left some TODOs.

## Third Party Libraries
*   Dagger / Hilt (Dependency Injection)
*   Kotlinx Serialization (JSON Parsing)
*   Retrofit (Networking)