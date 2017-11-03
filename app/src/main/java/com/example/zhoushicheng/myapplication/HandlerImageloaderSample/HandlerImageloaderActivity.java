package com.example.zhoushicheng.myapplication.HandlerImageloaderSample;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.zhoushicheng.myapplication.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class HandlerImageloaderActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private ImageView mImageView;
    private GridView mGridView;
    private ListAdapter mAdapter;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<>();

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
            mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(".jpg")) {
                        return true;
                    }
                    return false;
                }
            }));

            /**
             * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗
             */
            mAdapter = new ImageAdapter(getApplicationContext(), mImgs, mImgDir.getAbsolutePath());
            mGridView.setAdapter(mAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_imageloader);
        mGridView = (GridView) findViewById(R.id.gv_image_grid);
        getImages();
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法运行在子线程中完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        /**
         * 显示进度条
         */
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = HandlerImageloaderActivity.this.getContentResolver();
                /**
                 * 只查询jpeg和png的图片
                 */
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    /**
                     * 获取图片的路径
                     */
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    /**
                     * 获取该图片的父路径名
                     */
                    File parentFile = new File(path).getParentFile();
                    String dirPath = parentFile.getAbsolutePath();
                    /**
                     * 利用一个HashSet防止多次扫描同一个文件夹
                     */
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            if (name.endsWith(".jpg")) {
                                return true;
                            }
                            return false;
                        }
                    }).length;
                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();
                /**
                 * 扫描完成，辅助的HashSet也就可以释放内存了
                 */
                mDirPaths = null;
                /**
                 * 通知Handler扫描图片完成
                 */
                mHandler.sendEmptyMessage(0x110);
            }
        }).start();
    }


    class ImageAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> mData;
        private String mDirPath;
        private LayoutInflater mInflater;
        private ImageLoader mImageLoader;

        public ImageAdapter(Context context, List<String> data, String dirPath) {
            mContext = context;
            mData = data;
            mDirPath = dirPath;
            mInflater = LayoutInflater.from(context);
            mImageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_image, parent, false);
                holder.mImageView = (ImageView) convertView.findViewById(R.id.img_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mImageView.setImageResource(android.R.drawable.sym_def_app_icon);
            mImageLoader.loadImage(mDirPath + "/" + mData.get(position), holder.mImageView);

            return convertView;
        }
    }

    class ViewHolder {
        ImageView mImageView;
    }
}
