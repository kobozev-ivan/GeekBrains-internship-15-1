package reference;

import view.SheetReference;

import javax.swing.*;

/**
 * Created by Максим on 15.12.2017.
 */
public class Data<T> extends DefaultListModel<String> implements Editable,Requestable{

    private Data<T> data;
    private Request request = new Request();

    @Override
    public void toUpDate(SheetReference sheetReference) {
        request.toUpDate(sheetReference);
    }

    @Override
    public void toAdd(SheetReference sheetReference, String stringInput) {
        data = (Data<T>) sheetReference.list.getModel();
        data.addElement(stringInput);
        request.toAdd(sheetReference, stringInput);
    }

    @Override
    public void toModify(SheetReference sheetReference, String stringSelect, String stringInput) {
        int index = indexOf(stringSelect);
        data = (Data<T>) sheetReference.list.getModel();
        request.toModify(sheetReference, stringSelect, stringInput);
        data.removeElement(stringSelect);
        data.insertElementAt(stringInput, index);
    }

    @Override
    public void toRemove(SheetReference sheetReference, String stringSelect) {
        data = (Data<T>) sheetReference.list.getModel();
        data.removeElement(stringSelect);
        request.toRemove(sheetReference, stringSelect);
    }

    @Override
    public void toSave(SheetReference sheetReference) {
        request.toSave(sheetReference);
    }
}
