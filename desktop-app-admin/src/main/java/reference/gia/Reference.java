package reference.gia;

import reference.CUW;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Reference extends JTabbedPane{

    private SheetReference namesOfIndividuals = new SheetReference();
    private SheetReference namesOfSites = new SheetReference();
    private SheetReferenceKeywords keywords = new SheetReferenceKeywords();

    Reference(){

        namesOfIndividuals.setName(CUW.PERSONS);
        namesOfSites.setName(CUW.SITES);
        keywords.setName(CUW.KEYWORDS);

        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (((JTabbedPane)e.getSource()).getSelectedComponent() == keywords){
                    keywords.toAgree(namesOfIndividuals);
                    if (keywords.comboBoxModel.getSize() == 0) keywords.buttonAdd.setEnabled(false);
                    else keywords.buttonAdd.setEnabled(true);
                }
            }
        });

        add("Имена", namesOfIndividuals);
        add("Названия сайтов", namesOfSites);
        add("Ключевые слова", keywords);
    }
}