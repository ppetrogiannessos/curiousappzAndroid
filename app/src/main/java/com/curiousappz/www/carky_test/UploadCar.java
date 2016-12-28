package com.curiousappz.www.carky_test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadCar extends Activity implements View.OnClickListener
{
    /*TextView tvskip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_car);

        tvskip=(TextView)findViewById(R.id.tvskip);
        tvskip.setOnClickListener(this);
        ImageButton btnnext3 = (ImageButton)findViewById(R.id.bnext3);
        btnnext3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvskip :
            {
                Intent intent = new Intent(this, Terms.class);
                startActivity(intent);
                break;
            }
            case R.id.bnext3 :
            {
                Intent intent = new Intent(this, Terms.class);
                startActivity(intent);
                break;
            }
        }
    }*/



    public static final String UPLOAD_URL = "http://carky-app.azurewebsites.net/api/CarOwner/UploadCarPhotos?";
    public static final String UPLOAD_KEY = "image";
    private ImageButton side,front_side,three_quaters,interior ,next;
    private Button buttonUpload;
    private TextView skip;
    private ArrayList<String> imgPaths = new ArrayList<>();
    // private ImageView imageView;

    //  private EditText editTextName;

    private Bitmap bitmapside,bitmapfront,bitmapquaters,bitmapinterrior;

    private int PICK_IMAGE1_REQUEST = 1;
    private int PICK_IMAGE2_REQUEST = 2;
    private int PICK_IMAGE3_REQUEST = 3;
    private int PICK_IMAGE4_REQUEST = 4;

    //  private String UPLOAD_URL ="http://simplifiedcoding.16mb.com/VolleyUpload/upload.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_car);

        skip = (TextView) findViewById(R.id.tvforgetlink);
        side = (ImageButton) findViewById(R.id.side);
        front_side = (ImageButton) findViewById(R.id.front_side);
        three_quaters = (ImageButton) findViewById(R.id.three_quaters);
        interior= (ImageButton) findViewById(R.id.interior);
        next = (ImageButton) findViewById(R.id.bnext3);
        // imageView  = (ImageView) findViewById(R.id.imageView);

        side.setOnClickListener(this);
        front_side.setOnClickListener(this);
        three_quaters.setOnClickListener(this);
        interior.setOnClickListener(this);
        // buttonUpload.setOnClickListener(this);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipIntent();

            }


        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uploadFile(imgPaths);
                next4Intent();

            }


        });

    }

    private void next4Intent() {
        Intent i = new Intent(UploadCar.this, Terms.class);
        uploadFile(imgPaths);
        startActivity(i);

    }

    private void skipIntent() {
        Intent i = new Intent(UploadCar.this, Terms.class);
        startActivity(i);

    }
    private void showFileside() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE1_REQUEST);
    }
    private void showFilefrontside() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE2_REQUEST);
    }
    private void showFilethreequaters() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE3_REQUEST);
    }
    private void showFileinterior() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE4_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE1_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery

                bitmapside = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                side.setImageBitmap(bitmapside);
                side.setScaleType(ImageView.ScaleType.FIT_XY);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == PICK_IMAGE2_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery

                bitmapfront = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                front_side.setImageBitmap(bitmapfront);
                front_side.setScaleType(ImageView.ScaleType.FIT_XY);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == PICK_IMAGE3_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmapquaters = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                three_quaters.setImageBitmap(bitmapquaters);
                three_quaters.setScaleType(ImageView.ScaleType.FIT_XY);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == PICK_IMAGE4_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmapinterrior = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                interior.setImageBitmap(bitmapinterrior);
                interior.setScaleType(ImageView.ScaleType.FIT_XY);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void uploadFile(ArrayList<String> imgPaths) {

        String charset = "UTF-8";
        //File uploadFile1 = new File("e:/Test/PIC1.JPG");
        //File uploadFile2 = new File("e:/Test/PIC2.JPG");

        File sourceFile[] = new File[imgPaths.size()];
        for (int i=0;i<imgPaths.size();i++){
            sourceFile[i] = new File(imgPaths.get(i));
            // Toast.makeText(getApplicationContext(),imgPaths.get(i),Toast.LENGTH_SHORT).show();
        }

        String requestURL = "http://carky-app.azurewebsites.net/api/CarOwner/UploadCarPhotos?carid="+12;

        try {
            FileUploader multipart = new FileUploader(requestURL, charset);

            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");

            //  multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("sidepic",sourceFile[0].getName());
            multipart.addFormField("frontpic",sourceFile[1].getName());
            multipart.addFormField("outsidepic",sourceFile[2].getName());
            multipart.addFormField("insidepic",sourceFile[3].getName());

            multipart.addFilePart("sidepic", sourceFile[0]);
            multipart.addFilePart("frontpic", sourceFile[1]);
            multipart.addFilePart("outsidepic", sourceFile[2]);
            multipart.addFilePart("insidepic", sourceFile[3]);

            //  for (int i=0;i<imgPaths.size();i++){
            //     multipart.addFilePart("uploaded_file[]", sourceFile[i]);
            // }

            /*multipart.addFilePart("fileUpload", uploadFile1);
            multipart.addFilePart("fileUpload", uploadFile2);*/

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    /*private void uploadpic(ArrayList<String> imgPaths) throws IOException  {
        String charset = "UTF-8";
        //File uploadFile1 = new File("e:/Test/PIC1.JPG");
        //File uploadFile2 = new File("e:/Test/PIC2.JPG");
        String requestURL = "http://carky-app.azurewebsites.net/api/CarOwner/UploadCarPhotos?";
        MultipartUtility multipart = new MultipartUtility(requestURL, charset);
        multipart.addFormField("sidepic", "1.png");
        multipart.addFormField("frontpic", "2.png");
        multipart.addFormField("outsidepic", "1.png");
        multipart.addFormField("insidepic", "2.png");
        multipart.addFilePart("file_param_1", new File(String.valueOf(file_path)));
        String response = multipart.finish(); // response from server.
    }*/

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

  /* private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                String result = rh.sendPostRequest(UPLOAD_URL,data);


                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }*/

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.side: /** Start a new Activity MyCards.java */
                showFileside();
                break;

            case R.id.front_side: /** AlerDialog when click on Exit */
                showFilefrontside();
                break;
            case R.id.three_quaters: /** AlerDialog when click on Exit */
                showFilethreequaters();
                break;
            case R.id.interior: /** AlerDialog when click on Exit */
                showFileinterior();
                break;
         /*   case R.id.bnext3 :
                Intent intent = new Intent(this, Terms.class);
                startActivity(intent);
                break;*/

        }


        //  if(v == buttonUpload){
        // uploadImage();
        // }

        //if(v == buttonView){
        // viewImage();
        //}
    }

    // private void viewImage() {
    // startActivity(new Intent(this, ImageListView.class));
    // }


}