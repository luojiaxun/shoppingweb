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
 * Servlet implementation class addfoodsServlet
 */
@WebServlet("/addgoodsServlet")
public class addgoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addgoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
		String number=request.getParameter("number");
		String name=request.getParameter("name");
		String area=request.getParameter("area");
		String price=request.getParameter("price");
		String introduce=request.getParameter("introduce");
		String pic=request.getParameter("pic");
		String id=request.getParameter("id");
		String quantity=request.getParameter("quantity");
		Connection con = null;
		String uri="jdbc:mysql://localhost:3306/shop?"+
                "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";;
		try {
			con = DriverManager.getConnection(uri);
			PreparedStatement sql = con.prepareStatement("insert inf values (?,?,?,?,?,?,?,?)");
			sql.setString(1,number);
			sql.setString(2,name);
			sql.setString(3,area);
			sql.setString(4,price);
			sql.setString(5,introduce);
			sql.setString(6,pic);
			sql.setString(7,id);
			sql.setString(8,quantity);
			sql.executeUpdate();
			con.close(); // 关闭连接
		} catch (SQLException exp) {
		}
		request.getRequestDispatcher("addgoodssuccess.jsp").forward(request, response);;// 重定向到goodsinf.jsp
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
