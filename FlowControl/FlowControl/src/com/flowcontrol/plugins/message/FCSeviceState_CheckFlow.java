package com.flowcontrol.plugins.message;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.flowcontrol.FCAppController;
import com.flowcontrol.log_manager.FCLog;
import com.flowcontrol.plugins.setting.bean.FCSettingBean;

public class FCSeviceState_CheckFlow extends Service {

	Integer checkTime_ = null;
	final int handlerMessageIndex_ = 0;

	@Override
	public void onCreate() {
		FCLog.i("  FCCheckFlowService onCreate...");

		FCAppController app = (FCAppController) this.getApplication();
		FCSettingBean bean = app.getLocationContext().getSettingInfo();
		checkTime_ = bean.mCheckFlowMinute;

		startCheckHandler();

		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		FCLog.i("  FCCheckFlowService onBind...");
		return null;
	}

	@Override
	public void onDestroy() {
		stopCheckHandler();
		super.onDestroy();
	}

	private void startCheckHandler() {
		Message message = handler.obtainMessage(handlerMessageIndex_);
		if (checkTime_ != null) {
			handler.sendMessageDelayed(message, checkTime_ * 1000);
		}
	}

	private void stopCheckHandler() {
		handler.removeMessages(handlerMessageIndex_);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// do something
			FCLog.i("check flow..... checkTime_=" + checkTime_);

			Message message = handler.obtainMessage(handlerMessageIndex_);
			if (checkTime_ != null) {
				handler.sendMessageDelayed(message, checkTime_ * 1000);
			}
		}
	};

}
