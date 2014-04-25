<html>
  <head>
	  <title>gChimp Ping</title>
	  <style type="text/css">
	  		.message {
	  			border: 1px solid black;
	  			padding: 5px;
	  			background-color:#E9E9E9;
	  		}
	  </style>
  </head>

  <body>
    <h1>gChimp Ping</h1>

	<ul>
		<li>apiKey: ${ apiKey }</li>
		<li>defaultListId: ${ defaultListId }</li>
	</ul>

	<g:if test="${flash.message}">
	  	<div class="message">
			${flash.message}
	  	</div>
	</g:if>
	    	
  </body>
</html>