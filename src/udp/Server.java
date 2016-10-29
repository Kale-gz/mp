package udp;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Classe che gestisce il Server
 * 
 * @author Giuseppe De Gregorio, Amedeo Fortino, Francesca Sabatino
 * @version 1.2
 */
public class Server {
	public static void main(String args[]){
		ArrayList<Device> devicesList = new ArrayList<Device>();
		ArrayList<ThreadDevice> tDevList = new ArrayList<ThreadDevice>();
		boolean connected = false;

		//Socket per pacchetti in multicast
		try {
			int portM = 7777;
			InetAddress addr = InetAddress.getByName("239.0.0.2");
			MulticastSocket serverMSocket = new MulticastSocket(portM);

			System.out.println("Il server è connesso.");
			
			/*			
			 * una volta connesso il server inizia a interrogare la rete 
			 * per vedere se ci sono client collegati
			 * Per farlo viene lanciato il thread threadCheckList
			 * che invia i messaggi di 'isalive' in broadcast
			 */
			CheckList threadCheckList = new CheckList(serverMSocket, addr);
			threadCheckList.start();
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Errore nell'avviare la Multicast socket");
		}catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Errore indirizzo Multicast");
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("Errore di I/O Multicast socket");
		}

		// Socket per ricevere pacchetti
		try {
			int port = 7778;
			int maxDimDatagram = 65507;
			DatagramSocket serverSocket;
			serverSocket = new DatagramSocket(port);
			byte[] receiveData = new byte[maxDimDatagram];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, 
																receiveData.length);
			String receiveMessage;	

			/*
			 * A questo punto il server si mette in attesa di pacchetti  a lui 
			 * indirizzati i pacchetti possono essere di due tipi:
			 *	- messaggio di 'imalive' da parte di un client gia connesso
			 *	- messaggio da parte di un nuovo client
			 */
			while(true){
				try {
					serverSocket.receive(receivePacket);

					receiveMessage = new String(receivePacket.getData());

					String[] valuesReceived = receiveMessage.split("[ ]");
					int id = Integer.parseInt(valuesReceived[0]);
					String msg = valuesReceived[1];
					System.out.println("Sono connessi "+devicesList.size()+" devices");

					if(msg.equals("imalive")){
						for(ThreadDevice td : tDevList){
							if(td.getMyId()== id)
								td.doRestart();
						}
					}else{
						// msg = MAC_del_device
						Device newDevice = new Device (id, msg);
						ThreadDevice newThread = new ThreadDevice(devicesList, id);
						
						/*
						 * bisogna ora aggiungere il nuovo device alla lista 
						 * controllando prima che non ce ne sia uno con lo stesso 
						 * ID gia presente.
						 */
						for (Device i : devicesList){
							if(i.getId()==id){
								System.out.println("Device già connesso.");
								connected=true;
							}
						}
						if(!connected){
							devicesList.add(newDevice);
							tDevList.add(newThread);
							System.out.println("Nuovo devices connesso. id:"+ 
																newDevice.getId());
							newThread.start();
							connected=false;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Errore di I/O socket");
					serverSocket.close();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Errore nell'avviare la socket");
		}
	}
}

