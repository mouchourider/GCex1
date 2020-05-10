//Franck Schwartz 329863237
//Raphael Abenmoha 337689103
package utils;

import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * Math functions class.
 */
public class Mathematics {
    /**
     * Calculates average of two numbers.
     *
     * @param num1 first number
     * @param num2 second number
     * @return average
     */
    public static double average(double num1, double num2) {
        return (num1 + num2) / 2;
    }
    /**
     * Determines if var is between the numbers.
     *
     * @param num1 first border
     * @param var  number
     * @param num2 second border
     * @return true\false
     */
    public static boolean isBetween(double num1, double var, double num2) {
        return (num1 <= var && var <= num2) || (num2 <= var && var <= num1);
    }
    /**
     * calculate the distance between two points.
     * @param  x is second point.
     * @return distance
     */
    public static double distance(Point p1, double x, double y) {
        return Math.sqrt(Math.pow(p1.getX() - x, 2)
                    + Math.pow(p1.getY() - y, 2));
    }
    /**
     * Return a^2 + b^2.
     *
     * @param a first number
     * @param b second number
     * @return c^2
     */
    public static double pythagoras(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    /**
     * Convet string array to int array.
     *
     * @param sArr string array to convert
     * @return Int array
     */
    public static int[] stringsToInts(String[] sArr) {
        int[] iArr = new int[sArr.length];

        for (int i = 0; i < sArr.length; i++) {
            iArr[i] = Integer.parseInt(sArr[i]);
        }
        return iArr;
    }
    // return B = A^T
    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposeMatrix = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                transposeMatrix[j][i] = matrix[i][j];
        return transposeMatrix;
    }

    // return c = a + b
    public static double[][] add(double[][] matrixA, double[][] matrixB) {
        int rows = matrixA.length;
        int cols = matrixA[0].length;
        double[][] answer = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                answer[i][j] = matrixA[i][j] + matrixB[i][j];
        return answer;
    }

    // return c = a - b
    public static double[][] subtract(double[][] matrixA, double[][] matrixB) {
        int rows = matrixA.length;
        int cols = matrixA[0].length;
        double[][] answer = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                answer[i][j] = matrixA[i][j] - matrixB[i][j];
        return answer;
    }
    
    // return c = a * b
    public static double[][] multiplicateMatrix(double[][] matrixA, double[][] matrixB) {

        int aRows = matrixA.length;
        int aColumns = matrixA[0].length;
        int bRows = matrixB.length;
        int bColumns = matrixB[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return C;
    }
    
    // matrix-vector multiplication (y = A * x)
    public static double[] multiplyMatrixInVector(double[][] a, double[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }


    // vector-matrix multiplication (y = x^T A)
    public static double[] multiplyVectorInMatrix(double[] x, double[][] a) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                y[j] += a[i][j] * x[i];
        return y;
    }
    public static Point get_line_intersection(Line2D.Double pLine1, Line2D.Double pLine2)
    {
        Point
                result = null;

        double
                s1_x = pLine1.x2 - pLine1.x1,
                s1_y = pLine1.y2 - pLine1.y1,

                s2_x = pLine2.x2 - pLine2.x1,
                s2_y = pLine2.y2 - pLine2.y1,

                s = (-s1_y * (pLine1.x1 - pLine2.x1) + s1_x * (pLine1.y1 - pLine2.y1)) / (-s2_x * s1_y + s1_x * s2_y),
                t = ( s2_x * (pLine1.y1 - pLine2.y1) - s2_y * (pLine1.x1 - pLine2.x1)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
        {
            // Collision detected
            result = new Point(
                    (int) (pLine1.x1 + (t * s1_x)),
                    (int) (pLine1.y1 + (t * s1_y)));
        }   // end if

        return result;
    }
}
