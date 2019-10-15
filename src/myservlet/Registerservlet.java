package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybean.Register;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Registerservlet")
public class Registerservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registerservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public  void  doPost(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException {
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
    	String uri="jdbc:mysql://localhost:3306/shop?"+
                "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";
    	Connection con; 
    	PreparedStatement sql; 
    	Register userBean=new Register();  //创建的Javabean模型
    	request.setAttribute("userBean",userBean);
    	String logname=request.getParameter("logname").trim();
    	String password=request.getParameter("password").trim();
    	String again_password=request.getParameter("again_password").trim();
    	String phone=request.getParameter("phone").trim();
    	String address=request.getParameter("address").trim();
    	String realname=request.getParameter("realname").trim();
    	if(logname==null)
    		logname="";
    	if(password==null)
    		password="";
    	if(!password.equals(again_password)) { 
    		userBean.setBackNews("两次密码不同，注册失败，");
    		RequestDispatcher dispatcher= 
    				request.getRequestDispatcher("Register.jsp");
    		dispatcher.forward(request, response);//转发
    		return;
    	}
    	boolean isLD=true;
    	for(int i=0;i<logname.length();i++){
    		char c=logname.charAt(i);
    		if(!((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0'))) 
    			isLD=false;
    	} 
    	boolean boo=logname.length()>0&&password.length()>0&&isLD;
    	String backNews="";
    	try{   con=DriverManager.getConnection(uri);
    	String insertCondition="INSERT INTO user VALUES (?,?,?,?,?)";
    	sql=con.prepareStatement(insertCondition);
    	if(boo)
    	{ sql.setString(1,logname);
    	sql.setString(2,password);
    	sql.setString(3,phone);
    	sql.setString(4,address);
    	sql.setString(5,realname);
    	int m=sql.executeUpdate();
    	if(m!=0){
    		backNews="注册成功";
    		userBean.setBackNews(backNews);
    		userBean.setLogname(logname);
    		userBean.setPhone(phone);
    		userBean.setAddress(address);
    		userBean.setRealname(realname);
    	}
    	}
    	else {
    		backNews="信息填写不完整或名字中有非法字符";
    		userBean.setBackNews(backNews);  
    	}
    	con.close();
    	}
    	catch(SQLException exp){
    		backNews="该会员名已被使用，请您更换名字"+exp;
    		userBean.setBackNews(backNews); 
    	}
    	RequestDispatcher dispatcher= 
    			request.getRequestDispatcher("register.jsp");
    	dispatcher.forward(request, response);//转发
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
