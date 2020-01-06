# Workflow Linter Server

Workflow Linter Server (WLS) currently serves three functions:

1. BPMN Linting through REST endpoints using Kotlin-Script (.kts) and YAML based configurations. 
1. BPMN Sanitization: Using the linting capability, lint rules can be used to remove any xml content of a BPMN that is considered not sharable.  This is used in cases where you want to share your BPMN and have it render in a BPMN viewer (say in [BPMN.js](https://bpmn.io)), but you do not want to example all of the internals of the BPMN's xml such as documentation, expressions, scripts, headers, IO mappings, custom extensions, etc.
1. OpenAPI/Swagger generator for the REST APIs defined by WLS.  This provides easy HTTP-Client integration with various workflow services and tools such as a BPMN Modeler, Deployment Orchestration, CI, etc.


# Workflow Linting

Workflow linting is currently targeting for BPMN workflows.

Linting Rules can be configured using two methods:

1. YAML
1. Kotlin-Script (.kts) files

Execution of linting rules is performed through a REST Endpoint

## Execution of Linting Rules: REST Endpoint

**Option 1:**

Create a Multi-Part Form with a file upload property.  Property name must be `file`.

POST `localhost:8080/workflow/bpmn/linter`

If the linting is successful a status-code of `200` will be returned.


**Option 2:**

POST `localhost:8080/workflow/bpmn/linter`

XML Body: xml of the BPMN.

Content-Type: `application/xml`

If the linting is successful a status-code of `200` will be returned.


### Linter Response

The linter response will return a object with a `results` property.  
Each property in the `results` object is a elementId in the XML (BaseElement.class).  
This property contains a array of linting validation failures.

For each linting validation failure, the following properties are provided:

- `type`: the type of failure: `WARNING` or `ERROR`
- `elementId`: the unique element ID.  This is the same ID that is in the parent. 
- `elementType`: the model API full class path.  Used to uniquely identify the type of element.
- `message`: the linting validation message returned by the linting failure.
- `code`:  the linting validation code returned by the linting failure.

When configuring linting rules, you can set the `message` and `code` values per linting rule.

```json
{
    "results": {
        "Task_1pjpqz0": [
            {
                "type": "WARNING",
                "elementId": "Task_1pjpqz0",
                "elementType": "io.zeebe.model.bpmn.instance.MultiInstanceLoopCharacteristics",
                "message": "my linter warning",
                "code": 0
            }
        ]
    }
}
```

The following is a successful lint response, where the lint rule was set to trigger for every element in the XML.

This is a robust example to demonstrate the linting response capabilities and level of linting rules that can be created.

```json
{
    "results": {
        "Task_1pjpqz0": [
            {
                "type": "WARNING",
                "elementId": "Task_1pjpqz0",
                "elementType": "io.zeebe.model.bpmn.instance.MultiInstanceLoopCharacteristics",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Task_1pjpqz0",
                "elementType": "io.zeebe.model.bpmn.instance.ServiceTask",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "TextAnnotation_1t2spfv": [
            {
                "type": "WARNING",
                "elementId": "TextAnnotation_1t2spfv",
                "elementType": "io.zeebe.model.bpmn.instance.TextAnnotation",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Process_16i55l0": [
            {
                "type": "WARNING",
                "elementId": "Process_16i55l0",
                "elementType": "io.zeebe.model.bpmn.instance.Lane",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Process_16i55l0",
                "elementType": "io.zeebe.model.bpmn.instance.Lane",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Process_16i55l0",
                "elementType": "io.zeebe.model.bpmn.instance.LaneSet",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Process_16i55l0",
                "elementType": "io.zeebe.model.bpmn.instance.Process",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "IntermediateThrowEvent_1jlnvjm": [
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_1jlnvjm",
                "elementType": "io.zeebe.model.bpmn.instance.BoundaryEvent",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_1jlnvjm",
                "elementType": "io.zeebe.model.bpmn.instance.MessageEventDefinition",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SequenceFlow_0bjdega": [
            {
                "type": "WARNING",
                "elementId": "SequenceFlow_0bjdega",
                "elementType": "io.zeebe.model.bpmn.instance.SequenceFlow",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "EndEvent_1qai5bq": [
            {
                "type": "WARNING",
                "elementId": "EndEvent_1qai5bq",
                "elementType": "io.zeebe.model.bpmn.instance.EndEvent",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "IntermediateThrowEvent_0pftlc9": [
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_0pftlc9",
                "elementType": "io.zeebe.model.bpmn.instance.TimerEventDefinition",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_0pftlc9",
                "elementType": "io.zeebe.model.bpmn.instance.BoundaryEvent",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "IntermediateThrowEvent_0ue7kjs": [
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_0ue7kjs",
                "elementType": "io.zeebe.model.bpmn.instance.BoundaryEvent",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_0ue7kjs",
                "elementType": "io.zeebe.model.bpmn.instance.MessageEventDefinition",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Task_05r7ocx": [
            {
                "type": "WARNING",
                "elementId": "Task_05r7ocx",
                "elementType": "io.zeebe.model.bpmn.instance.MultiInstanceLoopCharacteristics",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Task_05r7ocx",
                "elementType": "io.zeebe.model.bpmn.instance.Task",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Collaboration_1xisnek": [
            {
                "type": "WARNING",
                "elementId": "Collaboration_1xisnek",
                "elementType": "io.zeebe.model.bpmn.instance.Participant",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Collaboration_1xisnek",
                "elementType": "io.zeebe.model.bpmn.instance.Collaboration",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Collaboration_1xisnek",
                "elementType": "io.zeebe.model.bpmn.instance.Participant",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Task_10ugd34": [
            {
                "type": "WARNING",
                "elementId": "Task_10ugd34",
                "elementType": "io.zeebe.model.bpmn.instance.CallActivity",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Task_10ugd34",
                "elementType": "io.zeebe.model.bpmn.instance.MultiInstanceLoopCharacteristics",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "EndEvent_1m14ym9": [
            {
                "type": "WARNING",
                "elementId": "EndEvent_1m14ym9",
                "elementType": "io.zeebe.model.bpmn.instance.EndEvent",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Process_1xanqu0": [
            {
                "type": "WARNING",
                "elementId": "Process_1xanqu0",
                "elementType": "io.zeebe.model.bpmn.instance.Process",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "IntermediateThrowEvent_0rqamn8": [
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_0rqamn8",
                "elementType": "io.zeebe.model.bpmn.instance.ErrorEventDefinition",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_0rqamn8",
                "elementType": "io.zeebe.model.bpmn.instance.BoundaryEvent",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "IntermediateThrowEvent_1c9t58w": [
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_1c9t58w",
                "elementType": "io.zeebe.model.bpmn.instance.TimerEventDefinition",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "IntermediateThrowEvent_1c9t58w",
                "elementType": "io.zeebe.model.bpmn.instance.BoundaryEvent",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SequenceFlow_1fetaup": [
            {
                "type": "WARNING",
                "elementId": "SequenceFlow_1fetaup",
                "elementType": "io.zeebe.model.bpmn.instance.SequenceFlow",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SequenceFlow_1hnbx9h": [
            {
                "type": "WARNING",
                "elementId": "SequenceFlow_1hnbx9h",
                "elementType": "io.zeebe.model.bpmn.instance.SequenceFlow",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "ServiceTask_1luzsfd": [
            {
                "type": "WARNING",
                "elementId": "ServiceTask_1luzsfd",
                "elementType": "io.zeebe.model.bpmn.instance.ServiceTask",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "TextAnnotation_1qi6dhp": [
            {
                "type": "WARNING",
                "elementId": "TextAnnotation_1qi6dhp",
                "elementType": "io.zeebe.model.bpmn.instance.TextAnnotation",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "ExclusiveGateway_0zw2nt1": [
            {
                "type": "WARNING",
                "elementId": "ExclusiveGateway_0zw2nt1",
                "elementType": "io.zeebe.model.bpmn.instance.ExclusiveGateway",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SubProcess_0th6hv3": [
            {
                "type": "WARNING",
                "elementId": "SubProcess_0th6hv3",
                "elementType": "io.zeebe.model.bpmn.instance.SubProcess",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SequenceFlow_135m3sa": [
            {
                "type": "WARNING",
                "elementId": "SequenceFlow_135m3sa",
                "elementType": "io.zeebe.model.bpmn.instance.SequenceFlow",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Association_1oysd0g": [
            {
                "type": "WARNING",
                "elementId": "Association_1oysd0g",
                "elementType": "io.zeebe.model.bpmn.instance.Association",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "StartEvent_1": [
            {
                "type": "WARNING",
                "elementId": "StartEvent_1",
                "elementType": "io.zeebe.model.bpmn.instance.StartEvent",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Task_1cl9hr3": [
            {
                "type": "WARNING",
                "elementId": "Task_1cl9hr3",
                "elementType": "io.zeebe.model.bpmn.instance.ServiceTask",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Task_00znhpl": [
            {
                "type": "WARNING",
                "elementId": "Task_00znhpl",
                "elementType": "io.zeebe.model.bpmn.instance.MultiInstanceLoopCharacteristics",
                "message": "my linter warning",
                "code": 0
            },
            {
                "type": "WARNING",
                "elementId": "Task_00znhpl",
                "elementType": "io.zeebe.model.bpmn.instance.ReceiveTask",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SequenceFlow_1uts8z1": [
            {
                "type": "WARNING",
                "elementId": "SequenceFlow_1uts8z1",
                "elementType": "io.zeebe.model.bpmn.instance.SequenceFlow",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Association_0nb28zy": [
            {
                "type": "WARNING",
                "elementId": "Association_0nb28zy",
                "elementType": "io.zeebe.model.bpmn.instance.Association",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SequenceFlow_15yu6zp": [
            {
                "type": "WARNING",
                "elementId": "SequenceFlow_15yu6zp",
                "elementType": "io.zeebe.model.bpmn.instance.SequenceFlow",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SequenceFlow_1edsqdq": [
            {
                "type": "WARNING",
                "elementId": "SequenceFlow_1edsqdq",
                "elementType": "io.zeebe.model.bpmn.instance.SequenceFlow",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "Message_1wmcbh6": [
            {
                "type": "WARNING",
                "elementId": "Message_1wmcbh6",
                "elementType": "io.zeebe.model.bpmn.instance.Message",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "StartEvent_1rxkj6g": [
            {
                "type": "WARNING",
                "elementId": "StartEvent_1rxkj6g",
                "elementType": "io.zeebe.model.bpmn.instance.StartEvent",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "MessageFlow_0sslgzl": [
            {
                "type": "WARNING",
                "elementId": "MessageFlow_0sslgzl",
                "elementType": "io.zeebe.model.bpmn.instance.MessageFlow",
                "message": "my linter warning",
                "code": 0
            }
        ],
        "SequenceFlow_1oknyaf": [
            {
                "type": "WARNING",
                "elementId": "SequenceFlow_1oknyaf",
                "elementType": "io.zeebe.model.bpmn.instance.SequenceFlow",
                "message": "my linter warning",
                "code": 0
            }
        ]
    }
}

```


## Workflow Linter

The workflow linter provides a linting/validation engine for BPMN workflows that are parsed by the BPMN Model API.

The linter acts as a warning and error system allowing you to validate workflows during the modeling process, and you can 
implement the linter at deployment time to ensure only valid models are deployed into their respective environments.

### YAML based linting rules

1. Rules are additive.  One rule cannot cancel out another rule.
1. Rules require a description, elementTypes, and a rule implementation.
1. Element Types: `ServiceTask`, `ReceiveTask`.... (more to come)
1. Rules can apply to multiple element types, and have various targeting rules.
1. the `Target` property defines "targeting rules" that applied to the rule.  Targeting rules define when the rule should be applied for the specific Element Type.
1. See the User Task example below for common usage: "Only target ServiceTasks with a task type of `user-task`.  This means the rule will only apply when a Service Task defines a type of `user-task` 

```yaml

workflow-linter:
    rules:
      global-rules:
        enable: false
        description: Global Restrictions for Service Task Types
        elementTypes:
          - ServiceTask
        serviceTaskRule:
          allowedTypes:
            - some-type
            - user-task
    
      user-task-rule:
        description: Specific rule for User Task Configuration of Service Tasks
        elementTypes:
          - ServiceTask
        target:
          serviceTasks:
            types:
              - user-task
        headerRule:
          requiredKeys:
            - title
            - candidateGroups
            - formKey
          allowedNonDefinedKeys: false
          allowedDuplicateKeys: false
          optionalKeys:
            - priority
            - assignee
            - candidateUsers
            - dueDate
            - description
```

#### Global Rules

If the `target` property configuration is **not** provided, then the rule will be applied globally to all Element Types defined in the rule.

Global rules can be a good way to implement some restrictions on your modeling teams to ensure that internal types and correlation keys are not used.

Global rules can be valuable naming conventions as well: "task types cannot start with a underscore `_`."

#### Element Type Rules (WIP)

Working model is to have rule factories for each major Element Type (Service Task, Receive Task, Catch message, etc).

Element Type Rules provide specific rule implementations that focus on the Element Type's configuration possibilities:

1. Service Task:
   1. Allowed Types + regex limit
   1. Allowed Retry Regex
   1. Allowed IO Mappings
   1. Allowed Headers (Required, Optional, duplicates, Key Value Pairs, Non-Defined Keys, etc)
1. Receive Task:
   1. Allowed Correlation Keys
   1. Correlation Key Restrictions (limit names allowed to be used + regex limit)
   1. Out-mapping restrictions.


#### Formatting Rules (WIP)

The Linter is not just about execution implementation rules, you can also define formatting rules for the BPMN.

Formatting rules enable you to prevent common errors in formatting of BPMN.

1. Label all Gateways
1. Pool Usage
1. Prevent label patterns with Regex
1. Gateways labels end in "?"
1. No Expressions
1. Double sequence flows
1. Sub-Process Names
1. Loop Characteristics naming
1. Start Timer names
1. Message Start Names
1. End Event Names
1. Intermediate Event Names
1. .. More?


#### YAML based linting rules TODO

1. Add support to define what will prevent a deployment (WARNING / ERRORS)
1. Add optional parameter on deployment endpoint to validate using linter
1. Provide JSON error support
1. Provide Language Server implementation of linter.
1. Add IO Mappings rules
1. Add Receive Task Rule
1. Add Message Rules
1. Add Formatting rules
1. Add Timer rules to prevent certain spectrum of timer durations / cycles
1. Add targeting based on BPMN Process Key
1. Add special negating rule for Task Types and Correlation Keys
1. Add Allowed Call Activity Process IDs: Rule to ensure only specific Process IDs can be called through the Modeler.
1. Add error code and error message YAML config examples


### Kotlin-Script (.kts) based linting rules

Kotlin Script `.kts` linting rules provide a scripting based approach to writing linting rules.  Using the scripting approach provides rule writers the most flexibility and power, but requires more advanced skills compared to writing YAML based linting rules.

Configure the paths of your .kts files in the configuration (@TODO)

Each script will be compiled are runtime and converted into ElementValidators.

```kotlin
example goes here
```

# Workflow Sanitizer

Workflow Sanitizer is the capability to remove aspects of a BPMN that are internal configuration that should not be shared when allowing users to download BPMN xml (such as when rendering a BPMN in the bpmn.js / bpmn.io modeler).

Every element in a BPMN can be replaced with a sanitized version: a sanitized version is a new blank instance of the element that replaces the original element.
Only configurations that are explicitly desired in a sanitized BPMN are transferred over into the new instance.

The Sanitizer provides flexible usage options depending on your sanitizing needs:

The core of Sanitizer provides a Workflow Linter that allows you to configure which types that inherit from `ModelElementInstance` will be targeted for cleaning.  
The default Sanitizer Linter configuration targets all elements and applies a error code of `5000` ("it's Audi 5000"...).
The default Sanitizer that actions each of the found elements, will take a lean approach:

1. element Id values are kept.  This allows to continue targeting elements based on the IDs used in the BPMN's execution so you can do heatmaps, and BPMN status overlays (counts, what activities are currently failing, which ones have completed, loop counts, etc).
1. All names are kept (these are usually the labels/names on each element/task/gateway/sequence-flow/pool, etc)
1. All Annotations are kept.
1. All markings and definition types are kept: but only the fact that they exist; their actual configuration is removed.
1. Default Flow markings on sequence flows are kept.
1. `Process` elements are not modified.  But their children would be modified as their are independent instances of `ModelElementInstance`

What is explicitly not kept?

1. Expressions
1. Configurations on Events: message correlation keys, timer expressions, etc
1. Receive Task Message Correlation configurations.
1. Service Task Configurations: Headers, Type, Retries
1. IO Mappings
1. Loop Characteristic configurations (the "parallel" vs "sequential" marking is kept)
1. Message Elements (even if a message is not tied to a element )


## Sanitizer Rest Endpoint

POST localhost

XML body and file upload options

```xml
body example goes here
```

Response

```xml
XML response goes here
```


# OpenAPI/Swagger Documentation

see openapi docs folder for generated api docs. @TODO