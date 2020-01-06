package com.github.stephenott.workflowlinter.linter

import com.github.stephenott.workflowlinter.controller.Helpers
import org.camunda.bpm.model.xml.validation.ValidationResultType
import java.util.*

data class ValidationResults(
    val results: Map<String, List<ValidationResult>>
)

data class ValidationResult(
    val type: ValidationResultType,
    val elementId: String,
    val elementType: String,
    val message: String,
    val code: Int
) {
    constructor(validationResult: org.camunda.bpm.model.xml.validation.ValidationResult) :
            this(
                validationResult.type,
                Helpers.getElementWithNameAttribute(validationResult.element).getAttributeValue("id") ?: UUID.randomUUID().toString(),
                validationResult.element.elementType.instanceType.typeName,
                validationResult.message,
                validationResult.code)

}