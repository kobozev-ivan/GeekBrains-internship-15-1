package reference;

import gia.SheetReference;

import java.net.ConnectException;
import java.util.ArrayList;

public interface Requestable {

    ArrayList<String> toUpDate(SheetReference sheetReference) throws ConnectException;
    void toSave(SheetReference sheetReference);

}
