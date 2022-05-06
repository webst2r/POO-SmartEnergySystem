package Model;

public class Request {
    private String type;
    private int nif;
    private String oldSupplier;
    private String newSupplier;


    public Request(int nif, String oldSupplier, String newSupplier){
        this.type = "CS"; // change supplier
        this.nif = nif;
        this.oldSupplier = oldSupplier;
        this.newSupplier = newSupplier;
    }

    public Request(Request request) {
        this.type = request.getType();
        this.nif = request.getNif();
        this.oldSupplier = request.getOldSupplier();
        this.newSupplier = request.getNewSupplier();
    }

    public String getType() { return this.type; }
    public String getOldSupplier() { return this.oldSupplier; }

    public int getNif() { return this.nif;}

    public String getNewSupplier() { return newSupplier;}

    public void setType(String type) { this.type = type; }
    public void setOldSupplier(String oldSupplier) { this.oldSupplier = oldSupplier; }
    public void setNewSupplier(String newSupplier) { this.newSupplier = newSupplier; }

    public Request clone(){
        return new Request(this);
    }
}
