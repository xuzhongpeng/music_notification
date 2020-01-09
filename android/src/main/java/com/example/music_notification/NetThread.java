package com.example.music_notification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import java.io.IOException;
import java.net.URL;

public class NetThread  extends  Thread{
    String Url;
    private RemoteViews remoteViews;
    NetThread(String Url, RemoteViews remoteViews){
        this.Url=Url;
        this.remoteViews=remoteViews;
    }
    @Override
    public void run(){
        super.run();
        try {
            Message message = new Message();
            message.obj="成功";
            remoteViews.setImageViewBitmap(R.id.notificationImageView,getImage());
        }catch (Exception e){

        }
    }
    Bitmap getImage()  throws Exception{
        try {
            URL url = new URL(Url);
            String responseCode = url.openConnection().getHeaderField(0);
            if (responseCode.indexOf("200") < 0)
                throw new Exception("图片文件不存在或路径错误，错误代码：" + responseCode);
            return BitmapFactory.decodeStream(url.openStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new Exception(e.getMessage());
        }
    }
}
