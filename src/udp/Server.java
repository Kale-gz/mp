package udp;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	public static void main(String args[]) throws Exception
    {
		ArrayList<Device> devicesList = new ArrayList<Device>();
		
		int port = 7778;
		int maxDimDatagram = 65507;
		DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[maxDimDatagram];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        String receiveMessage;		
        
        int portM = 7777;
        InetAddress addr = InetAddress.getByName("0.0.0.0");
        MulticastSocket serverMSocket = new MulticastSocket(portM);

        System.out.println("Il server è connesso.\n");
        
        // lancio del thread checkList
        CheckList threadCheckList = new CheckList(serverMSocket, addr);
        threadCheckList.start();
	   	 
         while(true){
        	 // Il server è in attesa di messaggi dai client        	 
        	 serverSocket.receive(receivePacket);
////////
             receiveMessage = new String(receivePacket.getData());
             System.out.println("RECEIVED: " + receiveMessage +"\n");
             
             //test sul messaggio
             if(receiveMessage.equals("imalive")){
            	 //if is alive
            	 // 
            	 //FAI QUELLO CHE DEVI FARE
             }else{
            	 // se il messagigo non è is alive
            	 // c'è un nuovo device che bisogna aggiungele alla lista
            	 String[] valuesReceived = receiveMessage.split("[ ]");
            	 int num = Integer.parseInt(valuesReceived[0]);
            	 int id = Integer.parseInt(valuesReceived[1]);
            	 String mac = valuesReceived[2];
            	 Device newDevice = new Device (id, num, mac);
            	 
            	 devicesList.add(newDevice);            	 
             } 
         }
	}
}

