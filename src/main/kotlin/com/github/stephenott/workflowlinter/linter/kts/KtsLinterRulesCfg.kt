package com.github.stephenott.workflowlinter.linter.kts

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Requires
import io.micronaut.core.convert.ConversionContext
import io.micronaut.core.convert.TypeConverter
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.inject.Singleton

@ConfigurationProperties("linter.kts")
@Context
class LinterRulesFromKtsCgf(){
    var rules: List<Path> = listOf()
}

@Singleton
class PathTypeConverter: TypeConverter<String, Path> {
    override fun convert(`object`: String, targetType: Class<Path>, context: ConversionContext): Optional<Path> {
        return Optional.of(Paths.get(`object`))
    }
}