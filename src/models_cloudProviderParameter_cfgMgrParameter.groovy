import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.CfgMgrParameter
import com.electriccloud.client.groovy.models.CloudProviderParameter

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')
ef.createProject(projectName: 'testProj')

/*
    CfgMgrParameter, CloudProviderParameter
 */

ef.createProcedure(projectName: 'testProj', procedureName: 'testProc')
ef.createFormalParameter(projectName: 'testProj', procedureName: 'testProc', formalParameterName: 'param')
ef.createProperty(projectName: 'testProj', propertyType: 'sheet', propertyName: 'ec_configurationmanagement_plugin')

result = ef.createResourceTemplate(
        projectName: 'testProj',
        resourceTemplateName: 'resourceTemplate2',
        cloudProviderParameters: [new CloudProviderParameter('config', 'val1')],
        cloudProviderPluginKey: 'EC-EC2',
        cloudProviderProcedure: 'DeleteConfiguration',
        cfgMgrParameters: [new CfgMgrParameter('param', 'val1')],
        cfgMgrProjectName: 'testProj',
        cfgMgrProcedure: 'testProc'
)

def cloudParam = result.resourceTemplate.cloudProviderParameters.parameterDetail[0]
assert cloudParam.parameterName == 'config'
assert cloudParam.parameterValue == 'val1'

def cfgMgrParam = result.resourceTemplate.cfgMgrParameters.parameterDetail[0]
assert cfgMgrParam.parameterName == 'param'
assert cfgMgrParam.parameterValue == 'val1'

println result

ef.deleteProject(projectName: 'testProj')
