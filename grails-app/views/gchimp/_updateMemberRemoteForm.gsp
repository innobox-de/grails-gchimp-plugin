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
	<g:formRemote name="updateMemberForm" url="[controller:'gchimp', action:'updateMemberRemote']" method="post" update="updateMember_wrapper">	   
			<ul>
		 		<li><g:select name="merges[TITLE]" from="${['', 'Herr', 'Frau']}" value="${form?.merges?.TITLE ?: memberInfo?.merges?.TITLE}"/></li>			   		
		 		<li><g:textField name="merges[FNAME]" value="${form?.merges?.FNAME ?: memberInfo?.merges?.FNAME}"/></li>
		 		<li><g:textField name="merges[LNAME]" value="${form?.merges?.LNAME ?: memberInfo?.merges?.LNAME}"/></li>
		 		<li><g:textField name="email" value="${form?.email ?: memberInfo?.merges?.EMAIL}" readonly="true"/></li>
		 		<li><g:submitButton name="submit" value="submit"/></li>
			</ul>
	</g:formRemote>
</g:else>