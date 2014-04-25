package de.innobox.gchimp

import grails.validation.Validateable

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.MapUtils

@Validateable
class GchimpSubscriberForm implements Serializable  {

	String email
	
	String email_type = 'text'
	
	String listId
	
	Map merges = MapUtils.lazyMap([:], FactoryUtils.constantFactory(''))
		
	static constraints = {
		email(nullable:false, blank:false, email:true)
		email_type(nullable: false, blank: false)
		listId(nullable: true, blank: true)
		merges(nullable: true, validator: { merges, form, errors ->
			merges?.each {
				if(it.key && !it.value) {
					errors.rejectValue('merges', 'de.innobox.gchimp.GchimpSubscriberForm.merges.'+ it.key + '.blank');
				}
			}
		})
	}
}