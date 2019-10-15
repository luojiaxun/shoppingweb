package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.Login;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public loginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
		Connection con;
		Statement sql;
		String logname = request.getParameter("logname").trim(), password = request.getParameter("password").trim();
		String uri="jdbc:mysql://localhost:3306/shop?"+
                "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";
		boolean boo = (logname.length() > 0) && (password.length() > 0);
		try {
			if(logname.equals("admin")&&password.equals("123"))
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("index1.jsp");// 转发
				dispatcher.forward(request, response);
			}
			else
			{
			con = DriverManager.getConnection(uri);
			String condition = "select * from user where logname = '" + logname + "' and password ='" + password + "'";
			sql = con.createStatement();
			if (boo) {
				ResultSet rs = sql.executeQuery(condition);
				boolean m = rs.next();
				if (m == true) {
					// 调用登录成功的方法:
					success(request, response, logname, password);
					RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");// 转发
					dispatcher.forward(request, response);
				} else {
					String backNews = "您输入的用户名不存在，或密码不般配";
					// 调用登录失败的方法:
					fail(request, response, logname, backNews);
				}
			} else {
				String backNews = "请输入用户名和密码";
				fail(request, response, logname, backNews);
			}
			con.close();
		} 
		}
		catch (SQLException exp) {
			String backNews = "" + exp;
			fail(request, response, logname, backNews);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void success(HttpServletRequest request, HttpServletResponse response, String logname, String password) {
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try {
			loginBean = (Login) session.getAttribute("loginBean");
			if (loginBean == null) {
				loginBean = new Login(); // 创建新的数据模型
				session.setAttribute("loginBean", loginBean);
				loginBean = (Login) session.getAttribute("loginBean");
			}
			String name = loginBean.getLogname();
			if (name.equals(logname)) {
				loginBean.setBackNews(logname + "已经登录了");
				loginBean.setLogname(logname);
			} else { // 数据模型存储新的登录用户
				loginBean.setBackNews(logname + "登录成功");
				loginBean.setLogname(logname);
			}
		} catch (Exception ee) {
			loginBean = new Login();
			session.setAttribute("loginBean", loginBean);
			loginBean.setBackNews(logname + "登录成功");
			loginBean.setLogname(logname);
		}
	}

	public void fail(HttpServletRequest request, HttpServletResponse response, String logname, String backNews) {
		response.setContentType("text/html;charset=GB2312");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>" + logname + "登录反馈结果<br>" + backNews + "</h2>");
			out.println("返回登录页面或主页<br>");
			out.println("<a href =login.jsp>登录页面</a>");
			out.println("<br><a href =index.jsp>主页</a>");
			out.println("</body></html>");
		} catch (IOException exp) {
		}
	}
}
