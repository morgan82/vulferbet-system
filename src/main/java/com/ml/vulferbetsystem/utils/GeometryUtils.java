package com.ml.vulferbetsystem.utils;

import com.ml.vulferbetsystem.domain.Point;

import java.util.Arrays;
import java.util.List;

public class GeometryUtils {


    /**
     * Convierte una coordenada polar a cartesiana
     *
     * @param radius
     * @param angule
     * @return coordenada cartesiana
     */
    public static Point polarToCartesian(double radius, double angule) {
        double x = radius * Math.cos(angule);
        double y = radius * Math.sin(angule);

        return new Point(x, y);
    }

    /**
     * Determina si una serie de puntos pertenecen a una recta
     *
     * @param points lista de puntos
     * @return true si pertenecen todos a la misma recta
     */
    public static boolean isBelongToStraight(List<Point> points) {
        if (points.size() < 2) {
            throw new IllegalArgumentException("No es posible determinar una recta");
        }
        Point p1 = points.get(0);
        Point p2 = points.get(1);
        double m = getSlopeStraight(p1, p2);
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

    /**
     * Determina si una Recta pasa por el origen
     *
     * @param p1,p2 para determinar la ecuacion de la recta
     * @return true si pasa por el origen
     */
    public static boolean isStraightPassForOrigin(Point p1, Point p2) {
        return isBelongToStraight(Arrays.asList(new Point(0, 0), p1, p2));
    }

    /**
     * Determina si 3 puntos forman un triangulo
     *
     * @param p1
     * @param p2
     * @param p3
     * @return true si forman un triangulo
     */
    public static boolean isTriangle(Point p1, Point p2, Point p3) {
        return !isBelongToStraight(Arrays.asList(p1, p2, p3));
    }


    /**
     * Determina si el origen pertenece un triangulo
     *
     * @param p1
     * @param p2
     * @param p3
     * @return true si el origen pertenece a un triangulo
     */
    public static boolean originBelongToTriangle(Point p1, Point p2, Point p3) {
        if (!isTriangle(p1, p2, p3)) {
            throw new IllegalArgumentException("Los puntos no forman un triangulo");
        }
        Point pOrigin = new Point(0, 0);
        double p1P2P3_Orientation = getTriangleOrientation(p1, p2, p3);
        //P1 P2 P0rigin
        double p1P2POrigin_Orientation = getTriangleOrientation(p1, p2, pOrigin);
        //P2 P3 P0rigin
        double p2P3POrigin_Orientation = getTriangleOrientation(p2, p3, pOrigin);
        //P3 P1 P0rigin
        double p3P1POrigin_Orientation = getTriangleOrientation(p3, p1, pOrigin);
        double orientationSignWithOrigin = p1P2POrigin_Orientation * p2P3POrigin_Orientation * p3P1POrigin_Orientation;

        //el signo de la orientacion del triangulo debe ser igual que con el punto de origen
        return (p1P2P3_Orientation >= 0) ? orientationSignWithOrigin >= 0 : orientationSignWithOrigin < 0;
    }

    //private methods
    private static double getSlopeStraight(Point p1, Point p2) {

        double y = p2.getY() - p1.getY();
        double x = p2.getX() - p1.getX();
        return y / x;
    }

    private static double getTriangleOrientation(Point p1, Point p2, Point p3) {
        //(p1.x-p3.x)*(p2.y-p3.y)-(p1.y-p3.y)*(p2.x-p3.x)
        return ((p1.getX() - p3.getX()) * (p2.getY() - p3.getY())) - ((p1.getY() - p3.getY()) * (p2.getX() - p3.getX()));
    }
}
