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
        this.consumption = 0.0;
    }

    public SmartSpeaker (String id,
                         boolean on,
                         LocalDateTime timeOfTurningOn,
                         String channel,
                         String brand,
                         double volume) {
        super (id, on,timeOfTurningOn);
        this.channel = channel;
        this.brand = brand;
        setVolume(volume);
        determineConsumption();
    }

    public SmartSpeaker (SmartSpeaker smartSpeaker) {
        super (smartSpeaker.getID(),smartSpeaker.getOn(),smartSpeaker.getTimeOfTurningOn());
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
        double consumption = getBrandDailyConsumption(this.brand) * ( 1 + (this.volume / 100));
        setConsumption(consumption);
    }

    public String toString(){ return super.toString(); }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartSpeaker smartSpeaker = (SmartSpeaker) o;
        return super.equals(smartSpeaker);
    }

    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }
}
