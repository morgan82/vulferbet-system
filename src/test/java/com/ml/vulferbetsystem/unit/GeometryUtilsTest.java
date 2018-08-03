package com.ml.vulferbetsystem.unit;

import com.ml.vulferbetsystem.domain.Point;
import com.ml.vulferbetsystem.utils.GeometryUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

public class GeometryUtilsTest {

    @Test
    public void calculatePendingStraightOK() throws Exception {

        boolean belong1 = GeometryUtils.isPointsBelongToStraight(
                Arrays.asList(new Point(0, 0),
                        new Point(1, 1),
                        new Point(2, 2),
                        new Point(-1, -1),
                        new Point(-6, -6),
                        new Point(4, 4)));
        //X constante
        boolean belong2 = GeometryUtils.isPointsBelongToStraight(
                Arrays.asList(new Point(3, 0),
                        new Point(3, 2),
                        new Point(3, 6),
                        new Point(3, 100)));
        //Y constante
        boolean belong3 = GeometryUtils.isPointsBelongToStraight(
                Arrays.asList(new Point(-3, 5),
                        new Point(1, 5),
                        new Point(6, 5),
                        new Point(9, 5)));
        then(belong1 && belong2 && belong3).isTrue();
    }
}
