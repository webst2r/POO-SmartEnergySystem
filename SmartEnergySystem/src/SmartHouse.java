import java.util.*;

public class SmartHouse {
    private String ownerName;
    private String ownerNIF;

    private List<SmartDevice> devices;
    private List<String> rooms;
    private Map<String, List<SmartDevice>> roomsNdevices;


    public SmartHouse(){
        this.ownerName = "n/a";
        this.ownerNIF = "n/a";
        this.devices = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.roomsNdevices = new HashMap<>();
    }

    public SmartHouse(String ownerName,
                      String ownerNIF,
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

    public String getOwnerNIF() { return this.ownerName;}

    public String getOwnerName() { return this.ownerName;}

    public void setOwnerNIF(String ownerNIF) {
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

    public void addRoom (String room) {
        this.rooms.add(room);
    }

    public void addDeviceToRoom (String room, String deviceID) {
        if (roomExists(room))
            this.roomsNdevices.get(room).add(getDevice(deviceID));
        else {
            List<SmartDevice> smartDevices = new ArrayList<>();
            smartDevices.add(getDevice(deviceID));
            this.roomsNdevices.put(room, smartDevices);
        }
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
        this.rooms.forEach(room -> roomList.add(room));
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



    public SmartHouse clone(){
        return new SmartHouse(this);
    }
}
