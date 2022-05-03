import Controller.Controller;
import Model.Model;
import Utilities.LinhaIncorretaException;
import Utilities.Parser;
import View.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws LinhaIncorretaException, IOException, ClassNotFoundException {

        Model model = new Model();
        View view = new View();
        Controller c = new Controller(model,view);
        c.run();


        /*
        Model model = new Model();
        Parser p = new Parser();
        p.parse(model);

         */
    }
}