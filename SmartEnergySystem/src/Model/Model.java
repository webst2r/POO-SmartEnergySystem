package Model;

import java.util.HashMap;
import java.util.Map;

public class Model {

    // Maps of devices/houses/suppliers on the system
    private Map<String, SmartDevice> devices;
    private Map<String, SmartHouse> houses;
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






}
