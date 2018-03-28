import com.electriccloud.client.groovy.ElectricFlow
import com.electriccloud.client.groovy.models.Attachment
import com.electriccloud.client.groovy.models.Inline

import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

ElectricFlow ef = new ElectricFlow()
ef.login('192.168.4.133', 'admin', 'changeme')

/*
    Attachment, Inline validation
 */

File image = File.createTempFile('logo', '.png')
URL urlToJar = new URL('https://mailcatcher.me/logo.png')
ReadableByteChannel rbc = Channels.newChannel(urlToJar.openStream())
FileOutputStream fos = new FileOutputStream(image)
fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE)

Attachment attachment = new Attachment(image.getAbsolutePath())
Inline inline = new Inline('image1', image.getAbsolutePath())

// BLOCKED BY http://jira.electric-cloud.com/browse/NMB-25917

ef.sendEmail(
        to: ['test@test.com'],
        subject: 'test',
        html: '<html><body>Some stuff <img src="cid:image1"></body></html>',
        inline: [inline],
        attachment: [attachment]
)

