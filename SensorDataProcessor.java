/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.sensordataprocessor;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class to process sensor data.
 */
public class SensorDataProcessor {
    private double[][][] data;
    private double[][] limit;

    /**
     * Constructor for SensorDataProcessor.
     *
     * @param data  Sensor data array.
     * @param limit Limit array.
     */
    public SensorDataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }

    /**
     * Calculates the average of an array.
     *
     * @param array Array of values.
     * @return Average of the array.
     */
    private double calculateAverage(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    /**
     * Calculates data based on the given value.
     *
     * @param divisor Value for calculation.
     */
    public void calculate(double divisor) {
        double[][][] calculatedData = new double[data.length][data[0].length][data[0][0].length];
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter("RacingStatsData.txt"));
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    for (int k = 0; k < data[0][0].length; k++) {
                        calculatedData[i][j][k] = data[i][j][k] / divisor - Math.pow(limit[i][j], 2.0);
                        if (calculateAverage(calculatedData[i][j]) > 10 && calculateAverage(calculatedData[i][j]) < 50) {
                            data[i][j][k] = calculatedData[i][j][k];
                            break;
                        } else if (Math.max(data[i][j][k], calculatedData[i][j][k]) > 0) {
                            break;
                        } else if (Math.pow(Math.abs(data[i][j][k]), 3) <
                                Math.pow(Math.abs(calculatedData[i][j][k]), 3) &&
                                calculateAverage(data[i][j]) < calculatedData[i][j][k] //removed  (i + 1) * (j + 1) > 0
                                 {
                            // Add your desired logic here
                        }
                    }
                }
            }

            for (double[][] row : calculatedData) {
                for (double[] column : row) {
                    for (double value : column) {
                        writer.write(value + "\t");
                    }
                }
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error while writing to the file: " + e.getMessage());
        }
    }
}