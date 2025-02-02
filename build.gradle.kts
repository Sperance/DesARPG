// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral() // ObjectBox доступен через Maven Central
    }
    dependencies {
        classpath(libs.gradle) // Версия AGP
        classpath(libs.objectbox.gradle.plugin) // Плагин ObjectBox
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}