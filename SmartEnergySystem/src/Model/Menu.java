package Model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String title;
    private List<String> options;

    public Menu(){
        this.title = "NaN";
        this.options = new ArrayList<>();
    }

    public void setTitle(String titulo){
        this.title = titulo;
    }

    public void addOption(String opcao){
        this.options.add(opcao);
    }

    public void removeOption(String opcao){
        this.options.remove(opcao);
    }

    /**
     * Show Menu Options.
     */
    public void show(boolean cursorVisible){
        System.out.println("\u001B[36m" + title + "\u001B[0m");
        System.out.println("-----------------------------");
        int i = 1;
        for (String string : this.options) {
            System.out.println("(" + i + "): " + string);
            i++;
        }
        if(cursorVisible) System.out.print("> ");
    }

    /**
     * Show Menu Options.
     */
    public void showMainMenu(){
        System.out.println("\u001B[34m");
        System.out.println("╔═╗┌┬┐┌─┐┬─┐┌┬┐╔═╗┌┐┌┌─┐┬─┐┌─┐┬ ┬");
        System.out.println("╚═╗│││├─┤├┬┘ │ ║╣ │││├┤ ├┬┘│ ┬└┬┘");
        System.out.println("╚═╝┴ ┴┴ ┴┴└─ ┴ ╚═╝┘└┘└─┘┴└─└─┘ ┴");
        System.out.println("╔═╗┬ ┬┌─┐┌┬┐┌─┐┌┬┐");
        System.out.println("╚═╗└┬┘└─┐ │ ├┤ │││");
        System.out.println("╚═╝ ┴ └─┘ ┴ └─┘┴ ┴");
        System.out.println("\u001B[0m");

        int i = 1;
        for (String string : this.options) {
            System.out.println("(" + i + "): " + string);
            i++;
        }
    }
}
