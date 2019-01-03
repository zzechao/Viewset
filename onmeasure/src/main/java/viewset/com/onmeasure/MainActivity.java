package viewset.com.onmeasure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flowLayout = findViewById(R.id.fl);
        flowLayout.setStrs("哒哒哒哒", "发他太尴尬放豆腐","哒哒哒哒", "发他太尴尬放豆腐","哒哒哒哒", "发他太尴尬放豆腐");
    }
}
