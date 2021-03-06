package Graphs;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

import static Graphics.Game.*;

public class LightweightRect extends JComponent {
    public ArrayList<Circle> circles = new ArrayList<>();
    public ArrayList<Rectangle> rectangles = new ArrayList<>();
    private ArrayList<Point> way = new ArrayList<>();

    public static boolean travelTime = false;

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        fillingPath();

        drawGrid(graphics);

        drawCircle(graphics);

        drawRectangle(graphics);

        drawLine(graphics);

    }


    private void fillingPath() {
        Graph.matrix = new int[fieldSize / splittingX][(fieldSize-3*splittingY)  / splittingY ];

        for (int i = 0; i < fieldSize / splittingX; i++) {
            for (int j = 0; j <(fieldSize-3*splittingY)  / splittingY; j++) {
                Graph.matrix[i][j] = splittingY;
            }
        }

        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle rectangle = rectangles.get(i);

            if (rectangle.point.getX() + rectangle.w <= fieldSize && rectangle.point.getY() + rectangle.h <= fieldSize)
                infiniteSpace(rectangle.point, rectangle.w, rectangle.h);
        }

    }

    private void drawLine(Graphics graphics) {
        graphics.setColor(Color.RED);
        for (int j = 0; j < circles.size() - 1; j++) {
            way = Graph.FindPath(new Point(
                            (int) circles.get(j).point.getX() / splittingX
                            , (int) circles.get(j).point.getY() / splittingY),
                    new Point((int) circles.get(j + 1).point.getX() / splittingX,
                            (int) circles.get(j + 1).point.getY() / splittingY));


            System.out.println(way);
            if (way == null) {
                isNoWay = true;
                break;
            } else {
                boolean isLine = true;
                for (int i = 0; i < way.size(); i++) {
                    Point temp = way.get(i);

                    if (Graph.matrix[temp.getX() / splittingX][temp.getY() / splittingY] == Integer.MAX_VALUE) {
                        travelTime = false;
                        isLine = false;
                        break;
                    }
                }

                if (travelTime && isLine) {
                    Graphics2D g = (Graphics2D) graphics;
                    g.setStroke(new BasicStroke(2));

                    for (int i = 0; i < way.size() - 1; i++) {
                        graphics.drawLine(way.get(i).X * splittingX + radius, way.get(i).Y * splittingY + radius
                                , way.get(i + 1).X * splittingX + radius, way.get(i + 1).Y * splittingY + radius);
                    }

                }
            }
        }
    }


    private void drawRectangle(Graphics graphics) {
        graphics.setColor(Color.MAGENTA);

        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle rectangle = rectangles.get(i);

            graphics.fillRect((int) rectangle.point.getX(), (int) rectangle.point.getY(), rectangle.w, rectangle.h);
        }
    }

    private void drawCircle(Graphics graphics) {
        for (int i = 0; i < circles.size(); i++) {
            Circle circle1 = circles.get(i);
            graphics.setColor(Color.BLUE);

            graphics.fillOval(circle1.point.x, circle1.point.y, radius * 2, radius * 2);
        }
    }

    private void drawGrid(Graphics g) {
        int w = fieldSize;
        int h = fieldSize;

        int dw = splittingX;
        int dh = splittingY;

        g.setColor(Color.BLUE);

        for (int i = 1; i <= fieldSize / splittingX; i++) {
            int y1 = dh * i;
            int x1 = dw * i;

            int y2 = dh * i;
            int x2 = dw * i;

            if (y2 <= fieldSize && y1 <= fieldSize) {
                g.drawLine(0, y1, w, y2);
            }

            if (x1 <= fieldSize && x2 <= fieldSize)
                g.drawLine(x1, 0, x2, h);
        }

    }

    private void infiniteSpace(java.awt.Point p, int width, int height) {
        for (int i = (int) p.getX() - radius; i < p.getX() + width; i += splittingX) {
            for (int j = (int) p.getY() - radius; j < p.getY() + height; j += splittingY) {
                if (i >= 0 && j >= 0 && p.getX() / splittingX < Graph.matrix.length - 1 && p.getY() / splittingY < Graph.matrix[(int) (p.getY() / splittingY)].length - 1) {
                    Graph.matrix[i / splittingX][j / splittingY] = Integer.MAX_VALUE;
                }
            }
        }
    }

}

