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
		int ms_wait=1000;
		int sec = 0;
		int max_sec = 300;
		
		while(sec<max_sec){
			try {
				sec++;
				if(restart){
					sec=0;
					restart=false;
				}
				wait(ms_wait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Device i : deviceList){
					if(i.getId()==id){
						deviceList.remove(i);
					}
				}
	}
	
	public void doRestart(){
		restart = true;
	}
}


