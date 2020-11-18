package ddwucom.mobile.mydiaryproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    String feeling = "happy";
    String[] item_picture;
    String picture_index;
    String timerating = "3";

    private RatingBar ratingBar;

    int y, m, d;
    DiaryDBManager diaryDBManager;

    final int PICTURE_CODE = 300;


    protected void onCreate(android.os.Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_diary);

        item = getResources().getStringArray(R.array.category);

        diaryDBManager = new DiaryDBManager(this);


        ettitle = findViewById(R.id.ettitle);
        etcontent = findViewById(R.id.etcontent);
        etplace = findViewById(R.id.etplace);
        tvcategory = findViewById(R.id.tvcategory);
        tvweather = findViewById(R.id.tvweather);
        imageView = findViewById(R.id.imgView);


        item_weather = getResources().getStringArray(R.array.weather);
        spinner = (android.widget.Spinner) findViewById(R.id.spinner);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        spinner_weather = (android.widget.Spinner) findViewById(R.id.spinner_weather);
        spinner_weather.setSelection(0);
        spinner_weather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                tvweather.setText(item_weather[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvweather.setText(item_weather[0]);
            }

        });


        tvdate = (android.widget.TextView) findViewById(R.id.tv_date);
        tvdate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        feeling_group = (RadioGroup) findViewById(R.id.feeling_group);
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

        ratingBar = findViewById(R.id.time_rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                timerating = Float.toString(rating);

            }
        });

        Random random = new Random();
        int i = random.nextInt(6);
        showImage(i);
        picture_index = String.valueOf(i);

        imageView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle(R.string.dialog_picture_title)
                        .setMessage(R.string.dialog_picture_message)
                        .setPositiveButton(R.string.dialog_picture_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AddActivity.this, selectPictureActivity.class);
                                startActivityForResult(intent, PICTURE_CODE);
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel,null)
                        .setCancelable(false)
                        .show();


            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
        tvcategory.setText(item[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        tvcategory.setText(item[0]);
    }


    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if ((!tvdate.getText().toString().equals("")) &&  (!ettitle.getText().toString().equals(""))
                        && (!etcontent.getText().toString().equals("")) && (!etplace.getText().toString().equals(""))) {
                    boolean result = diaryDBManager.addNewDiary(
                            new Diary(tvdate.getText().toString(), ettitle.getText().toString(), etcontent.getText().toString(),
                                    picture_index, etplace.getText().toString(), tvweather.getText().toString(), feeling, tvcategory.getText().toString(), timerating)

                    );
                    if (result) {
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(this, "새로운 음식 추가 실패!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "입력이 안된 항목이 있습니다!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
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


}
