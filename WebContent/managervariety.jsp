<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head1.txt" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<BODY style=background-image:url(images/background.jpg);background-size:cover;text-align:center>
<FORM action="queryvarietyServlet" Method="post">
<input type ='submit' value ='查询类别'>
</FORM>
<br>
<FORM action="deletevariety.jsp" Method="post">
<input type ='submit' value ='删除类别'>
</FORM>
<br>
<FORM action="addvariety.jsp" Method="post">
<input type ='submit' value ='添加类别'>
</FORM>
</body>
</html>