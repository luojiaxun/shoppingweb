<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品管理</title>
<%@ include file="head1.txt" %>
</head>
<BODY style=background-image:url(images/background.jpg);background-size:cover;text-align:center>
<FORM action="querygoodsServlet" Method="post">
<input type ='submit' value ='查询所有商品'>
</FORM>
<br>
<FORM action="updategoodsinf.jsp" Method="post">
<input type ='submit' value ='修改商品信息'>
</FORM>
<br>
<FORM action="addgoodsinf.jsp" Method="post">
<input type ='submit' value ='添加新商品'>
</FORM>
<br>
<FORM action="deletegoodsinf.jsp" Method="post">
<input type ='submit' value ='删除商品信息'>
</FORM>
</body>
</html>