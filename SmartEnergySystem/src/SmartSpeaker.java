import java.time.LocalDateTime;

public class SmartSpeaker extends SmartDevice{
    private String channel;
    private String brand;
    private double volume;
    private double consumption;


    public SmartSpeaker () {
        super();
        this.channel = "n/a";
        this.brand = "n/a";
        this.volume = 0.0;
        this.consumption = 0.0;
    }

    public SmartSpeaker (String id,
                         boolean on,
                         LocalDateTime timeOfTurningOn,
                         String canal,
                         String marca,
                         double volume) {
        super (id, on,timeOfTurningOn);
        this.channel = canal;
        this.brand = marca;
        setVolume(volume);
    }

    public SmartSpeaker (SmartSpeaker smartSpeaker) {
        super (smartSpeaker.getID(),smartSpeaker.getOn(),smartSpeaker.getTimeOfTurningOn());
        this.channel = smartSpeaker.getChannel();
        this.brand = smartSpeaker.getBrand();
        this.volume = smartSpeaker.getVolume();
    }

    public String getChannel() { return this.channel;}

    public String getBrand() { return this.brand;}

    public double getVolume() { return this.volume;}


    public void setChannel(String channel) { this.channel = channel;}

    public void setBrand(String brand) { this.brand = brand; }

    public void setVolume(double volume) { this.volume = volume;}

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
