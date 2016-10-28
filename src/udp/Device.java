package udp;
public class Device {
    private String mac;
    private int id;
    private int num;
    private boolean isalive;
     
    public Device (int id, int num, String mac){
        this.id = id;
        this.mac = mac;
        this.num = num;
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
         
    public int getNum() {
        return num;
    }
 
    public void setNum(int num) {
        this.num = num;
    }
 
    @Override
    public String toString() {
        return "Device [mac=" + mac + ", id=" + id + "]";
    }
}