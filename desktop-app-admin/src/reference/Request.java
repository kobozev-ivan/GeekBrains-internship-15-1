package reference;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.SheetReference;

import java.util.ArrayList;

    /**
 * Created by Максим on 19.12.2017.
 */
public class Request implements Requestable{

    private JSONArray addWords = new JSONArray();
    private JSONArray changeDelWords = new JSONArray();
    private JSONArray changeAddWords = new JSONArray();
    private JSONArray delWords = new JSONArray();

    @Override
    public ArrayList<String> toUpDate(SheetReference sheetReference) {
        Communication.communication.toSendData(new Message(sheetReference).toJSONString());
        return toExtractData(Communication.communication.toObtainData());
    }

    public void toSave(SheetReference sheetReference) {
        Communication.communication.toSendData(new Message(sheetReference, addWords, changeDelWords, changeAddWords, delWords).toJSONString());
        addWords.clear();
        changeDelWords.clear();
        changeAddWords.clear();
        delWords.clear();
        Communication.communication.toObtainData();

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

    private ArrayList<String> toExtractData(String string) {
        ArrayList<String> arrayList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(string);
            JSONArray jsonArray = (JSONArray) jsonObject.get(CUW.NAMES);
            if (!jsonArray.isEmpty()){
                for (int i = 0; i < jsonArray.size(); i++) {
                    arrayList.add((String) jsonArray.get(i));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
