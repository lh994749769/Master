package com.ooftf.service.utils;

import android.app.Activity;
import android.graphics.Bitmap;

import com.ooftf.hishare.DefaultShareCallback;
import com.ooftf.hishare.HiShare;
import com.ooftf.service.base.MyApplication;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

public class ShareUtil {
    static {
        HiShare.init(MyApplication.instance);
        HiShare.initWbShare("755399387");
    }

    public static void share(Activity activity, String targetUrl, String title, String content, Bitmap bitmap) {
        HiShare.share(activity, HiShare.ShareType.WI_BO, new HiShare.ShareParams(targetUrl, title, content, null, bitmap), new DefaultShareCallback());
    }
}
