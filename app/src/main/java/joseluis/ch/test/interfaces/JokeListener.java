package joseluis.ch.test.interfaces;

import joseluis.ch.test.entities.Joke;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeListener {

    @GET("/jokes/random")
    Call<Joke> get();

}
