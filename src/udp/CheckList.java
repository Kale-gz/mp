package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe che implementa un thread, il cui obiettivo è gestire i devices connessi al server.
 * In dettaglio ogni 5 minuti il thread invia un messaggio Multicast a tutti i devices connessi
 * e ripulisce la lista dei thread da quelli non più attivi.
 * 
 * @author Giuseppe De Gregorio, Amedeo Fortino, Francesca Sabatino
 * @version 1.2
 *
 */
public class CheckList extends Thread{
	/** porta a cui inviare i datagram*/
	private int PORT;
	
	/** indirizzo IP */
	private InetAddress addr;
	
	/** socket utilizzata per inviare i datagram*/
	private MulticastSocket sock;
	
	/**	lista dei thread assegnati ai devices	*/
	private ArrayList<ThreadDevice> thDevList; 

	/**
	 * Costruttore del thread
	 * 
	 * @param sock multicast socket del server
	 * @param addr indirizzo ip
	 * @param thDevList	lista dei thread
	 */
	public CheckList(MulticastSocket sock, InetAddress addr, ArrayList<ThreadDevice> thDevList){
		this.sock=sock;
		this.addr = addr;
		this.thDevList = thDevList;
		PORT = 7777;
	}	
	
	/**
	 * Metodo run del thread che richiama il metodo checkAlive() e il metodo cleanList() ogni 5 minuti
	 */
	public void run(){
		long ms_wait=300000;
		while(true){
			try {
				checkAlive();
				cleanList();
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
		DatagramPacket packet = new DatagramPacket(msg,msg.length,addr,PORT);
		sock.send(packet);
		System.out.println("Ho inviato il pacchetto Multicast");
	}
	
	/**
	 * Metodo del thread che si occupa di ripulire la lista dai thread non più attivi.
	 */
	public synchronized void cleanList(){
		ThreadDevice th_tmp;
		Iterator<ThreadDevice> it;
		for(it = thDevList.iterator(); it.hasNext();){
			th_tmp = it.next();
			if(!th_tmp.isAlive()){
				it.remove();
			}
		}
	}
}
