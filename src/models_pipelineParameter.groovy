import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.PipelineParameter
import com.electriccloud.client.groovy.models.ReleaseFlowRuntimeMapping

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')
ef.createProject(projectName: 'testProj')

/*
    PipelineParameter
 */

ef.evalDsl(dsl: '''

release 'testRelease', {
  plannedEndDate = null
  plannedStartDate = null
  projectName = 'testProj\'

  pipeline 'testPipeline', {
    description = \'\'
    enabled = '1\'
    projectName = 'testProj\'
    releaseName = 'testRelease\'
    templatePipelineName = 'testPipeline\'
    templatePipelineProjectName = 'testProj\'
    type = null

    formalParameter 'param1', defaultValue: null, {
      expansionDeferred = '0\'
      label = null
      orderIndex = '1\'
      required = '1\'
      type = 'entry\'
    }

    formalParameter 'ec_stagesToRun', defaultValue: null, {
      expansionDeferred = '1\'
      label = null
      orderIndex = null
      required = '0\'
      type = null
    }

    stage 'Stage 1', {
      colorCode = null
      completionType = 'auto\'
      condition = null
      duration = null
      parallelToPrevious = null
      pipelineName = 'testPipeline\'
      plannedEndDate = null
      plannedStartDate = null
      precondition = null
      projectName = 'testProj\'
      resourceName = \'\'
      waitForPlannedStartDate = '0\'

      gate 'PRE', {
        condition = null
        precondition = null
        projectName = 'testProj\'
      }

      gate 'POST', {
        condition = null
        precondition = null
        projectName = 'testProj\'
      }

      task 'task1', {
        description = \'\'
        actualParameter = [
          'commandToRun': 'echo pipeline',
        ]
        advancedMode = '0\'
        afterLastRetry = null
        alwaysRun = '0\'
        condition = null
        deployerExpression = null
        deployerRunType = null
        duration = null
        enabled = '1\'
        environmentName = null
        environmentProjectName = null
        environmentTemplateName = null
        environmentTemplateProjectName = null
        errorHandling = 'stopOnError\'
        gateCondition = null
        gateType = null
        groupName = null
        groupRunType = null
        insertRollingDeployManualStep = '0\'
        instruction = null
        notificationEnabled = null
        notificationTemplate = null
        parallelToPrevious = null
        plannedEndDate = null
        plannedStartDate = null
        precondition = null
        projectName = 'testProj\'
        requiredApprovalsCount = null
        resourceName = \'\'
        retryCount = null
        retryInterval = null
        retryType = null
        rollingDeployEnabled = null
        rollingDeployManualStepCondition = null
        skippable = '0\'
        snapshotName = null
        startingStage = null
        subErrorHandling = null
        subapplication = null
        subpipeline = null
        subpluginKey = 'EC-Core\'
        subprocedure = 'RunCommand\'
        subprocess = null
        subproject = null
        subrelease = null
        subservice = null
        subworkflowDefinition = null
        subworkflowStartingState = null
        taskProcessType = null
        taskType = 'COMMAND\'
        triggerType = null
        waitForPlannedStartDate = '0\'
      }
    }

    // Custom properties

    property 'ec_counters', {

      // Custom properties
      pipelineCounter = '2\'
    }
  }
}
''')

result = ef.startRelease(
        'projectName': 'testProj',
        'releaseName': 'testRelease',
        'pipelineParameters': [new PipelineParameter('param1', 'value')]
)

println result

def flowRuntimePropertySheet = ef.getProperties(propertySheetId: result.flowRuntime.propertySheetId).propertySheet
def paramProperty = flowRuntimePropertySheet.property.find {it.propertyName == 'param1'}
assert paramProperty.value == 'value'

/*
    ReleaseFlowRuntimeMapping
 */

def releaseId = result.flowRuntime.releaseId
def flowRuntimeId = result.flowRuntime.flowRuntimeId

// http://jira/browse/NMB-25961 - ec-groovy - getReleases operation requires projectName argument
result = ef.getReleases(projectName: 'testProj', releaseFlowRuntimeMappings: [new ReleaseFlowRuntimeMapping(releaseId, flowRuntimeId)])
println result

ef.deleteProject(projectName: 'testProj')
