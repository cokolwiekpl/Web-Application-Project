package wap.DatabaseServlets;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowTable
 */
@WebServlet("/UserInfo")
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String databaseSchema = "wap";
	private String username = "root";
	private String password = "wasd951753";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseSchema + "?serverTimezone=UTC",
					username, password);
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE idUser = ?");
			ps.setInt(1, Integer.parseInt(request.getParameter("uid"))); //Integer.parseInt(request.getParameter("uid"))
			//System.out.println(request.getParameter("uid"));

			ResultSet set = ps.executeQuery();
			//System.out.println("test");
			
			//response.getWriter().print("uid is" + set.first());
			//System.out.println("uid is" );
			//if(set.next()) {
			//	System.out.println("test w ifieie");
				//response.getWriter().print("Your name is" + set.getString("Nick"));
				
				response.getWriter().append(getHtmlTableFromResultSet(set));
			//}
			
			
			
			
		} catch (Exception e) {

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String getHtmlTableFromResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int colCount = meta.getColumnCount();
		String htmlTable = "<table border=\"1\">";
		// header row:
		htmlTable += "<tr>";
		for (int col = 1; col <= colCount; col++) {
			htmlTable += "<th>";
			htmlTable += meta.getColumnLabel(col);
			htmlTable += "</th>";
		}
		htmlTable += "</tr>";
		// data rows:
		while (rs.next()) {

			htmlTable += "<tr>";

			for (int col = 1; col <= colCount; col++) {
				Object value = rs.getObject(col);
				htmlTable += "<td>";
				if (value != null) {
					htmlTable += value.toString();
				}
				htmlTable += "</td>";
			}
			htmlTable += "</tr>";
		}
		htmlTable += "</table>";
		return htmlTable;
	}
}
