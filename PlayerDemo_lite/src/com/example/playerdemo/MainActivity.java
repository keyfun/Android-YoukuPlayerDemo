package com.example.playerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.youku.player.ApiManager;
import com.youku.player.YoukuPlayerBaseConfiguration;
import com.youku.player.apiservice.QualityVidReq;
import com.youku.player.apiservice.QualityVidReq.GetQualityListener;

public class MainActivity extends Activity implements View.OnClickListener{
	Button btn_player,/*btn_login,btn_logout,btn_state,*/btn_downloaded_video,btn_downloading_video;
	EditText et;
	//demo获取随机视频地址的开关
	private CheckBox checkBox;
	private String defaultVid = "XMTcwOTM3MjQ0NA==";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		init();

	}
	
	private void init(){
		btn_player = (Button)this.findViewById(R.id.btn_player);						//跳转到播放界面按钮
//		btn_login = (Button)this.findViewById(R.id.btn_login);						//登陆按钮
//		btn_logout = (Button)this.findViewById(R.id.btn_logout);						//登出按钮
//		btn_state = (Button)this.findViewById(R.id.btn_state);						//获取登陆状态
		btn_downloaded_video = (Button)this.findViewById(R.id.btn_downloaded);		//接入已经下载视频的展示界面
		btn_downloading_video = (Button)this.findViewById(R.id.btn_downloading);		//进入正在下载视频的展示界面	
		checkBox = (CheckBox) findViewById(R.id.vidck);
		et = (EditText)this.findViewById(R.id.vid);									//测试用，输入视频id
		
		btn_player.setOnClickListener(this);
//		btn_login.setOnClickListener(this);
//		btn_logout.setOnClickListener(this);
//		btn_state.setOnClickListener(this);
		btn_downloaded_video.setOnClickListener(this);
		btn_downloading_video.setOnClickListener(this);
		
		
		et.setText(defaultVid);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					QualityVidReq.getInstance().getQualityVid(
							new GetQualityListener() {

								@Override
								public void onResult(String vid) {
									// TODO Auto-generated method stub
									if (!TextUtils.isEmpty(vid)) {
										et.setText(vid);
									}

								}
							});
				} else {
					et.setText(defaultVid);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_player:
			go2Player();					//跳转到播放界面
			break;
//		case R.id.btn_login:
//			doLogin();						//进行登陆操作
//			break;
//		case R.id.btn_logout:
//			doLogout();						//进行登出操作
//			break;		
//		case R.id.btn_state:
//			getLoginState();				//获取当前登陆的用户
//			break;
		case R.id.btn_downloaded:			
			go2DownloadedPage();			//跳转到已经下载的界面
			break;
		case R.id.btn_downloading:
			go2DownloadingPage();			//跳转到正在下载的界面
			break;
		}
	}
	
	
	/**
	 * 跳转到播放界面进行播放
	 */
	
	private void go2Player(){
		Intent i = new Intent(MainActivity.this,PlayerActivity.class);
		i.putExtra("vid", et.getText().toString().trim());
		MainActivity.this.startActivity(i);
	}
	
	/**
	 * 应用退出时调用此方法
	 */
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub	
		YoukuPlayerBaseConfiguration.exit();		//退出应用时请调用此方法
		
		super.onBackPressed();

	}
	
	/**
	 * 跳转到已经下载的界面
	 */
	private void go2DownloadedPage(){
		Intent i = new Intent(this,CachedActivity.class);
		startActivity(i);
		
	}
	
	/**
	 * 跳转到正在下载的界面
	 */
	private void go2DownloadingPage(){
		Intent i = new Intent(this,CachingActivity.class);
		startActivity(i);
		
	}	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (checkBox.isChecked()) {
			QualityVidReq.getInstance().getQualityVid(new GetQualityListener() {

				@Override
				public void onResult(String vid) {
					// TODO Auto-generated method stub
					if (!TextUtils.isEmpty(vid)) {
						et.setText(vid);
					}

				}
			});
		}

	}
}
