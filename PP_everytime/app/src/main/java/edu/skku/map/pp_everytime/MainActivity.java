package edu.skku.map.pp_everytime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TemplateParams;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // run at the first time to get hashkey
//        getHashKey();

        Session.getCurrentSession().addCallback(sessionCallback);
    }

    // only used at the first time to get hashkey
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try{
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        if(packageInfo == null){
            Log.e("KeyHash", "KeyHash:null");
        }

        for(Signature signature: packageInfo.signatures){
            try{
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }catch(NoSuchAlgorithmException e){
                Log.e("KeyHash", "Unable to get MessageDigest. signature="+signature, e);
            }
        }
    }

    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO SESSION", "로그인 성공");
            Intent intent = new Intent(MainActivity.this, MajorActivity.class);
            startActivity(intent);
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.i("KAKAO SESSION", "로그인 실패", exception);
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(Session.getCurrentSession().handleActivityResult(requestCode, requestCode, data)){
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
