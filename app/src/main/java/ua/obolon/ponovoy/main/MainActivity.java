package ua.obolon.ponovoy.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ua.in.magentocaller.R;
import ua.obolon.ponovoy.dao.ResoursSaverImpl;
import ua.obolon.ponovoy.interfaces.ResoursSaver;
import ua.obolon.ponovoy.res.AppKeys;

public class MainActivity extends AppCompatActivity {

    EditText server;
    EditText port;
    EditText login;
    EditText password;
    ResoursSaver rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rs = new ResoursSaverImpl(MainActivity.this);
        server = (EditText) findViewById(R.id.editTextServer);
        port = (EditText) findViewById(R.id.editTextPort);
        login = (EditText) findViewById(R.id.editTextLogin);
        password = (EditText) findViewById(R.id.editTextPassword);

        server.setText(rs.ReadValue(AppKeys.SERVER));
        port.setText(rs.ReadValue(AppKeys.PORT));
        login.setText(rs.ReadValue(AppKeys.LOGIN));
        password.setText(rs.ReadValue(AppKeys.PASSWORD));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rs.SaveValue(AppKeys.SERVER, server.getText().toString());
                rs.SaveValue(AppKeys.PORT, port.getText().toString());
                rs.SaveValue(AppKeys.LOGIN, login.getText().toString());
                rs.SaveValue(AppKeys.PASSWORD, password.getText().toString());
                Toast tst = Toast.makeText(MainActivity.this,"All Saved", Toast.LENGTH_SHORT);
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
