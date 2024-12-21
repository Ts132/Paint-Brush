package drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class PaintingFrame extends JFrame {

    public PaintingFrame() {
        super("Paint Brush");

        DrawingPanel drawingPanel = new DrawingPanel();
        ControlPanel controlPanel = new ControlPanel(drawingPanel);

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }
}
