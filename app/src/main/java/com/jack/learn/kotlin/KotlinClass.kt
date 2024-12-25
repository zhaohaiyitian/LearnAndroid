package com.jack.learn.kotlin

class KotlinClass {

    fun nullableStringLength(str: String?): Int? {
        return str?.length
    }

    fun greet(name: String = "World") {
        println("Hello, $name!")
    }


    fun String.addSuffix(suffix: String): String {
        return this + suffix
    }
}