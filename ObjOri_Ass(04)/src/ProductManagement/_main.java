package ProductManagement;

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

class AppMain extends JFrame{
	private static boolean Toggle_Frame_Color = false;
	private static int frame_Size_x = 750;
	private static int frame_Size_y = 250;
	private static Container c;
	private MyPanel1 p1 = new MyPanel1();
		private JLabel Notice = new JLabel("## 메시지 : ");
	private MyPanel2 p2 = new MyPanel2();
	private MyPanel3 p3 = new MyPanel3();
		private JButton[] btn = new JButton[3];
		private String[] btn_str = {"등록", "조회", "삭제"};
	private MyPanel4 p4 = new MyPanel4();
		private JLabel[] p4_label = new JLabel[4];
		private String[] p4_label_str = {"관리 번호", "상품명", "단 가", "제조사"};
		private JComboBox<Integer> p4_cbx = new JComboBox<Integer>();
		private JTextField[] p4_tf = new JTextField[3];
	private MyPanel5 p5 = new MyPanel5();
		private JTextArea ta = new JTextArea("관리번호\t상품명\t\t\t단가\t제조사", 13, 47);
	public AppMain() {
		super("04조_Product Manager Application V1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c = getContentPane();
		c.setLayout(new BorderLayout());
		p1.add(Notice);
		c.add(p1, BorderLayout.NORTH);
		
		for(int i=0;i<4;i++)
		{
			p4_label[i] = new JLabel(p4_label_str[i]);
			p4.add(p4_label[i]);
			if(i == 0) p4.add(p4_cbx);
			else {
				p4_tf[i - 1] = new JTextField();
				p4.add(p4_tf[i - 1]);
			}
		}
		p2.add(p4);
		ta.setEditable(false);
		p5.add(new JScrollPane(ta));
		p2.add(p5);
		c.add(p2, BorderLayout.CENTER);
		
		for(int i=0;i<3;i++) 
		{
			btn[i] = new JButton(btn_str[i]);
			p3.add(btn[i]);
		}
		c.add(p3, BorderLayout.SOUTH);
		setSize(frame_Size_x, frame_Size_y);
		this.pack();
		this.setResizable(false);
		setVisible(true);
	}
	private class MyPanel1 extends JPanel{
		public MyPanel1() {
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			if(Toggle_Frame_Color == true) this.setBackground(Color.yellow);
		}
	}
	private class MyPanel2 extends JPanel{
		public MyPanel2() {
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
			
			if(Toggle_Frame_Color == true) this.setBackground(Color.green);
		}
	}
	private class MyPanel3 extends JPanel{
		public MyPanel3() {
			if(Toggle_Frame_Color == true) this.setBackground(Color.gray);
		}
	}
	private class MyPanel4 extends JPanel{
		public MyPanel4() {
			this.setLayout(new GridLayout(4, 2));
			this.setPreferredSize(new Dimension((int)(frame_Size_x * 0.32), (int)(frame_Size_y * 1)));
			if(Toggle_Frame_Color == true) this.setBackground(Color.cyan);
		}
	}
	private class MyPanel5 extends JPanel{
		public MyPanel5() {
			this.setPreferredSize(new Dimension((int)(frame_Size_x * 0.64), (int)(frame_Size_y * 1)));
			if(Toggle_Frame_Color == true) this.setBackground(Color.magenta);
		}
	}
}
public class _main {
	public static void main(String[] args) {
		new AppMain();
	}
	// 상품 정보를 표현하는 클래스로, PRODUCTS테이블과 구조가 동일하다.
	public class Product {
		private int id;
		private String name;
		private int price;
		private String manufacture;
		public int getId() {
			return id;
		}
		public void setId(int id){
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price){
			this.price = price;
		}
		public String getManufacture() {
			return manufacture;
		}
		public void setManufacture(String manufacture){
			this.manufacture = manufacture;
		}
	}
	//
	public class ProductDAO{
		String jdbcDriver = "com.mysql.cj.jdbc.Driver";
		String jdbcURL = "jdbc:mysql://localhost/04_productmanage?serverTimezone=UTC";
		Connection conn;
		
		PreparedStatement pstmt;
		ResultSet rs;
		
		//콤보박스 아이템 관리번호를 위한 벡터
		Vector<String> items = null;
		String sql;
		//DB연결 메소드.
		public void connectDB() {
			try {
				Class.forName(jdbcDriver);
				conn = DriverManager.getConnection(jdbcURL, "root", "zzll230");
				
				System.out.println("DB연결 성공!! ");
			}catch(ClassNotFoundException e) {
				//JDBC 드라이버 로드 에러
				System.out.println("JDBC 드라이버 로드 에러.");
			}catch(SQLException e) {
				//DB 연결 에러
				System.out.println("DB 연결  실패.");
				e.printStackTrace();
			}
		}
		//DB연결 종료 메소드.
		public void closeDB() throws SQLException {
			conn.close();
		}
		// 전체 Product클래스로 구성된 ArrayList를 리턴.
		public ArrayList<Product> getAll() throws SQLException{
			connectDB();
			sql = "SELECT * FROM PRODUCTS";
			
			ArrayList<Product> Datas = new ArrayList<Product>();
			
			items = new Vector<String>();
			items.add("전체");
			
			while(rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("NAME"));
				p.setPrice(rs.getInt("PRICE"));
				p.setManufacture(rs.getString("MANUF"));
				Datas.add(p);	//레코드 ArrayList에 저장.
				items.add(String.valueOf(rs.getInt("ID")));	//관리 번호 Vector에 저장.
			}
			
			return Datas;
		}
		//관리번호(id)에 해당하는 Product클래스를 리턴.
		Product getProduct(int id) {
			//Q. 연결을 메소드 불러올 때 마다 해줘야 할까???
			sql = "SELECT * FROM PRODUCT WHERE ID = ?";
			Product p = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				rs.next();
				p = new Product();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("NAME"));
				p.setPrice(rs.getInt("PRICE"));
				p.setManufacture(rs.getString("MANUF"));
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return p;
		}
		/*
		//파라미터의 product클래스의 내용을 DB에 저장.
		boolean newProduct(Product product) {
			//DB불러오기
			sql = "INSERT INTO PRODUCTS(";
		}
		//파라미터의 관리번호(id)에 해당하는 상품을 삭제.
		boolean delProduct(int id){return true;}
		//파라미터의 Product 객체의 내용으로 업데이트.
		boolean updateProduct(Product product) {return ;}
		//콤보박스용 관리번호 목록을 리턴.
		Vector<String> getItems(){return null;}
		*/
	}
}