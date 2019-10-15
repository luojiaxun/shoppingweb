<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="head1.txt" %>
</head>
<BODY style=background-image:url(images/background.jpg);background-size:cover>
<form action="addgoodsServlet" Method="post">
<input type="text" name="number">产品号 (*必填，且不能和别的产品重复)<br>
<input type="text" name="name">产品名<br>
<input type="text" name="area">产地 <br>
<input type="text" name="price">价格<br>
<input type="text" name="introduce">产品介绍 <br>
<input type="text" name="pic">产品图片链接 <br>
<input type="text" name="id">产品类别号(若填了因注意类别号需存在) <br>
<input type="text" name="quantity">库存量 <br>
<input type="submit" value="提交">
</form>
</body>
</html>