package info.pauek.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class MovieActivity extends AppCompatActivity
{

    private Movie movie;
    private Gson gson;
    private TextView titleview;
    private TextView yearView;
    private TextView ratedview;
    private TextView runtimeview;
    private TextView genreview;
    private TextView plotview;
    private TextView directorview;
    private TextView writerview;
    private TextView actorsview;
    private ImageView posterview;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        gson = new Gson();
        queue = Volley.newRequestQueue(this);

        titleview = findViewById(R.id.titleview);
        yearView = findViewById(R.id.yearview);
        ratedview = findViewById(R.id.ratedview);
        runtimeview = findViewById(R.id.runtimeview);
        genreview = findViewById(R.id.genreview);
        plotview = findViewById(R.id.plotview);
        directorview = findViewById(R.id.directorview);
        writerview = findViewById(R.id.writerview);
        actorsview = findViewById(R.id.actorsview);
        posterview = findViewById(R.id.posterview);

        StringRequest request = new StringRequest(Request.Method.GET,
                "https://www.omdbapi.com/?t=infinity+war&plot=full&apikey=fdb822b6",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        movie = gson.fromJson(response, Movie.class);
                        UpdateMovie();
                        Glide.with(MovieActivity.this)
                                .load(movie.getPoster())
                                .into(posterview);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
    }

    private void UpdateMovie() {
        titleview.setText(movie.getTitle());
        yearView.setText(movie.getYear());
        ratedview.setText(movie.getRated());
        runtimeview.setText(movie.getRuntime());
        genreview.setText(movie.getGenre());
        plotview.setText(movie.getPlot());
        directorview.setText(movie.getDirector());
        writerview.setText(movie.getWriter());
        actorsview.setText(movie.getActors().replace(",", "\n"));
    }
}
