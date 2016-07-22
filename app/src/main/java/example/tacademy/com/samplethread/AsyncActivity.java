package example.tacademy.com.samplethread;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncActivity extends AppCompatActivity {

    TextView messageView;
    ProgressBar downloadView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        messageView = (TextView) findViewById(R.id.text_message);
        downloadView = (ProgressBar) findViewById(R.id.progress_download);

        Button btn = (Button) findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadTask().execute("zzz");
            }
        });
    }

    class DownloadTask extends AsyncTask<String,Integer,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            downloadView.setMax(100);
            messageView.setText("download start...");
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            int progress = 0;
            while(progress <= 100){
                publishProgress(progress);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress += 5;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            downloadView.setProgress(progress);
            messageView.setText("progress : " + progress);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            downloadView.setProgress(100);
            messageView.setText("progress done");
        }
    }


}
