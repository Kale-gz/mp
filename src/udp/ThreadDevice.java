package udp;

import java.util.ArrayList;

public class ThreadDevice extends Thread {
	private ArrayList<Device> deviceList;
	private int id;
	boolean restart;
	 
	
	public ThreadDevice(ArrayList<Device> deviceList, int id){
		this.deviceList=deviceList;
		this.id=id;
		restart=false;
	}
	public int getMyId(){
		return id;
	}
	public void run(){
		long ms_wait=1000;
		int sec = 0;
		int max_sec = 30;
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
				// TODO Auto-generated catch block
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

	public void doRestart(){
		restart = true;
	}
}


