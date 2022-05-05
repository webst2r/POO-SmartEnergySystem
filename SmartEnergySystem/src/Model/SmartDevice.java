package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public abstract class SmartDevice implements Serializable {
    private String id;
    private boolean on;
    private LocalDateTime timeOfTurningOn;
    private double installationCost;

    public SmartDevice () {
        this.id = UUID.randomUUID().toString();
        Random random = new Random();
        this.on = random.nextBoolean();;
        this.timeOfTurningOn = LocalDateTime.now();
        this.installationCost = 4.99; // default installation cost
    }

    public SmartDevice (String id, boolean on, LocalDateTime timeOfTurningOn, double installationCost) {
        this.id = id;
        this.on = on;
        this.timeOfTurningOn = timeOfTurningOn;
        this.installationCost = installationCost;
    }

    public SmartDevice (SmartDevice device) {
        this.setID(device.getID());
        this.on = device.getOn();
        this.timeOfTurningOn = device.getTimeOfTurningOn();
        this.installationCost = device.getInstallationCost();
    }

    public String getID() {
        return this.id;
    }

    public boolean getOn() { return this.on;}

    public LocalDateTime getTimeOfTurningOn() { return this.timeOfTurningOn; }

    public double getInstallationCost() { return this.installationCost; }

    public void setID(String id) {
        this.id = id;
    }

    public void setInstallationCost(double installationCost) { this.installationCost = installationCost;}

    public void turnOn(){
        if(this.on == false){
            this.timeOfTurningOn = LocalDateTime.now();
            this.on = true;
        }
    }

    public boolean turnOff(){
        if(this.on == false) return true; // success (it was already turned off)

        if(LocalDateTime.now().isAfter(this.timeOfTurningOn)){
            if(LocalDateTime.now().getYear() == this.timeOfTurningOn.getYear() && LocalDateTime.now().getDayOfYear() == this.timeOfTurningOn.getDayOfYear())
                return false; // unsuccessful bc its the same day that it was turned on
            else {
                this.on = false;
                return true; // successfully turned it off
            }
        }
        return false;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("Device: {\n");
        sb.append("ID: ").append(this.id)
                .append("\n")
                .append("ON: ").append(this.on)
                .append("\n}\n");
        return sb.toString();
    }

    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        SmartDevice smartDevice = (SmartDevice) obj;
        return this.id.equals(smartDevice.getID())
                && this.on == smartDevice.getOn()
                && this.timeOfTurningOn.equals(smartDevice.getTimeOfTurningOn());
    }

    public abstract SmartDevice clone();

    public abstract double determineConsumption();
}
