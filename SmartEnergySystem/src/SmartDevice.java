public class SmartDevice {

    public enum Modo {
        ON,
        OFF
    }
    private Modo modo;
    private String id;

    public SmartDevice () {
        this.id= "";
        this.modo = Modo.OFF;
    }

    public SmartDevice (SmartDevice device) {
        this.setID(device.getID());
        this.modo = device.getModo();
    }

    public SmartDevice (String id, Modo modo) {
        this.id = id;
        this.modo = modo;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Modo getModo(){
        return this.modo;
    }

    public void setModo(Modo m) {
        this.modo = m;
    }

}
