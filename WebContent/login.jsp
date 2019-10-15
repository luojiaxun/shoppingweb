<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="loginBean" class="mybean.Login" scope="session"/>
<HTML><HEAD><%@ include file="head.txt" %></HEAD>
<BODY style=background-image:url(images/login.jpg);background-size:cover>
<div align="center">
<table border=2>
<tr> <th>登录</th></tr>
<FORM action="loginServlet" Method="post">
<tr><td>登录名称:<Input type=text name="logname"></td></tr>
<tr><td>输入密码:<Input type=password name="password"></td></tr>
</table>
<Input type=submit name="submit" value="提交">
</form>
</div >
<div align="center" >
登录反馈信息:<jsp:getProperty name="loginBean" property="backNews"/>
<br>登录名称:<jsp:getProperty name="loginBean" property="logname"/>
<div >
</BODY></HTML>