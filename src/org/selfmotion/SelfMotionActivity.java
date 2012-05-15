package org.selfmotion;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.dsol.planner.api.Goal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public abstract class SelfMotionActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			SelfMotion.initConcreteActions(getConcreteActions());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	//Used to manage the case when one activity that does not return a result is called
	private boolean waitingForActivity=false;
	@Override
	protected void onResume() {
		super.onResume();
		if(waitingForActivity && SelfMotion.isRunning()){
			waitingForActivity=false;
			SelfMotion.resume();			
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(SelfMotion.isRunning()){
			waitingForActivity=true; 
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		waitingForActivity=false; 
		if(SelfMotion.isManagedRequest(requestCode)){
			SelfMotion.callback(requestCode, resultCode, data);			
		}
	}
	
	public abstract List<Class> getConcreteActions();
	public abstract List<Goal> getGoal(View view);
	
	
}
