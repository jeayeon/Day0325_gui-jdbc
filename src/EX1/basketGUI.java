package EX1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class basketGUI extends JFrame {// 클래스가 갖고있는 기능중 하나가 프레임이고 그 프레임을 상속 받은메소드

	private JPanel Bpanel, TextPanel, buttonPanel;
	private JLabel TotalM;

	private JTextField[] Flist = new JTextField[6];
	private JButton add, mod, del, order, save;

	// J테이블 참조변수
	private String[] numRow = { "구매자id", "이름", "상품", "수량", "단가", "합계" };
	// private String[][] data = new String[10][6]; //값을 미리넣고싶을땐 DefaultTableModel의
	// null 대신 내용을집어넣고 이데이터를넣으면됨
	private DefaultTableModel TableM = new DefaultTableModel(null, numRow);
	private JTable table = new JTable(TableM);
	private TableColumn c = null; // 테이블의 열을 나타내는 참조변수
	private JScrollPane Jp = new JScrollPane(table); // 스크롤은 프레임 기준으로

	private infoDAO DAO = infoDAO.getInstance();
	private ArrayList<infoDTO> List = null;

	basketGUI() {
		this.setLayout(new BorderLayout()); // this 프레임은 보더 레이아웃이지만 이런기능이있다는걸 보여주고싶어서씀
		this.setBounds(600, 200, 500, 400);

		List();
		TableS();
		Button();
		Add();
		del();
		mod();
		order();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);

	}


	private void List() {
		List = DAO.list();
		String[] data = new String[6];
		for (infoDTO d : List) {
			data[0] = d.getId();
			data[1] = d.getName();
			data[2] = d.getProduct();
			data[3] = String.valueOf(d.getQuantity());
			data[4] = String.valueOf(d.getPrice());
			data[5] = String.valueOf(d.getTotal());
			TableM.addRow(data);

		}

	}

	private void removeList() {
		int count = TableM.getRowCount(); //
		for (int i = count; i > 0; i--) {// 테이블의 행개수를 다 지우는 메소드
			TableM.removeRow(i - 1);
			// System.out.println(i-1+"번테이블 삭제완료");
		}
	}

	
	
	private void order() {
		order.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Order();
				
			}
		});
		
		
	}
	private void mod() {
		mod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 5; i++) {
					// 테이블에 선택한 행에 컬럼값을 스트링으로 변환후 텍스트필드에 출력하는 메소드
					Flist[i].setText((String) table.getValueAt(table.getSelectedRow(), i));
				}

			}
		});
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = table.getSelectedRow();
				String[] get = new String[5];
				for (int i = 0; i < get.length; i++) {
					get[i] = Flist[i].getText();
					Flist[i].setText("");
				}
				setDB(get, a);
			}

			private void setDB(String[] get, int a) {
				infoDTO d = new infoDTO();
				d.setId(get[0]);
				d.setName(get[1]);
				d.setProduct(get[2]);
				d.setQuantity(Integer.parseInt(get[3]));
				d.setPrice(Integer.parseInt(get[4]));
				int total = Integer.parseInt(get[3]) * Integer.parseInt(get[4]);
				d.setTotal(total);
				DAO.mod(d, a);
				removeList();
				List();

			}
		});

	}

	private void del() {
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {

				} else {
					// System.out.println(table.getSelectedRow());
					DAO.del(table.getSelectedRow());
					// List.remove(table.getSelectedRow());
					TableM.removeRow(table.getSelectedRow());

				}
			}
		});

	}

	private void Add() {
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] get = new String[5];
				for (int i = 0; i < get.length; i++) {
					get[i] = Flist[i].getText();
					// System.out.println(get[i]);
					Flist[i].setText("");
				}
				saveDB(get);
				removeList();
				List();

			}

			private void saveDB(String[] get) {
				infoDTO d = new infoDTO();
				d.setId(get[0]);
				d.setName(get[1]);
				d.setProduct(get[2]);
				d.setQuantity(Integer.parseInt(get[3]));
				d.setPrice(Integer.parseInt(get[4]));
				int total = Integer.parseInt(get[3]) * Integer.parseInt(get[4]);
				d.setTotal(total);
				DAO.add(d);

			}
		});

	}

	private void TableS() {
		table.getTableHeader().setReorderingAllowed(false);// 드래그로 해더움직임을 막기위해 있는메소드
		table.getTableHeader().setResizingAllowed(false);// 드래그해서 해더(머리글)길이를 조절하는메소드(false 고정)

		for (int i = 0; i < 5; i++) {
			c = table.getColumnModel().getColumn(i);// 전체 열중에서 한줄한줄 가져옴
			if (i == 2) {
				c.setPreferredWidth(100); // 열의 기본폭을 설정하는 메소드
			} else {
				c.setPreferredWidth(50);
			}
		}
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
					int count = table.getRowCount();//테이블행의 개수 
					int total =0;
					String id = (String) table.getValueAt(table.getSelectedRow(), 0);//리턴값이 object라 스트링변환
					for (int i = 0; i < count; i++) {
						String idList = (String)table.getValueAt(i, 0);
						String m = (String)table.getValueAt(i,5);
						int money = Integer.parseInt(m);
						if(idList.equals(id)) { //선택한 행의 id와 전체 테이블행에 아이디중 같은아이디를 비교하는 조건문
							total = total+money;
						}

					}
					String tal = Integer.toString(total);
					Flist[5].setText(tal);
				}
				if (e.getClickCount() == 2) {// 얜 왜안될까?
					
				}
				if (e.getButton() == 3) {
					System.out.println("마우스 오른쪽클릭");
				}

			}

		});

		this.add(Jp, "Center");

	}

	private void Button() {
		Bpanel = new JPanel(); // 텍스트필드를 담고있는 패널과 버튼을 담고있는 패널을 한패널에 만들기위한 패널
		TextPanel = new JPanel();
		buttonPanel = new JPanel();
		Bpanel.setLayout(new BorderLayout());
		TextPanel.setLayout(new BoxLayout(TextPanel, BoxLayout.X_AXIS));
		for (int i = 0; i < 5; i++) { // 텍스트필드 5개 집어넣음
			TextPanel.add(Flist[i] = new JTextField());
		}
		Bpanel.add(TextPanel, "North");

		add = new JButton("add");
		mod = new JButton("mod");
		del = new JButton("del");
		save = new JButton("save");
		order = new JButton("주문");
		TotalM = new JLabel("  총금액");

		buttonPanel.add(add);
		buttonPanel.add(mod);
		buttonPanel.add(del);
		buttonPanel.add(save);
		buttonPanel.add(TotalM);
		buttonPanel.add(Flist[5] = new JTextField(7));
		buttonPanel.add(order);
		Bpanel.add(buttonPanel, "South");

		this.add(Bpanel, "South");

	}

	public static void main(String[] args) {
		new basketGUI();
	}

}
