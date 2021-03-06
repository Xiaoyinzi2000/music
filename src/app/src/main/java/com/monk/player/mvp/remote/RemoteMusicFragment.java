package com.monk.player.mvp.remote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andy.player.R;
import com.monk.player.activity.HotSonglistDetailAcitvity;
import com.monk.player.adapter.RemoteListAdapter;
import com.monk.player.aidl.SongBean;
import com.monk.player.bean.SongListInfo;
import com.monk.player.mvp.base.MvpFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by andy on 2017/12/1.
 */

public class RemoteMusicFragment extends MvpFragment<RemotePresenter> {
    public static final String HOTSONG_EXTRA="hotsongExtract";
    public static final String SONG_INFO="songInfo";
    List<SongBean> baiduHotSongList;
    List<SongBean> baiduNewSongList;
    List<SongBean> chineseGoldenMelodyList;
    List<SongBean> euramerican;
    List<SongBean> goldenSongList;
    List<SongBean> loveSongsToTheList;
    List<SongBean> onlineSongList;
    List<SongBean> listOfClassicOldSongs;
    List<SongBean> listOfRock;
    List<SongBean> ktvHotSongList;
    List<SongBean> Billboard;
    List<SongBean> hitoChineseList;
    List<SongBean> powerSongList;
    @BindView(R.id.lv_playlist)
    RecyclerView lvPlaylist;
    @BindView(R.id.tv_loading_text)
    TextView tvLoadingText;
    @BindView(R.id.tv_load_fail_text)
    TextView tvLoadFailText;
    Unbinder unbinder;
    private List<SongListInfo> listInfos=new ArrayList<>();
    private RemoteListAdapter listAdapter;
    public int loadcoutn=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public RemotePresenter createPresenter() {
        return new RemotePresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void initEvent() {
        RemoteListAdapter.OnRItemClickListner listner=new RemoteListAdapter.OnRItemClickListner() {
            @Override
            public void onClick(SongListInfo info) {
                List<SongBean> hotlist=getHotSongList(info.type);
                if(hotlist!=null){
                    Intent intent=new Intent(getActivity(), HotSonglistDetailAcitvity.class);
                    intent.putExtra(HOTSONG_EXTRA,(Serializable)hotlist);
                    intent.putExtra(SONG_INFO,info);
                    startActivity(intent);
                }
            }
        };
        listAdapter.setListner(listner);
    }

    @Override
    protected void initData() {
        //??????????????????
        initSonglistInfo();
        lvPlaylist.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter=new RemoteListAdapter(getContext(),listInfos);
        lvPlaylist.setAdapter(listAdapter);
       mPresenter.getSonglit(2);
       mPresenter.getSonglit(1);
       mPresenter.getSonglit(20);
       mPresenter.getSonglit(21);
       mPresenter.getSonglit(23);
       mPresenter.getSonglit(22);
       mPresenter.getSonglit(25);
       mPresenter.getSonglit(11);
       mPresenter.getSonglit(6);
       mPresenter.getSonglit(7);
       mPresenter.getSonglit(8);
        mPresenter.getSonglit(18);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//  <item>????????????</item>
//        <item>???????????????</item>
//        <item>???????????????</item>
//        <item>????????????</item>
//        <item>???????????????</item>
//        <item>???????????????</item>
//        <item>???????????????</item>
//        <item>???????????????</item>
//        <item>???????????????</item>
//        <item>???????????????</item>
//        <item>?????????</item>
//        <item>????????????</item>
//        <item>KTV?????????</item>
//        <item>Billboard</item>
//        <item>Hito?????????</item>
//        <item>???????????????</item>

//     <item>#</item>
//        <item>2</item>
//        <item>1</item>
//        <item>#</item>
//        <item>20</item>
//        <item>21</item>
//        <item>24</item>
//        <item>23</item>
//        <item>25</item>
//        <item>22</item>
//        <item>11</item>
//        <item>#</item>
//        <item>6</item>
//        <item>8</item>
//        <item>18</item>
//        <item>7</item>
    public void returnsonglist(int tipid, List<SongBean> list){
        switch (tipid){
            case 2:
                //??????
                baiduHotSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 1:
                //??????
               baiduNewSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 20:
                //??????
              chineseGoldenMelodyList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 21:
                //??????
               euramerican=list;
                insertIntoListInfo(tipid,list);
                break;
            case 24:
                //??????
               goldenSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 23:
                //??????
               loveSongsToTheList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 25:
                //??????
                onlineSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 22:
                //??????
               listOfClassicOldSongs=list;
                insertIntoListInfo(tipid,list);
                break;
            case 11:
                //??????
               listOfRock=list;
                insertIntoListInfo(tipid,list);
                break;
            case 6:
                //??????
               ktvHotSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 8:
                //??????
                Billboard=list;
                insertIntoListInfo(tipid,list);
                break;
            case 18:
                //??????
                hitoChineseList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 7:
                //??????
                powerSongList=list;
                insertIntoListInfo(tipid,list);
                break;

        }
        loadcoutn++;
        if(loadcoutn==13)
            finishload();
    }

    public List<SongBean> getHotSongList(int tipid) {
        switch (tipid){
            case 2:
                //??????
                return baiduHotSongList;

            case 1:
                //??????
                return baiduNewSongList;

            case 20:
                //??????
                return chineseGoldenMelodyList;

            case 21:
                //??????
                return euramerican;

            case 24:
                //??????
                return goldenSongList;

            case 23:
                //??????
                return loveSongsToTheList;

            case 25:
                //??????
                return onlineSongList;

            case 22:
                //??????
                return listOfClassicOldSongs;

            case 11:
                //??????
                return listOfRock;

            case 6:
                //??????
                return ktvHotSongList;

            case 8:
                //??????
                return Billboard;

            case 18:
                //??????
                return hitoChineseList;

            case 7:
                //??????
                return powerSongList;



        }
        return null;
    }
    public void finishload(){

    }

    public void initSonglistInfo(){
        int i=0;
        String[] titles = getResources().getStringArray(R.array.online_music_list_title);
        String[] types = getResources().getStringArray(R.array.online_music_list_type);
        for(String title:titles){
            SongListInfo info=new SongListInfo();
            if(!types[i].equals("#")){
                info.isSonglist=true;
                info.Title=title;
                info.type=Integer.valueOf(types[i]);
            }else {
                info.isSonglist=false;
                info.Title=title;
                info.type=0;
                info.isFinishload=false;
            }
            listInfos.add(info);
            i++;
        }
    }

    public void insertIntoListInfo(int tipid,List<SongBean> list){
        for(SongListInfo info:listInfos){
            if(info.type==tipid&&list.size()>0){
                info.firsSongname=list.get(0).getSongname();
                info.firsSingername=list.get(0).getSingername();
                if(list.size()>=3)
                {
                    info.secondSongname=list.get(1).getSongname();
                    info.secondSingername=list.get(1).getSingername();
                    info.thirdSongname=list.get(2).getSongname();
                    info.thirdSingername=list.get(2).getSingername();
                }else {
                    info.secondSongname=".....";
                    info.secondSingername=".....";
                    info.thirdSongname=".....";
                    info.thirdSingername=".....";
                }
                info.imageUrl=list.get(0).getAlbumpic_big();
                listAdapter.notifyDataSetChanged();
                break;
            }
        }
    }



}
