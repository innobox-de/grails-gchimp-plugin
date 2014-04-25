<g:if test="${ flash.gchimpSuccess  }">
	<g:message code="${flash.gchimpSuccess }"/>
</g:if>
<g:else>
	<g:if test="${flash.gchimpError}">
		<g:message code="${flash.gchimpError}"/>
	</g:if>
	
	<g:hasErrors bean="${form}">
	    <div class="errors">
	        <g:renderErrors bean="${form}" as="list"/>
	    </div>
	</g:hasErrors>


	<g:formRemote name="subscriptionForm" url="[controller:'gchimp', action:'subscribeRemote']" method="post" update="subscription_wrapper">	   
   		<ul>
	   		<li><g:select name="merges[TITLE]" from="${['', 'Herr', 'Frau']}" value="${form?.merges?.TITLE}"/></li>			   		
	   		<li><g:textField name="merges[FNAME]" value="${form?.merges?.FNAME}"/></li>
	   		<li><g:textField name="merges[LNAME]" value="${form?.merges?.LNAME}"/></li>
	   		<li><g:textField name="email" value="${form?.email}"/></li>
	   		<li><g:submitButton name="submit" value="submit"/></li>
   		</ul>
	</g:formRemote>
</g:else>