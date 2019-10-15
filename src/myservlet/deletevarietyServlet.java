package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class deletevarietyServlet
 */
@WebServlet("/deletevarietyServlet")
public class deletevarietyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deletevarietyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
		String id=request.getParameter("id");
		Connection con = null;
		String uri="jdbc:mysql://localhost:3306/shop?"+
                "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";;
		try {
			con = DriverManager.getConnection(uri);
			PreparedStatement sql = con.prepareStatement("delete from variety where id=?");
			sql.setString(1,id);
			sql.executeUpdate();
			con.close(); // 关闭连接
		} catch (SQLException exp) {
		}
		request.getRequestDispatcher("deletevarietysuccess.jsp").forward(request, response);;// 重定向到goodsinf.jsp
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
