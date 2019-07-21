package am.newway.lesson4.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import am.newway.lesson4.adapter.BasicRecyclerAdapter;
import am.newway.lesson4.R;
import am.newway.lesson4.data.Tasks;

public class MainActivity extends AppCompatActivity
{
    private BasicRecyclerAdapter mAdapter;

    @Override protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data )
    {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            Bundle bnd = data.getExtras();
            if (bnd != null)
            {
                mAdapter.addTasks( new Tasks( bnd.getString( "name" ) ,
                        bnd.getString( "desc" ) , Uri.parse( bnd.getString( "uri" ) ) , bnd.getString( "type" ) ) );
            }
        }
        super.onActivityResult( requestCode , resultCode , data );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        RecyclerView recycler = findViewById( R.id.recycler );

        RecyclerView.LayoutManager mManager = new LinearLayoutManager( this );
        recycler.setLayoutManager( mManager );

        mAdapter = new BasicRecyclerAdapter( new ArrayList<Tasks>() );

        recycler.setAdapter( mAdapter );

        findViewById( R.id.floatButton ).setOnClickListener( new View.OnClickListener()
        {
            @Override public void onClick( View v )
            {
                startActivityForResult( new Intent( MainActivity.this ,
                        SecondActivity.class ) , 1 );
            }
        } );


//        Glide.with(this)
//                .load("http://www.helloandroid.com/files/mobileplugin/180x180/d999d8b364dbe5b4c74432b5b57b519b.jpg")
//                .into(firstImage);
//
//        Picasso.get()
//                .load("http://www.helloandroid.com/files/mobileplugin/180x180/d999d8b364dbe5b4c74432b5b57b519b.jpg")
//                .into(lastImage);
    }
}
