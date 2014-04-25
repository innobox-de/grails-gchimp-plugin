<html>
  <head>
	  <title>gChimp Subscribe</title>
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
	  </style>
  </head>

  <body>
    <h1>gChimp Subscribe</h1>

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