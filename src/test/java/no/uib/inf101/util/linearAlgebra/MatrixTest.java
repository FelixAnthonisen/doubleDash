package no.uib.inf101.util.linearAlgebra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatrixTest {

        @Test
        public void testTranspose() {
                Matrix m = new Matrix(new double[][] {
                                { 1, 2, 3 },
                                { 4, 5, 6 },
                                { 7, 8, 9 }
                }), expected = new Matrix(
                                new double[][] {
                                                { 1, 4, 7 },
                                                { 2, 5, 8 },
                                                { 3, 6, 9 }
                                });

                m = m.transpose();

                assertEquals(m, expected);
        }

        @Test
        public void testMultiply() {
                Matrix m1 = new Matrix(new double[][] {
                                { 1, 2, -1 },
                                { 2, 1, 3 }
                }),
                                m2 = new Matrix(new double[][] {
                                                { 1, 2 },
                                                { -5, 0 },
                                                { 3, 1 }
                                }),
                                expected = new Matrix(new double[][] {
                                                { -12, 1 },
                                                { 6, 7 }
                                });
                Matrix res = m1.multiply(m2);

                assertEquals(expected, res);
        }

        @Test
        public void testDeterminant() {
                Matrix m = new Matrix(new double[][] {
                                { 1, 3, 2 },
                                { 4, 7, -11 },
                                { 0, -3, 9 },
                });

                assertEquals(-102, m.determinant());
        }
}