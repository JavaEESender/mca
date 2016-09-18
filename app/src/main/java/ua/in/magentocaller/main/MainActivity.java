package ua.in.magentocaller.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ua.in.magentocaller.R;
import ua.in.magentocaller.dao.ResoursSaverImpl;
import ua.in.magentocaller.interfaces.ResoursSaver;
import ua.obolon.ponovoy.res.AppKeys;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResoursSaver rs = new ResoursSaverImpl(MainActivity.this);
                rs.SaveValue(AppKeys.SERVER, "10.0.74.100");
                rs.SaveValue(AppKeys.PORT, "7878");
                rs.SaveValue(AppKeys.USER, "alexandr");
                rs.SaveValue(AppKeys.PASSWORD, "123456");
                Toast tst = Toast.makeText(MainActivity.this,"it's ok", Toast.LENGTH_SHORT);
                tst.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
