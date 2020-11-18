package ddwucom.mobile.mydiaryproject;
/*
 과제명 : 다이어리 정보 관리 앱
 분반 : 01분반
 학번: 20181028  성명: 한서희
 제출일 : 2020.07.03
 */

/*
<가산점 항목>
1. 검색기능
 옵션메뉴에서 검색 아이콘을 누르면 검색화면으로 이동
 날짜, 제목, 장소, (날짜or제목or장소)를 기준으로 검색할 수 있음. 검색 결과가 없으면 TOAST로 안내
검색결과가 있으면 해당하는 다이어리가 리스트로 보임.

2. 위젯 사용
1) scrollView 이용
2) DatePickerDialog 이용해서 날짜 선택하도록 함
3) 다이어리 추가화면에서 spinner를 이용해서 날짜와 카테고리 선택
4) RadioButton을 이용해서 기분상태를 고르게 함
5) ratingBar를 이용해서 하루 시간관리 평가

3. 기본 기능 이외의 추가적인 기능
1) 리스트 뷰 항목에 이미지 사용 기능
2) 다이어리 추가화면에서 이미지를 누르면 대화상자가 나오고 '수정'과 '취소'중에서 고를 수 있음.
수정을 누르면 이미지를 수정할 수 있는 화면으로 이동, 이동된 화면에서 사진을 선택하면 사진이 수정됨.
취소를 누르면 사진수정 취소(초기 이미지는 res에 있는 이미지들을 랜덤함수를 이용해서 지정)
3) 메인화면에서 STATISTICS 버튼을 누르면 기분과 카테고리, 시간효율을 통계낸 화면으로 넘어감
(DiaryDBManager에 관련 함수 생성해 이용, 시간효율은 ratingBar를 이용해 나타냄)
 */
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Diary> diaryArrayList;
    private MyAdapter myAdapter;
    private android.widget.ListView listView;
    final int UPDATE_CODE = 100;
    final int ADD_CODE =200;
    Diary t;

    DiaryDBManager diaryDBManager;


    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diaryArrayList = new ArrayList<Diary>();

        myAdapter = new MyAdapter(this,R.layout.custom_adapter,diaryArrayList);
        listView = (android.widget.ListView)findViewById(R.id.main_listview);

        listView.setAdapter(myAdapter);

        diaryDBManager = new DiaryDBManager(this);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_title)
                        .setMessage(diaryArrayList.get(pos).getTitle() +" 다이어리를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (diaryDBManager.removeDiary(diaryArrayList.get(pos).get_id())){
                                    Toast.makeText(MainActivity.this, "삭제완료", Toast.LENGTH_SHORT).show();
                                    diaryArrayList.clear();
                                    diaryArrayList.addAll(diaryDBManager.getAllDiary());
                                    myAdapter.notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel,null)
                        .setCancelable(false)
                        .show();

                return true;


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                final int pos = position;
                t = diaryDBManager.getDiary(diaryArrayList.get(pos).get_id());
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("update_id", String.valueOf(diaryArrayList.get(pos).get_id()));
                startActivityForResult(intent,UPDATE_CODE);


            }


        });

    }
    protected void onResume() {
        super.onResume();
        diaryArrayList.clear();
        diaryArrayList.addAll(diaryDBManager.getAllDiary());
        myAdapter.notifyDataSetChanged();

    }
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public void onClick(android.view.View view){
        switch(view.getId()){
            case R.id.btn_statistics:
                Intent intent = new Intent(this, statisticsActivity.class);
                startActivity(intent);
                break;
        }

    }


    public boolean onOptionsItemSelected (@androidx.annotation.NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()) {
            case R.id.btn_addmenu:
                intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, ADD_CODE);

                break;
            case R.id.btn_close:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_close_title)
                        .setMessage(R.string.dialog_close_message)
                        .setPositiveButton(R.string.dialog_close_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel,null)
                        .setCancelable(false)
                        .show();
                break;
            case R.id.search:
                intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this,  "수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "다이어리 수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        else if(requestCode == ADD_CODE){
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this,  "추가 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "다이어리 추가 취소", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

}