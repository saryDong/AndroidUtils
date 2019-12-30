package com.abu.utilsmodule;

import android.util.Log;

public class PreandNextUtil {
    private static volatile PreandNextUtil preandNextUtil;

    private static final String TAG=PreandNextUtil.class.getSimpleName();

    //初始化变量
    private int count=0;
    private int sum=0;
    private int totalPage=0;

    private static int EVERY_PAGE_NUM=6;  //每页显示数量
    private int curPage=0; //当前页面


    private PreandNextUtil(){}

    public static PreandNextUtil getInstance(){
        if (preandNextUtil==null){
            synchronized (PreandNextUtil.class){
                if (preandNextUtil==null){
                    preandNextUtil=new PreandNextUtil();
                }
            }
        }
        return preandNextUtil;
    }

    /**
     *  计算总页数
     * @param sum  数据总长度
     * @return
     */
    private int caculTotalPage(int sum){

        //计算是否为整页
        if (sum % EVERY_PAGE_NUM > 0){
            totalPage = totalPage+1;  //不满一页
        }
        //计算整页有几页
        int perPages=sum/EVERY_PAGE_NUM;

        if(perPages>0){
            totalPage=totalPage+perPages;
        }

        return totalPage;
    }


    public void init(int dataLength, int every_page_num,
                     OnrefreshDataLisener onrefreshDataLisener,
                     OnHidePreNextButtonLisener hidePreNextButtonLisener){
        sum=dataLength;
        EVERY_PAGE_NUM=every_page_num;
        totalPage=EVERY_PAGE_NUM>0?caculTotalPage(sum):0;
        lisener=onrefreshDataLisener;
        hideLisener=hidePreNextButtonLisener;
        if (sum>0){
            curPage=1;
        }
        //当数据不足一页时
        if(sum<EVERY_PAGE_NUM){
            for(int i=0;i<sum;i++){
                //将数据初始化到控件上
                count++;
            }
            lisener.onDataRefresh(0,sum-1);
            //隐藏上一页和下一页按钮
            hideLisener.hidePreandNextButton(false,false);
            Log.i("TAG","数据只有一页哦");
        }else{
            Log.i("TAG","当前为第一页");
            hideLisener.hidePreandNextButton(false,true);
            //当数据大于等于一页时
            for(int i=0;i<EVERY_PAGE_NUM;i++){
                //每适配一个数据,已显示数量加一
                count++;
            }
            //适配数据到控件
            lisener.onDataRefresh(0,EVERY_PAGE_NUM-1);

            /*if(totalPage==1){
                //隐藏上一页和下一页按钮
                hideLisener.hidePreandNextButton(false,false);
            }
            if(totalPage>1&&curPage==1){
                hideLisener.hidePreandNextButton(false,true);
            }*/

        }
        if (dataLength<=0||every_page_num<1){
            hideLisener.hidePreandNextButton(false,false);
            try {
                Log.i(TAG,"参数every_page_num值不能小于等于0或数据长度小于等于0");
                throw new Exception("参数every_page_num值不能小于等于0或数据长度小于等于0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //点击上一页
    public void prePage(){
        if (curPage<=1){
            Log.i(TAG,"数据已经显示为第一页!");
            hideLisener.hidePreandNextButton(false,true);
        }else {
            int lastIndex=EVERY_PAGE_NUM*(curPage-1)-1;
            int firstIndex=lastIndex-EVERY_PAGE_NUM+1;
            if(firstIndex>=0){
                curPage-=1;
                lisener.onDataRefresh(firstIndex,lastIndex);
                hideLisener.hidePreandNextButton(true,true);
                count=curPage*EVERY_PAGE_NUM;
            }
            if (curPage==1){
                hideLisener.hidePreandNextButton(false,true);
                Log.i(TAG,"数据已经显示为第一页!");
            }
        }
    }
    //获取当前页码
    public int getCurPage(){
        return curPage;
    }
    //获取数据总页数
    public int getTotalPage(){
        return totalPage;
    }

    //点击下一页
    public void nextPage(){
        if (curPage==totalPage){
            Log.i("TAG","数据已经显示最后一页！");
            hideLisener.hidePreandNextButton(true,false);
        }else {
            int firstIndex=count;
            int lastIndex=count+EVERY_PAGE_NUM-1;
            if (lastIndex+1>=sum){
                hideLisener.hidePreandNextButton(true,false);
            }
            if(lastIndex+1<=sum){
                for(int i=firstIndex;i<=lastIndex;i++){
                    count++;
                }
                curPage+=1;
                lisener.onDataRefresh(firstIndex,lastIndex);
                hideLisener.hidePreandNextButton(true,true);
            }else {
                lastIndex=sum-1;
                hideLisener.hidePreandNextButton(true,false);
                Log.i("TAG","数据已经显示最后一页！");
                for(int i=firstIndex;i<sum;i++){
                    //适配新数据
                    count++;
                }
                curPage+=1;
                lisener.onDataRefresh(firstIndex,lastIndex);
            }
        }
    }

    public void release(){
        if (preandNextUtil!=null){
            preandNextUtil=null;
        }
    }


    OnrefreshDataLisener lisener;
    public interface OnrefreshDataLisener{
        void onDataRefresh(int firstIndex, int lastIndex);
    }

    OnHidePreNextButtonLisener hideLisener;
    public interface OnHidePreNextButtonLisener{
        void hidePreandNextButton(boolean preButton, boolean NextButton);
    }
}
