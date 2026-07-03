import com.uaspbo.view.TiketPanel;
import javax.swing.*;

public class TestTiketPanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test - Master Tiket");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 550);
            frame.setLocationRelativeTo(null);
            frame.add(new TiketPanel());
            frame.setVisible(true);
        });
    }
}