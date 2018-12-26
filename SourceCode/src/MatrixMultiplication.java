
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;




public class MatrixMultiplication {

    private static Scanner kb;


    public static void main(String[] args) {


//        writeMatrixInputIntoFile(inFile, n); //this method to generate two matrices of a given size randomly and save them in txt file.

        ArrayList<long[][]> fd = readMatrixFromFile(6, "input/matrix_06.txt");
        System.out.println(fd.get(0)[0].length);
        System.out.println(fd.get(1).length);



//        System.out.println("Welcome to our Matrix Multiplication System... ");
//        System.out.println("Please write the number of method you want to use followed by the size of the matrix " +
//                "followed by the input and output file " +
//                "\n(in case you choose method number (4), the program will ask you to provide the base)");
//        kb = new Scanner(System.in);
//        boolean k = false;
//        String opFile;
//          do {    //this loop for the main menu (OUTER LOOP)
//              System.out.println("\nmethodNum sizeOfMatrix inputFile.txt outputFile.txt\n Here is an example: \n1 8 matrix_08.txt output.txt");
//              System.out.println("\n 1-The classical iterative algorithm." +
//                      "\n 2-The classical divide and conquer recursive algorithm.  " +
//                      "\n 3-The classical Strassen’s divide and conquer recursive algorithm." +
//                      "\n 4-Strassen’s divide and conquer recursive algorithm.\n"); // the Main Menu.
//
//              int choice = kb.nextInt();
//              int n = kb.nextInt();
//              String inFile = "input/";
//              inFile += kb.next();
//              opFile = kb.next();
//
//              if (choice == 1) {
//                  iterativeClassicalMultiply(n, inFile, opFile);
//                  k = true;
//
//              } else if (choice == 2) {
//                  recursiveClassicalMultiply(n, inFile, opFile);
//                  k = true;
//
//              } else if (choice == 3) {
//                  strassenMultiplyBase1(n, inFile, opFile);
//                  k = true;
//
//              } else if (choice == 4) {
//                  System.out.print("Enter the base you want: ");
//                  int base = kb.nextInt();
//                  strassenMultiplyBaseN(n, inFile, opFile, base);
//                  k = true;
//
//
//              }
//              else {
//                  System.out.println("Invalid Number of method, please try again...");
//                  k = false;
//              }
//
//          } while (!k);
//          System.out.println();
//          System.out.print("The multiplication result has been written successfully in " + opFile + " file");
//          System.out.println();
//
//        kb.close();
    }



    public static void iterativeClassicalMultiply(int n, String input, String output) {
        ArrayList<long[][]> matrices = readMatrixFromFile(n,input);
        double sTime = System.currentTimeMillis();
        long[][] resultMatrix = iterativeClassicalMultiply(matrices.get(0),matrices.get(1));
        double eTime = System.currentTimeMillis();
        double period = (eTime - sTime)/1000.0;
        writeMatrixIntoFile(output,resultMatrix, period);
    }

    private static long[][] iterativeClassicalMultiply(long[][] fMatrix,long[][] sMatrix) {
        int size = fMatrix.length;
        long[][] resultMatrix = new long[size][size];
        for(int i=0;i<size;i++){
            for(int j =0; j<size;j++){
                resultMatrix[i][j]=0;
                for(int k = 0;k<size;k++){
                    resultMatrix[i][j] += fMatrix[i][k] * sMatrix[k][j];
                }
            }
        }
        return resultMatrix;
    }

    //-----------------------------------------------------------------------------------------------------

    public static void recursiveClassicalMultiply(int n, String input, String output) {
        ArrayList<long[][]> matrices = readMatrixFromFile(n,input);
        double sTime = System.currentTimeMillis();
        long[][] resultMatrix = rCM(matrices.get(0),matrices.get(1),0,0,0,0,matrices.get(0).length);
        double eTime = System.currentTimeMillis();
        double period = (eTime - sTime)/1000.0;
        writeMatrixIntoFile(output,resultMatrix, period);
    }

    public static long[][]  rCM(long[][] fMatrix,long[][] sMatrix, int fMRS,  int fMCS, int sMRS,  int sMCS,int eSize){
        long[][] result = new long[eSize][eSize];
        if(eSize==1){
            result[0][0] = fMatrix[fMRS][fMCS] * sMatrix[sMRS][sMCS];
            return result;
        }
        else {
            int nESize = eSize /2;
            // This is for the frist part result 1 1
            long[][] result11a =  rCM(fMatrix,sMatrix,fMRS,fMCS,sMRS,sMCS,nESize);
            long[][] result11b =  rCM(fMatrix,sMatrix,fMRS,fMCS + nESize,sMRS + nESize,sMCS,nESize);
            addMatricesForRecursive(result11a, result11b, result,0, 0);
            // This is for the frist part result 1 2
            long[][] result12a =  rCM(fMatrix,sMatrix,fMRS,fMCS,sMRS,sMCS + nESize,nESize);
            long[][] result12b =  rCM(fMatrix,sMatrix,fMRS,fMCS + nESize,sMRS + nESize,sMCS + nESize,nESize);
            addMatricesForRecursive(result12a, result12b, result,0, nESize);
            // This is for the frist part result 2 1
            long[][] result21a =  rCM(fMatrix,sMatrix,fMRS + nESize,fMCS,sMRS,sMCS,nESize);
            long[][] result21b =  rCM(fMatrix,sMatrix,fMRS + nESize,fMCS + nESize,sMRS + nESize,sMCS,nESize);
            addMatricesForRecursive(result21a, result21b, result,nESize, 0);
            // This is for the frist part result 2 2
            long[][] result22a =  rCM(fMatrix,sMatrix,fMRS + nESize,fMCS,sMRS,sMCS + nESize,nESize);
            long[][] result22b =  rCM(fMatrix,sMatrix,fMRS + nESize,fMCS + nESize,sMRS + nESize,sMCS + nESize,nESize);
            addMatricesForRecursive(result22a, result22b, result,nESize, nESize);
            return result;
        }
    }

    //-----------------------------------------------------------------------------------------------------

    public static void strassenMultiplyBase1(int n, String input, String output) {
        ArrayList<long[][]> matrices = readMatrixFromFile(n,input);
        double sTime = System.currentTimeMillis();
        long [][] resultMatrix  = strassenMultiplyBase1(matrices.get(0), matrices.get(1));
        double eTime = System.currentTimeMillis();
        double period = (eTime - sTime)/1000.0;
        writeMatrixIntoFile(output,resultMatrix, period);

    }

    private static long [][] strassenMultiplyBase1(long[][] fMatrix, long[][] sMatrix) {
        int n = fMatrix.length;
        long [][] result = new long[n][n];

        if(n == 2) {
            long fMatrix11 = fMatrix[0][0];
            long fMatrix12 = fMatrix[0][1];
            long fMatrix21 = fMatrix[1][0];
            long fMatrix22 = fMatrix[1][1];

            long sMatrix11 = sMatrix[0][0];
            long sMatrix12 = sMatrix[0][1];
            long sMatrix21 = sMatrix[1][0];
            long sMatrix22 = sMatrix[1][1];

            long d1 = (fMatrix11+fMatrix22)*(sMatrix11+sMatrix22);
            long d2 = (fMatrix21+fMatrix22)*(sMatrix11);
            long d3 = (fMatrix11)*(sMatrix12-sMatrix22);
            long d4 = (fMatrix22)*(sMatrix21-sMatrix11);
            long d5 = (fMatrix11+fMatrix12)*(sMatrix22);
            long d6 = (fMatrix21-fMatrix11)*(sMatrix11+sMatrix12);
            long d7 = (fMatrix12-fMatrix22)*(sMatrix21+sMatrix22);

            result[0][0] = d1 + d4 - d5+ d7;
            result[0][1]= d3 + d5;
            result[1][0]= d2 + d4;
            result[1][1] = d1 + d3 - d2+ d6;
        }
        else {
            // Matrix fMatrix
            long [][] a11 = new long[n/2][n/2];

            long [][] a22 = new long[n/2][n/2];

            // Matrix sMatrix
            long [][] b11 = new long[n/2][n/2];

            long [][] b22 = new long[n/2][n/2];

            divide(fMatrix, a11, 0, 0);
            divide(fMatrix, a22, n/2, n/2);
            divide(sMatrix, b11, 0, 0);
            divide(sMatrix, b22, n/2, n/2);


            //d1 = (a11 + a22) * (b11 + b22)
            long [][] d1A = addMatricesStrassen(fMatrix, 0, 0 ,n/2, n/2, n/2);
            long [][] d1B = addMatricesStrassen(sMatrix, 0, 0 ,n/2, n/2, n/2);
            long [][] d1 = strassenMultiplyBase1(d1A,d1B);

            // d2 = (a21 + a22) * b11
            long [][] d2A = addMatricesStrassen(fMatrix, n/2, 0 ,n/2, n/2, n/2);
            long [][] d2 = strassenMultiplyBase1(d2A,b11);

            //d3 = a11 * (b12 - b22)
            long [][] d3B = subMatricesStrassen(sMatrix, 0, n/2 ,n/2, n/2, n/2);
            long [][] d3 = strassenMultiplyBase1(a11,d3B);

            //d4 = a22 * (b21 - b11)
            long [][] d4B = subMatricesStrassen(sMatrix, n/2, 0 ,0, 0, n/2);
            long [][] d4 = strassenMultiplyBase1(a22,d4B);

            //d5 = (a11 + a12) * b22
            long [][] d5A = addMatricesStrassen(fMatrix, 0, 0 ,0, n/2, n/2);
            long [][] d5 = strassenMultiplyBase1(d5A,b22);

            //d6 = (a21 - a11) * (b11 + b12)
            long [][] d6A = subMatricesStrassen(fMatrix, n/2, 0 ,0, 0, n/2);
            long [][] d6B = addMatricesStrassen(sMatrix, 0, 0 ,0, n/2, n/2);
            long [][] d6 = strassenMultiplyBase1(d6A,d6B);

            //d7 = (a12 - a22) * (b21 + b22)
            long [][] d7A = subMatricesStrassen(fMatrix, 0, n/2 ,n/2, n/2, n/2);
            long [][] d7B = addMatricesStrassen(sMatrix, n/2, 0 ,n/2, n/2, n/2);
            long [][] d7 = strassenMultiplyBase1(d7A,d7B);






            // c11 = ((d1 + d4) - d5) + d7
            long [][] c11_tmp = subMatrices( d4 , d5);
            c11_tmp = addMatrices(c11_tmp,d1);
            addMatricesForRecursive(c11_tmp, d7, result, 0,0);


            // c12 = d3 + d5
            addMatricesForRecursive(d3, d5, result, 0,n/2);

            // c21 = ((d1 + d4) - d5) + d7
            addMatricesForRecursive(d2, d4, result, n/2,0);


            // c22 = ((d1 + d4) - d5) + d7
            long [][] c22_tmp = subMatrices( d3 , d2);
            c22_tmp = addMatrices(c22_tmp,d1);
            addMatricesForRecursive(c22_tmp, d6, result, n/2,n/2);


        }

        return result;
    }

    //-----------------------------------------------------------------------------------------------------

    public static void strassenMultiplyBaseN(int n, String input, String output, int base) {
        ArrayList<long[][]> matrices = readMatrixFromFile(n,input);
        double sTime = System.currentTimeMillis();
        base = (int) Math.pow(2,base);
        long [][] resultMatrix  = strassenMultiplyBaseN(matrices.get(0), matrices.get(1), base);
        double eTime = System.currentTimeMillis();
        double period = (eTime - sTime)/1000.0;
        writeMatrixIntoFile(output,resultMatrix, period);
    }

    public static long [][] strassenMultiplyBaseN(long[][] fMatrix, long[][] sMatrix, int base) {
        int n = fMatrix.length;
        long [][] result = new long[n][n];

        if(n <= base) {
            result = iterativeClassicalMultiply(fMatrix,sMatrix);
        }
        else {
            // Matrix fMatrix
            long [][] a11 = new long[n/2][n/2];

            long [][] a22 = new long[n/2][n/2];

            // Matrix sMatrix
            long [][] b11 = new long[n/2][n/2];

            long [][] b22 = new long[n/2][n/2];

            divide(fMatrix, a11, 0, 0);
            divide(fMatrix, a22, n/2, n/2);
            divide(sMatrix, b11, 0, 0);
            divide(sMatrix, b22, n/2, n/2);


            //d1 = (a11 + a22) * (b11 + b22)
            long [][] d1A = addMatricesStrassen(fMatrix, 0, 0 ,n/2, n/2, n/2);
            long [][] d1B = addMatricesStrassen(sMatrix, 0, 0 ,n/2, n/2, n/2);
            long [][] d1 = strassenMultiplyBaseN(d1A,d1B,base);

            // d2 = (a21 + a22) * b11
            long [][] d2A = addMatricesStrassen(fMatrix, n/2, 0 ,n/2, n/2, n/2);
            long [][] d2 = strassenMultiplyBaseN(d2A,b11,base);

            //d3 = a11 * (b12 - b22)
            long [][] d3B = subMatricesStrassen(sMatrix, 0, n/2 ,n/2, n/2, n/2);
            long [][] d3 = strassenMultiplyBaseN(a11,d3B,base);

            //d4 = a22 * (b21 - b11)
            long [][] d4B = subMatricesStrassen(sMatrix, n/2, 0 ,0, 0, n/2);
            long [][] d4 = strassenMultiplyBaseN(a22,d4B,base);

            //d5 = (a11 + a12) * b22
            long [][] d5A = addMatricesStrassen(fMatrix, 0, 0 ,0, n/2, n/2);
            long [][] d5 = strassenMultiplyBaseN(d5A,b22,base);

            //d6 = (a21 - a11) * (b11 + b12)
            long [][] d6A = subMatricesStrassen(fMatrix, n/2, 0 ,0, 0, n/2);
            long [][] d6B = addMatricesStrassen(sMatrix, 0, 0 ,0, n/2, n/2);
            long [][] d6 = strassenMultiplyBaseN(d6A,d6B,base);

            //d7 = (a12 - a22) * (b21 + b22)
            long [][] d7A = subMatricesStrassen(fMatrix, 0, n/2 ,n/2, n/2, n/2);
            long [][] d7B = addMatricesStrassen(sMatrix, n/2, 0 ,n/2, n/2, n/2);
            long [][] d7 = strassenMultiplyBaseN(d7A,d7B,base);






            // c11 = ((d1 + d4) - d5) + d7
            long [][] c11_tmp = subMatrices( d4 , d5);
            c11_tmp = addMatrices(c11_tmp,d1);
            addMatricesForRecursive(c11_tmp, d7, result, 0,0);


            // c12 = d3 + d5
            addMatricesForRecursive(d3, d5, result, 0,n/2);

            // c21 = ((d1 + d4) - d5) + d7
            addMatricesForRecursive(d2, d4, result, n/2,0);


            // c22 = ((d1 + d4) - d5) + d7
            long [][] c22_tmp = subMatrices( d3 , d2);
            c22_tmp = addMatrices(c22_tmp,d1);
            addMatricesForRecursive(c22_tmp, d6, result, n/2,n/2);


        }

        return result;
    }

    //-----------------------------------------------------------------------------------------------------

    public static ArrayList<long[][]> readMatrixFromFile(int n,String fileName) {
        Scanner in;
        ArrayList<long[][]> matrices = new ArrayList<long[][]>(2);

        try {
            Scanner s = new Scanner(new File(fileName));
            n = (int) Math.pow(2,n);
            long matrix[][] = new long[n][n];
            long matrix1[][] = new long[n][n];
            for (int i = 0; i < n && s.hasNextLine(); i++) {
                for (int col = 0; col < matrix.length && s.hasNextInt(); col++) {
                    matrix[i][col] = s.nextByte();
                }
//                s.nextLine(); // col values populated for this row, time to go to the next line
            }
            for (int i = 0; i < n && s.hasNextLine(); i++) {
                for (int col = 0; col < matrix.length && s.hasNextInt(); col++) {
                    matrix1[i][col] = s.nextByte();
                }
//                s.nextLine(); // col values populated for this row, time to go to the next line
            }
            s.close();
            matrices.add(matrix);
            matrices.add(matrix1);
            return matrices;

        } catch (IOException i) {
            System.out.println("Problems..");

        }
        return null;
    }

    public static long[][] readMatrixFromFile1(int n,String fileName) {
        Scanner in;
        try {
            Scanner s = new Scanner(new File(fileName));
            n = (int) Math.pow(2,n);
            long matrix[][] = new long[n][n];
            for (int i = 0; i < n && s.hasNextLine(); i++) {
                for (int col = 0; col < matrix.length && s.hasNextInt(); col++) {
                    matrix[i][col] = s.nextByte();
                }
//                s.nextLine(); // col values populated for this row, time to go to the next line
            }

            s.close();
            return matrix;

        } catch (IOException i) {
            System.out.println("Problems..");

        }
        return null;
    }

    //-----------------------------------------------------------------------------------------------------

    public static void writeMatrixIntoFile(String filename, long[][] matrix, double time) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("The time spent on matrix multiplication = "+time + " seconds");
            bw.newLine();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    bw.write(matrix[i][j] + "\t");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {}
    }


    public static void writeMatrixInputIntoFile(String filename, int n) {

        int size = (int) Math.pow(2,n)*2;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size/2; j++) {
                    bw.write(getRandomNumberInRange(-100, 100) + "\t");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {}
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (int)(Math.random() * ((max - min) + 1)) + min;
    }


    //-----------------------------------------------------------------------------------------------------

    public static void addMatricesForRecursive(long[][] fMatrix, long[][] sMatrix, long[][] result,int rRS, int rCS) {
        int size = fMatrix.length;
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                result[rRS + i][rCS + k] = fMatrix[i][k] + sMatrix[i][k];
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------

    public static long [][] addMatricesStrassen(long[][] matrix, int fMRS,  int fMCS, int sMRS,  int sMCS,int eSize) {
        long[][] result = new long [eSize][eSize];
        for (int i = 0; i < eSize; i++) {
            for (int k = 0; k < eSize; k++) {
                result[i][k] = matrix[fMRS+i][fMCS+k] + matrix[sMRS+i][sMCS+k];
            }
        }
        return result;
    }

    public static long [][] subMatricesStrassen(long[][] matrix, int fMRS,  int fMCS, int sMRS,  int sMCS,int eSize) {
        long[][] result = new long [eSize][eSize];
        for (int i = 0; i < eSize; i++) {
            for (int k = 0; k < eSize; k++) {
                result[i][k] = matrix[fMRS+i][fMCS+k] - matrix[sMRS+i][sMCS+k];
            }
        }
        return result;
    }

    //-----------------------------------------------------------------------------------------------------

    // Adding 2 matrices
    public static long[][] addMatrices(long[][] a, long[][] b) {
        int n = a.length;
        long[][] c = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }

    // Subtracting 2 matrices
    public static long[][] subMatrices(long[][] a, long[][] b) {
        int n = a.length;
        long[][] c = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = a[i][j] - b[i][j];
            }
        }
        return c;
    }

    // divides original two Matrices
    public static void divide(long[][] P, long[][] C, int iBound, int jBound)
    {
        for(int i1 = 0, i2 = iBound; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jBound; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }

    //-----------------------------------------------------------------------------------------------------



}
