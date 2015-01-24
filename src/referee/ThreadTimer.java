package referee;

import java.awt.event.ActionListener;

import javax.swing.Timer;

public class ThreadTimer extends Timer implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ThreadTimer(int arg0, ActionListener arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		start();
	}

}
