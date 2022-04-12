import java.time.LocalDateTime;

public class SmartCamera extends SmartDevice{
    private int resolutionX;
    private int resolutionY;
    private double fileSize; // MBytes
    private double consumption;


    public SmartCamera(){
        this.resolutionX = 1024;
        this.resolutionY = 768;
        this.fileSize = 500;
        determineConsumption();
    }

    public SmartCamera(String id,
                       boolean on,
                       LocalDateTime timeOfTurningOn,
                       int resolutionX,
                       int resolutionY,
                       double fileSize) {
        super (id, on, timeOfTurningOn);
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.fileSize = fileSize;
        determineConsumption();
    }

    public SmartCamera(SmartCamera smartCamera){
        super (smartCamera.getID(),smartCamera.getOn(),smartCamera.getTimeOfTurningOn());
        this.resolutionX = smartCamera.getResolutionX();
        this.resolutionY = smartCamera.getResolutionY();
        this.fileSize = smartCamera.getFileSize();
        this.consumption = smartCamera.getConsumption();
    }

    public int getResolutionX() { return this.resolutionX;}

    public int getResolutionY() { return this.resolutionY;}

    public double getFileSize() { return this.fileSize; }

    public double getConsumption() { return this.consumption;}

    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
    }

    public void setResolutionY(int resolutionY) { this.resolutionY = resolutionY; }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public void determineConsumption(){
        double sum = (this.resolutionX + resolutionY) / 10000;
        double consumption = this.fileSize * (1 + sum);

        setConsumption(consumption);
    }

    public String toString(){ return super.toString(); }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartCamera smartCamera = (SmartCamera) o;
        return super.equals(smartCamera);
    }

    public SmartCamera clone() {
        return new SmartCamera(this);
    }
}
