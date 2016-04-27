package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;

import zjut.jianlu.breakfast.R;

/**
 * 聊天页面，需要fragment的使用
 */
public class ChatActivity extends EaseBaseActivity {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        //聊天人或群id
        toChatUsername = getIntent().getExtras().getString("userId");
        //可以直接new EaseChatFratFragment使用
        chatFragment = new EaseChatFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        //传入参数
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }
}
