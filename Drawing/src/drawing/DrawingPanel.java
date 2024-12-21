/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

class DrawingPanel extends JPanel {

    private final List<Shape> shapes = new ArrayList<>();
    private final List<Shape> redoStack = new ArrayList<>();
    private String currentMode = "Pencil"; // Default to Pencil
    private Color currentColor = Color.BLACK;
    private boolean isDotted = false;
    private boolean fillShape = false; // Whether shapes are filled or outlined
    private Stroke currentStroke = new BasicStroke(2);
    private Shape currentShape;

    public DrawingPanel() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            
                Stroke stroke = isDotted ? new BasicStroke(
                        ((BasicStroke) currentStroke).getLineWidth(),
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND,
                        0,
                        new float[]{5, 5},
                        0) : currentStroke;
                switch (currentMode) {
                    case "Pencil":
                        currentShape = new Pencil(currentColor, currentStroke);
                        break;
                    case "Eraser":
                        currentShape = new Pencil(getBackground(), currentStroke);
                        break;
                case "Brush":
                        currentShape = new Brush( currentColor,currentStroke);
                        break;

                    default:
                        currentShape = createShape(e.getX(), e.getY(), e.getX(), e.getY());
                }
                if (currentShape instanceof Pencil) {
                    ((Pencil) currentShape).addPoint(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShape != null) {
                    if (!(currentShape instanceof Pencil || currentShape instanceof Eraser)) {
                        shapes.add(currentShape);
                    } else if (currentShape instanceof Pencil) {
                        shapes.add(currentShape); // Add Pencil drawing to shapes list
                    } else if (currentShape instanceof Eraser) {
                        shapes.add(currentShape); // Add Eraser drawing to shapes list
                    }
                }
                currentShape = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentShape instanceof Pencil) {
                    ((Pencil) currentShape).addPoint(e.getX(), e.getY());
                } else if (currentShape instanceof Eraser) {
                    ((Eraser) currentShape).addPoint(e.getX(), e.getY());
                } else if (currentShape != null) {
                    currentShape.endX = e.getX();
                    currentShape.endY = e.getY();
                }
                repaint();
            }
        });
    }

    public void setCurrentMode(String mode) {
        this.currentMode = mode;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setFillShape(boolean fill) {
        this.fillShape = fill;
    }

    public void setCurrentStroke(Stroke stroke) {
        this.currentStroke = stroke;
    }

    public void setCurrentStroke(int thickness) {
        this.currentStroke = new BasicStroke(thickness);
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            redoStack.add(shapes.remove(shapes.size() - 1));
            repaint();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            shapes.add(redoStack.remove(redoStack.size() - 1));
            repaint();
        }
    }

    public void clear() {
        shapes.clear();
        redoStack.clear();
        repaint();
    }

    private Shape createShape(int x1, int y1, int x2, int y2) {
        switch (currentMode) {
            case "Line":
                return new Line(x1, y1, x2, y2, currentColor, currentStroke);
            case "Rectangle":
                return new Rectangle(x1, y1, x2, y2, currentColor, fillShape, currentStroke);
            case "Oval":
                return new Oval(x1, y1, x2, y2, currentColor, fillShape, currentStroke);
            default:
                return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Shape shape : shapes) {
            shape.draw(g2);
        }
        if (currentShape != null) {
            currentShape.draw(g2);
        }
    }
}

// Control panel for user actions
class ControlPanel extends JPanel {

    public ControlPanel(DrawingPanel drawingPanel) {
        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(e -> drawingPanel.setCurrentMode("Line"));

        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(e -> drawingPanel.setCurrentMode("Rectangle"));

        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(e -> drawingPanel.setCurrentMode("Oval"));

        JButton pencilButton = new JButton("Pencil");
        pencilButton.addActionListener(e -> drawingPanel.setCurrentMode("Pencil"));
         JButton brushButton = new JButton("Brush");
        brushButton.addActionListener(e -> drawingPanel.setCurrentMode("Brush"));
        JButton eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(e -> drawingPanel.setCurrentMode("Eraser"));

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> drawingPanel.undo());

        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> drawingPanel.redo());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> drawingPanel.clear());

        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Select a Color", Color.BLACK);
            if (color != null) {
                drawingPanel.setCurrentColor(color);
            }
        });

        JSlider sizeSlider = new JSlider(1, 20, 2);
        sizeSlider.addChangeListener(e -> drawingPanel.setCurrentStroke(sizeSlider.getValue()));

        JRadioButton solidButton = new JRadioButton("Solid");
        solidButton.addActionListener(e -> {
            drawingPanel.setFillShape(true); // تعيين ملء الشكل
            drawingPanel.setCurrentStroke(new BasicStroke(1)); // تعيين خط عادي
        });

        JRadioButton outlinedButton = new JRadioButton("Outlined");
        outlinedButton.addActionListener(e -> {
            drawingPanel.setFillShape(false); // تعيين الشكل كحدود فقط
            drawingPanel.setCurrentStroke(new BasicStroke(1)); // تعيين خط عادي
        });

        JRadioButton dottedButton = new JRadioButton("Dotted");
        dottedButton.addActionListener(e -> {
            drawingPanel.setFillShape(false); // تعيين الشكل كحدود فقط
            drawingPanel.setCurrentStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); // تعيين خط منقط
        });
// Create a ButtonGroup and add buttons
        ButtonGroup fillGroup = new ButtonGroup();
        fillGroup.add(solidButton);
        fillGroup.add(outlinedButton);
        fillGroup.add(dottedButton);

        add(lineButton);
        add(rectangleButton);
        add(ovalButton);
        add(pencilButton);
         add(brushButton);
        add(eraserButton);
        add(undoButton);
        add(redoButton);
        add(clearButton);
        add(colorButton);
        add(solidButton);
        add(outlinedButton);
        add(dottedButton);
        add(new JLabel("Size:"));

        add(sizeSlider);

    }

}
