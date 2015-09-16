package com.mylearning.fragement;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylearning.R;
import com.mylearning.common.Constants;
import com.mylearning.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by user on 2015/8/17.
 */
public class MyFragement extends Fragment {
    @InjectView(R.id.personal_mine_base_avatar)
    ImageView personalMineBaseAvatar;
    @InjectView(R.id.personal_mine_title)
    TextView personalMineTitle;
    @InjectView(R.id.personal_mine_winning_time)
    TextView personalMineWinningTime;
    @InjectView(R.id.daily_arrow_right)
    ImageView dailyArrowRight;
    @InjectView(R.id.personal_mine_base_info)
    RelativeLayout personalMineBaseInfo;
    @InjectView(R.id.personal_mine_bean_text)
    TextView personalMineBeanText;
    @InjectView(R.id.arrow_right)
    ImageView arrowRight;
    @InjectView(R.id.personal_mine_bean)
    RelativeLayout personalMineBean;
    @InjectView(R.id.personal_mine_integral_label)
    TextView personalMineIntegralLabel;
    @InjectView(R.id.personal_mine_integral_red_point)
    ImageView personalMineIntegralRedPoint;
    @InjectView(R.id.personal_mine_integral_text)
    TextView personalMineIntegralText;
    @InjectView(R.id.personal_mine_integral)
    RelativeLayout personalMineIntegral;
    @InjectView(R.id.personal_mine_comment_label)
    TextView personalMineCommentLabel;
    @InjectView(R.id.personal_mine_comment_text)
    TextView personalMineCommentText;
    @InjectView(R.id.personal_mine_comment)
    RelativeLayout personalMineComment;
    @InjectView(R.id.personal_mine_message_label)
    TextView personalMineMessageLabel;
    @InjectView(R.id.personal_mine_message_red_point)
    ImageView personalMineMessageRedPoint;
    @InjectView(R.id.my_favorite_count)
    TextView myFavoriteCount;
    @InjectView(R.id.personal_mine_message)
    RelativeLayout personalMineMessage;
    @InjectView(R.id.personal_mine_auth_text)
    TextView personalMineAuthText;
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
    @InjectView(R.id.personal_mine_help_text)
    TextView personalMineHelpText;
    @InjectView(R.id.personal_mine_help)
    RelativeLayout personalMineHelp;
    @InjectView(R.id.personal_mine_suggestion_text)
    TextView personalMineSuggestionText;
    @InjectView(R.id.personal_mine_suggestion)
    RelativeLayout personalMineSuggestion;
    @InjectView(R.id.personal_mine_about_text)
    TextView personalMineAboutText;
    @InjectView(R.id.personal_mine_about)
    RelativeLayout personalMineAbout;
    @InjectView(R.id.personal_mine_update_text)
    TextView personalMineUpdateText;
    @InjectView(R.id.personal_mine_update)
    RelativeLayout personalMineUpdate;
    @InjectView(R.id.main_scrollview)
    ScrollView mainScrollview;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(getClass().getName(), "create view");
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragement_my, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.personal_mine_update)
    public void change(){
        Constants.DEVELOP_POSITION = (Constants.DEVELOP_POSITION + 1) % 3;
        switch (Constants.DEVELOP_POSITION){
            case 0:
                Toast.makeText(mContext, "default workspace", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(mContext, "work workspace", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(mContext, "home workspace", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
