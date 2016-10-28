package udp;
public class Device {
    private String mac;
    private int id;
    private boolean isalive;
     
    public Device (int id, int num, String mac){
        this.id = id;
        this.mac = mac;
    }
 
    public boolean isIsalive() {
        return isalive;
    }
 
    public void setIsalive(boolean isalive) {
        this.isalive = isalive;
    }
 
    public String getMac() {
        return mac;
    }
 
    public void setMac(String mac) {
        this.mac = mac;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
         
}