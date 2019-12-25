package ProductManagement;

import javax.swing.JFrame;

class MyFrame extends JFrame{
	 public MyFrame() {
		 super("04조_상품 관리 프로그램");
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 setSize(800, 300);
		 setVisible(true);
	}
}
public class _main {
	public static void main(String[] args) {
		new MyFrame();
	}
}
