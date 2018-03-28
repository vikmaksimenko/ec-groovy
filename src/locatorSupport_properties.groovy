// Run this script from job step

import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()

def result

// property management
result = ef.createProperty(propertyName: 'testProp', value: 'test')
assert result.property.value == 'test'
println "Created property: ${result.dump()}"

result = ef.getProperty(propertyName: 'testProp')
println "Got property: ${result.dump()}"
assert result.property.value == 'test'

result = ef.setProperty(propertyName: 'testProp', value: '1')
println "Set property: ${result.dump()}"
assert result.property.value == '1'

result = ef.getProperty(propertyName: 'testProp')
println "Got updated property: ${result.dump()}"
assert result.property.value == '1'

result = ef.incrementProperty(propertyName: 'testProp')
println "Got incremented property: ${result.dump()}"
assert result.property.value == '2'

result = ef.modifyProperty(propertyName: 'testProp', newName: 'testProp2')
println "Got modified property: ${result.dump()}"
assert result.property.propertyName == 'testProp2'

ef.getProperty(propertyName: 'testProp2')
println "Got modified property: ${result.dump()}"
assert result.property.propertyName == 'testProp2'

ef.deleteProperty(propertyName: 'testProp')
ef.deleteProperty(propertyName: 'testProp2')

result = ef.getProperties()
println "Got properties: ${result.dump()}"
// TODO: Update after http://jira/browse/NMB-25933
//assert result.property.propertyName == 'testProp2'

