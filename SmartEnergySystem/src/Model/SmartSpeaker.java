package Model;

import java.time.LocalDateTime;
import java.util.Map;

public class SmartSpeaker extends SmartDevice{
    private String channel;
    private String brand;
    private double volume;
    private double consumption;
    private Map<String, Double> brandsNConsumption;


    public SmartSpeaker () {
        super();
        this.channel = "n/a";
        this.brand = "n/a";
        this.volume = 15.0;
        this.consumption = 15.0;
    }

    public SmartSpeaker (String channel,
                         String brand,
                         double volume,
                         double consumption) {
        super();
        this.channel = channel;
        this.brand = brand;
        setVolume(volume);
        this.consumption = consumption;
    }

    public SmartSpeaker (SmartSpeaker smartSpeaker) {
        super (smartSpeaker.getID(),smartSpeaker.getOn(),smartSpeaker.getTimeOfTurningOn(), smartSpeaker.getInstallationCost());
        this.channel = smartSpeaker.getChannel();
        this.brand = smartSpeaker.getBrand();
        this.volume = smartSpeaker.getVolume();
        this.consumption = smartSpeaker.getConsumption();
    }

    public String getChannel() { return this.channel;}

    public String getBrand() { return this.brand;}

    public double getVolume() { return this.volume;}

    public double getConsumption() { return this.consumption; }


    public void setChannel(String channel) { this.channel = channel;}

    public void setBrand(String brand) { this.brand = brand; }

    public void setConsumption(double consumption) { this.consumption = consumption; }

    public void setVolume(double volume) { this.volume = volume;}

    public double getBrandDailyConsumption(String brand) {
        return this.brandsNConsumption.get(brand);
    }

    public void determineConsumption() {
        // ...
    }

    public String toString(){ return super.toString(); }

    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        SmartSpeaker smartSpeaker = (SmartSpeaker) o;
        return super.equals(smartSpeaker);
    }

    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }
}
