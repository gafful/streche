import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

version = "1.0"

val sqlDelight = "1.5.0"
val okhttp = "4.9.0"
val ktor = "1.6.3"
val koin = "3.0.2"
val androidXtest = "1.3.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        else -> ::iosX64
    }

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")//TODO Extract
                implementation("com.squareup.sqldelight:runtime:$sqlDelight")
                implementation("com.squareup.sqldelight:coroutines-extensions:$sqlDelight")
                implementation("io.ktor:ktor-client-core:$ktor")
                implementation("io.ktor:ktor-client-json:$ktor")
                implementation("io.ktor:ktor-client-logging:$ktor")
                implementation("io.ktor:ktor-client-serialization:$ktor")
                implementation("io.insert-koin:koin-core:$koin")
                api("co.touchlab:kermit:0.1.9")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                //implementation(kotlin("app.cash.turbine:turbine:0.5.2"))
                implementation("io.ktor:ktor-client-mock:$ktor")
            }
        }

        sourceSets.matching { it.name.endsWith("Test") }
            .configureEach {
                languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
            }

        val androidMain by getting {
            dependencies {
                implementation("com.squareup.okhttp3:okhttp:$okhttp")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelight")
                //implementation("io.ktor:ktor-client-android:$ktor")
                implementation("io.ktor:ktor-client-okhttp:$ktor")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:core:$androidXtest")
                implementation("androidx.test.ext:junit:1.1.3")
                implementation("androidx.test:runner:$androidXtest")
                implementation("androidx.test:rules:$androidXtest")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
                implementation("org.robolectric:robolectric:4.6.1")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqlDelight")
                implementation("io.ktor:ktor-client-ios:$ktor")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}

//android {
//    configurations {
//        create("androidTestApi")
//        create("androidTestDebugApi")
//        create("androidTestReleaseApi")
//        create("testApi")
//        create("testDebugApi")
//        create("testReleaseApi")
//    }
//}

sqldelight {
    database("AppDatabase") {
        packageName = "com.gafful.streche"
    }
}