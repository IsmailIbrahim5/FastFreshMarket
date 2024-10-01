package com.idea.fastfreshmarket.UI.activities;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.idea.fastfreshmarket.Models.User;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.alertDialogs.Loading;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    boolean error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final Button create = findViewById(R.id.create_account);
        final ScrollView scrollView = findViewById(R.id.scroll);
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.LayoutParams layoutParams = scrollView.getLayoutParams();
                layoutParams.height = (int) (scrollView.getRootView().getHeight() -dpToPx((float) (create.getHeight()+0.25*create.getHeight())));
                scrollView.setLayoutParams(layoutParams);
            }
        });
          final AutoCompleteTextView gender = findViewById(R.id.gender);
        final ImageView userAvatar = findViewById(R.id.avatar);
        final TextInputEditText name = findViewById(R.id.name) ,
        phone = findViewById(R.id.phone) ,
        job = findViewById(R.id.job);

        final TextInputLayout nameLayout = findViewById(R.id.name_layout) ,
                genderLayout = findViewById(R.id.gender_layout) ,
                phoneLayout = findViewById(R.id.phone_layout) ,
                jobLayout = findViewById(R.id.job_layout);
        final MaterialButton birthday = findViewById(R.id.birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this,R.style.DatePickerDialogStyle, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1++; birthday.setText(i+"/"+i1+"/"+i2);
                    }
                } , calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.green));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.green));

            }
        });

        String[] genders = getResources().getStringArray(R.array.genders);
        final ArrayAdapter<String> adapter = new ArrayAdapter(SignUp.this , R.layout.list_item , genders);
        gender.setAdapter(adapter);
        gender.setDropDownBackgroundResource(R.color.green);
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                    userAvatar.setImageDrawable(getDrawable(R.drawable.ic_male));
                else
                    userAvatar.setImageDrawable(getDrawable(R.drawable.ic_female));

            }
        });



        name.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty())
                    nameLayout.setError(null);
            }
            public void afterTextChanged(Editable editable) {}
        });


        gender.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty())
                    genderLayout.setError(null);
            }
            public void afterTextChanged(Editable editable) {}
        });


        phone.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty())
                    phoneLayout.setError(null);
            }
            public void afterTextChanged(Editable editable) {}
        });


        birthday.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty())
                    birthday.setError(null);
            }
            public void afterTextChanged(Editable editable) {}
        });

        job.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty())
                    jobLayout.setError(null);
            }
            public void afterTextChanged(Editable editable) {}
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error = false;
                if(name.getText().toString().isEmpty()) {
                    nameLayout.setError(getString(R.string.name_error));
                    error = true;
                }
                if(gender.getText().toString().isEmpty()) {
                    genderLayout.setError(getString(R.string.gender_error));
                    error = true;
                }
                String regexStr = "^[+]?[0-9]{10,13}$";

                String number=phone.getText().toString();
                if(phone.getText().toString().isEmpty()) {
                    if(number.length()<10 || number.length()>13 || !number.matches(regexStr)) {
                        phoneLayout.setError(getString(R.string.phone_error));
                        error = true;
                    }
                }
                if(birthday.getText().toString().isEmpty()) {
                    birthday.setError(getString(R.string.birthday));
                    error = true;
                }
                if(job.getText().toString().isEmpty()) {
                    jobLayout.setError(getString(R.string.job_error));
                    error = true;
                }
                if(!error) {
                    final Loading loading = new Loading(SignUp.this);
                    loading.start();
                    final User user = new User();

                    user.setName(name.getText().toString());
                    user.setGender(gender.getText().toString());
                    user.setPhone(phone.getText().toString());
                    user.setBirthday(birthday.getText().toString());
                    user.setJob(job.getText().toString());
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    final DatabaseReference newUser = databaseReference.push();
                    user.setId(newUser.getKey());
                    newUser.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            try {
                                FileOutputStream fos = openFileOutput("userId" , Context.MODE_PRIVATE);
                                Writer out = new OutputStreamWriter(fos);
                                out.write(newUser.getKey());
                                out.close();
                            } catch (Exception ignored) {}
                            loading.dismiss();
                            Toast.makeText(SignUp.this, getString(R.string.account_created), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this , Home.class));
                            overridePendingTransition(R.anim.left_to_right , R.anim.right_to_left);
                            finish();
                        }
                    });
                }

            }
        });
    }

    public float dpToPx(float valueInDp) {
        try {
            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
        } catch (Exception ignored) {}
        return 0;
    }

}