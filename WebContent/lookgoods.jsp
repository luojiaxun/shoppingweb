<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*" %>
<HTML><HEAD><%@ include file="head.txt" %></HEAD>
<BODY style=background-image:url(images/lookgoods.jpg);background-size:cover>
<div align="center">
<%  
      String uri="jdbc:mysql://localhost:3306/shop?"+
              "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";
      Connection con; 
      Statement sql;
      ResultSet rs;
      try {
        con=DriverManager.getConnection(uri);
        sql=con.createStatement();
        //读取classify表，获得分类：  
        rs=sql.executeQuery("SELECT * FROM variety  ");
        out.print("<form action='queryServlet' method ='post'>") ;
        out.print("<select name='fenleiNumber'>") ;//下拉表单
        while(rs.next()){
           int id = rs.getInt(1);
           String name = rs.getString(2);
           out.print("<option value ="+id+">"+name+"</option>");
        }  
        out.print("</select>");
        out.print("<input type ='submit' value ='提交'>");  
        out.print("</form>");
        con.close();
     }
     catch(SQLException e){ 
        out.print(e);
     }
%>
</div>
</BODY></HTML>
