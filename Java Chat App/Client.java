package chatapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class Client {
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	private synchronized boolean socketClosed(){
		return socket.isClosed();
	}
	Client(){
		try {
			socket=new Socket("localhost",6666);
			System.out.println("Connected to server...");
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			readData();
			writeData();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Can't Connect...");
		}
		
	}
	private void writeData() {
		Runnable r1=()->{
			try {
				System.out.println("Writing Started...");
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
		new Thread(r1).start();
	}
	private void readData() {
		Runnable r2 = ()->{
			System.out.println("Reading Started...");
			while(!socket.isClosed()) {
				try {
					String msg = br.readLine();
					if(msg==null||msg.equals("exit")) {
						System.out.println("Chat Terminated!");
						socket.close();
						break;
					}
					System.out.println("Server: "+msg);
					
				} catch (IOException e) {
					//e.printStackTrace();
				}	
			}
					  
		}; 
		new Thread(r2).start();
	}
	public static void main(String[] args) {
		new Client();
	}
}
