package com.github.stephenott.workflowlinter.linter.kts

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Requires
import io.micronaut.core.convert.ConversionContext
import io.micronaut.core.convert.TypeConverter
import org.jetbrains.kotlin.utils.ifEmpty
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.inject.Singleton
import kotlin.streams.toList

@ConfigurationProperties("linter.kts")
@Context
class LinterRulesFromKtsCgf(){
    var folder: Path = Paths.get("./rules")
    var rules: List<Path> = getRulesPathsFromRulesFolder(folder)

    private fun getRulesPathsFromRulesFolder(folderPath: Path): List<Path> {
        val extension = "kts"
        return if (!Files.exists(folderPath)){
            println("No folder exists at $folderPath for linter rules.")
            listOf()
        } else {
            val rulesPaths = Files.walk(folderPath)
                    .filter { Files.isReadable(it) }
                    .filter { it.toFile().extension == extension }.toList()

            if (rulesPaths.isEmpty()){
                println("No rules were found in $folderPath with .kts extensions.")
            }
            rulesPaths
        }
    }
}

@Singleton
class PathTypeConverter: TypeConverter<String, Path> {
    override fun convert(`object`: String, targetType: Class<Path>, context: ConversionContext): Optional<Path> {
        return Optional.of(Paths.get(`object`))
    }
}