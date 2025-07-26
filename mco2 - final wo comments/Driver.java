import controller.Controller;
import view.MainFrame;
import model.model;

public class Driver {
    public Driver(){
      
    }
    public static void main(String[] args) {
    model model = new model();
    MainFrame view = new MainFrame();

    new Controller(model, view);
  }
}
