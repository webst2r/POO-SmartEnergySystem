package Model;

import java.util.*;

public class SmartHouse {
    private String ownerName;
    private int ownerNIF;

    private List<SmartDevice> devices;
    private List<String> rooms;
    private Map<String, List<SmartDevice>> roomsNdevices;


    public SmartHouse(String ownerName, int ownerNIF){
        this.ownerName = ownerName;
        this.ownerNIF = ownerNIF;
        this.devices = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.roomsNdevices = new HashMap<>();
    }

    public SmartHouse(String ownerName,
                      int ownerNIF,
                      List<SmartDevice> devices,
                      List<String> rooms,
                      Map<String, List<SmartDevice>> roomsNdevices){
        this.ownerName = ownerName;
        this.ownerNIF = ownerNIF;
        this.devices = devices;
        this.rooms = rooms;
        this.roomsNdevices = roomsNdevices;
    }



    public SmartHouse(SmartHouse smarthouse){
        this.ownerName = smarthouse.getOwnerName();
        this.ownerNIF = smarthouse.getOwnerNIF();
        this.devices = smarthouse.getDeviceList();
        this.rooms = smarthouse.getRoomList();
        this.roomsNdevices = smarthouse.getRoomsNDevices();
    }

    public int getOwnerNIF() { return this.ownerNIF;}

    public String getOwnerName() { return this.ownerName;}

    public int getNumberOfDevices() { return this.devices.size();}

    public void setOwnerNIF(int ownerNIF) {
        this.ownerNIF = ownerNIF;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public boolean roomExists (String room) {
        return this.roomsNdevices.containsKey(room);
    }

    public boolean deviceExists (String deviceID) {
        return this.devices.contains(deviceID);
    }

    public void addDevice (SmartDevice smartDevice) {
        this.devices.add(smartDevice.clone());
    }

    public void addRoom (String room, List<SmartDevice> dispositivos) {
        List<SmartDevice> deviceList = new ArrayList<>();
        for(SmartDevice d : dispositivos)
            deviceList.add(d.clone());
        this.roomsNdevices.put(room,deviceList);
    }


    public SmartDevice getDevice (String deviceID) {
        for (SmartDevice smartDevice : this.devices) {
            if (smartDevice.getID().equals(deviceID)) return smartDevice.clone();
        }
        return null;
    }

    public List<SmartDevice> getDeviceList(){
        List<SmartDevice> deviceList = new ArrayList<>();
        this.devices.forEach(d -> deviceList.add(d.clone()));
        return deviceList;
    }

    public List<String> getRoomList(){
        List<String> roomList = new ArrayList<>();
        this.roomsNdevices.forEach((room,listDev) -> roomList.add(room));
        return roomList;
    }

    public Map<String,List<SmartDevice>> getRoomsNDevices() {
        Map<String,List<SmartDevice>> catalog = new HashMap<>();
        this.roomsNdevices.forEach((k,v) -> catalog.put(k,new ArrayList<>(v)));
        return catalog;
    }

    public boolean turnOffRoom(String room){
        boolean turnedOff = false;
        if(roomExists(room)){
            this.roomsNdevices.get(room).forEach(smartDevice -> smartDevice.turnOff());
            turnedOff = true;
        }
        return turnedOff;
    }

    public boolean turnOnRoom(String room){
        boolean turnedOn = false;
        if(roomExists(room)){
            this.roomsNdevices.get(room).forEach(smartDevice -> smartDevice.turnOn());
            turnedOn = true;
        }
        return turnedOn;
    }

    public boolean turnOnDevice(String deviceID){
        SmartDevice device = getDevice(deviceID);
        if(device != null){
            device.turnOn();
            return true;
        } else return false;
    }

    public boolean turnOffDevice(String deviceID){
        SmartDevice device = getDevice(deviceID);
        if(device != null){
            return device.turnOff();
        } else return false;
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
