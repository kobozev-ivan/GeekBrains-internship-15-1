package gb_in.mobile_app_admin.presenter;

import gb_in.mobile_app_admin.AddictionActivity;
import gb_in.mobile_app_admin.model.Model;

/**
 * Created by user on 22.01.2018.
 */

public class AddictionPresenter {
    private AddictionActivity addictionActivity;
    private Model model;

    public AddictionPresenter(AddictionActivity addictionActivity) {
        this.addictionActivity = addictionActivity;
        model = new Model();
    }

    public void loadKeywords(String name) {
        addictionActivity.updateListData(model.loadKeywords(name));
    }

    public void addKeyword(String name, String newKeyword){
        model.addKeyword(name,newKeyword);
    }
}
