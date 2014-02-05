package nthu.cs.minna_no_nihongo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class LessonListView extends Activity {
	
	private String[] list = new String[50];
	private ArrayAdapter<String> listAdapter;
	
	private void initList(){
		for(int i = 1; i <= 50; i++)
			list[i-1] = "第"+i+"課";
	}
	
	private void initListView() {
		ListView listView = (ListView) this.findViewById(R.id.listView);
		
		listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
		   listView.setAdapter(listAdapter);
		   listView.setOnItemClickListener(new OnItemClickListener(){
		       @Override
		       public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		    
		          Toast.makeText(getApplicationContext(),"你選擇的是"+list[position], Toast.LENGTH_SHORT).show();
		          try{
		        	  Intent intent = new Intent(LessonListView.this, WebActivity.class);
		        	  intent.putExtra("name", "lesson"+(position+1));
			          startActivity(intent);
			          
		          }
		          catch(Exception e){
		        	  Log.e("MNN", e.getStackTrace().toString());
		          }
		       }
		   });
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		initList();
		initListView();
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
    	AlertDialog.Builder ad = new AlertDialog.Builder(LessonListView.this);
    	ad.setTitle("退出");
    	ad.setMessage("是否退出 みんなの日本語?");
    	ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按钮
			@Override
			public void onClick(DialogInterface dialog, int i) {
				// TODO Auto-generated method stub
				LessonListView.this.finish();//Shutdown activity
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
