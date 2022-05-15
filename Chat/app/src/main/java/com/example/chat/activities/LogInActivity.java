package com.example.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat.databinding.ActivityLogInBinding;
import com.example.chat.utilities.Constants;
import com.example.chat.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity {
    private ActivityLogInBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager=new PreferenceManager(getApplicationContext());
        binding=ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }
    private void setListeners(){
        binding.textCreateAccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class)));
        binding.buttonLogIn.setOnClickListener(v->{
            if(isValidLogInDetails()){
                logIn();
            }
        });

    }
    private void logIn(){
        loading(true);
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_NUMBER,binding.inputNumber.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD,binding.inputPassword.getText().toString())
                .get()
                .addOnCompleteListener(task->{
                    if(task.isSuccessful()&&task.getResult() !=null
                            && task.getResult().getDocuments().size()>0){
                        DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_In,true);
                        preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                    else{
                        loading(false);
                        showToast("Unable to Log In");
                    }
                });


    }
    private void loading(boolean isLoading){
        if(isLoading){
            binding.buttonLogIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonLogIn.setVisibility(View.VISIBLE);
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private Boolean isValidLogInDetails(){
        if(binding.inputNumber.getText().toString().trim().isEmpty()){
            showToast("Enter the Phone number");
            return false;
        }
        else if(binding.inputPassword.getText().toString().trim().isEmpty()){
            showToast("Enter Password");
            return false;
        }
        else{
            return true;
        }
    }

}