/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * A simple 'hello world' plugin.
 */
class CukehubPluginPlugin implements Plugin<Project> {
    void apply(Project project) {
        // Register a task
        project.tasks.register("greeting") {
            doLast {
                println("Hello from plugin 'org.example.greeting'")
            }
        }
    }
}
