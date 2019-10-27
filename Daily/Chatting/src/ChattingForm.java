
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.Connection;

public class ChattingForm extends JFrame implements ActionListener {

	Color color;
	Color color2;

	private Frame frame;
	private Panel panel;
	private Panel textpanel;
	private Label label;
	private Label label2;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	
	Connection con=null;
	
	String url="jdbc:mysql:///Daily?serverTimezone=Asia/Seoul";
	Socket socket;
	String Link_id;
	String Link_name;
	String title;
	String [] FriendList;
	
	PrintWriter printWriter;
	public ChattingForm(String title, String[] friendid ,String id, String name, Socket socket) {
		color = new Color(243, 218, 232); // 위 아래 색깔 (바꿔도 돼요)
		color2 = new Color(255, 250, 251); // 채팅창 바탕 색깔 (바꿔도 돼요)

		frame = new Frame();
		panel = new Panel();
		textpanel = new Panel();
		buttonSend = new Button("전송");
		textField = new TextField();
		label = new Label();
		label2 = new Label();
		textArea = new TextArea(30, 80);
		
		this.socket = socket;
		this.Link_id = id;
		this.Link_name = name;
		this.FriendList = friendid;
		this.title = title;
		
		show();
		printWriter.println("chatting-join:"+Link_name);
		try {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, "root", "1234");
		Statement stmt = con.createStatement();
		
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		buttonSend.addActionListener(this);
		
	}

	public void show() {
		// Button
		buttonSend.setBackground(new Color(209, 82, 119));
		buttonSend.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonSend.setForeground(Color.WHITE);

		// label (방 제목)
		label.setText("방 제목");
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		label.setBackground(color);
		label.setPreferredSize(new Dimension(700, 30));
		textpanel.add(label, BorderLayout.NORTH);

		// label (참여자)
		label2.setText("백화랑,유성재,전웅재,장주리,원윤희");
		label2.setAlignment(Label.CENTER);
		label2.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label2.setBackground(color);
		label2.setPreferredSize(new Dimension(700, 30));
		textpanel.add(label2, BorderLayout.SOUTH);

		textpanel.setPreferredSize(new Dimension(700, 70));
		textpanel.setBackground(color);
		frame.add(BorderLayout.NORTH, textpanel);

		// Textfield
		textField.setColumns(50);
		textField.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		// Pannel
		panel.setBackground(color);
		panel.add(textField);
		panel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, panel);

		// TextArea
		textArea.setEditable(false);
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textArea.setBackground(color2);
		frame.add(BorderLayout.CENTER, textArea);

		
		// frame
		frame.addWindowListener(new WindowAdapter() { // 창 x키 누르면 닫히는거
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});

		Dimension dim = new Dimension(700, 500); // 윈도우 창 크기
		frame.setPreferredSize(dim);
		frame.setTitle("데일리 채팅 프로그램");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터화면의 해상도 얻기

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// 프레임이 화면 중앙에 위치하도록 left, top 계산
		int left = (screen.width / 2) - (700 / 2);
		int top = (screen.height / 2) - (500 / 2);

		setLocation(left, top);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == buttonSend) {
			printWriter.println("sendMassage:"+Link_id+"&#34"+textField.getText()+"&#34");
			textField.setText("");
		}
	}
	
	private class ChatFormThread extends Thread{
		Socket socket;
		public ChatFormThread(Socket socket) {
			// TODO Auto-generated constructor stub
			this.socket = socket;
		}
		public void run() {
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				while(true) {
					String msg = br.readLine();
					String tokens[] = msg.split(":");
					if(tokens[0].equals("message")) {
						
					}
					if(tokens[0].equals("quit"))
					{
						
					}
					if(tokens[0].equals("chatting-join")) {
						textArea.append(msg+"님이 채팅방에 참가하셨습니다.");
	                    textArea.append("\n");
					}					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}