import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()
ef.login('10.200.1.10', 'admin', 'changeme')

// Make your API calls
def result = ef.getServerInfo()

// Handle response
println result.dump()
