package View;

public class View {

    public void showln(Object arg){
        System.out.println(arg);
    }
    public void showln(String[] args){
        for(int i = 0; i < args.length; i++)
            System.out.println(args[i]);
    }
    public void prompt(String name, String module){
        System.out.print("["+ name +"@" + module + "]$ ");
    }


}
