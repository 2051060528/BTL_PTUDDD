package slide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.exam.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import question.Question;
import question.QuestionController;

public class ScreenSlideActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 10; //cho chạy 10 trang định nghĩa số trang

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    TextView tvKiemtra, tvTimer, tvXemdiem;
    public int checkAns=0;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;
    //CSDL
    QuestionController questionController;
    ArrayList<Question> arr_Question;
    CounterClass timer;
    String subject;
    int num_exam;
    int totalTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        num_exam = intent.getIntExtra("num_exam",0);

        totalTimer=10;
        timer = new CounterClass(totalTimer*60*1000, 1000);
        questionController = new QuestionController(this);
        arr_Question = new ArrayList<>();
        arr_Question = questionController.getQuestion(num_exam,subject);
        tvKiemtra = (TextView) findViewById(R.id.tvKiemTra);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvXemdiem = (TextView) findViewById(R.id.tvXemdiem);

        tvKiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvXemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent(ScreenSlideActivity.this, TestDoneActivity.class);
                intent1.putExtra("arr_Question", arr_Question);
                startActivity(intent1);
            }
        });
        timer.start();
    }

    public ArrayList<Question> getData(){
        //return giá trị trả về là 1 mảng
        return arr_Question;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            dialogExit();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void dialogExit(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ScreenSlideActivity.this);
        builder.setIcon(R.drawable.baseline_exit_to_app_24);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn thoát hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.show();
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return ScreenSlidePageFragment.screenSlidePageFragment(position, checkAns);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    public void checkAnswer(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_answer_dialog);

        checkAnswerAdapter answerAdapter = new checkAnswerAdapter(arr_Question,this);
        GridView gvlsQuestion = (GridView) dialog.findViewById(R.id.gvLsQuestion);
        gvlsQuestion.setAdapter(answerAdapter);

        gvlsQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPager.setCurrentItem(position);
                dialog.dismiss(); //tắt cái dialog đi
            }
        });

        Button btnCancel, btnFinish;
        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnFinish = (Button) dialog.findViewById(R.id.btnFinish);
        //bắt sự kiện
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel(); //tắt tgian chạy
                result();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void result(){
        checkAns=1;
        if(mPager.getCurrentItem() >= 5) mPager.setCurrentItem(mPager.getCurrentItem()-4);
        else if (mPager.getCurrentItem() <= 5) mPager.setCurrentItem(mPager.getCurrentItem()+4);
        tvXemdiem.setVisibility(View.VISIBLE);
        tvKiemtra.setVisibility(View.GONE);
    }

    public class CounterClass extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */


        //millisInFuture: 60*1000
        //countDownInterval:  1000
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");  //SetText cho textview hiện thị thời gian.
        }
    }
}