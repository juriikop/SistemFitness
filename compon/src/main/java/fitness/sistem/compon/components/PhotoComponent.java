package fitness.sistem.compon.components;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.ActivityResult;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.tools.Constants;

public class PhotoComponent extends BaseComponent{

    View view;
    private ImageView img;
    private Uri photoURI;
    private String photoPath;

    public PhotoComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        Log.d("QWERT","zzzzzzzzzzzzzzzzzz");
        view = parentLayout.findViewById(paramMV.paramView.viewId);
        if (view == null) {
            iBase.log( "Не найден View в " + paramMV.nameParentComponent);
            return;
        }
        img = (ImageView) parentLayout.findViewById(paramMV.paramView.layoutTypeId[0]);
        if (img == null) {
            iBase.log( "Не найден ImageView в " + paramMV.nameParentComponent);
            return;
        }
        paramMV.startActual = false;
        view.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    iBase.log("Error occurred while creating the File");
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(activity,
                            activity.getPackageName() +".provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    activity.addForResult(Constants.REQUEST_CODE_PHOTO, activityResult);
                    activity.startActivityForResult(takePictureIntent, Constants.REQUEST_CODE_PHOTO);
                }
            }
        }
    };

    private ActivityResult activityResult = new ActivityResult() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            img.setImageURI(photoURI);
        }
    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void changeData(Field field) {

    }
}
