package am.newway.lesson4.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import am.newway.lesson4.R;
import am.newway.lesson4.enums.TaskType;

public class SecondActivity extends AppCompatActivity
{
    private final int BROWSEPICTURE = 1;
    private ImageView imageView;
    private EditText textName;
    private EditText textDescription;
    private RadioGroup radioButtonGroup;
    private Uri uriPicture;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_second );

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
            getSupportActionBar().setTitle( "Add NEW" );
        }

        imageView = findViewById( R.id.image );
        textName = findViewById( R.id.edit_name );
        textDescription = findViewById( R.id.edit_description );
        radioButtonGroup = findViewById( R.id.radioGroup );
        uriPicture = null;

        findViewById( R.id.bntBrowse ).setOnClickListener( new View.OnClickListener() {
            @Override public void onClick( View v )
            {
                uriPicture = null;
                browsePicture();
            }
        } );
        findViewById( R.id.floatingSecondButton ).setOnClickListener( new View.OnClickListener() {
            @Override public void onClick( View v )
            {
                sendData();
            }
        } );

        textDescription.setOnEditorActionListener( new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction( TextView v , int actionId , KeyEvent event )
            {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    sendData();
                }
                return false;
            }
        } );
    }

    private void browsePicture()
    {
        Intent intent = new Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult( intent,  BROWSEPICTURE);
    }

    private void sendData()
    {
        String strName = textName.getText().toString();
        String strDescription = textDescription.getText().toString();

        if (strName.isEmpty() || strDescription.isEmpty() || uriPicture == null)
        {
            Toast.makeText( SecondActivity.this , "Please input all values" , Toast.LENGTH_LONG ).show();
            return;
        }

        TaskType nType = null;
        int nID = radioButtonGroup.getCheckedRadioButtonId();
        switch ( nID )
        {
            case R.id.radioButton:
                nType = TaskType.IMP;
                break;
            case R.id.radioButton2:
                nType = TaskType.ORD;
                break;
            case R.id.radioButton3:
                nType = TaskType.UNI;
                break;
        }

        if (nType != null)
        {
            Bundle bnd = new Bundle();
            bnd.putString( "name" , strName );
            bnd.putString( "desc" , strDescription );
            bnd.putString( "type" , nType.toString() );
            bnd.putString( "uri" , uriPicture.toString() );

            Intent i = new Intent(  );
            i.putExtras(  bnd );
            setResult( RESULT_OK, i);
            finishAfterTransition();
        }
    }

    @Override protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data )
    {
        super.onActivityResult( requestCode , resultCode , data );

        if(requestCode == BROWSEPICTURE && resultCode == RESULT_OK && data != null)
        {
            uriPicture =  data.getData();
            if(uriPicture != null)
                imageView.setImageURI(uriPicture);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
