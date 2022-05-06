package Model;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private String type;
    private int nif;
    private String oldSupplier;
    private String newSupplier;
    private List<SmartDevice> devices;


    public Request(int nif, String oldSupplier, String newSupplier){
        this.type = "CS"; // change supplier
        this.nif = nif;
        this.oldSupplier = oldSupplier;
        this.newSupplier = newSupplier;
        this.devices = new ArrayList<>();
    }

    // Request to Turn ON or Turn OFF some devices
    public Request(String type, int nif, List<SmartDevice> devices){
        this.type = type; // TON  or TOFF
        this.nif = nif;
        this.devices = devices;
        this.newSupplier = "";
        this.oldSupplier = "";
    }

    public Request(Request request) {
        this.type = request.getType();
        this.nif = request.getNif();
        this.oldSupplier = request.getOldSupplier();
        this.newSupplier = request.getNewSupplier();
        this.devices = request.getDevices();
    }

    public String getType() { return this.type; }
    public int getNif() { return this.nif;}
    public String getOldSupplier() { return this.oldSupplier; }
    public String getNewSupplier() { return newSupplier;}

    public List<SmartDevice> getDevices() {
        List<SmartDevice> deviceList = new ArrayList<>();
        for(SmartDevice d : this.devices)
            deviceList.add(d);
        return deviceList;
    }

    public Request clone(){
        return new Request(this);
    }
}
