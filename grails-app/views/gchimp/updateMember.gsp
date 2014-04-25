<html>
  <head>
	  <title>gChimp UpdateMember</title>
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
  </head>

  <body>
    <h1>gChimp UpdateMember result</h1>

	<ul>
		<li>apiKey: ${ apiKey }</li>
		<li>defaultListId: ${ defaultListId }</li>
	</ul>

	<g:if test="${flash.message}">
	  	<div class="message">
			${flash.message}
	  	</div>
	</g:if>
	
	<g:link controller="gchimp" action="memberInfo" params="[email: email]">view member info</g:link>   
		
	</body>
</html>