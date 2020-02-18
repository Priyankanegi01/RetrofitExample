package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofit.Model.Get;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult=findViewById(R.id.text_view_result);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AppService appService=retrofit.create(AppService.class);
        Call<List<Get>> call=appService.getData();

        //not call.excecute as this will run synchronusly at whatever thread we are on..
        //enqueue will run this on background thread..

        call.enqueue(new Callback<List<Get>>() {
            @Override
            public void onResponse(Call<List<Get>> call, Response<List<Get>> response) {

                if (!response.isSuccessful()){
                    textViewResult.setText("code:"+response.code());  //response.code is the http code that we get
                    return;
                }
                //data that we want to get from the web service
                List<Get> getlist=response.body();
                //for each get append the data to the textview
                for (Get modeldata: getlist){
                    String content="";
                    content +="ID:" + modeldata.getId() + "\n";
                    content +="User ID:" + modeldata.getUserId() +"\n";
                            content += "Title" +modeldata.getTitle() +"\n";
                                    content += "Text" +modeldata.getText() +"\n\n";     //double backslash n for next object


                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Get>> call, Throwable t) {
              textViewResult.setText(t.getMessage());
            }
        });
    }
}
