import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.PluginParameter
import com.electriccloud.client.groovy.models.ProvisionParameter

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')
ef.createProject(projectName: 'testProj')
ef.createApplication(projectName: 'testProj', applicationName: 'testApp')
ef.createApplicationTier(projectName: 'testProj', applicationName: 'testApp', applicationTierName: 'testAppTier')
ef.createEnvironment(projectName: 'testProj', environmentName: 'testEnv')
ef.createEnvironmentTier(projectName: 'testProj', environmentName: 'testEnv', environmentTierName: 'testEnvTier')

/*
    PluginParameter
 */

result = ef.createComponent(
        projectName: 'testProj',
        componentName: 'testComp',
        pluginKey: 'EC-Maven',
        pluginParameters: [
                new PluginParameter('artifact', '1.0'),
        ])
println result

def contentDetailsProp = ef.getProperties(propertySheetId: result.component.propertySheetId).propertySheet.property.find {it.propertyName == 'ec_content_details'}
assert ef.getProperties(propertySheetId: contentDetailsProp.propertySheetId).propertySheet.property.find {it.propertyName == 'artifact'}.value == '1.0'

ef.deleteProject(projectName: 'testProj')
