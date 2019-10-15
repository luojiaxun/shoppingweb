<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML><HEAD><%@ include file="head.txt" %></HEAD>
<BODY style=background-image:url(images/background1.jpg);background-size:cover>
<div align="center">
<br>查询时可以输入化妆品的版本号或化妆品名称及价格。<br>
化妆品名称支持模糊查询。
<br>输入价格是在2个值之间的价格，格式是：价格1-价格2<br>
例如 258-689 
<FORM action="searchByConditionServlet" Method="post" >
   <br>输入查询信息:<Input type=text name="searchMess"><br>
   <Input type =radio name="radio" value="number">商品版本号
   <Input type =radio name="radio" value="name" checked="ok">商品名称
   <Input type =radio name="radio" value="price">商品价格
   <br><Input type=submit name="g" value="提交">
</Form>
</div>
</BODY></HTML>