package rules

import com.github.stephenott.workflowlinter.linter.*
import io.zeebe.model.bpmn.instance.ServiceTask

val requiredUserTaskHeaders = listOf(
        "title",
        "candidateGroups",
        "formKey"
)

val optionalUserTaskHeaders = listOf(
        "priority",
        "assignee",
        "candidateUsers",
        "dueDate",
        "description"
)

elementValidator<ServiceTask> { e, v ->

    e.getZeebeTaskDefinition()?.let { taskDef ->
        if (taskDef.type == "user-task") {

            e.getZeebeTaskHeaders()?.let {
                if (!it.hasRequiredKeys(requiredUserTaskHeaders)){
                    v.addError(1, "Missing one or more required headers for a user-task type: required headers: $requiredUserTaskHeaders")
                }

                if (!it.hasOptionalAndRequiredKeys(requiredUserTaskHeaders + optionalUserTaskHeaders)){
                    v.addError(1, "Only required ($requiredUserTaskHeaders) and optional ($optionalUserTaskHeaders) headers are allowed for user-task type.")
                }

                if (!it.noDuplicateKeys()) {
                    v.addError(1, "Duplicate header keys are not allowed.")
                }

            } ?: v.addError(1, "Missing headers for user-task")
        }
    }
}