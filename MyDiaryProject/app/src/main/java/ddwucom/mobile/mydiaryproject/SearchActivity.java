package ddwucom.mobile.mydiaryproject;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText want;
    Button button;
    RadioGroup radioGroup;
    int want_search = 0;
    DiaryDBManager diaryDBManager;
    Diary t;

    private ArrayList<Diary> diaryArrayList;
    private ArrayList<Diary> searchList;
    private MyAdapter myAdapter;
    private android.widget.ListView listView;

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        diaryDBManager = new DiaryDBManager(this);

        diaryArrayList = new ArrayList<Diary>();

        myAdapter = new MyAdapter(this,R.layout.custom_adapter,diaryArrayList);
        listView = (android.widget.ListView)findViewById(R.id.searchlist);

        listView.setAdapter(myAdapter);

        want = (EditText)findViewById(R.id.etsearch);

        button = (Button)findViewById(R.id.btn_search);
        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String text = want.getText().toString();
                if (!text.equals("")){
                    if (want_search == 0){
                        searchList = diaryDBManager.searchByDate(text);
                        if (searchList.isEmpty()) {
                            Toast.makeText(SearchActivity.this, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                            updateSearchList(searchList);

                    }
                    else if(want_search==1){
                        searchList = diaryDBManager.searchByTitle(text);
                        if (searchList.isEmpty()){
                            Toast.makeText(SearchActivity.this,"검색결과가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                            updateSearchList(searchList);
                    }
                    else if(want_search==2){
                        searchList = diaryDBManager.searchAll(text);
                        if (searchList.isEmpty()){
                            Toast.makeText(SearchActivity.this,"검색결과가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                            updateSearchList(searchList);
                    }
                    else if(want_search==3){
                        searchList = diaryDBManager.searchByPlace(text);
                        if (searchList.isEmpty()){
                            Toast.makeText(SearchActivity.this,"검색결과가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                        updateSearchList(searchList);
                    }

                }
                else{
                    Toast.makeText(SearchActivity.this,"검색어를 입력하시오", Toast.LENGTH_SHORT).show();
                }

            }
        });

        radioGroup = (RadioGroup)findViewById(R.id.search_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.searchdate){
                    want_search = 0;
                }
                else if(checkedId == R.id.searchtitle){
                    want_search = 1;
                }
                else if (checkedId == R.id.searchall){
                    want_search = 2;
                }
                else if (checkedId == R.id.searchplace){
                    want_search = 3;
                }

            }
        });


    }
    public void updateSearchList(ArrayList searchList){
        diaryArrayList.clear();
        diaryArrayList.addAll(searchList);
        myAdapter.notifyDataSetChanged();

    }

    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.search_info,menu);
        return true;
    }
    public boolean onOptionsItemSelected (@androidx.annotation.NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.btn_back:
                finish();
                break;

        }

        return true;
    }

}
