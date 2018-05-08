package com.minersdk.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ofbank.work.impl.RequestObserver;
import com.ofbank.work.manager.Miner;
import com.ofbank.work.manager.MinerSDK;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 请求的回调
     */
    private MyRequestObserver mRequestObserver;
    /**
     * 服务器返回正确信息提示文本
     */
    private TextView mResultTextView;
    /**
     * 登录返回的token
     */
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTextView = findViewById(R.id.request_result);
        LinearLayout itemLayout = findViewById(R.id.item_layout);
        addListener(itemLayout);
        mRequestObserver = new MyRequestObserver();
    }

    //TODO demo上为了方便，写了固定的用户名,实际开发中接入方传入约定的用户名
    public String getUserName() {
        return "努力的黄金矿工";
    }

    //TODO demo上为了方便，写了固定的密码,实际开发中接入方传入约定的用户密码
    public String getUserPwd() {
        return "123456789";
    }

    //TODO demo上为了方便，写了固定的token有效期，实际开发中改为自定义的值
    public String getTokenExpireTime() {
        return "1000";
    }

    public void parseTokenJson(String result) {
        try {
            JSONObject rootObj = new JSONObject(result);
            mToken = rootObj.getString("uToken");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register://注册
                register();
                break;
            case R.id.login://登录
                login();
                break;
            case R.id.start_mining://启动挖矿服务
                if (isLogined()) {
                    Miner.startMining(mToken);
                }
                break;
            case R.id.stop_mining://停止挖矿服务
                Miner.stopMining();
                break;
            case R.id.get_reward_list://获取挖矿记录
                if (isLogined()) {
                    Miner.getRewardList(1, 10, mToken, mRequestObserver);
                }
                break;
            case R.id.with_draw://提现
                if (isLogined()) {
                    //TODO 第一个参数为转账接收方地址,demo中的地址为示例，接入方请自行替换！
                    Miner.withdrawCash("0x000008009cceda218bed5dd52f4135ac7e12353deed9c96c71", "1", mToken, mRequestObserver);
                }
                break;
            case R.id.get_with_draw_transaction://获取提现记录
                if (isLogined()) {
                    Miner.getWithDrawTransactionList(1, 10, mToken, mRequestObserver);
                }
                break;
        }
    }

    private void register() {
        Miner.register(getUserName(), getUserPwd(), new MyRequestObserver() {
            @Override
            public void onFailure() {
                showToast("登录出错，请在log中查看错误日志");
            }
        });
    }

    /**
     * 用户是否已登录
     *
     * @return
     */
    public boolean isLogined() {
        boolean tokenIsEmpty = TextUtils.isEmpty(mToken);
        if (tokenIsEmpty) {
            showToast("token为空，请先调用登录");
        }
        return !tokenIsEmpty;
    }

    /**
     * 登录
     */
    public void login() {
        Miner.login(getUserName(), getUserPwd(), getTokenExpireTime(), new MyRequestObserver() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                parseTokenJson(s);
            }

            @Override
            public void onFailure() {
                showToast("登录出错，请在log中查看错误日志");
            }
        });
    }

    /**
     * 设置点击事件
     */
    private void addListener(ViewGroup viewGroup) {
        int itemCount = viewGroup.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View itemView = viewGroup.getChildAt(i);
            if (itemView instanceof ViewGroup) {
                addListener((ViewGroup) itemView);
            } else {
                if (itemView instanceof Button) {
                    itemView.setOnClickListener(this);
                }
            }
        }
    }

    public class MyRequestObserver implements RequestObserver {

        @Override
        public void onStart() {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onSuccess(String s) {
            if (!TextUtils.isEmpty(s)) {
                mResultTextView.setText(s);
            }
        }

        @Override
        public void onFailure() {
            if (TextUtils.isEmpty(mToken)) {
                showToast("❌调用其他接口前请先调用【登录】❌");
            } else {
                showToast("请求出错，请在log中查看错误日志");
            }
        }
    }

    public void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

    }
}
