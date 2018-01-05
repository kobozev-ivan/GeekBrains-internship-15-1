package reference;

import gia.SheetReference;

import javax.swing.*;
import javax.xml.ws.WebServiceException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;

public class Data<T> extends DefaultListModel<String> implements Editable, Requestable {

    private Data<T> data;
    private HashMap<Data<T>, Request> hashMap = new HashMap<>();

    @Override
    public ArrayList<String> toUpDate(SheetReference sheetReference) throws WebServiceException, ConnectException {
        Data<T> key = (Data<T>)sheetReference.list.getModel();
        if (!hashMap.containsKey(key)) hashMap.put(key, new Request());
        ArrayList<String> arrayListData = hashMap.get(key).toUpDate(sheetReference);
        if (!arrayListData.isEmpty()){
            key.clear();
            for (String arrayListElement : arrayListData) {
                key.addElement(arrayListElement);
            }
        }
        return arrayListData;
    }

    @Override
    public void toAdd(SheetReference sheetReference, String stringInput) {
        data = (Data<T>) sheetReference.list.getModel();
        data.addElement(stringInput);
        if (!hashMap.containsKey(data))  hashMap.put(data, new Request());
        (hashMap.get(data)).toAdditionOfWords(stringInput);
    }

    @Override
    public void toModify(SheetReference sheetReference, String stringSelect, String stringInput) {
        data = (Data<T>) sheetReference.list.getModel();
        data.removeElement(stringSelect);
        data.addElement(stringInput);
        if (!hashMap.containsKey(data))  hashMap.put(data, new Request());
        hashMap.get(data).toChangeWords(stringSelect, stringInput);
    }

    @Override
    public void toRemove(SheetReference sheetReference, String stringSelect) {
        data = (Data<T>) sheetReference.list.getModel();
        data.removeElement(stringSelect);
        if (!hashMap.containsKey(data))  hashMap.put(data, new Request());
        hashMap.get(data).toRemovingWords(stringSelect);
    }

    @Override
    public void toSave(SheetReference sheetReference) throws ConnectException {
        Data<T> key = (Data<T>)sheetReference.list.getModel();
        if (hashMap.containsKey(key)) hashMap.get(key).toSave(sheetReference);
    }


}
