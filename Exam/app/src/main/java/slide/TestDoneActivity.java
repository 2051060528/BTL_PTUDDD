package slide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam.R;

import java.util.ArrayList;

import question.Question;
import score.ScoreController;

public class TestDoneActivity extends AppCompatActivity {
    ArrayList<Question> arr_QuesBegin = new ArrayList<Question>();
    int numNoAns=0;
    int numTrue=0;
    int numFalse=0;
    int totalScore=0;
    ScoreController scoreController;
    TextView tvTrue, tvFalse, tvNotAns, tvTotalSore;
    Button btnSave, btnExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done);
        scoreController = new ScoreController(TestDoneActivity.this);

        Intent intent = getIntent();
        arr_QuesBegin = (ArrayList<Question>) intent.getExtras().getSerializable("arr_Question");
        begin();
        checkResult();
        totalScore= numTrue*1;
        tvNotAns.setText(""+numNoAns);
        tvFalse.setText(""+numFalse);
        tvTrue.setText(""+numTrue);
        tvTotalSore.setText(""+totalScore);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                builder.setIcon(R.drawable.baseline_exit_to_app_24);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(TestDoneActivity.this);
                LayoutInflater inflater=TestDoneActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.alert_dialog_save_score,null);
                builder.setView(view);

                final EditText edtName= (EditText) view.findViewById(R.id.edtName);
                final EditText edtRoom= (EditText) view.findViewById(R.id.edtRoom);
                TextView tvScore= (TextView) view.findViewById(R.id.tvScore);
                final int numTotal= numTrue*1;
                tvScore.setText(numTotal+" điểm");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name= edtName.getText().toString();
                        String room= edtRoom.getText().toString();
                        scoreController.insertScore(name,numTotal,room);
                        Toast.makeText(TestDoneActivity.this, "Lưu điểm thành công!",Toast.LENGTH_LONG).show();
                        finish();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog b= builder.create();
                b.show();
            }
        });
    }

    public void begin(){
        tvFalse = (TextView) findViewById(R.id.tvFalse);
        tvTrue = (TextView) findViewById(R.id.tvTrue);
        tvNotAns = (TextView) findViewById(R.id.tvNotAns);
        tvTotalSore= (TextView)findViewById(R.id.tvTotalPoint);
        btnSave = (Button) findViewById(R.id.btnSaveScore);
        btnExit = (Button) findViewById(R.id.btnExit);
    }
    //Phương thức check kết quả
    public void checkResult(){
        for(int i=0; i<arr_QuesBegin.size(); i++){
            if(arr_QuesBegin.get(i).getAnswer().equals("")==true){
                numNoAns++;
            } else if (arr_QuesBegin.get(i).getResult().equals(arr_QuesBegin.get(i).getAnswer())==true) {
                numTrue++;
            } else numFalse++;
        }
    }
}