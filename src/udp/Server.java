package udp;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	public static void main(String args[]) throws Exception
    {
		ArrayList<Device> devicesList = new ArrayList<Device>();
		ArrayList<ThreadDevice> tDevList = new ArrayList<ThreadDevice>();
		
		int port = 7778;
		int maxDimDatagram = 65507;
		DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[maxDimDatagram];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        String receiveMessage;		
        
        int portM = 7777;
        InetAddress addr = InetAddress.getByName("239.0.0.2");
        MulticastSocket serverMSocket = new MulticastSocket(portM);

        System.out.println("Il server è connesso.\n");
        
        // lancio del thread checkList
        CheckList threadCheckList = new CheckList(serverMSocket, addr);
        threadCheckList.start();
	   	 
         while(true){
        	 // Il server è in attesa di messaggi dai client 
        	 System.out.println("sono nel while");
        	 System.out.println(serverSocket.getLocalPort()+" "+serverSocket.getPort()+" "+serverSocket.getLocalAddress());
        	 serverSocket.receive(receivePacket);
             receiveMessage = new String(receivePacket.getData());
             
             System.out.println("RECEIVED: " + receiveMessage +"\n");
             
             String[] valuesReceived = receiveMessage.split("[ ]");
             int id = Integer.parseInt(valuesReceived[0]);
             String msg = valuesReceived[1];
             
             //test sul messaggio
             if(msg.equals("imalive")){
            	 //if is alive
            	 for(ThreadDevice td : tDevList){
            		 if(td.getMyId()== id)
            			 td.doRestart();
            	 }
             }else{
            	 // se il messagigo non è isalive
            	 // c'è un nuovo device che bisogna aggiungele alla lista

            	 // msg = MAC

            	 Device newDevice = new Device (id, msg);
            	 ThreadDevice newThread = new ThreadDevice(devicesList, id);
            	 
            	 devicesList.add(newDevice);
            	 tDevList.add(newThread);
            	 
            	 newThread.start();
            	 // il thread inizia a contare
             } 
         }
	}
}

