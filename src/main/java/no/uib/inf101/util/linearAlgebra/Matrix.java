package no.uib.inf101.util.linearAlgebra;

import java.util.Arrays;

import no.uib.inf101.util.Pair;

public class Matrix {
    private final int rows, cols;
    private final double[][] arr;

    /**
     * Call to construct a matrix from a 2d double array directly mapping rows and
     * cols from the array to the matrix. The array must at least be of dimensions
     * 1x1 and all columns must be of same length
     * 
     * @param arr the array the matrix should be constructed from
     * @throws IllegalArgumentException if arr doesn't fit the dimension
     *                                  requirements mentioned
     */
    public Matrix(double[][] arr) throws IllegalArgumentException {
        if (arr.length < 1 || arr[0].length < 1) {
            throw new IllegalArgumentException("Array must at least have dimensions 1x1");
        }
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i].length != arr[0].length) {
                throw new IllegalArgumentException("All columns of the array must be of same length");
            }
        }
        this.arr = arr;
        rows = arr.length;
        cols = arr[0].length;
    }

    /**
     * Call to get a value from the matrix
     * 
     * @param row row position of the value
     * @param col col position of the value
     * @return the value at the specified position
     */
    public double get(int row, int col) {
        return arr[row][col];
    }

    /**
     * @return the number of rows in the matrix
     */
    public int rows() {
        return rows;
    }

    /**
     * @return the number of cols in the matrix
     */
    public int cols() {
        return cols;
    }

    /**
     * Call to multiply this matrix with another. If A = this, B = other, then
     * function corresonds to A*B.
     * 
     * @param m matrix that current should be multiplied with
     * @return the product of the two matrices
     */
    public Matrix multiply(Matrix m) {
        if (cols() != m.rows()) {
            throw new IllegalArgumentException("Rows of first matrix must be equal to cols of second matrix");
        }
        double[][] arr = new double[rows()][m.cols()];
        for (int i = 0; i < m.cols(); ++i) {
            for (int j = 0; j < rows(); ++j) {
                for (int k = 0; k < cols(); ++k) {
                    arr[j][i] += get(j, k) * m.get(k, i);
                }
            }
        }
        return new Matrix(arr);
    }

    /**
     * Call to calculate and return the determinant of the matrix
     * 
     * @return the determinant of the matrix
     * @throws IllegalArgumentException if the matrix is not a square matrix
     */
    public double determinant() throws IllegalArgumentException {
        if (rows() != cols()) {
            throw new IllegalArgumentException("Matrix is not a square matrix, and therefore has no determinant");
        }
        double ans = 1;
        Pair<double[][], Integer> p = rowReduce(arr);
        for (int i = 0; i < rows; ++i) {
            ans *= p.first()[i][i];
        }
        return ans * Math.pow(-1, p.second());
    }

    /**
     * Call to calculate and the return the inverse of the matrix
     * 
     * @return the inverse of the matrix
     * @throws IllegalArgumentException if the matrix is not a square matrix
     */
    public Matrix inverse() throws IllegalArgumentException {
        if (rows() != cols()) {
            throw new IllegalArgumentException("Matrix is not a square matrix, and is therefore not invertible");
        }
        double[][] aug = new double[rows][cols * 2];
        // copy over
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                aug[i][j] = arr[i][j];
            }
            for (int j = 0; j < cols; ++j) {
                aug[i][j + cols] = j == i ? 1 : 0;
            }
        }
        aug = rowReduce(aug).first();

        // scales rows such that pivots are 1
        for (int i = 0; i < rows; ++i) {
            double scalar = aug[i][i];
            aug[i][i] = 1;
            for (int j = i + 1; j < cols * 2; ++j) {
                aug[i][j] /= scalar;
            }
        }

        // subtract rows such that only pivots in the first matrix remain
        for (int i = 0; i < rows; ++i) {
            for (int j = i + 1; j < cols; ++j) {
                double scalar = -(aug[i][j] / aug[j][j]);
                for (int k = j; k < cols * 2; ++k) {
                    aug[i][k] += scalar * aug[j][k];
                }
            }

        }
        return new Matrix(sliceCols(aug, cols, cols * 2));
    }

    /**
     * @return the transposed version of this matrix
     */
    public Matrix transpose() {
        double[][] arr = new double[cols()][rows()];
        for (int i = 0; i < rows(); ++i) {
            for (int j = 0; j < cols(); ++j) {
                arr[j][i] = get(i, j);
            }
        }
        return new Matrix(arr);
    }

    @Override
    public String toString() {
        String[] s = new String[rows];
        for (int i = 0; i < rows; ++i) {
            s[i] = "";
            for (int j = 0; j < cols; ++j) {
                s[i] += Double.toString(arr[i][j]) + " ";
            }
        }
        return String.join("\n", s);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(arr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Matrix m = (Matrix) o;
        return Arrays.deepEquals(arr, m.arr);
    }

    private static Pair<double[][], Integer> rowReduce(double[][] arr) {
        double[][] newArr = new double[arr.length][arr[0].length];
        int rowsSwapped = 0;
        for (int i = 0; i < arr.length; ++i) {
            newArr[i] = arr[i].clone();
        }
        for (int i = 0; i < arr.length; ++i) {
            int pivot = -1;
            for (int j = i; j < arr.length; ++j) {
                if (newArr[j][i] == 0) {
                    continue;
                }
                pivot = j;
                break;
            }
            if (pivot == -1) {
                continue;
            }
            if (pivot != i) {
                double[] temp = newArr[i];
                newArr[i] = newArr[pivot];
                newArr[pivot] = temp;
                ++rowsSwapped;
            }
            for (int j = i + 1; j < arr.length; ++j) {
                double scalar = -(newArr[j][i] / newArr[i][i]);
                for (int k = i; k < arr[0].length; ++k) {
                    newArr[j][k] += scalar * newArr[i][k];
                }
            }
        }
        return new Pair<>(newArr, rowsSwapped);
    }

    private static double[][] sliceCols(double[][] arr, int start, int stop) {
        double[][] newArr = new double[arr.length][stop - start];
        for (int i = 0; i < arr.length; ++i) {
            for (int j = start; j < stop; ++j) {
                newArr[i][j - start] = arr[i][j];
            }
        }
        return newArr;
    }
}
