package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model implements Serializable {

    // Maps of devices/houses/suppliers on the system
    private Map<String, SmartDevice> devices;
    private Map<Integer, SmartHouse> houses;
    private Map<String, Supplier> suppliers;


    public Model(){
        this.devices = new HashMap<>();
        this.houses = new HashMap<>();
        this.suppliers = new HashMap<>();
    }


    /**
     * Adds a SmartDevice to the system
     */
    public void add(SmartDevice s) {
        this.devices.put(s.getID(),s.clone());
    }

    /**
     * Adds a Supplier to the system
     */
    public void add(Supplier sup) {
        this.suppliers.put(sup.getSupplierID(),sup.clone());
    }


    /**
     * Adds a SmartHouse to the system
     */
    public void add(SmartHouse house) { this.houses.put(house.getOwnerNIF(),house.clone()); }


    public SmartHouse getHouse(int NIF){
        return this.houses.get(NIF);
    }

    public List<SmartDevice> getDevices(){
        List<SmartDevice> devs = new ArrayList<>();
        for(SmartDevice s : this.devices.values())
            devs.add(s.clone());

        return devs;
    }

    public List<SmartDevice> getHouseDevices(int nif){
        List<SmartDevice> devs = new ArrayList<>();
        return this.houses.get(nif).getDeviceList();
    }

    public boolean turnOffRoom(String room, int nif){
        return this.houses.get(nif).turnOffRoom(room);
    }

    public List<String> getRooms(int nif){
        return this.houses.get(nif).getRoomList();
    }


    public List<SmartHouse> getHouses(){
        List<SmartHouse> smartHouses = new ArrayList<>();
        for(SmartHouse s : this.houses.values())
            smartHouses.add(s.clone());

        return smartHouses;
    }

    public List<Supplier> getSuppliers(){
        List<Supplier> sups = new ArrayList<>();
        for(Supplier s : this.suppliers.values())
            sups.add(s.clone());

        return sups;
    }

    public List<String> getSupplierNames(){
        List<String> availableSuppliers = new ArrayList<>();
        for(Supplier s : this.suppliers.values())
            availableSuppliers.add(s.getSupplierID());
        return availableSuppliers;
    }

    public boolean supplierExists(String supplier){
        return this.suppliers.containsKey(supplier);
    }

    public boolean houseExists(int nif){
        return this.houses.containsKey(nif);
    }


}
