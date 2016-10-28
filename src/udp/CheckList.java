package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class CheckList extends Thread{
	private int PORT;
	private InetAddress addr;
	private MulticastSocket sock;

	public CheckList(MulticastSocket sock, InetAddress addr){
		this.sock=sock;
		this.addr = addr;
		PORT = 7777;
	}	

	public void run(){
		long ms_wait=2000;
		
        while(true){
			try {
				checkAlive();
				System.out.println("ho inviato un isalive\n");
				Thread.sleep(ms_wait);
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	public void checkAlive() throws IOException{
		byte[] msg={'i','s','a','l','i','v','e'};
		//		MulticastSocket sock=new MulticastSocket();
		//		InetAddress addr=InetAddress.getByName("0.0.0.0");
		DatagramPacket packet=new DatagramPacket(msg,msg.length,addr,PORT);
		sock.send(packet);
	}
}
