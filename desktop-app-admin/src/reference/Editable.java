package reference;

import view.SheetReference;

/**
 * Created by Максим on 15.12.2017.
 */
public interface Editable {

    void toUpDate(SheetReference sheetReference);
    void toAdd(SheetReference sheetReference, String string);
    void toModify(SheetReference sheetReference, int index, String string);
    void toRemove(SheetReference sheetReference, int index);
    void toSave(SheetReference sheetReference);
}
