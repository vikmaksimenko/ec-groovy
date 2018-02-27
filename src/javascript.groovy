import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

def result = ef.evalScript(value: "(api.createProject( { 'projectName':'testJSApiProjects' } )).project.projectName ;")
println(result)
assert result.value == 'testJSApiProjects'

ef.deleteProject(projectName: 'testJSApiProjects')
