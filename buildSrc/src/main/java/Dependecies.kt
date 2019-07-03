object Config {
    val android_minSdk = 21
    val android_targetSdk = 28
    val android_compileSdk = 28
    val android_appVersionCode = 100        // XYZ; X = Major, Y = Minor, Z = Patch
    val android_appVersionName = "1.0.0"    // X.Y.Z; X = Major, Y = Minor, Z = Patch
}

object Versions {
    val gradle = "3.4.0"

    val kotlin_stdlib = "1.3.31"
    val kotlin_coroutines = "1.1.1"

    val androidx_appCompat = "1.0.2"
    val androidx_coreKtx = "1.0.1"
    val androidx_constraintLayout = "1.1.3"
    val androidx_lifecycle = "2.0.0"
    val androidx_navigation = "2.0.0"

    val test_junit = "4.12"
    val test_androidx_testRunner = "1.1.1"
    val test_androidx_espressoCore = "3.1.1"

    val google_materialDesign = "1.1.0-alpha04"
    val google_services = "4.0.2"
    val google_playServicesAuth = "16.0.1"

    val firebase_core = "16.0.7"
    val firebase_auth = "16.2.0"

    val logging_timber = "4.7.1"
}

object Deps {
    val tools_gradleAndroid = "com.android.tools.build:gradle:${Versions.gradle}"
    val tools_gradleKotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_stdlib}"
    val tools_androidx_navigationSafeArgsGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidx_navigation}"
    val tools_google_servicesGradlePlugin = "com.google.gms:google-services:${Versions.google_services}"

    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_stdlib}"
    val kotlin_coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
    val kotlin_coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

    val androidx_appCompat = "androidx.appcompat:appcompat:${Versions.androidx_appCompat}"
    val androidx_coreKtx = "androidx.core:core-ktx:${Versions.androidx_coreKtx}"
    val androidx_constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraintLayout}"
    val androidx_lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidx_lifecycle}"
    val androidx_lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.androidx_lifecycle}"
    val androidx_navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.androidx_navigation}"
    val androidx_navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.androidx_navigation}"

    val test_junit = "junit:junit:${Versions.test_junit}"
    val test_androidx_testRunner = "androidx.test:runner:${Versions.test_androidx_testRunner}"
    val test_androidx_espressoCore = "androidx.test.espresso:espresso-core:${Versions.test_androidx_espressoCore}"

    val google_materialDesign = "com.google.android.material:material:${Versions.google_materialDesign}"
    val google_playServicesAuth = "com.google.android.gms:play-services-auth:${Versions.google_playServicesAuth}"

    val firebase_core = "com.google.firebase:firebase-core:${Versions.firebase_core}"
    val firebase_auth = "com.google.firebase:firebase-auth:${Versions.firebase_auth}"

    val logging_timber = "com.jakewharton.timber:timber:${Versions.logging_timber}"
}