package com.github.stephenott.workflowlinter.controller

import com.fasterxml.jackson.annotation.JsonUnwrapped


data class LinterResponse<T>(@JsonUnwrapped val result: T) {

}

data class LinterResponseError(val message: String) {

}



