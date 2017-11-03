package test.fatiedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText bt, nr;
    private TextView tv;
    private ImageView iv;
    private String path;
    private StringBuffer imageurl;
    private Gson mGson=new Gson();
    private String avaterurl;
    private StringBuffer sb1;
    private String s;
    public static  String html="";
    private List<LocalMedia> localMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (EditText) findViewById(R.id.bt);
        nr = (EditText) findViewById(R.id.nr);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        sb1 = new StringBuffer();
        Log.e("JGB","回调方法中的集合"+localMedia);



        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(MainActivity.this)
                        .openGallery(PictureMimeType.ofAll())
                        .maxSelectNum(3)
                        .compress(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    localMedia = PictureSelector.obtainMultipleResult(data);
                    for(int i = 0; i< localMedia.size(); i++){
                        RequestQueue requestQueue1 = NoHttp.newRequestQueue();
                        Request<String> request1 = NoHttp.createStringRequest("http://192.168.1.7:8080/qda/upload/uploadFile.do", RequestMethod.POST);
                        request1.add("url",new FileBinary(new File((localMedia.get(i).getPath()))));//上传文件
                        requestQueue1.add(0, request1, new OnResponseListener<String>() {
                            @Override
                            public void onStart(int what) {
                            }

                            @Override
                            public void onSucceed(int what, Response<String> response) {
                                String json = response.get();
                                if (json == null) {
                                    return;
                                } else {
                                    gson(json);


                                }
                            }

                            @Override
                            public void onFailed(int what, Response<String> response) {
                            }

                            @Override
                            public void onFinish(int what) {

                            }
                        });







                    }


                   Log.e("JGB","循环体以外的html"+ html);

                    break;
            }
        }
    }

    private void gson(String json) {
        PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
        String avaterok = photoBean.getOk();
        avaterurl = photoBean.getFilelist().get(0).getURL();
        imageurl = sb1.append( "<p><img src=\"").append("http://192.168.1.7:8080/qda").append(avaterurl).append("\"/></p>");
        Log.e("JGB","图片上传结果："+ imageurl);

        s = nr.getText().toString();
        html = s+imageurl;
        Log.e("JGB","生成的html数据::::"+ html);

    }


}
