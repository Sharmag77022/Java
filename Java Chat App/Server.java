package chatapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	ServerSocket serverSocket;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	private synchronized boolean socketClosed(){
		return socket.isClosed();
	}
	Server(){
		try{
			serverSocket = new ServerSocket(6666);
			System.out.println("Server is waiting for a new Connection...");
			socket = serverSocket.accept();
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			readData();
			writeData();
			
		}catch(Exception e) {
			System.out.println("Can't Connect...");
		}
		
	}
	private void readData() {
		Runnable r1 = ()->{
			System.out.println("Reading Started...");
			try {
			while(!socket.isClosed()) {
				
					String msg = br.readLine();
					
					if(msg==null||msg.equals("exit")) {
						System.out.println("Chat Terminated!");
						socket.close();
						break;
					}
					System.out.println("Client: "+msg);
					
				} 
			}catch (IOException e) { 
				//e.printStackTrace();
			}	
					  
		}; 
		new Thread(r1).start();
	}
	private void writeData() {
		Runnable r2 = ()->{
			System.out.println("Writing Started...");
		try {
		while(!socket.isClosed()) {
			
				BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
				String msg = br1.readLine();
				if(socketClosed()){
					br1.close();
					break;
				}
				out.println(msg);
				if(msg.equals("exit")){
					socket.close();
					br1.close();
					System.out.println("You Terminated This Chat...");
					break;
				}
				out.flush();
			
			
		}
	} catch (Exception e) {
				//e.printStackTrace();
			}
		};
		new Thread(r2).start();
	}
	public static void main(String[] args) {
		new Server();
	}
	
}
