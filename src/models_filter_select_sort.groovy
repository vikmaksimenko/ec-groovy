import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.Filter
import com.electriccloud.client.groovy.models.Select
import com.electriccloud.client.groovy.models.Sort

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

/*
    Filter, Select, Sort validation
 */

Filter filter = new Filter('jobName', 'like', '%job%')
Select select = new Select('jobName')
Sort sort = new Sort('jobName', 'ascending')

result = ef.findObjects(
        objectType: 'job',
        filter: [filter],
        select: [select],
        sort: [sort]
)

println result.dump()

