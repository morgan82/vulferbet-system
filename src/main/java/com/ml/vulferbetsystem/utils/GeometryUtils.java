package com.ml.vulferbetsystem.utils;

import com.ml.vulferbetsystem.domain.Point;

import java.util.List;

public class GeometryUtils {


    public static Point polarToCartesian(double radius, double angule) {
        double x = radius * Math.cos(angule);
        double y = radius * Math.sin(angule);

        return new Point(x, y);
    }

    public static boolean isPointsBelongToStraight(List<Point> points) throws Exception {
        if (points.size() < 2) {
            throw new Exception("No es posible determinar una recta");
        }
        Point p1 = points.get(0);
        Point p2 = points.get(1);
        double m = pendingStraight(p1, p2);
        if (Double.isFinite(m)) {
            Point pXY;
            for (int i = 2; i < points.size(); i++) {
                pXY = points.get(i);
                // Ecuacion de la recta -> y-y1 = m (x-x1)
                // si no cumple la ecuacion el punto no pertenece a la misma
                if (!(pXY.getY() - p1.getY() == m * (pXY.getX() - p1.getY()))) {
                    return false;
                }
            }
            return true;
        } else {//X constante
            return points.stream().allMatch(p -> p.getX() == p1.getX());
        }
    }

    //private
    private static double pendingStraight(Point p1, Point p2) {

        double y = p2.getY() - p1.getY();
        double x = p2.getX() - p1.getX();
        return y / x;
    }
}
