package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Supplier implements Serializable {
    private String supplierID;
    private double dailyCost;
    private double tax;
    private Map<Integer, SmartHouse> clients;
    private List<Invoice> invoices;


    public Supplier(String supplierID) {
        this.supplierID = supplierID;
        this.dailyCost = 5.0;
        this.tax = 0.15;
        this.clients = new HashMap<>();
        this.invoices = new ArrayList<>();
    }

    public Supplier(String supplierID, double dailyCost, double tax) {
        this.supplierID = supplierID;
        this.dailyCost = dailyCost;
        this.tax = tax;
        this.clients = new HashMap<>();
        this.invoices = new ArrayList<>();
    }


    public Supplier(Supplier supplier){
        this.supplierID = supplier.getSupplierID();
        this.dailyCost = supplier.getDailyCost();
        this.tax = supplier.getTax();
        this.clients = supplier.getClients();
        this.invoices = supplier.getInvoices();
    }


    public String getSupplierID() { return this.supplierID; }

    public double getDailyCost() { return this.dailyCost; }

    public double getTax() { return this.tax;}

    public List<Invoice> getInvoices() {
        List<Invoice> inv = new ArrayList<>();
        for(Invoice i : this.invoices){
            inv.add(i.clone());
        }
        return inv;
    }

    public List<Invoice> getInvoicesByPeriod(LocalDateTime start, LocalDateTime end) {
        List<Invoice> inv = new ArrayList<>();
        for(Invoice i : this.invoices){
            if(i.getStart().equals(start) && i.getEnd().equals(end))
                inv.add(i.clone());
        }
        return inv;
    }

    public void setDailyCost(double dailyCost) { this.dailyCost = dailyCost; }

    public void setTax(double tax) { this.tax = tax;}

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

    public void addInvoice(Invoice invoice) {
        this.invoices.add(invoice.clone());
    }

    public void removeClient(SmartHouse client){
        this.clients.remove(client.getOwnerNIF());
    }

    public double determineCostPerDay(SmartHouse house){
        double consumption = house.getTotalDailyConsumption();
        double costPerDay = 0.0;
        int numberOfDevices = house.getNumberOfDevices();

        if(numberOfDevices < 10){
            costPerDay = this.dailyCost * consumption * (1+this.tax) * 0.1;
        }
        if( numberOfDevices > 10 && numberOfDevices <= 15)
            costPerDay = this.dailyCost * consumption * (1+this.tax) * 0.15;
        else
            costPerDay = this.dailyCost * consumption * (1+this.tax) * 0.35;


        return costPerDay;
    }


    public Supplier clone(){
        return new Supplier(this);
    }
}
