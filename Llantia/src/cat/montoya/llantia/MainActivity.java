package cat.montoya.llantia;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;

public class MainActivity extends Activity implements SurfaceHolder.Callback {
	private Camera _camera;
	private SurfaceView _surfaceView;
	private SurfaceHolder _surfaceHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		_surfaceHolder = _surfaceView.getHolder();
		_surfaceHolder.addCallback(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void toggleFlash(View view) {
		if (_camera == null
				|| Parameters.FLASH_MODE_OFF.equals(_camera.getParameters()
						.getFlashMode())) {
			try {
				startFlash();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			stopFlash();
			// _camera.getParameters().setFlashMode(Parameters.FLASH_MODE_OFF);
			// _camera.stopPreview();
			// _camera.setPreviewCallback(null);
			// _camera.release();
		}
	}

	private void startFlash() throws IOException {
		
		Camera.Parameters param = _camera.getParameters();
		param.setFlashMode(Parameters.FLASH_MODE_TORCH);
		_camera.setParameters(param);
		_camera.setPreviewDisplay(_surfaceHolder);
		_camera.startPreview();
		_camera.stopPreview();

	}

	private void stopFlash() {
		Camera.Parameters param = _camera.getParameters();
		param.setFlashMode(Parameters.FLASH_MODE_OFF);
		_camera.setParameters(param);
		_camera.startPreview();
		_camera.stopPreview();		
	}

	@Override
	protected void onStop() {
		super.onStop();
		_camera.release();
	}

	@Override
	protected void onStart() {
		super.onStart();
		onStartOnResume();
	}
	
	@Override
	protected void onResume() {		
		super.onResume();
		onStartOnResume();
	}
	
	private void onStartOnResume(){
		if (_camera == null) {
			_camera = Camera.open();
		} else {
			try {
				_camera.reconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}
