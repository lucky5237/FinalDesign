package zjut.jianlu.breakfast.sevice;

import retrofit2.Call;
import retrofit2.http.GET;
import zjut.jianlu.breakfast.entity.Book;

/**
 * Created by jianlu on 16/3/8.
 */
public interface githubService {

    @GET("/example")
    Call<Book> getBook();

}
