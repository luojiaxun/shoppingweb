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
 * Servlet implementation class queryordersServlet
 */
@WebServlet("/queryordersServlet")
public class queryordersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public queryordersServlet() {
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
			ResultSet rs = sql.executeQuery("SELECT * FROM orderform ");
			while(rs.next()){
				allinf+="������:"+rs.getString(1)+";";
				allinf+="�����û�����:"+rs.getString(2)+";";
				allinf+="������Ϣ:"+rs.getString(3)+";";
				allinf+="�����ܼ�:"+rs.getString(4);
				allinf+="<br>";
			}
			con.close(); // �ر�����
		} catch (SQLException exp) {
		}
		request.setAttribute("goodsinf", allinf);
		request.getRequestDispatcher("allordersinf.jsp").forward(request, response);;// �ض���goodsinf.jsp
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}

