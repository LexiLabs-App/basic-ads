# CocoaPods to Swift Package Manager Conversion: Downstream Consumption Lifecycle

### Overview of the Consumption Lifecycle

When an iOS developer integrates `basic-ads` into their Kotlin Multiplatform (KMP) project and supplies their own Swift Package Manager (SPM) dependencies in Xcode, the architecture functions through a **compile-time decoupled, link-time unified model**. 

Because `basic-ads` is distributed as a multiplatform library producing `.klib` artifacts rather than bundled binary frameworks, it provides symbol declarations without packaging the underlying Google binaries. This yields maximum systemic efficiency: it prevents binary bloat, avoids duplicate symbol collisions, and allows the consuming application full control over dependency versioning.

---

### Phase-by-Phase Integration Mechanics

```
┌─────────────────────────────────────────────────────────────┐
│                      Consuming Project                      │
├──────────────────────────────┬──────────────────────────────┤
│      Kotlin / KMP Layer      │       Native iOS / Xcode     │
│                              │                              │
│  commonMain / iosMain        │  Host iOS Application Target │
│  implementation(basic-ads)   │  + SPM: GoogleMobileAds      │
│              │               │  + SPM: UserMessagingPlatform│
│              ▼               │              │               │
│  Produces iOS Framework /    │              │               │
│  Binary with external symbol │              │               │
│  references (e.g., GADAdSize)│              │               │
└──────────────┬───────────────┴──────────────┬───────────────┘
               │                              │
               └──────────────┬───────────────┘
                              ▼
               ┌──────────────────────────────┐
               │         Xcode Linker         │
               │   Binds Kotlin symbols with  │
               │   SPM binary implementations │
               └──────────────┬───────────────┘
                              ▼
               ┌──────────────────────────────┐
               │    Unified Running App       │
               └──────────────────────────────┘
```

#### 1. Gradle Dependency Resolution (Kotlin Layer)
* **What happens:** The consumer adds `implementation("app.lexilabs.basic:basic-ads:<version>")` to their `build.gradle.kts`.
* **Outcome:** The Kotlin compiler downloads the published `.klib` metadata. The consumer's shared code (`commonMain`) and iOS code (`iosMain`) compile against the public API surface (`BannerAd`, `InterstitialAd`, `AdSize`, `Consent`, etc.).
* **Zero Overhead:** The consumer does **not** need `swiftPMDependencies` configured in their own Gradle build to consume `basic-ads`.

#### 2. Native Dependency Resolution (Xcode / SPM Layer)
* **What happens:** Following the library documentation, the iOS developer opens their Xcode workspace or `Package.swift` and imports the official Google packages (`swift-package-manager-google-mobile-ads` and `swift-package-manager-google-user-messaging-platform`).
* **Outcome:** Xcode pulls the official Google binaries/sources into the app's build graph and prepares them to be compiled and linked into the final iOS app executable.

#### 3. Compilation and Linking (Xcode Build Phase)
* **What happens:** When Xcode compiles the host app (or executes `embedAndSignAppleFrameworkForXcode`):
  1. The Kotlin/Native compiler produces the application's shared framework. Because `basic-ads` was compiled with cinterop bindings, the generated code contains external references to Objective-C symbols (such as `_OBJC_CLASS_$_GADBannerView` and `_OBJC_CLASS_$_UMPConsentInformation`).
  2. The Xcode linker (`ld64` / `lld`) links the Kotlin framework and the SPM packages together.
  3. The unresolved symbol references emitted by `basic-ads` match the symbols provided by the consumer's SPM packages.
* **Outcome:** The final Mach-O application binary is produced with all symbol addresses resolved.

#### 4. Runtime Execution
* **What happens:** When the app launches and calls `BasicAds.Initialize()` or renders a `BannerAd()`, the Kotlin runtime invokes the Objective-C runtime selectors.
* **Outcome:** The native Google Mobile Ads SDK initializes, requests ads, and renders them seamlessly inside UIKit views wrapped by Compose Multiplatform.

---

### Systemic Advantages of This Architecture

1. **Elimination of Duplicate Symbol Collisions:**
   If `basic-ads` had bundled the Google SDK binaries inside its published artifact, any consumer attempting to also import Google Mobile Ads in Xcode would suffer fatal linker errors (`duplicate symbol '_OBJC_CLASS_$_GADBannerView'`). By supplying only metadata and delegating binary linking to the consumer, overall ecosystem compatibility is maximized.

2. **Minimization of Binary Bloat:**
   The consumer's final app binary contains exactly one copy of the Google Mobile Ads SDK, keeping app bundle sizes minimal and conserving end-user device storage and bandwidth.

3. **Maintenance and Security Sovereignty:**
   The consuming team can independently patch, update, or pin their SPM Google SDK versions in Xcode (for zero-day vulnerability fixes or compliance updates) without waiting for a new release of `basic-ads`, provided the Objective-C API signatures remain backwards-compatible.

---

### Potential Failure Modes and Mitigations

| Scenario                                                 | Consequence                                                                                                                                                        | Optimal Mitigation                                                                                                                                       |
|:---------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Consumer omits SPM packages in Xcode**                 | The build fails during Xcode linking with: `Undefined symbols for architecture ...: "_OBJC_CLASS_$_GADBannerView"`                                                 | Enforced via `@DependsOnGoogleMobileAds` opt-in requirements and explicit onboarding documentation in `README.md`.                                       |
| **Major SDK version incompatibility**                    | If Google introduces breaking symbol renames across major versions, runtime selector errors or link failures occur.                                                | Maintaining clear version compatibility matrices in `VERSIONS.md` to ensure predictable builds across major releases.                                    |
| **Direct cinterop usage in consumer's Kotlin `iosMain`** | If the consumer writes custom Kotlin code in their own `iosMain` expecting direct access to `swiftPMImport...`, those packages are internal to the library's klib. | Consumers should use `basic-ads` common abstractions or configure their own local cinterop if direct access to native Google APIs in Kotlin is required. |

---

### Summary

The setup operates with complete harmony: `basic-ads` acts as a lightweight, type-safe API bridge, while the consumer's SPM configuration supplies the actual executable binary in Xcode. This cleanly separates interface from implementation, maximizing stability, performance, and developer control.
