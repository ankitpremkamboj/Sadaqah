package com.innoapps.sadaqah.screens.editprofile;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.editprofile.presenter.EditProfilePresenter;
import com.innoapps.sadaqah.screens.editprofile.presenter.EditProfilePresenterImpl;
import com.innoapps.sadaqah.screens.editprofile.view.EditProfileView;
import com.innoapps.sadaqah.screens.navigation.model.UserDetailModel;
import com.innoapps.sadaqah.screens.navigation.presenter.UserDetailPresenter;
import com.innoapps.sadaqah.screens.navigation.presenter.UserDetailPresenterImpl;
import com.innoapps.sadaqah.screens.navigation.view.UserDetailView;
import com.innoapps.sadaqah.screens.signup.SignUpActivity;
import com.innoapps.sadaqah.utils.AppConstant;
import com.innoapps.sadaqah.utils.AppFonts;
import com.innoapps.sadaqah.utils.GlideCircleTransform;
import com.innoapps.sadaqah.utils.UserSession;
import com.innoapps.sadaqah.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EditProfileActivity extends AppCompatActivity implements UserDetailView, EditProfileView {

    @InjectView(R.id.img_profile)
    ImageView img_profile;
    @InjectView(R.id.img_edit)
    ImageView img_edit;
    @InjectView(R.id.input_name)
    EditText input_name;
    @InjectView(R.id.input_email)
    EditText input_email;
    @InjectView(R.id.input_password)
    EditText input_password;
    @InjectView(R.id.btn_save)
    Button btn_save;
    @InjectView(R.id.txt_change)
    TextView txt_change;
    UserDetailPresenter userDetailPresenter;
    UserSession userSession;


    private Dialog selectImageDialog;

    private static final String DIRECTORY_NAME = "CHARITY";
    private static final String FILE_NAME = "cameraImage.jpg";
    private static final int CAMERA_PIC_REQUEST = 102;
    private static final int REQ_CODE_PICK_IMAGE = 101;
    private String cameraImageFilePath = "";
    private File file;

    EditProfilePresenter editProfilePresenter;
    private String _name = "", _email = "", _password = "", _signupImage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.inject(this);
        userSession = new UserSession(this);
        userDetailPresenter = new UserDetailPresenterImpl();
        userDetailPresenter.gettingUserDetails(this, this, userSession.getUserID());
        setFonts();
        editProfilePresenter = new EditProfilePresenterImpl();
    }

    @OnClick(R.id.btn_save)
    public void saveProfile() {


        _name = input_name.getText().toString().trim();
        _email = input_email.getText().toString().trim();
        _password = input_password.getText().toString().trim();

        editProfilePresenter.validateProfile(EditProfileActivity.this, this, _name, _email, _password, cameraImageFilePath);

    }

    @Override
    public void getUserDetailsSuccessfull(UserDetailModel.Data data) {
        if (!data.getName().isEmpty() && data.getName() != null) {
            input_name.setText(data.getName());
        } else {
            input_name.setText("N/A");
        }
        if (!data.getEmail().isEmpty() && data.getEmail() != null) {
            input_email.setText(data.getEmail());
        } else {
            input_email.setText("N/A");
        }
        if (!data.getImage().isEmpty() && data.getImage() != null) {
       /*  Glide.with(this)
                    .load(data.getImage())
                    .transform(new GlideCircleTransform(this))
                    .placeholder(R.mipmap.ic_default_profile)
                    .error(R.mipmap.ic_default_profile)
                   // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img_profile);*/

            Picasso.with(this).load(data.getImage()).placeholder(R.mipmap.ic_default_profile).error(R.mipmap.ic_default_profile).into(img_profile);

        }
    }

    @Override
    public void getUserDetailsUnSuccessful(String message) {

    }

    private void setFonts() {

        input_password.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        input_email.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        input_name.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_change.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));

    }

    @OnClick(R.id.img_edit)
    public void changeImage() {
        selectImage();
    }


    private void selectImage() {
        selectImageDialog = new Dialog(this, R.style.DialogSlideAnim);
        if (!selectImageDialog.isShowing()) {
            selectImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(selectImageDialog.getWindow().getAttributes());
            lp.gravity = Gravity.BOTTOM;
            selectImageDialog.getWindow().setAttributes(lp);
            selectImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            selectImageDialog.setContentView(R.layout.dialog_select_image);
            selectImageDialog.findViewById(R.id.card_Photo).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (selectImageDialog != null) {
                        selectImageDialog.dismiss();
                    }
                    openGallery();


                }
            });

            selectImageDialog.findViewById(R.id.card_Camera).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (selectImageDialog != null) {
                        selectImageDialog.dismiss();
                    }
                    openCamera();
                }
            });

            selectImageDialog.findViewById(R.id.card_Cancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (selectImageDialog != null) {
                        selectImageDialog.dismiss();
                    }

                }
            });


            selectImageDialog.show();

        }

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
    }

    ///storage/emulated/0/EVENT/cameraImage.jpg
    public void openCamera() {
        try {
            File dir = Utils.getInstance().createDirectory(this, DIRECTORY_NAME);
            File cameraImageFile = Utils.getInstance().createFile(this, dir.getAbsolutePath(), FILE_NAME);
            cameraImageFilePath = cameraImageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(new File(cameraImageFile.getAbsolutePath()));
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == this.RESULT_OK) {
                if (data != null && data.getData() != null) {
                    file = new File(compressImage(data.getData(), false));

                    cameraImageFilePath = compressImage(data.getData(), false);
                    Picasso.with(this).load(file).placeholder(R.mipmap.ic_default_profile).error(R.mipmap.ic_default_profile).into(img_profile);

                   /* Glide.with(this)
                            .load(file)
                            .transform(new GlideCircleTransform(this))
                            .placeholder(R.mipmap.ic_default_profile)
                            .error(R.mipmap.ic_default_profile)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(img_profile);*/
                }
            } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == this.RESULT_OK) {
                if (cameraImageFilePath != null) {
                    file = Utils.getInstance().processCameraImage(this, cameraImageFilePath, this, null);

                    Picasso.with(this).load(file).placeholder(R.mipmap.ic_default_profile).error(R.mipmap.ic_default_profile).into(img_profile);
                  /*  Glide.with(this)
                            .load(file)
                            .transform(new GlideCircleTransform(this))
                            .placeholder(R.mipmap.ic_default_profile)
                            .error(R.mipmap.ic_default_profile)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(img_profile);*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String compressImage(Uri imageUri, boolean isFilePath) {

        String filePath = "";
        if (isFilePath)
            filePath = imageUri.toString();
        else
            filePath = getPath(this, imageUri);

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        if (actualWidth == 0 || actualHeight == 0) {
            actualHeight = 1280;
            actualWidth = 720;
            options.outHeight = 1280;
            options.outWidth = 720;
            bmp = BitmapFactory.decodeFile(filePath, options);
        }
//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        if (bmp != null) {
            int hei = bmp.getWidth();

        }
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), AppConstant.DIRECTORY_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + "local_file" + System.currentTimeMillis() + ".jpeg");
        return uriSting;

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @Override
    public void onNameError(String name) {

    }

    @Override
    public void onEmailError(String name) {

    }

    @Override
    public void onValidEmailError(String name) {

    }

    @Override
    public void onPasswordError(String name) {

    }

    @Override
    public void onSignUpImageError(String profileImage) {

    }

    @Override
    public void onSignUpInternetError() {

    }

    @Override
    public void onSignUpSuccessful(String message) {

    }

    @Override
    public void onSignUpUnSuccessful(String msg) {

    }
}
