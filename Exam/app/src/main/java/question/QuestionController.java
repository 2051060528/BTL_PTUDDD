package question;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class QuestionController {
    private DBHelper dbHelper;
    public QuestionController(Context context){
        dbHelper = new DBHelper(context);
    }
    //Viết 1 phương thức để xử lý CSDL = cách chạy câu lệnh truy vấn
    public ArrayList<Question> getQuestion(int num_exam, String subject){
        ArrayList<Question> lsData = new ArrayList<Question>();
        SQLiteDatabase db = dbHelper.getReadableDatabase(); //chỉ đọc CSDL
        Cursor cursor = db.rawQuery("SELECT * FROM tracnghiem WHERE num_exam = '"+num_exam+"' AND subject = '"+subject+"'",null); //chạy câu lệnh SQL
        cursor.moveToFirst(); //Duyệt từ dòng đầu tiên của bảng trên
        //lấy ra từng dòng gán giá trị vào mảng lsData
        do{
            Question item;
            item = new Question(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getString(9),"");
            lsData.add(item);
        }while (cursor.moveToNext());
        return lsData; //trả về 1 mảng danh sách câu hỏi
    }

    //Lấy ra danh sách câu hỏi
    public Cursor getSearchQuestion(String subject, String key){
        SQLiteDatabase db = dbHelper.getReadableDatabase(); //chỉ đọc CSDL
        Cursor cursor = db.rawQuery("SELECT * FROM tracnghiem WHERE question LIKE '%"+key+"%' AND subject LIKE '%"+subject+"%'",null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor; //trả về 1 mảng danh sách câu hỏi
    }
    public Cursor getQuestionBySubject(String key) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM tracnghiem WHERE subject LIKE '%"+key+"%'",null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
