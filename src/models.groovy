import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.*

import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')

/*
* AttributeDataType, AttributePath, LinkParameter, VisualizationProperty validations
* */

//VisualizationProperty visualizationProperty = new VisualizationProperty('defaultColor', '#F00')
//AttributeDataType attributeDataTypeXAxis = new AttributeDataType('xAxis', 'DATE')
//AttributeDataType attributeDataTypeYAxis = new AttributeDataType('yAxis', 'NUMBER')
//AttributeDataType attributeDataTypeGroups = new AttributeDataType('groups', 'STRING')
//AttributePath attributePathXAxis = new AttributePath('xAxis', 'deployment_date_label')
//AttributePath attributePathYAxis = new AttributePath('yAxis', 'deployment_outcome_count')
//AttributePath attributePathGroups = new AttributePath('groups', 'deployment_outcome')
//LinkParameter linkParameter = new LinkParameter('externalUrl','https://www.facebook.com/')
//Color successColor = new Color('success', '#70b723')
//
//ef.deleteDashboard(projectName: 'Default', dashboardName: 'dashboardName')
//ef.deleteReport(projectName: 'Default', reportName: 'reportName')
//
//def result = ef.createDashboard(
//        projectName:    'Default',
//        dashboardName:  'dashboardName',
//        description:    'test description',
//        layout:         'FLOW',
//        type:           'STANDARD',
//        phases:         ['phase1', 'phase2']
//)
//
//println result.dump()
//
//result = ef.createReport(
//        projectName:    'Default',
//        reportName:     'reportName',
//        definition:     'definition',
//        description:    'description',
//        parameters:     'parameters',
//        title:          'title',
//        uri:            'uri'
//)
//
//println result.dump()
//println ef.getVersions().dump()
//
//result = ef.createWidget(
//        projectName:            'Default',
//        dashboardName:          'dashboardName',
//        widgetName:             'widgetName',
//        attributeDataTypes:      [attributeDataTypeXAxis, attributeDataTypeYAxis, attributeDataTypeGroups],
//        attributePaths:          [attributePathXAxis, attributePathYAxis, attributePathGroups],
//        colors:                  [successColor],
//        description:            'description',
//        iconUrl:                'iconUrl',
//        linkParameters:          [linkParameter],
//        linkTarget:             'External',
//        orderIndex:             0,
//        phase:                  'phase1',
//        reportName:             'reportName',
//        reportProjectName:      'Default',
//        section:                'PHASE_DETAIL',
//        title:                  'title',
//        visualization:          'STACKED_AREA_CHART',
//        visualizationProperty:  [visualizationProperty],
//)
//
//println result.dump()
//
//
//widget = ef.getWidget(
//        projectName:            'Default',
//        dashboardName:          'dashboardName',
//        widgetName:             'widgetName',
//)
//
//println result.dump()
//
//assert widget == result

/*
    Attachment, Inline validation
 */

//File image = File.createTempFile('logo', '.png')
//URL urlToJar = new URL('https://mailcatcher.me/logo.png')
//ReadableByteChannel rbc = Channels.newChannel(urlToJar.openStream())
//FileOutputStream fos = new FileOutputStream(image)
//fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE)
//
//Attachment attachment = new Attachment(image.getAbsolutePath())
//Inline inline = new Inline('image1', image.getAbsolutePath())
//
//ef.sendEmail(
//        to: ['test@test.com'],
//        subject: 'test',
//        html: '<html><body>Some stuff <img src="cid:image1"></body></html>',
//        inline: [inline],
//        attachment: [attachment]
//)

/*
    Filter, Select, Sort validation
 */

//Filter filter = new Filter('jobName', 'like', '%job%')
//Select select = new Select('jobName')
//Sort sort = new Sort('jobName', 'ascending')
//
//result = ef.findObjects(
//        objectType: 'job',
//        filter: [filter],
//        select: [select],
//        sort: [sort]
//)
//
//println result.dump()

/*
    CfgMgrParameter, CloudProviderParameter
 */

//CfgMgrParameter cfgMgrParameter = new CfgMgrParameter('cfgMgrParameter', 'value')
//CloudProviderParameter cloudProviderParameter = new CloudProviderParameter('cloudProviderParameter', 'value')
//
//result = ef.createResourceTemplate(
//        projectName: 'Default',
//        resourceTemplateName: 'resourceTemplateName',
//        cloudProviderProjectName: 'Default',
//        cloudProviderParameter: [cloudProviderParameter],
//        cfgMgrParameter: [cfgMgrParameter]
//)
//
//println result.dump()

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

          property 'versionRange', value: '', {
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

      formalParameter 'ec_enforceDependencies', defaultValue: '0', {
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

//result = ef.createSnapshot(
//        projectName: 'testProj',
//        applicationName: 'testapp',
//        snapshotName: 'snapshot',
//        componentversions: [
//                new ComponentVersion('versionName', '1.0')
//        ])
//println result.dump()

/*
    Plugin parameter
 */

//result = ef.createComponent(
//        projectName: 'testProj',
//        componentName: 'component',
//        pluginKey: 'EC-Artifact',
//        pluginParameters: [
//                new PluginParameter('artifactName', 'artifact'),
//                new PluginParameter('artifactVersionLocationProperty', 'version'),
//                ]
//)
//
//println result.dump()

// TODO check plugin parameter

/*
    ResourcePhaseMapping, ResourcePoolPhaseMapping
 */

//ResourcePhaseMapping resourcePhaseMapping = new ResourcePhaseMapping('local')
//ResourcePoolPhaseMapping resourcePoolPhaseMapping = new ResourcePhaseMapping('local')

// TODO: Complete


/*
    TierResourceCount
 */

//ef.deleteTierMap(projectName: 'testProj', applicationName: 'testApp', environmentProjectName: 'testProj', environmentName: 'env')
//
//result = ef.createTierMap(
//        projectName: 'testProj',
//        applicationName: 'testApp',
//        environmentProjectName: 'testProj',
//        environmentName: 'env',
//        tierMapping: new TierMapping('Tier 1', 'envTier'))
//
//println result.dump()
//
//// TODO: Validate tierMapping


/*
    ProvisionParameter
 */

result = ef.createCluster(projectName: 'testProj', clusterName: 'cluster', provisionParameter: new ProvisionParameter('param', 'value'))
println result.dump()

