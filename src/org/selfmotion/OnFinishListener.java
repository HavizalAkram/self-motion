package org.selfmotion;

import java.util.Map;

import org.dsol.planner.api.Goal;

public interface OnFinishListener {
	
	public void onSuccess(Goal goal, Map<String, Object> executionData);
	public void onError();

}
