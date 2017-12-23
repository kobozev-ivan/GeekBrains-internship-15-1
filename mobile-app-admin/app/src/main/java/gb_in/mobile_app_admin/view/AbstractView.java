package gb_in.mobile_app_admin.view;

/**
 * Created by Kuzin on 22.12.2017.
 */

public interface AbstractView {
    void updateViewPersonData(String[] data);
    void updateViewSiteData(String[] data);
    void updateViewKeywordData(String[] data);
    void displayError(String msg);
}
