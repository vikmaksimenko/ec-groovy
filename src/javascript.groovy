import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

def result = ef.evalScript(value: "(api.createProject( { 'projectName':'testJSApiProjects' } )).project.projectName ;")
println(result)
assert result.value == 'testJSApiProjects'

ef.deleteProject(projectName: 'testJSApiProjects')
