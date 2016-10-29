package udp;

import java.util.ArrayList;
/**
 * Classe che implementa un thread, il cui obiettivo è assicurarsi che un device sia 
 * effettivamente connesso al server.
 * In dettaglio se un devices non invia un pacchetto per più di 100 secondi, il thread 
 * si occupa di eliminarlo dalla lista dei devices connessi.
 * 
 * @author Giuseppe De Gregorio, Amedeo Fortino, Francesca Sabatino
 * @version 1.2
 *
 */
public class ThreadDevice extends Thread {
	/** Lista dei device connessi*/
	private ArrayList<Device> deviceList;
	
	/**ID del device*/
	private int id;
	
	/** Flag che mi permette di far ripartire il thread all'arrivo di un nuovo pacchetto "imalive" */
	boolean restart;

	/**
	 * Costruttore della classe
	 * 
	 * @param deviceList lista dei device
	 * @param id id del device
	 */
	public ThreadDevice(ArrayList<Device> deviceList, int id){
		this.deviceList=deviceList;
		this.id=id;
		restart=false;
	}
	
	/**
	 * Metodo per ottenere l'id del device
	 * @return id
	 */
	public int getMyId(){
		return id;
	}
	
	/**
	 * Metodo run del thread. Il thread conta 100 secondi, durante i quali:
	 * - se non riceve pacchetti dal device, tramite il flag restart, lo rimuove dalla lista. 
	 * - ogni volta che riceve pacchetti dal device, riparte il conteggio.
	 */
	public void run(){
		long ms_wait = 1000;
		int sec = 0;
		int max_sec = 100;
		Device ndev = new Device();

		while(sec<max_sec){
			try {
				sec++;
				if(restart){
					sec=0;
					restart=false;
					System.out.println("Ho resettato il thread del device "+id+" dalla lista.");
				}
				Thread.sleep(ms_wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (Device i : deviceList){
			if(i.getId()==id){
				ndev = i;
			}
		}
		deviceList.remove(ndev);
		System.out.println("Ho eliminato il device "+id+"  dalla lista.");
	}

	/**
	 * Metodo che setta il flag restart opportunamente al fine di poter eseguire il restart del thread
	 */
	public void doRestart(){
		restart = true;
	}
}


