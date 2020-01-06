package com.github.stephenott.workflowlinter.linter

import io.zeebe.model.bpmn.Bpmn
import io.zeebe.model.bpmn.BpmnModelInstance
import io.zeebe.model.bpmn.instance.BaseElement
import org.camunda.bpm.model.xml.validation.ModelElementValidator
import org.camunda.bpm.model.xml.validation.ValidationResults
import java.io.File
import java.io.InputStream

/**
 * Main Linter class used to create a Linter for a specific BPMN file/inputstream
 */
class WorkflowLinter(inputStream: InputStream) {

    constructor(file: File): this(file.inputStream())

    private val bpmmModelInstance: BpmnModelInstance = Bpmn.readModelFromStream(inputStream)

    fun lintWithValidators(validators: List<ModelElementValidator<*>>): ValidationResults{
        val result = bpmmModelInstance.validate(validators)
//        result.results.forEach { (model, results) ->
//            println("Element ---> ${model.elementType.typeName} ${(model.takeIf { it is BaseElement } as BaseElement).id}")
//            results.forEach { validationResult ->
//                println("""
//                    Type: ${validationResult.type}
//                    Code: ${validationResult.code}
//                    Element Type: ${model.elementType.typeName}
//                    Element Id: ${(model.takeIf { it is BaseElement } as BaseElement).id}
//                    Message: ${validationResult.message}
//                """.trimIndent())
//            }
//        }
        return result
    }
}