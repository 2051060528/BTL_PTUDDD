package slide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.exam.R;

import java.util.ArrayList;

import question.Question;

public class checkAnswerAdapter extends BaseAdapter {
    ArrayList lsData;
    LayoutInflater layoutInflater;

    public checkAnswerAdapter(ArrayList lsData, Context context) {
        this.lsData = lsData;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lsData.size(); //kích thước
    }

    @Override
    public Object getItem(int position) {
        return lsData.get(position); //lấy vị trí bao nhiêu
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question data = (Question) getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_gridview_list_answer, null);
            viewHolder.tvNumAns = (TextView) convertView.findViewById(R.id.tvNumAnswer);
            viewHolder.tvYourAns = (TextView) convertView.findViewById(R.id.tvAnswer);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int i = position + 1; //ở đây i là câu tl, còn postion từ 0 nên phải thêm +1
        viewHolder.tvNumAns.setText("Câu "+i+": ");
        viewHolder.tvYourAns.setText(data.getAnswer());
        return convertView;
    }

    private static class ViewHolder{
        TextView tvNumAns, tvYourAns;
    }
}
