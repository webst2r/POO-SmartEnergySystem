import java.time.LocalDateTime;

public class SmartCamera extends SmartDevice{
    private String resolution;
    private double fileSize;
    private double consumption;


    public SmartCamera(){
        this.resolution = "";
        this.fileSize = 0.0;
        this.consumption = 0.0;
    }

    public SmartCamera(String id,
                       boolean on,
                       LocalDateTime timeOfTurningOn,
                       String resolution,
                       double fileSize,
                       double consumption) {
        super (id, on, timeOfTurningOn);
        this.resolution = resolution;
        this.fileSize = fileSize;
        this.consumption = consumption;
    }

    public SmartCamera(SmartCamera smartCamera){
        super (smartCamera.getID(),smartCamera.getOn(),smartCamera.getTimeOfTurningOn());
        this.resolution = smartCamera.getResolution();
        this.fileSize = smartCamera.getFileSize();
        this.consumption = smartCamera.getConsumption();
    }

    public String getResolution() { return this.resolution;}

    public double getFileSize() { return this.fileSize; }

    public double getConsumption() { return this.consumption;}

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
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
