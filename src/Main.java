
import javax.swing.SwingUtilities;
import view.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
