package com.ociweb.iot.project.lightblink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ociweb.iot.hardware.impl.test.TestHardware;
import com.ociweb.iot.maker.IOTDeviceRuntime;
import com.ociweb.pronghorn.stage.scheduling.NonThreadScheduler;

/**
 * Unit test for simple App.
 */
public class AppTest { 

	 @Test
	    public void testApp()
	    {
	    	IOTDeviceRuntime runtime = IOTDeviceRuntime.test(new IoTApp());
	    	    	
	    	NonThreadScheduler scheduler = (NonThreadScheduler)runtime.getScheduler();    	
	    
	    	scheduler.setSingleStepMode(true);
	    	scheduler.startup();

	    	TestHardware hardware = (TestHardware)runtime.getHardware();
	    
	    	int iterations = 10;
	    	
	    	int expected = 1;
	    	
	    	long lastTime = 0;
	    	while (iterations>0) {
	    		
	    		scheduler.run();
	    		
	    		long time = hardware.getLastTime(IoTApp.LED_CONNECTION);
	    		if (0!=time) {
	    			iterations--;
	    			assertEquals(expected, hardware.digitalRead(IoTApp.LED_CONNECTION));
	    			expected = 1&(expected+1);
	    			
	    			if (0!=lastTime) {
	    				long durationMs = (time-lastTime);
	    				//System.out.println(durationMs);
	    				assertTrue(durationMs>=500);
	    				assertTrue(durationMs<=750);
	    				
	    			}
	    			
	    			lastTime = time;
	    			hardware.clearCaputuredLastTimes();
	    			hardware.clearCaputuredHighs();
	    		}
		    	
	    		
	    	}
	    }
}
