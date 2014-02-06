package nthu.cs.minna_no_nihongo;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.ProgressBar;
 
public class WebActivity extends Activity implements OnPreparedListener, MediaController.MediaPlayerControl, OnTouchListener{
    private final static String PLAYING_POSITION = "PLAYING_POSITION";
	private final static String LESSON_ID = "LESSON_ID";
	private final static String IS_PAUSE = "IS_PAUSE";
	
	private WebView webView;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private Handler handler = new Handler();
    
    private String name;
    private int startOffset;
    private boolean isPause;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        
        int lesson = this.getIntent().getIntExtra(LESSON_ID, 1); 
        String title = getResources().getString(R.string.app_name)+" - 第"+lesson+"課";
        name = "lesson"+lesson;
        setTitle(title);
        
        if(savedInstanceState != null){
        	Log.d("MNN", "recover");
        	startOffset = savedInstanceState.getInt(PLAYING_POSITION);
        	isPause = savedInstanceState.getBoolean(IS_PAUSE);
        }
        else {
			startOffset = 0;
		}
     
        webView = (WebView) this.findViewById(R.id.webView);
        // set a webChromeClient to track progress    
        webView.setWebChromeClient(new WebChromeClient() {
        	@Override
        	public void onProgressChanged(WebView view, int progress){
        		if(progress == 100){
        			if(!isPause)        				        			       	
        				mediaPlayer.start();        			
        			mediaController.show();
        			((ProgressBar)WebActivity.this.findViewById(R.id.progressBar)).setVisibility(ProgressBar.GONE);
        		}
        	}
        });
        
        initAudio();
        
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setPluginState(PluginState.ON_DEMAND);
        
        
        webView.setOnTouchListener(this);
        webView.setInitialScale(1);
        webView.loadUrl("file:///android_asset/lessons_html/" + name + ".html");
        
    } 
    
    private void initAudio() {
    	mediaPlayer = new MediaPlayer();
    	mediaPlayer.setOnPreparedListener(this);
  	    mediaController = new MediaController(this);
   	    try {
   	    	AssetFileDescriptor descriptor = getAssets().openFd("lessons_mp3/" + name + ".mp3");   	    
   	        mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
   	        descriptor.close();

   	    	mediaPlayer.prepare();
   	    	mediaPlayer.setLooping(true);
   	    	mediaPlayer.seekTo(startOffset);
   	    	
   	    } catch (Exception e) {
   	    	Log.e("MNN", "Could not open file " + name + " for playback.", e);
   	    }
   	    
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putInt(PLAYING_POSITION, mediaPlayer.getCurrentPosition());
    	savedInstanceState.putBoolean(IS_PAUSE, !(mediaPlayer.isPlaying()));
    	Log.d("MNN", "onSaveInstance");
    }
    @Override
    protected void onResume(){
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mediaPlayer.pause();
    }
    
	@Override
	protected void onStop() {
		super.onStop();
		
    }
	@Override protected void onDestroy() {
		super.onDestroy();
		mediaController.hide();
		mediaPlayer.stop();
		mediaPlayer.release();
	}
    //--MediaPlayerControl methods----------------------------------------------------
    public void start() {
    	mediaPlayer.start();
    }

    public void pause() {
    	mediaPlayer.pause();
    }

    public int getDuration() {
    	return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
    	return mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int i) {
    	mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
    	return mediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
    	return 0;
    }

    public boolean canPause() {
    	return true;
    }

    public boolean canSeekBackward() {
    	return true;
    }

    public boolean canSeekForward() {
      return true;
    }
    //--------------------------------------------------------------------------------

    public void onPrepared(MediaPlayer mediaPlayer) {
      Log.d("MNN", "onPrepared");
      mediaController.setMediaPlayer(this);
      mediaController.setAnchorView(findViewById(R.id.main_webview));

      handler.post(new Runnable() {
        public void run() {
          mediaController.setEnabled(true);
          mediaController.show();
        }
      });
    }
    
    
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		//the MediaController will hide after 3 seconds - tap the screen to make it appear again
		if(!mediaController.isShowing()){
			mediaController.show(8000);
		}
		
		return false;
	}
}