package edu.skku.map.week12_class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.ListTemplate;
import com.kakao.message.template.SocialObject;
import com.kakao.message.template.TemplateParams;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("KAKAO API", "세션이 닫혀 있음: "+ errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                TextView myText = findViewById(R.id.user_name);
                Log.i("KAKAO API", "사용자 아이디: " + result.getId());
                UserAccount kakaoAccount = result.getKakaoAccount();
                if (kakaoAccount != null){
                    Profile profile = kakaoAccount.getProfile();
                    if(profile != null){
                        myText.setText(profile.getNickname());
                    } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE){

                    } else{

                    }
                }

            }
        });

        Button button1 = findViewById(R.id.send_button);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LinkObject link = LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .build();
                TemplateParams params = TextTemplate.newBuilder("Text", link)
                        .setButtonTitle("This is button")
                        .build();

                KakaoTalkService.getInstance().requestSendMemo(new TalkResponseCallback<Boolean>() {
                    @Override
                    public void onNotKakaoTalkUser() {
                        Log.e("KAKAO API", "not kakaotalk user.");
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO API", "Session closed: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult){
                        Log.e("KAKAO API", "sending failed: " + errorResult);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Log.i("KAKAO API", "successfully send");
                    }
                }, params);
            }
        });


        Button button2 = findViewById(R.id.logout_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Log.i("KAKAO API", "로그아웃 완료");
                    }
                });
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
