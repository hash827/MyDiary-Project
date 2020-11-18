package ddwucom.mobile.mydiaryproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Diary> diaryArrayList;
    private LayoutInflater layoutInflater;


    public MyAdapter(Context context, int layout, ArrayList<Diary> diaryArrayList){
        this.context = context;
        this.layout = layout;
        this.diaryArrayList = diaryArrayList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return diaryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return diaryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return diaryArrayList.get(position).get_id();
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
        final int pos = position;
        ViewHolder holder;

        if (convertView == null){
            convertView = layoutInflater.inflate(layout,parent, false);

            holder = new ViewHolder();
            holder.tvtitle = (android.widget.TextView)convertView.findViewById(R.id.tvtitle);
            holder.tvdate = (android.widget.TextView)convertView.findViewById(R.id.tv_date);
            holder.tvcontent = (android.widget.TextView)convertView.findViewById(R.id.tvcontent);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.tvweather = (android.widget.TextView)convertView.findViewById(R.id.tv_weather);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvtitle.setText(diaryArrayList.get(pos).getTitle());
        holder.tvcontent.setText(diaryArrayList.get(pos).getContent());
        holder.tvdate.setText(diaryArrayList.get(pos).getDate());
        holder.tvweather.setText(diaryArrayList.get(pos).getWeather());


        int i = Integer.parseInt(diaryArrayList.get(pos).getPicture());
        if (i == 0){
            holder.imageView.setImageResource(R.drawable.sky1);
        }
        else if(i == 1){
            holder.imageView.setImageResource(R.drawable.sky2);
        }
        else if(i == 2){
            holder.imageView.setImageResource(R.drawable.sky3);
        }
        else if(i == 3){
            holder.imageView.setImageResource(R.drawable.sky4);
        }
        else if(i == 4){
            holder.imageView.setImageResource(R.drawable.sky5);
        }
        else if(i == 5){
            holder.imageView.setImageResource(R.drawable.sky6);
        }

        return convertView;


    }
    static class ViewHolder{
        android.widget.TextView tvtitle;
        android.widget.TextView tvcontent;
        android.widget.TextView tvdate;
        ImageView imageView;
        android.widget.TextView tvweather;

    }

}

