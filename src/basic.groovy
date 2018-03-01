import com.electriccloud.client.groovy.ElectricFlow

ElectricFlow ef = new ElectricFlow()
ef.login('https://192.168.4.133:8443', 'admin', 'changeme')

// Make your API calls
def result = ef.getServerInfo()

// Handle response
println result.dump()
