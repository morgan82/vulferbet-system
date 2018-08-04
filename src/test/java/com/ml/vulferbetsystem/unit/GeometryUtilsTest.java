package com.ml.vulferbetsystem.unit;

import com.ml.vulferbetsystem.domain.Point;
import com.ml.vulferbetsystem.utils.ConfigUtils;
import com.ml.vulferbetsystem.utils.GeometryUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

public class GeometryUtilsTest {

    @Test
    public void calculatePendingStraightOK() throws Exception {

        boolean belong1 = GeometryUtils.isBelongToStraight(
                Arrays.asList(new Point(0, 0),
                        new Point(1, 1),
                        new Point(2, 2),
                        new Point(-1, -1),
                        new Point(-6, -6),
                        new Point(4, 4)));
        //X constante
        boolean belong2 = GeometryUtils.isBelongToStraight(
                Arrays.asList(new Point(3, 0),
                        new Point(3, 2),
                        new Point(3, 6),
                        new Point(3, 100)));
        //Y constante
        boolean belong3 = GeometryUtils.isBelongToStraight(
                Arrays.asList(new Point(-3, 5),
                        new Point(1, 5),
                        new Point(6, 5),
                        new Point(9, 5)));
        then(belong1 && belong2 && belong3).isTrue();
    }

    @Test
    public void isTrianguleOK(){
        Point p1 = new Point(1,1);
        Point p2 = new Point(1,-1);
        Point p3 = new Point(ConfigUtils.GEOMETRY_UTILS_PRECISION+3,ConfigUtils.GEOMETRY_UTILS_PRECISION+0);
        then(GeometryUtils.isTriangle(p1, p2, p3)).isTrue();
    }
    @Test
    public void isTrianguleNoOK(){
        //recta X constante
        Point p1 = new Point(0,1);
        Point p2 = new Point(0,-1);
        Point p3 = new Point(0,-3);
        then(GeometryUtils.isTriangle(p1, p2, p3)).isFalse();

        //recta Y constante
        p1 = new Point(-1, 0);
        p2 = new Point(2, 0);
        p3 = new Point(5, 0);
        then(GeometryUtils.isTriangle(p1, p2, p3)).isFalse();

        //recta pendiente 1
        p1 = new Point(1, 1);
        p2 = new Point(2, 2);
        p3 = new Point(-3, -3);
        then(GeometryUtils.isTriangle(p1, p2, p3)).isFalse();
    }
    @Test
    public void getAnguleByVelocityAndTimesOK(){
        int angule;
        angule = GeometryUtils.getAnguleByVelocityAndTimes(0, 1, 1);
        then(angule == 1).isTrue();

        angule = GeometryUtils.getAnguleByVelocityAndTimes(360, 1, 1);
        then(angule == 1).isTrue();

        angule = GeometryUtils.getAnguleByVelocityAndTimes(180, 1, 180);
        then(angule == 0).isTrue();

        angule = GeometryUtils.getAnguleByVelocityAndTimes(180, 1, 179);
        then(angule == 359).isTrue();

        angule = GeometryUtils.getAnguleByVelocityAndTimes(180, 2, 90);
        then(angule == 0).isTrue();

        angule = GeometryUtils.getAnguleByVelocityAndTimes(180, -2, 90);
        then(angule == 0).isTrue();

        angule = GeometryUtils.getAnguleByVelocityAndTimes(90, -1, (2*360));
        then(angule == 90).isTrue();

        angule = GeometryUtils.getAnguleByVelocityAndTimes(90, -1, 45);
        then(angule == 45).isTrue();

    }
    @Test
    public void roundOK() {
        double num = GeometryUtils.round(10.66, 1);
        then(num).isEqualTo(10.7);
        num = GeometryUtils.round(0.0000000005, 5);
        then(num).isEqualTo(0);
        num = GeometryUtils.round(0.0000000005, 9);
        then(num).isEqualTo(0.000000001);
        num = GeometryUtils.round(5.0E-155, 154);
        then(num).isEqualTo(1.0E-154);
    }
    @Test
    public void getCartesianCoordinatesFromPolarOK(){
        Point point = GeometryUtils.getCartesianCoordinatesFromPolar(1, 90);
        then(point.getX()).isEqualTo(0);
        then(point.getY()).isEqualTo(1);

        point = GeometryUtils.getCartesianCoordinatesFromPolar(1, 180);
        then(point.getX()).isEqualTo(-1);
        then(point.getY()).isEqualTo(0);

        point = GeometryUtils.getCartesianCoordinatesFromPolar(1, 270);
        then(point.getX()).isEqualTo(0);
        then(point.getY()).isEqualTo(-1);

        point = GeometryUtils.getCartesianCoordinatesFromPolar(1, 0);
        then(point.getX()).isEqualTo(1);
        then(point.getY()).isEqualTo(0);


        point = GeometryUtils.getCartesianCoordinatesFromPolar(1, 45);
        then(point.getX()).isEqualTo(0.7071067812);
        then(point.getY()).isEqualTo(point.getX());

    }

}
