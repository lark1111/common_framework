package com.imindera.wgy.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhouyu on 2018/9/4.
 * ACTION:多次使用的通用工具类
 * TYPE:工具类
 */
public class CommonUtils {

    /**
     * 统一处理标题栏
     *
     * @param ctx            上下文
     * @param isActivity     是否为Activity
     * @param view           fragment需要用到的布局参数
     * @param leftContent    左边展示内容
     * @param leftListener   左边点击事件
     * @param leftResource   左边是否是回退键
     * @param leftX          左边是否是"X"号
     * @param centerResource 中间是否有图片
     * @param centerResId    中间图片资源地址
     * @param centerContent  中间文字
     * @param rightContent   右边展示内容
     * @param rightListener  右边点击事件
     * @param rightResource  右边是否是图片
     * @param rightResId     右边图片资源地址
     */
    public static void handleTitleBar(final Activity ctx, boolean isActivity, View view, String
            leftContent, View.OnClickListener leftListener, boolean leftResource, boolean leftX,
                                      boolean centerResource, int centerResId, String
                                              centerContent, String rightContent, View
                                              .OnClickListener rightListener, boolean
                                              rightResource, int rightResId) {
        //处理左边,可能文字,回退键,什么都没有
        ImageView leftBack;
        ImageView leftClose;
        TextView leftShow;

    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * list是否为空判断
     *
     * @param list
     * @return
     */
    public static boolean isEmptyList(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 自定义EditText hinit大小 默认14
     *
     * @param editText
     */
    public static void setHintSize(EditText editText) {
        setHintTextSize(editText, 14);
    }

    public static void setHintTextSize(EditText editText, int hintTextSize) {
        SpannableString hintSpan = new SpannableString(editText.getHint());//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(hintTextSize, true);//设置字体大小 true表示单位是dp
        hintSpan.setSpan(ass, 0, hintSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(hintSpan));
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    public static String subInt(String str) {

        if (str != null && !"".equals(str) && str.length() > 2 && str.contains(".")) {
            double doubleStr = Double.parseDouble(str);
            int intStr = (int) doubleStr;
            return String.valueOf(intStr);
        }
        return str;
    }

    /**
     * 多个标题栏切换Fragment通用方法
     *
     * @param mContent 用于置换页面的第三个参数
     * @param from     当前Fragment
     * @param to       切换后的Fragment
     * @param FrameId  FrameLayout的Id
     * @param ctx      Activity的上下文
     */
    public static Fragment switchFragmentContent(Fragment mContent, Fragment from, Fragment to,
                                                 int FrameId, FragmentActivity ctx) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction tran = ctx.getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                tran.hide(from).add(FrameId, to);
            } else {
                tran.hide(from).show(to);
            }
            tran.commitAllowingStateLoss();
        }
        return mContent;
    }

    /**
     * isMobie
     * 手机号是否合法验证
     *
     * @param mobiles 手机号
     * @return 返回值
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 隐藏手机号码中间4位
     *
     * @return
     */
    public static String hidePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() >= 11) {
            phoneNumber = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11);
        }

        return phoneNumber;
    }

    /**
     * 隐藏用户名保留姓氏
     *
     * @return
     */
    public static String hideUserName(String userName) {
        if (userName.length() > 0) {
            String firstName = userName.substring(0, 1);
            String lastName = userName.substring(1).replaceAll(".", "*");
            userName = firstName + lastName;
        }

        return userName;
    }


    /**
     * 验证密码是否是数字+字母+符号的组合
     *
     * @param password 密码
     * @return 返回值
     */
    public static boolean isPassword(String password) {
        boolean ret;
        String s = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern p = Pattern.compile(s);
        Matcher m = p.matcher(password);
        ret = m.matches();
        return ret;
    }

    /**
     * 身份证号是否合法验证
     *
     * @param idCard 身份证号
     * @return 返回值
     */
    public static boolean isIdCard(String idCard) {
        if (idCard.length() == 15) {
            Pattern p = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])" +
                    "\\d{3}$");
            Matcher m = p.matcher(idCard);
            return m.matches();
        } else if (idCard.length() == 18) {
            Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)" +
                    "|3[0-1])\\d{3}([0-9]|X)$");
            Matcher m = p.matcher(idCard);
            return m.matches();
        } else {
            return false;
        }
    }


    public static String getRandom() {
        int max = 9999;
        int min = 100;
        Random random = new Random();

        String s = String.valueOf(random.nextInt(max) % (max - min + 1) + min);
        return s;
    }

    /**
     * 获得统一上传参数
     *
     * @return 返回拼接好的map集合用于做下一步拼接上传参数
     */
    public static HashMap<String, String> getUpNetworkHashMap() {
        HashMap<String, String> map = new HashMap<>();
        //用户中心统一处理的token，存放在radius中
        map.put("token", "");
        //设备渠道
        map.put("deviceType", "");
        //项目标识
        map.put("proId", getProId());
        //app版本号
//        map.put("version", versionName);
        //时间戳
        map.put("t", String.valueOf(System.currentTimeMillis()));

        String model;
        //机型
        try {
            model = Build.MODEL;
        } catch (Exception e) {
            model = "android";
        }
        if (model == null || "".equals(model)) {
            model = "android";
        }
        //渠道id
        map.put("proId", getProId());
        return map;
    }

    /**
     * 获取imei
     *
     * @param context 上下文
     * @return 手机唯一标识符
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    private static String getImei(Context context) {
        String imei = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager
                    .PERMISSION_GRANTED) {
                TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context
                        .TELEPHONY_SERVICE);
                try {
                    if (mTm != null) {
                        imei = mTm.getDeviceId();
                        imei = imei.trim();
                        String[] s = imei.split(" ");
                        StringBuilder imeiBuilder = new StringBuilder();
                        for (String s1 : s) {
                            imeiBuilder.append(s1);
                        }
                        imei = imeiBuilder.toString();
                    }
                } catch (Exception e) {
                    imei = "";
                }
            } else {
                imei = "";
            }
            return imei;
        }
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        try {
            if (mTm != null) {
                imei = mTm.getDeviceId();
                imei = imei.trim();
                String[] s = imei.split(" ");
                StringBuilder imeiBuilder = new StringBuilder();
                for (String s1 : s) {
                    imeiBuilder.append(s1);
                }
                imei = imeiBuilder.toString();
            }
        } catch (Exception e) {
            imei = "";
        }
        return imei;
    }

    /**
     * 获取电信运营商
     *
     * @param context 上下文
     * @return 电信运营商
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    private static String getOperator(Context context) {
        String operatorName = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager
                    .PERMISSION_GRANTED) {
                TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context
                        .TELEPHONY_SERVICE);
                try {
                    if (mTm != null) {
                        operatorName = mTm.getSimOperator();
                        operatorName = operatorName.trim();
                        String[] s = operatorName.split(" ");
                        StringBuilder nameBuilder = new StringBuilder();
                        for (String s1 : s) {
                            nameBuilder.append(s1);
                        }
                        operatorName = nameBuilder.toString();
                    } else {
                        operatorName = "";
                    }
                } catch (Exception e) {
                    operatorName = "";
                }
            } else {
                operatorName = "";
            }
            return operatorName;
        }
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        try {
            if (mTm != null) {
                operatorName = mTm.getSimOperator();
                operatorName = operatorName.trim();
                String[] s = operatorName.split(" ");
                StringBuilder nameBuilder = new StringBuilder();
                for (String s1 : s) {
                    nameBuilder.append(s1);
                }
                operatorName = nameBuilder.toString();
            }
        } catch (Exception e) {
            operatorName = "";
        }
        return operatorName;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getImei(Activity context) {
        String imei = "";
        if (getPermition(context)) {
            TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context
                    .TELEPHONY_SERVICE);
            try {
                if (mTm != null) {
                    imei = mTm.getDeviceId();
                    imei = imei.trim();
                    String[] s = imei.split(" ");
                    StringBuilder imeiBuilder = new StringBuilder();
                    for (String s1 : s) {
                        imeiBuilder.append(s1);
                    }
                    imei = imeiBuilder.toString();
                }
            } catch (Exception e) {
                imei = "";
            }
        } else {
            imei = "";
        }
        return imei;
    }

    public static String getUnitType(Activity context) {
        String model = "";
        if (getPermition(context)) {
            TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context
                    .TELEPHONY_SERVICE);
            try {
                model = Build.MODEL.trim();
                String[] ss = model.split(" ");
                model = "";
                for (String ss1 : ss) {
                    model = model + ss1;
                }
            } catch (Exception e) {
                model = "android";
            }
        } else {
            model = "android";
        }
        return model;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getUpCount(Activity context, String url) {
        String ret = "";
        String imei = "";
        String model = "";
        if (getPermition(context)) {
            TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context
                    .TELEPHONY_SERVICE);
            try {
                imei = mTm.getDeviceId();
                String[] s = imei.split(" ");
                imei = "";
                for (String s1 : s) {
                    imei = imei + s1;
                }
                model = Build.MODEL.trim();
                String[] ss = model.split(" ");
                model = "";
                for (String ss1 : ss) {
                    model = model + ss1;
                }
            } catch (Exception e) {
                imei = "";
                model = "android";
            }
        } else {
            imei = "";
            model = "android";
        }

        PackageInfo appManager;
        String version = "6";
        try {
            appManager = context.getPackageManager().getPackageInfo
                    (context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            appManager = null;
        }
        if (appManager != null) {
            version = String.valueOf(appManager.versionCode);
        }

        return ret;
    }

    //获取权限
    private static boolean getPermition(Activity activity) {
        if (activity == null) {
            return false;
        }
        boolean ret = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //动态获取手机信息如imei，手机号码
            if (activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager
                    .PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission
                        .READ_PHONE_STATE}, 1);
                ret = false;
            } else if (activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_GRANTED) {
                ret = true;
            }
        } else {
            ret = true;
        }
        return ret;
    }

    /**
     * 获得渠道id
     *
     * @return 返回项目id
     */
    public static String getProId() {
        String ret = "";
        return ret;
    }

    /**
     * 拨打电话
     *
     * @param phoneNum 电话号码
     * @param context  上下文
     * @return 返回成功或者失败
     */
    public static boolean callPhone(String phoneNum, Context context) {
        try {
            Uri uri = Uri.parse("tel:" + phoneNum);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "抱歉，拨打电话失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 判断网络状态
     *
     * @param status 返回状态
     * @return
     */
    public static boolean responseSuccess(String status, boolean hasData, Object data) {
        if (hasData) {
            return "1".equals(status) && data != null;
        }
        return "1".equals(status);
    }

    /**
     * 模糊处理手机号
     *
     * @param mobile 返回状态
     * @return
     */
    public static String indistinctMobile(String mobile) {
        String ret = "";
        if (isMobileNO(mobile)) {
            String s = mobile.substring(0, 3);
            String s1 = mobile.substring(7, 11);
            ret = s + "****" + s1;
        } else {
            ret = "***********";
        }
        return ret;
    }

    public static String indistinctMobile2(String mobile) {
        String ret = "";
        String s1 = mobile.substring(7, 11);
        ret = "**" + s1;
        return ret;
    }

    /**
     * 模糊处理身份证号
     *
     * @param idCardNo 传入参数
     * @return 返回结果
     */
    public static String indistinctIdCardNo(String idCardNo) {
        String ret = "";
        if (idCardNo == null || "".equals(idCardNo)) {
            return ret;
        }
        if (idCardNo.length() == 18) {
            String s = idCardNo.substring(0, 4);
            String s1 = idCardNo.substring(14, 18);
            ret = s + "********" + s1;
        } else if (idCardNo.length() == 15) {
            String s = idCardNo.substring(0, 4);
            String s1 = idCardNo.substring(11, 15);
            ret = s + "*****" + s1;
        } else {
            ret = idCardNo;
        }
        return ret;
    }

    /**
     * 迷糊处理用户身份姓名
     *
     * @param idCardName //传入的卡用户名
     * @return
     */
    public static String indistinctIdCardName(String idCardName) {
        String ret = "";
        if (TextUtils.isEmpty(idCardName)) {
            return ret;
        }
        if (idCardName.length() == 1) {
            return idCardName;
        }
        String frontName = idCardName.substring(0, idCardName.length() - 1);
        ret = frontName + "*";
        return ret;
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception ignored) {

        }
        return hasNavigationBar;
    }

    /**
     * base64加密
     *
     * @param string
     * @return
     */
    public static String getEcode(String string) {
        if (!TextUtils.isEmpty(string)) {
            String pwd = new String(Base64.encode(string.getBytes(), Base64.DEFAULT));
            pwd = pwd.replaceAll("\n", "");
            pwd = pwd.replaceAll("\r", "");
            return pwd;
        }
        return "";
    }

    public static List<String> getImageListName(String path) {
        List<String> imageList = new ArrayList<String>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            if (files[j].isFile() & files[j].getName().endsWith(".jpg")) {
                imageList.add(files[j].getName());
            }
        }
        return imageList;
    }

    /**
     * 加载本地图片
     *
     * @param url the file url
     * @return Bitmap
     */
    public static Bitmap getLoacalBitmap(String url) {
        Bitmap bitmap = null;
        try {
            FileInputStream fis = new FileInputStream(url);
            bitmap = BitmapFactory.decodeStream(fis);
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 发送短信
     *
     * @param smsBody
     */

    public static void sendSMS(String smsBody, Context context) {
        Uri smsToUri = Uri.parse("smsto:10086");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }

    /**
     * 二进制转字符串
     *
     * @param b byte数组
     * @return 二进制字符串
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    /** */
    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 权限判断
     *
     * @param context    上下文
     * @param permission 权限名称
     * @return 返回结果
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isPermission(Activity context, String permission) {
        int s = context.checkSelfPermission(permission);
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取当前没有的权限
     *
     * @param context     上下文
     * @param permission  权限名称
     * @param requestCode 请求码
     * @return 返回结果
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean getPermission(Activity context, String permission, int requestCode) {
        if (!isPermission(context, permission)) {
            context.requestPermissions(new String[]{permission}, requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 从字符串文本中获得数字
     *
     * @param text
     * @return
     */
    public static String getDigit(String text) {
        StringBuffer digitList = new StringBuffer();
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(text);
        while (m.find()) {
            String find = m.group(1);
            digitList.append(find);
        }
        return digitList.toString();
    }

    /**
     * 检查的用户上传网络的字符
     *
     * @param input
     * @return
     */
    public static boolean checkIsUnLawful(String[] input) {
        if (input != null && input.length > 0) {
            for (int i = 0; i < input.length; i++) {
                String length = input[i];
                int codePoint;
                if (!TextUtils.isEmpty(length)) {
                    for (int j = 0; j < length.length(); j += Character.charCount(codePoint)) {
                        codePoint = length.codePointAt(j);

                        if ((codePoint >= 0xd800 && codePoint <= 0xdfff) || codePoint > 0x10ffff) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 检查的用户上传网络的字符
     *
     * @param input
     * @retur
     */
    public static boolean checkIsUnLawful2(String input) {
        String length = input;
        int codePoint;
        if (!TextUtils.isEmpty(length)) {
            for (int j = 0; j < length.length(); j += Character.charCount(codePoint)) {
                codePoint = length.codePointAt(j);

                if ((codePoint >= 0xd800 && codePoint <= 0xdfff) || codePoint > 0x10ffff) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 图片转化成base64字符串
     *
     * @param path 待处理的图片路径
     * @return base字符串
     */
    public static String Image2Base64String(String path) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            return "";
        }
        //对字节数组Base64编码
        //返回Base64编码过的字节数组字符串
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * 数据转化成base64字符串
     *
     * @param data 待处理的数据
     * @return base64字符串
     */
    public static String data2Base64String(String data) {
        //将数据转化为字节数组字符串，并对其进行Base64编码处理
        //对字节数组Base64编码
        //返回Base64编码过的字节数组字符串
        return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
    }

    /**
     * 压缩并且保存图片
     *
     * @param filePath  图片路径
     * @param context   上下文
     * @param reqWidth  宽
     * @param reqHeight 高
     */
    public static void scaleSaveBmFile(String filePath, Context context, float reqWidth, float reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        // Calculate inSampleSize
        options.inSampleSize = inSampleSize;
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        try {
            if (bm != null) {
                // 保存图片
                File file1 = new File(filePath);
                if (!file1.getParentFile().exists()) {
                    file1.getParentFile().mkdirs();
                }
                if (!file1.exists()) {
                    file1.createNewFile();
                }
                FileOutputStream fos1 = new FileOutputStream(file1);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, fos1);
                fos1.flush();
                fos1.close();
            }
        } catch (IOException e) {
            Toast.makeText(context, "图片保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 交易密码复杂度校验
     *
     * @param pwd
     * @return
     */
    public static boolean passwordRule(String pwd) {

        // 全一样
        if (pwd.matches("^(\\d)\\1+$")) {
            return false;
        }
        // 顺增或者顺减
        if (pwd.matches("(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){5}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){5})\\d")) {
            return false;
        }
        return true;
    }

    /**
     * base64转drawable
     *
     * @param base64
     * @return
     */
    public static Drawable base64ToDrawable(String base64) {
        BitmapDrawable drawable = null;
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        if (bytes.length != 0) {
            Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            drawable = new BitmapDrawable(bit);
        }
        return drawable;
    }

    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     *
     * @param s     需要格式化的数字
     * @param scale 小数点后保留几位
     * @return
     */
    public static String roundByScale(String s, int scale) {
        if (s == null) {
            return "0.00";
        }
        Double v = Double.valueOf(s);

        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if (scale == 0) {
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

    /**
     * 判断是否安装了客户端
     *
     * @param context
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {

        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 判断是否安装了支付宝
     *
     * @param context
     * @return
     */
    public static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 字符串没四位中间添加一个空格
     *
     * @param s
     * @return
     */
    public static String insertSpace(String s) {
        if (!TextUtils.isEmpty(s)) {
            String regex = "(.{4})";
            s = s.replaceAll(regex, "$1 ");
            return s;
        }
        return "";
    }

    /**
     * 半角转换为全角
     *
     * @param str
     * @return
     */

    public static String toDBC(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static String handleQrCode(String qrCodeOld) {
        String qrCodeNew = "";
        String s1 = qrCodeOld.substring(0, 4);
        s1 = handleAddEmptyString(s1);
        String s2 = qrCodeOld.substring(4, 8);
        s2 = handleAddEmptyString(s2);
        String s3 = qrCodeOld.substring(8, 12);
        s3 = handleAddEmptyString(s3);
        String s4 = qrCodeOld.substring(12, qrCodeOld.length());
        s4 = handleAddEmptyString(s4);
        qrCodeNew = s1 + "    " + s2 + "    " + s3 + "    " + s4;
        return qrCodeNew;
    }

    private static String handleAddEmptyString(String s) {
        StringBuilder s1 = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != s.length() - 1) {
                s1.append(s.substring(i, i + 1)).append(" ");
            } else {
                s1.append(s.substring(i, i + 1));
            }
        }
        return s1.toString();
    }


    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        if (("").equals(serviceName) || serviceName == null) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService =
                (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                        .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName()
                    .equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    public static void glideLoad(Context context, String url, int defultRes, ImageView view) {
        if (TextUtils.isEmpty(url)) {
            view.setImageDrawable(context.getResources().getDrawable(defultRes));
            return;
        }
        Glide.with(context)
                .load(url)
                .error(defultRes)
                .crossFade(0)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public static void glideLoad(Context context, String url, ImageView view) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .crossFade(0)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        }
    }
}