package com.michen.olaxueyuan.ui.me.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.michen.olaxueyuan.protocol.manager.SERestManager;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.service.SEUserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

public class EvaluationActivity extends SEBaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        setTitleText("蜗牛评测");
        webView = (WebView) findViewById(R.id.evaluationWeb);
        evaluation();
    }

    private void evaluation() {
        SEUserService userService = SERestManager.getInstance().create(SEUserService.class);
        userService.evaluation(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                TypedInput body = response.getBody();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()));
                    StringBuilder out = new StringBuilder();
                    String newLine = System.getProperty("line.separator");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                        out.append(newLine);
                    }
                    String summary = out.toString();
                    webView.loadDataWithBaseURL(null, summary, "text/html", "UTF-8", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
