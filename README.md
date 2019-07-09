# 1.前言
方便平时快速开发用到的一个基础框架

# 2.地址

## [github](https://github.com/JiangHaiYang01/XBase) 欢迎指出不合理的地方，

# 3.如何下载

- Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
- Step 2. Add the dependency
- 最新版本可以github上查看

```
	dependencies {
	        implementation 'com.github.JiangHaiYang01:XBase:1.0.0'
	}

```

# 4 如何使用

## 1. 继承 ``BaseApplication``

```
public class MyApp extends BaseApplication {

    @Override
    protected LogInfo onLogInfo() {
        return LogInfo.builder()
                .fileName("log")//文件名称
                .isOpen(true)//是否保存到文件
                //.path()//保存文件地址 默认沙盒
                .maxFileSize(10)//最大文件数
                .maxM(5)//单个文件最大多少M
                .tag("log--->")
                .build();
    }
}

```
默认log  参数配置

- ``onAppFrontOrBack`` 前后台状态
- ``onOpenOnePx`` 是否开启一像素
- ``onAppLockScreen`` 锁屏状态
- ``onAppNetWorkStatus`` app 网络变化
- ``onBlueStatusChange`` 蓝牙变化


## 2 继承 ``BaseActivity``

```
    /***
     * 是否开启沉寂式布局
     */
    void silence();

    int getWith();

    int getHeight();

    /***
     * 跳转
     * @param clz activity
     */
    void startActivity(Class<?> clz);

    void startActivity(Class<?> clz, Bundle bundle);

    void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode);


    void showFragment(Fragment fragment, int resId);


    /***
     * 获取当前系统语言状态
     * @return
     */
    Locale getLocal();

    /***
     * 检查网络是否可用
     * @return true 可用
     *         false 不可用
     */
    boolean isNetworkAvailable();

    Context getContext();
```

```
    void toast(String msg);

    void toast(int resId);


    void showLoading();

    void hideLoading();


    /***
     * 绑定控件
     * @param resId
     * @param <T>
     * @return
     */
    <T extends View> T $(int resId);


    /***
     * 获取资源的String
     * @param id
     * @return
     */
    String getResId(int id);


    /* 获取SharedPreference 参数*/
    <T> T getPref(String key);

    <T> T getPref(String key, T defaultValue);

    <T> boolean putPref(String key, T value);

    boolean deleteAllPref();

    boolean deletePref(String key);

    long getPrefCount();

    boolean containsPref(String key);
```

```
//两个手手指放大
        void onTouchBig();

//两个手指缩小
        void onTouchSmall();
```


## 3. 继承 `` BaseFragment ``

使用接口几乎同上

## 4 网络模块

- 初始化配置
```
 xHttp = new XHttp.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .baseUrl("http://134.175.88.222/")
                .retryOnConnectionFailure(false)
                .isLog(true)
                .isLogToFile(true)
//                .addHeard()
                .build();
```

>网络请求绑定了生命周期，防止在onDestroy的时候还有请求的任务，导致内存泄漏，
断点下载，暂停下载，取消下载

```
 public <T> void doGet(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {

 public <T> void doGet(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
```


```
 public <T> void doPost(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {

 public <T> void doPost(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
```


```
  public <T> void doBody(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {

  public <T> void doBody(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
```

```
  public <T> void doPut(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {

  public <T> void doPut(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
```

```
  public <T> void doDelete(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {

  public <T> void doDelete(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
```

```
  public void doDownload(String url, String filePath, String name, OnDownLoadListener loadListener) {

  public void doDownloadBindAct(BaseActivity activity, String url, String filePath, String name, OnDownLoadListener loadListener) {

  public void doDownloadBindFragment(BaseFragment fragment, String url, String filePath, String name, OnDownLoadListener loadListener) {

  public void doDownload(String url, String filePath, OnDownLoadListener loadListener) {

  public void doDownloadBindAct(BaseActivity activity, String url, String filePath, OnDownLoadListener loadListener) {

  public void doDownloadBindFragment(BaseFragment fragment, String url, String filePath, OnDownLoadListener loadListener) {


  public void doDownLoadPause(String url) {

  public void doDownLoadPauseAll() {

  public void doDownLoadCancel(String url) {

  public void doDownLoadCancelAll() {
```
## 5. mvp base 封装

- Contract

```
public interface TestMvpContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView {

        void showToast();
    }

    interface Presenter {

        void testToast();
    }
}

```

- model

```
public class TestMvpModel implements TestMvpContract.Model {
}
```
- presenter

```
public class TestMvpPresenter extends BasePresenter<TestMvpContract.Model, TestMvpContract.View> implements TestMvpContract.Presenter {
    @Override
    protected void onViewDestroy() {

    }

    @Override
    public void testToast() {

        getView().showToast();
    }
}

```

- act

```
public class TestMvpAct extends BaseMvpActivity<TestMvpModel, TestMvpContract.View, TestMvpPresenter> implements TestMvpContract.View {
    @Override
    public int getContentViewId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        $(R.id.btn).setOnClickListener(v -> {
            presenter.testToast();
        });

    }


    @Override
    public TestMvpModel createModel() {
        return new TestMvpModel();
    }

    @Override
    public TestMvpContract.View createView() {
        return this;
    }

    @Override
    public TestMvpPresenter createPresenter() {
        return new TestMvpPresenter();
    }

    @Override
    public void showToast() {
        toast("测试成功");
    }
}

```