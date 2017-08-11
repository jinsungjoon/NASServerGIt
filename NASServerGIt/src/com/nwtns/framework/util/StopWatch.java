package com.nwtns.framework.util;

public class StopWatch {
	long startTime;
	long elapsedTime=0;
	StringBuffer currentName;
	boolean threadFlag=false;
	
	public StopWatch() {
		currentName = new StringBuffer();
		startTime = System.nanoTime();
	}
	public StopWatch(boolean threadFlag) {
		changeMessage("",true,true);
	}
	public StopWatch(String message) {
		changeMessage(message,false,true);
	}
	public StopWatch(String message,boolean threadFlag) {
		changeMessage("",threadFlag,true);
	}
	
	public void start() {
		startTime = System.nanoTime();
		elapsedTime = 0;
	}
	public void stop() {
		elapsedTime=System.nanoTime() - startTime;
	}
	public void changeMessage(String message, boolean threadFlag, boolean resetFlag) {
		StringBuffer threadName = new StringBuffer();
		this.threadFlag = threadFlag;
		if(threadFlag) {
			threadName.append("ThreadName=").append(Thread.currentThread().getName());
		}
		currentName.append("[").append(message).append(threadName).append("] ");
		if(resetFlag) {
			start();
		}
	}
	public double getElapsedMS() {
		if(elapsedTime == 0) stop();
		return elapsedTime/1000000.0;
	}
	public double getElapsedNano() {
		if(elapsedTime == 0) stop();
		return elapsedTime;
	}
	public String toString() {
		if(elapsedTime == 0) stop();
		currentName.append("elapsed Time : ").append(elapsedTime/1000000.0).append("ms");
		return currentName.toString();
	}
	
}
