package com.github.stephenott.workflowlinter.linter.kts

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Requires
import org.camunda.bpm.model.xml.instance.ModelElementInstance
import org.camunda.bpm.model.xml.validation.ModelElementValidator
import java.nio.file.Files
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Singleton
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

interface LinterRulesFromKts {
    fun validators(): List<ModelElementValidator<out ModelElementInstance>>
}

@Singleton
@Requires(beans = [LinterRulesFromKtsCgf::class])
@Context
class LinterServerRulesFromKts : LinterRulesFromKts {

    @Inject
    lateinit var ktsRulesList: LinterRulesFromKtsCgf

    private val scriptEngine: ScriptEngine = ScriptEngineManager().getEngineByExtension("kts")

//    private val validatorsList = listOf(
//        elementValidator<BaseElement> { element, validatorResultCollector ->
//            validatorResultCollector.addWarning(0, "my linter warning")
//        })

    private lateinit var validatorsList: List<ModelElementValidator<out ModelElementInstance>>

    override fun validators(): List<ModelElementValidator<out ModelElementInstance>> {
        return validatorsList
    }

    @PostConstruct
    private fun processKtsFileList() {
        val paths = ktsRulesList.rules
        println("number of kts: ${paths.size}")
        validatorsList =  paths.map { path ->
            val scriptReader = Files.newBufferedReader(path)
            println("running!!")
            val scriptResult = scriptEngine.eval("1+1")
            println(scriptResult::class)
            scriptResult as ModelElementValidator<out ModelElementInstance>
        }.toList()
    }
}