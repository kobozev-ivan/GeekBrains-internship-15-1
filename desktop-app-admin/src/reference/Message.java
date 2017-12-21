package reference;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    Message(String nameTable){
        put(TABLE, nameTable);
        put(NAMES, ALL);
    }

    Message(String nameTable, JSONArray addWords, JSONArray changeDelWords, JSONArray changeAddWords, JSONArray delWords){
        put(TABLE, nameTable);
        if (addWords.isEmpty()) put(ADD, null);
        else put(ADD, addWords);
        if (changeDelWords.isEmpty()) put(CHANGE_DEL, null);
        else put(CHANGE_DEL, changeDelWords);
        if (changeAddWords.isEmpty()) put(CHANGE_ADD, null);
        else put(CHANGE_ADD, changeAddWords);
        if (delWords.isEmpty())put(DEL, null);
        else put(DEL, delWords);
    }
}
