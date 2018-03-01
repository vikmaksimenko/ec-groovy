import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

def description = '''
English: Hello World
Russian: Привет мир
Chines: 你好，世界
Arabic: مرحبا بالعالم
And a few symbols: ♠☀☁☂☃☄★☆☇☈☉☊☋☌☍☎☏☐☑☒☓☔☕
'''

// cleanup
ef.deleteProcedure(projectName: 'Default', procedureName: 'UTF-8 Check')

def result = ef.createProcedure(
        projectName: 'Default',
        procedureName: 'UTF-8 Check',
        description: description
)

println result.dump()

assert result.procedure.description == description
