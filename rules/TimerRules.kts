package rules

import com.github.stephenott.workflowlinter.linter.elementValidator
import com.github.stephenott.workflowlinter.linter.getDuration
import io.zeebe.model.bpmn.instance.TimeDuration

listOf(
        elementValidator<TimeDuration> { element, validator ->
            if (element.textContent.isNullOrBlank()) {
                validator.addError(24, "Missing timer configuration.")
            }
        },

        elementValidator<TimeDuration> { element, validator ->
            if (element.getDuration().seconds == 0L) {
                validator.addError(12, "A timer configuration cannot be set to equal a duration (or equivalent) of zero seconds.")
            }
        }
)