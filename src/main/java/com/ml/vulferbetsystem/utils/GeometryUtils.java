package com.ml.vulferbetsystem.utils;

import com.ml.vulferbetsystem.domain.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class GeometryUtils {
    private static final Logger log = LoggerFactory.getLogger(GeometryUtils.class);
    private static final int PRECISION = ConfigUtils.GEOMETRY_UTILS_PRECISION;

    /**
     * Convierte una coordenada polar a cartesiana
     *
     * @param radius
     * @param angule in degrees
     * @return coordenada cartesiana
     */
    public static Point getCartesianCoordinatesFromPolar(double radius, double angule) {
        double x = round(radius * Math.cos(Math.toRadians(angule)), 10);
        double y = round(radius * Math.sin(Math.toRadians(angule)), 10);

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
                double ySide = pXY.getY() - p1.getY();
                double xSide = m * (pXY.getX() - p1.getX());
                double equationResult = ySide - xSide;

                if (Math.abs(equationResult) > PRECISION) {
                    return false;
                }
            }
            return true;
        } else {//X constante
            return points.stream().allMatch(p -> Math.abs(p.getX() - p1.getX()) <= PRECISION);
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
     * Determina si el origen pertenece a un triangulo
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
        //el signo de la orientacion del triangulo debe ser igual que con el punto de origen
        if (p1P2P3_Orientation >= 0) {
            return p1P2POrigin_Orientation >= 0 && p2P3POrigin_Orientation >= 0 && p3P1POrigin_Orientation >= 0;
        } else {
            return p1P2POrigin_Orientation < 0 && p2P3POrigin_Orientation < 0 && p3P1POrigin_Orientation < 0;
        }
    }

    /**
     * Determina un angulo, dado una angulo inicial una velocidad angular
     * y un periodo de tiempo
     *
     * @param angularVelocity
     * @param timePeriod
     * @return angulo calculado valores entre 0 y 359
     */
    public static int getAnguleByVelocityAndTimes(int initialPosition, int angularVelocity, int timePeriod) {
        if (angularVelocity > 0) {
            return (initialPosition + (timePeriod * angularVelocity)) % 360;
        } else if (angularVelocity < 0) {
            int aux = initialPosition + (angularVelocity * timePeriod);
            return aux < 0 ? (aux % 360) + 360 : aux;
        } else {
            return 0;
        }
    }

    /**
     * Redondea un numero a una candidad de decimales determinada
     *
     * @param number
     * @param decimals
     * @return numero redondeado
     */
    public static double round(double number, double decimals) {
        return Math.round(number * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    /**
     * Determina el perimetro de un triangulo
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double getPerimeterFromTriangle(Point a, Point b, Point c) {
        if (!isTriangle(a, b, c)) {
            throw new IllegalArgumentException("Los puntos no forman un triangulo");
        }
        double distanceAB = distanceBetween2Point(a, b);
        double distanceBC = distanceBetween2Point(b, c);
        double distanceAC = distanceBetween2Point(a, c);

        return distanceAB + distanceBC + distanceAC;
    }

    //private methods
    private static double distanceBetween2Point(Point a, Point b) {
        return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
    }

    private static double getSlopeStraight(Point p1, Point p2) {

        double y = p2.getY() - p1.getY();
        double x = p2.getX() - p1.getX();
        if (Math.abs(x) <= PRECISION) {
            return (x >= 0) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        } else if (Math.abs(y) <= PRECISION) {
            return 0;
        } else {
            return y / x;
        }
    }

    private static double getTriangleOrientation(Point p1, Point p2, Point p3) {
        //(p1.x-p3.x)*(p2.y-p3.y)-(p1.y-p3.y)*(p2.x-p3.x)
        return ((p1.getX() - p3.getX()) * (p2.getY() - p3.getY())) - ((p1.getY() - p3.getY()) * (p2.getX() - p3.getX()));
    }
}
