package cat.montoya.llantia;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Camera _camera;
	private SurfaceView _surfaceView;
	private SurfaceHolder _surfaceHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * Method called when we click on the SurfaceView
	 * 
	 * @param view
	 */
	public void toggleFlash(View view) {
		if (Parameters.FLASH_MODE_OFF.equals(_camera.getParameters()
				.getFlashMode())) {
			startFlash();
		} else {
			stopFlash();
		}
	}

	private void startFlash() {
		Camera.Parameters params = _camera.getParameters();
		params.setFlashMode(Parameters.FLASH_MODE_TORCH);
		_camera.setParameters(params);
		try {
			_camera.setPreviewDisplay(_surfaceHolder);
		} catch (IOException e) {
			toast("Error loading surface holder");
		}
		onOffPreview();
	}

	private void stopFlash() {
		Camera.Parameters params = _camera.getParameters();
		params.setFlashMode(Parameters.FLASH_MODE_OFF);
		_camera.setParameters(params);
		onOffPreview();
	}

	private void onOffPreview() {
		_camera.startPreview();
		_camera.stopPreview();
	}

	@Override
	protected void onStop() {
		super.onStop();
		releaseCamera();
	}

	@Override
	protected void finalize() throws Throwable {
		releaseCamera();
		super.finalize();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initCamera();
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera();
	}

	private void releaseCamera() {
		if (_camera != null) {
			_camera.release();
		}
	}

	private void initCamera() {
		_camera = Camera.open();
		_surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		_surfaceHolder = _surfaceView.getHolder();
	}

	private void toast(CharSequence text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

}
