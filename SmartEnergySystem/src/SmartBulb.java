import java.time.LocalDateTime;

public class SmartBulb extends SmartDevice{
    private int tone;
    private double dimension;
    private double consumption;

    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;


    public SmartBulb () {
        super();
        this.tone = NEUTRAL;
        this.dimension = 0.0;
        determineConsumption();
    }

    public SmartBulb (String id,
                      boolean on,
                      LocalDateTime timeOfTurningOn,
                      int tone,
                      double dimension) {
        super(id, on, timeOfTurningOn);
        this.tone = tone;
        this.dimension = dimension;
        determineConsumption();
    }

    public SmartBulb (SmartBulb smartBulb) {
        super(smartBulb.getID(), smartBulb.getOn(),smartBulb.getTimeOfTurningOn());
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

    void determineConsumption(){
        double kilowatts = 0.011;          // 11W
        double averageTimeOfUsage = 4.00; // 4h per day
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

        setConsumption(kilowatts * averageTimeOfUsage * multiplier);
    }

    public String toString(){ return super.toString(); }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return super.equals(smartBulb);
    }

    public SmartBulb clone () {
        return new SmartBulb(this);
    }

}
