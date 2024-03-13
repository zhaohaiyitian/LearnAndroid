package com.jack.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension


class MyCustomPlugin:Plugin<Project> {
    override fun apply(p: Project) {
        val extension = p.extensions.getByType(AppExtension::class.java)
        extension.registerTransform(MyCustomTransform())
        println("plugin开发")
    }
}