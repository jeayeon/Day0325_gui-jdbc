package EX1;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Order extends JFrame {
	JTable table = new JTable();
	JPanel Spanel;
	
	Order(){
		this.setLayout(new BorderLayout());
		this.setBounds(700, 200, 300, 250);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
	}

}
