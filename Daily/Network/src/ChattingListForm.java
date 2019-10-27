import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ChattingListForm extends JFrame {

	Color color;

	private JFrame frame;
	private JLabel label;
	private JPanel panel;

	Container contentPane;
	String tableCells[][] = new String[15][3]; // 15부분에 디비 갯수
	String colNames[] = { "번호", "방 제목", "인원 수" };
	JTable table;
	JScrollPane scrollpane;
	JPanel bottomPanel;
	JButton makeButton;
	JTextField roomText;

	JPanel listPanel;
	JLabel listLabel;
	JList list;
	DefaultListModel listModel;
	JScrollPane Listscrollpane;
	JTextField listText;
	JPanel btnPanel;
	JButton addButton;
	JButton delButton;

	Connection con = null;
	String sql;
	String url;

	public ChattingListForm() {

		color = new Color(243, 218, 232); // 위 아래 색깔 (바꿔도 돼요)

		frame = new JFrame();
		contentPane = frame.getContentPane();

		panel = new JPanel();
		label = new JLabel();
		roomText = new JTextField(20);
		table = new JTable(tableCells, colNames);

		list = new JList(new DefaultListModel());
		listModel = (DefaultListModel) list.getModel();
		listPanel = new JPanel();
		listLabel = new JLabel();
		listText = new JTextField(9);
		addButton = new JButton("추가");
		delButton = new JButton("삭제");
		btnPanel = new JPanel();

		bottomPanel = new JPanel();
		makeButton = new JButton("방 만들기");
		makeButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		makeButton.setBackground(Color.BLACK);
		makeButton.setForeground(Color.WHITE);
		show();
	}

	public void show() {

		// label
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setText("채팅방 목록");

		panel.setBackground(color);
		panel.add(label);
		frame.add(BorderLayout.NORTH, panel);

		// 방 만들기
		bottomPanel.setBackground(color);
		roomText.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		bottomPanel.add(roomText);
		bottomPanel.add(makeButton);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		// table
		table.setRowHeight(40);
		table.getColumn("번호").setPreferredWidth(5);
		table.getColumn("방 제목").setPreferredWidth(300);
		table.getColumn("인원 수").setPreferredWidth(5);
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(550, 500));
		contentPane.add(scrollpane, BorderLayout.WEST);

		// list
		listLabel.setText("친구 목록");
		listLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		Listscrollpane = new JScrollPane(list);
		Listscrollpane.setPreferredSize(new Dimension(130, 280));
		listPanel.add(listLabel, BorderLayout.NORTH);
		listPanel.add(Listscrollpane, BorderLayout.SOUTH);

		listText.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		listPanel.add(listText, BorderLayout.SOUTH);

		// 친구 추가 버튼
		addButton.setPreferredSize(new Dimension(60, 28));
		addButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		addButton.setBackground(Color.BLACK);
		addButton.setForeground(Color.WHITE);
		btnPanel.add(addButton, BorderLayout.WEST);

		// 친구 삭제 버튼
		delButton.setPreferredSize(new Dimension(60, 28));
		delButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		delButton.setBackground(Color.RED);
		delButton.setForeground(Color.WHITE);
		btnPanel.add(delButton, BorderLayout.EAST);

		listPanel.add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setBackground(Color.WHITE);
		listPanel.setBackground(Color.WHITE);

		frame.add(listPanel, BorderLayout.CENTER);

		/* 친구 추가 버튼 클릭 리스너 */
	

		// frame
		frame.addWindowListener(new WindowAdapter() { // 창 x키 누르면 닫히는거
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		Dimension dim = new Dimension(700, 500); // 윈도우 창 크기
		frame.setPreferredSize(dim);
		frame.setTitle("데일리 채팅 프로그램");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터화면의 해상도 얻기

		// 프레임이 화면 중앙에 위치하도록 left, top 계산
		int left = (screen.width / 2) - (700 / 2);
		int top = (screen.height / 2) - (500 / 2);

		setLocation(left, top);
	}
}
