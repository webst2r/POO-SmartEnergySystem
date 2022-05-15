package Model;

public class SmartBulb extends SmartDevice{
    private int tone;
    private int diameter;
    private double consumption;

    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;


    public SmartBulb () {
        super();
        this.tone = NEUTRAL;
        this.diameter = 3;
        this.consumption = 15.0;
    }

    public SmartBulb (int tone,
                      int diameter,
                      double consumption) {
        super();
        this.tone = tone;
        this.diameter = diameter;
        this.consumption = consumption;
    }

    public SmartBulb (SmartBulb smartBulb) {
        super(smartBulb.getID(), smartBulb.getOn(),smartBulb.getTimeOfTurningOn(), smartBulb.getInstallationCost());
        this.setTone(smartBulb.getTone());
        this.diameter = smartBulb.getDiameter();
        this.consumption = smartBulb.getConsumption();
    }


    public int getTone() { return this.tone;}

    public int getDiameter() { return this.diameter;}

    public double getConsumption() { return this.consumption;}

    public void setTone(int tone) { this.tone = tone;}

    public void setDiameter(int diameter) {
        this.diameter = diameter;
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
                multiplier = 1.11;
                break;
            case WARM:
                multiplier = 1.07;
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
