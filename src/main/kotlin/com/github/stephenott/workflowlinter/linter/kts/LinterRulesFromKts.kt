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

    private lateinit var validatorsList: List<ModelElementValidator<out ModelElementInstance>>

    override fun validators(): List<ModelElementValidator<out ModelElementInstance>> {
        return validatorsList
    }

    @PostConstruct
    private fun processKtsFileList() {
        //@TODO add try catch around- ./src/main/resources/rules/rule1.kts this to capture better error handling when scriptResult does not work
        val paths = ktsRulesList.rules
        validatorsList = paths.map { path ->
            val sm = ScriptEngineManager().getEngineByExtension("kts")
            val scriptReader = Files.newBufferedReader(path)
            val scriptResult = sm.eval(scriptReader)

            scriptReader.close()
            scriptResult as ModelElementValidator<out ModelElementInstance>
        }.toList()
    }
}