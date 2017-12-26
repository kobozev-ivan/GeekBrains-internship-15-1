package parser;

public class StringWorker {
    //работает со строками большой длины
    //сокращает, вынимая из исходных строк нужную для обработки информацию

    public String handlingString(String str) { //требуется оптимизация метода, работает очень медленно!
        String[] splitResult1 = str.split("<loc>");
        String[] resOfSingleThread;
        String res = "";
        String[][] array1;
        Thread[] threads;
        int length = splitResult1.length;
        int NUMBER_OF_THREADS = length;
        int partSize = splitResult1.length / NUMBER_OF_THREADS;

        array1 = new String[NUMBER_OF_THREADS][partSize];
        threads = new Thread[NUMBER_OF_THREADS];
        resOfSingleThread = new String[NUMBER_OF_THREADS];

        for (int i = 0; i < threads.length; i++) {
            System.arraycopy(splitResult1, partSize * i, array1[i], 0, partSize);
            final int u = i;
            threads[i] = new Thread(() -> {
                String[] splitResult2;
                for(int j = 0; j < partSize; j++) {
                    String string = array1[u][j];
                    splitResult2 = string.split("</loc>");
                    array1[u][j] = splitResult2[0];
                }
                resOfSingleThread[u] = "";
                for(int k = 0; k < array1[u].length; k++) {
                    resOfSingleThread[u] += array1[u][k] + " ";
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 1; i < resOfSingleThread.length; i++) {
            res += resOfSingleThread[i];
        }

        return res;
    }
}
