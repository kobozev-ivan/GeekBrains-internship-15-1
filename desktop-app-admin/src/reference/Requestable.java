package reference;

import view.SheetReference;

/**
 * Created by Максим on 19.12.2017.
 */
public interface Requestable {

//    void toObtainData();
//    void toSendData();
    void toUpDate(SheetReference sheetReference);
    void toSave(SheetReference sheetReference);
}
