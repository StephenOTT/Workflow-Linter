package com.github.stephenott.workflowlinter

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("com.github.stephenott.workflowlinter")
            .mainClass(Application.javaClass)
            .start()
    }
}
