import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.ResourcePhaseMapping
import com.electriccloud.client.groovy.models.ResourcePoolPhaseMapping

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')
ef.createProject(projectName: 'testProj')

ef.createEnvironment(projectName: 'testProj', environmentName: 'testEnv')
ef.createRollingDeployPhase(projectName: 'testProj', environmentName: 'testEnv', rollingDeployPhaseName: 'testPhase')
ef.createRollingDeployPhase(projectName: 'testProj', environmentName: 'testEnv', rollingDeployPhaseName: 'testPoolPhase')


/*
    ResourcePhaseMapping
 */

ResourcePhaseMapping resourcePhaseMapping = new ResourcePhaseMapping('local', 'testPhase')
ef.createEnvironmentTier(projectName: 'testProj', environmentName: 'testEnv', environmentTierName: 'envtier', resourcePhaseMappings: [resourcePhaseMapping])

result = ef.getResourcesInEnvironmentTier(projectName: 'testProj', environmentName: 'testEnv', environmentTierName: 'envtier')
assert result.resource[0].resourceName == 'local'

println result
/*
    ResourcePoolPhaseMapping
 */

ef.deleteResourcePool(resourcePoolName: 'testPool')
ef.createResourcePool(resourcePoolName: 'testPool')
ResourcePoolPhaseMapping resourcePoolPhaseMapping = new ResourcePoolPhaseMapping('testPool', 'testPoolPhase')
ef.createEnvironmentTier(projectName: 'testProj', environmentName: 'testEnv', environmentTierName: 'envtier2', resourcePoolPhaseMappings: [resourcePoolPhaseMapping])


result = ef.getResourcePoolsInEnvironmentTier(projectName: 'testProj', environmentName: 'testEnv', environmentTierName: 'envtier2')
println result
assert result.resourcePool[0].resourcePoolName == 'testPool'

ef.deleteResourcePool(resourcePoolName: 'testPool')
ef.deleteProject(projectName: 'testProj')
