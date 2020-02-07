package com.example.a12053.voicectroller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends Activity implements OnClickListener {

    //存放听写分析结果文本
    private static final String TAG = MainActivity.class .getSimpleName();
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private Button l_btn;       // 语音听写按键
//    private Button r_btn;       // 合成语音按键
    private EditText e_text;    // 编辑文字按键
    private TextView textView;  // 反馈结果文本框
    private Spinner dialect_spinner;
    private TextView dialect_text;
    private ArrayAdapter<String> adapter;

    private static final String[] dialect = {
            "四川话", "河南话", "武汉话", "广东话", "甘肃话",
            "客家话", "河北话", "合肥话", "闽南话", "台湾话",
            "云南话", "东北话", "陕西话", "苏州话", "太原话",
            "天津话", "皖北话", "贵州话", "宁夏话", "南昌话",
            "山东话", "上海话", "长沙话"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialect_spinner = findViewById(R.id.dialect_spinner);
        dialect_text = findViewById(R.id.dialect_text);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dialect);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialect_spinner.setAdapter(adapter);
        dialect_spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        dialect_spinner.setVisibility(View.VISIBLE);

        l_btn = findViewById(R.id.listen_btn);
//        r_btn = findViewById(R.id.read_btn);
        e_text = findViewById(R.id.content_et);
        textView = findViewById(R.id.textView);

        l_btn.setOnClickListener(this);
//        r_btn.setOnClickListener(this);

        SpeechUtility.createUtility(this, SpeechConstant. APPID + "=5b932d09");
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            dialect_text.setText("你选择的方言为："+dialect[arg2]);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listen_btn:  // 语音识别 （把语音转成文字）
                startSpeechDialog();
                break;
            case R.id.read_btn:    // 语音合成 （把文字转成语音）
//                speekText();
                break;
            default:
                break;
        }
    }

//    private void speekText() {
//        // 创建 SpeechSynthesizer 对象 , 第二个参数： 本地合成时传 InitListener
//        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer( this, null);
//        // 合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
//        // 设置发音人（更多在线发音人，用户可参见 附录 13.2
//        mTts.setParameter(SpeechConstant. VOICE_NAME, "vixyun" ); // 设置发音人
//        mTts.setParameter(SpeechConstant. SPEED, "50" );// 设置语速
//        mTts.setParameter(SpeechConstant. VOLUME, "80" );// 设置音量，范围 0~100
//        mTts.setParameter(SpeechConstant. ENGINE_TYPE, SpeechConstant. TYPE_CLOUD); //设置云端
//        // 设置合成音频保存位置（可自定义保存位置），保存在 “./sdcard/iflytek.pcm”
//        // 保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
//        // 仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
//        mTts.setParameter(SpeechConstant. TTS_AUDIO_PATH, "./sdcard/iflytek.pcm" );
//        // 3.开始合成
//        mTts.startSpeaking( e_text.getText().toString(), new MySynthesizerListener()) ;
//
//    }
//
//    class MySynthesizerListener implements SynthesizerListener {
//
//        @Override
//        public void onSpeakBegin() {
//            showTip(" 开始播放 ");
//        }
//
//        @Override
//        public void onSpeakPaused() {
//            showTip(" 暂停播放 ");
//        }
//
//        @Override
//        public void onSpeakResumed() {
//            showTip(" 继续播放 ");
//        }
//
//        @Override
//        public void onBufferProgress(int percent, int beginPos, int endPos ,
//                                     String info) {
//            // 合成进度
//        }
//
//        @Override
//        public void onSpeakProgress(int percent, int beginPos, int endPos) {
//            // 播放进度
//        }
//
//        @Override
//        public void onCompleted(SpeechError error) {
//            if (error == null) {
//                showTip("播放完成 ");
//            } else if (error != null ) {
//                showTip(error.getPlainDescription( true));
//            }
//        }
//
//        @Override
//        public void onEvent(int eventType, int arg1 , int arg2, Bundle obj) {
//            // 以下代码用于获取与云端的会话 id，当业务出错时将会话 id提供给技术支持人员，可用于查询会话日志，定位出错原因
//            // 若使用本地能力，会话 id为null
//            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
//                 String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
//                 Log.d(TAG, "session id =" + sid);
//            }
//        }
//    }


    private void startSpeechDialog(){
        // 初始化RecognizerDialog
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
        // 将语言设置成中文
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 将语言设置成普通话（后期可选择为方言）
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        mDialog.setListener(new MyRecognizerDialogListener());
        mDialog.show();
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener{
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String text = JsonParser.parseIatResult(recognizerResult.getResultString());

            String sn = null;
            // 读取json结果中的 sn字段
            try {
                JSONObject resultJson = new JSONObject(recognizerResult.getResultString()) ;
                sn = resultJson.optString("sn" );
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults .put(sn, text) ;//没有得到一句，添加到

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults .get(key));
            }

            textView.setText(resultBuffer.toString());
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    }

    class MyInitListener implements InitListener{
        @Override
        public void onInit(int i) {
            if(i != ErrorCode.SUCCESS){
                textView.setText("初始化失败...");
            }
        }
    }


    private void startSpeech() {
        //1. 创建SpeechRecognizer对象，第二个参数： 本地识别时传 InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer( this, null); //语音识别器
        //2. 设置听写参数，详见《 MSC Reference Manual》 SpeechConstant类
        mIat.setParameter(SpeechConstant. DOMAIN, "iat" );// 短信和日常用语： iat (默认)
        mIat.setParameter(SpeechConstant. LANGUAGE, "zh_cn" );// 设置中文
        mIat.setParameter(SpeechConstant. ACCENT, "mandarin" );// 设置普通话
        //3. 开始听写
        mIat.startListening( mRecoListener);
    }


    // 听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        // 听写结果回调接口 (返回Json 格式结果，用户可参见附录 13.1)；
        // 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        // 关于解析Json的代码可参见 Demo中JsonParser 类；
        // isLast等于true 时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.e (TAG, results.getResultString());
            System.out.println(results.getResultString()) ;
            showTip(results.getResultString()) ;
        }

        // 会话发生错误回调接口
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true)) ;
            // 获取错误码描述
            Log. e(TAG, "error.getPlainDescription(true)==" + error.getPlainDescription(true ));
        }

        // 开始录音
        public void onBeginOfSpeech() {
            showTip(" 开始录音 ");
        }

        //volume 音量值0~30， data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
            showTip(" 声音改变了 ");
        }

        // 结束录音
        public void onEndOfSpeech() {
            showTip(" 结束录音 ");
        }

        // 扩展用接口
        public void onEvent(int eventType, int arg1 , int arg2, Bundle obj) {
        }
    };

    private void showTip (String data) {
        Toast.makeText( this, data, Toast.LENGTH_SHORT).show() ;
    }

}

class JsonParser {

    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer() ;
        try {
            JSONTokener tokener = new JSONTokener(json) ;
            JSONObject joResult = new JSONObject(tokener) ;

            JSONArray words = joResult.getJSONArray("ws" );
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw" );
                JSONObject obj = items.getJSONObject(0 );
                ret.append(obj.getString("w" ));
//                  如果需要多候选结果，解析数组其他字段
//                 for(int j = 0; j < items.length(); j++)
//                 {
//                      JSONObject obj = items.getJSONObject(j);
//                      ret.append(obj.getString("w"));
//                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
    public static String parseGrammarResult(String json) {
        StringBuffer ret = new StringBuffer() ;
        try {
            JSONTokener tokener = new JSONTokener(json) ;
            JSONObject joResult = new JSONObject(tokener) ;
            JSONArray words = joResult.getJSONArray("ws" );
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw" );
                for (int j = 0; j < items.length() ; j++)
                {
                    JSONObject obj = items.getJSONObject(j);
                    if (obj.getString("w").contains( "nomatch"))
                    {
                        ret.append( "没有匹配结果.") ;
                        return ret.toString();
                    }
                    ret.append( "【结果】" + obj.getString("w" ));
                    ret.append("【置信度】 " + obj.getInt("sc" ));
                    ret.append("\n ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.append(" 没有匹配结果 .");
        }
        return ret.toString();
    }
    public static String parseLocalGrammarResult(String json) {
        StringBuffer ret = new StringBuffer() ;
        try {
            JSONTokener tokener = new JSONTokener(json) ;
            JSONObject joResult = new JSONObject(tokener) ;
            JSONArray words = joResult.getJSONArray("ws" );
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw" );
                for (int j = 0; j < items.length() ; j++)
                {
                    JSONObject obj = items.getJSONObject(j);
                    if (obj.getString("w").contains( "nomatch"))
                    {
                        ret.append( "没有匹配结果.") ;
                        return ret.toString();
                    }
                    ret.append( "【结果】" + obj.getString("w" ));
                    ret.append("\n ");
                }
            }
            ret.append("【置信度】 " + joResult.optInt("sc" ));
        } catch (Exception e) {
            e.printStackTrace();
            ret.append(" 没有匹配结果 .");
        }
        return ret.toString();
    }
}
