// Run this script from job step

import com.electriccloud.client.groovy.ElectricFlow

import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

ElectricFlow ef = new ElectricFlow()

// Download Joda Time jar (http://www.joda.org/joda-time/)
File lib = File.createTempFile('joda-time-2.9.9-', '.jar')
URL urlToJar = new URL('http://central.maven.org/maven2/joda-time/joda-time/2.9.9/joda-time-2.9.9.jar')
ReadableByteChannel rbc = Channels.newChannel(urlToJar.openStream())
FileOutputStream fos = new FileOutputStream(lib)
fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE)

// Create job step that will use downloaded jar
ef.createJobStep(
        jobStepName: 'daysToNewYear',
        command: '''
import org.joda.time.Days
import org.joda.time.LocalDate

LocalDate today = new LocalDate()
LocalDate newYear = today.plusYears(1).withDayOfYear(1)
println "${Days.daysBetween(today, newYear).getDays()} days to New Year"
''',
        shell: "ec-groovy -cp ${lib.getAbsolutePath()}"
)
