package reference;

import view.SheetReference;

import javax.swing.*;
import java.util.HashMap;

/**
 * Created by Максим on 15.12.2017.
 */
public class Data<T> extends DefaultListModel<String> implements Editable,Requestable{

    private Data<T> data;
    private HashMap<Data<T>, Request> hashMap = new HashMap<>();

    @Override
    public void toUpDate(SheetReference sheetReference) {
        Data<T> key = (Data<T>)sheetReference.list.getModel();
        if (!hashMap.containsKey(key)) hashMap.put(key, new Request());
        hashMap.get(key).toUpDate(sheetReference);
    }

    @Override
    public void toAdd(SheetReference sheetReference, String stringInput) {
        data = (Data<T>) sheetReference.list.getModel();
        data.addElement(stringInput);
        if (!hashMap.containsKey(data))  hashMap.put(data, new Request());
        (hashMap.get(data)).toAdd(sheetReference, stringInput);
    }

    @Override
    public void toModify(SheetReference sheetReference, String stringSelect, String stringInput) {
        data = (Data<T>) sheetReference.list.getModel();
        data.removeElement(stringSelect);
        data.addElement(stringInput);
        if (!hashMap.containsKey(data))  hashMap.put(data, new Request());
        hashMap.get(data).toModify(sheetReference, stringSelect, stringInput);
    }

    @Override
    public void toRemove(SheetReference sheetReference, String stringSelect) {
        data = (Data<T>) sheetReference.list.getModel();
        data.removeElement(stringSelect);
        if (!hashMap.containsKey(data))  hashMap.put(data, new Request());
        hashMap.get(data).toRemove(sheetReference, stringSelect);
    }

    @Override
    public void toSave(SheetReference sheetReference) {
        Data<T> key = (Data<T>)sheetReference.list.getModel();
        if (hashMap.containsKey(key)) hashMap.get(key).toSave(sheetReference);
    }
}
