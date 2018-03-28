import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.*

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')
ef.createProject(projectName: 'testProj')

/*
* AttributeDataType, AttributePath, Color, LinkParameter, VisualizationProperty validations
* */

VisualizationProperty visualizationProperty = new VisualizationProperty('testProjColor', '#F00')
AttributeDataType attributeDataTypeXAxis = new AttributeDataType('xAxis', 'DATE')
AttributeDataType attributeDataTypeYAxis = new AttributeDataType('yAxis', 'NUMBER')
AttributeDataType attributeDataTypeGroups = new AttributeDataType('groups', 'STRING')
AttributePath attributePathXAxis = new AttributePath('xAxis', 'deployment_date_label')
AttributePath attributePathYAxis = new AttributePath('yAxis', 'deployment_outcome_count')
AttributePath attributePathGroups = new AttributePath('groups', 'deployment_outcome')
LinkParameter linkParameter = new LinkParameter('externalUrl','https://www.facebook.com/')
Color successColor = new Color('success', '#70b723')

ef.deleteDashboard(projectName: 'testProj', dashboardName: 'dashboardName')
ef.deleteReport(projectName: 'testProj', reportName: 'reportName')

def result = ef.createDashboard(
        projectName:    'testProj',
        dashboardName:  'dashboardName',
        description:    'test description',
        layout:         'FLOW',
        type:           'STANDARD',
        phases:         ['phase1', 'phase2']
)

println result.dump()

result = ef.createReport(
        projectName:    'testProj',
        reportName:     'reportName',
        definition:     'definition',
        description:    'description',
        parameters:     'parameters',
        title:          'title',
        uri:            'uri'
)

println result.dump()
println ef.getVersions().dump()

result = ef.createWidget(
        projectName:            'testProj',
        dashboardName:          'dashboardName',
        widgetName:             'widgetName',
        attributeDataTypes:      [attributeDataTypeXAxis, attributeDataTypeYAxis, attributeDataTypeGroups],
        attributePaths:          [attributePathXAxis, attributePathYAxis, attributePathGroups],
        colors:                  [successColor],
        description:            'description',
        iconUrl:                'iconUrl',
        linkParameters:          [linkParameter],
        linkTarget:             'External',
        orderIndex:             0,
        phase:                  'phase1',
        reportName:             'reportName',
        reportProjectName:      'testProj',
        section:                'PHASE_DETAIL',
        title:                  'title',
        visualization:          'STACKED_AREA_CHART',
        visualizationProperties:  [visualizationProperty],
)

println result.dump()


widget = ef.getWidget(
        projectName:            'testProj',
        dashboardName:          'dashboardName',
        widgetName:             'widgetName',
)

println result.dump()

assert widget == result

ef.deleteProject(projectName: 'testProj')

