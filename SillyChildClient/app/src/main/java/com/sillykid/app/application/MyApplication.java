package com.sillykid.app.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.common.StringConstants;
import com.common.cklibrary.utils.GlideCatchUtil;
import com.lzy.imagepicker.ImagePicker;
import com.sillykid.app.BuildConfig;

import com.sillykid.app.utils.GlideImageLoader;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.sillykid.app.message.interactivemessage.imuitl.RongCloudEvent;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

import static com.umeng.socialize.utils.Log.LOGTAG;


/**
 * 程序入口
 * Created by Administrator on 2016/5/13.
 */
public class MyApplication extends Application {

    public static MyApplication instance;
    private static Context mContext;

    /**
     * 解除65k限制,让应用支持多DEX文件
     * http://blog.csdn.net/fjnu_se/article/details/73302290
     *
     * @param base 如果你的Application类已经继承自其它类，你不想修改它，那么可以重写attachBaseContext()方法：
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //错误日志保存本地，并强制重启程序
//        LocalCrashHandler crashHandler = LocalCrashHandler.getInstance();
//        crashHandler.init(this);
        initJPushInterface();
        initBaiDuSDK();
        instance = this;
        mContext = getApplicationContext();
        UMShareAPI.get(this);//友盟分享
        initRongCloud();
        initImagePicker();
        testMemoryInfo();
    }

    /**
     * 初始化图片加载器
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        imagePicker.setImageLoader(glideImageLoader);   //设置图片加载器
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public void finishActivity() {
        KJActivityStack.create().finishAllActivity();
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    // 各个平台的配置，建议放在全局Application或者程序入口
    {
        // 微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin(BuildConfig.WEIXIN_APPKEY, BuildConfig.WEIXIN_APPSECRET);
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone(BuildConfig.QQ_APPID, BuildConfig.QQ_APPKEY);
        // PlatformConfig.setSinaWeibo(BuildConfig.SiNA_WEIBOKEY, BuildConfig.SiNA_WEIBOSECRET, "http://sns.whalecloud.com");
        Config.DEBUG = true;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 极光推送所需
     */
    private void initJPushInterface() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        JPushInterface.initCrashHandler(this);// JPush开启CrashLog上报
        JPushInterface.getRegistrationID(getApplicationContext());//获取设备RegistrationID   注：每个设备只有一个
        Log.d("JPush", JPushInterface.getRegistrationID(getApplicationContext()));
    }

    /**
     * 百度定位以及地图所需
     */
    private void initBaiDuSDK() {
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        // SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * 调用融云初始化方法
     */
    private void initRongCloud() {
        RongIM.init(this);
        RongCloudEvent.init(this);
    }

    //查询缓存
    public void testMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int largeMemoryClass = activityManager.getLargeMemoryClass();
        int memoryClass = activityManager.getMemoryClass();
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        Log.d(LOGTAG, "largeMemoryClass = " + largeMemoryClass);
        Log.d(LOGTAG, "memoryClass = " + memoryClass);
        Log.d(LOGTAG, "availMem = " + (info.availMem / 1024 / 1024) + "M");
        Log.d(LOGTAG, "lowMemory = " + info.lowMemory);
        long size = GlideCatchUtil.getInstance().getCacheSize();
        if (size >= StringConstants.GLIDE_CATCH_SIZE) {
            // 必须在UI线程中调用
            GlideCatchUtil.getInstance().clearCacheMemory();
            // 必须在后台线程中调用，建议同时clearMemory()
            GlideCatchUtil.getInstance().clearCacheDiskSelf();
//            GlideCatchUtil.getInstance().cleanCatchDisk();
        }
    }
}