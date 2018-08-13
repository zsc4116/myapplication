package com.example.zhoushicheng.myapplication.GlideSample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.zhoushicheng.myapplication.R;

public class GlideSampleActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_sample);

        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516877270477&di=69302d4587aac777d6f3ce86f84c66d1&imgtype=0&src=http%3A%2F%2Fimgtu.5011.net%2Fuploads%2Fcontent%2F20170303%2F4502511488530663.jpg";

        imageView = (ImageView) findViewById(R.id.img_glide_sample);

        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setImageDrawable(resource);
            }
        };

        Glide.with(getApplicationContext())//指定Context
                .load(new GlideSampleActivity.MyUrl(url))//指定图片的URL
                .placeholder(R.mipmap.ic_launcher)//指定图片未成功加载前显示的图片
                .error(R.mipmap.ic_launcher)//指定图片加载失败显示的图片
                .override(300, 300)//指定图片的尺寸
//                .animate()//指定图片加载完成后显示的动画
//                .dontAnimate()//不需要显示animate()指定的动画
                .fitCenter()//指定图片的缩放类型
                .centerCrop()//指定图片的缩放类型
                .dontTransform()//指定图片不进行变换，使用图片原本的大小分辨率
                .transform(new GlideSampleActivity.CycleCrop(this))//使用自定义变换
                .listener(new RequestListener<MyUrl, GlideDrawable>() {//指定加载请求的监听，加载成功时onResourceReady方法被调用，返回true表示在GenericRequest中被消耗，后面target的onResourceReady方法不会被调用
                    @Override
                    public boolean onException(Exception e, GlideSampleActivity.MyUrl model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, GlideSampleActivity.MyUrl model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//跳过磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//仅仅只缓存原来的全分辨率的图像
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//仅仅只缓存最终的图像
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有版本的图像
                .priority(Priority.HIGH)//指定优先级
                .into(simpleTarget);//指定显示图片的ImageView
    }

    class MyUrl extends GlideUrl {
        String mUrl;

        public MyUrl(String url) {
            super(url);
            mUrl = url;
        }

        @Override
        public String getCacheKey() {
            return mUrl.replace(findTokenParam(), "");
        }

        private String findTokenParam() {
            String tokenParam = "";
            int tokenKeyIndex = mUrl.indexOf("?token=") >= 0 ? mUrl.indexOf("?token=") : mUrl.indexOf("&token=");
            if (tokenKeyIndex != -1) {
                int nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1);
                if (nextAndIndex != -1) {
                    tokenParam = mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1);
                } else {
                    tokenParam = mUrl.substring(tokenKeyIndex);
                }
            }
            return tokenParam;
        }
    }

    class CycleCrop extends BitmapTransformation {

        public CycleCrop(Context context) {
            super(context);
        }

        public CycleCrop(BitmapPool bitmapPool) {
            super(bitmapPool);
        }

        @Override
        public String getId() {
            return "com.example.zhoushicheng.myapplication.GlideSample.CircleCrop";
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            int diameter = Math.min(toTransform.getWidth(), toTransform.getHeight());

            final Bitmap toReuse = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            final Bitmap result;
            if (toReuse != null) {
                result = toReuse;
            } else {
                result = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
            }

            int dx = (toTransform.getWidth() - diameter) / 2;
            int dy = (toTransform.getHeight() - diameter) / 2;
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP,
                    BitmapShader.TileMode.CLAMP);
            if (dx != 0 || dy != 0) {
                Matrix matrix = new Matrix();
                matrix.setTranslate(-dx, -dy);
                shader.setLocalMatrix(matrix);
            }
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float radius = diameter / 2f;
            canvas.drawCircle(radius, radius, radius, paint);

            if (toReuse != null && !pool.put(toReuse)) {
                toReuse.recycle();
            }
            return result;
        }
    }

}
