<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>

<!DOCTYPE HTML>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>Welcome Page</title>
</head>

<body>

<%-- S T A N D A R D       H E A D E R  --%>
<%@ include file="/WEB-INF/jsp/stdHeader.jsp" %>

<h2>welcome.jsp</h2>

<br/>

Hello ${userInfo.userName} <br/>
Are you an administrator: ${userInfo.isAdministrator }

<br/>
<br/>

<%-- S T A N D A R D       F O O T E R  --%>
<%@ include file="/WEB-INF/jsp/stdFooter.jsp" %>

</body>
</html>