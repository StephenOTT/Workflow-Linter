package com.github.stephenott.workflowlinter

import com.github.stephenott.linter.linter.elementValidator

//@MicronautTest
//class LinterTest1(
//        private var server: EmbeddedServer
//): StringSpec({
//    "Test Linter"{
//
//        val file = File("src/test/resources/test1.bpmn")
//
////        val rules: List<LinterRule> =
////                LinterConfigurationParser.getLintRuleBeans(server.applicationContext)
////
////        val validators: List<ModelElementValidator<out ModelElementInstance>> =
////                LinterConfigurationParser.lintRulesToValidators(rules)
////
////
////        WorkflowLinter(file).lintWithValidators(validators)
//
//        val cleanerValidators = listOf(
//                elementValidator<BaseElement> { element, validatorResultCollector ->
//                    validatorResultCollector.addError(5000, "cleaner code")
//                }
//        )
//
//        val cleanModel = Cleaner(cleanerValidators).cleanModel(file)
//        println(Bpmn.convertToString(cleanModel))
//
//    }
//})