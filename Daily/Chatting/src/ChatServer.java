import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ChatServer {
	public static final int PORT = 5000;

	public static void main(String[] args) {
		Connection con = null;
		String url = "jdbc:mysql:///Daily?serverTimezone=Asia/Seoul"; // mysql 저장소 url을 고쳐주세요
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,"root","1234");
			Statement stmt = con.createStatement();
			String sql ="delete from userCheck;";
			stmt.executeUpdate(sql);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ServerSocket serverSocket = null;
		List<PrintWriter> listWriters = new ArrayList<PrintWriter>();

		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			consoleLog("연결 기다림 - " + hostAddress + ":" + PORT);

			// 3. 요청 대기
			while (true) {
				Socket socket = serverSocket.accept();
				new ChatServerProcessThread(socket, listWriters).start();
				System.out.println("접속됨");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void consoleLog(String log) {
		System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
	}
}