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
			private JLabel Notice = new JLabel("## �޽��� : ");
		private MyPanel2 p2 = new MyPanel2();
		private MyPanel3 p3 = new MyPanel3();
			private JButton[] btn = new JButton[3];
		private String[] btn_str = {"���", "��ȸ", "����"};
			private MyPanel4 p4 = new MyPanel4();
		private JLabel[] p4_label = new JLabel[4];
			private String[] p4_label_str = {"���� ��ȣ", "��ǰ��", "�� ��", "������"};
			private JComboBox<String> p4_cbx = new JComboBox<String>();
			private JTextField[] p4_tf = new JTextField[3];
		private MyPanel5 p5 = new MyPanel5();
			private String ta_col = "������ȣ\t��ǰ��\t\t\t�ܰ�\t������";
			private JTextArea ta = new JTextArea(ta_col, 13, 47);
		public Product pr = new Product();
		public ProductDAO pd = new ProductDAO();
		public AppMain() throws SQLException {
			super("04��_Product Manager Application V1.0");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pd.connectDB();
			
			c = getContentPane();
			c.setLayout(new BorderLayout());
			p1.add(Notice);
			c.add(p1, BorderLayout.NORTH);
			
			p4_cbx.addItem("��ü");
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
		//JPanel�� ��ӹ��� MyPanel Ŭ������
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
		// ������ ���� �κ�
		///////////////////////////////////////
		private void View() {
			ArrayList<Product> Res = new ArrayList<Product>();
			String str = "";
			Product l;
			try {
				if(p4_cbx.getSelectedIndex() == 0) { //��ü ��ȸ
					Res = pd.getAll();
					for(int i=0;i<Res.size();i++) {
						l = Res.get(i);
						str += l.toString()+"\n";
					}
					ta.setText(ta_col + "\n" + str);
					
					Notice.setText("## �޽��� : ��ü ��ǰ�� ��ȸ�մϴ�.");
				}
				else { //Ư�� ������ȣ�� ��ǰ ��ȸ
					l = pd.getProduct(p4_cbx.getSelectedIndex());
					
					Notice.setText("## �޽��� : "+ l.getName() +" ��ǰ�� ��ȸ�մϴ�.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		private void Insertion() {
			//��� ���� �޼ҵ�
			if(p4_cbx.getSelectedIndex() == 0) {/* ��ü�׸��� ����Ų ���¿��� ��� ��ư�� ���� Ʃ���� INSERT�� �����Ϸ��� ��� */
				//������ ��� ������ȣ ������ �ʿ�
				Product p = new Product();
				p.setName(p4_tf[0].getText());
				p.setPrice(Integer.parseInt(p4_tf[1].getText()));
				p.setManufacture(p4_tf[2].getText());
				Boolean result = pd.newProduct(p);
				if(result) {
					Notice.setText("## �޽��� : "+ p.getName() +" �� ���������� DB�� �����Ͽ����ϴ�.");
				}
				else{
					Notice.setText("## ERROR : "+ p.getName() +" �� �����ϴµ� �����߽��ϴ�.");
				}
			}
			else {/* Ư�� ���� ��ȣ�� ������ �� ����� ���� Ʃ���� ������Ʈ�� �����Ϸ��� ��� */
				//������Ʈ�� ��� ������ȣ ������ �ʿ� ����.
				Product p = new Product();
				p.setId(p4_cbx.getSelectedIndex());
				p.setName(p4_tf[0].getText());
				p.setPrice(Integer.parseInt(p4_tf[1].getText()));
				p.setManufacture(p4_tf[2].getText());
				Boolean result = pd.updateProduct(p);
				if(result) {
					Notice.setText("## �޽��� : "+ p.getName() +" �� ���������� �����߽��ϴ�.");
				}
				else {
					Notice.setText("## �޽��� : "+ p.getName() +" �� �����ϴµ� �����߽��ϴ�.");
				}
			}
		}
		private void Deletion() {
			int id = p4_cbx.getSelectedIndex();
			//���� ���� �޼ҵ�
			Boolean result = pd.delProduct(id);
			if(result) {
				Notice.setText("## �޽��� : �����׸�"+ id +"���� ���������� DB���� �����߽��ϴ�.");
			}
			else{
				Notice.setText("## ERROR : �����׸�"+ id +"���� �����ϴµ� �����߽��ϴ�.");
			}
			Vector<String> v = pd.getItems();
			p4_cbx.removeAllItems();
			p4_cbx.addItem("��ü");
			for(int i=0;i<v.size();i++)
				p4_cbx.addItem(v.get(i));
		}
		private class AL implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				
				if(btn.getText().equals("���")) {
					Insertion();
				}
				else if(btn.getText().equals("��ȸ")) {
					View();
				}
				else { //����
					Deletion();
				}
			}
		}
		
		///////////////////////////////////////
		// ��ǰ ������ ǥ���ϴ� Ŭ������, PRODUCTS���̺�� ������ �����ϴ�.
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
		// ������ ���̽� ����. 
		///////////////////////////////////////
		class ProductDAO{
			String jdbcDriver = "com.mysql.cj.jdbc.Driver";
			String jdbcURL = "jdbc:mysql://localhost/04_productmanage?serverTimezone=UTC";
			Connection conn;
			
			PreparedStatement pstmt;
			Statement stmt;
			ResultSet rs;
			
			//�޺��ڽ� ������ ������ȣ�� ���� ����
			//Vector<String> items = null;
			String sql;
			//DB���� �޼ҵ�.
			public void connectDB() {
				try {
					Class.forName(jdbcDriver);
					conn = DriverManager.getConnection(jdbcURL, "root", "zzll230");
					
					System.out.println("DB���� ����!! ");
				}catch(ClassNotFoundException e) {
					//JDBC ����̹� �ε� ����
					System.out.println("JDBC ����̹� �ε� ����.");
				}catch(SQLException e) {
					//DB ���� ����
					System.out.println("DB ����  ����.");
					e.printStackTrace();
				}
			}
			//DB���� ���� �޼ҵ�.
			public void closeDB() throws SQLException {
				conn.close();
			}
			// ��ü ProductŬ������ ������ ArrayList�� ����.
			public ArrayList<Product> getAll() throws SQLException{
				sql = "SELECT * FROM PRODUCTS";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				ArrayList<Product> Datas = new ArrayList<Product>();
				
				p4_cbx.removeAllItems();
				//items = new Vector<String>();
				p4_cbx.addItem("��ü");
				
				while(rs.next()) {
					Product p = new Product();
					p.setId(rs.getInt("ID"));
					p.setName(rs.getString("NAME"));
					p.setPrice(rs.getInt("PRICE"));
					p.setManufacture(rs.getString("MANUF"));
					//System.out.println(p.toString());
					Datas.add(p);	//���ڵ� ArrayList�� ����.
					//items.add(String.valueOf(rs.getInt("ID")));	//���� ��ȣ Vector�� ����.
					p4_cbx.addItem(String.valueOf(rs.getInt("ID")));
				}
				
				for(int i=0;i<3;i++)
					p4_tf[i].setText("");
				
				return Datas;
			}
			//������ȣ(id)�� �ش��ϴ� ProductŬ������ ����.
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
			//�Ķ������ productŬ������ ������ DB�� ����.
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
					result = pstmt.executeUpdate();	//Update�� ���ڵ� ���� ��ȯ.
					
					conn.createStatement().executeQuery(sql1);
					conn.createStatement().executeUpdate(sql2);
					
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return (result <= 0)?false:true;
			}
			//�Ķ������ ������ȣ(id)�� �ش��ϴ� ��ǰ�� ����.
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
			//�Ķ������ Product ��ü�� �������� ������Ʈ.
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
			//�޺��ڽ��� ������ȣ ����� ����.
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
