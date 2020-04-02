package com.samir.luniva.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.samir.luniva.R;
import com.samir.luniva.databinding.ActivityMainBinding;
import com.samir.luniva.helpers.ShowToast;
import com.samir.luniva.presenters.UploadPrescriptionPreseneter;
import com.samir.luniva.utils.FileUtil;
import com.samir.luniva.utils.Utilities;
import com.samir.luniva.utils.UtilitiesFunctions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;

import static com.samir.luniva.constants.AppConstants.REQUEST_STORAGE_PERMISSION;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, UploadPrescriptionPreseneter.View {

    ActivityMainBinding binding;
    private String getSelectedCity;
    private Uri fileUri;
    File file = null;
    ArrayList<String> allSelectedFileString = new ArrayList<>();
    ArrayList<Uri> allSelectedFile = new ArrayList<>();

    String selectedFilePath;

    UploadPrescriptionPreseneter uploadPrescriptionPreseneter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        selectPatientCity();
        binding.chooseFile.setOnClickListener(this);
        binding.submit.setOnClickListener(this);
        binding.sendEmailBtn.setOnClickListener(this);
        binding.sendEmailBtnn.setOnClickListener(this);

        uploadPrescriptionPreseneter = new UploadPrescriptionPreseneter(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.choose_file:
                chooseFile();
                break;

            case R.id.submit:
                uploadDetails();
                break;

            case R.id.sendEmailBtn:
                sendEmail();
                break;

            case R.id.sendEmailBtnn:
                sendMailUsingLibrary();
                break;
        }
    }

    private void sendEmail() {
        String email_address = binding.sendTo.getText().toString();
        String subject = binding.subject.getText().toString();
        String message = binding.message.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{email_address});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

//        if(allSelectedFile != null){
//            email.putExtra(Intent.EXTRA_STREAM, allSelectedFile);
//        }

//        to select single file
//        if(fileUri != null){
//            email.putExtra(Intent.EXTRA_STREAM, fileUri);
//        }

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));

//        Send Mail
//        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email_address, subject, message);
//        javaMailAPI.execute();
    }

    private void uploadDetails() {
        if(UtilitiesFunctions.isNetworkAvailable(this)){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED){

                if (fileUri != null) {
                    uploadPrescriptionPreseneter.uploadPrescription(binding.mobileNumber.getText().toString(), binding.username.getText().toString(), file);
                } else {
                    ShowToast.withLongMessage("not selected any file");
                }
            }
        } else {
            ShowToast.withLongMessage("Internet connection failed");
        }
    }

    private void chooseFile() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this, perms)){

            ShowToast.withLongMessage("permission already granted");
            openFile();

        } else {
            EasyPermissions.requestPermissions(this, "Storage permission is needed",123, perms);
        }

    }

    private void openFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        Intent i = Intent.createChooser(intent, "File");
        startActivityForResult(i, 123);
    }

    private void selectPatientCity(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.patient_cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.patientCity.setAdapter(adapter);
        binding.patientCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 getSelectedCity = parent.getItemAtPosition(position).toString();
                Log.e("onItemSelected: ", getSelectedCity );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sendMailUsingLibrary(){

        String email_address = binding.sendTo.getText().toString();
        String subject = binding.subject.getText().toString();
        String message = binding.message.getText().toString();
        String patient_name = binding.username.getText().toString();
        String patient_mobile = binding.mobileNumber.getText().toString();
        String patient_email = binding.userEmail.getText().toString();

        String bodymessage = message;

        if(UtilitiesFunctions.isNetworkAvailable(this)){
            BackgroundMail.newBuilder(this)
                    .withUsername(email_address)
                    .withPassword("ema1lpass")
                    .withMailto("samirshrestha010@gmail.com")
                    .withType(BackgroundMail.TYPE_PLAIN)
                    .withSubject(subject)
                    .withBody(bodymessage)
                    .withAttachments(allSelectedFileString)
//                    .withAttachments(String.valueOf(file))
                    .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                        @Override
                        public void onSuccess() {
                            ShowToast.withLongMessage("email sent");
                            Log.e("onSuccess: ", "email sent");
                        }
                    })
                    .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                        @Override
                        public void onFail() {
                            Log.e("onSuccess: ", "email not sent");
                            ShowToast.withLongMessage("email not sent");
                        }
                    })
                    .send();
        } else {
            ShowToast.withLongMessage("Internet not available");
        }
    }

    @Override
    public void onClinicResponseSuccess(ResponseBody body) {
        ShowToast.withLongMessage("Prescription uploaded successfully");
    }

    @Override
    public void onClinicResponseFailure(String something_went_wrong) {
        ShowToast.withLongMessage("unable to uploaded Prescription");
    }

    private void requestInternalStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission is needed")
                    .setMessage("Permission is needed to access storage")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFile();
            } else {
                ShowToast.withLongMessage("Storage permission not granted");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 123) {
                if(null != data.getClipData()) { // checking multiple selection or not
                    for(int i = 0; i < data.getClipData().getItemCount(); i++) {
                        allSelectedFile.add(data.getClipData().getItemAt(i).getUri());
                    }

                    try {
                        getSelectedFiles();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e( "onActivityResult: ", "single file selected" );
                    allSelectedFile.add(data.getData());

                    try {
                        getSelectedFiles();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getSelectedFiles() throws IOException {

        try {
            if(allSelectedFile.size()>1){
                for(int i = 0; i < allSelectedFile.size(); i++){
                    File multiFile = FileUtil.from(this, allSelectedFile.get(i));
                    Log.e("getSelectedFiles: ", multiFile.getName());

                    allSelectedFileString.add(multiFile.getAbsolutePath());
                }

            } else {
                File singleFile = FileUtil.from(this, allSelectedFile.get(0));
                Log.e("getSelectedFiles: ", singleFile.getAbsolutePath() );
                allSelectedFileString.add(singleFile.getAbsolutePath());
                }
            }

        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
