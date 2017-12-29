package gb_in.mobile_app_admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import gb_in.mobile_app_admin.presenter.Presenter;
import gb_in.mobile_app_admin.view.AbstractView;

public class MainActivity extends AppCompatActivity implements AbstractView{
    private Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(this);

        setClickListeners();
    }

    private void setClickListeners(){
        Button btnAdd = (Button) findViewById(R.id.button_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addPersonData("p1");
                presenter.addKeywordData(0,"tt");
                presenter.addKeywordData(0,"ttt");

                presenter.addPersonData("p2");
                presenter.addKeywordData(1,"t");

                presenter.loadKeywordData(0);
                presenter.loadKeywordData(1);
            }
        });

        Button btnDel = (Button) findViewById(R.id.button_del);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteKeywordData(1,0);

                presenter.loadKeywordData(1);
            }
        });

        Button btnUpd = (Button) findViewById(R.id.button_upd);
        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.updateKeywordData(1, 0 ,"Site0");

                presenter.loadKeywordData(0);
            }
        });
    }

    @Override
    public void updateViewPersonData(String[] data) {
        Toast.makeText(getBaseContext(),Arrays.toString(data),Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateViewSiteData(String[] data) {
        Toast.makeText(getBaseContext(),Arrays.toString(data),Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateViewKeywordData(String[] data) {
        Toast.makeText(getBaseContext(),Arrays.toString(data),Toast.LENGTH_LONG).show();
    }
}
