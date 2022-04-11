import java.util.*;

public class SmartHouse {
    private String ownerName;
    private String ownerNIF;

    private Set<SmartDevice> devices;
    private Set<String> rooms;
    private Map<String, Set<SmartDevice>> roomsNdevices;


    public SmartHouse(){
        this.ownerName = "n/a";
        this.ownerNIF = "n/a";
        this.devices = new HashSet<>();
        this.rooms = new HashSet<>();
        this.roomsNdevices = new HashMap<>();
    }

    public SmartHouse(String ownerName,
                      String ownerNIF,
                      Set<SmartDevice> devices,
                      Set<String> rooms,
                      Map<String, Set<SmartDevice>> roomsNdevices){
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

    public boolean deviceExists (String deviceID) {
        return this.devices.contains(deviceID);
    }

    public void addDevice (SmartDevice smartDevice) {
        this.devices.add(smartDevice.clone());
    }

    public SmartDevice getDevice (String deviceID) {
        for (SmartDevice sd : this.devices) {
            if (sd.getID().equals(deviceID)) return sd.clone();
        }
        return null;
    }

    public Set<SmartDevice> getDeviceList(){
        Set<SmartDevice> deviceList = new HashSet<>();
        this.devices.forEach(d -> deviceList.add(d.clone()));
        return deviceList;
    }

    public Set<String> getRoomList(){
        Set<String> roomList = new HashSet<>();
        this.rooms.forEach(room -> roomList.add(room));
        return roomList;
    }

    public Map<String,Set<SmartDevice>> getRoomsNDevices() {
        Map<String,Set<SmartDevice>> catalog = new HashMap<>();
        this.roomsNdevices.forEach((k,v) -> catalog.put(k,new HashSet<>(v)));
        return catalog;
    }

    public SmartHouse clone(){
        return new SmartHouse(this);
    }
}
