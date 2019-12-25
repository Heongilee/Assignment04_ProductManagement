package ProductManagement;

import javax.swing.JFrame;

class MyFrame extends JFrame{
	 public MyFrame() {
		 super("04Á¶_Product Management Program");
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
