package io.dev.build.convention.ext

import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope

object DependencyHandlerScopeExt {

    fun DependencyHandlerScope.api(dependencyNotation: Any): Dependency? {
        return add("api", dependencyNotation)
    }

    fun DependencyHandlerScope.androidTestApi(dependencyNotation: Any): Dependency? {
        return add("androidTestApi", dependencyNotation)
    }

    fun DependencyHandlerScope.compileOnly(dependencyNotation: Any): Dependency? {
        return add("compileOnly", dependencyNotation)
    }

    fun DependencyHandlerScope.debugApi(dependencyNotation: Any): Dependency? {
        return add("debugApi", dependencyNotation)
    }

    fun DependencyHandlerScope.implementation(dependencyNotation: Any): Dependency? {
        return add("implementation", dependencyNotation)
    }

    fun DependencyHandlerScope.debugImplementation(dependencyNotation: Any): Dependency? {
        return add("testImplementation", dependencyNotation)
    }

    fun DependencyHandlerScope.kapt(dependencyNotation: Any): Dependency? {
        return add("kapt", dependencyNotation)
    }

    fun DependencyHandlerScope.ksp(dependencyNotation: Any): Dependency? {
        return add("ksp", dependencyNotation)
    }

    fun DependencyHandlerScope.testImplementation(dependencyNotation: Any): Dependency? {
        return add("testImplementation", dependencyNotation)
    }

    fun DependencyHandlerScope.androidTestImplementation(dependencyNotation: Any): Dependency? {
        return add("androidTestImplementation", dependencyNotation)
    }
}