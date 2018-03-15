
// Add your imports here

// 1. All the ElectricFlow APIs are accessible through the
// ElectricFlow groovy class so you can start  by importing it.
import com.electriccloud.client.groovy.ElectricFlow

// 2. Import the models for the API arguments from the client models package.
import com.electriccloud.client.groovy.models.ActualParameter
import com.electriccloud.client.groovy.models.Credential

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')


def newProcName = 'New procedure created by runProcedureExample'
ef.evalDsl(dsl: """
project 'Groovy Demo', {
  procedure '$newProcName', {

    formalParameter 'param1'
    formalParameter 'param2'
    formalParameter 'testCred', type: 'credential'

    step 'testStep', {
      command = '''
import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()

def result = ef.getFullCredential(credentialName: 'testCred')

println "Credentials: \$result.credential.userName - \$result.credential.password"

      '''
      shell = 'ec-groovy'
      alwaysRun = true
    }

    property 'ec_customEditorData', {

    // Custom properties

    property 'parameters', {

      // Custom properties

      property 'testCred', {

        // Custom properties
        formType = 'standard'
      }
    }
  }

attachParameter(projectName: 'Groovy Demo',
          procedureName: '$newProcName',
          stepName: 'testStep',
          formalParameterName: 'testCred')

  }


}
""", /*success handler*/ { response, json->
    println('DSL Response')
    println(json)
    println "Created procedure '$newProcName'"
    return json
})


List<ActualParameter> params = new ArrayList<>()
params.add(new ActualParameter(actualParameterName: 'param1', value: 'test12'))
params.add(new ActualParameter(actualParameterName: 'param2', value: 'test24'))
params.add(new ActualParameter(actualParameterName: 'testCred', value: 'testCred'))

List<Credential> creds = new ArrayList<>()
creds.add(new Credential(credentialName: 'testCred', userName: 'zzzxxx', password: 'pwd'))

ef.runProcedure(projectName: 'Groovy Demo', procedureName: newProcName, actualParameters:  params, credentials: creds,
        /*success handler*/ { response, json ->
    println('Success')
    println(json)
    return json
},
        /*failure handler*/ { response, data ->
    println('Failure')
    println(data)
})

// Parameters check
def step = ef.getStep(projectName: 'Groovy Demo', procedureName: newProcName, stepName: 'testStep')
assert step.step.stepName == "testStep"
assert step.step.alwaysRun == "1"


// Generate DSL
def result = ef.generateDsl(path: '/projects[Groovy Demo]')
println(result.dump())