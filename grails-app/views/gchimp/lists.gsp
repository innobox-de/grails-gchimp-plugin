<html>
  <head>
	  <title>gChimp Lists</title>
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
    <h1>gChimp Lists</h1>

	<ul>
		<li>apiKey: ${ apiKey }</li>
		<li>defaultListId: ${ defaultListId }</li>
	</ul>

	<g:if test="${flash.message}">
	  	<div class="message">
			${flash.message}
	  	</div>
	</g:if>
	   
	<g:if test="${lists}">		
		<g:each in="${ lists }" var="list">
			<h2>List "<%  print list.find { it.key == "name" }?.value %>" </h2> 
			<dl>
			<g:each in="${ list }" var="map">
				<dt>${ map.key }:</dt> <dd>${ map.value }</dd> 
			</g:each>
			</dl>
		</g:each>		
	</g:if>	   	    
  </body>
</html>