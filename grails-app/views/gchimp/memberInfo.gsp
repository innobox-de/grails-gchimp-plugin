<html>
  <head>
	  <title>gChimp MemberInfo</title>
	  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	  <style type="text/css">
	  		body {
	  			font-family: Verdana;
	  			font-size: 11px;
	  		}
	  		.message {
	  			border: 1px solid black;
	  			padding: 5px;
	  			background-color:#E9E9E9;
	  		}
	  		dt {
	  			font-weight: bold;
	  			display: block;
	  			float: left;
	  			width: 220px;
	  			clear: left;
	  		}
	  </style>
	  <g:javascript library="prototype" />
  </head>

  <body>
    <h1>gChimp MemberInfo</h1>

	<ul>
		<li>apiKey: ${ apiKey }</li>
		<li>defaultListId: ${ defaultListId }</li>
	</ul>

	<g:if test="${flash.message}">
	  	<div class="message">
			${flash.message}
	  	</div>
	</g:if>
	   
	<g:if test="${memberInfo}">		
		<g:each in="${ memberInfo }" var="info">
			<g:if test="${ info }">
			<dl>
			<g:each in="${ info }" var="map">
				<dt>${ map?.key }:</dt> <dd>${ map?.value }</dd> 
			</g:each>
			</dl>
			</g:if>
		</g:each>		
	</g:if>	 
   <gchimp:ifSubscribed email="${ params.email }">
  	 <g:if test="${!memberInfo.merges.TITLE || !memberInfo.merges.FNAME || !memberInfo.merges.LNAME}">
   		<!-- subs -->
   		<h2>Update member info</h2>
		   
		   <div id="updateMember_wrapper">  	  
			   <g:render template="updateMemberRemoteForm" model="['memberInfo': memberInfo]"/>
		   	</div>	 	   		  		  
	   	</g:if>
	</gchimp:ifSubscribed>	
	<gchimp:elseSubscribed>
		<!-- !subs -->
		<div id="subscription_wrapper">
			<g:render template="remoteSubscriptionForm"/>	 		
		</div>
	</gchimp:elseSubscribed>
	
	</body>
</html>