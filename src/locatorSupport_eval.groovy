//
// Run this script from job step

import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()
def result

// EvalScript
println "EvalScript: ${ef.evalScript(value: 'myProject.projectName')}"

// EvalDsl
result = ef.evalDsl(dsl: "property 'dslProp', { value = 'value' }")
println "Eval DSL: ${result.dump()}"

result = ef.getProperty(propertyName: 'dslProp')
println "Got property created with DSL: ${result.dump()}"
assert result.property.value == 'value'

// expandString
println "Expanded log: ${ef.expandString(value: '$' + '[/myWorkspace/agentUncPath]/$' + '[logFileName]')}"

