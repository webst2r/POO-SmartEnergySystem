package Model;

public class SmartCamera extends SmartDevice{
    private int resolutionX;
    private int resolutionY;
    private double fileSize;
    private double consumption;


    public SmartCamera(){
        this.resolutionX = 1024;
        this.resolutionY = 768;
        this.fileSize = 500;
        this.consumption = 15.0;
    }

    public SmartCamera(int resolutionX,
                       int resolutionY,
                       double fileSize,
                       double consumption) {
        super();
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.fileSize = fileSize;
        this.consumption = consumption;
    }

    public SmartCamera(SmartCamera smartCamera){
        super (smartCamera.getID(),smartCamera.getOn(),smartCamera.getTimeOfTurningOn(), smartCamera.getInstallationCost());
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

    public double determineConsumption(){
        return (this.fileSize / 1000.0) * ((this.resolutionY + this.resolutionY) / 1000.0);
    }

    public String toString(){ return super.toString(); }

    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        SmartCamera smartCamera = (SmartCamera) o;
        return super.equals(smartCamera);
    }

    public SmartCamera clone() {
        return new SmartCamera(this);
    }
}
