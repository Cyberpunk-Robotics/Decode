package org.firstinspires.ftc.teamcode.LookUpTable;

import java.util.Arrays;
import java.util.Comparator;

public class LinearLookupTable {

    private final double[] xValues;
    private final double[] yValues;
    private final int size;


    /**
     * Constructor. Takes unsorted arrays and sorts them by xValues.
     * @param xValues Array of x values (must be same length as yValues)
     * @param yValues Array of y values
     */
    public LinearLookupTable(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("xValues and yValues must have the same length.");
        }
        this.size = xValues.length;
        this.xValues = Arrays.copyOf(xValues, size);
        this.yValues = Arrays.copyOf(yValues, size);

        // Sort both arrays by xValues
        Integer[] indices = new Integer[size];
        for (int i = 0; i < size; i++) indices[i] = i;
        Arrays.sort(indices, Comparator.comparingDouble(i -> this.xValues[i]));

        double[] sortedX = new double[size];
        double[] sortedY = new double[size];
        for (int i = 0; i < size; i++) {
            sortedX[i] = this.xValues[indices[i]];
            sortedY[i] = this.yValues[indices[i]];
        }
        System.arraycopy(sortedX, 0, this.xValues, 0, size);
        System.arraycopy(sortedY, 0, this.yValues, 0, size);
    }

    /**
     * Linearly interpolates (or extrapolates) a value for the given x.
     * @param x The x value to interpolate
     * @return Interpolated y value
     */
    public double get(double x) {
        // Below first value -> extrapolate
        if (x <= xValues[0]) {
            return linear(x, xValues[0], yValues[0], xValues[1], yValues[1]);
        }
        // Above last value -> extrapolate
        if (x >= xValues[size - 1]) {
            return linear(x, xValues[size - 2], yValues[size - 2], xValues[size - 1], yValues[size - 1]);
        }
        // Find interval for interpolation
        for (int i = 0; i < size - 1; i++) {
            if (xValues[i] <= x && x <= xValues[i + 1]) {
                return linear(x, xValues[i], yValues[i], xValues[i + 1], yValues[i + 1]);
            }
        }
        // Should never reach here
        throw new IllegalStateException("Interpolation failed for x = " + x);
    }

    // Helper method: linear interpolation formula
    private double linear(double x, double x0, double y0, double x1, double y1) {
        return y0 + (y1 - y0) * (x - x0) / (x1 - x0);
    }

    public static void main(String[] args) {
        double[] x = {0, 1, 2, 3, 4};
        double[] y = {0, 10, 20, 30, 40};
        LinearLookupTable table = new LinearLookupTable(x, y);

        System.out.println("Interpolate 2.5: " + table.get(2.5)); // Should be 25.0
        System.out.println("Interpolate -1: " + table.get(-1));   // Extrapolation: -10.0
        System.out.println("Interpolate 5: " + table.get(5));     // Extrapolation: 50.0
    }
}
