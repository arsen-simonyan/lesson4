package am.newway.lesson4.ui;

import am.newway.lesson4.R;
import am.newway.lesson4.adapter.MyPagerAdapter;
import am.newway.lesson4.data.Settings;
import am.newway.lesson4.data.Tasks;
import am.newway.lesson4.data.variable.Var;
import am.newway.lesson4.fragments.BaseFragment;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomappbar.BottomAppBar;

public class MainActivity extends AppCompatActivity
{
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private ViewPager pager;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        findViewById( R.id.fab ).setOnClickListener( new View.OnClickListener()
        {
            @Override public void onClick( View v )
            {
                startActivityForResult( new Intent( MainActivity.this ,
                        SecondActivity.class ) , 1 );
            }
        } );

        final BottomAppBar bar = findViewById(R.id.bar);
        setSupportActionBar(bar);

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Home Clicked1",Toast.LENGTH_LONG).show();
            }
        });

        bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick( MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Toast.makeText(getApplicationContext(), "Home Clicked2", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        pager = findViewById( R.id.pager );

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        pager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("pager", "onPageScrolled position = "
                        + position
                        + " positionOffset = "
                        + positionOffset
                        + " positionOffsetPixels = "
                        + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("pager", "onPageSelected position = "
                        + position);
                bar.setTitle( pager.getAdapter().getPageTitle( position ) );

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.w("pager", "onPageScrollStateChanged position = "
                        + state);
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i("Permission !!!", "Permission is not granted");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("Warrning")
                        .setMessage("without this permission contacts will not be read. please provide access.")
                        .setPositiveButton("Acces", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                Log.i("Permission !!!", "asynchronously");
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                Settings.getInstance( this ).setReadContactsAccess(false);

                Log.i("Permission !!!", "request the permission");
            }
        } else {
            Settings.getInstance( this ).setReadContactsAccess(true);

            Log.i("Permission !!!", "Permission has already been granted");
        }

//        Glide.with(this)
//                .load("http://www.helloandroid.com/files/mobileplugin/180x180/d999d8b364dbe5b4c74432b5b57b519b.jpg")
//                .into(firstImage);
//
//        Picasso.get()
//                .load("http://www.helloandroid.com/files/mobileplugin/180x180/d999d8b364dbe5b4c74432b5b57b519b.jpg")
//                .into(lastImage);
    }

    @Override public void onActivityResult( int requestCode , int resultCode , @Nullable Intent data )
    {
        if ( null != data  && 1 == requestCode && RESULT_OK == resultCode )
        {
            Bundle bnd = data.getExtras();
            if (bnd != null)
            {
                BaseFragment fr = (BaseFragment) getSupportFragmentManager().getFragments().get( pager.getCurrentItem() );
                fr.setTask( new Tasks( bnd.getString( Var._Name ) ,
                        bnd.getString( Var._Description ) , Uri.parse( bnd.getString( Var._Uri ) ) , bnd.getString( Var._Type ) ) );
                Settings.getInstance(  this ).SQL().addValue( Var._Name, bnd.getString( Var._Name ) );
                Settings.getInstance(  this ).SQL().addValue( Var._Description, bnd.getString( Var._Description ) );
                Settings.getInstance(  this ).SQL().addValue( Var._Uri, bnd.getString( Var._Uri ) );
                Settings.getInstance(  this ).SQL().addValue( Var._Type, bnd.getString( Var._Type ) );
                Settings.getInstance(  this ).SQL().insertDB( "Tasks" );
            }
        }else
            Toast.makeText(  this , getString( R.string._oops) , Toast.LENGTH_SHORT ).show();

        super.onActivityResult( requestCode , resultCode , data );
    }
}
