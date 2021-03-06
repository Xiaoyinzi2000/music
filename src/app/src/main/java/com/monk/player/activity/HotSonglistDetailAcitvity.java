package com.monk.player.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.TwoLineListItem;
import android.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.* ;
import com.example.andy.player.R;
import com.monk.player.adapter.HotSongListAdapter;
import com.monk.player.aidl.SongBean;
import com.monk.player.bean.BaseActivity;
import com.monk.player.bean.DownloadInfo;
import com.monk.player.bean.PlayeBean;
import com.monk.player.bean.SongListInfo;
import com.monk.player.http.HttpCallback;
import com.monk.player.http.HttpClient;
import com.monk.player.mvp.remote.RemoteMusicFragment;
import com.monk.player.tools.DownloadExcuter;
import com.monk.player.tools.LogUtil;
import com.monk.player.tools.ShareOnlineMusic;
import com.monk.player.tools.SongEvent;
import com.monk.player.tools.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotSonglistDetailAcitvity extends BaseActivity {

    List<SongBean> songBeanList;
    List<SongBean> recycleList;
    SongListInfo info;
    @BindView(R.id.disease_detail_image)
    ImageView detailImage;
    @BindView(R.id.hotsong_songList)
    RecyclerView hotsongRecycleList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    HotSongListAdapter hotSongListAdapter;
    HotSongListAdapter.OnRItemClickListner listner;
    private ProgressDialog mProgressDialog;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_hot_songlist_detail_acitvity;
    }

    @Override
    protected void initData() {
        super.initData();

        //隐藏状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
           getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));

        ButterKnife.bind(this);
        songBeanList = (ArrayList<SongBean>) getIntent().getSerializableExtra(RemoteMusicFragment.HOTSONG_EXTRA);
        info = getIntent().getParcelableExtra(RemoteMusicFragment.SONG_INFO);
        if (info!=null)
        initToolbar(info.Title,true,null);
        recycleList=new ArrayList<>();
        if(songBeanList.size()>=20){
        for(int i=0;i<20;i++){
            recycleList.add(songBeanList.get(i));
        }}else {
            for(int i=0;i<songBeanList.size();i++){
                recycleList.add(songBeanList.get(i));
            }
        }
        Glide.with(this)
                .load(info.imageUrl)
                .into(detailImage);
        hotSongListAdapter=new HotSongListAdapter(this,recycleList);
        hotsongRecycleList.setLayoutManager(new LinearLayoutManager(this));
        hotsongRecycleList.setAdapter(hotSongListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        listner=new HotSongListAdapter.OnRItemClickListner() {
            @Override
            public void onClick(final SongBean songBean) {
                LogUtil.doLog("onClick","doClick");
                HttpClient.getPlayerBean((int) songBean.getSongid(), new HttpCallback<PlayeBean>() {
                    @Override
                    public void onSuccess(PlayeBean playeBean) {
                        songBean.setM4a(playeBean.getBitrate().getFile_link());
                        EventBus.getDefault().post(new SongEvent(songBean));
                        fin();
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });

            }

            @Override
            public void onMoreClick(SongBean songBean) {
                onMoreClicks(songBean);
            }
        };
        hotSongListAdapter.setListner(listner);
    }

    public void fin(){
        finish();
    }

    private void onMoreClicks(final SongBean songBean){
        AlertDialog.Builder dialog = new AlertDialog.Builder(HotSonglistDetailAcitvity.this);
        dialog.setTitle(songBean.getSongname());
//        String path = FileUtils.getMusicDir() + FileUtils.getMp3FileName(song.getArtistname(), song.getSongname());
//        File file = new File(path);
        int itemsId =  R.array.search_music_dialog;
        dialog.setItems(itemsId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:// 分享
                        share(songBean);
                        break;
                    case 1:// 下载
                        HttpClient.getPlayerBean((int) songBean.getSongid(), new HttpCallback<PlayeBean>() {
                            @Override
                            public void onSuccess(PlayeBean playeBean) {
                                songBean.setM4a(playeBean.getBitrate().getFile_link());
                                download(songBean);
                            }

                            @Override
                            public void onFail(Exception e) {

                            }
                        });

                        break;
                }
            }
        });
        dialog.show();
    }

    private void download(final SongBean songBean) {
        new DownloadExcuter(this, songBean) {
            @Override
            public void onPrepare() {
                mProgressDialog.show();
            }

            @SuppressLint("StringFormatInvalid")
            @Override
            public void onExecuteSuccess(DownloadInfo info) {
                mProgressDialog.cancel();
                ToastUtils.show(HotSonglistDetailAcitvity.this.getString(R.string.now_download, songBean.getSongname()));
            }

            @Override
            public void onExecuteFail(Exception e) {
                mProgressDialog.cancel();
                ToastUtils.show(R.string.unable_to_download);
            }
        }.execute();
    }

    private void share(SongBean songBean) {
        new ShareOnlineMusic(this, songBean).execute();
    }
}
