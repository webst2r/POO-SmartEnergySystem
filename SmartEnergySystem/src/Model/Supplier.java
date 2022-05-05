package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Supplier implements Serializable {
    private String supplierID;
    private double energyDailyCost;
    private Map<Integer, SmartHouse> clients;


    public Supplier(String supplierID) {
        this.supplierID = supplierID;
        this.energyDailyCost = 5.0;
        this.clients = new HashMap<>();
    }

    public Supplier(String supplierID, double energyDailyCost) {
        this.supplierID = supplierID;
        this.energyDailyCost = energyDailyCost;
        this.clients = new HashMap<>();
    }


    public Supplier(Supplier supplier){
        this.supplierID = supplier.getSupplierID();
        this.energyDailyCost = supplier.getEnergyDailyCost();
        this.clients = supplier.getClients();
    }


    public Supplier(String supplierID,
                    double energyDailyCost,
                    Map<Integer,SmartHouse> clients) {
        this.supplierID = supplierID;
        this.energyDailyCost = energyDailyCost;
        setClients(clients);
    }

    public String getSupplierID() { return this.supplierID; }

    public double getEnergyDailyCost() { return this.energyDailyCost; }

    public void setSupplierID(String supplierID) { this.supplierID = supplierID;}

    public void setEnergyDailyCost(double energyDailyCost) { this.energyDailyCost = energyDailyCost; }

    public void setClients(Map<Integer,SmartHouse> clients) {
        this.clients = new HashMap<Integer, SmartHouse>();
        clients.forEach((k,v) -> this.clients.put(k,v.clone()));
    }

    public Map<Integer,SmartHouse> getClients(){
        HashMap<Integer,SmartHouse> map = new HashMap<>();
        this.clients.forEach((k, v) -> map.put(k,v.clone()));
        return map;
    }

    public List<SmartHouse> getListClients(){
        List<SmartHouse> cli = new ArrayList<>();
        for(SmartHouse house : this.clients.values())
            cli.add(house.clone());
        return cli;
    }

    public void addClient(SmartHouse client){
        this.clients.put(client.getOwnerNIF(),client.clone());
    }

    public double determineCostPerDay(SmartHouse house){
        double consumption = house.getTotalDailyConsumption();
        double costPerDay = 0.0;
        double tax = 0.15; // 15 % imposto
        int numberOfDevices = house.getNumberOfDevices();

        if( numberOfDevices > 10)
            costPerDay = this.energyDailyCost * consumption * (1+tax) * 0.9;
        else
            costPerDay = this.energyDailyCost * consumption * (1+tax) * 0.75;

        // Se tiver mais de 10 devices paga mais
        // Custo diário de uma casa = valorBase + consumoTotalDiario + imposto * 0.9 (ou 0.75)
        return costPerDay;
    }


    public Supplier clone(){
        return new Supplier(this);
    }

}
