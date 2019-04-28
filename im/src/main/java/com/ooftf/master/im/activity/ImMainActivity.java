package com.ooftf.master.im.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ooftf.master.im.R;
import com.ooftf.master.im.R2;
import com.ooftf.service.base.BaseActivity;
import com.ooftf.service.constant.RouterPath;
import com.tencent.qcloud.uikit.business.session.model.SessionInfo;
import com.tencent.qcloud.uikit.business.session.view.SessionPanel;
import com.tencent.qcloud.uikit.business.session.view.wedgit.SessionClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 99474
 */
@Route(path = RouterPath.IM_ACTIVITY_MAIN)
public class ImMainActivity extends BaseActivity {

    @BindView(R2.id.session_panel)
    SessionPanel sessionPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_main);
        ButterKnife.bind(this);
        sessionPanel.initDefault();
        sessionPanel.setSessionClick(session -> {
            //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
            if (session.isGroup()) {
                //如果是群组，跳转到群聊界面
                ARouter.getInstance().build(RouterPath.IM_ACTIVITY_GROUP_CHAT).withString("chatId", session.getPeer()).navigation();
            } else {
                //否则跳转到C2C单聊界面
                ARouter.getInstance().build(RouterPath.IM_ACTIVITY_PERSONAL_CHAT).withString("chatId", session.getPeer()).navigation();

            }
        });
    }

    /*@Override
    public int getToolbarId() {
        return R.id.session_title_panel;
    }

    @Override
    public boolean isDarkFont() {
        return true;
    }*/

    @Override
    public boolean isImmersionEnable() {
        return false;
    }
}
