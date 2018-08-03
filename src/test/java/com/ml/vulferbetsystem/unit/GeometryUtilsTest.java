package com.ml.vulferbetsystem.unit;

import com.ml.vulferbetsystem.domain.Point;
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
        Point p3 = new Point(3,0);
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
}
