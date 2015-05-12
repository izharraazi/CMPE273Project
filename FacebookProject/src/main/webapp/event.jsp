<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Design and Code an integrated Facebook App</title>

<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.3.0/build/cssreset/reset-min.css">
<script src="http://code.jquery.com/jquery-latest.js" type="text/javascript"></script>
<script src="/js/myjava.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {

	$(".tab_content").hide(); //On page load hide all the contents of all tabs
	$("ul.tabs li:nth-child(4)").addClass("active").show(); //Default to the first tab
	$(".tab_content:nth-child(4)").show(); //Show the default tabs content

	//When the user clicks on the tab
	$("ul.tabs li").click(function() {

		$("ul.tabs li").removeClass("active"); //Remove the active class
		$(this).addClass("active"); //Add the active tab to the selected tab
		$(".tab_content").hide(); //Hide all other tab content

		var activeTab = $(this).find("a").attr("href"); //Get the href's attribute value (class) and fade in the corresponding tabs content
		$(activeTab).fadeIn(); //Fade the tab in
		return false;
	});
});
</script>
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
body, input{
background: white;
font-size: 11px;
font-family: "lucida grande",tahoma,verdana,arial,sans-serif;
color: #333;
line-height: 1.28;
word-wrap:break-word;	
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
width:760px;
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
ul.tabs li:AFTER{ /*--Removes the left border from the first child of the list--*/
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
</style>
</head>
<body>

<div class="topdiv">
</div>
<div style="height:2px;background"></div>
<div class="mainbody">

<div class="wrapper">

	<div class="maincontent">
        
        <ul class="tabs">
            <li><a href="#tab1">&nbsp;Photos&nbsp;</a></li>
            <li><a href="/video">&nbsp;Videos&nbsp;</a></li>
            <li><a href="/status">&nbsp;Statuses&nbsp;</a></li>
            <li><a href="/event">&nbsp;Events&nbsp;</a></li>
		</ul>
	<div class="tab_container">
    <div id="tab1" class="tab_content">
    
          <div class="post">
                <h3><a href="#">The user liked these photos</a></h3>
                <span class="postInfo">by <a href="#">Connor Turnbull</a> on Jul 22nd 2011 with 2 comments</span>
                <p>Color blindness is a mild disability through which the affected experience a decreased ability to distinguish colors from others. This can be a real drawback for anyone in the design field since color theory is an integral feature in successful design, and a lot of decisions are based on the feeling and emotion derived from...</p>
                <a class="more" href="#">Read More</a>
                <span class="line"></span>
                  <a href="#">14 Comments</a>
                <span class="line"></span>
        </div><!--End Blog Post-->
        
        <div class="post">
                <h3><a href="#">Designing For, and As, a Color-Blind Person</a></h3>
                <span class="postInfo">by <a href="#">Connor Turnbull</a> on Jul 22nd 2011 with 2 comments</span>
                <p>The user: <b> ${user.name}</b></p>
<br/>
<c:forEach items="${feeds}" var="arr">
    <c:set var="likes" value="${arr.like}"/>
    	<c:if test="${likes== true}">
    	<c:if test="${type=='video'}">
    	<p> &nbsp;  <a href="${arr.link}"> </a>&nbsp;</p>
    	</c:if>
    </c:if> 
</c:forEach>
<br/>
                <a class="more" href="#">Read More</a>
                <span class="line"></span>
                <a href="#">14 Comments</a>
                <span class="line"></span>
        </div><!--End Blog Post-->
        
    </div>
    <div id="tab2" class="tab_content">
    
    <h3>About</h3>
    
    <p>Webdesigntuts+ is a blog made to house and showcase some of the best web design tutorials and articles around. We publish tutorials that not only produce great results and interfaces, but explain the techniques behind them in a friendly, approachable manner.</p>
    
    <p>Web design is a booming industry with a lot of competition. We hope that reading Webdesigntuts+ will help our readers learn a few tricks, techniques and tips that they might not have seen before and help them maximize their creative potential!</p>
    
    <p><strong>Webdesigntuts+ is part of the Tuts+ Network</strong></p>
		

    
    </div><!--End Tab 2 -->
  </div>
    
</div><!--End Wrapper -->
</div>
</div>
</body>
</html>
