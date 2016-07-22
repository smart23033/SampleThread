package example.tacademy.com.samplethread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView messageView;
    ProgressBar downloadView;

    public static final int MESSAGE_PROGRESS = 1;
    public static final int MESSAGE_DONE = 2;

    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_PROGRESS:
                    int progress = msg.arg1;
                    downloadView.setProgress(progress);
                    messageView.setText("progress : " + progress);
                    break;
                case MESSAGE_DONE:
                    downloadView.setProgress(100);
                    messageView.setText("progress done");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageView = (TextView)findViewById(R.id.text_message);
        downloadView = (ProgressBar)findViewById(R.id.progress_download);

        Button btn = (Button)findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadView.setMax(100);
                messageView.setText("download start...");
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        int progress = 0 ;
                        while(progress <= 100){
                            Message msg = mHandler.obtainMessage(MESSAGE_PROGRESS,progress,0);
                            mHandler.sendMessage(msg);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progress += 5;
                        }

                        mHandler.sendEmptyMessage(MESSAGE_DONE);
                    }
                }).start();
            }
        });
    }
}
