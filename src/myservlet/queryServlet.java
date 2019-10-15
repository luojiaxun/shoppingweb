package myservlet;

import java.io.IOException;
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
 * Servlet implementation class queryServlet
 */
@WebServlet("/queryServlet")
public class queryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CachedRowSetImpl rowSet=null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public queryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
		String idNumber = request.getParameter("fenleiNumber");
		if (idNumber == null)
			idNumber = "0";
		int id = Integer.parseInt(idNumber);
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
                "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";;
		try {
			con = DriverManager.getConnection(uri);
			Statement sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = sql.executeQuery("SELECT * FROM inf where id = " + id);
			rowSet = new CachedRowSetImpl(); // 创建行集对象
			rowSet.populate(rs);
			dataBean.setRowSet(rowSet); // 行集数据存储在dataBean中
			con.close(); // 关闭连接
		} catch (SQLException exp) {
		}
		response.sendRedirect("goodsinf.jsp");// 重定向到goodsinf.jsp
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
