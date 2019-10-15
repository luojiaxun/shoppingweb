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
 * Servlet implementation class updatefoodsServlet
 */
@WebServlet("/updategoodsServlet")
public class updategoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updategoodsServlet() {
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
			Statement sql = con.createStatement();
			ResultSet rs = sql.executeQuery("SELECT * FROM inf where number ="+number);
			if(rs.next()){
				String name1=rs.getString(2);
				String area1=rs.getString(3);
				String price1=rs.getString(4);
				String introduce1=rs.getString(5);
				String pic1=rs.getString(6);
				String id1=rs.getString(7);
				String quantity1=rs.getString(8);
				if(name.equals(""))
					name=name1;
				if(area.equals(""))
					area=area1;
				if(price.equals(""))
					price=price1;
				if(introduce.equals(""))
					introduce=introduce1;
				if(id.equals(""))
					id=id1;
				if(pic.equals(""))
					pic=pic1;
				if(quantity.equals(""))
					quantity=quantity1;
			}
			String sql1="update inf set name="+"'"+name+"'"+",made="+"'"+area+"'"+",price="+price+","
					+ "introduce="+"'"+introduce+"'"+",id="+id+",pic="+"'"+pic+"'"+",quantity="+quantity+" where number="+number;
			sql.executeUpdate(sql1);
			con.close(); // 关闭连接
		} catch (SQLException exp) {
		}
		request.getRequestDispatcher("updatesuccess.jsp").forward(request, response);;// 重定向到goodsinf.jsp
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
