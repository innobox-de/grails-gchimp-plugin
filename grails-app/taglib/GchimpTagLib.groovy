import com.nwire.mailchimp.IMailChimpServices
import com.nwire.mailchimp.MailChimpServiceException
import com.nwire.mailchimp.MailChimpServiceException.ErrorType

class GchimpTagLib {

	static namespace = "gchimp"
	
	def gchimpService
	
	def ifSubscribed = { attrs, body ->
		def email = attrs['email']
		def listId = attrs['listId']
		def memberInfo
		
		pageScope.memberInfo = null
		
		if(!email) {
			log.error "Attribute email is missing!"
			return
		}
		
		try {
			if(listId) {
				memberInfo = gchimpService.listMemberInfo(listId, email)			
			 } else {
			 	memberInfo = gchimpService.listMemberInfo(email)
			 }
		} catch(MailChimpServiceException ex) {
			if(ex.getErrorType() == ErrorType.Email_NotExists ||
				ex.getErrorType() == ErrorType.Email_AlreadyUnsubscribed ||
				ex.getErrorType() == ErrorType.List_NotSubscribed) {					
			} else {
				log.error "GChimp Taglib Exception", ex				
			}
		}
		
		if(memberInfo && memberInfo[IMailChimpServices.MEMBER_INFO_STATUS] == IMailChimpServices.STATUS_SUBSCRIBED) {
			pageScope.memberInfo = memberInfo
			out << body((memberInfo):memberInfo)
		}		
	}
	
	def elseSubscribed = { attrs, body ->
		if(!pageScope.getProperty("memberInfo")) {
			out << body()
		}
	}
}
