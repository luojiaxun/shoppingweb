<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="mybean.Login" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="loginBean" class="mybean.Login" scope="session"/>
<HTML><HEAD><%@ include file="head.txt" %></HEAD>
<BODY style=background-image:url(images/background1.jpg);background-size:cover>
<div align="center">
<%  if(loginBean==null){
        response.sendRedirect("login.jsp");//重定向到登录页面
    }
    else {
       boolean b =loginBean.getLogname()==null||
                  loginBean.getLogname().length()==0;
       if(b)
         response.sendRedirect("login.jsp");//重定向到登录页面
    }
    LinkedList car =loginBean.getCar();
    if(car==null)
      out.print("<h2> 购物车没有物品.</h2>");
    else {
       Iterator<String> iterator=car.iterator();//遍历
       StringBuffer buyGoods = new StringBuffer();
       int n=0;
       int i=0;
       String a[]=new String[100];//存购物车商品的商品号
       double priceSum =0;
       out.print("购物车中的物品：<table border=2>");
       while(iterator.hasNext()) {	//遍历
           String goods=iterator.next();//(2,三鹿,中国,9.90)#9.90
           String showGoods="";
           n++; 
           //购车车物品的后缀是“#价格数字"，比如“化妆品价格3989 #3989”
           int index=goods.lastIndexOf("#");
           if(index!=-1){
              priceSum+=Double.parseDouble(goods.substring(index+1));//算总价
              showGoods = goods.substring(0,index);//(2,三鹿,中国,9.90)
              a[i]=goods.substring(1,2);
              i++;
           }
           buyGoods.append(n+":"+showGoods);
           String del="<form  action='deleteServlet' method = 'post'>"+
                     "<input type ='hidden' name='delete' value= "+goods+">"+
                     "<input type ='submit'  value='删除' ></form>";
          
           out.print("<tr><td>"+showGoods+"</td>");
           out.print("<td>"+del+"</td></tr>");
       }
       String quantity="";
       for(int j=0;j<a.length;j++)//传订单数组
       {
    	  quantity+="<input type ='hidden' name='number' value= "+a[j]+" >";
       }
       out.print("</table>");
       String orderForm = "<form action='buyServlet' method='post'>"+
              " <input type ='hidden' name='buy' value= "+buyGoods+" >"+ 
              " <input type ='hidden' name='price' value= "+priceSum+" >"+
              quantity+
              "<input type ='submit'  value='生成订单'></form>";
       out.print(orderForm); 
    } 
%>
</div>
</BODY></HTML>
