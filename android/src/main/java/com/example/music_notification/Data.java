package com.example.music_notification;
import android.app.Application;
import android.content.Context;
import android.widget.RemoteViews;

public class Data extends Application{
    static Data instance;
    private  Boolean isPlaying=false;
    static public Data getInstance(){
        if(instance==null){
            instance=new Data();
        }
        return instance;
    }
    public Boolean getIsPlaying() {
        return isPlaying;
    }
    public void setIsPlaying(Boolean score) {
        this.isPlaying = score;
    }
    public void changePlayingState(Context context){
        this.isPlaying=!this.isPlaying;
        setPlayingState(context);
    }
    public void setPlayingState(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getApplicationContext().getPackageName(),
                R.layout.notification);
        RemoteViews sremoteViews = new RemoteViews(context.getApplicationContext().getPackageName(),
                R.layout.notificationsmal);
        if(this.isPlaying){
            remoteViews.setInt(R.id.play,"setBackgroundResource",R.raw.play);
            sremoteViews.setInt(R.id.play1,"setBackgroundResource",R.raw.play);
        }else{
            remoteViews.setInt(R.id.play,"setBackgroundResource",R.raw.stop);
            sremoteViews.setInt(R.id.play1,"setBackgroundResource",R.raw.stop);
        }
    }
}
