package myservlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.Login;

/**
 * Servlet implementation class deleteServlet
 */
@WebServlet("/deleteServlet")
public class deleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public deleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String delete = request.getParameter("delete");
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try {
			loginBean = (Login) session.getAttribute("loginBean");
			boolean b = loginBean.getLogname() == null || loginBean.getLogname().length() == 0;
			if (b)
				response.sendRedirect("login.jsp");// 重定向到登录页面
			LinkedList<String> car = loginBean.getCar();
			car.remove(delete);//移除购物车 通过值
		} catch (Exception exp) {
			response.sendRedirect("login.jsp");// 重定向到登录页面
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("lookcar.jsp");
		dispatcher.forward(request, response);// 转发
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
