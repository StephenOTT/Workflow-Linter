package com.github.stephenott.workflowlinter.controller

import com.github.stephenott.workflowlinter.controller.Helpers.lint
import com.github.stephenott.workflowlinter.controller.Helpers.processLintResults
import com.github.stephenott.workflowlinter.linter.kts.LinterServerRulesFromKts
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Post
import io.micronaut.http.multipart.CompletedFileUpload
import io.reactivex.Single
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import javax.inject.Inject

@OpenAPIDefinition(
    info = Info(
        title = "Workflow Linter Server",
        version = "0.5",
        description = "Workflow Linting Server for BPMN Workflows.",
        license = License(name = "", url = "http://github.com/stephenott/workflow-linter"),
        contact = Contact(url = "http://github.com/stephenott", name = "Stephen Russett", email = "http://github.com/stephenott")
    )
)
@Controller("/workflow/bpmn/linter")
class LinterController(){

    @Inject
    lateinit var rules: LinterServerRulesFromKts

    @Post(value = "/", consumes = [MediaType.MULTIPART_FORM_DATA])
    fun upload(file: CompletedFileUpload): Single<HttpResponse<LinterResponse<com.github.stephenott.workflowlinter.linter.ValidationResults>>> {
        return Single.fromCallable {
            lint(file.inputStream, rules)
        }.map {
            processLintResults(it)
        }.onErrorResumeNext {
            Single.error(IllegalArgumentException(it)) //@TODO crate custom error
        }.map {
            HttpResponse.ok(LinterResponse(it))
        }
    }

    @Error
    fun lintingError(request: HttpRequest<*>, exception: Exception): HttpResponse<Any> {
        exception.printStackTrace()
        return HttpResponse.badRequest(LinterResponseError(exception.message ?: "Internal Error Occurred"))
    }

}