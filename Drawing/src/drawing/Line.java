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

public class Line extends Shape {

     public Line(int startX, int startY, int endX, int endY, Color color, Stroke stroke) {
        super(startX, startY, endX, endY, color, false, stroke);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setStroke(stroke);
        g2.drawLine(startX, startY, endX, endY);
    }
}