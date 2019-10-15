<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="mybean.Login" %>
<%@ page import="java.sql.*" %>
<jsp:useBean id="loginBean" class="mybean.Login" scope="session"/>
<%@ include file="head.txt" %></HEAD>
<HTML><Body style=background-image:url(images/background1.jpg);background-size:cover><center>
<% if(loginBean==null){
        response.sendRedirect("login.jsp");//重定向到登录页面
   }
   else {
       boolean b =loginBean.getLogname()==null||
                  loginBean.getLogname().length()==0;
       if(b)
         response.sendRedirect("login.jsp");//重定向到登录页面
   }
   String numberID = request.getParameter("xijie"); 
   if(numberID==null) {
       out.print("没有产品号，无法查看细节");
       return;
   } 
   Connection con;
   Statement stmt; 
   ResultSet rs;
   String uri="jdbc:mysql://localhost:3306/shop?user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";
   
   try{ 
     con=DriverManager.getConnection(uri);
     stmt=con.createStatement();
     String sql=
     "SELECT * FROM inf where number = '"+numberID+"'";
     rs=stmt.executeQuery(sql);
     out.print("<table border=2>");
     out.print("<tr>");
     out.print("<th>产品号</th>");
     out.print("<th>名称</th>");
     out.print("<th>制造商</th>");
     out.print("<th>价格</th>");
     out.print("<th><font color=blue>放入购物车</font></th>");
     out.print("</TR>");
     String picture="";
     String introduce="";
     while(rs.next()){
       String number=rs.getString(1);
       String name=rs.getString(2);
       String made=rs.getString(3);
       String price=rs.getString(4);
       introduce=rs.getString(5);
       picture=rs.getString(6); 
       String goods =
       "("+number+","+name+","+made+
             ","+price+")#"+price;//便于购物车计算价格,尾缀上"#价格值"
        goods = goods.replaceAll("\\p{Blank}","");//干什么？
        String button="<form  action='putcarServlet' method = 'post'>"+
                     "<input type ='hidden' name='java' value= "+goods+">"+
                     "<input type ='submit'  value='放入购物车' ></form>";
        out.print("<tr>");
        out.print("<td>"+number+"</td>");
        out.print("<td>"+name+"</td>");
        out.print("<td>"+made+"</td>");
        out.print("<td>"+price+"</td>");
        out.print("<td>"+button+"</td>");
        out.print("</tr>");
     } 
     out.print("</table>");
     out.print("产品详情:<br>");
     out.println("<div align=center>"+introduce+"<div>");
     String pic ="<img src='image/"+picture+"' width=260 height=200 ></img>";
     out.print(pic); //产片图片
     con.close();
  }
  catch(SQLException exp){}
%>
</Center></BODY></HTML>