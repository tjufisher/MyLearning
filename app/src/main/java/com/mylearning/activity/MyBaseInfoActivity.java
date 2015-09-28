package com.mylearning.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.utils.LogUtils;
import com.mylearning.view.MiddlePopWindow;
import com.mylearning.view.RoundImageView;

import java.io.BufferedOutputStream;
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
    private static final int REQUEST_CODE_PICK_IMAGE = 1;//相册


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
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.btn_pick_photo:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");//相片类型
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

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
