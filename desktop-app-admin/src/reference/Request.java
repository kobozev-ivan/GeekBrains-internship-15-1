package reference;

import org.json.simple.JSONArray;
import view.SheetReference;

/**
 * Created by Максим on 19.12.2017.
 */
public class Request implements Requestable, Editable{

    Message message;
    private JSONArray addWords = new JSONArray();
    private JSONArray changeDelWords = new JSONArray();
    private JSONArray changeAddWords = new JSONArray();
    private JSONArray delWords = new JSONArray();

    @Override
    public void toUpDate(SheetReference sheetReference) {
        message = new Message(sheetReference.getName());
        System.out.println(message);
    }

    public void toSave(SheetReference sheetReference) {
        message = new Message(sheetReference.getName(), addWords, changeDelWords, changeAddWords, delWords);

        System.out.println(message);
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

    @Override
    public void toAdd(SheetReference sheetReference, String stringInput) {
        addWords.add(stringInput);
        if (!delWords.isEmpty()) isMatchWord(delWords, stringInput);
    }

    @Override
    public void toModify(SheetReference sheetReference, String stringSelect, String stringInput) {
        if (!addWords.isEmpty()){
            if (isMatchWord(addWords, stringSelect)){
                addWords.add(stringInput);
                return;
            }
        }
        changeDelWords.add(stringSelect);
        changeAddWords.add(stringInput);
    }

    @Override
    public void toRemove(SheetReference sheetReference, String stringSelect) {
        if (!addWords.isEmpty()){
            if (isMatchWord(addWords, stringSelect)) return;
        }
        if (!changeAddWords.isEmpty()){
            if (isMatchWord(changeAddWords, stringSelect)) return;
        }
        delWords.add(stringSelect);
    }
}
