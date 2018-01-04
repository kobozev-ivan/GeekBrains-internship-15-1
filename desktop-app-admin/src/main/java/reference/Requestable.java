package reference;

import gia.SheetReference;
import java.util.ArrayList;

public interface Requestable {

    ArrayList<String> toUpDate(SheetReference sheetReference);
    void toSave(SheetReference sheetReference);

}
