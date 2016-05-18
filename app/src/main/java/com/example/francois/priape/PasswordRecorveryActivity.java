package com.example.francois.priape;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.databinding.ActivityPasswordRecorveryBinding;

public class PasswordRecorveryActivity extends AppCompatActivity {

    private ActivityPasswordRecorveryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recorvery);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_recorvery);

        binding.recoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkfields()){
                    API.recoveryPassword(binding.emailRecovery.getText().toString(), new Callback.RecoveryCallback() {
                        @Override
                        public void success() {
                            Toast.makeText(PasswordRecorveryActivity.this,R.string.toast_message_success_recovery,Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void error(String errorCode) {

                            if(errorCode.equals("3020")){
                                Toast.makeText(PasswordRecorveryActivity.this,R.string.error_3020,Toast.LENGTH_LONG).show();
                            }
                            if(errorCode.equals("3025")){
                                Toast.makeText(PasswordRecorveryActivity.this,R.string.error_3025,Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }

            }
        });
    }

    public boolean checkfields(){

        if (binding.emailRecovery.getText().toString().isEmpty()) {
            binding.emailRecovery.setError(getString(R.string.errorEmailInscription));
            return false;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailRecovery.getText().toString()).matches()){
            binding.emailRecovery.setError(getString(R.string.errorEmail));
            return false;
        }

        return true;
    }
}
