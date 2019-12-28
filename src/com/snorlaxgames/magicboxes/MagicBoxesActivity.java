package com.snorlaxgames.magicboxes;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class MagicBoxesActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splashlayout);
		
		new Handler().postDelayed(new Thread(){
			@Override
			public void run(){
				Intent intent = new Intent(MagicBoxesActivity.this, GameActivity.class);
				MagicBoxesActivity.this.startActivity(intent);
				MagicBoxesActivity.this.finish();
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		}, 3000l);
	}
}
