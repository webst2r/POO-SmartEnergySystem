import java.time.LocalDateTime;

public class SmartDevice {
    private String id;
    private boolean on;
    private LocalDateTime timeOfTurningOn;

    public SmartDevice () {
        this.id= "";
        this.on = false;
        this.timeOfTurningOn = LocalDateTime.now();
    }

    public SmartDevice (String id, boolean on, LocalDateTime timeOfTurningOn ) {
        this.id = id;
        this.on = on;
        this.timeOfTurningOn = timeOfTurningOn;
    }

    public SmartDevice (SmartDevice device) {
        this.setID(device.getID());
        this.on = device.getOn();
        this.timeOfTurningOn = device.getTimeOfTurningOn();
    }

    public String getID() {
        return this.id;
    }

    public boolean getOn() { return this.on;}

    public LocalDateTime getTimeOfTurningOn() { return this.timeOfTurningOn; }

    public void setID(String id) {
        this.id = id;
    }

    public void turnOn(){
        this.timeOfTurningOn = LocalDateTime.now();
        this.on = true;
    }

    public boolean turnOff(){
        if(this.on == false) return true;

        if(LocalDateTime.now().isAfter(this.timeOfTurningOn)){
            if(LocalDateTime.now().getYear() == this.timeOfTurningOn.getYear() && LocalDateTime.now().getDayOfYear() == this.timeOfTurningOn.getDayOfYear())
                return false;
            else {
                this.on = false;
                return true;
            }
        }
        return false;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id)
                .append("\n")
                .append("ON: ").append(this.on)
                .append("\n");
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

    public SmartDevice clone () {
        return new SmartDevice(this);
    }
}
