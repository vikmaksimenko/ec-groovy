import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.*

ElectricFlow ef = new ElectricFlow()
//ef.login('192.168.4.133', 'admin', 'changeme')

/*
* AttributeDataType, AttributePath, LinkParameter, VisualizationProperty validations
* */

VisualizationProperty visualizationProperty = new VisualizationProperty('defaultColor', '#F00')
AttributeDataType attributeDataTypeXAxis = new AttributeDataType('xAxis', 'DURATION')
AttributeDataType attributeDataTypeYAxis = new AttributeDataType('yAxis', 'DURATION')
AttributePath attributePathXAxis = new AttributePath('xAxis', 'duration')
AttributePath attributePathYAxis = new AttributePath('yAxis', 'duration')
LinkParameter linkParameter = new LinkParameter('externalUrl','https://www.facebook.com/')
Color automatedColor = new Color('Automated', '#DA833E')
Color manualColor = new Color('Manual', '#eb1c24')

ef.deleteDashboard(projectName: 'Default', dashboardName: 'dashboardName')
ef.deleteReport(projectName: 'Default', reportName: 'reportName')

def result = ef.createDashboard(
        projectName:    'Default',
        dashboardName:  'dashboardName',
        description:    'test description',
        layout:         'FLOW',
        type:           'STANDARD',
        phases:         ['phase1', 'phase2']
)

println result.dump()

result = ef.createReport(
        projectName:    'Default',
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
        projectName:            'Default',
        dashboardName:          'dashboardName',
        widgetName:             'widgetName',
        attributeDataTypes:      [attributeDataTypeXAxis, attributeDataTypeYAxis],
        attributePaths:          [attributePathXAxis, attributePathYAxis],
        colorRanges:            'colorRanges',
        colors:                  [automatedColor, manualColor],
        description:            'description',
        iconUrl:                'iconUrl',
        linkParameters:          [linkParameter],
        linkTarget:             'External',
        orderIndex:             0,
        phase:                  'phase1',
        reportName:             'reportName',
        reportProjectName:      'Default',
        section:                'PHASE_DETAIL',
        title:                  'title',
        visualization:          'AREA_CHART',
        visualizationProperty:  [visualizationProperty],
)

println result.dump()

result = ef.getWidget(
        projectName:            'Electric Cloud',
        dashboardName:          'Releases',
        widgetName:             'TopLongestTasks'
)

assert result.widget.widgetName == 'TopLongestTasks'

// TODO: add validation for visualisations, attributes and links

println result.dump()

