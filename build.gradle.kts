buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.0")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.5.21")//1.5.30 throws Cannot have abstract method KotlinJavaToolchain.getJdk(). on build
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}