package slide;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam.R;

import java.util.ArrayList;

import question.Question;

public class ScreenSlidePageFragment extends Fragment {
    ArrayList<Question> arr_Question;
    public static final String ARG_PAGE ="page";
    public static final String ARG_CHECKANSWER ="checkAnswer";
    private int mPageNumber; //vị trí trang hiện tại
    public int checkAns; //biến kiểm tra
    TextView tvNum, tvQuestion;
    RadioGroup radGroup;
    RadioButton radA, radB, radC, radD;
    ImageView imgIcon;

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        tvNum = (TextView) rootView.findViewById(R.id.tvNum);
        tvQuestion = (TextView) rootView.findViewById(R.id.tvQuestion);
        radGroup = (RadioGroup) rootView.findViewById(R.id.radGroup);
        radA = (RadioButton) rootView.findViewById(R.id.radA);
        radB = (RadioButton) rootView.findViewById(R.id.radB);
        radC = (RadioButton) rootView.findViewById(R.id.radC);
        radD = (RadioButton) rootView.findViewById(R.id.radD);
        imgIcon = (ImageView) rootView.findViewById(R.id.ivIcon);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arr_Question = new ArrayList<Question>();
        ScreenSlideActivity screenSlideActivity = (ScreenSlideActivity) getActivity();
        arr_Question = screenSlideActivity.getData();
        mPageNumber = getArguments().getInt(ARG_PAGE);
        checkAns = getArguments().getInt(ARG_CHECKANSWER);
    }

    public static ScreenSlidePageFragment screenSlidePageFragment(int pageNumber, int checkAnswer){
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_CHECKANSWER, checkAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvNum.setText("Câu "+(mPageNumber+1));
        tvQuestion.setText(arr_Question.get(mPageNumber).getQuestion());
        radA.setText(getIem(mPageNumber).getAns_a());
        radB.setText(getIem(mPageNumber).getAns_b());
        radC.setText(getIem(mPageNumber).getAns_c());
        radD.setText(getIem(mPageNumber).getAns_d());
        imgIcon.setImageResource(getResources().getIdentifier(getIem(mPageNumber).getImage()+"","drawable","com.example.exam"));


        //Khi kết thúc không cho chọn nút radio nữa
        if(checkAns!=0){
            radA.setClickable(false);
            radB.setClickable(false);
            radC.setClickable(false);
            radD.setClickable(false);
            getCheckAns(getIem(mPageNumber).getResult().toString());
        }

        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getIem(mPageNumber).choiceID = checkedId;
                getIem(mPageNumber).setAnswer(getChoiceFromID(checkedId));
                //Toast.makeText(getActivity(), "Đây là đáp án "+ checkedId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Question getIem(int position){
        return arr_Question.get(position);
    }
    //Lấy giá trị (vị trí) radGroup chuyển thành đáp án A/B/C/D
    private String getChoiceFromID(int ID){
        if(ID == R.id.radA){
            return "A";
        } else if (ID == R.id.radB) {
            return "B";
        }else if (ID == R.id.radC) {
            return "C";
        }else if (ID == R.id.radD) {
            return "D";
        }
        else{
            return "";
        }
    }

    //Hàm kiểm tra câu đúng, nếu câu đúng thì đổi màu background radiobutton tương ứng
    private void getCheckAns(String ans){
        if(ans.equals("A")==true){
            radA.setBackgroundColor(Color.RED);
        } else if (ans.equals("B")==true) {
            radB.setBackgroundColor(Color.RED);
        }
        else if (ans.equals("C")==true) {
            radC.setBackgroundColor(Color.RED);
        }else if (ans.equals("D")==true) {
            radD.setBackgroundColor(Color.RED);
        }else;
    }
}