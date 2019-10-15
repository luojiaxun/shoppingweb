package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.Login;

/**
 * Servlet implementation class buyServlet
 */
@WebServlet("/buyServlet")
public class buyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public buyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String buyGoodsMess = request.getParameter("buy");
		String number[]=request.getParameterValues("number");//系统固定为100了
		if (buyGoodsMess == null || buyGoodsMess.length() == 0) {
			fail(request, response, "购物车没有物品，无法生成订单");
			return;
		}
		String price = request.getParameter("price");
		if (price == null || price.length() == 0) {
			fail(request, response, "没有计算价格和，无法生成订单");
			return;
		}
		float sum = Float.parseFloat(price);
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try {
			loginBean = (Login) session.getAttribute("loginBean");
			boolean b = loginBean.getLogname() == null || loginBean.getLogname().length() == 0;
			if (b)
				response.sendRedirect("login.jsp");// 重定向到登录页面
		} catch (Exception exp) {
			response.sendRedirect("login.jsp");// 重定向到登录页面
		}
		String uri="jdbc:mysql://localhost:3306/shop?"+
                "user=root&password=1234&characterEncoding=utf-8&serverTimezone=UTC";
		Connection con;
		PreparedStatement sql;
		int[] a=new int[100];
		boolean flag=true;
		String name = null;
		try {
			con = DriverManager.getConnection(uri);
			for(int k=0;k<number.length;k++)//保存原库存量防止数量不够,进行恢复。
			{	
				if(number[k]==null)//退出循环
					break;	
				if(k==0)
				{
					String queryquantity = "select quantity from inf where number =? ";
					sql = con.prepareStatement(queryquantity);
					sql.setString(1, number[k]);
					ResultSet rs=sql.executeQuery();
					if (rs.next()) { 
						a[k]=Integer.valueOf(rs.getString(1));
					}
				}
				for(int j=0;j<k;j++){
				if(number[k]==number[j]&&j!=k){
					continue;
				}
				else{	
				String queryquantity = "select quantity from inf where number =? ";
				sql = con.prepareStatement(queryquantity);
				sql.setString(1, number[k]);
				ResultSet rs=sql.executeQuery();
				if (rs.next()) { 
				a[k]=Integer.valueOf(rs.getString(1));
				}
				}
			}
		}
		for(int k=0;k<number.length;k++)
			{
			if(number[k]==null)//退出循环
				break;	
			String queryquantity = "select quantity from inf where number =? ";
			String quantity = "update inf set quantity=? where number =? ";
			sql = con.prepareStatement(queryquantity);//判断库存量
			sql.setString(1, number[k]);
			ResultSet rs=sql.executeQuery();
			if (rs.next()) { 
			if(rs.getInt(1)>0)//库存量更新
			{
			sql=con.prepareStatement(quantity);
			sql.setInt(1,rs.getInt(1)-1);
			sql.setString(2, number[k]);
			sql.executeUpdate();
			}
			else{
			String queryname = "select name from inf where number =? ";
			sql = con.prepareStatement(queryname);//获取库存量不够的名字
			sql.setString(1, number[k]);
			rs=sql.executeQuery();
			if(rs.next())
			{
			name=rs.getString(1);
			flag=false;
			break;
			}
			}
		}
	}
			if(flag==true)
			{	
				String insertCondition = "INSERT INTO orderform VALUES (?,?,?,?)";
				sql = con.prepareStatement(insertCondition);
				sql.setInt(1, 0); // 订单序号会自动增加
				sql.setString(2, loginBean.getLogname());
				sql.setString(3, buyGoodsMess);
				sql.setFloat(4, sum);
				sql.executeUpdate();
				LinkedList car = loginBean.getCar();
				car.clear(); // 清空购物车
				success(request, response, "生成订单成功");
			}
			else{
				for(int k=0;k<number.length;k++)
				{
				if(number[k]==null)//退出循环
					break;
				if(k==0)
				{
					String quantity = "update inf set quantity=? where number =? ";
					sql=con.prepareStatement(quantity);
					sql.setInt(1,a[k]);
					sql.setString(2, number[k]);
					sql.executeUpdate();
				}
				for(int j=0;j<k;j++){
				if(number[k]==number[j]&&j!=k){
					continue;
				}
				else{
				String quantity = "update inf set quantity=? where number =? ";
				sql=con.prepareStatement(quantity);
				sql.setInt(1,a[k]);
				sql.setString(2, number[k]);
				sql.executeUpdate();
				}
				}
			}
				fail(request, response, "生成订单失败,"+name+"库存量不足");
		}
		}
			catch (SQLException exp) {
			fail(request, response, "生成订单失败" + exp);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void success(HttpServletRequest request, HttpServletResponse response, String backNews) {
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body style=background-image:url(images/background1.jpg);background-size:cover>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("返回主页<br>");
			out.println("<a href =index.jsp>主页</a><br>");
			out.println("查看订单");
			out.println("<br><a href =lookorder.jsp>查看订单</a>");
			out.println("</body></html>");
		} catch (IOException exp) {
		}
	}

	public void fail(HttpServletRequest request, HttpServletResponse response, String backNews) {
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body style=background-image:url(images/background1.jpg);background-size:cover>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("返回主页：");
			out.println("<a href =index.jsp>主页</a>");
			out.println("</body></html>");
		} catch (IOException exp) {
		}
	}
}
