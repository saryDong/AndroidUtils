package com.abu.utilsmodule;

import android.content.Context;
import android.util.Log;

public class DisplayUtils {

    //讲px转换为与之相等的dp
    public static int px2dp(Context context, float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        Log.i("TAG",pxValue+"px"+"-----"+(int)(pxValue/scale+0.5f)+"dp");
        return (int)(pxValue/scale+0.5f);
    }

    //将dp转换为与之相等的px
    public static int dp2px(Context context, float dipValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        Log.i("TAG",dipValue+"dp"+"-----"+(int)(dipValue*scale+0.5f)+"px");
        return (int) (dipValue*scale+0.5f);
    }

    //将sp转换为px
    public static int sp2px(Context context, float spValue){
        final float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        Log.i("TAG",spValue+"sp"+"-----"+(int)(spValue*fontScale+0.5f)+"px");
        return (int) (spValue*fontScale+0.5f);
    }

    //将px转换为sp
    public static int px2sp(Context context, float pxValue){
        final float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        Log.i("TAG",pxValue+"px"+"-----"+(int)(pxValue/fontScale+0.5f)+"sp");
        return (int)(pxValue/fontScale+0.5f);
    }
}
