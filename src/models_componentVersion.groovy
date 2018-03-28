import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.ComponentVersion

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')
ef.createProject(projectName: 'testProj')

/*
    ComponentVersion
 */

ef.evalDsl(dsl: '''

project 'testProj', {
  resourceName = null
  workspaceName = null

  environment 'env', {
    environmentEnabled = '1\'
    projectName = 'testProj\'
    reservationRequired = '0\'
    rollingDeployEnabled = null
    rollingDeployType = null

    environmentTier 'Tier 1', {
      batchSize = null
      batchSizeType = null
      resourceName = [
        'local',
      ]
    }
  }

  application 'testApp', {
    description = \'\'

    applicationTier 'Tier 1', {
      applicationName = 'testApp\'
      projectName = 'testProj\'

      component 'testcomp', pluginName: null, {
        applicationName = 'testApp\'
        pluginKey = 'EC-Artifact\'
        reference = '0\'
        sourceComponentName = null
        sourceProjectName = null

        // Custom properties

        property 'ec_content_details', {

          // Custom properties

          property 'artifactName', value: 'artifact', {
            expandable = '1\'
          }
          artifactVersionLocationProperty = '/myJob/retrievedArtifactVersions/$[assignedResourceName]\'
          filterList = \'\'
          overwrite = 'update\'
          pluginProcedure = 'Retrieve\'

          property 'pluginProjectName', value: 'EC-Artifact', {
            expandable = '1\'
          }
          retrieveToDirectory = \'\'

          property 'versionRange', value: '1.0', {
            expandable = '1\'
          }
        }
      }
    }

    process 'appProc', {
      applicationName = 'testApp\'
      processType = 'OTHER\'
      serviceName = null
      smartUndeployEnabled = null
      timeLimitUnits = null
      workingDirectory = null
      workspaceName = null

      formalParameter 'ec_enforceDependencies', testProjValue: '0', {
        expansionDeferred = '1\'
        label = null
        orderIndex = null
        required = '0\'
        type = 'checkbox\'
      }

      processStep 'appProcStep', {
        actualParameter = [
          'commandToRun': 'echo hello',
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

    tierMap '64afc958-25f4-11e8-9c9d-0050563309e5', {
      applicationName = 'testApp\'
      environmentName = 'env\'
      environmentProjectName = 'testProj\'
      projectName = 'testProj\'

      tierMapping 'baa084e7-25f4-11e8-8120-0050563309e5', {
        applicationTierName = 'Tier 1\'
        environmentTierName = 'Tier 1\'
        tierMapName = '64afc958-25f4-11e8-9c9d-0050563309e5\'
      }
    }

    // Custom properties

    property 'ec_deploy', {

      // Custom properties
      ec_notifierStatus = '0\'
    }
    jobCounter = '1\'
  }
}
''')

result = ef.createSnapshot(
        projectName: 'testProj',
        applicationName: 'testapp',
        snapshotName: 'snapshot',
        componentVersions: [
                new ComponentVersion('artifact', '1.0')
        ])

def artifact = ef.getProperty(propertyName: '/projects[testProj]/applications[testapp]/snapshots[snapshot]/ec_component_versions/ec_testcomp/ec_artifactName').property.value
def version = ef.getProperty(propertyName: '/projects[testProj]/applications[testapp]/snapshots[snapshot]/ec_component_versions/ec_testcomp/ec_Version').property.value

assert artifact == 'artifact'
assert version == '1.0'

println result

ef.deleteProject(projectName: 'testProj')
