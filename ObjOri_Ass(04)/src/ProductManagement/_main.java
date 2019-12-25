package ProductManagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class _main {
	public static class AppMain extends JFrame{
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
			private JComboBox<String> p4_cbx = new JComboBox<String>();
			private JTextField[] p4_tf = new JTextField[3];
		private MyPanel5 p5 = new MyPanel5();
			private String ta_col = "관리번호\t상품명\t\t\t단가\t제조사";
			private JTextArea ta = new JTextArea(ta_col, 13, 47);
		public Product pr = new Product();
		public ProductDAO pd = new ProductDAO();
		public AppMain() throws SQLException {
			super("04조_Product Manager Application V1.0");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pd.connectDB();
			
			c = getContentPane();
			c.setLayout(new BorderLayout());
			p1.add(Notice);
			c.add(p1, BorderLayout.NORTH);
			
			p4_cbx.addItem("전체");
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
				btn[i].addActionListener(new AL());
				p3.add(btn[i]);
			}
			c.add(p3, BorderLayout.SOUTH);
			setSize(frame_Size_x, frame_Size_y);
			this.pack();
			this.setResizable(false);
			setVisible(true);
		}
		////////////////////////////
		//JPanel을 상속받은 MyPanel 클래스들
		////////////////////////////
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
		///////////////////////////////////////
		// 리스너 구현 부분
		///////////////////////////////////////
		private void View() {
			ArrayList<Product> Res = new ArrayList<Product>();
			String str = "";
			Product l;
			try {
				if(p4_cbx.getSelectedIndex() == 0) { //전체 조회
					Res = pd.getAll();
					for(int i=0;i<Res.size();i++) {
						l = Res.get(i);
						str += l.toString()+"\n";
					}
					ta.setText(ta_col + "\n" + str);
					
					Notice.setText("## 메시지 : 전체 상품을 조회합니다.");
				}
				else { //특정 관리번호의 상품 조회
					l = pd.getProduct(p4_cbx.getSelectedIndex());
					
					Notice.setText("## 메시지 : "+ l.getName() +" 상품을 조회합니다.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		private void Insertion() {
			//등록 관련 메소드
			if(p4_cbx.getSelectedIndex() == 0) {/* 전체항목을 가리킨 상태에서 등록 버튼을 누른 튜플의 INSERT를 진행하려는 경우 */
				//삽입의 경우 관리번호 갱신이 필요
				Product p = new Product();
				p.setName(p4_tf[0].getText());
				p.setPrice(Integer.parseInt(p4_tf[1].getText()));
				p.setManufacture(p4_tf[2].getText());
				Boolean result = pd.newProduct(p);
				if(result) {
					Notice.setText("## 메시지 : "+ p.getName() +" 을 정상적으로 DB에 저장하였습니다.");
				}
				else{
					Notice.setText("## ERROR : "+ p.getName() +" 을 저장하는데 실패했습니다.");
				}
			}
			else {/* 특정 관리 번호를 선택한 뒤 등록을 누른 튜플의 업데이트를 진행하려는 경우 */
				//업데이트의 경우 관리번호 갱신이 필요 없음.
				Product p = new Product();
				p.setId(p4_cbx.getSelectedIndex());
				p.setName(p4_tf[0].getText());
				p.setPrice(Integer.parseInt(p4_tf[1].getText()));
				p.setManufacture(p4_tf[2].getText());
				Boolean result = pd.updateProduct(p);
				if(result) {
					Notice.setText("## 메시지 : "+ p.getName() +" 을 정상적으로 수정했습니다.");
				}
				else {
					Notice.setText("## 메시지 : "+ p.getName() +" 을 수정하는데 실패했습니다.");
				}
			}
		}
		private void Deletion() {
			int id = p4_cbx.getSelectedIndex();
			//삭제 관련 메소드
			Boolean result = pd.delProduct(id);
			if(result) {
				Notice.setText("## 메시지 : 관리항목"+ id +"번을 정상적으로 DB에서 삭제했습니다.");
			}
			else{
				Notice.setText("## ERROR : 관리항목"+ id +"번을 삭제하는데 실패했습니다.");
			}
			Vector<String> v = pd.getItems();
			p4_cbx.removeAllItems();
			p4_cbx.addItem("전체");
			for(int i=0;i<v.size();i++)
				p4_cbx.addItem(v.get(i));
		}
		private class AL implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				
				if(btn.getText().equals("등록")) {
					Insertion();
				}
				else if(btn.getText().equals("조회")) {
					View();
				}
				else { //삭제
					Deletion();
				}
			}
		}
		
		///////////////////////////////////////
		// 상품 정보를 표현하는 클래스로, PRODUCTS테이블과 구조가 동일하다.
		///////////////////////////////////////
		class Product {
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
			public String toString() {
				return this.id+"\t"+this.name+"\t\t\t"+this.price+"\t"+this.manufacture;
			}
		}
		
		///////////////////////////////////////
		// 데이터 베이스 연동. 
		///////////////////////////////////////
		class ProductDAO{
			String jdbcDriver = "com.mysql.cj.jdbc.Driver";
			String jdbcURL = "jdbc:mysql://localhost/04_productmanage?serverTimezone=UTC";
			Connection conn;
			
			PreparedStatement pstmt;
			Statement stmt;
			ResultSet rs;
			
			//콤보박스 아이템 관리번호를 위한 벡터
			//Vector<String> items = null;
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
				sql = "SELECT * FROM PRODUCTS";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				ArrayList<Product> Datas = new ArrayList<Product>();
				
				p4_cbx.removeAllItems();
				//items = new Vector<String>();
				p4_cbx.addItem("전체");
				
				while(rs.next()) {
					Product p = new Product();
					p.setId(rs.getInt("ID"));
					p.setName(rs.getString("NAME"));
					p.setPrice(rs.getInt("PRICE"));
					p.setManufacture(rs.getString("MANUF"));
					//System.out.println(p.toString());
					Datas.add(p);	//레코드 ArrayList에 저장.
					//items.add(String.valueOf(rs.getInt("ID")));	//관리 번호 Vector에 저장.
					p4_cbx.addItem(String.valueOf(rs.getInt("ID")));
				}
				
				for(int i=0;i<3;i++)
					p4_tf[i].setText("");
				
				return Datas;
			}
			//관리번호(id)에 해당하는 Product클래스를 리턴.
			Product getProduct(int id) {
				sql = "SELECT * FROM PRODUCTS WHERE ID = ?";
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
					
					p4_tf[0].setText(p.getName());
					p4_tf[1].setText(String.valueOf(p.getPrice()));
					p4_tf[2].setText(p.getManufacture());
					
					rs.close();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return p;
			}
			//파라미터의 product클래스의 내용을 DB에 저장.
			boolean newProduct(Product p) {
				int result = 0;
				sql = "INSERT INTO 04_productmanage.products(PRODUCTS.NAME, PRODUCTS.PRICE, PRODUCTS.MANUF) VALUES(?, ?, ?)";
				String sql1 = "SET @CNT = 0";
				String sql2 = "UPDATE PRODUCTS SET PRODUCTS.ID = @CNT:=@CNT+1";
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, p.getName());
					pstmt.setInt(2, p.getPrice());
					pstmt.setString(3, p.getManufacture());
					result = pstmt.executeUpdate();	//Update된 레코드 수를 반환.
					
					conn.createStatement().executeQuery(sql1);
					conn.createStatement().executeUpdate(sql2);
					
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return (result <= 0)?false:true;
			}
			//파라미터의 관리번호(id)에 해당하는 상품을 삭제.
			boolean delProduct(int id){
				int result = 0;
				sql = "DELETE FROM PRODUCTS WHERE ID = ?";
				String sql1 = "SET @CNT = 0";
				String sql2 = "UPDATE PRODUCTS SET PRODUCTS.ID = @CNT:=@CNT+1";
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);
					result = pstmt.executeUpdate();
					
					conn.createStatement().executeQuery(sql1);
					conn.createStatement().executeUpdate(sql2);
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return (result <= 0)?false:true;
			}
			//파라미터의 Product 객체의 내용으로 업데이트.
			boolean updateProduct(Product p){
				int result = 0;
				sql = "UPDATE PRODUCTS SET NAME = ?, PRICE = ?, MANUF = ? WHERE ID = ?";
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, p.getName());
					pstmt.setInt(2, p.getPrice());
					pstmt.setString(3, p.getManufacture());
					pstmt.setInt(4, p.getId());
					result = pstmt.executeUpdate();
					
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return (result<=0)?false:true;
			}
			//콤보박스용 관리번호 목록을 리턴.
			Vector<String> getItems(){
				Vector<String> v = new Vector<String>();
				sql = "SELECT ID FROM products";
				try {
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()) {
						v.add(String.valueOf(rs.getInt("ID")));
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
				
				return v;
			}
		}
	}
	public static void main(String[] args) throws SQLException {
		new AppMain();
	}
}
