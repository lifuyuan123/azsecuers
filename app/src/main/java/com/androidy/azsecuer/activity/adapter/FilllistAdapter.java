package com.androidy.azsecuer.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.basedataAdapter.BaseDataAdapter;
import com.androidy.azsecuer.activity.entity.Filllist;
import com.androidy.azsecuer.activity.manager.FileSearchTypeEvent;
import com.androidy.azsecuer.activity.util.PublicUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/8/26.
 */
public class FilllistAdapter extends BaseDataAdapter<Filllist> {
    private ArrayList<Filllist> dataList = new ArrayList<Filllist>();
    private Bitmap defBitmap;
    private AbsListView absListView;
    private boolean isFling;
    public FilllistAdapter(Context context, AbsListView absListView) {
        this.context = context;
        this.absListView = absListView;
        layoutInflater = LayoutInflater.from(context);
        defBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isFling = false;
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    isFling = true;
                }
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=this.layoutInflater.inflate(R.layout.list_fill_list,null);
            viewHolder=new ViewHolder();
            viewHolder.phone_qiuck_liststyle_check=(CheckBox)convertView.findViewById(R.id.phone_qiuck_liststyle_check);
            viewHolder.im_phone_quick_liststyle=(ImageView)convertView.findViewById(R.id.im_phone_quick_liststyle);
            viewHolder.tv_phonequick_liststyle_top=(TextView)convertView.findViewById(R.id.tv_phonequick_liststyle_top);
            viewHolder.tv_phonequick_liststyle_buttom=(TextView)convertView.findViewById(R.id.tv_phonequick_liststyle_buttom);
            viewHolder.tv_phonequick_liststyle_right=(TextView)convertView.findViewById(R.id.tv_phonequick_liststyle_right) ;
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Filllist filllist=getItem(position);
        viewHolder.phone_qiuck_liststyle_check.setChecked(filllist.ischeck);
        viewHolder.im_phone_quick_liststyle.setImageBitmap(getFileIconBitmap(position,filllist));
        viewHolder.tv_phonequick_liststyle_top.setText(filllist.file.getName());
        viewHolder.tv_phonequick_liststyle_buttom.setText(PublicUtils.formatDate(filllist.file.lastModified()));
        viewHolder.tv_phonequick_liststyle_right.setText(PublicUtils.formatSize(filllist.file.length()));
        viewHolder.phone_qiuck_liststyle_check.setTag(filllist);
        viewHolder.phone_qiuck_liststyle_check.setOnCheckedChangeListener(onCheckedChangeListener);
        return convertView;
    }
    class ViewHolder{
        TextView tv_phonequick_liststyle_top,tv_phonequick_liststyle_buttom,tv_phonequick_liststyle_right;
        ImageView im_phone_quick_liststyle;
        CheckBox phone_qiuck_liststyle_check;

    }
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ((Filllist)buttonView.getTag()).ischeck=isChecked;
        }
    };
    /** 获取此文件的图像Bitmap对象 (根据图像名称) */
    private Bitmap getFileIconBitmap(int position, Filllist filllist) {
        // 快速滑动时直接返回默认图像
        if (isFling) {
            return defBitmap;
        }
        String filePath = filllist.file.getAbsolutePath();
        // #1 到缓存中取图像(文件路径做为key)
        Bitmap bitmap = lruCache.get(filePath);
        if (bitmap != null) {
            return bitmap;
        }
        // #2 缓存中没有图像
        String fileType = filllist.fileType;
        // 不是图像的文件 - 根据iconName到drawable中加载
        if (!fileType.equals(FileSearchTypeEvent.TYPE_IMAGE)) {
            String iconName = filllist.iconName;
            int id = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
            bitmap = BitmapFactory.decodeResource(context.getResources(), id);
            if (bitmap == null) {
                bitmap = defBitmap;
            } else {
                lruCache.put(filePath, bitmap);
            }
            return bitmap;
        }
        // 是图像的文件 - 异步-到本地去加载
        else {
            asyncLoadImageFromDisk(position, filllist);
            // 返回一张默认图像
            return defBitmap;
        }
    }
    /** 用来缓存文件图像 */
    private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(1024 * 1024 * 4) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        };
    };
    /** 用来异步加载图像文件的线程池 */
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                int position = msg.arg1;
                int cfPosition = absListView.getFirstVisiblePosition();
                int clPosition = absListView.getLastVisiblePosition();
                // 图像还在屏幕上
                if (position >= cfPosition && position <= clPosition) {
                    notifyDataSetChanged();
                }
            }
        };
    };
    private void asyncLoadImageFromDisk(final int position, final Filllist filllist) {
        // 线程池控制线程并发数量
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // 本地加载图像
                String filePath = filllist.file.getAbsolutePath();
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;// 打开加载边界处理
                BitmapFactory.decodeFile(filePath, opts);
                int wp = opts.outWidth / 40;
                int hp = opts.outHeight / 40;
                int p = wp > hp ? wp : hp;
                if (p < 1) {
                    p = 1;
                }
                opts.inSampleSize = p;// 计算设置缩放比
                opts.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);
                if (bitmap == null) {
                    bitmap = defBitmap;
                } else {
                    lruCache.put(filePath, bitmap);
                    Message message = mainHandler.obtainMessage();
                    message.what = 0;
                    message.arg1 = position;
                    mainHandler.sendMessage(message);
                }
            }
        });
    }
}
