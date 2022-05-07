package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model implements Serializable {
    private Map<String, SmartDevice> devices;
    private Map<Integer, SmartHouse> houses;
    private Map<String, Supplier> suppliers;
    private List<Request> requests;


    public Model(){
        this.devices = new HashMap<>();
        this.houses = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.requests = new ArrayList<>();
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


    /**
     * Adds a client (SmartHouse) to a supplier
     */
    public void addClient(String supplier, SmartHouse client){
        if(supplierExists(supplier)){
            this.suppliers.get(supplier).addClient(client);
        }

    }
    /**
     * Returns a list of clients of an Energy Supplier
     */
    public List<SmartHouse> getClients(String supplier){
        List<SmartHouse> clients = new ArrayList<>();
        if(supplierExists(supplier)){
            return this.suppliers.get(supplier).getListClients();
        }
        return clients;
    }

    /**
     * Returns a list of the house bills from a round of simulation
     * @param nif
     * @return
     */

    public List<Invoice> getInvoices(int nif){
        List<Invoice> invoices = new ArrayList<>();
        if(houseExists(nif)){
            invoices = this.houses.get(nif).getInvoices();
        }
        return invoices;
    }

    /**
     * Adds an invoice to the list of bills of a house
     * @param nif
     * @param invoice
     */

    public void addInvoiceToHouse(int nif, Invoice invoice){
        if(houseExists(nif)){
            this.houses.get(nif).addInvoice(invoice);
        }
    }

    /**
     * Sets a new energy supplier for a SmartHouse
     */
    public void changeSupplier(int ownerNif, Supplier newSupplier){
        SmartHouse client = this.houses.get(ownerNif);

        // Break old contract
        Supplier oldSupplier = getSupplier(client.getSupplier());
        removeClient(oldSupplier,client);

        // Sign new contract
        client.setSupplier(newSupplier.getSupplierID());
        newSupplier.addClient(client);
    }

    /**
     * Removes a client from supplier's list of clients
     */
    public void removeClient(Supplier supplier, SmartHouse client){
        if(supplierExists(supplier.getSupplierID()) && houseExists(client.getOwnerNIF()) )
            this.suppliers.get(supplier.getSupplierID()).removeClient(client);
    }

    /**
     * Get a house by NIF
     */

    public SmartHouse getHouse(int NIF){
        return this.houses.get(NIF).clone();
    }
    /**
     * Get a supplier by ID
     */
    public Supplier getSupplier(String supplier) {
        return this.suppliers.get(supplier).clone();
    }

    /**
     * Get a list of the all the devices in the system
     */

    public List<SmartDevice> getDevices(){
        List<SmartDevice> devs = new ArrayList<>();
        for(SmartDevice s : this.devices.values())
            devs.add(s.clone());

        return devs;
    }

    /**
     * Get a list of the rooms in a house identified by NIF
     */

    public List<String> getRooms(int nif){
        return this.houses.get(nif).getRoomList();
    }

    /**
     * Get a list of all the houses in the system
     */
    public List<SmartHouse> getHouses(){
        List<SmartHouse> smartHouses = new ArrayList<>();
        for(SmartHouse s : this.houses.values())
            smartHouses.add(s.clone());

        return smartHouses;
    }

    /**
     * Get a list of all the suppliers in the system
     */
    public List<Supplier> getSuppliers(){
        List<Supplier> sups = new ArrayList<>();
        for(Supplier s : this.suppliers.values())
            sups.add(s.clone());

        return sups;
    }

    /**
     * Get a list of all the supplier ID's in the system
     */
    public List<String> getSupplierNames(){
        List<String> availableSuppliers = new ArrayList<>();
        for(Supplier s : this.suppliers.values())
            availableSuppliers.add(s.getSupplierID());
        return availableSuppliers;
    }

    /**
     * Returns true if a Supplier exists.
     */

    public boolean supplierExists(String supplier){
        return this.suppliers.containsKey(supplier);
    }

    /**
     * Returns true if a house identified by NIF exists.
     */
    public boolean houseExists(int nif){
        return this.houses.containsKey(nif);
    }


    /**
     * Returns a list of all the existing requests in the system.
     */

    public List<Request> getRequests(){
        List<Request> requestList = new ArrayList<>();
        for(Request r : this.requests){
            requestList.add(r.clone());
        }
        return requestList;
    }

    /**
     * Adds a request to the list of requests
     */
    public void addRequest(Request request){
        this.requests.add(request.clone());
    }

    /**
     * Removes all the requests.
     */
    public void clearRequests(){
        this.requests.clear();
    }


    /**
     * Turn ON a list of devices as per requested by the user
     * @param nif
     * @param devs
     */
    public void TurnON (int nif, List<SmartDevice> devs) {
        if(houseExists(nif)){
            SmartHouse house = this.houses.get(nif);
            for(SmartDevice d : devs){
                house.turnOnDevice(d.getID());
            }
        }
    }

    /**
     * Turn OFF a list of devices as per requested by the user
     * @param nif
     * @param devs
     */
    public void TurnOFF (int nif, List<SmartDevice> devs, LocalDateTime timeStamp) {
        if(houseExists(nif)){
            SmartHouse house = this.houses.get(nif);
            for(SmartDevice d : devs){
                house.turnOffDevice(d.getID(),timeStamp);
            }
        }
    }


    /**
     * Get the devices in a room of a house
     * @param nif
     * @param room
     */
    public List<SmartDevice> getRoomDevices(int nif, String room) {
        List<SmartDevice> roomDevices = new ArrayList<>();
        SmartHouse house = this.houses.get(nif);
        if(house != null && house.roomExists(room)){
            roomDevices = this.houses.get(nif).getRoomDevices(room);
        }

        return roomDevices;
    }

    /**
     * Returns a map containing the devices turned off in each room
     * @param nif
     * @return
     */

    public Map<String, List<SmartDevice>> getDevicesTurnedOff(int nif) {
        SmartHouse house = this.houses.get(nif);
        Map<String, List<SmartDevice>> res = new HashMap<>();

        for(String r : house.getRoomsNDevices().keySet()){
            List<SmartDevice> offDevices = new ArrayList<>();
            for(SmartDevice d : house.getRoomDevices(r)){
                if(!d.getOn()){
                    offDevices.add(d.clone());
                }
            }
            res.put(r,offDevices);
        }
        return res;
    }

    public Map<String, List<SmartDevice>> getDevicesTurnedOn(int nif) {
        SmartHouse house = this.houses.get(nif);
        Map<String, List<SmartDevice>> res = new HashMap<>();

        for(String r : house.getRoomsNDevices().keySet()){
            List<SmartDevice> onDevices = new ArrayList<>();
            for(SmartDevice d : house.getRoomDevices(r)){
                if(d.getOn()){
                    onDevices.add(d.clone());
                }
            }
            res.put(r,onDevices);
        }
        return res;
    }
}
