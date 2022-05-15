package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Model implements Serializable {
    private Map<String, SmartDevice> devices;
    private Map<Integer, SmartHouse> houses;
    private Map<String, Supplier> suppliers;
    private List<SmartDevice> freeDevices;
    private List<Request> requests;
    private List<LocalDateTime> dates; // contem as datas


    public Model(){
        this.devices = new HashMap<>();
        this.houses = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.freeDevices = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.dates = new ArrayList<>();
    }


    /**
     * Adds a SmartDevice to the system
     */
    public void add(SmartDevice s) {
        this.devices.put(s.getID(),s.clone());
    }

    /**
     * Adds a free SmartDevice to the system
     */
    public void addFreeDevice(SmartDevice s) {
        this.freeDevices.add(s.clone());
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
     * Returns a list with the names of the clients of an Energy Supplier
     */
    public List<String> getClientsNames(String supplier){
        List<SmartHouse> clients = new ArrayList<>();
        List<String> names = new ArrayList<>();
        if(supplierExists(supplier)){
            clients = this.suppliers.get(supplier).getListClients();
        }
        for(SmartHouse client : clients){
            names.add(client.getOwnerName());
        }
        return names;
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
     * Add an invoice to the list of invoices issued by a supplier
     * @param supplier
     * @param invoice
     */

    public void addInvoiceToSupplier(String supplier, Invoice invoice){
        if(supplierExists(supplier)){
            this.suppliers.get(supplier).addInvoice(invoice);
        }
    }

    /**
     * Get List of invoices issued by a supplier
     * @param supplier
     * @return
     */

    public List<Invoice> getInvoicesIssuedBySupplier(String supplier){
        List<Invoice> invoices = new ArrayList<>();
        if(supplierExists(supplier)){
            invoices = this.suppliers.get(supplier).getInvoices();
        }
        return invoices;
    }

    /**
     * Get the supplier with the most turn over
     * @return
     */

    public Supplier getSupplierMostTurnOver(){
        int maxInvoices = 0;
        Supplier bestSupplier = null;
        for(Supplier s : this.suppliers.values()){
            int n = s.getInvoices().size();
            if(n > maxInvoices){
                maxInvoices = n;
                bestSupplier = s.clone();
            }
        }
        return bestSupplier;
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
     * Get a map with the rooms and its devices
     */

    public Map<String,List<String>> getRoomsNDevices(int nif){
        Map<String,List<String>> res = new HashMap<>();

        if(houseExists(nif)){
            SmartHouse house = this.houses.get(nif);
            Map<String,List<SmartDevice>> rnd = house.getRoomsNDevices();

            for(String k : rnd.keySet()){
                List<SmartDevice> devs = getRoomDevices(nif,k);
                List<String> listDevNames = new ArrayList<>();
                for(SmartDevice d : devs){
                    StringBuilder sb = new StringBuilder();
                    if(d.getOn()){
                        sb.append(d.getClass().getSimpleName()).append(" state: ").append("\u001B[32m" + "ON" + "\u001B[0m");
                    } else {
                        sb.append(d.getClass().getSimpleName()).append(" state: ").append("\u001B[31m" + "OFF" + "\u001B[0m");
                    }
                    listDevNames.add(sb.toString());
                }
                res.put(k,listDevNames);
            }
        }
        return res;
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
     * Get a list of the all the devices that have not been integrated into a house
     */

    public List<SmartDevice> getFreeDevices(){
        List<SmartDevice> devs = new ArrayList<>();
        for(SmartDevice s : this.freeDevices)
            devs.add(s.clone());

        return devs;
    }

    /**
     * Removes a free Device
     */

    public void removeFreeDevice(int index){
        this.freeDevices.remove(index);
    }

    /**
     * Set rooms n devices for a house
     * @param nif
     */
    public void setRoomsNDevices(int nif,Map<String, List<SmartDevice>> rnd){
        if(houseExists(nif)){
            this.houses.get(nif).setRoomsNDevices(rnd);
        }
    }

    /**
     * Get a list of the all the dates the system has advanced from/to
     */

    public List<LocalDateTime> getDates(){
        List<LocalDateTime> rounds = new ArrayList<>();
        for(LocalDateTime r : this.dates){
            rounds.add(r);
        }

        return rounds;
    }

    /**
     * Adds a date to the list of dates the system has advanced from/to
     */

    public void addDate(LocalDateTime date){
        this.dates.add(date);
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

    public Map<String, List<String>> getTurnedOffDevicesNames(int nif) {
        Map<String, List<String>> res = new HashMap<>();
        if(houseExists(nif)){
            SmartHouse house = this.houses.get(nif);
            Map<String, List<SmartDevice>> rnd = house.getRoomsNDevices();


            for(String room : rnd.keySet()){
                List<String> offDevices = new ArrayList<>();
                for(SmartDevice d : house.getRoomDevices(room)){
                    if(!d.getOn()){
                        offDevices.add(d.getClass().getSimpleName());
                    }
                }
                res.put(room,offDevices);
            }
        }
        return res;
    }

    public List<SmartDevice> getTurnedOffDevices(int nif) {
        List<SmartDevice> offDevices = new ArrayList<>();
        if(houseExists(nif)){
            SmartHouse house = this.houses.get(nif);
            Map<String, List<SmartDevice>> rnd = house.getRoomsNDevices();

            for(String room : rnd.keySet()){
                for(SmartDevice d : house.getRoomDevices(room)){
                    if(!d.getOn()){
                        offDevices.add(d.clone());
                    }
                }
            }
        }
        return offDevices;
    }

    public Map<String, List<String>> getTurnedOnDevicesNames(int nif) {
        Map<String, List<String>> res = new HashMap<>();
        if(houseExists(nif)){
            SmartHouse house = this.houses.get(nif);
            Map<String, List<SmartDevice>> rnd = house.getRoomsNDevices();

            for(String room : rnd.keySet()){
                List<String> onDevices = new ArrayList<>();
                for(SmartDevice d : house.getRoomDevices(room)){
                    if(d.getOn()){
                        onDevices.add(d.getClass().getSimpleName());
                    }
                }
                res.put(room,onDevices);
            }
        }
        return res;
    }

    public List<SmartDevice> getTurnedOnDevices(int nif) {
        List<SmartDevice> onDevices = new ArrayList<>();
        if(houseExists(nif)){
            SmartHouse house = this.houses.get(nif);
            Map<String, List<SmartDevice>> rnd = house.getRoomsNDevices();

            for(String room : rnd.keySet()){
                for(SmartDevice d : house.getRoomDevices(room)){
                    if(d.getOn()){
                        onDevices.add(d.clone());
                    }
                }
            }
        }
        return onDevices;
    }


    public void changeSupplierValues(String supplierID, double tax, double baseValue){
        if(supplierExists(supplierID)){
            Supplier supplier = this.suppliers.get(supplierID);
            supplier.setTax(tax);
            supplier.setDailyCost(baseValue);
        }
    }

    /**
     * Returns an ordered list of the largest energy consumers during a period
     * @param start
     * @param end
     * @return
     */

    public List<String> getConsumersPeriod(LocalDateTime start, LocalDateTime end){
        List<String> consumers = new ArrayList<>();
        List<Invoice> allInvoices = new ArrayList<>();
        for(Supplier s : this.suppliers.values()){
            List<Invoice> invoices = s.getInvoicesByPeriod(start,end);
            for(Invoice i : invoices){
                allInvoices.add(i);
            }
        }
        Collections.sort(allInvoices, Comparator.comparingDouble((Invoice i) -> i.getConsumption()));
        Collections.sort(allInvoices,Collections.reverseOrder());

        for(Invoice i : allInvoices){
            StringBuilder sb = new StringBuilder();
            sb.append(i.getHouseOwner()).append(" ").append("(").append(i.getConsumption()).append(" kWh)");
            consumers.add(sb.toString());
        }

        return consumers;
    }
}
