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

abstract class Shape {
 
    protected int startX, startY, endX, endY;
    protected Color color;
    protected boolean fill;
    protected Stroke stroke;

    public Shape(int startX, int startY, int endX, int endY, Color color, boolean fill, Stroke stroke) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.fill = fill;
        this.stroke = stroke;
    }

    public abstract void draw(Graphics2D g2);

}
