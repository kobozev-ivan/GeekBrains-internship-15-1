package reference;

import view.SheetReference;

/**
 * Created by Максим on 15.12.2017.
 */
public interface Editable {

    void toAdd(SheetReference sheetReference, String stringInput);
    void toModify(SheetReference sheetReference, String stringSelect, String stringInput);
    void toRemove(SheetReference sheetReference, String stringSelect);
}
