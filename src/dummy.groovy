// Run this script from job step

import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

// findArtifactVersions
ef.deleteArtifactVersion(artifactVersionName: 'test:test:1.0')
result = ef.createArtifactVersion(version: '1.0', artifactName: 'test:test')
println "Created artifact version: ${result.dump()}"

result = ef.findArtifactVersions(artifactName: 'test:test')
println "Find artifact version: ${result.dump()}"
ef.deleteArtifactVersion(artifactVersionName: 'test:test:1.0')

println new ElectricFlow().findArtifactVersions(artifactName: 'test:test').dump()
