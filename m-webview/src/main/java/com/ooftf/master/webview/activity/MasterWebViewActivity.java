package com.ooftf.master.webview.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ooftf.master.webview.R;
import com.ooftf.master.webview.bean.GridPanelBean;
import com.ooftf.master.webview.databinding.ActivityMasterWebviewBinding;
import com.ooftf.master.webview.engine.JsInjector;
import com.ooftf.master.webview.utils.WebViewUtil;
import com.ooftf.master.webview.widget.ImgSrcHandlerDialog;
import com.ooftf.master.widget.dialog.ui.GridPanelDialog;
import com.ooftf.arch.frame.mvvm.activity.BaseActivity;
import com.ooftf.service.constant.RouterPath;
import com.ooftf.service.utils.extend.ToastExtendKt;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * @author ooftf
 */
@Route(path = RouterPath.Web.Activity.MAIN)
public class MasterWebViewActivity extends BaseActivity {
    JsInjector jsInjector = new JsInjector();
    @Autowired
    String url;
    ActivityMasterWebviewBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_master_webview);
        initToolbar();
        ARouter.getInstance().inject(this);
        binding.smartRefreshLayout.setOnRefreshListener(refreshLayout -> binding.webView.reload());
        initWebView();

    }

    private void initWebView() {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("jmimage:")) {
                    new ImgSrcHandlerDialog(MasterWebViewActivity.this, url.replace("jmimage:", "")).show();
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @SuppressLint("CheckResult")
            @Override
            public void onPageFinished(WebView view, String url) {
                jsInjector.inject(view);
            }
        });
        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title.length() > 8) {
                    binding.toolbar.setTitle(title.substring(0, 8));
                } else {
                    binding.toolbar.setTitle(title);
                }

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    binding.progress.setVisibility(View.VISIBLE);
                } else {
                    binding.progress.setVisibility(View.GONE);
                    binding.smartRefreshLayout.finishRefresh();
                }
                binding.progress.setProgress(newProgress);
            }
        });


        binding.webView.setDownloadListener((s, s1, s2, s3, l) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(url);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(uri);
            startActivity(intent);
        });

        if (url != null) {
            binding.webView.loadUrl(url);
        } else {
            ToastExtendKt.toast("Url == null");
        }
    }

    GridPanelDialog<GridPanelBean> gridPanelDialog;

    private void initToolbar() {
        binding.toolbar.getMenu().add("JS面板").setIcon(R.drawable.ic_more_horiz).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS).setOnMenuItemClickListener(item -> {
            if (gridPanelDialog == null) {
                gridPanelDialog = new GridPanelDialog<GridPanelBean>(MasterWebViewActivity.this)
                        .setItemAdapter((item1, position, icon, textView, itemRoot, dialog) -> {
                            icon.setImageResource(item1.iconId);
                            textView.setText(item1.text);
                            itemRoot.setOnClickListener(v -> {
                                switch (position) {
                                    case 0:
                                        copyToClipboard(MasterWebViewActivity.this, binding.webView.getUrl());
                                        break;
                                    case 1:
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        Uri content_url = Uri.parse(binding.webView.getUrl());
                                        intent.setData(content_url);
                                        startActivity(intent);
                                        break;
                                    case 2:
                                        binding.webView.reload();
                                        break;
                                    default:
                                }
                                dialog.dismiss();
                            });
                        })
                        .setList(new ArrayList<GridPanelBean>() {
                            {
                                add(new GridPanelBean("复制链接", R.drawable.ic_link));
                                add(new GridPanelBean("浏览器打开", R.drawable.ic_browser));
                                add(new GridPanelBean("刷新", R.drawable.ic_refresh2));
                                add(new GridPanelBean("分享到手机QQ", R.drawable.ic_qq));
                                add(new GridPanelBean("分享到微博", R.drawable.ic_wb));
                                add(new GridPanelBean("分享到QQ空间", R.drawable.ic_qq_zone));
                                add(new GridPanelBean("分享到微信好友", R.drawable.ic_wx));
                                add(new GridPanelBean("分享到朋友圈", R.drawable.ic_time_line));
                            }
                        });
            }
            gridPanelDialog.show();
            return true;
        });
    }

    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(text); // 复制
    }

    @Override
    public void onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebViewUtil.destory(binding.webView);
    }
}
