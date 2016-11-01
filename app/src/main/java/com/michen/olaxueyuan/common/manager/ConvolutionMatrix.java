package com.michen.olaxueyuan.common.manager;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by kevin on 15/9/9.
 */
public class ConvolutionMatrix {
    public static final int SIZE = 3;

    public double[][] Matrix;
    public double Factor = 1;
    public double Offset = 1;

    public ConvolutionMatrix(int size) {
        Matrix = new double[size][size];
    }

    public void setAll(double value) {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                Matrix[x][y] = value;
            }
        }
    }

    public void applyConfig(double[][] config) {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                Matrix[x][y] = config[x][y];
            }
        }
    }

    public static Bitmap computeConvolution3x3(Bitmap src, ConvolutionMatrix matrix) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[SIZE][SIZE];
        int halfmode = SIZE / 2;

        for (int y = 1; y < (height - 1); ++y) {
            for (int x = 1; x < (width - 1); ++x) {

                // get pixel matrix
                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        pixels[i][j] = src.getPixel(x + i - halfmode, y + j - halfmode);
                    }
                }

                // get alpha of center pixel
                A = Color.alpha(pixels[1][1]);

                // init color sum
                sumR = sumG = sumB = 0;

                // get sum of RGB on matrix
                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        sumR += (Color.red(pixels[i][j]) * matrix.Matrix[i][j]);
                        sumG += (Color.green(pixels[i][j]) * matrix.Matrix[i][j]);
                        sumB += (Color.blue(pixels[i][j]) * matrix.Matrix[i][j]);
                    }
                }

                /***********************************/
            /*          |-1   -1   -1|         */
            /*          |            |         */
            /* 1/8  X   |-1    8   -1|         */
            /*          |            |         */
            /*          |-1    -1  -1|         */
            /*                                 */
            /*                                 */
                /***********************************/
                //矩阵如上述模版，那么matrix.Factor ＝ 8（意义是8邻域参加计算）
                //matrix.Offset ＝ 0.5 应该是一个四舍五入的参数

                // get final Red
                R = (int) (sumR / matrix.Factor + matrix.Offset);
                if (R < 0) {
                    R = 0;
                } else if (R > 255) {
                    R = 255;
                }

                // get final Green
                G = (int) (sumG / matrix.Factor + matrix.Offset);
                if (G < 0) {
                    G = 0;
                } else if (G > 255) {
                    G = 255;
                }

                // get final Blue
                B = (int) (sumB / matrix.Factor + matrix.Offset);
                if (B < 0) {
                    B = 0;
                } else if (B > 255) {
                    B = 255;
                }

                // apply new pixel
                result.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // final image
        return result;
    }

}
