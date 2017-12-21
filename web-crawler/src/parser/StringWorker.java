package parser;

public class StringWorker {
    //работает со строками большой длины
    //сокращает, вынимая из исходных строк нужную для обработки информацию

    public String handlingString(String str) { //требуется оптимизация метода, работает очень медленно!
        String[] splitResult1 = str.split("<loc>");
        String[] splitResult2;
        String res = "";

        for (int i = 1; i < splitResult1.length; i++) {
            String stroka = splitResult1[i];
            splitResult2 = stroka.split("</loc>");
            res += splitResult2[0] + " ";

        }

        return res;
    }
}
