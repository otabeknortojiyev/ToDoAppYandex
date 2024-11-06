plugins {
    id("java-library")
    id("kotlin-kapt")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    //Gson
    implementation(libs.gson)

    //ROOM
    //implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)
}