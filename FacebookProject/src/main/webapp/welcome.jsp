<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" >
<title>Welcome</title>
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.3.0/build/cssreset/reset-min.css">
<script src="http://code.jquery.com/jquery-latest.js" type="text/javascript"></script>
<script src="/js/myjava.js" type="text/javascript"></script>
<style type="text/css">
/*-----------------------------------------------------------------------------------*/
/*	Setup
/*-----------------------------------------------------------------------------------*/
.topdiv
{
width:1366px;
height:43px;
bbackground-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAAqCAMAAABFoMFOAAAAWlBMVEVOaaJDYJxCX5xBXptIZJ9MZ6E/XJpFYZ1KZqA9W5lGYp5HY55MaKFJZZ9LZqBEYZ1NaaJNaKJNaKFAXZtAXZpLZ6E+XJo+W5lJZaA9Wpk8Wpk8Wpg8WZg7WZj2xcGWAAAANElEQVR42lWGSQoAIBDDHCjo0f8/UxBxQDQuFwlpqgBZBq6+P+unVY1GnDgwqbD2zGz5e1lBdwvGGPE6OgAAAABJRU5ErkJggg==");
background-repeat: repeat-x;
background-size: auto auto;
background-position: 0px 0px;
background-color: #3A5795;
border-width: 0px 0px 1px;
border-style: none none solid;
}
.wrapper{
width:760px;
height:200px;
margin-left:auto;
margin-right:auto;
padding-top:33px;
}
.mainbody
{
width:1349px;
height:1628.07px;
background-color:#F2F2F6;
}
.wrapper{
width: 760px;
height:1628.07px;
margin-left:auto;
margin-right:auto;
padding-top:20px;
background-color:white;
}
.maincontent{
width:480px;
float:left;	
background-color:white;	
}
.sidebar{
	width:240px;
	float:right;
	margin-right:20px;
}
.tabHeader{
background-color: #F2F2F2;
border-top: solid 1px #E2E2E2;
padding: 4px 5px 5px;
margin-top:15px;
margin-bottom:10px;
}
a{
cursor: pointer;
color: #3B5998;
text-decoration: none;
}
ul.tabs {
	margin: 0;
	padding: 0;
	float: left;
	list-style: none;
	height: 19px; /*--Set height of tabs--*/
	border-bottom: 1px solid #E2E2E2;
	border-left: 1px solid #E2E2E2;
	width: 100%;
	margin-bottom:20px;
}
ul.tabs li {
	float: left;
	margin: 0;
	padding: 0;
	height: 18px; /*--Minus 1px from the height of  ul--*/
	line-height: 18px; /*--aligns text within the tab--*/
	border: 1px solid #E2E2E2;
	margin-bottom: -1px; /*--Pull the list item down 1px--*/
	overflow: hidden;
	position: relative;
	background: #f2f2f2;
	margin-right:5px;
	min-width:73px;
	text-align:center;
	
}
ul.tabs li:first-child{ /*--Removes the left border from the first child of the list--*/
border-left:none;	
	
}
ul.tabs li a {
	text-decoration: none;
	color: #333333;
	display: block;
	font-size: 11px;
	padding-right:5px;
	padding-left:5px;
	outline: none;
}
ul.tabs li a:hover {
	background: #fff;
}
html ul.tabs li.active, html ul.tabs li.active a:hover  { /*--Makes sure that the active tab does not listen to the hover properties--*/
	background: #fff;
	border-bottom: 1px solid #fff; 
	color:#3B5998;
}
ul.tabs li.active a{
	color:#3B5998;	
}
.button{
	background-color:#ECEEF5;
	border:1px solid #CAD4E7;
	text-decoration:none;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
padding: 2px 3px 2px 2px;
margin-right:5px;
}
.button:hover{
	border:1px solid #9DACCE;
	text-decoration:none;
	
}
</style>
</head>
<body>
<div class="topdiv">
</div>
<div style="height:2px;background"></div>
<div class="mainbody">
<div class="wrapper">
	<div class="maincontent">
		<h1 style="padding-left: 20px;font-size: 36px;font-weight: bold;color: #333333 ">Facebook User - Activity Log</h1>
        <div style="padding-left: 250px;padding-top: 100px">
         <button class="button" onclick="location.href='https://www.facebook.com/dialog/oauth?client_id=1444903262469722&redirect_uri=http://localhost:8080/facebookApp'">Connect to Facebook</button>
    	</div>
</div><!--End Wrapper -->
</div>
</div>
</body>
</html>
