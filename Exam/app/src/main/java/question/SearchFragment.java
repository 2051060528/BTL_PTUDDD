package question;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.exam.MainActivity;
import com.example.exam.R;

public class SearchFragment extends Fragment {
    ListView lvQuestion;
    QuestionController questionController;
    QuestionAdapter adapter;
    EditText edtSearch;
    ImageButton imgSubject;
    String subject = "";
    public SearchFragment() {
        // Required empty public constructor
    }

    public void begin(){
        lvQuestion = (ListView) getActivity().findViewById(R.id.lvQuestion);
        edtSearch = (EditText) getActivity().findViewById(R.id.edtSearch);
        imgSubject=(ImageButton) getActivity().findViewById(R.id.imgSubject);
        questionController = new QuestionController(getActivity());
        listCursor(questionController.getSearchQuestion(subject,edtSearch.getText().toString()));
    }

    public void listCursor(Cursor cursor){
        adapter = new QuestionAdapter(getActivity(), cursor, true);
        lvQuestion.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Tìm kiếm");
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        begin();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listCursor(questionController.getSearchQuestion(subject,edtSearch.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }
    public void showMenu(View v){
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.questall) {
                    subject="";
                    listCursor(questionController.getQuestionBySubject(subject));
                }else if (itemId == R.id.qhoa) {
                    subject="hoa";
                    listCursor(questionController.getQuestionBySubject(subject));
                } else if (itemId == R.id.qtoan) {
                    subject="toan";
                    listCursor(questionController.getQuestionBySubject(subject));
                }else if (itemId == R.id.qsinh) {
                    subject="sinh";
                    listCursor(questionController.getQuestionBySubject(subject));
                }else if (itemId == R.id.qly) {
                    subject="ly";
                    listCursor(questionController.getQuestionBySubject(subject));
                }else if (itemId == R.id.qanh) {
                    subject="anh";
                    listCursor(questionController.getQuestionBySubject(subject));
                }
                return false;
            }
        });
        popupMenu.inflate(R.menu.menu_question);
        popupMenu.show();
    }
}