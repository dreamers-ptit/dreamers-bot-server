package com.dreamers.service;

public class DreamfulBotService implements Runnable {
	// Thread implements
	protected Thread thread;
	protected String threadName;
	protected boolean threadSuspended = false;
	protected long interval; //milliseconds
	
	public void suspend() {
		threadSuspended = true;
		System.out.println(threadName + " suspended");
	}
	
	public synchronized void resume() {
		threadSuspended = false;
		notify();
		System.out.println(threadName + " resumed");
	}
	
	public synchronized void stop() {
		thread = null;
		notify();
		System.out.println(threadName + " stoped");
	}
	
	public void start() {
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
			System.out.println(threadName + " started");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread thisThread = Thread.currentThread();
		while (thread == thisThread) {
			try {
				// ...
				
				Thread.sleep(interval);
				synchronized (this) {
					while (threadSuspended && thread == thisThread)
						wait();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
