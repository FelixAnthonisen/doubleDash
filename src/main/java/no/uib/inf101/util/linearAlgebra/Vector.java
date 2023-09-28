package no.uib.inf101.util.linearAlgebra;

import java.util.Arrays;

public class Vector {
    private final int rows;
    private final double[] arr;

    /**
     * Call to construct a matrix from a double array directly mapping elements from
     * the array to the vector. The array must at least be of dimensions
     * 1
     * 
     * @param arr the array the vector should be constructed from
     */
    public Vector(double[] arr) {
        this.arr = arr;
        rows = arr.length;
    }

    /**
     * 
     * @param row the row that should be returned
     * @return the value at that row
     */
    public double get(int row) {
        return arr[row];
    }

    /**
     * 
     * @return how many dimensions the rows span
     */
    public int rows() {
        return rows;
    }

    /**
     * 
     * @return the length of the vector in euclidean space
     */
    public double length() {
        int len = 0;
        for (double i : arr) {
            len += Math.pow(i, 2);
        }
        return Math.sqrt(len);
    }

    /**
     * Call to create a scaled copy of the vector
     * 
     * @param scalar the scalar the vector should be scaled by
     * @return the new scaled vector
     */
    public Vector scale(double scalar) {
        double[] arr = new double[rows];
        for (int i = 0; i < rows; ++i) {
            arr[i] = get(i) * scalar;
        }
        return new Vector(arr);
    }

    /**
     * Call to create a vector adding together the current vector and the argument
     * 
     * @param v the vector that should be added
     * @return the new vector
     */
    public Vector add(Vector v) {
        if (rows != v.rows()) {
            throw new IllegalArgumentException("Vectors are not same dimensions");
        }
        double[] arr = new double[rows];
        for (int i = 0; i < rows; ++i) {
            arr[i] = get(i) + v.get(i);
        }
        return new Vector(arr);
    }

    /**
     * call to get the dot product of the current vector and the argument
     * 
     * @param v the vector it should be dotted with
     * @return the dot product
     */
    public double dot(Vector v) {
        if (rows != v.rows()) {
            throw new IllegalArgumentException("Vectors are not same dimensions");
        }
        double ans = 0;
        for (int i = 0; i < rows; ++i) {
            ans += get(i) * v.get(i);
        }
        return ans;
    }

    /**
     * Call to create a new vector of the cross product between the current vector
     * and the argument
     * 
     * @param v the vector it should be crossed with
     * @return the cross product of the two vectors
     */
    public Vector cross(Vector v) {
        if (rows != v.rows() || rows != 3) {
            throw new IllegalArgumentException("Both vectors should be in R3");
        }
        double[] arr = new double[] {
                get(1) * v.get(2) - get(2) * v.get(1),
                get(2) * v.get(0) - get(0) * v.get(2),
                get(0) * v.get(1) - get(1) * v.get(0)
        };
        return new Vector(arr);
    }

    /**
     * Call to create a new vector which is the multiplication of the current vector
     * and the argument matrix
     * 
     * @param m the matrix it should be multiplied with
     * @return the new multiplied vector
     */
    public Vector matMul(Matrix m) {
        if (rows() != m.cols()) {
            throw new IllegalArgumentException("Rows of vector must be equal to cols of matrix");
        }
        double[] arr = new double[m.rows()];
        for (int j = 0; j < m.rows(); ++j) {
            for (int k = 0; k < m.cols(); ++k) {
                arr[j] += m.get(j, k) * get(k);
            }
        }
        return new Vector(arr);
    }

    public static boolean linearDependent(Vector[] v) {
        // TODO
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < rows; ++i) {
            s += Double.toString(arr[i]) + " ";
        }
        return s;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
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
        Vector v = (Vector) o;
        return Arrays.equals(arr, v.arr);
    }
}
