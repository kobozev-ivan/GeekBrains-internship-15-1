package reference;

import fakeDatabase.FakeServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.SheetReference;

import java.util.ArrayList;

/**
 * Created by Максим on 19.12.2017.
 */
public class Request implements Requestable{

    Message message;
    JSONObject answer;
    private JSONArray addWords = new JSONArray();
    private JSONArray changeDelWords = new JSONArray();
    private JSONArray changeAddWords = new JSONArray();
    private JSONArray delWords = new JSONArray();

    @Override
    public ArrayList<String> toUpDate(SheetReference sheetReference) {
        message = new Message(sheetReference);
        answer =  new FakeServer().toUpDate(message); // fake
        return message.toExtractData(answer);
    }

    public void toSave(SheetReference sheetReference) {

        message = new Message(sheetReference, addWords, changeDelWords, changeAddWords, delWords);
        System.out.println(message);
        new FakeServer().toSave(message); // fake
        addWords.clear();
        changeDelWords.clear();
        changeAddWords.clear();
        delWords.clear();

    }

    private boolean isMatchWord(JSONArray jsonArray, String string){
        for (int i = 0; i < jsonArray.size(); i++) {
            if (jsonArray.get(i).equals(string)){
                jsonArray.remove(i);
                return true;
            }
        }
        return false;
    }

    void toAdditionOfWords(String stringInput) {
        addWords.add(stringInput);
        if (!delWords.isEmpty()) isMatchWord(delWords, stringInput);
    }

    void toChangeWords(String stringSelect, String stringInput) {
        if (!addWords.isEmpty()){
            if (isMatchWord(addWords, stringSelect)){
                addWords.add(stringInput);
                return;
            }
        }
        changeDelWords.add(stringSelect);
        changeAddWords.add(stringInput);
    }

    void toRemovingWords(String stringSelect) {
        if (!addWords.isEmpty()){
            if (isMatchWord(addWords, stringSelect)) return;
        }
        if (!changeAddWords.isEmpty()){
            if (isMatchWord(changeAddWords, stringSelect)) return;
        }
        delWords.add(stringSelect);
    }
}
