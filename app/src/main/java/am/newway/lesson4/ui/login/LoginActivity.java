package am.newway.lesson4.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import am.newway.lesson4.R;
import am.newway.lesson4.ui.MainActivity;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity
{

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        loginViewModel = ViewModelProviders.of( this , new LoginViewModelFactory() )
                .get( LoginViewModel.class );

        final EditText usernameEditText = findViewById( R.id.username );
        final EditText passwordEditText = findViewById( R.id.password );
        final Button loginButton = findViewById( R.id.login );

        loginViewModel.getLoginFormState().observe( this , new Observer<LoginFormState>()
        {
            @Override
            public void onChanged( @Nullable LoginFormState loginFormState )
            {
                if (loginFormState == null)
                {
                    return;
                }
                loginButton.setEnabled( loginFormState.isDataValid() );
                if (loginFormState.getUsernameError() != null)
                {
                    usernameEditText.setError( getString( loginFormState.getUsernameError() ) );
                }
                if (loginFormState.getPasswordError() != null)
                {
                    passwordEditText.setError( getString( loginFormState.getPasswordError() ) );
                }
            }
        } );

        loginViewModel.getLoginResult().observe( this , new Observer<LoginResult>()
        {
            @Override
            public void onChanged( @Nullable LoginResult loginResult )
            {
                if (loginResult == null)
                {
                    return;
                }
                if (loginResult.getError() != null)
                {
                    showLoginFailed( loginResult.getError() );
                }
                if (loginResult.getSuccess() != null)
                {
                    updateUiWithUser( loginResult.getSuccess() );
                }
                setResult( Activity.RESULT_OK );

                //Complete and destroy login activity once successful
                startActivity(new Intent( LoginActivity.this, MainActivity.class ) );
                finish();
            }
        } );

        TextWatcher afterTextChangedListener = new TextWatcher()
        {
            @Override
            public void beforeTextChanged( CharSequence s , int start , int count , int after )
            {
                // ignore
            }

            @Override
            public void onTextChanged( CharSequence s , int start , int before , int count )
            {
                // ignore
            }

            @Override
            public void afterTextChanged( Editable s )
            {
                loginViewModel.loginDataChanged( usernameEditText.getText().toString() ,
                        passwordEditText.getText().toString() );
            }
        };
        usernameEditText.setText( "test@aca.com");
        passwordEditText.setText( "123456" );


        usernameEditText.addTextChangedListener( afterTextChangedListener );
        passwordEditText.addTextChangedListener( afterTextChangedListener );
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
        passwordEditText.setOnEditorActionListener( new TextView.OnEditorActionListener()
        {

            @Override
            public boolean onEditorAction( TextView v , int actionId , KeyEvent event )
            {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    loginViewModel.login( usernameEditText.getText().toString() ,
                            passwordEditText.getText().toString() );
                }
                return false;
            }
        } );

        loginButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                loginViewModel.login( usernameEditText.getText().toString() ,
                        passwordEditText.getText().toString() );
            }
        } );
    }

    private void updateUiWithUser( LoggedInUserView model )
    {
        String welcome = getString( R.string.welcome ) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        //Toast.makeText( getApplicationContext() , welcome , Toast.LENGTH_LONG ).show();
    }

    private void showLoginFailed( @StringRes Integer errorString )
    {
        Toast.makeText( getApplicationContext() , errorString , Toast.LENGTH_SHORT ).show();
    }
}
