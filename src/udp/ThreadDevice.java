package udp;

import java.util.ArrayList;

public class ThreadDevice extends Thread {
	private ArrayList<Device> deviceList;
	private int id;
	 
	
	public ThreadDevice(ArrayList<Device> deviceList, int id){
		this.deviceList=deviceList;
		this.id=id;
	}
	
	public int getMyId(){
		return id;
	}
	
	public void run(){
		int ms_wait=5000;
		while(true){
			try {
				wait(ms_wait);
				for (Device i : deviceList){
					if(i.getId()==id){
						deviceList.remove(i);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
