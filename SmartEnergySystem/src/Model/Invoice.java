package Model;

import java.time.LocalDateTime;

public class Invoice {
    private LocalDateTime start;
    private LocalDateTime end;
    private int NIF;
    private String houseOwner;
    private String supplier;
    private double consumption;
    private double cost;

    public Invoice(LocalDateTime start,
                   LocalDateTime end,
                   int NIF,
                   String houseOwner,
                   String supplier,
                   double consumption,
                   double cost){
        this.start = start;
        this.end = end;
        this.NIF = NIF;
        this.houseOwner = houseOwner;
        this.supplier = supplier;
        this.consumption = consumption;
        this.cost = cost;
    }

    public Invoice (Invoice invoice) {
        this.start = invoice.getStart();
        this.end = invoice.getEnd();
        this.NIF = invoice.getNIF();
        this.houseOwner = invoice.getHouseOwner();
        this.supplier = invoice.getSupplier();
        this.consumption = invoice.getConsumption();
        this.cost = invoice.getCost();
    }

    public LocalDateTime getStart() { return start; }

    public LocalDateTime getEnd() { return end; }

    public int getNIF() { return NIF; }

    public String getHouseOwner() { return houseOwner;}

    public String getSupplier() { return supplier; }

    public double getConsumption() { return consumption; }

    public double getCost() { return cost; }


    public void setStart(LocalDateTime start) { this.start = start; }

    public void setEnd(LocalDateTime end) { this.end = end; }

    public void setConsumption(double consumption) { this.consumption = consumption; }

    public void setCost(double cost) { this.cost = cost; }

    public void setNIF(int NIF) { this.NIF = NIF; }

    public Invoice clone(){
        return new Invoice(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("—————————————————————————— " + "\uD83E\uDDFE" + supplier + " Invoice "+ "——————————————————————————").append("\n");
        sb.append(" House owner: ").append(this.houseOwner).append("\n");
        sb.append(" NIF:: ").append(this.NIF).append("\n");
        sb.append(" Consumption: ").append(this.consumption).append("\n");
        sb.append(" Cost: ").append(this.cost).append("€\n");
        sb.append(" \u001B[31m" + "Billing period: " + "\u001B[0m").append(start.toString()).append(" to ").append(end.toString()).append("\n");
        return sb.toString();
    }
}
