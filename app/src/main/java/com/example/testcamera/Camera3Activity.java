package com.example.testcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vtc365.api.BaseApi;
import com.vtc365.api.ChatApi;
import com.vtc365.api.LiveApi;
import com.vtc365.api.LiveBroadcast;
import com.vtc365.api.TalkApi;
import com.vtc365.api.VtcPlayer;
import com.vtc365.api.XmppTalk;
import com.vtc365.bean.MobileConfigBeanClient;
import com.vtc365.utils.StreamManager;
import com.vtc365.view.VideoStreamsView;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import static com.example.testcamera.Camera2Activity.getDate;

public class Camera3Activity extends AppCompatActivity implements SurfaceHolder.Callback{


    public static String MY_APPID = "";
    public static String serviceIP = "www.vtc365.com";
    public Activity activity;
    public BaseApi baseApi;
    public ChatApi chatApi;

    private LocalStartRun localStartRun = new LocalStartRun();
    private LinearLayout.LayoutParams localLayoutParams = null;
    private LinearLayout.LayoutParams testLayoutParams = null;
    private LinearLayout.LayoutParams liveLayoutParams = null;
    private VideoStreamsView videoStreamsView;
    private Map<String, String> properties = new HashMap<String, String>();
    private static final String ACTION_USB_PERMISSION = "com.vtc365.vtest.ACTION_USB_PERMISSION";
    protected static final String TAG = "VTC";
    private String to = null;
    private VtcPlayer vtcPlayer = null;

    private static final String VTCH5GETURL = "http://www.vtc365.com:8080/LiveVideoServer/ajx/play.do?video.videoId=";

    private static final int TEST_X = 60;
    private static final int TEST_Y = 22;
    Point point = new Point();

    private LiveBroadcast liveBroadcast = null;

    private class IncomingCall {
        public String caller;
        public String sdp;
        public TalkApi.TalkMode mode;
    }


    public Handler handler;
    public Handler livingHandler;


    private LinearLayout ll_content;

    private VideoStreamsView createVideoStreamsView() {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        VideoStreamsView view = new VideoStreamsView(activity, point);
        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera3);

        activity = this;
        ll_content = (LinearLayout)this.findViewById(R.id.ll_content);

        SurfaceHolder holder = ((SurfaceView)this.findViewById(R.id.surfaceview)).getHolder();
        holder.addCallback(this);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        init();
    }

    public void init(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler = new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what) {
                            case TalkApi.TALK_BUSY:

                                break;
                            case TalkApi.TALK_ANSWERED:

                                break;
                            case TalkApi.TALK_FAILED:

                                break;
                            case TalkApi.TALK_HANGUP:


//				             	chatApi.endCall();
                                break;
                            case TalkApi.TALK_REFUSED:

                                break;
                            case TalkApi.TALK_VIDEO_OFF:
                                // SIP based talk not support
                                // SipTalk.getInstance().talkVideoOff(false);
                                // videoStreamsView.setVisibility(View.INVISIBLE);
                                break;
                            case TalkApi.TALK_BANDWIDTH_WARN:
                                // if(msg.arg1 == 1) {
                                // Toast.makeText(getApplicationContext(), "网络状况不良，关闭视频",
                                // Toast.LENGTH_SHORT).show();
                                // apiClient.talkVideoOff(true);
                                // videoStreamsView.setVisibility(View.INVISIBLE);
                                // }
                                break;
                            case FusionType.Message.BLOCKING_PHONE:

                                if(liveBroadcast != null) {
                                    liveBroadcast.stop();
                                    liveBroadcast.detach();
                                    liveBroadcast = null;
                                }

                                if(vtcPlayer != null){
                                    vtcPlayer.detach();
                                    vtcPlayer = null;
                                }
/*                                View view = webView.getView();
                                ViewGroup viewGroup = (ViewGroup)view.getParent();

                                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                                    if(!viewGroup.getChildAt(i).equals(view)){
                                        viewGroup.removeViewAt(i);
                                        i--;
                                    }

                                }*/
//								try {
//									XmppTalk.getInstance().hangup();
//									callbackContext.success();
//								} catch (Exception e) {
//									callbackContext.error(e.getMessage());
//								} finally {
//									ViewGroup viewGroup = (ViewGroup)webView.getView().getParent();
//									for (int i = 0; i < viewGroup.getChildCount(); i++) {
//										if(viewGroup.getChildAt(i) != webView.getView()){
//											viewGroup.removeViewAt(i);
//											i --;
//										}
//									}
//								}


                                break;
                            case FusionType.UI.REFRESH_PROGRESSBAR:
                                break;
                            case FusionType.Message.INCOMING_CALL:
//								final IncomingCall incomingCall = (IncomingCall) msg.obj;
//								SharedPreferences sp = (SharedPreferences) ConfigureHelper
//										.getObjectValue(ConfigureHelper.SHAREDPREFERENCE);
//								TalkApi.mStunServer = sp.getString(
//										ConfigureHelper.STUN_SERVER_ADDR,
//										TalkApi.mStunServerDefault);
//								TalkApi.mTurnServer = sp.getString(
//										ConfigureHelper.TURN_SERVER_ADDR,
//										TalkApi.mTurnServerDefault);
//								handler.post(new Runnable() {
//									@Override
//									public void run() {
//										videoStreamsView = createVideoStreamsView();
//										XmppTalk.getInstance().startTerm(activity, handler, videoStreamsView, 0, incomingCall.sdp);
//										postAddChild(videoStreamsView);
//										View view = webView.getView();
//										((ViewGroup) view).bringToFront();
//									}
//								});

                                break;
                            case FusionType.Message.HAS_SOMEBODY_ADDFRIEND:


                            case FusionType.Message.NEW_LIVEVIDEO:

                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

                livingHandler = new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what) {
                            case LiveBroadcast.START_SUCCESS:
                                /*cordova.getThreadPool().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        sendMessage(to, liveBroadcast.getRtsp(), null);
                                        JSONObject jsonObject = new JSONObject();
                                        InputStream in = null;
                                        try	{
                                            URL url = new URL(VTCH5GETURL + liveBroadcast.getVid());
                                            URLConnection connection = url.openConnection();
                                            in = connection.getInputStream();
                                            byte[] cacheBytes = new byte[1024];
                                            byte[] bytes = new byte[512];
                                            int length = 0;
                                            int cacheLength = 0;
                                            StringBuilder sb = new StringBuilder();
                                            while((length = in.read(bytes)) != -1)
                                            {
                                                System.arraycopy(bytes, 0, cacheBytes, cacheLength, length);
                                                cacheLength += length;
                                            }
                                            JSONObject result = new JSONObject(new String(cacheBytes, 0, cacheLength));
                                            jsonObject.put("liveUrl", result.optString("URL"));
                                            updateReceivedData(jsonObject);
                                        } catch (Exception e) {

                                        } finally {
                                            if(in != null){
                                                try{
                                                    in.close();
                                                } catch (Exception e){

                                                }
                                            }
                                        }
                                    }
                                });*/
                            default:
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        baseApi = BaseApi.getInstance(activity.getApplicationContext(), MY_APPID);
        LiveApi.getInstance(activity, baseApi);
        XmppTalk.getInstance().setSpeakerOn(activity.getApplicationContext(), false);
        XmppTalk.getInstance().muteMic(activity.getApplicationContext(), false);
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);

        TalkApi.LOCAL_Y = 25;
        TalkApi.LOCAL_X = 75;
        int separator_width = point.y * 5 / 100;
        LiveApi.SEPARATOR_WIDTH = separator_width - separator_width % 2;
        MobileConfigBeanClient cfg = MobileConfigBeanClient.getInstance();
        cfg.setType(1);
        cfg.setH264_width(1280);
        cfg.setH264_height(480);
        cfg.init();

        localLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        localLayoutParams.setMargins((int)(point.x * TalkApi.LOCAL_X / 100), (int)(point.y * TalkApi.LOCAL_Y / 100) - LiveApi.SEPARATOR_WIDTH - (int)(point.x * 0.23) * 480 / 640, 0, 0);

        testLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        testLayoutParams.setMargins((int)(point.x * TEST_X / 100), (int)(point.y * TEST_Y / 100), 0, 0);

        liveLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        liveLayoutParams.setMargins((int)(point.x * TalkApi.LOCAL_X / 100), (int)(point.y * TalkApi.LOCAL_Y / 100), 0, 0);
    }

    private void requestUsb(){
        UsbManager uMan = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        if (uMan != null) {
            PendingIntent pi = PendingIntent.getBroadcast(activity, 0, new Intent(ACTION_USB_PERMISSION), 0);

            Map<String, UsbDevice> devlist = uMan.getDeviceList();
            if (devlist != null) {
                for (String name : devlist.keySet()) {
                    UsbDevice dev = devlist.get(name);
                    Log.d(TAG, "Device " + name);
                    if (!uMan.hasPermission(dev)) {
                        uMan.requestPermission(dev, pi);
                    }
                }
            }
        }

    }

    SurfaceHolder holder;
    private SurfaceView initLocal() {
        SurfaceView video = new SurfaceView(activity);
        holder = video.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                int width = (int)(point.x * 0.23);
                int height = (int)(width * 960 / 640 + LiveApi.SEPARATOR_WIDTH);
                holder.setFixedSize(width, height);
                if(liveBroadcast == null || !liveBroadcast.isRunning()){
                    localStartRun.setHolder(holder);
                    new Thread(localStartRun).start();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
        return video;
    }

    class LocalStartRun implements Runnable {

        SurfaceHolder holder = null;

        public void setHolder(SurfaceHolder holder) {
            this.holder = holder;
        }

        @Override
        public void run() {
            liveBroadcast = new LiveBroadcast(activity, livingHandler, LiveBroadcast.LIVING, LiveBroadcast.USB, this.holder, null);
            liveBroadcast.start();
        }
    }

    public void openLocalVideo(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                requestUsb();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SurfaceView surfaceView = initLocal();
                        ll_content.addView(surfaceView,localLayoutParams);
                    }
                });
            }
        }).start();

    }

    public void startVideo(View v)
    {
        liveBroadcast.start();
        Log.e("mytest","aaaaaaaaa:"+liveBroadcast.getMp4());
    }

    public void stopVideo(View v)
    {
        liveBroadcast.stop();
        Log.e("mytest","bbbbbbbb:"+liveBroadcast.getMp4());
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        }
        return null;
    }

    private MediaRecorder mRecorder;
    private boolean mStartedFlg = false;//是否正在录像
    private  Camera mCamera;
    public void testButton(View v)
    {
            Log.e("mytest", StreamManager.getInstance().getCp() + "");
            if (!mStartedFlg) {
                if (mRecorder == null) {
                    mRecorder = new MediaRecorder();
                }

                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                if (mCamera != null) {
                    mCamera.setDisplayOrientation(90);
                    mCamera.unlock();
                    mRecorder.setCamera(mCamera);
                }

                try {
                    Toast.makeText(activity, "开始录制", Toast.LENGTH_SHORT).show();
                    // 这两项需要放在setOutputFormat之前
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                    mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                    // Set output file format
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                    // 这两项需要放在setOutputFormat之后
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP); //MediaRecorder.VideoEncoder.H264

                    mRecorder.setVideoSize(640, 480);
                    mRecorder.setVideoFrameRate(30);
                    mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);
                    mRecorder.setOrientationHint(90);
                    //设置记录会话的最大持续时间（毫秒）
                    mRecorder.setMaxDuration(30 * 1000);
                    //mRecorder.setPreviewDisplay(holder.getSurface());
                    mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

                    String path = getSDPath();
                    if (path != null) {
                        File dir = new File(path + "/recordtest");
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        path = dir + "/" + getDate() + ".mp4";
                        mRecorder.setOutputFile(path);
                        mRecorder.prepare();
                        mRecorder.start();
                        mStartedFlg = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //stop
                if (mStartedFlg) {
                    try {
                        Toast.makeText(activity, "停止录制", Toast.LENGTH_SHORT).show();
                        mRecorder.stop();
                        mRecorder.reset();
                        mRecorder.release();
                        mRecorder = null;
                        if (mCamera != null) {
                            mCamera.release();
                            mCamera = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mStartedFlg = false;
            }
        }

    private SurfaceHolder mSurfaceHolder;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
