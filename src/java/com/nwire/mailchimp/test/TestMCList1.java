package com.nwire.mailchimp.test;

import java.lang.reflect.UndeclaredThrowableException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nwire.mailchimp.IMailChimpServices;
import com.nwire.mailchimp.MailChimpServiceFactory;
import com.nwire.mailchimp.MailChimpUtils;

@SuppressWarnings("unchecked")
public class TestMCList1 {

	private final Log	logger	= LogFactory.getLog(getClass());

	public static void main(final String[] args) {
		final TestMCList1 testMCList1 = new TestMCList1();

		testMCList1.run();
	}

    //API Key - see http://admin.mailchimp.com/account/api or run login() once
	private final String		apiKey		= "KEY";

    // A List Id to run examples against. use lists() to view all
    // Also, login to MC account, go to List, then List Tools, and look for the List ID entry
	private final String		listId		= "ID";

	private final String		apiUrl 		= "https://api.mailchimp.com:443/1.2/";
		
	private final String		encoding 	= "UTF-8";
	
	private IMailChimpServices	mcServices	= null;
	
	private void run() {
		initialize();
		getLists();
		//getDetails();
		addUser();
		listMembers();
	}

	private void listMembers() {
		final Object[] listMembers = mcServices.listMembers(apiKey, listId,
				IMailChimpServices.STATUS_SUBSCRIBED, "2009-01-01 00:00:00", 0,
				1000);
		System.out
				.println("listMembers got " + listMembers.length + " members");
		for (final Object member : listMembers) {
			System.out.print("\tMember: ");
			final Map<String, Object> map = (Map<String, Object>) member;
			for (final Object key : map.keySet()) {
				final Object value = map.get(key);

				if (key.equals(IMailChimpServices.MEMBER_FIELD_TIMESTAMP)) {
					try {
						final Date parsedDate = MailChimpUtils.parseDate(value
								.toString());
						System.out.print(key + "=" + parsedDate + "\t");
					} catch (final ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.print(key + " = " + value + "("
							+ value.getClass().getSimpleName() + ")\t");
				}
			}
			System.out.println();

		}

	}

	private void addUser() {
		final Map<String, String> merges = new HashMap<String, String>();

		merges.put("LNAME", "last11");
		merges.put("FNAME", "first");
//		merges.put("INTERESTS", "Java");
		merges.put("EMAIL", "test5@example.com");
		merges.put("OPTINIP", "127.0.0.1");
//		merges.put("ORG", "X Corp");
//		merges.put("LEXP", "03/31/2009");

		final boolean listSubscribe = mcServices.listSubscribe(apiKey,
				listId, "test5@example.com", merges,
				IMailChimpServices.EMAIL_TYPE_HTML, false, false, false, false);
		System.out.println("listSubscribe: " + listSubscribe);
	}

	private void getLists() {
		final Object[] lists = mcServices.lists(apiKey);
		System.out.println("lists:");
		for (final Object list : lists) {
			final Map<String, Object> map = (Map<String, Object>) list;
			for (final Object key : map.keySet()) {
				final Object value = map.get(key);
				System.out.print(key + " = " + value + "("
						+ value.getClass().getSimpleName() + ")\t");
			}
			System.out.println();
		}
	}

	private void getDetails() {
		try {
			final Map<String, Object> listMemberInfo = mcServices
					.listMemberInfo(apiKey, listId,
							"test4@example.com");
			System.out.println("listMemberInfo: ");
			final Map<String, Object> map = listMemberInfo;
			for (final Object key : map.keySet()) {
				final Object value = map.get(key);
				System.out.print(key + " = " + value + "("
						+ value.getClass().getSimpleName() + ")\t");
			}
			System.out.println();

		} catch (final UndeclaredThrowableException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		mcServices = MailChimpServiceFactory.getMailChimpServices(apiUrl, encoding);
		final String ping = mcServices.ping(apiKey);
		if (IMailChimpServices.PING_SUCCESS.equals(ping)) {
			logger.error("MailChimp connection pinged successfully");
		} else {
			logger.error("Failed to ping MailChimp, response: " + ping);
		}
	}

}
