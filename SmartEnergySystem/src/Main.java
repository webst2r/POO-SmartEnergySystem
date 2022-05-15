import Controller.Controller;
import Model.Model;
import Exceptions.IncorrectLineException;
import View.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IncorrectLineException, IOException, ClassNotFoundException {

        Model model = new Model();
        View view = new View();
        Controller c = new Controller(model,view);
        c.run();
    }
}