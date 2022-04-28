package Model;

import java.time.LocalDateTime;

public class SmartBulb extends SmartDevice{
    private int tone;
    private double dimension;
    private double consumption; // consumo base
                                // no Menu, quando for a criar um SmartDevice, passar o consumo etc

    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;


    public SmartBulb () {
        super();
        this.tone = NEUTRAL;
        this.dimension = 0.0;
        this.consumption = 15.0;
    }

    public SmartBulb (String id,
                      boolean on,
                      LocalDateTime timeOfTurningOn,
                      int tone,
                      double dimension,
                      double consumption,
                      double installationCost) {
        super(id, on, timeOfTurningOn, installationCost);
        this.tone = tone;
        this.dimension = dimension;
        this.consumption = consumption;
    }

    public SmartBulb (SmartBulb smartBulb) {
        super(smartBulb.getID(), smartBulb.getOn(),smartBulb.getTimeOfTurningOn(), smartBulb.getInstallationCost());
        this.setTone(smartBulb.getTone());
        this.dimension = smartBulb.getDimension();
        this.consumption = smartBulb.getConsumption();
    }


    public int getTone() { return this.tone;}

    public double getDimension() { return this.dimension;}

    public double getConsumption() { return this.consumption;}

    public void setTone(int tone) { this.tone = tone;}

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double determineConsumption(){
        double multiplier = 1.0;

        switch (this.tone){
            case NEUTRAL:
                multiplier = 1.0;
                break;
            case COLD:
                multiplier = 1.25;
                break;
            case WARM:
                multiplier = 1.15;
                break;
        }
        return (consumption * multiplier);
    }

    public String toString(){ return super.toString(); }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return super.equals(smartBulb);
    }

    public SmartBulb clone () {
        return new SmartBulb(this);
    }

}
