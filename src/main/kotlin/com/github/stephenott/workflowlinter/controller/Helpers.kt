package com.github.stephenott.workflowlinter.controller

import com.github.stephenott.workflowlinter.linter.ValidationResult
import com.github.stephenott.workflowlinter.linter.WorkflowLinter
import com.github.stephenott.workflowlinter.linter.kts.LinterServerRulesFromKts
import io.reactivex.Flowable
import io.reactivex.Single
import io.zeebe.model.bpmn.instance.*
import org.camunda.bpm.model.xml.instance.ModelElementInstance
import org.camunda.bpm.model.xml.validation.ValidationResults
import java.io.InputStream
import java.util.*
import kotlin.reflect.full.isSubclassOf

object Helpers {
    fun lint(workflow: InputStream, rules: LinterServerRulesFromKts): ValidationResults {
        return WorkflowLinter(workflow).lintWithValidators(rules.validators())
    }

    fun processLintResults(results: ValidationResults): com.github.stephenott.workflowlinter.linter.ValidationResults {
        return com.github.stephenott.workflowlinter.linter.ValidationResults(
                results.results
                        .flatMap { it.value }
                        .map {
                            ValidationResult(it)
                        }
                        .groupBy({ it.elementId }, { it })
        )
    }

    tailrec fun getElementWithNameAttribute(instance: ModelElementInstance): ModelElementInstance =
            if ((instance.elementType.instanceType.kotlin in
                            listOf(Process::class,
                                    Collaboration::class,
                                    Message::class,
                                    MessageFlow::class) ||
                            (instance.elementType.instanceType.kotlin.isSubclassOf(FlowElement::class)
                                    || instance.elementType.instanceType.kotlin.isSubclassOf(Artifact::class))
                            )
                    && instance.getAttributeValue("id") != null) instance
            else getElementWithNameAttribute(instance.parentElement)
}


