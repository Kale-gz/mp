package udp;

/**
 * Classe contenente tutti i metodi necessari alla gestione di un Device.
 * 
 * @author Giuseppe De Gregorio, Amedeo Fortino, Francesca Sabatino
 * @version 1.0.0
 *
 */
public class Device {
	/** MAC address del device	*/
	private String mac;
	
	/** ID del device	*/
	private int id;

	/**
	 * Costruttore del Device.
	 * 
	 * @param id	Id del device
	 * @param mac	MAC address del device
	 */
	public Device (int id, String mac){
		this.id = id;
		this.mac = mac;
	}
	public Device (){
		
	}

	/**
	 * Restituisce il MAC address del device
	 * 
	 * @return	MAC address del device
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * Imposta il MAC address del device
	 * 
	 * @param mac	mac address da impostare
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * Restituisce l'ID del device
	 * 
	 * @return	ID del device
	 */
	public int getId() {
		return id;
	}

	/**
	 * Imposta l'ID del device
	 * 
	 * @param id	ID del device da impostare
	 */
	public void setId(int id) {
		this.id = id;
	}

}