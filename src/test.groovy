import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

def pipelineName = 'Test Pipeline'
def result = 'handler does not work'

// Cleanup
ef.deletePipeline(pipelineName: pipelineName, projectName: 'Default')

// Create pipeline
ef.createPipeline(pipelineName: pipelineName, projectName: 'Default',
        /*success handler*/ { response, json ->
    println('Pipeline created')
    result = json
},
        /*failure handler*/ { response, data ->
    println('Failed to create pipeline')
    result = data
})

// Check created pipeline
println result.dump()
assert result != 'handler does not work'
assert result.containsKey('pipeline')

def pipeline = result.pipeline
assert !pipeline.createTime.isEmpty()
assert !pipeline.modifyTime.isEmpty()
assert !pipeline.pipelineId.isEmpty()
assert !pipeline.propertySheetId.isEmpty()

assert pipeline.hasDeployerTask              == '0'
assert pipeline.lastModifiedBy               == 'admin'
assert pipeline.owner                        == 'admin'
assert pipeline.pipelineEnabled              == '1'
assert pipeline.pipelineName                 == pipelineName
assert pipeline.projectName                  == 'Default'
assert pipeline.requiredParameterCount       == '0'
assert pipeline.stageCount                   == '0'
assert pipeline.totalParameterCount          == '1'
assert pipeline.tracked                      == '1'
assert pipeline.userDefinedParameterCount    == '0'

// Get pipeline (retval validation)
println('Get pipeline and check result')
result = ef.getPipeline(pipelineName: pipelineName, projectName: 'Default')
println result.dump()

assert result != 'handler does not work'
assert result.containsKey('pipeline')

pipeline = result.pipeline
assert !pipeline.createTime.isEmpty()
assert !pipeline.modifyTime.isEmpty()
assert !pipeline.pipelineId.isEmpty()
assert !pipeline.propertySheetId.isEmpty()

assert pipeline.hasDeployerTask              == '0'
assert pipeline.lastModifiedBy               == 'admin'
assert pipeline.owner                        == 'admin'
assert pipeline.pipelineEnabled              == '1'
assert pipeline.pipelineName                 == pipelineName
assert pipeline.projectName                  == 'Default'
assert pipeline.requiredParameterCount       == '0'
assert pipeline.stageCount                   == '0'
assert pipeline.totalParameterCount          == '1'
assert pipeline.tracked                      == '1'
assert pipeline.userDefinedParameterCount    == '0'

// Try to create pipeline with save name
ef.createPipeline(pipelineName: pipelineName, projectName: 'Default',
        /*success handler*/ { response, json ->
    println('Pipeline created')
    result = json
},
        /*failure handler*/ { response, data ->
    println('Pipeline with same name was not created')
    result = data
})

println result.dump()
assert result.containsKey('error')

def error = result.error
assert error.code     == 'DuplicatePipelineName'
assert error.details  == ''
assert error.message  == "Pipeline '${pipelineName}' already exists in project 'Default'"
assert error.where    == 'pipelineName'

ef.deletePipeline(pipelineName: pipelineName, projectName: 'Default')
println('Deleted pipeline ' + pipelineName)
