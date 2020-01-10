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

    private val scriptEngine: ScriptEngine = ScriptEngineManager().getEngineByExtension("kts")

    override fun validators(): List<ModelElementValidator<out ModelElementInstance>> {
        return validatorsList
    }

    @PostConstruct
    private fun processKtsFileList() {
        //@TODO add try catch around- ./src/main/resources/rules/rule1.kts this to capture better error handling when scriptResult does not work
        val paths = ktsRulesList.rules
        val list: MutableList<ModelElementValidator<out ModelElementInstance>> = mutableListOf()

        paths.forEach { path ->
            println("Generating linter rules from $path")
            val scriptReader = Files.newBufferedReader(path)
            val bindings = scriptEngine.createBindings()
            val scriptResult = scriptEngine.eval(scriptReader, bindings)
            scriptReader.close()

            try {
                val singleResult = scriptResult as ModelElementValidator<out ModelElementInstance>
                list.add(singleResult)
            } catch (e: Exception){
                val listResult = (scriptResult as List<*>)
                        .filterIsInstance<ModelElementValidator<out ModelElementInstance>>()
                list.addAll(listResult)
            } catch (e: Exception){
                throw IllegalStateException("script $path did not return a model element validator or list of model element validators.")
            }
        }
        validatorsList = list
    }
}