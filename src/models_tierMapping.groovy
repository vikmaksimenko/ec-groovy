import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.TierMapping

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')
ef.createProject(projectName: 'testProj')
ef.createApplication(projectName: 'testProj', applicationName: 'testApp')
ef.createApplicationTier(projectName: 'testProj', applicationName: 'testApp', applicationTierName: 'testAppTier')
ef.createEnvironment(projectName: 'testProj', environmentName: 'testEnv')
ef.createEnvironmentTier(projectName: 'testProj', environmentName: 'testEnv', environmentTierName: 'testEnvTier')

/*
    TierMapping
 */

result = ef.createTierMap(
        projectName: 'testProj',
        applicationName: 'testApp',
        environmentName: 'testEnv',
        environmentProjectName: 'testProj',
        tierMappings: [new TierMapping('testAppTier', 'testEnvTier')])

assert result.tierMap.tierMappings.tierMapping[0].environmentTierName == 'testEnvTier'
assert result.tierMap.tierMappings.tierMapping[0].applicationTierName == 'testAppTier'

println result

ef.deleteProject(projectName: 'testProj')
