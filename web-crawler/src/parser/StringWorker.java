/**
 * Работает со строками большой длины, сокращает, вынимая из исходных строк нужную для обработки информацию
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;

public class StringWorker {
    private String[] splitResult1;
    private String[] resOfSingleThread;
    private String[][] array1;
    private Thread[] threads;
    private int threadsCount;
    private int length;
    private String result;
    private int partSize;

    /**
     * Метод обработки строк большой длины, вычленение из них ссылок веб-страниц
     * @param str
     * @return
     */

    public String handlingString(String str) {
        /**
         * TODO: требуется оптимизация метода, работает очень медленно!
         */
        this.splitResult1 = str.split("<loc>");
        this.result = "";
        this.length = this.splitResult1.length;
        this.threadsCount = this.length;
        this.partSize = this.splitResult1.length / this.threadsCount;

        this.array1 = new String[this.threadsCount][this.partSize];
        this.threads = new Thread[this.threadsCount];
        this.resOfSingleThread = new String[this.threadsCount];

        for (int i = 0; i < this.threads.length; i++) {
            System.arraycopy(this.splitResult1, this.partSize * i, this.array1[i], 0, this.partSize);
            final int u = i;
            this.threads[i] = new Thread(() -> {
                String[] splitResult2;
                for(int j = 0; j < this.partSize; j++) {
                    String string = this.array1[u][j];
                    splitResult2 = string.split("</loc>");
                    this.array1[u][j] = splitResult2[0];
                }
                this.resOfSingleThread[u] = "";
                for(int k = 0; k < this.array1[u].length; k++) {
                    this.resOfSingleThread[u] += this.array1[u][k] + " ";
                }
            });
            this.threads[i].start();
        }

        for (int i = 0; i < this.threadsCount; i++) {
            try {
                this.threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 1; i < this.resOfSingleThread.length; i++) {
            this.result += this.resOfSingleThread[i];
        }

        return this.result;
    }
}
