package de.innobox.gchimp
import java.util.Map;

import com.nwire.mailchimp.IMailChimpServices;
import com.nwire.mailchimp.MailChimpServiceFactory;

/**
 * @author Malte Hübner
 *
 */
class GchimpService {

	def mcServices
	
	def apiKey
		
	def defaultListId
		

	String login(String username, String password) {
		return mcServices.login(username, password)
	}

	/**
	 * Checks service availability, returns {@code PING_SUCCESS} if successful.
	 * 
	 * @return Ping response string
	 */
	String ping() {
		return mcServices.ping(this.apiKey)
	}

	/**
	 * Expire an apiKey
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	boolean apiKeyExpire(String username, String password) {
		return mcServices.apiKeyExpire(username, password, this.apiKey)
	}

	/**
	 * Returns the lists in the account. The result is an array of {@code
	 * Map<String, Object>}(could be casted individually but not as an array).
	 * 
	 * The fields included in each map: (See LIST_FIELD_ constants for values)
	 * <ul>
	 * <li>member_count (Double) - number of members</li>
	 * <li>cleaned_count (Double) - number of deleted members</li>
	 * <li>unsubscribe_count (Double) - number of unsubscribed members</li>
	 * <li>web_id (Integer) - id on the web URLs</li>
	 * <li>name (String) - Name as it appears in the web UI</li>
	 * <li>date_created (String)- date in the format "Feb 03, 2009 06:43 pm",
	 * parse with {@link MailChimpUtils.parseDate11}</li>
	 * <li>id (String) - this is the ID which is used for APIs which receive
	 * listId</li>
	 * </ul>
	 * 
	 * @return Array of {@code Map<String, Object>}
	 */
	Object[] lists() {
		return mcServices.lists(this.apiKey)
	}

	/**
	 * Returns the members of a list in the account. The result is an array of {@code
	 * Map<String, String>} (could be casted individually but not as an array).
	 * 
	 * The fields included in each map: (See MEMBER_FIELD_ constants for values)
	 * <ul>
	 * <li>email (String)</li>
	 * <li>timestamp (String) - date in the format "yyyy-MM-dd hh:mm:ss" , parse
	 * with {@link MailChimpUtils.parseDate}</li>
	 * </ul>
	 * 
	 * @param listId
	 *            returned from lists
	 * @param status
	 *            status of subscribers, see STATUS_ constants
	 * @param since
	 *            date to start from in the format "yyyy-MM-dd hh:mm:ss"
	 * @param start
	 *            start from member number
	 * @param limit
	 *            maximum number of members to return
	 * @return Array of {@code Map<String, String>}
	 */
	Object[] listMembers(String listId, String status, String since, int start, int limit) {
		return mcServices.listMembers(this.apiKey, listId, status, since, start, limit)
	}
	
	/**
	 * Returns the members of the default list in the account. The result is an array of {@code
	 * Map<String, String>} (could be casted individually but not as an array).
	 * 
	 * The fields included in each map: (See MEMBER_FIELD_ constants for values)
	 * <ul>
	 * <li>email (String)</li>
	 * <li>timestamp (String) - date in the format "yyyy-MM-dd hh:mm:ss" , parse
	 * with {@link MailChimpUtils.parseDate}</li>
	 * </ul>
	 * 
	 * @param status
	 *            status of subscribers, see STATUS_ constants
	 * @param since
	 *            date to start from in the format "yyyy-MM-dd hh:mm:ss"
	 * @param start
	 *            start from member number
	 * @param limit
	 *            maximum number of members to return
	 * @return Array of {@code Map<String, String>}
	 */	
	
	Object[] listMembers(String status, String since, int start, int limit) {
		return mcServices.listMembers(this.apiKey, this.defaultListId, status, since, start, limit)
	}

	/**
	 * Subscribes a new member to the default list.
	 * 
	 * If the member status is unsubscribed, this will return true but will not
	 * subscribe the member to the default list.
	 * 
	 * Merges should include the standard fields, see MERGE_FIELD_ constants.
	 * Other than that, may include user defined fields. Undefined fields are
	 * ignored. Dates should be formated using
	 * {@link MailChimpUtils.formateDate}
	 * 
	 * @param email
	 * @param emailType
	 * @param doubleOptin
	 *            boolean - if true, a confirmation email will be sent to the
	 *            subscriber and the subscriber will not be added until the
	 *            confirmation is received. Note that this email does not cost
	 *            email credits.
	 * @param update_existing boolean - flag to control whether a existing subscribers should be updated instead of throwing and error
	 * @param replace_interests boolean - flag to determine whether we replace the interest groups with the groups provided, or we add the provided groups to the member's interest groups (optional, defaults to true)
	 * @param boolean send_welcome boolean - if your double_optin is false and this is true, we will send your lists Welcome Email if this subscribe succeeds - this will *not* fire if we end up updating an existing subscriber. If double_optin is true, this has no effect. defaults to false.
	 * @return
	 * @return
	 * @throws MailChimpServiceException
	 *             When the subscriber is already subscribed to the list. The
	 *             message will be like
	 *  	
	 *             "me@example.com is already subscribed to list My List".
	 */

	boolean listSubscribe(String email, String emailType = IMailChimpServices.EMAIL_TYPE_HTML, boolean doubleOptin = true, boolean update_existing = false, boolean  replace_interests = false, boolean send_welcome = false) {
		return mcServices.listSubscribe(this.apiKey, this.defaultListId, email, [:], emailType, doubleOptin, update_existing, replace_interests, send_welcome)
	}		
	
	/**
	 * Subscribes a new member to the list.
	 * 
	 * If the member status is unsubscribed, this will return true but will not
	 * subscribe the member to the list.
	 * 
	 * Merges should include the standard fields, see MERGE_FIELD_ constants.
	 * Other than that, may include user defined fields. Undefined fields are
	 * ignored. Dates should be formated using
	 * {@link MailChimpUtils.formateDate}
	 * 
	 * @param listId
	 * @param email
	 * @param mergeVars
	 *            a {@code Map<String, String>} of arguments.
	 * @param emailType
	 * @param doubleOptin
	 *            boolean - if true, a confirmation email will be sent to the
	 *            subscriber and the subscriber will not be added until the
	 *            confirmation is received. Note that this email does not cost
	 *            email credits.
	 * @param update_existing boolean - flag to control whether a existing subscribers should be updated instead of throwing and error
	 * @param replace_interests boolean - flag to determine whether we replace the interest groups with the groups provided, or we add the provided groups to the member's interest groups (optional, defaults to true)
	 * @param boolean send_welcome boolean - if your double_optin is false and this is true, we will send your lists Welcome Email if this subscribe succeeds - this will *not* fire if we end up updating an existing subscriber. If double_optin is true, this has no effect. defaults to false.	 *            
	 * @return
	 * @return
	 * @throws MailChimpServiceException
	 *             When the subscriber is already subscribed to the list. The
	 *             message will be like
	 *             "me@example.com is already subscribed to list My List".
	 */
	boolean listSubscribe(String listId, String email, Map<String, String> mergeVars, String emailType = IMailChimpServices.EMAIL_TYPE_HTML, boolean doubleOptin = true, boolean update_existing = false, boolean replace_interests = false, boolean send_welcome = false) {
		return mcServices.listSubscribe(this.apiKey, listId, email, mergeVars, emailType, doubleOptin, update_existing, replace_interests, send_welcome)
	}

	/**
	 * Subscribes a new member to the default list.
	 * 
	 * If the member status is unsubscribed, this will return true but will not
	 * subscribe the member to the default list.
	 * 
	 * Merges should include the standard fields, see MERGE_FIELD_ constants.
	 * Other than that, may include user defined fields. Undefined fields are
	 * ignored. Dates should be formated using
	 * {@link MailChimpUtils.formateDate}
	 * 
	 * @param email
	 * @param mergeVars
	 *            a {@code Map<String, String>} of arguments.
	 * @param emailType
	 * @param doubleOptin
	 *            boolean - if true, a confirmation email will be sent to the
	 *            subscriber and the subscriber will not be added until the
	 *            confirmation is received. Note that this email does not cost
	 *            email credits.
	 * @param update_existing boolean - flag to control whether a existing subscribers should be updated instead of throwing and error
	 * @param replace_interests boolean - flag to determine whether we replace the interest groups with the groups provided, or we add the provided groups to the member's interest groups (optional, defaults to true)
	 * @param boolean send_welcome boolean - if your double_optin is false and this is true, we will send your lists Welcome Email if this subscribe succeeds - this will *not* fire if we end up updating an existing subscriber. If double_optin is true, this has no effect. defaults to false.	             
	 * @return
	 * @return
	 * @throws MailChimpServiceException
	 *             When the subscriber is already subscribed to the list. The
	 *             message will be like
	 *  	
	 *             "me@example.com is already subscribed to list My List".
	 */

	boolean listSubscribe(String email, Map<String, String> mergeVars, String emailType = IMailChimpServices.EMAIL_TYPE_HTML, boolean doubleOptin = true, boolean update_existing = false, boolean replace_interests = false, boolean send_welcome = false) {
		return mcServices.listSubscribe(this.apiKey, this.defaultListId, email, mergeVars, emailType, doubleOptin, update_existing, replace_interests, send_welcome)
	}	
	
	/**
	 * @param listId
	 * @param email
	 * @param deleteMember
	 * @param sendGoodbye
	 * @param sendNotify
	 * @return
	 */
	boolean listUnsubscribe(String listId, String email, boolean deleteMember = false, boolean sendGoodbye = true, boolean sendNotify = false) {
		return mcServices.listUnsubscribe(this.apiKey, listId, email, deleteMember, sendGoodbye, sendNotify)
	}
	
	/**
	 * @param email
	 * @param deleteMember
	 * @param sendGoodbye
	 * @param sendNotify
	 * @return
	 */
	boolean listUnsubscribe(String email, boolean deleteMember = false, boolean sendGoodbye = true, boolean sendNotify = false) {
		return mcServices.listUnsubscribe(this.apiKey, this.defaultListId, email, deleteMember, sendGoodbye, sendNotify)
	}	

	/**
	 * Updates a list member. The member must be in the list, in status
	 * "subscribed", otherwise an exception is thrown.
	 * 
	 * @param id
	 * @param emailAddress
	 * @param mergeVars
	 * @param emailType
	 * @param replaceInterests
	 * @return @ throws MailChimpServiceException When the email is not found in
	 *         the list or the user is unsubscribed from the list. The message
	 *         will be like
	 *         "The email address "me@example.com" does not belong to this list"
	 *         .
	 */
	boolean listUpdateMember(String emailAddress, Map<String, String> mergeVars, String emailType = IMailChimpServices.EMAIL_TYPE_HTML, boolean replaceInterests = false) {
		return mcServices.listUpdateMember(this.apiKey, this.defaultListId, emailAddress, mergeVars, emailType, replaceInterests)
	}

	/**
	* Updates a list member. The member must be in the list, in status
	* "subscribed", otherwise an exception is thrown.
	*
	* @param id
	* @param emailAddress
	* @param mergeVars
	* @param emailType
	* @param replaceInterests
	* @return @ throws MailChimpServiceException When the email is not found in
	*         the list or the user is unsubscribed from the list. The message
	*         will be like
	*         "The email address "me@example.com" does not belong to this list"
	*         .
	*/
   boolean listUpdateMember(String listId, String emailAddress, Map<String, String> mergeVars, String emailType = IMailChimpServices.EMAIL_TYPE_HTML, boolean replaceInterests = false) {
	   return mcServices.listUpdateMember(this.apiKey, listId, emailAddress, mergeVars, emailType, replaceInterests)
   }
	
	/**
	 * Returns the information on a single subscriber in the list. The returned
	 * map contains all the subscriber details. See MEMBER_INFO_ constants for a
	 * list of fields.
	 * 
	 * Note that all fields are strings, except for the {@code
	 * MEMBER_INFO_MERGES} which is a {@code Map<String, String>} and for lists
	 * which is an array of objects (currently, it seems to be empty).
	 * 
	 * @param listId
	 * @param emailAddress
	 * @return Member details in a Map.
	 * @throws MailChimpServiceException
	 *             when the email is not found in the list. The message will be
	 *             like "me@example.com is not a member of My List" or
	 *             "There is no record of "me@example.com" in the database".
	 */
	Map<String, Object> listMemberInfo(String listId, String emailAddress) {
		return mcServices.listMemberInfo(this.apiKey, listId, emailAddress)
	}

	/**
	 * Returns the information on a single subscriber in the default list. The returned
	 * map contains all the subscriber details. See MEMBER_INFO_ constants for a
	 * list of fields.
	 * 
	 * Note that all fields are strings, except for the {@code
	 * MEMBER_INFO_MERGES} which is a {@code Map<String, String>} and for lists
	 * which is an array of objects (currently, it seems to be empty).
	 * 
	 * @param emailAddress
	 * @return Member details in a Map.
	 * @throws MailChimpServiceException
	 *             when the email is not found in the list. The message will be
	 *             like "me@example.com is not a member of My List" or
	 *             "There is no record of "me@example.com" in the database".
	 */
	Map<String, Object> listMemberInfo(String emailAddress) {
		return mcServices.listMemberInfo(this.apiKey, this.defaultListId, emailAddress)
	}	
	
	/**
	 * Create a new draft campaign to send
	 * 
	 * @param type
	 *            see CAMPAIGN_TYPE_ constants
	 * @param optionssee
	 *            CAMPAIGN_OPTION_ constants
	 * @param content
	 *            see CAMPAIGN_CONTENT_ constants
	 * @param segmentOpts
	 *            optional. For list segmentation.
	 * @param typeOpts
	 *            optional. Extra options for RSS campaign or A/B split
	 *            campaigns.
	 * @return
	 */
	String campaignCreate(String type,
			Map<String, Object> options, Map<String, String> content,
			Map<String, String> segmentOpts, Map<String, String> typeOpts) {
		return mcServices.campaignCreate(this.apikey, type, options, content, segmentOpts, typeOpts)
	}

	/**
	 * Retrieve all templates defined for your user account
	 * 
	 * @param apikey
	 * @return Array of {@code Map<String, String>}
	 */
	Object[] campaignTemplates() {
		return mcServices.campaignTemplates(this.apikey)
	}

	/**
	 * Send a given campaign immediately
	 * 
	 * @param apikey
	 * @param cid
	 *            Campaign ID (returned from {@link campaignCreate}
	 * @return
	 */
	boolean campaignSendNow(String cid) {
		return mcServices.campaignSendNow(this.apikey, cid)
	}
	
}
