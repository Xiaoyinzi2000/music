package com.monk.player.mvp.comment;

import com.monk.player.bean.Comment;
import com.monk.player.contract.Contract;
import com.monk.player.mvp.base.BaseModel;
import com.monk.player.mvp.comment.api.CommentServer;
import com.monk.player.tools.RetrofitUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by andy on 2018/1/2.
 */

public class CommentModle extends BaseModel<CommentPresenter>{
    public CommentModle(CommentPresenter presenter) {
        super(presenter);
    }

    public void addComment(Observer<ResponseBody> observer, int SongId, String cotent){
        Retrofit retrofit= RetrofitUtil.getRetrofit();
        CommentServer api=retrofit.create(CommentServer.class);
        api.add(Contract.TOKEN,SongId,cotent)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void finBySongid(Observer<List<Comment>> observer, int SongId){
        Retrofit retrofit= RetrofitUtil.getRetrofit();
        CommentServer api=retrofit.create(CommentServer.class);
        api.findbysongid(Contract.TOKEN,SongId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
