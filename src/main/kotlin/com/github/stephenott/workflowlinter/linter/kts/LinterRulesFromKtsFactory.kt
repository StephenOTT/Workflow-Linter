package com.github.stephenott.workflowlinter.linter.kts

import com.github.stephenott.workflowlinter.linter.elementValidator
import io.zeebe.model.bpmn.instance.BaseElement
import org.camunda.bpm.model.xml.instance.ModelElementInstance
import org.camunda.bpm.model.xml.validation.ModelElementValidator
import javax.inject.Singleton

class LinterRulesFromKtsFactory{

}


interface LinterRulesFromKts {
    fun validators(): List<ModelElementValidator<out ModelElementInstance>>
}

@Singleton
class LinterServerRulesFromKts : LinterRulesFromKts {

    private val validatorsList = listOf(
        elementValidator<BaseElement> { element, validatorResultCollector ->
            validatorResultCollector.addWarning(0, "my linter warning")
        })

    override fun validators(): List<ModelElementValidator<out ModelElementInstance>> {
        return validatorsList
    }
}