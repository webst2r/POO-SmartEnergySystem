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
    private List<Request> requests; // Change supplier / Turn ON-OFF


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

    public List<Invoice> getInvoices(int nif){
        List<Invoice> invoices = new ArrayList<>();
        if(houseExists(nif)){
            invoices = this.houses.get(nif).getInvoices();
        }
        return invoices;
    }

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

    public int numberOfClients(String supplierID){
        return this.suppliers.get(supplierID).getListClients().size();
    }

    /**
     * remove a client from supplier's list of clients
     */

    public void removeClient(Supplier supplier, SmartHouse client){
        if(supplierExists(supplier.getSupplierID()) && houseExists(client.getOwnerNIF()) )
            this.suppliers.get(supplier.getSupplierID()).removeClient(client);
    }


    public SmartHouse getHouse(int NIF){
        return this.houses.get(NIF);
    }

    public Supplier getSupplier(String supplier) { return this.suppliers.get(supplier); }

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


    public List<Request> getRequests(){
        List<Request> requestList = new ArrayList<>();
        for(Request r : this.requests){
            requestList.add(r.clone());
        }
        return requestList;
    }

    public void addRequest(Request req){
        this.requests.add(req.clone());
    }

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
    public void TurnOFF (int nif, List<SmartDevice> devs) {
        if(houseExists(nif)){
            SmartHouse house = this.houses.get(nif);
            for(SmartDevice d : devs){
                house.turnOffDevice(d.getID());
            }
        }
    }



    public List<SmartDevice> getRoomDevices(int nif, String room) {
        List<SmartDevice> roomDevices = new ArrayList<>();
        SmartHouse house = this.houses.get(nif);
        if(house != null && house.roomExists(room)){
            roomDevices = this.houses.get(nif).getRoomDevices(room);
        }

        return roomDevices;
    }


}
