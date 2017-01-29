<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Atmosphere Chat</title>

    <script type="text/javascript">
        var gs_contextPath = "${contextPath}";
    </script>

    <!-- Atmosphere -->
    <script type="text/javascript" src="${contextPath}/resources/atmosphere-2.3.2/atmosphere.js"></script>

    <!-- Application -->
    <script type="text/javascript" src="${contextPath}/resources/jquery-2.0.3/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/application.js"></script>

    <style>
        * {font-family: tahoma; font-size: 12px; padding: 0px; margin: 0px;}
        p {line-height: 18px;}
        div {width: 500px; margin-left: auto; margin-right: auto;}
        #rooms {padding: 5px; background: #ffc0cb; border-radius: 5px; border: 1px solid #CCC; margin-top: 10px;}
        #content {padding: 5px; background: #ddd; border-radius: 5px; border: 1px solid #CCC; margin-top: 10px;}
        #users {padding: 5px; background: #ddd; border-radius: 5px; border: 1px solid #CCC; margin-top: 10px;}
        #header {padding: 5px; background: #f5deb3; border-radius: 5px; border: 1px solid #CCC; margin-top: 10px;}
        #info {padding: 5px; background: #f5deb3; border-radius: 5px; border: 1px solid #CCC; margin-top: 10px;}
        #input {border-radius: 2px; border: 1px solid #ccc; margin-top: 10px; padding: 5px; width: 400px;}
        #status {width: 88px; display: block; float: left; margin-top: 15px;}
    </style>
</head>
<body>
<div id="header"><h3>Atmosphere Chat. Default transport is WebSocket, fallback is long-polling</h3></div>
<div id="rooms"></div>
<div id="users"></div>
<div id="content"></div>
<div id="info"><h4>To send a private message, type the name followed by : and then the message. To send a message to all chatroon, type all: message</h4></div>
<div>
    <span id="status">Connecting...</span>
    <input type="text" id="input" disabled="disabled"/>
</div>
</body>
</html>