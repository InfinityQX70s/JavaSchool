  <!DOCTYPE html>
  <html>
    <head>
      <!--Import Google Icon Font-->
      <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
      <!--Import materialize.css-->
      <link type="text/css" rel="stylesheet" href="/css/materialize.css"  media="screen,projection"/>
    </head>

    <body>
<br>
<br>
<br>
<br>
<div class="row">
        <div class="col col s4 offset-l4">
          <div class="card white">
            <div class="card-content black-text">
               <div class="row">
		    <div class="col s12">
		      <ul class="tabs">
			<li class="tab col s3"><a class="active" href="#manager">Manager</a></li>
			<li class="tab col s3"><a href="#driver">Driver</a></li>
		      </ul>
		    </div>
		    <div id="manager" class="col s12">
		       <div class="row">
			<div class="input-field col s12">
			  <input id="email" type="email" class="validate">
			  <label for="email">Email</label>
			</div>
		      </div>
			<div class="row">
			<div class="input-field col s12">
			  <input id="password" type="password" class="validate">
			  <label for="password">Password</label>
			</div>
		      </div>
		    </div>
		    <div id="driver" class="col s12">
			<div class="row">
			<div class="input-field col s12">
			  <input id="password" type="text" class="validate">
			  <label for="password">Number</label>
			</div>
		      </div>
		    </div>
		  </div>
            <div class="card-action center">
              <a href="#">log in</a>
            </div>
          </div>
        </div>
      </div>
	</div>>
      <!--Import jQuery before materialize.js-->
      <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
      <script type="text/javascript" src="/js/materialize.min.js"></script>
    </body>
  </html>
