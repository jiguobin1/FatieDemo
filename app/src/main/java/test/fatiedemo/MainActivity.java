package test.fatiedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.GetChars;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText bt, nr;
    private TextView tv;
    private ImageView iv;
    private StringBuffer imageurl;
    private Gson mGson = new Gson();
    private List<LocalMedia> localMedia;
    private Button bt_click;
    private RecyclerView recy;
    private Adapter adapter;
    private int bs;


    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String a = (String) msg.obj;
                    Log.e("JGB", "html格式数据：：：" + a);
                    tv.setText(a);
                    Log.e("JGB", "1");
                    break;
            }
            return true;
        }
    });
    private String paths;
    private String image2;
    private int data1;
    private int size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (EditText) findViewById(R.id.bt);
        nr = (EditText) findViewById(R.id.nr);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        recy = (RecyclerView) findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(this));

        bt_click = (Button) findViewById(R.id.bt_click);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(MainActivity.this)
                        .openGallery(PictureMimeType.ofAll())
                        .maxSelectNum(9)
                        .compress(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    localMedia = PictureSelector.obtainMultipleResult(data);
                    size = localMedia.size();
                    adapter = new Adapter(MainActivity.this, localMedia);
                    recy.setAdapter(adapter);
                    SetOnClickListen setOnClickListen = new SetOnClickListen() {
                        @Override
                        public void setOnClick(int position) {
                            localMedia .remove(position);
                            adapter.notifyItemRemoved(position);
                            adapter.notifyDataSetChanged();
                            size=localMedia.size();
                            Log.e("JGB","删除后的集合数量：："+ data1);
                        }
                    };
                    adapter.onClick(setOnClickListen);

                    bt_click.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                switch (size) {
                                    case 1:
                                        RequestQueue requestQueue = NoHttp.newRequestQueue();
                                        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
                                        request.add("url", new FileBinary(new File(localMedia.get(0).getPath())));//上传文件
                                        requestQueue.add(0, request, new OnResponseListener<String>() {
                                            @Override
                                            public void onStart(int what) {
                                            }

                                            @Override
                                            public void onSucceed(int what, Response<String> response) {
                                                String json = response.get();
                                                if (json == null) {
                                                    return;
                                                } else {
                                                    Log.e("JGB", "图片1上传结果" + json);
                                                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                                                    String url1 = photoBean.getFilelist().get(0).getURL();
                                                }
                                            }

                                            @Override
                                            public void onFailed(int what, Response<String> response) {
                                            }

                                            @Override
                                            public void onFinish(int what) {

                                            }
                                        });
                                        break;
                                    case 2:
                                        RequestQueue requestQueue2 = NoHttp.newRequestQueue();
                                        Request<String> request2 = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
                                        request2.add("url", new FileBinary(new File(localMedia.get(0).getPath())));//上传文件
                                        requestQueue2.add(0, request2, new OnResponseListener<String>() {
                                            @Override
                                            public void onStart(int what) {
                                            }

                                            @Override
                                            public void onSucceed(int what, Response<String> response) {
                                                String json = response.get();
                                                if (json == null) {
                                                    return;
                                                } else {
                                                    Log.e("JGB", "图片1上传结果" + json);
                                                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                                                    String url1 = photoBean.getFilelist().get(0).getURL();
                                                    case2imageHttp(url1);


                                                }
                                            }

                                            @Override
                                            public void onFailed(int what, Response<String> response) {
                                            }

                                            @Override
                                            public void onFinish(int what) {

                                            }
                                        });
                                        break;
                                    case 3:
                                        RequestQueue requestQueue3 = NoHttp.newRequestQueue();
                                        Request<String> request3 = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
                                        request3.add("url", new FileBinary(new File(localMedia.get(0).getPath())));//上传文件
                                        requestQueue3.add(0, request3, new OnResponseListener<String>() {
                                            @Override
                                            public void onStart(int what) {
                                            }

                                            @Override
                                            public void onSucceed(int what, Response<String> response) {
                                                String json = response.get();
                                                if (json == null) {
                                                    return;
                                                } else {
                                                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                                                    String image31 = photoBean.getFilelist().get(0).getURL();
                                                    case32imageHttp(image31);

                                                }
                                            }

                                            @Override
                                            public void onFailed(int what, Response<String> response) {
                                            }

                                            @Override
                                            public void onFinish(int what) {

                                            }
                                        });
                                        break;

                                    case 4:
                                        RequestQueue requestQueue4 = NoHttp.newRequestQueue();
                                        Request<String> request4 = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
                                        request4.add("url", new FileBinary(new File(localMedia.get(0).getPath())));//上传文件
                                        requestQueue4.add(0, request4, new OnResponseListener<String>() {
                                            @Override
                                            public void onStart(int what) {
                                            }

                                            @Override
                                            public void onSucceed(int what, Response<String> response) {
                                                String json = response.get();
                                                if (json == null) {
                                                    return;
                                                } else {
                                                    Log.e("JGB", "图片1上传结果" + json);
                                                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                                                    String image41 = photoBean.getFilelist().get(0).getURL();
                                                    case42imageHttp(image41);


                                                }
                                            }

                                            @Override
                                            public void onFailed(int what, Response<String> response) {
                                            }

                                            @Override
                                            public void onFinish(int what) {

                                            }
                                        });
                                        break;
                                    case 5:
                                        RequestQueue requestQueue5 = NoHttp.newRequestQueue();
                                        Request<String> request5 = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
                                        request5.add("url", new FileBinary(new File(localMedia.get(0).getPath())));//上传文件
                                        requestQueue5.add(0, request5, new OnResponseListener<String>() {
                                            @Override
                                            public void onStart(int what) {
                                            }

                                            @Override
                                            public void onSucceed(int what, Response<String> response) {
                                                String json = response.get();
                                                if (json == null) {
                                                    return;
                                                } else {
                                                    Log.e("JGB", "图片1上传结果" + json);
                                                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                                                    String image51 = photoBean.getFilelist().get(0).getURL();
                                                    case52imageHttp(image51);


                                                }
                                            }

                                            @Override
                                            public void onFailed(int what, Response<String> response) {
                                            }

                                            @Override
                                            public void onFinish(int what) {

                                            }
                                        });
                                        break;
                                    case 6:
                                        RequestQueue requestQueue6 = NoHttp.newRequestQueue();
                                        Request<String> request6 = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
                                        request6.add("url", new FileBinary(new File(localMedia.get(0).getPath())));//上传文件
                                        requestQueue6.add(0, request6, new OnResponseListener<String>() {
                                            @Override
                                            public void onStart(int what) {
                                            }

                                            @Override
                                            public void onSucceed(int what, Response<String> response) {
                                                String json = response.get();
                                                if (json == null) {
                                                    return;
                                                } else {
                                                    Log.e("JGB", "图片1上传结果" + json);
                                                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                                                    String image61 = photoBean.getFilelist().get(0).getURL();
                                                    case62imageHttp(image61);


                                                }
                                            }

                                            @Override
                                            public void onFailed(int what, Response<String> response) {
                                            }

                                            @Override
                                            public void onFinish(int what) {

                                            }
                                        });
                                        break;
                                    case 7:
                                        RequestQueue requestQueue7 = NoHttp.newRequestQueue();
                                        Request<String> request7 = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
                                        request7.add("url", new FileBinary(new File(localMedia.get(0).getPath())));//上传文件
                                        requestQueue7.add(0, request7, new OnResponseListener<String>() {
                                            @Override
                                            public void onStart(int what) {
                                            }

                                            @Override
                                            public void onSucceed(int what, Response<String> response) {
                                                String json = response.get();
                                                if (json == null) {
                                                    return;
                                                } else {
                                                    Log.e("JGB", "图片1上传结果" + json);
                                                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                                                    String image71 = photoBean.getFilelist().get(0).getURL();
                                                    case72imageHttp(image71);


                                                }
                                            }

                                            @Override
                                            public void onFailed(int what, Response<String> response) {
                                            }

                                            @Override
                                            public void onFinish(int what) {

                                            }
                                        });
                                        break;
                                    case 8:
                                        RequestQueue requestQueue8 = NoHttp.newRequestQueue();
                                        Request<String> request8 = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
                                        request8.add("url", new FileBinary(new File(localMedia.get(0).getPath())));//上传文件
                                        requestQueue8.add(0, request8, new OnResponseListener<String>() {
                                            @Override
                                            public void onStart(int what) {
                                            }

                                            @Override
                                            public void onSucceed(int what, Response<String> response) {
                                                String json = response.get();
                                                if (json == null) {
                                                    return;
                                                } else {
                                                    Log.e("JGB", "图片1上传结果" + json);
                                                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                                                    String image81 = photoBean.getFilelist().get(0).getURL();
                                                    case82imageHttp(image81);


                                                }
                                            }

                                            @Override
                                            public void onFailed(int what, Response<String> response) {
                                            }

                                            @Override
                                            public void onFinish(int what) {

                                            }
                                        });
                                        break;
                                    case 9:
                                        break;
                                }
                            }
                    });
            }
        }
    }



    private void case82imageHttp(final String image81) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(1).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image82 = photoBean.getFilelist().get(0).getURL();
                    case83imageHttp(image81,image82);


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

    private void case83imageHttp(final String image81, final String image82) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(2).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image83 = photoBean.getFilelist().get(0).getURL();
                    case84imageHttp(image81,image82,image83);


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

    private void case84imageHttp(final String image81, final String image82, final String image83) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(3).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image84 = photoBean.getFilelist().get(0).getURL();
                    case85imageHttp(image81,image82,image83,image84);


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

    private void case85imageHttp(final String image81, final String image82, final String image83, final String image84) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(4).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image85 = photoBean.getFilelist().get(0).getURL();
                    case86imageHttp(image81,image82,image83,image84,image85);


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

    private void case86imageHttp(final String image81, final String image82, final String image83, final String image84, final String image85) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(5).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image86 = photoBean.getFilelist().get(0).getURL();
                    case87imageHttp(image81,image82,image83,image84,image85,image86);


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

    private void case87imageHttp(final String image81, final String image82, final String image83, final String image84, final String image85, final String image86) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(6).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image87 = photoBean.getFilelist().get(0).getURL();
                    case88imageHttp(image81,image82,image83,image84,image85,image86,image87);


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

    private void case88imageHttp(final String image81, final String image82, final String image83, final String image84, final String image85, final String image86, final String image87) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(7).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image88 = photoBean.getFilelist().get(0).getURL();
                    StringBuffer sb8=new StringBuffer();
                    imageurl = sb8.append("<p><img src=\"").append("http://www.olacos.net").append(image81).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image82).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image83).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image84).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image85).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image86).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image87).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image88).append("\"/></p>");
                    Log.e("JGB", "图片上传结果：" + imageurl);


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

    private void case72imageHttp(final String image71) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(1).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image72 = photoBean.getFilelist().get(0).getURL();
                    case73imageHttp(image71,image72);


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

    private void case73imageHttp(final String image71, final String image72) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(2).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image73 = photoBean.getFilelist().get(0).getURL();
                    case74imageHttp(image71,image72,image73);


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

    private void case74imageHttp(final String image71, final String image72, final String image73) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(3).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image74 = photoBean.getFilelist().get(0).getURL();
                    case75imageHttp(image71,image72,image73,image74);


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

    private void case75imageHttp(final String image71, final String image72, final String image73, final String image74) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(4).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image75 = photoBean.getFilelist().get(0).getURL();
                    case76imageHttp(image71,image72,image73,image74,image75);


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

    private void case76imageHttp(final String image71, final String image72, final String image73, final String image74, final String image75) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(5).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image76 = photoBean.getFilelist().get(0).getURL();
                    case77imageHttp(image71,image72,image73,image74,image75,image76);


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

    private void case77imageHttp(final String image71, final String image72, final String image73, final String image74, final String image75, final String image76) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(6).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image77 = photoBean.getFilelist().get(0).getURL();
                    StringBuffer sb7=new StringBuffer();
                    imageurl = sb7.append("<p><img src=\"").append("http://www.olacos.net").append(image71).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image72).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image73).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image74).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image75).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image76).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image77).append("\"/></p>");
                    Log.e("JGB", "图片上传结果：" + imageurl);
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

    private void case62imageHttp(final String image61) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(1).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image62 = photoBean.getFilelist().get(0).getURL();
                    case63imageHttp(image61,image62);


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

    private void case63imageHttp(final String image61, final String image62) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(2).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image63 = photoBean.getFilelist().get(0).getURL();
                    case64imageHttp(image61,image62,image63);


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

    private void case64imageHttp(final String image61, final String image62, final String image63) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(3).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image64 = photoBean.getFilelist().get(0).getURL();
                    case65imageHttp(image61,image62,image63,image64);


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

    private void case65imageHttp(final String image61, final String image62, final String image63, final String image64) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(4).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image65 = photoBean.getFilelist().get(0).getURL();
                    case66imageHttp(image61,image62,image63,image64,image65);
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

    private void case66imageHttp(final String image61, final String image62, final String image63, final String image64, final String image65) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(5).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image66 = photoBean.getFilelist().get(0).getURL();
                    StringBuffer sb6=new StringBuffer();
                    imageurl = sb6.append("<p><img src=\"").append("http://www.olacos.net").append(image61).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image62).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image63).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image64).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image65).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image66).append("\"/></p>");
                    Log.e("JGB", "图片上传结果：" + imageurl);
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

    private void case52imageHttp(final String image51) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(1).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image52 = photoBean.getFilelist().get(0).getURL();
                    case53imageHttp(image51,image52);


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

    private void case53imageHttp(final String image51, final String image52) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(2).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image53 = photoBean.getFilelist().get(0).getURL();
                    case54imageHttp(image51,image52,image53);


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

    private void case54imageHttp(final String image51, final String image52, final String image53) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(3).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image54 = photoBean.getFilelist().get(0).getURL();
                    case55imageHttp(image51,image52,image53,image54);


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

    private void case55imageHttp(final String image51, final String image52, final String image53, final String image54) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File(localMedia.get(4).getPath())));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片1上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image55 = photoBean.getFilelist().get(0).getURL();
                    StringBuffer sb5=new StringBuffer();
                    imageurl = sb5.append("<p><img src=\"").append("http://www.olacos.net").append(image51).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image52).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image53).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image54).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image55).append("\"/></p>");
                    Log.e("JGB", "图片上传结果：" + imageurl);
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

    private void case42imageHttp(final String image41) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File((localMedia.get(1).getPath()))));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image42 = photoBean.getFilelist().get(0).getURL();
                    case43imageHttp(image41,image42);


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

    private void case43imageHttp(final String image41, final String image42) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File((localMedia.get(2).getPath()))));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image43 = photoBean.getFilelist().get(0).getURL();
                    case44imageHttp(image41,image42,image43);


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

    private void case44imageHttp(final String image41, final String image42, final String image43) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File((localMedia.get(3).getPath()))));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image44 = photoBean.getFilelist().get(0).getURL();
                    StringBuffer sb4=new StringBuffer();
                    imageurl = sb4.append("<p><img src=\"").append("http://www.olacos.net").append(image41).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image42).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image43).append("\"/></p>")
                            .append("<p><img src=\"").append("http://www.olacos.net").append(image44).append("\"/></p>");
                    Log.e("JGB", "图片上传结果：" + imageurl);
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

    private void case32imageHttp(final String image31) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File((localMedia.get(1).getPath()))));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {

                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image32 = photoBean.getFilelist().get(0).getURL();
                    case33imageHttp(image31,image32);


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

    private void case33imageHttp(final String image31, final String image32) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File((localMedia.get(2).getPath()))));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String image33 = photoBean.getFilelist().get(0).getURL();
                    StringBuffer sb3=new StringBuffer();
                    imageurl = sb3.append("<p><img src=\"").append("http://www.olacos.net").append(image31).append("\"/></p>").append("<p><img src=\"").append("http://www.olacos.net").append(image32).append("\"/></p>").append("<p><img src=\"").append("http://www.olacos.net").append(image33).append("\"/></p>");
                    Log.e("JGB", "图片上传结果：" + imageurl);
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

    private void case2imageHttp(final String avaterurl) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.olacos.net/upload/uploadFile.do", RequestMethod.POST);
        request.add("url", new FileBinary(new File((localMedia.get(1).getPath()))));//上传文件
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (json == null) {
                    return;
                } else {
                    Log.e("JGB", "图片2上传结果" + json);
                    PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
                    String url = photoBean.getFilelist().get(0).getURL();
                    StringBuffer sb2=new StringBuffer();
                    imageurl = sb2.append("<p><img src=\"").append("http://www.olacos.net").append(avaterurl).append("\"/></p>").append("<p><img src=\"").append("http://www.olacos.net").append(url).append("\"/></p>");
                    Log.e("JGB", "图片上传结果：" + imageurl);
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



//    private void gson(String json) {
//        PhotoBean photoBean = mGson.fromJson(json, PhotoBean.class);
//        String avaterok = photoBean.getOk();
//        avaterurl = photoBean.getFilelist().get(0).getURL();
//        imageurl = sb1.append( "<p><img src=\"").append("http://www.olacos.net").append(avaterurl).append("\"/></p>");
//        //  Log.e("JGB","图片上传结果："+ imageurl);
//
//        s = nr.getText().toString();
//        html = s+imageurl;
//        //Log.e("JGB","生成的html数据::::"+ html);
//        Message message=new Message();
//        message.what=1;
//        message.obj=html;
//        handler.sendMessage(message);
//    }


}
