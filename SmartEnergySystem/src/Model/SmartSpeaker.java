package Model;

public class SmartSpeaker extends SmartDevice{
    private String radio;
    private String brand;
    private double volume;
    private double consumption;

    public SmartSpeaker () {
        super();
        this.radio = "n/a";
        this.brand = "n/a";
        this.volume = 15.0;
        this.consumption = 15.0;
    }

    public SmartSpeaker (String channel,
                         String brand,
                         double volume,
                         double consumption) {
        super();
        this.radio = channel;
        this.brand = brand;
        setVolume(volume);
        this.consumption = consumption;
    }

    public SmartSpeaker (SmartSpeaker smartSpeaker) {
        super (smartSpeaker.getID(),smartSpeaker.getOn(),smartSpeaker.getTimeOfTurningOn(), smartSpeaker.getInstallationCost());
        this.radio = smartSpeaker.getChannel();
        this.brand = smartSpeaker.getBrand();
        this.volume = smartSpeaker.getVolume();
        this.consumption = smartSpeaker.getConsumption();
    }

    public String getChannel() { return this.radio;}

    public String getBrand() { return this.brand;}

    public double getVolume() { return this.volume;}

    public double getConsumption() { return this.consumption; }


    public void setChannel(String channel) { this.radio = channel;}

    public void setBrand(String brand) { this.brand = brand; }

    public void setConsumption(double consumption) { this.consumption = consumption; }

    public void setVolume(double volume) { this.volume = volume;}


    public double determineConsumption() {
        return 1.0;
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
