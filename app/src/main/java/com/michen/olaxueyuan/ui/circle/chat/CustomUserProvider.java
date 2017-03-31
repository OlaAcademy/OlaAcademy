package com.michen.olaxueyuan.ui.circle.chat;

import android.text.TextUtils;

import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.SEUserByPhoneResult;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wli on 15/12/4.
 * 实现自定义用户体系
 */
public class CustomUserProvider implements LCChatProfileProvider {

    private static CustomUserProvider customUserProvider;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    // 此数据均为 fake，仅供参考
    static {
        partUsers.add(new LCChatKitUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
        partUsers.add(new LCChatKitUser("Jerry", "Jerry", "http://www.avatarsdb.com/avatars/jerry.jpg"));
        partUsers.add(new LCChatKitUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
        partUsers.add(new LCChatKitUser("William", "William", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"));
        partUsers.add(new LCChatKitUser("Bob", "Bob", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));
    }

    public void setpartUsers(LCChatKitUser lcChatKitUser) {
        partUsers.add(lcChatKitUser);
    }

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
//        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        Logger.e("userList==" + list.toString());
        String listString = "";
        for (String userId : list) {
            listString = userId + ",";
        }
        if (!TextUtils.isEmpty(listString)) {
            queryUserByPhoneNumbers(listString, callBack);
        }
//        callBack.done(userList, null);
    }

    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }

    private void queryUserByPhoneNumbers(String phones, final LCChatProfilesCallBack callBack) {
        SEUserManager.getInstance().queryUserByPhoneNumbers(phones, new Callback<SEUserByPhoneResult>() {
            @Override
            public void success(SEUserByPhoneResult seUserByPhoneResult, Response response) {
                if (seUserByPhoneResult.getApicode() == 10000) {
                    List<SEUserByPhoneResult.ResultBean> result = seUserByPhoneResult.getResult();
                    List<LCChatKitUser> userList = new ArrayList<>();
                    String avatarUrl = "";
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getAvator().contains("http://")) {
                            avatarUrl = result.get(i).getAvator();
                        } else if (result.get(i).getAvator().contains(".")) {
                            avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + result.get(i).getAvator();
                        } else {
                            avatarUrl = SEAPP.PIC_BASE_URL + result.get(i).getAvator();
                        }
                        CustomUserProvider.getInstance().setpartUsers(new LCChatKitUser(result.get(i).getPhone(), result.get(i).getName(), avatarUrl));
                        userList.add(new LCChatKitUser(result.get(i).getPhone(), result.get(i).getName(), avatarUrl));
                    }
                    Logger.e("userList=" + userList.toString());
                    callBack.done(userList, null);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
