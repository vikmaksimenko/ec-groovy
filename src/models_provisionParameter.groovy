import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.ProvisionParameter

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

ef.deleteProject(projectName: 'testProj')
ef.createProject(projectName: 'testProj')

/*
    ProvisionParameter
 */

result = ef.createCluster(
        projectName: 'testProj',
        clusterName: 'cluster',
        provisionParameters: [
                new ProvisionParameter('param', 'value'),
                new ProvisionParameter('param2', 'value2')
        ])
println result

params = result.cluster.provisionParameters.parameterDetail
assert params.size == 2
assert params*.parameterName == ["param", "param2"]
assert params*.parameterValue == ["value", "value2"]

ef.deleteProject(projectName: 'testProj')

