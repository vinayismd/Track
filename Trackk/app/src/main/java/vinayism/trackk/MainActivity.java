package vinayism.trackk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button b1 ,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.button);
        b2 = (Button)findViewById(R.id.button2);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.button:{
               Intent i = new Intent(getApplicationContext(), Shuttle1.class );
                startActivity(i);

            }
            case R.id.button2:{
                Toast.makeText(getApplicationContext(),"WOrking on It",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
