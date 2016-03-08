package zjut.jianlu.breakfast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjut.jianlu.breakfast.entity.Book;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.button)Button button;
    @OnClick(R.id.button)
    public void query(View view){
        Book book =new Book("he3wr","212121");
        book.save();
        List<Book> books = Book.listAll(Book.class);
        Log.d("jianlu",books.size()+"");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Book book =new Book("ref","people edition");
//        book.save();
        ButterKnife.bind(this);

    }
}
