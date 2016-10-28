package udp;

import java.net.*;
import java.io.*;


public class Client {
	public static void main(String args[]) throws IOException{
		String serv_addr = "0.0.0.0";
		int PORT_M = 7777;
		int PORT_D = 7778;
		int MAX = 65507;
		Device dev = new Device(1,12,"ASDFGHJKL");
		
		MulticastSocket sock = new MulticastSocket(PORT_M);
		InetAddress addr = InetAddress.getByName(serv_addr);
		sock.joinGroup(addr);
		
		byte [] mess = new byte[MAX];
		DatagramPacket p = new DatagramPacket(mess, mess.length);
		sock.receive(p);
		
		String response = dev.getNum() + " " + dev.getId() + " " + dev.getMac();
		byte [] res = response.getBytes();
		DatagramPacket rp = new DatagramPacket(res, res.length);
		sock.send(rp);
		sock.close();
		
		DatagramSocket d_sock = new DatagramSocket(PORT_D);
		long ms_wait = 1000;
		while(true){
			byte[] msg = {'i','m','a','l','i','v','e'};
			DatagramPacket packet=new DatagramPacket(msg, msg.length, addr, PORT_D);
			d_sock.send(packet);
			try {
				Thread.sleep(ms_wait);
			} catch (InterruptedException e){
				d_sock.close();
				e.printStackTrace();
			}
		}
	}
}
