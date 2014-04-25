package de.innobox.gchimp
import com.nwire.mailchimp.IMailChimpServices
import com.nwire.mailchimp.MailChimpServiceException
import com.nwire.mailchimp.MailChimpServiceException.ErrorType

/**
 * @author Malte Hübner
 *
 */

class GchimpController {
	
	def gchimpService
	
	def afterInterceptor = { model ->
		def apiKey = gchimpService.apiKey
		def keySize = apiKey.size()
		def maskSize = keySize - 8
		
		model.apiKey = '*' * maskSize  + apiKey[maskSize..keySize-1]
		model.defaultListId = gchimpService.defaultListId
	}
	
	def index = {								
		def ping = gchimpService.ping()
		
		if (IMailChimpServices.PING_SUCCESS.equals(ping)) {
			flash.message = "MailChimp connection pinged successfully"
		} else {
			flash.message = "Failed to ping MailChimp, response: " + ping
		}
	}
	
	def lists = {
		def lists = gchimpService.lists();
		
		return [lists:lists]
	}
	
	def memberInfo = {
		def memberInfo
		
		if(!params.email) {
			throw new IllegalArgumentException("Param email is missing")
		}
		try {
			memberInfo = gchimpService.listMemberInfo(params.email);
		} catch(MailChimpServiceException ex) {
			if(ex.getErrorType() == ErrorType.Email_NotExists ||
			ex.getErrorType() == ErrorType.Email_AlreadyUnsubscribed ||
			ex.getErrorType() == ErrorType.List_NotSubscribed) {
				// do sth
			} else {
				log.error "GChimp Taglib Exception", ex
			}
		}
		
		return [memberInfo:memberInfo]
	}
	
	def subscribe = {
		if(!params.email) {
			throw new IllegalArgumentException("Param email is missing")
		}
		
		def res = gchimpService.listSubscribe(params.email);
		
		if(res) {
			flash.message = 'subscription successful'
		} else {
			flash.message = 'subscription not successful'
		}
		
		return [:]
	}	
	
	def unsubscribe = {
		if(!params.email) {
			throw new IllegalArgumentException("Param email is missing")
		}
		
		def res = gchimpService.listUnsubscribe(params.email);
		
		if(res) {
			flash.message = 'subscription successful'
		} else {
			flash.message = 'subscription not successful'
		}
		
		return [:]
	}
	
	def updateMember = {
		if(!params.email) {
			throw new IllegalArgumentException("Param email is missing")
		}
		
		if(!params.merges) { 
			throw new IllegalArgumentException("Param merges is missing")
		}		
		
		def updated = gchimpService.listUpdateMember(params.email, params.merges, 'html', false)
		
		if(updated) {
			flash.message = 'update member successful'
		} else {
			flash.message = 'update member not successful'
		}
		
		return [email: params.email]
	}
	
	def updateMemberRemote = { GchimpSubscriberForm form ->		
		if(!form.hasErrors()) {		
			def updated
			
			try {				
				updated = gchimpService.listUpdateMember(form.email, form.merges)
			} catch(MailChimpServiceException ex) {
				if(ex.getErrorType() == ErrorType.Email_NotExists ) {
					flash.gchimpError = 'gchimp.error.email.not.exists'
				}
				if(ex.getErrorType() == ErrorType.Email_NotSubscribed ) {
					flash.gchimpError = 'gchimp.error.email.not.subscribed'
				} else {
					log.error "Gchimp API exception: ${ex.errorType} (${ex.code})", ex
					flash.gchimpError = 'gchimp.general.error'
				}
			}
			
			if(updated) {
				flash.gchimpSuccess = 'gchimp.success.memberUpdate'
			}
		}
		
		render(template: 'updateMemberRemoteForm', model: [form: form], plugin: 'gchimp')
	}
	
	def subscribeRemote = { GchimpSubscriberForm form ->
		if(!form.hasErrors()) {
			def result
			
			try {		
				result = gchimpService.listSubscribe(form.email, form.merges)
			} catch(MailChimpServiceException ex) {
				if(ex.getErrorType() == ErrorType.Email_AlreadySubscribed ||
				ex.getErrorType() == ErrorType.List_AlreadySubscribed ) {
					flash.gchimpError = 'gchimp.error.email.already.subscribed'
				} else {
					log.error "Gchimp API exception: ${ex.errorType} (${ex.code})", ex
					flash.gchimpError = 'gchimp.general.error'
				}
			}
			
			if(result) {
				flash.gchimpSuccess = 'gchimp.success.subscription'
			}
		} 
			
		render(template: 'remoteSubscriptionForm', model: [form: form], plugin: 'gchimp')
	}
}
