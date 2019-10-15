package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.Login;

/**
 * Servlet implementation class putServlet
 */
@WebServlet("/putcarServlet")
public class putcarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public putcarServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String goods = request.getParameter("java");
		//System.out.println(goods);
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try {
			loginBean = (Login) session.getAttribute("loginBean");
			boolean b = loginBean.getLogname() == null || loginBean.getLogname().length() == 0;
			if (b)
				response.sendRedirect("login.jsp");// 重定向到登录页面
			LinkedList<String> car = loginBean.getCar();
			car.add(goods);
			speakSomeMess(request, response, goods);
		} catch (Exception exp) {
			response.sendRedirect("login.jsp");// 重定向到登录页面
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void speakSomeMess(HttpServletRequest request, HttpServletResponse response, String goods) {
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			//out.print("<%@ include file='head.txt' %>");//html无法显示
			out.println("<html><body style=background-image:url(images/background1.jpg);background-size:cover>");
			out.println("<h2>" + goods + "放入购物车</h2>");
			out.println("查看购物车或返回浏览商品<br>");
			out.println("<a href =lookcar.jsp>查看购物车</a>");
			out.println("<br><a href =goodsinf.jsp>浏览商品</a>");
			out.println("</body></html>");
		} catch (IOException exp) {
		}
	}
}
