package no.uib.inf101.util.linearAlgebra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VectorTest {
        @Test
        public void testDot() {
                Vector v1 = new Vector(new double[] { 2, 7, 1 }),
                                v2 = new Vector(new double[] { 8, 2, 8 });
                double expected = 38;

                assertEquals(expected, v1.dot(v2));
                assertEquals(expected, v2.dot(v1));
        }

        @Test
        public void testCross() {
                Vector v1 = new Vector(new double[] { 2, 3, 4 }),
                                v2 = new Vector(new double[] { 5, 6, 7 });
                Vector expected = new Vector(new double[] { -3, 6, -3 });

                assertEquals(expected, v1.cross(v2));
        }

        @Test
        public void testMatMul() {
                Vector v = new Vector(new double[] { 3, -1, -2 }),
                                expected = new Vector(new double[] { 1, -15, 8 });
                Matrix m = new Matrix(new double[][] {
                                { 5, 4, 5 },
                                { -5, -10, 5 },
                                { 5, 7, 0 } });
                assertEquals(expected, v.matMul(m));

        }

        @Test
        public void testMatMul2() {
                Vector v = new Vector(new double[] { 1, 1, 1, 1 }),
                                expected = new Vector(new double[] { 2, -3, 6, 1 });
                Matrix m = new Matrix(new double[][] {
                                { 1, 0, 0, 1 },
                                { 0, 1, 0, -4 },
                                { 0, 0, 1, 5 },
                                { 0, 0, 0, 1 }
                });
                assertEquals(expected, v.matMul(m));

        }

}
