package com.dsm.xiaodiif;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.base.util.BaseUtil;
import com.base.util.ToastUtil;
import com.base.util.log.LogUtil;
import com.dsm.xiaodi.biz.sdk.XiaodiSdkLibInit;
import com.dsm.xiaodi.biz.sdk.business.BusinessResponse;
import com.dsm.xiaodi.biz.sdk.business.adddevice.AddDevice;
import com.dsm.xiaodi.biz.sdk.business.deviceinfo.WifiUpdate;
import com.dsm.xiaodi.biz.sdk.servercore.ServerUnit;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView logTextView;
    View dialogView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.initButton).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.addLockButton).setOnClickListener(this);
        findViewById(R.id.configWifiButton).setOnClickListener(this);
        findViewById(R.id.loadDeviceListButton).setOnClickListener(this);
        findViewById(R.id.uploadLogButton).setOnClickListener(this);
        logTextView = (TextView) findViewById(R.id.logTextView);
        dialog = new ProgressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("MainActivity", "onPause");
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.initButton) {
            //库初始化
            XiaodiSdkLibInit.init(this, "25");
            ServerUnit.getInstance().enableOnlineServer();
            logTextView.setText("库初始化成功");
        } else if (view.getId() == R.id.loginButton) {
            dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_login, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请登录");
            builder.setView(dialogView);
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText accountEditText = (EditText) dialogView.findViewById(R.id.accountEditText);
                    EditText pwdEditText = (EditText) dialogView.findViewById(R.id.pwdEditText);
                    String account = accountEditText.getText().toString().trim();
                    String pwd = pwdEditText.getText().toString().trim();
                    //模拟调用服务器接口API，用户登录
                    ServerUnit.getInstance().login(
                            account,
                            pwd,
                            null,
                            new ServerUnit.OnServerUnitListener() {
                                @Override
                                public void success(final List data, final String msg) {
                                    runOnUiThread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                    logTextView.setText("登录成功,data=" + data + "\nmsg=" + msg);
                                                }
                                            }
                                    );
                                }

                                @Override
                                public void failure(final String error, final int loglever) {
                                    runOnUiThread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                    logTextView.setText("登录失败,error=" + error + "\nloglever=" + loglever);
                                                }
                                            }
                                    );
                                }
                            }
                    );
                }
            });
            builder.show();
        } else if (view.getId() == R.id.addLockButton) {
            dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_addlock, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请输入设备信息");
            builder.setView(dialogView);
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("添加设备", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.show();
                    EditText accountEditText = (EditText) dialogView.findViewById(R.id.accountEditText);
                    EditText nickEditText = (EditText) dialogView.findViewById(R.id.nickEditText);
                    EditText locknameEditText = (EditText) dialogView.findViewById(R.id.locknameEditText);
                    EditText lockmacEditText = (EditText) dialogView.findViewById(R.id.lockmacEditText);
                    EditText managepwdEditText = (EditText) dialogView.findViewById(R.id.managepwdEditText);
                    EditText metertypeEditText = (EditText) dialogView.findViewById(R.id.metertypeEditText);
                    //模拟调用基本业务接口API，添加设备
                    new AddDevice(
//                            accountEditText.getText().toString().trim(),
//                            nickEditText.getText().toString().trim(),
//                            locknameEditText.getText().toString().trim(),
//                            lockmacEditText.getText().toString().trim(),
//                            managepwdEditText.getText().toString().trim(),
//                            metertypeEditText.getText().toString().trim(),
                            "18668165280",
                            "dccjll",
                            "T700_978e",
                            "aaaa",
                            "88888888",
                            "T700_0",
                            "LOCK",
                            "",
                            "",
                            null,
                            new AddDevice.OnAddDeviceListener() {
                                @Override
                                public void addSuccessOnline(final Object object, final String locksn, final String lockChannelPassword, final String lockSoftwareVersion) {
                                    runOnUiThread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                    logTextView.setText("设备添加成功(在线),object=" + object + "\nlocksn=" + locksn + "\nlockChannelPassword=" + lockChannelPassword + "\nlockSoftwareVersion=" + lockSoftwareVersion);
                                                }
                                            }
                                    );
                                }

                                @Override
                                public void addSuccessOffline(final String lockmac, final String locknameString, final String belongUserAccount, final String belongUserNickName, final String managepwd, final String metertype, final String locksn, final String lockChannelPassword, final String lockSoftwareVersion) {
                                    runOnUiThread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                    logTextView.setText("设备添加成功(离线),lockmac=" + lockmac + "\nlocknameString=" + locknameString + "\nbelongUserAccount=" + belongUserAccount + "\nbelongUserNickName=" + belongUserNickName + "\nmanagepwd=" + managepwd + "\nmetertype=" + metertype + "\nlocksn=" + locksn + "\nlockChannelPassword=" + lockChannelPassword + "\nlockSoftwareVersion=" + lockSoftwareVersion);
                                                }
                                            }
                                    );
                                }

                                @Override
                                public void addFailure(final String error, final int loglever) {
                                    runOnUiThread(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                    logTextView.setText("设备添加失败,error=" + error + "\nloglever=" + loglever);
                                                }
                                            }
                                    );
                                }
                            }
                    ).walk();
                }
            });
            builder.show();
        } else if (view.getId() == R.id.configWifiButton) {
            new WifiUpdate("FE:0A:A3:D2:97:8E", "Xiaomi_89E6", "qazwsx2017", "A0.1.007_20161201", new BusinessResponse() {
                @Override
                public void onSuccess(final Object data) {
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    logTextView.setText("wifi配置成功,data=" + data);
                                }
                            }
                    );
                }

                @Override
                public void onFailure(final String error, final int loglevel) {
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    logTextView.setText("wifi配置失败,error=" + error + ",loglevel=" + BaseUtil.getMsgByLoglevel(error, loglevel, "wifi配置失败"));
                                }
                            }
                    );
                }
            }).walk();
        } else if (view.getId() == R.id.loadDeviceListButton) {
            dialog.show();
            ServerUnit.getInstance().loadMainDeviceList("18668165280", null, new ServerUnit.OnServerUnitListener() {
                @Override
                public void success(final List list, String s) {
                    dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logTextView.setText(list.toString());
                        }
                    });
                }

                @Override
                public void failure(final String s, int i) {
                    dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logTextView.setText(s);
                        }
                    });
                }
            });
        } else if (view.getId() == R.id.uploadLogButton) {
            dialog.show();
            ServerUnit.getInstance().uploadOperatorLog(new ServerUnit.OnServerUnitListener() {
                @Override
                public void success(List data, String msg) {
                    dialog.dismiss();
                    ToastUtil.showToast("上传log日志成功");
                }

                @Override
                public void failure(String error, int loglevel) {
                    LogUtil.e(error);
                    dialog.dismiss();
                    ToastUtil.showToast("上传log日志失败");
                }
            });
        }
    }
}
