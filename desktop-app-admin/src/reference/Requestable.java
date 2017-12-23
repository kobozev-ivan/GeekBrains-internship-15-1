package reference;

import view.SheetReference;

import java.util.ArrayList;

/**
 * Created by Максим on 19.12.2017.
 */
public interface Requestable {

//    void toObtainData();
//    void toSendData();
    ArrayList<String> toUpDate(SheetReference sheetReference);
    void toSave(SheetReference sheetReference);
}
