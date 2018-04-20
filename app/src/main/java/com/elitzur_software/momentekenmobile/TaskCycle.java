package com.elitzur_software.momentekenmobile;

public interface TaskCycle {

	public void beforeTask();
	public void onTaskCompleted(boolean result, Object ob, int taskId);
	public void onProgress(int taskId, int prog);
	public boolean task(int taskId);
}
