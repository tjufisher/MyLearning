package com.mylearning.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.utils.FileUtils;
import com.mylearning.utils.IntentUtils;
import com.mylearning.utils.LogUtils;
import com.mylearning.view.MiddlePopWindow;
import com.mylearning.view.RoundImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyBaseInfoActivity extends BaseActivity {


    @InjectView(R.id.tv_header_photo)
    TextView tvHeaderPhoto;
    @InjectView(R.id.riv_header_photo)
    RoundImageView rivHeaderPhoto;
    @InjectView(R.id.tv_header_photo_integral_text)
    TextView tvHeaderPhotoIntegralText;
    @InjectView(R.id.rl_header_photo)
    RelativeLayout rlHeaderPhoto;
    @InjectView(R.id.personal_mine_message_label)
    TextView personalMineMessageLabel;
    @InjectView(R.id.my_favorite_count)
    TextView myFavoriteCount;
    @InjectView(R.id.personal_mine_message)
    RelativeLayout personalMineMessage;
    @InjectView(R.id.tv_address_des)
    TextView tvAddressDes;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.rl_address)
    RelativeLayout rlAddress;
    @InjectView(R.id.personal_mine_auth)
    RelativeLayout personalMineAuth;
    @InjectView(R.id.personal_mine_setting_text)
    TextView personalMineSettingText;
    @InjectView(R.id.personal_mine_setting)
    RelativeLayout personalMineSetting;
    @InjectView(R.id.personal_mine_sign_text)
    TextView personalMineSignText;
    @InjectView(R.id.personal_mine_sign)
    RelativeLayout personalMineSign;
    @InjectView(R.id.personal_mine_share_text)
    TextView personalMineShareText;
    @InjectView(R.id.personal_mine_share)
    RelativeLayout personalMineShare;
    @InjectView(R.id.personal_mine_help)
    RelativeLayout personalMineHelp;
    @InjectView(R.id.personal_mine_suggestion_text)
    TextView personalMineSuggestionText;
    @InjectView(R.id.personal_mine_suggestion)
    RelativeLayout personalMineSuggestion;
    @InjectView(R.id.main_scrollview)
    ScrollView mainScrollview;

    private Context mContext;
    private MiddlePopWindow popupWindow;

    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;//拍照
    private static final int REQUEST_CODE_PICK_IMAGE = 1;//相册,SDK 19以上
    private static final int REQUEST_CODE_PICK_IMAGE_OLD = 2;//相册,SDK 19以下

    File tempFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base_info, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("欢迎页面", R.drawable.share);
        mContext = this;

    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();
            switch (v.getId()){
                case R.id.btn_take_video:

                    break;
                case R.id.btn_take_photo:
                    tempFile = FileUtils.getTempPath();
                    if (tempFile == null) {
                        toast("sd卡不可用");
                        return;
                    }
                    if (tempFile != null) {
                        try {
                            startActivityForResult(
                                    IntentUtils.createShotIntent(tempFile),
                                    REQUEST_CODE_CAPTURE_CAMEIA);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case R.id.btn_pick_photo:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");//相片类型
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                    if (android.os.Build.VERSION.SDK_INT >= 19) {
                        startActivityForResult(IntentUtils.createAlbumIntent(),
                                REQUEST_CODE_PICK_IMAGE);
                    } else {
                        startActivityForResult(IntentUtils.createAlbumIntent(),
                                REQUEST_CODE_PICK_IMAGE_OLD);
                    }
                    break;
                case R.id.btn_cancel:
                    popupWindow.dismiss();
                    break;
                default:
                    break;

            }
        }
    } ;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


//
//
//        if (resultCode == RESULT_OK) {
//            imagePath = "";
//            if (requestCode == PICK_PIC_CAMERA) {
//                Uri uri = Uri.fromFile(tempFile);
//                Log.e(tag, uri.toString());
//                if (android.os.Build.VERSION.SDK_INT >= 19) {
//                    AlbumAndComera.getImageClipIntent(uri, this, true);
//                } else {
//                    AlbumAndComera.getImageClipIntent(uri, this, false);
//                }
//
//            } else if (requestCode == PICK_PIC_OLD || requestCode == PICK_PIC_KITKAT) {
//                if (null == data) {
//                    return;
//                }
//                albumUri = data.getData();
//                tempFile = null;
//                if (android.os.Build.VERSION.SDK_INT >= 19) {
//                    AlbumAndComera.getImageClipIntent(albumUri, this, true);
//                } else {
//                    AlbumAndComera.getImageClipIntent(albumUri, this, false);
//                }
//
//            } else if (requestCode == SoufunConstants.CHOOSE_CUT && null != data) {
//                if (tempFile != null) {
//                    try {
//                        if (tempFile.length() > 0) {
//                            String filePath = "";
//
//                            if (tempFile != null) {
//                                if (tempFile.length() > 0) {
//                                    options.inPreferredConfig = Bitmap.Config.RGB_565;
//                                    try {
//                                        filePath = tempFile.getAbsolutePath();
//                                        imagePath = filePath;
//                                        AlbumAndComera.compressForupload(imagePath);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    if (!StringUtils.isNullOrEmpty(imagePath)) {
//                                        showDialog = Utils.showProcessDialog(MyAcountActivity.this,
//                                                "正在上传头像");
//                                        new Thread(new Runnable() {
//
//                                            @Override
//                                            public void run() {
//                                                if (android.os.Build.VERSION.SDK_INT >= 19
//                                                        || null == data.getData()) {
//                                                    try {
//                                                        Bitmap bitmap = (Bitmap) data.getExtras()
//                                                                .getParcelable("data");
//                                                        Uri uri = Uri.parse(MediaStore.Images.Media
//                                                                .insertImage(getContentResolver(),
//                                                                        bitmap, null, null));
//                                                        imagePath = HttpApi.uploadFile(AlbumAndComera
//                                                                .getAlbumPath(mContext, uri));
//                                                    } catch (Exception e) {
//                                                        imagePath = HttpApi.uploadFile(imagePath);
//                                                    }
//                                                } else {
//                                                    imagePath = HttpApi.uploadFile(AlbumAndComera
//                                                            .getAlbumPath(mContext, data));
//                                                }
//                                                imageUrl = imagePath;
//                                                Log.i("hwq", "" + imageUrl);
//                                                myPhotoTask = new MyPhotoTask();
//                                                myPhotoTask.execute();
//                                                mHandler.sendEmptyMessage(0);
//                                            }
//                                        }).start();
//                                    }
//                                }
//                            } else {
//                                toast("上传图片失败");
//                                UtilsLog.e("msg", "上传图片失败");
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    if (android.os.Build.VERSION.SDK_INT >= 19 || null == data.getData()) {// 4.4之后
//                        try {
//
//                            imagePath = AlbumAndComera.getAlbumPath(mContext, albumUri);
//                            Log.i("MyCat", "return data(false):" + imagePath);
//                            if (!StringUtils.isNullOrEmpty(imagePath)) {
//                                showDialog = Utils.showProcessDialog(MyAcountActivity.this,
//                                        "正在上传头像");
//                                new Thread(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            Bitmap bitmap = (Bitmap) data.getExtras()
//                                                    .getParcelable("data");
//                                            Uri uri = Uri.parse(MediaStore.Images.Media
//                                                    .insertImage(getContentResolver(), bitmap,
//                                                            null, null));
//                                            imagePath = HttpApi.uploadFile(AlbumAndComera
//                                                    .getAlbumPath(mContext, uri));
//                                        } catch (Exception e) {
//                                            imagePath = HttpApi.uploadFile(AlbumAndComera
//                                                    .getAlbumPath(mContext, albumUri));
//                                        }
//                                        imageUrl = imagePath;
//                                        myPhotoTask = new MyPhotoTask();
//                                        myPhotoTask.execute();
//                                        mHandler.sendEmptyMessage(0);
//                                    }
//                                }).start();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {// 4.4之前
//                        InputStream is = null;
//                        String filePath = "";
//                        options.inPreferredConfig = Bitmap.Config.RGB_565;
//                        Uri uri = data.getData();
//                        try {
//                            ContentResolver contentResolver = mContext.getContentResolver();
//                            is = contentResolver.openInputStream(uri);
//                            filePath = AlbumAndComera.convertStream2File(is);
//                            imagePath = filePath;
//                            AlbumAndComera.compressForupload(imagePath);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        if (!StringUtils.isNullOrEmpty(imagePath)) {
//                            showDialog = Utils.showProcessDialog(MyAcountActivity.this, "正在上传头像");
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    imagePath = HttpApi.uploadFile(AlbumAndComera.getAlbumPath(
//                                            mContext, data));
//                                    imageUrl = imagePath;
//                                    myPhotoTask = new MyPhotoTask();
//                                    myPhotoTask.execute();
//                                    mHandler.sendEmptyMessage(0);
//                                }
//                            }).start();
//                        }
//                    }
//                }
//            } else if (requestCode == 100) {
//                setResult(RESULT_OK);
//                finish();
//            } else if (requestCode == 520) {
//                String mobile_new = data.getStringExtra("mobile_new");
//                if (!StringUtils.isNullOrEmpty(mobile_new)) {
//                    tv_telephone2.setText(mobile_new);
//                }
//            }
//        }
//
//
//        





        Uri uri = null;
        switch (requestCode){
            case  REQUEST_CODE_CAPTURE_CAMEIA:
                uri = data.getData();
                if(uri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                        //spath :生成图片取个名字和路径包含类型
                        String spath = "";
                        saveImage(photo, spath);
                    } else {
                        Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                    break;
            case REQUEST_CODE_PICK_IMAGE:
                uri = data.getData();
                break;
             case REQUEST_CODE_PICK_IMAGE_OLD:
                uri = data.getData();
                break;

            default:
                break;
        }
        LogUtils.i("URI",uri.toString());
    }

    public static void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }
        return ;
    }

    @OnClick (R.id.rl_header_photo )
    public void changeHeaderPhoto(){
        popupWindow = new MiddlePopWindow(mContext, clickListener,
                new String[]{"拍照", "从相册上传", "取消"});
        popupWindow.showAtLocation(
                MyBaseInfoActivity.this.getWindow()
                        .getDecorView(), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
        popupWindow.update();
    }

}
