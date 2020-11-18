package ddwucom.mobile.mydiaryproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    DiaryDBManager diaryDBManager;
    android.widget.TextView tvdate;
    android.widget.TextView tvcategory;
    EditText ettitle;
    EditText etcontent;
    EditText etplace;
    android.widget.TextView tvweather;
    RadioGroup feeling_group;
    ImageView imageView;


    final int DIALOG_DATE = 1;

    android.widget.Spinner spinner;
    android.widget.Spinner spinner_weather;
    String[] item;
    String[] item_weather;
    String feeling;
    String[] item_picture;
    String picture_index;
    String timerating;
    RadioButton radioButton;

    String a,b;

    private RatingBar ratingBar;

    int y, m, d;

    Diary diary;

    final int PICTURE_CODE = 300;
    String update_id;

    protected void onCreate(android.os.Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_diary);


        Intent intent = getIntent();
        update_id = intent.getStringExtra("update_id");

        item = getResources().getStringArray(R.array.category);

        diaryDBManager = new DiaryDBManager(this);
        diary = diaryDBManager.getDiary(Long.parseLong(update_id));

        ettitle = (EditText)findViewById(R.id.update_title);
        etcontent = (EditText)findViewById(R.id.update_content);
        etplace = (EditText)findViewById(R.id.update_place);
        tvcategory = (android.widget.TextView)findViewById(R.id.update_category);
        tvweather = (android.widget.TextView)findViewById(R.id.update_weather);
        imageView = (ImageView)findViewById(R.id.update_imgView);
        tvdate = (android.widget.TextView)findViewById(R.id.update_date);

        a = diary.getCategory();
        b = diary.getWeather();

        ettitle.setText(diary.getTitle());
        etcontent.setText(diary.getContent());
        etplace.setText(diary.getPlace());
        tvdate.setText(diary.getDate());
        timerating = diary.getRating();
        picture_index = diary.getPicture();
        feeling = diary.getFeeling();
        showImage(Integer.parseInt(picture_index));

        spinner = (android.widget.Spinner) findViewById(R.id.update_spinner);
        spinner.setSelection(setCategory(diary.getCategory()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                tvcategory.setText(item[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        item_weather = getResources().getStringArray(R.array.weather);
        spinner_weather = (android.widget.Spinner) findViewById(R.id.spinner_update_weather);
        spinner_weather.setSelection(setWeather(diary.getWeather()));
        spinner_weather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                tvweather.setText(item_weather[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        tvdate = (android.widget.TextView) findViewById(R.id.update_date);
        tvdate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y = year;
                        m = month + 1;
                        d = dayOfMonth;
                        tvdate.setText(y + "." + m + "." + d);

                    }
                }, 2020, 6, 11);

                datePickerDialog.setMessage("메시지");
                datePickerDialog.show();
            }
        });


        feeling_group = (RadioGroup) findViewById(R.id.update_feeling_group);
        switch(diary.getFeeling()) {
            case "angry":
                radioButton = findViewById(R.id.btn_angry);
                radioButton.setChecked(true);
                break;
            case "happy":
                radioButton = findViewById(R.id.btn_happy);
                radioButton.setChecked(true);
                break;
            case "sad":
                radioButton = findViewById(R.id.btn_sad);
                radioButton.setChecked(true);
                break;
            case "love":
                radioButton = findViewById(R.id.btn_love);
                radioButton.setChecked(true);
                break;
            case "cry":
                radioButton = findViewById(R.id.btn_cry);
                radioButton.setChecked(true);
                break;

        }
        feeling_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn_angry) {
                    feeling = "angry";
                } else if (checkedId == R.id.btn_sad) {
                    feeling = "sad";
                } else if (checkedId == R.id.btn_happy) {
                    feeling = "happy";
                } else if (checkedId == R.id.btn_cry) {
                    feeling = "cry";
                } else if (checkedId == R.id.btn_love) {
                    feeling = "love";
                }
            }
        });


        ratingBar = findViewById(R.id.update_rating);
        ratingBar.setRating(Float.valueOf(timerating));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                timerating = Float.toString(rating);

            }
        });


        imageView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                builder.setTitle(R.string.dialog_picture_title)
                        .setMessage(R.string.dialog_picture_message)
                        .setPositiveButton(R.string.dialog_picture_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(UpdateActivity.this, selectPictureActivity.class);
                                startActivityForResult(intent, PICTURE_CODE);
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel,null)
                        .setCancelable(false)
                        .show();


            }
        });



    }
    public void showImage(int i) {
        if (i == 0) {
            imageView.setImageResource(R.drawable.sky1);
        } else if (i == 1) {
            imageView.setImageResource(R.drawable.sky2);
        } else if (i == 2) {
            imageView.setImageResource(R.drawable.sky3);
        } else if (i == 3) {
            imageView.setImageResource(R.drawable.sky4);
        } else if (i == 4) {
            imageView.setImageResource(R.drawable.sky5);
        } else if (i == 5) {
            imageView.setImageResource(R.drawable.sky6);
        }
    }



    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                if ((!tvdate.getText().toString().equals("")) &&  (!ettitle.getText().toString().equals(""))
                        && (!etcontent.getText().toString().equals("")) && (!etplace.getText().toString().equals(""))) {
                    boolean result = diaryDBManager.modifyDiary(
                            new Diary(Long.parseLong(update_id),tvdate.getText().toString(), ettitle.getText().toString(), etcontent.getText().toString(),
                                    picture_index, etplace.getText().toString(), tvweather.getText().toString(), feeling, tvcategory.getText().toString(), timerating)

                    );
                    if (result) {
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(this, "다이어리 수정 실패!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "입력이 안된 항목이 있습니다!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    picture_index = data.getStringExtra("index");
                    showImage(Integer.parseInt(picture_index));
                    Toast.makeText(this, " 사진 수정완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "사진 수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public int setCategory(String category){
        if (category.equals("일상")) {
            return 0;
        } else if (category.equals("연애")) {
            return 1;
        } else if (category.equals("공부")) {
            return 2;
        }else if (category.equals("교훈")) {
            return 3;
        }else if (category.equals("우정")) {
            return 4;
        }else if (category.equals("여행")) {
            return 5;
        }
        return 0;
    }
    public int setWeather(String weather){
        if (weather.equals("맑음")) {
            return 0;
        } else if (weather.equals("구름")) {
            return 1;
        } else if (weather.equals("비")) {
            return 2;
        }else if (weather.equals("눈")) {
            return 3;
        }else if (weather.equals("뇌우")) {
            return 4;
        }else if (weather.equals("바람")) {
            return 5;
        }
        return 0;
    }

}



