package udp;
import java.net.*;
import javax.swing.JOptionPane;
import java.io.*;

/**
 * Classe contenente tutti i metodi per la gestione di un Client dell'architettura.
 * 
 * @author Giuseppe De Gregorio, Amedeo Fortino, Francesca Sabatino
 * @version 1.2.2
 */
public class Client {
	public static void main(String args[]) throws IOException{
		int id_device = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID del device"));
		String mac_device = JOptionPane.showInputDialog("Inserisci il MAC Address del device: ");
		String server_addr = "localhost";
		String mult_addr = "239.0.0.2";
		int PORT_M = 7777;
		int PORT_D = 7778;
		int MAX = 65507;
		long MS_WAIT = 1000;

		// Istanzio un device sul client
		Device dev = new Device(id_device,mac_device);
		
		// Resto in attesa del messaggio in Multicast per l'attivazione
		MulticastSocket sock = new MulticastSocket(PORT_M);
		InetAddress m_addr = InetAddress.getByName(mult_addr);
		sock.joinGroup(m_addr);
		System.out.println("Client connesso");
		byte [] mess = new byte[MAX];
		DatagramPacket pack = new DatagramPacket(mess, mess.length);
		sock.receive(pack);
		String rec_data = new String(pack.getData());
		System.out.println("Ho ricevuto il pacchetto Multicast: " + rec_data);
		sock.close();
		
		/*
		 * Una volta che il messaggio è stato ricevuto la socket Multicast
		 * viene chiusa e viene aperta una socket Datagram per rispondere
		 * inviando il suo id ed il suo MAC address.
		 */
		DatagramSocket d_sock = new DatagramSocket();
		String response = dev.getId() + " " + dev.getMac();
		byte [] res = response.getBytes("UTF-8");
		InetAddress addr = InetAddress.getByName(server_addr);
		DatagramPacket res_pack = new DatagramPacket(res, res.length, addr, PORT_D);
		d_sock.send(res_pack);
		System.out.println("Ho inviato la risposta al Multicast.");
		
		/*
		 * A questo punto il Client è operativo e comincia a mandare i suoi
		 * messaggi di imalive una volta ogni secondo.
		 */
		while(true){	
			String str = dev.getId() + " imalive ";
			byte[] msg = str.getBytes();
			DatagramPacket packet = new DatagramPacket(msg, msg.length, addr, PORT_D);
			d_sock.send(packet);
			System.out.println("Ho inviato il pacchetto imalive.");
			try {
				Thread.sleep(MS_WAIT);
			} catch (InterruptedException e){
				d_sock.close();
				e.printStackTrace();
			}
		}
	}
}
