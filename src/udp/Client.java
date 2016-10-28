package udp;
import java.net.*;
import java.io.*;

/**
 * Classe contenente tutti i metodi per la gestione di un Client dell'architettura.
 * 
 * @author Giuseppe De Gregorio, Amedeo Fortino, Francesca Sabatino
 * @version 1.2.0
 */
public class Client {
	public static void main(String args[]) throws IOException{
		String serv_addr = "0.0.0.0";
		int PORT_M = 7777;
		int PORT_D = 7778;
		int MAX = 65507;
		long MS_WAIT = 1000;
		
		// Istanzio un device sul client
		Device dev = new Device(1,"ASDF");
		
		// Resta in attesa del messaggio in Multicast per l'attivazione
		MulticastSocket sock = new MulticastSocket(PORT_M);
		InetAddress addr = InetAddress.getByName(serv_addr);
		sock.joinGroup(addr);
		byte [] mess = new byte[MAX];
		DatagramPacket pack = new DatagramPacket(mess, mess.length);
		sock.receive(pack);
		sock.close();
		
		/*
		 * Una volta che il messaggio è stato ricevuto la socket Multicast
		 * viene chiusa e viene aperta una socket Datagram per rispondere
		 * inviando il suo id ed il suo MAC address.
		 */
		DatagramSocket d_sock = new DatagramSocket(PORT_D);
		String response = dev.getId() + " " + dev.getMac();
		byte [] res = response.getBytes();
		DatagramPacket res_pack = new DatagramPacket(res, res.length);
		d_sock.send(res_pack);
		
		/*
		 * A questo punto il Client è operativo e comincia a mandare i suoi
		 * messaggi di imalive una volta ogni secondo.
		 */
		while(true){
			String str = dev.getId() + " imalive";
			byte[] msg = str.getBytes();
			DatagramPacket packet=new DatagramPacket(msg, msg.length, addr, PORT_D);
			d_sock.send(packet);
			try {
				Thread.sleep(MS_WAIT);
			} catch (InterruptedException e){
				d_sock.close();
				e.printStackTrace();
			}
		}
	}
}
