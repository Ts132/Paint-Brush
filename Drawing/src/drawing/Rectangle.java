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

public class Rectangle extends Shape {

       public Rectangle (int startX, int startY, int endX, int endY, Color color, boolean fill, Stroke stroke) {
        super(startX, startY, endX, endY, color, fill, stroke);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setStroke(stroke);
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);
        if (fill) {
            g2.fillRect(x, y, width, height);
        } else {
            g2.drawRect(x, y, width, height);
        }
    }
}