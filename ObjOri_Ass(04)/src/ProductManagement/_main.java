package ProductManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
class Product {
	
}
class ProductDAO{
	
}
public class _main {
	public static void main(String[] args) {
		new AppMain();
	}
}
