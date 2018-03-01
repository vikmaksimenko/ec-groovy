import com.electriccloud.client.groovy.apis.ServerApi
import com.electriccloud.client.groovy.apis.SessionApi

SessionApi session = new SessionApi()
session.login(url: '192.168.4.133', userName: 'admin', password: 'changeme')

ServerApi api = new ServerApi()
println(api.versions.serverVersion.version)
