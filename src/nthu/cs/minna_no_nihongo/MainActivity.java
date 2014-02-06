package nthu.cs.minna_no_nihongo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	//Use as a welcome activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView imageView = (ImageView) this.findViewById(R.id.imageView1);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, LessonListView.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
		});
	}	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// When pressed return, it will goto desktop
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        confirmExit();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	public void confirmExit(){
    	AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
    	ad.setTitle("退出");
    	ad.setMessage("是否退出 みんなの日本語?");
    	ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按钮
			@Override
			public void onClick(DialogInterface dialog, int i) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();//Shutdown activity
			}
		});
    	ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int i) {
				
			}
		});
    	ad.show();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
