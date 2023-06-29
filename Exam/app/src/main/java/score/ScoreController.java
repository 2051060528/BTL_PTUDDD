package score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import question.DBHelper;
import question.Question;

public class ScoreController {
    private DBHelper dbHelper;
    public ScoreController(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertScore(String name, int score, String room){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("name", name); //"name":tên bảng, name: là đối số lấy ở trên hàng void
        values.put("score", score);
        values.put("room", room);
        db.insert("tbdiem", null, values);
        db.close();
    }

    //Lấy ra danh sách điểm
    public Cursor getScore(){
        SQLiteDatabase db = dbHelper.getReadableDatabase(); //chỉ đọc CSDL
        Cursor cursor = db.query("tbdiem", //tên bảng
                null, //danh sách cột cần lấy
                null, //Điều kiện Where
                null, //Đối số điều kiện Where
                null, //biểu thức Groupby
                null, //biểu thức Having
                "_id DESC", //Biểu thức Orderby
                null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor; //trả về 1 mảng danh sách câu hỏi
    }
}
