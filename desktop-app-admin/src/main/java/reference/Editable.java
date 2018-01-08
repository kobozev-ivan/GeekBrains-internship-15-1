package reference;

import gia.SheetReference;

public interface Editable {

    void toAdd(SheetReference sheetReference, String stringInput);
    void toModify(SheetReference sheetReference, String stringSelect, String stringInput);
    void toRemove(SheetReference sheetReference, String stringSelect);
}
