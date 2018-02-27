import com.electriccloud.client.groovy.ElectricFlow

import com.electriccloud.client.groovy.apis.WidgetApi

import com.electriccloud.client.groovy.models.*

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

VisualizationProperty visualizationProperty = new VisualizationProperty('defaultColor', '#F00')
AttributeDataType attributeDataTypeXAxis = new AttributeDataType('xAxis', 'DURATION')
AttributeDataType attributeDataTypeYAxis = new AttributeDataType('yAxis', 'DURATION')
AttributePath attributePathXAxis = new AttributePath('xAxis', 'duration')
AttributePath attributePathYAxis = new AttributePath('yAxis', 'duration')

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

result = ef.createWidget(
        projectName:            'Default',
        dashboardName:          'dashboardName',
        widgetName:             'widgetName',
        attributeDataType:      [attributeDataTypeXAxis, attributeDataTypeYAxis],
        attributePath:          [attributePathXAxis, attributePathYAxis],
        description:            'description',
        iconUrl:                'iconUrl',
        linkTarget:             'CUSTOM',
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
