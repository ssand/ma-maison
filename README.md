# Ma Maison

The project follows Clean Architecture with a Orbit-powered MVI presentation layer. It splits responsibilities into domain, data, utility, design system modules, and feature-specific presentation stacks to keep UI, business logic, and infrastructure isolated.

## Modules

- `app` – Android entry point, wires navigation and composes feature modules.
- `domain` – Business layer with models and repository contracts (pure Kotlin) No use cases implemented as those would have been just wrappers over Repository classes.
- `core:data` – Infrastructure: DTOs, mappers, Ktor-based API layer, repository implementations.
- `core:util` – Shared utilities and testing helpers (e.g., dispatcher rule).
- `core:designsystem` – Compose components, theming, shared UI resources.
- `core:commonres` – Common Android resources (strings, drawables).
- `presentation:properties` – Feature module for property list screens (Orbit MVI + Compose).
- `presentation:propertydetails` – Feature module for property details UI (Orbit MVI + Compose).

## Module Dependencies

- `app` depends on every feature (`presentation:*`), `domain`, and the `core:*` modules.
- `presentation:*` modules depend on `domain` plus shared `core:designsystem`, `core:util`, and `core:commonres`.
- `core:data` depends on `domain`, implements repository interfaces.
- `domain` has no project dependencies (only Kotlin + coroutines), making it portable and test-friendly.
- `core:designsystem`, `core:util`, and `core:commonres` are independent utility/resource providers consumed across presentation and app layers.

## Key Third-Party Libraries

- **Orbit MVI** – MVI State container.
- **Koin** – Dependency injection with annotation processing.
- **Ktor Client** – Networking stack.
- **Coil 3** – Image loading.

## Final notes

- In a multi-module environment, it's very important to rely on Gradle convention plugins to avoid maintenance issues of the Gradle files. This wasn’t included in the current scope.
- I assumed that all image URLs are valid if present, so I didn’t include placeholders for failed property images.
- I didn’t include a Toolbar, as it’s not particularly relevant in this context.
- Some resources from the `app` module could be moved to `commonres` during a further clean-up.
- I’m happy to discuss any other aspects of the implementation if needed.
