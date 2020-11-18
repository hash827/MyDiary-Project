package ddwucom.mobile.mydiaryproject;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class selectPictureActivity extends AppCompatActivity {

    String index;

    protected void onCreate(android.os.Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_picture);
    }

    public void onClick(android.view.View v){
        switch(v.getId()){
            case R.id.btn_select_cancel:
                setResult(RESULT_CANCELED);
                break;
            default:
                Intent resultIntent = new Intent();
                index = findPictureId(v.getId());
                resultIntent.putExtra("index",index);
                setResult(RESULT_OK,resultIntent);
                break;
        }
        finish();


    }

    public String findPictureId(int id){
        String index="";
        switch(id){
            case R.id.img_sky1:
                index = "0";
                break;
            case R.id.img_sky2:
                index= "1";
                break;
            case R.id.img_sky3:
                index="2";
                break;
            case R.id.img_sky4:
                index="3";
                break;
            case R.id.img_sky5:
                index="4";
                break;
            case R.id.img_sky6:
                index="5";
                break;

        }
        return index;
    }
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.select_picture_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected (@androidx.annotation.NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.btn_back:
                AlertDialog.Builder builder = new AlertDialog.Builder(selectPictureActivity.this);
                builder.setTitle(R.string.dialog_info_title)
                        .setMessage(R.string.select_info)
                        .setPositiveButton(R.string.dialog_info_ok, null)
                        .show();
                break;
        }
        return true;
    }
}