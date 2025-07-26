import controller.Controller;
import view.MainFrame;
import model.model;

/**
 * Driver.java
 *
 * The entry point of the application. Initializes the model, view, and controller
 * to start the game.
 */
public class Driver {
  /**
     * Constructs a new Driver instance.
     * 
     * Note: No initialization logic is performed in the constructor; all setup
     * happens in the {@link #main(String[])} method.
     * 
     */
    public Driver(){
      
    }
    /**
     * The main method launches the application by creating the model, view, and controller.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
      // Initialize game model
    model model = new model();
    // Create and display the main application window
    MainFrame view = new MainFrame();

    // Wire up controller with model and view to handle user interactions
    new Controller(model, view);
  }
}
