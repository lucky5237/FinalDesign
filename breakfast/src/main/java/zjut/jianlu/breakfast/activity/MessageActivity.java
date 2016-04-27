/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import zjut.jianlu.breakfast.R;

public class MessageActivity extends EaseBaseActivity {

    private EaseConversationListFragment conversationListFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);

        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MessageActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.container, conversationListFragment).commit();
    }

}
