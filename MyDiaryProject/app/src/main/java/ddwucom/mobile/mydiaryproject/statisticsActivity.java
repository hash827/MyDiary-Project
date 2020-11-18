package ddwucom.mobile.mydiaryproject;

import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class statisticsActivity extends AppCompatActivity {
    DiaryDBManager diaryDBManager;
    int [] diaryArray;
    int [] categoryArray;


    android.widget.TextView happy;
    android.widget.TextView sad;
    android.widget.TextView cry;
    android.widget.TextView love;
    android.widget.TextView angry;
    android.widget.TextView total;

    android.widget.TextView c1;
    android.widget.TextView c2;
    android.widget.TextView c3;
    android.widget.TextView c4;
    android.widget.TextView c5;
    android.widget.TextView c6;
    android.widget.TextView t1;
    String time="";

    RatingBar ratingBar;

    protected void onCreate(android.os.Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        diaryDBManager = new DiaryDBManager(this);

        diaryArray = diaryDBManager.getFeelingSt();
        happy = (android.widget.TextView) findViewById(R.id.st1);
        sad = (android.widget.TextView) findViewById(R.id.st2);
        cry = (android.widget.TextView) findViewById(R.id.st3);
        angry = (android.widget.TextView) findViewById(R.id.st5);
        love = (android.widget.TextView) findViewById(R.id.st4);
        total = (android.widget.TextView) findViewById(R.id.sttotal);

        happy.setText(String.valueOf(diaryArray[0]));
        sad.setText(String.valueOf(diaryArray[1]));
        cry.setText(String.valueOf(diaryArray[2]));
        love.setText(String.valueOf(diaryArray[3]));
        angry.setText(String.valueOf(diaryArray[4]));
        total.setText(String.valueOf(diaryArray[5]));

        categoryArray = diaryDBManager.getCategorylistSt();
        c1 = (android.widget.TextView) findViewById(R.id.st_1);
        c2 = (android.widget.TextView) findViewById(R.id.st_2);
        c3 = (android.widget.TextView) findViewById(R.id.st_3);
        c4 = (android.widget.TextView) findViewById(R.id.st_4);
        c5 = (android.widget.TextView) findViewById(R.id.st_5);
        c6 = (android.widget.TextView) findViewById(R.id.st_6);


        c1.setText(String.valueOf(categoryArray[0]));
        c2.setText(String.valueOf(categoryArray[1]));
        c3.setText(String.valueOf(categoryArray[2]));
        c4.setText(String.valueOf(categoryArray[3]));
        c5.setText(String.valueOf(categoryArray[4]));
        c6.setText(String.valueOf(categoryArray[5]));

        time = diaryDBManager.getTimeRatingSt();

        ratingBar = (RatingBar)findViewById(R.id.rt);
        ratingBar.setRating(Float.parseFloat(time));
    }



}
