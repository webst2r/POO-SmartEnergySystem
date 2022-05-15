package Model;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private String type;
    private int nif;
    private double tax;
    private double baseValue;
    private String oldSupplier;
    private String newSupplier;
    private List<SmartDevice> devices;


    public Request(int nif, String oldSupplier, String newSupplier){
        this.type = "CS"; // change supplier
        this.nif = nif;
        this.tax = 0.0;
        this.baseValue = 0.0;
        this.oldSupplier = oldSupplier;
        this.newSupplier = newSupplier;
        this.devices = new ArrayList<>();
    }

    public Request(String supplier, double t, double v){
        this.type = "CSV"; // change supplier values
        this.nif = 0;
        this.tax = t;
        this.baseValue = v;
        this.oldSupplier = supplier;
        this.newSupplier = "";
        this.devices = new ArrayList<>();
    }

    public Request(String type, int nif, List<SmartDevice> devices){
        this.type = type; // TON  or TOFF
        this.nif = nif;
        this.tax = 0.0;
        this.baseValue = 0.0;
        this.devices = devices;
        this.newSupplier = "";
        this.oldSupplier = "";
    }

    public Request(Request request) {
        this.type = request.getType();
        this.nif = request.getNif();
        this.tax = request.getTax();
        this.baseValue = request.getBaseValue();
        this.oldSupplier = request.getOldSupplier();
        this.newSupplier = request.getNewSupplier();
        this.devices = request.getDevices();
    }

    public String getType() { return this.type; }
    public int getNif() { return this.nif;}
    public String getOldSupplier() { return this.oldSupplier; }
    public String getNewSupplier() { return newSupplier;}
    public double getTax() { return this.tax; }
    public double getBaseValue() { return this.baseValue; }

    public List<SmartDevice> getDevices() {
        List<SmartDevice> deviceList = new ArrayList<>();
        for(SmartDevice d : this.devices)
            deviceList.add(d.clone());
        return deviceList;
    }

    public Request clone(){
        return new Request(this);
    }
}
