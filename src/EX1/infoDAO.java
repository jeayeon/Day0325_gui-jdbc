package EX1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class infoDAO {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	public static infoDAO D = null;
	private ArrayList<infoDTO> list = null;

	public infoDAO() {

	}

	static { // 클래스가 생성될때 딱 한번말 실행(공용)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("클래스로드성공");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean Connect() {
		boolean t = false;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			t = true;
		} catch (SQLException e) {
			System.out.println("접속실패");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public ArrayList<infoDTO> list() {
		list = new ArrayList<>();
		if (Connect()) {
			try {
				String sql = "select * from basket";
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					infoDTO dto = new infoDTO();
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
					dto.setProduct(rs.getString("product"));
					dto.setQuantity(rs.getInt("quantity"));
					dto.setPrice(rs.getInt("price"));
					dto.setTotal(rs.getInt("total"));
					list.add(dto);

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return list;
	}

	public void add(infoDTO d) {

		if (Connect()) {
			try {
				String sql = "insert into basket values(?,?,?,?,?,?)";
				// 쿼리문장 완성하고 바인딩
				PreparedStatement psmt = con.prepareStatement(sql);
				// 바인딩
				psmt.setString(1, d.getId());
				psmt.setString(2, d.getName());
				psmt.setString(3, d.getProduct());
				psmt.setInt(4, d.getQuantity());
				psmt.setInt(5, d.getPrice());
				psmt.setInt(6, d.getTotal());

				psmt.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void del(int Row) {
		try {
			String sql = "delete from basket where id =? and name=? and product=? and quantity=? and price=? ";
			PreparedStatement psmt = con.prepareStatement(sql);
			// System.out.println(list.get(z).getId());
			psmt.setString(1, list.get(Row).getId());
			psmt.setString(2, list.get(Row).getName());
			psmt.setString(3, list.get(Row).getProduct());
			psmt.setInt(4, list.get(Row).getQuantity());
			psmt.setInt(5, list.get(Row).getPrice());

			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static infoDAO getInstance() {
		if (D == null) {
			D = new infoDAO();
		}
		return D;
	}

	public void mod( infoDTO d, int Row) {
		try {
			String Sql = "update basket set id=? , name=? , product=? , quantity=? , price=? , total=?"
					+ " where id=? and name=? and product=? and quantity=? and price=? ";
			PreparedStatement psmt = con.prepareStatement(Sql);
			psmt.setString(1, d.getId());
			psmt.setString(2, d.getName());
			psmt.setString(3, d.getProduct());
			psmt.setInt(4, d.getQuantity());
			psmt.setInt(5, d.getPrice());
			psmt.setInt(6, d.getTotal());
			psmt.setString(7, list.get(Row).getId());
			psmt.setString(8, list.get(Row).getName());
			psmt.setString(9, list.get(Row).getProduct());
			psmt.setInt(10, list.get(Row).getQuantity());
			psmt.setInt(11, list.get(Row).getPrice());

			psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
