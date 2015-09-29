package com.mylearning.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.FileNotFoundException;
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

    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0x0001;//拍照
    private static final int REQUEST_CODE_PICK_IMAGE = 0x0002;//相册,SDK 19以上
    private static final int REQUEST_CODE_PICK_IMAGE_OLD = 0x0003;//相册,SDK 19以下
    private static final int REQUEST_CODE_CROP_SMALL = 0x0004;
    private static final int REQUEST_CODE_CROP_BIG = 0x0005;

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
//                    Intent intentFromGallery = new Intent();
//                    // 设置文件类型
//                    intentFromGallery.setType("image/*");
//                    intentFromGallery.setAction(Intent.ACTION_PICK);
//                    startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);



                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");//相片类型
//                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
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

    Uri uri = null;
    Bitmap photo = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK){
            switch (requestCode){
                case  REQUEST_CODE_CAPTURE_CAMEIA:
                    uri = Uri.fromFile(tempFile);
                    cropRawPhoto(uri, true);
                    break;
                case REQUEST_CODE_PICK_IMAGE:
                    uri = data.getData();
                    cropRawPhoto(uri, false);
                    break;
                case REQUEST_CODE_PICK_IMAGE_OLD:
                    uri = data.getData();
                    cropRawPhoto(uri, false);
                    break;
                case REQUEST_CODE_CROP_SMALL:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        photo = extras.getParcelable("data");
                        rivHeaderPhoto.setImageBitmap(photo);
                    }
                    break;
                case REQUEST_CODE_CROP_BIG:
                   if( uri != null){
                       try {
                           photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                       } catch (FileNotFoundException e) {
                           e.printStackTrace();
                       }
                       rivHeaderPhoto.setImageBitmap(photo);
                   }
                    break;
                default:
                    break;
            }
            LogUtils.i("URI", uri.toString());
        }


    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri, boolean isBig) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 225);
        intent.putExtra("outputY", 225);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        if( isBig){
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_CODE_CROP_BIG);
        } else {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_CROP_SMALL);
        }

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
