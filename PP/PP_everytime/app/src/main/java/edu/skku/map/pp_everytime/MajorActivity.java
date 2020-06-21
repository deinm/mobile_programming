package edu.skku.map.pp_everytime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MajorActivity extends AppCompatActivity {

    List<Button> buttonList = new ArrayList<Button>();
    FlexboxLayout flexboxLayout, newflexboxLayout;
    LinearLayout linearLayout, newlinearLayout;

    String userName, userId;
    int i = 0;

    // school data
    HashMap<String, String> school_id_map = new HashMap<String, String>(){{
        put("Dance", "SKD");
        put("Design", "DES");
        put("Fashion Design", "FDM");
        put("Film, Television and Multimedia", "FTM");
        put("Fine Art", "ART");
        put("Theatre", "DAT");

        put("Bio-Mechatronic Engineering", "EBM");
        put("Food Science and Biotechnology", "FBT");
        put("Integrative Biotechnology", "IBT");

        put("Business Administration", "BUS");
        put("Entrepreneurship & Innovation", "EPN");
        put("Global Business Administration", "GBA");

        put("Computer Science and Engineering", "SWE");

        put("Confucian and Oriental Studies", "COS");

        put("Economics", "ECO");
        put("Global Economics", "GEC");
        put("International Trade an Policy", "INT");
        put("Statistics", "STA");

        put("Computer Education", "COM");
        put("Education", "EDU");
        put("Education in Classical Chinese", "HAN");
        put("Mathematics Education", "MAE");

        put("Advanced Materials Science and Engineering", "EAM");
        put("Architecture", "ADD");
        put("Chemical Engineering", "ECH");
        put("Civil, Architectural Engineering and Landscape Architecture", "CAL");
        put("Mechanical Engineering", "EME");
        put("Systems Management Engineering", "ESM");

        put("Electronic and Eletical Engineering", "EEE");
        put("Information and Communication Engineering", "ICE");
        put("Semiconductor Systems Engineering", "SSE");

        put("Biomedical Engineering", "GBE");
        put("Culture and Technology", "CNT");
        put("Data Science", "DSC");
        put("Applied Artificial Intelligence", "AAI");

        put("Korean Studies", "IKS");

        put("Chinese Language and Literature", "CHI");
        put("Classics", "CLA");
        put("Cross-cultural Studies", "CCS");
        put("English Language and Literature", "ENG");
        put("French Language and Literature", "FRE");
        put("German Language and Literature", "GER");
        put("History", "HIS");
        put("Humanistic Future Studies", "HFS");
        put("Interdisciplinary Linguistics", "ILI");
        put("Japanology", "JAP");
        put("Korean Language and Literature", "DKL");
        put("Korean Literature in Classical Chinese", "KLC");
        put("Library and Information Science", "LIS");
        put("Philosophy", "PHL");
        put("Russian Language and Literature", "RUS");
        put("Studies of Glocal-Cultural Contents", "GCC");

        put("Medicine", "MED");

        put("Pharmacy", "PHR");

        put("Biological Sciences", "BIO");
        put("Chemistry", "CHY");
        put("Mathematics", "MTH");
        put("Physics", "PHY");

        put("Child Psychology and Education", "KID");
        put("College of Social Sciences", "USS");
        put("Consumer and Family Sciences", "CON");
        put("Contracts and Rights", "PIL");
        put("Media & Communication", "MCJ");
        put("Political Science and Diplomacy", "PSD");
        put("Psychology", "PSY");
        put("Public Administration", "PAD");
        put("Public Affairs", "GLD");
        put("Social Welfare", "SWF");
        put("Sociology", "SOC");

        put("Sport Science", "SPT");
    }};

    HashMap<String, ArrayList<String>> school_map = new HashMap<String, ArrayList<String>>(){{
        put("Art 🎨",new ArrayList<String>() {{ add("Dance"); add("Design"); add("Fashion Design"); add("Film, Television and Multimedia"); add("Fine Art"); add("Theatre");}});
        put("Biotechnology and Bioengineering 🌱",new ArrayList<String>() {{ add("Bio-Mechatronic Engineering"); add("Food Science and Biotechnology"); add("Integrative Biotechnology");}});
        put("Business 💵",new ArrayList<String>() {{ add("Business Administration"); add("Entrepreneurship & Innovation"); add("Global Business Administration");}});
        put("Computing 💻",new ArrayList<String>() {{ add("Computer Science and Engineering");}});
        put("Confucian Studies & Eastern philosophy 💭",new ArrayList<String>() {{ add("Confucian and Oriental Studies");}});
        put("Economics 📉",new ArrayList<String>() {{ add("Economics"); add("Global Economics"); add("International Trade an Policy"); add("Statistics");}});
        put("Education 🏫",new ArrayList<String>() {{ add("Computer Education"); add("Education"); add("Education in Classical Chinese"); add("Mathematics Education");}});
        put("Engineering ⚙️",new ArrayList<String>() {{ add("Advanced Materials Science and Engineering"); add("Architecture"); add("Chemical Engineering"); add("Civil, Architectural Engineering and Landscape Architecture"); add("Culture and Technology"); add("Mechanical Engineering"); add("Systems Management Engineering");}});
        put("Information and Communication Engineering ⚡",new ArrayList<String>() {{ add("Electronic and Eletical Engineering"); add("Information and Communication Engineering"); add("Semiconductor Systems Engineering");}});
        put("Institute for Convergence 🌎",new ArrayList<String>() {{ add("Biomedical Engineering"); add("Culture and Technology"); add("Data Science"); add("Applied Artificial Intelligence");}});
        put("International Office 🇰🇷",new ArrayList<String>() {{ add("Korean Studies");}});
        put("Liberal Arts 📖",new ArrayList<String>() {{ add("Chinese Language and Literature"); add("Classics"); add("Cross-cultural Studies"); add("English Language and Literature"); add("French Language and Literature"); add("German Language and Literature"); add("History"); add("Humanistic Future Studies"); add("Interdisciplinary Linguistics"); add("Japanology"); add("Korean Language and Literature"); add("Korean Literature in Classical Chinese"); add("Library and Information Science"); add("Philosophy"); add("Russian Language and Literature"); add("Studies of Glocal-Cultural Contents");}});
        put("Medicine 🏥",new ArrayList<String>() {{ add("Medicine");}});
        put("Pharmacy 💊",new ArrayList<String>() {{ add("Pharmacy");}});
        put("Science 🔬",new ArrayList<String>() {{ add("Biological Sciences"); add("Chemistry"); add("Mathematics"); add("Physics");}});
        put("Social Sciences 👨‍👩‍👧‍👦",new ArrayList<String>() {{ add("Child Psychology and Education"); add("College of Social Sciences"); add("Consumer and Family Sciences"); add("Contracts and Rights"); add("Media & Communication"); add("Political Science and Diplomacy"); add("Psychology"); add("Public Administration"); add("Public Affairs"); add("Social Welfare"); add("Sociology");}});
        put("Sport Science 🏃",new ArrayList<String>() {{ add("Sport Science");}});
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("KAKAO API", "세션이 닫혀 있음: "+ errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                TextView username = findViewById(R.id.user_name);
                userId = String.valueOf(result.getId());
                Log.i("KAKAO API", "사용자 아이디: " + userId);
                UserAccount kakaoAccount = result.getKakaoAccount();
                if (kakaoAccount != null){
                    Profile profile = kakaoAccount.getProfile();
                    if(profile != null){
                        username.setText(profile.getNickname());
                        userName = profile.getNickname();
                    } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE){

                    } else{

                    }
                }

            }
        });

        Button logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Log.i("KAKAO API", "로그아웃 완료");
                    }
                });
                Intent intent = new Intent(MajorActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button more_info = findViewById(R.id.more_info);

        more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkObject link = LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .build();
                String message = "Hello! Are you interested in some information about SKKU?\n" +
                        "SKKU has two campus, Seoul campus and Suwon campus.";
                TemplateParams params = TextTemplate.newBuilder(message, link)
                        .setButtonTitle("SKKU")
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

                String msg = "More infos sent to your Kakaotalk!";
                Toast myToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        flexboxLayout = (FlexboxLayout)findViewById(R.id.flexboxLayout);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

//        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflater.inflate(R.layout.activity_major, null);
//
//        flexboxLayout = (FlexboxLayout)v.findViewById(R.id.flexboxLayout);
//        setContentView(v);

        Iterator<String> keys = school_map.keySet().iterator();
        while( keys.hasNext() ){
            String key = keys.next();
//            System.out.println( String.format("키 : %s, 값 : %s", key, school_map.get(key)) );

            TextView textview = new TextView(this);
            textview.setText(key);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30, 0, 5);

            linearLayout.addView(textview, params);

            newlinearLayout = new LinearLayout(this);
            newlinearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(newlinearLayout);

            newflexboxLayout = new FlexboxLayout(this);
            newflexboxLayout.setAlignContent(AlignContent.CENTER);
            newflexboxLayout.setAlignItems(AlignItems.CENTER);
            newflexboxLayout.setFlexWrap(FlexWrap.WRAP);
            // Makes Content Align to center
//            newflexboxLayout.setJustifyContent(JustifyContent.CENTER);
            newlinearLayout.addView(newflexboxLayout);

            for (String element : school_map.get(key)){
                final Button button = new Button(this);
                button.setText(element);
                buttonList.add(button);
                buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button b = (Button)view;
                        String buttonText = b.getText().toString();
                        String schoolID = school_id_map.get(buttonText);
                        Toast myToast = Toast.makeText(getApplicationContext(), schoolID, Toast.LENGTH_SHORT);
                        myToast.show();

                        Intent lectureIntent = new Intent(getApplicationContext(), LectureActivity.class);
                        lectureIntent.putExtra("schoolName", buttonText);
                        lectureIntent.putExtra("schoolID", schoolID);
                        startActivity(lectureIntent);
                    }
                });
                newflexboxLayout.addView(button);
                i += 1;
                System.out.println(i+". "+element);
            }
        }
    }
}
