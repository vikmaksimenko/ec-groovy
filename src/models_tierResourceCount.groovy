import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.TierResourceCount

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')


/*
    TierResourceCount
 */

ef.evalDsl(dsl: '''
project 'T1', {
  resourceName = null
  workspaceName = null


  resourceTemplate 'amazonEC2-ResourceTemplate', {
    cfgMgrPluginKey = null
    cfgMgrProcedure = null
    cfgMgrProjectName = null
    cloudProviderParameter = [
      'config': 'ec2config',
      'count': '1',
      'group': 'PROVIDE SECURITY GROUP',
      'image': 'PROVIDE IMAGE NAME',
      'instanceInitiatedShutdownBehavior': '',
      'instanceType': 'PROVIDE INSTANCE TYPE',
      'keyname': 'PROVIDE KEY NAME',
      'privateIp': '',
      'propResult': '',
      'res_poolName': '',
      'res_port': '',
      'res_workspace': '',
      'resource_zone': 'default',
      'subnet_id': 'PROVIDE SUBNET ID',
      'use_private_ip': '0',
      'userData': '',
      'zone': 'PROVIDE AVAILABILITY ZONE',
    ]
    cloudProviderPluginKey = 'EC-EC2\'
    cloudProviderProcedure = 'API_RunInstances\'
    cloudProviderProjectName = null

    // Custom properties

    property 'ec_cloud_plugin_parameter', {

      // Custom properties
      config = 'ec2config\'
      count = '1\'
      group = 'PROVIDE SECURITY GROUP\'
      image = 'PROVIDE IMAGE NAME\'
      instanceInitiatedShutdownBehavior = \'\'
      instanceType = 'PROVIDE INSTANCE TYPE\'
      keyname = 'PROVIDE KEY NAME\'
      privateIp = \'\'
      propResult = \'\'
      res_poolName = \'\'
      res_port = \'\'
      res_workspace = \'\'
      resource_zone = 'default\'
      subnet_id = 'PROVIDE SUBNET ID\'
      use_private_ip = '0\'
      userData = \'\'

      property 'zone', value: 'PROVIDE AVAILABILITY ZONE', {
        expandable = '1\'
      }
    }
  }

  environmentTemplate 'Temp', {

    environmentTemplateTier 'Tier 1', {
      resourceCount = '1\'
      resourceTemplateName = 'amazonEC2-ResourceTemplate\'
      resourceTemplateProjectName = null
    }

    // Custom properties

    property 'ec_deploy', {

      // Custom properties
      ec_usageCount = '0\'
    }
  }


  application 'app1', {
    description = \'\'

    applicationTier 'Tier 1', {
      applicationName = 'app1\'
      projectName = 'T1\'
    }

    environmentTemplateTierMap 'e831a867-30ac-f170-a4b0-005056330b84', {
      applicationName = 'app1\'
      environmentProjectName = 'T1\'
      environmentTemplateName = 'Temp\'
      projectName = 'T1\'
      tierMapping = ['Tier 1': 'Tier 1']
    }

    process 'p1', {
      applicationName = 'app1\'
      processType = 'OTHER\'
      serviceName = null
      smartUndeployEnabled = null
      timeLimitUnits = null
      workingDirectory = null
      workspaceName = null

      formalParameter 'ec_enforceDependencies', defaultValue: '0', {
        expansionDeferred = '1\'
        label = null
        orderIndex = null
        required = '0\'
        type = 'checkbox\'
      }

      processStep 's1', {
        actualParameter = [
          'commandToRun': 'ls',
        ]
        afterLastRetry = null
        alwaysRun = '0\'
        applicationTierName = 'Tier 1\'
        componentRollback = null
        dependencyJoinType = 'and\'
        errorHandling = 'abortJob\'
        instruction = null
        notificationEnabled = null
        notificationTemplate = null
        processStepType = 'command\'
        retryCount = null
        retryInterval = null
        retryType = null
        rollbackSnapshot = null
        rollbackType = null
        rollbackUndeployProcess = null
        skipRollbackIfUndeployFails = null
        smartRollback = null
        subcomponent = null
        subcomponentApplicationName = null
        subcomponentProcess = null
        subprocedure = 'RunCommand\'
        subproject = '/plugins/EC-Core/project\'
        subservice = null
        subserviceProcess = null
        timeLimitUnits = null
        useUtilityResource = '0\'
        utilityResourceName = null
        workingDirectory = null
        workspaceName = null

        // Custom properties

        property 'ec_deploy', {

          // Custom properties
          ec_notifierStatus = '0\'
        }
      }

      // Custom properties

      property 'ec_deploy', {

        // Custom properties
        ec_notifierStatus = '0\'
      }
    }

    // Custom properties

    property 'ec_deploy', {

      // Custom properties
      ec_notifierStatus = '0\'
    }
  }

  pipeline 'p1', {
    description = \'\'
    enabled = '1\'
    releaseName = null
    templatePipelineName = null
    templatePipelineProjectName = null
    type = null

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
      pipelineName = 'p1\'
      plannedEndDate = null
      plannedStartDate = null
      precondition = null
      resourceName = \'\'
      waitForPlannedStartDate = '0\'

      gate 'PRE', {
        condition = null
        precondition = null
      }

      gate 'POST', {
        condition = null
        precondition = null
      }
    }
  }
}
''')

result = ef.createTask(
        'taskName': 't1',
        'projectName': 'T1',
        'pipelineName': 'p1',
        'environmentName': 'e1',
        'environmentProjectName': 'T1',
        'environmentTemplateName': 'Temp',
        'environmentTemplateProjectName': 'T1',
        'subapplication': 'app1',
        'subprocess': 'p1',        'subproject': 'T1',
        'stageName': 'Stage 1',
        'taskProcessType': 'APPLICATION',
        'taskType': 'PROCESS',
        'tierResourceCounts': [new TierResourceCount('Tier 1', '3')]
)

println result

assert result.task.flowStateTierMap[0].tierName == 'Tier 1'
assert result.task.flowStateTierMap[0].resourceCount == '3'

ef.deleteProject(projectName: 'T1')
