grails.project.work.dir = 'target'

//grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {

	inherits 'global'   // inherit Grails' default dependencies
	log 'warn'          // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'

	repositories {
		grailsPlugins()
		grailsHome()
		grailsCentral()
		mavenCentral()
	}
	dependencies {
		compile("commons-codec:commons-codec:1.3")
		compile("commons-httpclient:commons-httpclient:3.1")
		compile("commons-logging:commons-logging:1.1") { 
			export = false      // Interferes with the Cache-Plugin in Grails 2.3.x, see: http://jira.grails.org/browse/GPCACHE-33
		}
		compile("org.apache.ws.commons.util:ws-commons-util:1.0.2")
		compile("org.apache.xmlrpc:xmlrpc-client:3.1.3")
		compile("org.apache.xmlrpc:xmlrpc-common:3.1.3")
		compile("org.apache.xmlrpc:xmlrpc-server:3.1.3")
	}
	plugins {
		build ':release:2.2.1', ':rest-client-builder:1.0.3', { 
			export = false 
		}
	}
}

grails.release.scm.enabled = false
grails.project.repos.default = "localGrailsPlugins"
