import java.util.HashMap;
import java.util.Map;

public class Supplier {
    private String supplierID;
    private double energyDailyCost;

    private Map<String, SmartHouse> customers;   // ownerNIF -> Casas

    public Supplier(){
        this.supplierID = "n/a";
        this.energyDailyCost = 0.0;
        this.customers = new HashMap<>();
    }

    public Supplier(Supplier supplier){
        this.supplierID = supplier.getSupplierID();
        this.energyDailyCost = supplier.getEnergyDailyCost();
        this.customers = supplier.getCustomers();
    }

    public Supplier(String supplierID,
                    double energyDailyCost,
                    Map<String,SmartHouse> customers) {
        this.supplierID = supplierID;
        this.energyDailyCost = energyDailyCost;
        setCustomers(customers);
    }

    public String getSupplierID() { return this.supplierID; }

    public double getEnergyDailyCost() { return this.energyDailyCost; }

    public void setSupplierID(String supplierID) { this.supplierID = supplierID;}

    public void setEnergyDailyCost(double energyDailyCost) { this.energyDailyCost = energyDailyCost; }

    public void setCustomers(Map<String,SmartHouse> customers) {
        this.customers = new HashMap<String, SmartHouse>();
        customers.forEach((k,v) -> this.customers.put(k,v.clone()));
    }

    public Map<String,SmartHouse> getCustomers(){
        HashMap<String,SmartHouse> map = new HashMap<>();
        this.customers.forEach((k,v) -> map.put(k,v.clone()));
        return map;
    }

    public double determineDailyCost(SmartHouse house){
        double baseValue = 3.00;
        double tax = 0.15; // 15 % imposto
        double deviceConsumption = -1; //
        double dailyCostPerDevice = 0.0;
        int numberOfDevices = house.getNumberOfDevices();
        if( numberOfDevices > 10)
            dailyCostPerDevice = baseValue * deviceConsumption * (1+tax) * 0.9;
        else
            dailyCostPerDevice = baseValue * deviceConsumption * (1+tax) * 0.75;

        return dailyCostPerDevice * numberOfDevices;
    }


    public Supplier clone(){
        return new Supplier(this);
    }

}
