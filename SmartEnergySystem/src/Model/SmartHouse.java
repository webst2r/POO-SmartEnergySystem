package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class SmartHouse implements Serializable {
    private String ownerName;
    private String supplier;
    private int ownerNIF;
    private List<SmartDevice> devices;
    private List<String> rooms;
    private Map<String, List<SmartDevice>> roomsNdevices;
    private List<Invoice> invoices;

    public SmartHouse(String ownerName, int ownerNIF, String supplier){
        this.ownerName = ownerName;
        this.ownerNIF = ownerNIF;
        this.supplier = supplier;
        this.devices = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.roomsNdevices = new HashMap<>();
        this.invoices = new ArrayList<>();
    }

    public SmartHouse(SmartHouse smarthouse){
        this.ownerName = smarthouse.getOwnerName();
        this.ownerNIF = smarthouse.getOwnerNIF();
        this.supplier = smarthouse.getSupplier();
        this.devices = smarthouse.getDeviceList();
        this.rooms = smarthouse.getRoomList();
        this.roomsNdevices = smarthouse.getRoomsNDevices();
        this.invoices = smarthouse.getInvoices();
    }

    public int getOwnerNIF() { return this.ownerNIF;}

    public String getOwnerName() { return this.ownerName;}

    public String getSupplier() { return this.supplier; }


    public List<Invoice> getInvoices() {
        List<Invoice> invoiceList = new ArrayList<>();
        for(Invoice i : this.invoices){
            invoiceList.add(i.clone());
        }
        return this.invoices;
    }

    public void setOwnerNIF(int ownerNIF) {
        this.ownerNIF = ownerNIF;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setSupplier(String supplier) { this.supplier = supplier; }



    public boolean roomExists (String room) {
        return this.roomsNdevices.containsKey(room);
    }

    public List<SmartDevice> getRoomDevices(String room){
        List<SmartDevice> roomDevices = new ArrayList<>();
        for(SmartDevice d : this.roomsNdevices.get(room)){
            roomDevices.add(d.clone());
        }
        return roomDevices;
    }

    public List<SmartDevice> getRoomDevicesOFF(String room){
        List<SmartDevice> roomDevices = new ArrayList<>();
        for(SmartDevice d : this.roomsNdevices.get(room)){
            if(!d.getOn())
                roomDevices.add(d.clone());
        }
        return roomDevices;
    }

    public String getRoomOfDevice(SmartDevice device){
        String res = null;
        for(String room : this.roomsNdevices.keySet()){
            List<SmartDevice> roomDevices = this.roomsNdevices.get(room);
            for(SmartDevice d : roomDevices){
                if(d.getID().equals(device.getID())){
                    res = room;
                }
            }
        }
        return res;
    }

    public boolean deviceExists (String deviceID) {
        return this.devices.contains(deviceID);
    }

    public void addDevice (SmartDevice smartDevice) {
        this.devices.add(smartDevice.clone());
    }

    public void addInvoice(Invoice invoice){
        this.invoices.add(invoice.clone());
    }

    public void addRoom (String room, List<SmartDevice> dispositivos) {
        List<SmartDevice> deviceList = new ArrayList<>();
        for(SmartDevice d : dispositivos)
            deviceList.add(d.clone());
        this.roomsNdevices.put(room,deviceList);
    }


    public SmartDevice getDevice (String deviceID) {
        for(List<SmartDevice> room : this.roomsNdevices.values())
            for (SmartDevice smartDevice : room) {
                if (smartDevice.getID().equals(deviceID)) return smartDevice.clone();
            }
        return null;
    }

    public List<SmartDevice> getDeviceList(){
        List<SmartDevice> deviceList = new ArrayList<>();
        for(List<SmartDevice> room : this.roomsNdevices.values()){
            for(SmartDevice dev : room){
                deviceList.add(dev.clone());
            }
        }

        return deviceList;
    }

    public List<String> getRoomList(){
        List<String> roomList = new ArrayList<>();
        for(String room : this.roomsNdevices.keySet())
            roomList.add(room);
        return roomList;
    }

    public Map<String,List<SmartDevice>> getRoomsNDevices() {
        Map<String,List<SmartDevice>> catalog = new HashMap<>();
        this.roomsNdevices.forEach((k,v) -> catalog.put(k,new ArrayList<>(v)));
        return catalog;
    }

    public int getNumberOfDevices(){
        int number = 0;
        for(List<SmartDevice> room : this.roomsNdevices.values())
            number += room.size();

        return number;
    }

    public double getTotalDailyConsumption() {
        double consumption = 0.0;
        for (List<SmartDevice> room : this.roomsNdevices.values()) {
            for (SmartDevice d : room) {
                consumption += d.determineConsumption();
            }
        }
        return consumption;
    }


    public boolean turnOffRoom(String room, LocalDateTime timeStamp){
        boolean turnedOff = true;
        if(roomExists(room)){
            for(SmartDevice device : this.roomsNdevices.get(room))
                if(!device.turnOff(timeStamp)) turnedOff = false;
        }
        return turnedOff; // it is only true if all devices were successfully turned off
    }

    public boolean turnOnRoom(String room){
        boolean turnedOn = false;
        if(roomExists(room)){
            this.roomsNdevices.get(room).forEach(smartDevice -> smartDevice.turnOn());
            turnedOn = true;
        }
        return turnedOn;
    }

    public void turnOnDevice(String deviceID){
        for(List<SmartDevice> room : this.roomsNdevices.values()){
            for(SmartDevice d : room){
                if(d.getID().equals(deviceID)){
                    d.turnOn();
                    break;
                }
            }
        }
    }

    public void turnOffDevice(String deviceID, LocalDateTime timeStamp){
        for(List<SmartDevice> room : this.roomsNdevices.values()){
            for(SmartDevice d : room){
                if(d.getID().equals(deviceID)){
                    d.turnOff(timeStamp);
                    break;
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Owner: ").append(this.ownerName).append("\n")
                .append("NIF: ").append(this.ownerNIF).append("\n");
        return sb.toString();
    }

    public SmartHouse clone(){
        return new SmartHouse(this);
    }
}
