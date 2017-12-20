package reference;

import view.SheetReference;

import javax.swing.*;

/**
 * Created by Максим on 15.12.2017.
 */
public class Data<T> extends DefaultListModel<String> implements Editable{

    Data<T> data;


    @Override
    public void toUpDate(SheetReference sheetReference) {

    }

    @Override
    public void toAdd(SheetReference sheetReference, String string) {
        data = (Data<T>) sheetReference.list.getModel();
        data.addElement(string.trim());
    }

    @Override
    public void toModify(SheetReference sheetReference, int index, String string) {
        data = (Data<T>) sheetReference.list.getModel();
        data.remove(index);
        data.insertElementAt(string.trim(), index);
    }

    @Override
    public void toRemove(SheetReference sheetReference, int index) {
        data = (Data<T>) sheetReference.list.getModel();
        data.remove(index);
    }

    @Override
    public void toSave(SheetReference sheetReference) {

    }
}
