package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

import mybean.DataByPage;

/**
 * Servlet implementation class searchByConditionServlet
 */
@WebServlet("/searchByConditionServlet")
public class searchByConditionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 CachedRowSetImpl rowSet=null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public searchByConditionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String searchMess = request.getParameter("searchMess");
		String radioMess = request.getParameter("radio");
		if (searchMess == null || searchMess.length() == 0) {
			fail(request, response, "没有查询信息，无法查询");
			return;
		}
		String condition = "";
		if (radioMess.equals("number")) {
			condition = "SELECT * FROM inf where number ='" + searchMess + "'";
		} else if (radioMess.equals("name")) {
			condition = "SELECT * FROM inf where name LIKE '%" + searchMess + "%'";
		} else if (radioMess.equals("price")) {
			double max = 0, min = 0;
			String regex = "[^0123456789.]";
			String[] priceMess = searchMess.split(regex);
			if (priceMess.length == 1) {
				max = min = Double.parseDouble(priceMess[0]);
			} else if (priceMess.length == 2) {
				min = Double.parseDouble(priceMess[0]);
				max = Double.parseDouble(priceMess[1]);
				if (max < min) {
					double t = max;
					max = min;
					min = t;
				}
			} else {
				fail(request, response, "输入的价格格式有错误");
				return;
			}
			condition = "SELECT * FROM inf where " + "price <= " + max + " AND price>="
					+ min;
		}
		HttpSession session = request.getSession(true);
		Connection con = null;
		DataByPage dataBean = null;
		try {
			dataBean = (DataByPage) session.getAttribute("dataBean");
			if (dataBean == null) {
				dataBean = new DataByPage(); // 创建Javabean对象
				session.setAttribute("dataBean", dataBean);
			}
		} catch (Exception exp) {
			dataBean = new DataByPage();
			session.setAttribute("dataBean", dataBean);
		}
		String uri="jdbc:mysql://localhost:3306/shop?"+
                "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";
		try {
			con = DriverManager.getConnection(uri);
			Statement sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = sql.executeQuery(condition);
			rowSet = new CachedRowSetImpl(); // 创建行集对象
			rowSet.populate(rs);
			dataBean.setRowSet(rowSet); // 行集数据存储在dataBean中
			con.close(); // 关闭连接
		} catch (SQLException exp) {
		}
		response.sendRedirect("goodsinf.jsp");// 重定向到byPageShow.jsp
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void fail(HttpServletRequest request, HttpServletResponse response, String backNews) {
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("返回：");
			out.println("<a href =searchgoods.jsp>查询商品</a>");
			out.println("</body></html>");
		} catch (IOException exp) {
		}
	}
}
