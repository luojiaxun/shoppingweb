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
/**
 * Servlet implementation class queryfoodsServlet
 */
@WebServlet("/querygoodsServlet")
public class querygoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public querygoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String allinf="";
		request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
		Connection con = null;
		String uri="jdbc:mysql://localhost:3306/shop?"+
                "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";;
		try {
			con = DriverManager.getConnection(uri);
			Statement sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = sql.executeQuery("SELECT * FROM inf ");
			while(rs.next()){
				allinf+="产品号:"+rs.getString(1)+";";
				allinf+="产品名:"+rs.getString(2)+";";
				allinf+="产地:"+rs.getString(3)+";";
				allinf+="价格:"+rs.getBigDecimal(4)+";";
				allinf+="产品介绍:"+rs.getString(5)+";";
				allinf+="产品图片链接:"+rs.getString(6)+";";
				allinf+="产品类别号:"+rs.getInt(7)+";";
				allinf+="库存量:"+rs.getInt(8);
				allinf+="<br>";
			}
			con.close(); // 关闭连接
		} catch (SQLException exp) {
		}
		request.setAttribute("goodsinf", allinf);
		request.getRequestDispatcher("allgoodsinf.jsp").forward(request, response);;// 重定向到goodsinf.jsp
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
