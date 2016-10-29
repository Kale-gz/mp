package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
/**
 * Classe che implementa un thread, il cui obiettivo è gestire la ricerca dei nuovi devices connessi al server.
 * In dettaglio ogni 5 minuti il thread invia un messaggio Multicast a tutti i devices connessi.
 * 
 * @author Giuseppe De Gregorio, Amedeo Fortino, Francesca Sabatino
 * @version 1.1
 *
 */
public class CheckList extends Thread{
	/** porta a cui inviare i datagram*/
	private int PORT;
	
	/** indirizzo IP */
	private InetAddress addr;
	
	/** socket utilizzata per inviare i datagram*/
	private MulticastSocket sock;

	/**
	 * Costruttore del thread
	 * 
	 * @param sock multicast socket del server
	 * @param addr indirizzo ip
	 */
	public CheckList(MulticastSocket sock, InetAddress addr){
		this.sock=sock;
		this.addr = addr;
		PORT = 7777;
	}	
	
	/**
	 * Metodo run del thread che richiama il metodo checkAlive() ogni 5 minuti
	 */
	public void run(){
		long ms_wait=300000;
		while(true){
			try {
				checkAlive();
				Thread.sleep(ms_wait);
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * Metodo del thread che si occupa di inviare un messaggio multicast ai devices connessi
	 */
	public void checkAlive() throws IOException{
		byte[] msg = {'i','s','a','l','i','v','e'};
		DatagramPacket packet=new DatagramPacket(msg,msg.length,addr,PORT);
		sock.send(packet);
		System.out.println("Ho inviato il pacchetto Multicast");
	}
}
