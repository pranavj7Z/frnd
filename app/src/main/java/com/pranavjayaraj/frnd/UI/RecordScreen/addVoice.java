package com.pranavjayaraj.frnd.UI.RecordScreen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import com.pranavjayaraj.frnd.AudioViewModel;
import com.pranavjayaraj.frnd.R;
import com.pranavjayaraj.frnd.data.API.GetProjectService;
import com.pranavjayaraj.frnd.data.API.RetrofitInstance;
import com.pranavjayaraj.frnd.data.Audio;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addVoice extends AppCompatActivity {
    Button record;
    Long time;
    private MediaRecorder myAudioRecorder;
    private String outputFile;
    Boolean recording=false;
    AudioViewModel avm;
    Toolbar toolbar;
    LottieAnimationView animationView;
    ImageView mic ;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        avm = ViewModelProviders.of(this).get(AudioViewModel.class);
        ActivityCompat.requestPermissions(addVoice.this,
                new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE
               , Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    void setupview()
    {
        animationView = (LottieAnimationView) findViewById(R.id.menuAnimation2);
        toolbar = (Toolbar) findViewById(R.id.tb);
        record = (Button) findViewById(R.id.record);
        mic = (ImageView) findViewById(R.id.mic);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new upload(time, new File(outputFile)).execute();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!recording) {
                    recording=true;
                    record.setBackgroundResource(R.drawable.round);
                    record.setText("Stop");
                    mic.setVisibility(View.INVISIBLE);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.setAnimation("animations/record.json");
                    animationView.loop(true);
                    animationView.playAnimation();
                    time  = System.currentTimeMillis();
                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + time +".3gp";
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(outputFile);
                    try {
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                    } catch (IllegalStateException ise) {
                        // make something ...
                    } catch (IOException ioe) {
                        // make something
                    }
                }
                else {
                    recording=false;
                    record.setText("Record");
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    mic.setVisibility(View.VISIBLE);
                    animationView.cancelAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    record.setBackgroundResource(R.drawable.round);
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(addVoice.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }


    }

    private class upload extends AsyncTask<Void, Void, Void> {
       Long title_string;
       File file;

        upload(Long path, File file)
        {
            this.title_string=path;
            this.file= file;
        }

        @Override
        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... params)
        {
            GetProjectService service = RetrofitInstance.getRetrofitInstance().create(GetProjectService.class);
            MultipartBody.Part AudioPart = null;
            Call<Void> call = null;
            try
            {
                if(file.exists()) {
                    Uri audio = Uri.fromFile(file);
                    AudioPart = prepareAudio("audio_media", file);
                    call = service.Upload(AudioPart);
                }
            }
            catch (Exception ie)
            {

            }
            // create upload service client

            Log.wtf("URL Called", call.request().url() + "");
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    try {

                        Audio audio = new Audio(title_string.toString());
                        avm.insert(audio);
                        //link to  write to room

                    }
                    catch (Exception e)
                    {

                    }

                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Void result) {
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        private MultipartBody.Part prepareAudio(String partName, File file) {

            RequestBody imageBody =  RequestBody.create(MediaType.parse("audio/*"),file);
            return MultipartBody.Part.createFormData(partName ,file.getName(),imageBody);
        }
        // close the document
    }
}
