class GchimpGrailsPlugin {
    def version = "0.4.1"
    def grailsVersion = "2.2 > *"
    def loadAfter = ['services']
    def pluginExcludes = [
            "grails-app/views/**",
            "grails-app/conf/Config.groovy",
            "grails-app/controllers/GchimpController.groovy",
			"grails-app/i18n/**"
    ]

    def author = "innobox GmbH"
    def authorEmail = "support@innobox.de"
    def title = "gchimp Plugin"
    def description = '''The grails gchimp Plugin is an interface for the MailChimp 1.2. API.'''
    def documentation = "http://grails.org/plugin/gchimp"

    def doWithSpring = {
		def apiUrl = application.config.gchimp.apiUrl ?: 'https://api.mailchimp.com:443/1.2/'
		def encoding = application.config.gchimp.encoding ?: 'UTF-8'

    		gchimpService(de.innobox.gchimp.GchimpService) {
    			mcServices = com.nwire.mailchimp.MailChimpServiceFactory.getMailChimpServices(apiUrl, encoding)
    			apiKey = application.config.gchimp?.apiKey
    			defaultListId = application.config.gchimp?.defaultListId
    		}
    }
}
