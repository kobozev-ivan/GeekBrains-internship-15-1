package gb_in.mobile_app_admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gb_in.mobile_app_admin.presenter.Presenter;


public class ReferenceFragment extends Fragment{

    private Spinner refSpinner;
    private ListView refList;
    private Presenter presenter;
    private TextView listTitle;
    private ImageButton button;
    private boolean isSitesMode = false;

    public ReferenceFragment() {
        // Required empty public constructor
    }
    public void setPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reference, container, false);
        setComponents(view);
        return view;
    }

    private void setComponents(View view) {
        refList = view.findViewById(R.id.refList);
        refSpinner = view.findViewById(R.id.refSpinner);
        listTitle = view.findViewById(R.id.listTitle);

        refList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isSitesMode) {
                    Intent intent = new Intent(view.getContext(), AddictionActivity.class);
                    String clickedItem = adapterView.getItemAtPosition(i).toString();
                    intent.putExtra("name", clickedItem);
                    view.getContext().startActivity(intent);
                }
            }
        });

        refSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: presenter.loadPersonData();
                        listTitle.setText("Список персон");
                        isSitesMode = false;
                        break;
                    case 1: presenter.loadSiteData();
                        listTitle.setText("Список сайтов");
                        isSitesMode = true;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button = view.findViewById(R.id.addButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                //Получаем вид с файла prompt.xml, который применим для диалогового окна:
                LayoutInflater li = LayoutInflater.from(view.getContext());
                View promptsView = li.inflate(R.layout.add_dialog, null);
                TextView textView = promptsView.findViewById(R.id.tv);
                final int position = refSpinner.getSelectedItemPosition();
                switch (position){
                    case 0: presenter.loadPersonData();
                        textView.setText("Имя персоны");
                        break;
                    case 1: presenter.loadSiteData();
                        textView.setText("Наименование сайта");
                        break;
                }

                //Создаем AlertDialog
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(view.getContext());

                //Настраиваем prompt.xml для нашего AlertDialog:
                mDialogBuilder.setView(promptsView);

                //Настраиваем отображение поля для ввода текста в открытом диалоге:
                final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);

                //Настраиваем сообщение в диалоговом окне:
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Добавить",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //Вводим текст и отображаем в строке ввода на основном экране:
                                        if (position == 0) {
                                            presenter.addPersonData(userInput.getText().toString());
                                            presenter.loadPersonData();
                                        } else {
                                            presenter.addSiteData(userInput.getText().toString());
                                            presenter.loadSiteData();
                                        }
                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                //Создаем AlertDialog:
                AlertDialog alertDialog = mDialogBuilder.create();

                //и отображаем его:
                alertDialog.show();

            }
        });

    }

    public void updateListAdapter(String[] adapter){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,adapter);
        refList.setAdapter(arrayAdapter);
    }
}
