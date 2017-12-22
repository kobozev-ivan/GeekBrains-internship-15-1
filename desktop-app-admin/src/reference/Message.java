package reference;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.SheetReference;
import view.SheetReferenceKeywords;

/**
 * Created by Максим on 21.12.2017.
 */
class Message extends JSONObject{

    private static final String TABLE = "table";
    private static final String ADD = "add";
    private static final String CHANGE_DEL = "changeDel";
    private static final String CHANGE_ADD = "changeAdd";
    private static final String DEL = "del";
    private static final String NAMES = "namesString";
    private static final String ALL = "*";

    Message(SheetReference sheetReference){
        toDefineTable(sheetReference);
        put(NAMES, ALL);
    }

    Message(SheetReference sheetReference, JSONArray addWords, JSONArray changeDelWords, JSONArray changeAddWords, JSONArray delWords){
        toDefineTable(sheetReference);
        if (addWords.isEmpty()) put(ADD, null);
        else put(ADD, addWords);
        if (changeDelWords.isEmpty()) put(CHANGE_DEL, null);
        else put(CHANGE_DEL, changeDelWords);
        if (changeAddWords.isEmpty()) put(CHANGE_ADD, null);
        else put(CHANGE_ADD, changeAddWords);
        if (delWords.isEmpty())put(DEL, null);
        else put(DEL, delWords);
    }

    private void toDefineTable(SheetReference sheetReference){
        String nameTable = sheetReference.getName();
        put(TABLE, nameTable);
        if (nameTable.equals(CUW.KEYWORDS)){
            String selectPerson = ((SheetReferenceKeywords) sheetReference).selectComboBoxModel;
            put(nameTable, selectPerson);
        }
    }
}
