package fitness.sistem.compon.components;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String imgPath;

    public PhotoComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
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

    public String getFilePath() {
        return imgPath + "";
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                iBase.log("Error occurred while creating the File");
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(activity,
                        activity.getPackageName() +".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
            List<Intent> intentList = new ArrayList<>();
            Intent pickIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intentList = addIntentsToList(activity, intentList, pickIntent);
            intentList = addIntentsToList(activity, intentList, takePictureIntent);

            Intent chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    activity.getString(paramMV.paramView.layoutFurtherTypeId[0]));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
            activity.addForResult(Constants.REQUEST_CODE_PHOTO, activityResult);
            activity.startActivityForResult(chooserIntent, Constants.REQUEST_CODE_PHOTO);
        }
    };

    private List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            targetedIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            list.add(targetedIntent);
        }
        return list;
    }

    private ActivityResult activityResult = new ActivityResult() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(resultCode == activity.RESULT_OK) {
                Uri selectedImage;
                if (data == null) {
                    selectedImage = null;
                } else {
                    selectedImage = data.getData();
                }
                if (selectedImage != null) {
                    imgPath = selectedImage.toString();
                    img.setImageURI(selectedImage);
                } else {
                    imgPath = photoURI.toString();
                    img.setImageURI(photoURI);
                }
            }
        }
    };

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);
        photoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void changeData(Field field) {

    }
}
