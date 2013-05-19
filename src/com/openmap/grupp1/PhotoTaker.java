package com.openmap.grupp1;
 
import java.io.File;
 
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
 
public class PhotoTaker extends Activity {
        final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
        static Uri photoUri;
        private Intent intentCamera;
       
       
   
        @Override
        public void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                toCamera();
               
        }
       
        private void toCamera(){
                // create Intent to take a picture and return control to the calling application
                       
                intentCamera = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
                File image=new File(Environment.getExternalStorageDirectory(),"test.jpg");
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(image));
                
                photoUri=Uri.fromFile(image);
                
                //Tillfälligt
              
                
                // start the image capture Intent
                startActivityForResult(intentCamera, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
       
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
                if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Funkar");
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Your intent to take a photo failed");
                }
        }
                this.finish();
 
}
}