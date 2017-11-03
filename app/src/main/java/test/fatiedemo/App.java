package test.fatiedemo;

import android.app.Application;
import android.os.Handler;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * Created by Administrator on 2017/11/3.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
        Logger.setDebug(true);//开启 NoHttp 的调试模式, 配置后可看到请求过程、日志和错误信息。上线后改为false 不然影响性能
        Logger.setTag("NoHttpSample");//设置 NoHttp 打印 Log 的 tag
        Logger.setDebug(true);//开启 NoHttp 的调试模式, 配置后可看到请求过程、日志和错误信息。上线后改为 false 不然影响性能
        Logger.setTag("NoHttpSample");//设置 NoHttp 打印 Log 的 tag

    }
}
