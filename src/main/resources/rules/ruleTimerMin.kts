package rules

import com.github.stephenott.workflowlinter.linter.elementValidator
import com.github.stephenott.workflowlinter.linter.getDuration
import io.zeebe.model.bpmn.instance.TimeDuration

elementValidator<TimeDuration> { e, v ->
    if (e.getDuration().seconds in 0..60){
        v.addError(60, "Timers Durations must be greater than 60 seconds.")
    }
}