package com.ptplib.usbcamera;



/*	
 *  Copyright 2012 by Ashraf <code@awesomeash.com>
 * 
 *  This file is part of PTP Android Camera Control (PACC).
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or 
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */

/*

	Corrections:
	Response: GetEvent = 0x9116;
	Container: toString replace ParamVector by getU8(i) procedure, format using String.format("%02x",


	Firing shutter: add CloseSession Command and add Waiting loop until not busy

	add BroadcastReceiver

 */



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
///http://www.koders.com/info.aspx?c=ProjectInfo&pid=UCBHEX8BYMVVNMXBWVSEQ1BH8A
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

import com.ptplib.usbcamera.eos.EosEventConstants;
import com.ptplib.usbcamera.eos.EosInitiator;
import com.ptplib.usbcamera.nikon.NikonEventConstants;
import com.ptplib.usbcamera.nikon.NikonInitiator;
import com.strickling.usbcamera.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class USBCameraTest extends Activity { //implements Runnable {

	private USBCameraTest mainActivity = this;
	
	private static final String ACTION_USB_PERMISSION = "com.test.hello.USB_PERMISSION";
	public  static final String TAG = "TestRunActivity";

	public Thread thread;
	private Toast toast;
	private Timer eventTimer;

	public boolean liveViewTurnedOn = false;
	
	private UsbManager mUsbManager;
	//private UsbDeviceConnection mConnection;
	private BaselineInitiator bi;

	static final int 	MSG_SHOW_TV2 	= 2;
	static final int 	MSG_SHOW_TV3 	= 3;
	static final int 	MSG_SHOW_TV4 	= 4;
	static final int 	MSG_SHOW_TV5 	= 5;
	static final int 	MSG_SHOW_TV6 	= 6;
	static final String THREAD_SHUTTER 	= "THREAD_SHUTTER";
	static final String THREAD_TEST		= "THREAD_TEST";
	static final String THREAD_STARTBULB= "THREAD_STARTBULB";
	static final String THREAD_STOPBULB = "THREAD_STOPBULB";
	static final String THREAD_SETSHUTTER = "THREAD_SETSHUTTER";
	static final String THREAD_SETISO = "THREAD_SETISO";
	static final String THREAD_SETAPERTURE = "THREAD_SETAPERTURE";
	static final String THREAD_FOCUSFORWARD = "THREAD_FOCUSFORWARD";
	static final String THREAD_FOCUSBACKWARD = "THREAD_FOCUSBACKWARD";
	static final String THREAD_SETPICTURESTYLE = "THREAD_SETPICTURESTYLE";
	static final String THREAD_SETWHITEBALANCE = "THREAD_SETWHITEBALANCE";
	static final String THREAD_SETEXPOSURE = "THREAD_SETEXPOSURE";
	static final String THREAD_SETDRIVEMODE = "THREAD_SETDRIVEMODE";
	static final String THREAD_SETMETERING = "THREAD_SETMETERING";
	static final String THREAD_INITLIVEVIEW = "THREAD_INITLIVEVIEW";
	static final String THREAD_STARTLIVEVIEW = "THREAD_STARTLIVEVIEW";
	static final String THREAD_STOPLIVEVIEW = "THREAD_STOPLIVEVIEW";
	
	private NameFactory factory = new NameFactory();

	private int inMaxPS;
	Spinner shutter_spinner, iso_spinner, aperture_spinner, picturestyle_spinner, whitebalance_spinner, exposure_spinner, drivemode_spinner, metering_spinner;
	Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, ButtonMoveForward,ButtonMoveBackward;
	TextView tv1, tv2, tv3, tv4, tv5, tv6;
	int shutter_value = EosEventConstants.SHUTTER_SPEED_1_60;
	int iso_value = EosEventConstants.ISO_Auto;
	int aperture_value = EosEventConstants.APERTURE_F4_5;
	int picturestyle_value = EosEventConstants.PictureStyle_Standard;
	int whitebalance_value = EosEventConstants.AutoWhiteBalance;
	int exposure_value = EosEventConstants.EXPOSURE_0;
	int drivemode_value = EosEventConstants.driveSingleShot;
	int metering_value = EosEventConstants.CenterWeightedAverageMetering;
	ImageView liveViewHolder;
	Button button13, startLiveViewButton, endLiveViewButton;
	
	DevicePropDesc testInfo = null;
	
	public boolean isCanon = false, isNikon = false;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Log.d(TAG, "onCreate");
		mUsbManager = (UsbManager)getSystemService(Context.USB_SERVICE);

		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		button1 = (Button) findViewById (R.id.button1);
		button2 = (Button) findViewById (R.id.button2);
		button3 = (Button) findViewById (R.id.button3);
		button4 = (Button) findViewById (R.id.button4);
		button5 = (Button) findViewById (R.id.button5);
		button6 = (Button) findViewById (R.id.button6);
		button7 = (Button) findViewById (R.id.button7);
		button8 = (Button) findViewById (R.id.button8);
		button9 = (Button) findViewById (R.id.button9);
		button10 = (Button) findViewById (R.id.button10);
		button11 = (Button) findViewById (R.id.button11);
		button12 = (Button) findViewById (R.id.button12);
		button13 = (Button) findViewById (R.id.button13);
		startLiveViewButton = (Button) findViewById (R.id.startLiveViewButton);
		endLiveViewButton = (Button) findViewById (R.id.endLiveViewButton);
		
		liveViewHolder = (ImageView) findViewById (R.id.liveViewHolder);
		
		ButtonMoveForward = (Button) findViewById (R.id.ButtonMoveForward);
		ButtonMoveBackward = (Button) findViewById (R.id.ButtonMoveBackward);

		shutter_spinner = (Spinner) findViewById(R.id.shutter_spinner);
		ArrayAdapter<CharSequence> shutter_adapter = ArrayAdapter.createFromResource(this,
		        R.array.shutter_list, android.R.layout.simple_spinner_item);
		shutter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shutter_spinner.setAdapter(shutter_adapter);
		
		iso_spinner = (Spinner) findViewById(R.id.iso_spinner);
		ArrayAdapter<CharSequence> iso_adapter = ArrayAdapter.createFromResource(this,
		        R.array.iso_list, android.R.layout.simple_spinner_item);
		iso_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		iso_spinner.setAdapter(iso_adapter);
		
		aperture_spinner = (Spinner) findViewById(R.id.aperture_spinner);
		ArrayAdapter<CharSequence> aperture_adapter = ArrayAdapter.createFromResource(this,
		        R.array.aperture_list, android.R.layout.simple_spinner_item);
		aperture_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		aperture_spinner.setAdapter(aperture_adapter);
		
		picturestyle_spinner = (Spinner) findViewById(R.id.picturestyle_spinner);
		ArrayAdapter<CharSequence> picturestyle_adapter = ArrayAdapter.createFromResource(this,
		        R.array.picturestyle_list, android.R.layout.simple_spinner_item);
		picturestyle_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		picturestyle_spinner.setAdapter(picturestyle_adapter);
		
		whitebalance_spinner = (Spinner) findViewById(R.id.whitebalance_spinner);
		ArrayAdapter<CharSequence> whitebalance_adapter = ArrayAdapter.createFromResource(this,
		        R.array.whitebalance_list, android.R.layout.simple_spinner_item);
		whitebalance_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		whitebalance_spinner.setAdapter(whitebalance_adapter);

		exposure_spinner = (Spinner) findViewById(R.id.exposure_spinner);
		ArrayAdapter<CharSequence> exposure_adapter = ArrayAdapter.createFromResource(this,
		        R.array.exposure_list, android.R.layout.simple_spinner_item);
		exposure_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		exposure_spinner.setAdapter(exposure_adapter);

		drivemode_spinner = (Spinner) findViewById(R.id.drivemode_spinner);
		ArrayAdapter<CharSequence> drivemode_adapter = ArrayAdapter.createFromResource(this,
		        R.array.drivemode_list, android.R.layout.simple_spinner_item);
		drivemode_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		drivemode_spinner.setAdapter(drivemode_adapter);
		
		metering_spinner = (Spinner) findViewById(R.id.metering_spinner);
		ArrayAdapter<CharSequence> metering_adapter = ArrayAdapter.createFromResource(this,
		        R.array.metering_list, android.R.layout.simple_spinner_item);
		metering_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		metering_spinner.setAdapter(metering_adapter);
		
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
		filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
		registerReceiver(mUsbReceiver, filter);


		button1.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				clearTV();
				testInfo.showInTextView(tv3);
				//tv3.setText(testInfo.toString());
				//changeSpinnerToCanon();
				if (bi== null || bi.info == null){
					tv2.setText ("Error, not connected");
					initDevice (searchDevice());
					return;
				}
				try {
					tv2.setText ("Reset bi");
					bi.reset();
				} catch (PTPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	
		
		button2.setOnClickListener(new View.OnClickListener() {	// detailed Device info	
			@Override
			public void onClick(View v) {
				clearTV();
				//changeSpinnerToNikon();
				
				if (bi== null || bi.info == null){
					tv2.setText ("Error, not connected");
					return;
				}
				DeviceInfo	info = bi.info;
				if (info.vendorExtensionId != 0) {
					factory = factory.updateFactory (info.vendorExtensionId);
					info.factory = factory;
				}
				tv2.setText(bi.info.toString());
			}
		});



		button3.setOnClickListener(new View.OnClickListener() // Release Shutter
		{
			@Override
			public void onClick(View v) {
				clearTV();
				tv1.setText("Release shutter");
				if (bi == null) {
					tv1.setText("Error: no Device connected!");
					return;
				}
				tv2.setText("Starting Thread");					
				thread  = new Thread (r, THREAD_SHUTTER);
				thread.start();
			}
		}); 

		button4.setOnClickListener(new View.OnClickListener() // Select ISO  EOS_DPC_Iso
		{
			@Override
			public void onClick(View v) {
				//clearTV();
				tv1.setText("TestButton");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv2.setText("Starting thread.");
				thread  = new Thread (r, THREAD_TEST);
				thread.start();
			}
		}); 
		
		button5.setOnClickListener(new View.OnClickListener() // Select ISO  EOS_DPC_Iso
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String shutter_name;
				shutter_name = String.valueOf(shutter_spinner.getSelectedItem());
				
				if (shutter_name.equals("SHUTTER_SPEED_BULB"))
				{
					shutter_value = EosEventConstants.SHUTTER_SPEED_BULB;
					//Toast.makeText(USBCameraTest.this, String.valueOf(shutter_spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
				}
				
				if (shutter_name.equals("SHUTTER_SPEED_1_500"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_500;
				if (shutter_name.equals("SHUTTER_SPEED_30_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_30_SEC;			
				if (shutter_name.equals("SHUTTER_SPEED_25_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_25_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_20_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_20_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_15_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_15_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_13_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_13_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_10_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_10_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_8_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_8_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_6_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_6_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_5_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_5_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_4_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_4_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_3_2_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_3_2_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_2_5_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_2_5_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_2_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_2_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_1_6_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_6_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_1_3_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_3_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_1_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_0_8_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_0_8_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_0_6_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_0_6_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_0_5_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_0_5_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_0_4_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_0_4_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_0_3_SEC"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_0_3_SEC;
				if (shutter_name.equals("SHUTTER_SPEED_1_4"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_4;
				if (shutter_name.equals("SHUTTER_SPEED_1_5"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_5;
				if (shutter_name.equals("SHUTTER_SPEED_1_6"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_6;
				if (shutter_name.equals("SHUTTER_SPEED_1_8"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_8;
				if (shutter_name.equals("SHUTTER_SPEED_1_10"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_10;
				if (shutter_name.equals("SHUTTER_SPEED_1_13"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_13;
				if (shutter_name.equals("SHUTTER_SPEED_1_15"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_15;
				if (shutter_name.equals("SHUTTER_SPEED_1_20"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_20;
				if (shutter_name.equals("SHUTTER_SPEED_1_25"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_25;
				if (shutter_name.equals("SHUTTER_SPEED_1_30"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_30;
				if (shutter_name.equals("SHUTTER_SPEED_1_40"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_40;
				if (shutter_name.equals("SHUTTER_SPEED_1_50"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_50;
				if (shutter_name.equals("SHUTTER_SPEED_1_60"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_60;
				if (shutter_name.equals("SHUTTER_SPEED_1_80"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_80;
				if (shutter_name.equals("SHUTTER_SPEED_1_100"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_100;
				if (shutter_name.equals("SHUTTER_SPEED_1_125"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_125;
				if (shutter_name.equals("SHUTTER_SPEED_1_160"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_160;
				if (shutter_name.equals("SHUTTER_SPEED_1_200"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_200;
				if (shutter_name.equals("SHUTTER_SPEED_1_250"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_250;
				if (shutter_name.equals("SHUTTER_SPEED_1_320"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_320;
				if (shutter_name.equals("SHUTTER_SPEED_1_400"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_400;
				if (shutter_name.equals("SHUTTER_SPEED_1_500"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_500;
				if (shutter_name.equals("SHUTTER_SPEED_1_640"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_640;
				if (shutter_name.equals("SHUTTER_SPEED_1_800"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_800;
				if (shutter_name.equals("SHUTTER_SPEED_1_1000"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_1000;
				if (shutter_name.equals("SHUTTER_SPEED_1_1250"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_1250;
				if (shutter_name.equals("SHUTTER_SPEED_1_1600"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_1600;
				if (shutter_name.equals("SHUTTER_SPEED_1_2000"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_2000;
				if (shutter_name.equals("SHUTTER_SPEED_1_2500"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_2500;
				if (shutter_name.equals("SHUTTER_SPEED_1_3200"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_3200;
				if (shutter_name.equals("SHUTTER_SPEED_1_4000"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_4000;
				if (shutter_name.equals("SHUTTER_SPEED_1_5000"))
					shutter_value = EosEventConstants.SHUTTER_SPEED_1_5000;

				if (shutter_name.equals("NK_ShutterSpeed_4000"))
					shutter_value = NikonEventConstants.NK_ShutterSpeed_4000;
				if (shutter_name.equals("NK_ShutterSpeed_3200"))
					shutter_value = NikonEventConstants.NK_ShutterSpeed_3200;
				if (shutter_name.equals("NK_ShutterSpeed_2500"))
					shutter_value = NikonEventConstants.NK_ShutterSpeed_2500;
				if (shutter_name.equals("NK_ShutterSpeed_2000")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_2000;
				if (shutter_name.equals("NK_ShutterSpeed_1600"))
					shutter_value = NikonEventConstants.NK_ShutterSpeed_1600;
				if (shutter_name.equals("NK_ShutterSpeed_1250")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_1250;
				if (shutter_name.equals("NK_ShutterSpeed_1000")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_1000;
				if (shutter_name.equals("NK_ShutterSpeed_800")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_800;
				if (shutter_name.equals("NK_ShutterSpeed_640")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_640;
				if (shutter_name.equals("NK_ShutterSpeed_500")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_500;
				if (shutter_name.equals("NK_ShutterSpeed_400")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_400;
				if (shutter_name.equals("NK_ShutterSpeed_320")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_320;
				if (shutter_name.equals("NK_ShutterSpeed_250")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_250;
				if (shutter_name.equals("NK_ShutterSpeed_200")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_200;
				if (shutter_name.equals("NK_ShutterSpeed_160")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_160;
				if (shutter_name.equals("NK_ShutterSpeed_125")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_125;
				if (shutter_name.equals("NK_ShutterSpeed_100")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_100;
				if (shutter_name.equals("NK_ShutterSpeed_80")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_80;
				if (shutter_name.equals("NK_ShutterSpeed_60")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_60;
				if (shutter_name.equals("NK_ShutterSpeed_50")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_50;
				if (shutter_name.equals("NK_ShutterSpeed_40")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_40;
				if (shutter_name.equals("NK_ShutterSpeed_30")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_30;
				if (shutter_name.equals("NK_ShutterSpeed_25")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_25;
				if (shutter_name.equals("NK_ShutterSpeed_20")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_20;
				if (shutter_name.equals("NK_ShutterSpeed_15")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_15;
				if (shutter_name.equals("NK_ShutterSpeed_13")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_13;
				if (shutter_name.equals("NK_ShutterSpeed_10")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_10;
				if (shutter_name.equals("NK_ShutterSpeed_8")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_8;
				if (shutter_name.equals("NK_ShutterSpeed_6")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_6;
				if (shutter_name.equals("NK_ShutterSpeed_5")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_5;
				if (shutter_name.equals("NK_ShutterSpeed_4")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_4;
				if (shutter_name.equals("NK_ShutterSpeed_3")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_3;
				if (shutter_name.equals("NK_ShutterSpeed_2_5")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_2_5;
				if (shutter_name.equals("NK_ShutterSpeed_2")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_2;
				if (shutter_name.equals("NK_ShutterSpeed_1_6")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_1_6;
				if (shutter_name.equals("NK_ShutterSpeed_1_3")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_1_3;
				if (shutter_name.equals("NK_ShutterSpeed_1_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_1_sec;
				if (shutter_name.equals("NK_ShutterSpeed_1_3_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_1_3_sec;
				if (shutter_name.equals("NK_ShutterSpeed_1_6_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_1_6_sec;
				if (shutter_name.equals("NK_ShutterSpeed_2_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_2_sec;
				if (shutter_name.equals("NK_ShutterSpeed_2_5_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_2_5_sec;
				if (shutter_name.equals("NK_ShutterSpeed_3_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_3_sec;
				if (shutter_name.equals("NK_ShutterSpeed_4_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_4_sec;
				if (shutter_name.equals("NK_ShutterSpeed_5_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_5_sec;
				if (shutter_name.equals("NK_ShutterSpeed_6_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_6_sec;
				if (shutter_name.equals("NK_ShutterSpeed_8_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_8_sec;
				if (shutter_name.equals("NK_ShutterSpeed_10_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_10_sec;
				if (shutter_name.equals("NK_ShutterSpeed_13_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_13_sec;
				if (shutter_name.equals("NK_ShutterSpeed_15_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_15_sec;
				if (shutter_name.equals("NK_ShutterSpeed_20_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_20_sec;
				if (shutter_name.equals("NK_ShutterSpeed_25_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_25_sec;
				if (shutter_name.equals("NK_ShutterSpeed_30_sec")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_30_sec;
				if (shutter_name.equals("NK_ShutterSpeed_Bulb")) 
					shutter_value = NikonEventConstants.NK_ShutterSpeed_Bulb;
				
				tv3.setText("Set Exposure");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_SETSHUTTER);
				thread.start();
				
			}
		}); 

	
		
		button6.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String iso_name;
				iso_name = String.valueOf(iso_spinner.getSelectedItem());
				
				if (iso_name.equals("ISO_Auto"))
					iso_value = EosEventConstants.ISO_Auto;
				if (iso_name.equals("ISO_50"))
					iso_value = EosEventConstants.ISO_50;
				if (iso_name.equals("ISO_100"))
					iso_value = EosEventConstants.ISO_100;
				if (iso_name.equals("ISO_125"))
					iso_value = EosEventConstants.ISO_125;
				if (iso_name.equals("ISO_160"))
					iso_value = EosEventConstants.ISO_160;
				if (iso_name.equals("ISO_200"))
					iso_value = EosEventConstants.ISO_200;
				if (iso_name.equals("ISO_250"))
					iso_value = EosEventConstants.ISO_250;
				if (iso_name.equals("ISO_320"))
					iso_value = EosEventConstants.ISO_320;
				if (iso_name.equals("ISO_400"))
					iso_value = EosEventConstants.ISO_400;
				if (iso_name.equals("ISO_500"))
					iso_value = EosEventConstants.ISO_500;
				if (iso_name.equals("ISO_640"))
					iso_value = EosEventConstants.ISO_640;
				if (iso_name.equals("ISO_800"))
					iso_value = EosEventConstants.ISO_800;
				if (iso_name.equals("ISO_1000"))
					iso_value = EosEventConstants.ISO_1000;
				if (iso_name.equals("ISO_1250"))
					iso_value = EosEventConstants.ISO_1250;
				if (iso_name.equals("ISO_1600"))
					iso_value = EosEventConstants.ISO_1600;
				if (iso_name.equals("ISO_3200"))
					iso_value = EosEventConstants.ISO_3200;				
				if (iso_name.equals("NK_ISO_100"))
					iso_value = NikonEventConstants.NK_ISO_100;
				if (iso_name.equals("NK_ISO_125"))
					iso_value = NikonEventConstants.NK_ISO_125;
				if (iso_name.equals("NK_ISO_160"))
					iso_value = NikonEventConstants.NK_ISO_160;
				if (iso_name.equals("NK_ISO_200"))
					iso_value = NikonEventConstants.NK_ISO_200;
				if (iso_name.equals("NK_ISO_250"))
					iso_value = NikonEventConstants.NK_ISO_250;
				if (iso_name.equals("NK_ISO_320"))
					iso_value = NikonEventConstants.NK_ISO_320;
				if (iso_name.equals("NK_ISO_400"))
					iso_value = NikonEventConstants.NK_ISO_400;
				if (iso_name.equals("NK_ISO_500"))
					iso_value = NikonEventConstants.NK_ISO_500;
				if (iso_name.equals("NK_ISO_640"))
					iso_value = NikonEventConstants.NK_ISO_640;
				if (iso_name.equals("NK_ISO_800"))
					iso_value = NikonEventConstants.NK_ISO_800;
				if (iso_name.equals("NK_ISO_1000"))
					iso_value = NikonEventConstants.NK_ISO_1000;
				if (iso_name.equals("NK_ISO_1250"))
					iso_value = NikonEventConstants.NK_ISO_1250;
				if (iso_name.equals("NK_ISO_1600"))
					iso_value = NikonEventConstants.NK_ISO_1600;
				if (iso_name.equals("NK_ISO_2000"))
					iso_value = NikonEventConstants.NK_ISO_2000;
				if (iso_name.equals("NK_ISO_2500"))
					iso_value = NikonEventConstants.NK_ISO_2500;
				if (iso_name.equals("NK_ISO_3200"))
					iso_value = NikonEventConstants.NK_ISO_3200;
				if (iso_name.equals("NK_ISO_4000"))
					iso_value = NikonEventConstants.NK_ISO_4000;
				if (iso_name.equals("NK_ISO_5000"))
					iso_value = NikonEventConstants.NK_ISO_5000;
				if (iso_name.equals("NK_ISO_6400"))
					iso_value = NikonEventConstants.NK_ISO_6400;
				if (iso_name.equals("NK_ISO_Hi03"))
					iso_value = NikonEventConstants.NK_ISO_Hi03;
				if (iso_name.equals("NK_ISO_Hi07"))
					iso_value = NikonEventConstants.NK_ISO_Hi07;
				if (iso_name.equals("NK_ISO_Hi_1"))
					iso_value = NikonEventConstants.NK_ISO_Hi_1;
				if (iso_name.equals("NK_ISO_Hi_2"))
					iso_value = NikonEventConstants.NK_ISO_Hi_2;
				
				tv3.setText("Set ISO");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_SETISO);
				thread.start();
				
			}
		}); 

		button7.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String aperture_name;
				aperture_name = String.valueOf(aperture_spinner.getSelectedItem());
				
				if (aperture_name.equals("APERTURE_F1_2"))
					aperture_value = EosEventConstants.APERTURE_F1_2;
				if (aperture_name.equals("APERTURE_F1_4"))
					aperture_value = EosEventConstants.APERTURE_F1_4;
				if (aperture_name.equals("APERTURE_F1_6"))
					aperture_value = EosEventConstants.APERTURE_F1_6;
				if (aperture_name.equals("APERTURE_F1_8"))
					aperture_value = EosEventConstants.APERTURE_F1_8;
				if (aperture_name.equals("APERTURE_F2_0"))
					aperture_value = EosEventConstants.APERTURE_F2_0;
				if (aperture_name.equals("APERTURE_F2_2"))
					aperture_value = EosEventConstants.APERTURE_F2_2;
				if (aperture_name.equals("APERTURE_F2_5"))
					aperture_value = EosEventConstants.APERTURE_F2_5;
				if (aperture_name.equals("APERTURE_F2_8"))
					aperture_value = EosEventConstants.APERTURE_F2_8;
				if (aperture_name.equals("APERTURE_F3_2"))
					aperture_value = EosEventConstants.APERTURE_F3_2;
				if (aperture_name.equals("APERTURE_F3_5"))
					aperture_value = EosEventConstants.APERTURE_F3_5;
				if (aperture_name.equals("APERTURE_F4_0"))
					aperture_value = EosEventConstants.APERTURE_F4_0;
				if (aperture_name.equals("APERTURE_F4_5"))
					aperture_value = EosEventConstants.APERTURE_F4_5;
				if (aperture_name.equals("APERTURE_F5_0"))
					aperture_value = EosEventConstants.APERTURE_F5_0;
				if (aperture_name.equals("APERTURE_F5_6"))
					aperture_value = EosEventConstants.APERTURE_F5_6;
				if (aperture_name.equals("APERTURE_F6_3"))
					aperture_value = EosEventConstants.APERTURE_F6_3;
				if (aperture_name.equals("APERTURE_F7_1"))
					aperture_value = EosEventConstants.APERTURE_F7_1;
				if (aperture_name.equals("APERTURE_F8"))
					aperture_value = EosEventConstants.APERTURE_F8;
				if (aperture_name.equals("APERTURE_F9"))
					aperture_value = EosEventConstants.APERTURE_F9;
				if (aperture_name.equals("APERTURE_F10"))
					aperture_value = EosEventConstants.APERTURE_F10;
				if (aperture_name.equals("APERTURE_F11"))
					aperture_value = EosEventConstants.APERTURE_F11;
				if (aperture_name.equals("APERTURE_F13"))
					aperture_value = EosEventConstants.APERTURE_F13;
				if (aperture_name.equals("APERTURE_F14"))
					aperture_value = EosEventConstants.APERTURE_F14;
				if (aperture_name.equals("APERTURE_F16"))
					aperture_value = EosEventConstants.APERTURE_F16;
				if (aperture_name.equals("APERTURE_F18"))
					aperture_value = EosEventConstants.APERTURE_F18;
				if (aperture_name.equals("APERTURE_F20"))
					aperture_value = EosEventConstants.APERTURE_F20;
				if (aperture_name.equals("APERTURE_F22"))
					aperture_value = EosEventConstants.APERTURE_F22;
				if (aperture_name.equals("APERTURE_F25"))
					aperture_value = EosEventConstants.APERTURE_F25;
				if (aperture_name.equals("APERTURE_F29"))
					aperture_value = EosEventConstants.APERTURE_F29;
				if (aperture_name.equals("APERTURE_F32"))
					aperture_value = EosEventConstants.APERTURE_F32;
				if (aperture_name.equals("NK_Aperture_3_50"))
					aperture_value = NikonEventConstants.NK_Aperture_3_50;
				if (aperture_name.equals("NK_Aperture_4_00"))
					aperture_value = NikonEventConstants.NK_Aperture_4_00;
				if (aperture_name.equals("NK_Aperture_4_50"))
					aperture_value = NikonEventConstants.NK_Aperture_4_50;
				if (aperture_name.equals("NK_Aperture_4_80"))
					aperture_value = NikonEventConstants.NK_Aperture_4_80;
				if (aperture_name.equals("NK_Aperture_5"))
					aperture_value = NikonEventConstants.NK_Aperture_5;
				if (aperture_name.equals("NK_Aperture_5_60"))
					aperture_value = NikonEventConstants.NK_Aperture_5_60;
				if (aperture_name.equals("NK_Aperture_6_30"))
					aperture_value = NikonEventConstants.NK_Aperture_6_30;
				if (aperture_name.equals("NK_Aperture_7_10"))
					aperture_value = NikonEventConstants.NK_Aperture_7_10;
				if (aperture_name.equals("NK_Aperture_8"))
					aperture_value = NikonEventConstants.NK_Aperture_8;
				if (aperture_name.equals("NK_Aperture_9"))
					aperture_value = NikonEventConstants.NK_Aperture_9;
				if (aperture_name.equals("NK_Aperture_10"))
					aperture_value = NikonEventConstants.NK_Aperture_10;
				if (aperture_name.equals("NK_Aperture_11"))
					aperture_value = NikonEventConstants.NK_Aperture_11;	
				if (aperture_name.equals("NK_Aperture_13"))	
					aperture_value = NikonEventConstants.NK_Aperture_13;
				if (aperture_name.equals("NK_Aperture_14"))
					aperture_value = NikonEventConstants.NK_Aperture_14;	
				if (aperture_name.equals("NK_Aperture_16"))
					aperture_value = NikonEventConstants.NK_Aperture_16;	
				if (aperture_name.equals("NK_Aperture_18"))
					aperture_value = NikonEventConstants.NK_Aperture_18;	
				if (aperture_name.equals("NK_Aperture_20"))
					aperture_value = NikonEventConstants.NK_Aperture_20;
				if (aperture_name.equals("NK_Aperture_22"))
					aperture_value = NikonEventConstants.NK_Aperture_22;	
				if (aperture_name.equals("NK_Aperture_25"))
					aperture_value = NikonEventConstants.NK_Aperture_25;	
				if (aperture_name.equals("NK_Aperture_29"))
					aperture_value = NikonEventConstants.NK_Aperture_29;	
				if (aperture_name.equals("NK_Aperture_32"))
					aperture_value = NikonEventConstants.NK_Aperture_32;
				if (aperture_name.equals("NK_Aperture_36"))
					aperture_value = NikonEventConstants.NK_Aperture_36;

				
				tv3.setText("Set Aperture");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_SETAPERTURE);
				thread.start();
				
			}
		}); 
		
		ButtonMoveBackward.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();

				tv3.setText("Set ButtonMoveBackward");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_FOCUSBACKWARD);
				thread.start();
				
			}
		}); 
		
		ButtonMoveForward.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String aperture_name;
				
				tv3.setText("Set ButtonMoveForward");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_FOCUSFORWARD);
				thread.start();
				
			}
		});
		
		
		button8.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String picturestyle_name;
				picturestyle_name = String.valueOf(picturestyle_spinner.getSelectedItem());
				
				if (picturestyle_name.equals("PictureStyle_Standard"))
					picturestyle_value = EosEventConstants.PictureStyle_Standard;
				if (picturestyle_name.equals("PictureStyle_Portrait"))
					picturestyle_value = EosEventConstants.PictureStyle_Portrait;
				if (picturestyle_name.equals("PictureStyle_Landscape"))
					picturestyle_value = EosEventConstants.PictureStyle_Landscape;
				if (picturestyle_name.equals("PictureStyle_Neutral"))
					picturestyle_value = EosEventConstants.PictureStyle_Neutral;
				if (picturestyle_name.equals("PictureStyle_Faithful"))
					picturestyle_value = EosEventConstants.PictureStyle_Faithful;
				if (picturestyle_name.equals("PictureStyle_Monochrome"))
					picturestyle_value = EosEventConstants.PictureStyle_Monochrome;
				if (picturestyle_name.equals("PictureStyle_User1"))
					picturestyle_value = EosEventConstants.PictureStyle_User1;
				if (picturestyle_name.equals("PictureStyle_User2"))
					picturestyle_value = EosEventConstants.PictureStyle_User2;
				if (picturestyle_name.equals("PictureStyle_User3"))
					picturestyle_value = EosEventConstants.PictureStyle_User3;				

				
				tv3.setText("Set Picture style");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_SETPICTURESTYLE);
				thread.start();
				
			}
		}); 
		
		button9.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String whitebalance_name;
				whitebalance_name = String.valueOf(whitebalance_spinner.getSelectedItem());
				
				if (whitebalance_name.equals("AutoWhiteBalance"))
					whitebalance_value = EosEventConstants.AutoWhiteBalance;
				if (whitebalance_name.equals("Daylight"))
					whitebalance_value = EosEventConstants.Daylight;
				if (whitebalance_name.equals("Clouds"))
					whitebalance_value = EosEventConstants.Clouds;
				if (whitebalance_name.equals("Tungsteen"))
					whitebalance_value = EosEventConstants.Tungsteen;
				if (whitebalance_name.equals("Fluoriscent"))
					whitebalance_value = EosEventConstants.Fluoriscent;
				if (whitebalance_name.equals("Strobe"))
					whitebalance_value = EosEventConstants.Strobe;
				if (whitebalance_name.equals("WhitePaper"))
					whitebalance_value = EosEventConstants.WhitePaper;
				if (whitebalance_name.equals("Shade"))
					whitebalance_value = EosEventConstants.Shade;
				if (whitebalance_name.equals("NK_WhiteBalance_Undefined")) 
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Undefined;
				if (whitebalance_name.equals("NK_WhiteBalance_Manual"))  
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Manual;
				if (whitebalance_name.equals("NK_WhiteBalance_Automatic")) 
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Automatic; 
				if (whitebalance_name.equals("NK_WhiteBalance_One_push_Automatic")) 
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_One_push_Automatic; 
				if (whitebalance_name.equals("NK_WhiteBalance_Daylight")) 
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Daylight; 
				if (whitebalance_name.equals("NK_WhiteBalance_Fluorescent"))  
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Fluorescent;
				if (whitebalance_name.equals("NK_WhiteBalance_Tungsten"))  
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Tungsten;
				if (whitebalance_name.equals("NK_WhiteBalance_Flash_Strobe"))  
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Flash_Strobe;
				if (whitebalance_name.equals("NK_WhiteBalance_Clouds"))  
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Clouds;
				if (whitebalance_name.equals("NK_WhiteBalance_Shade"))    
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Shade;      
				if (whitebalance_name.equals("NK_WhiteBalance_Preset"))   
					whitebalance_value = NikonEventConstants.NK_WhiteBalance_Preset;
				
				tv3.setText("Set White Balance style");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_SETWHITEBALANCE);
				thread.start();
				
			}
		}); 	
		button10.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String exposure_name;
				exposure_name = String.valueOf(exposure_spinner.getSelectedItem());
				
				if (exposure_name.equals("EXPOSURE_P_5_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_P_5_0d0;
				if (exposure_name.equals("EXPOSURE_P_4_2d30"))
					exposure_value = EosEventConstants.EXPOSURE_P_4_2d30;
				if (exposure_name.equals("EXPOSURE_P_4_1d30"))
					exposure_value = EosEventConstants.EXPOSURE_P_4_1d30;
				if (exposure_name.equals("EXPOSURE_P_4_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_P_4_0d0;
				if (exposure_name.equals("EXPOSURE_P_3_2d30"))
					exposure_value = EosEventConstants.EXPOSURE_P_3_2d30;
				if (exposure_name.equals("EXPOSURE_P_3_1d30"))
					exposure_value = EosEventConstants.EXPOSURE_P_3_1d30;
				if (exposure_name.equals("EXPOSURE_P_3_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_P_3_0d0;
				if (exposure_name.equals("EXPOSURE_P_2_2d30"))
					exposure_value = EosEventConstants.EXPOSURE_P_2_2d30;
				if (exposure_name.equals("EXPOSURE_P_2_1d20"))
					exposure_value = EosEventConstants.EXPOSURE_P_2_1d20;
				if (exposure_name.equals("EXPOSURE_P_2_1d30"))
					exposure_value = EosEventConstants.EXPOSURE_P_2_1d30;
				if (exposure_name.equals("EXPOSURE_P_2_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_P_2_0d0;
				if (exposure_name.equals("EXPOSURE_P_1_2d30"))
					exposure_value = EosEventConstants.EXPOSURE_P_1_2d30;
				if (exposure_name.equals("EXPOSURE_P_1_1d20"))
					exposure_value = EosEventConstants.EXPOSURE_P_1_1d20;
				if (exposure_name.equals("EXPOSURE_P_1_1d30"))
					exposure_value = EosEventConstants.EXPOSURE_P_1_1d30;
				if (exposure_name.equals("EXPOSURE_P_1_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_P_1_0d0;
				if (exposure_name.equals("EXPOSURE_P_0_2d3"))
					exposure_value = EosEventConstants.EXPOSURE_P_0_2d3;
				if (exposure_name.equals("EXPOSURE_P_0_1d2"))
					exposure_value = EosEventConstants.EXPOSURE_P_0_1d2;
				if (exposure_name.equals("EXPOSURE_P_0_1d3"))
					exposure_value = EosEventConstants.EXPOSURE_P_0_1d3;
				if (exposure_name.equals("EXPOSURE_0"))
					exposure_value = EosEventConstants.EXPOSURE_0;
				if (exposure_name.equals("EXPOSURE_N_0_1d3"))
					exposure_value = EosEventConstants.EXPOSURE_N_0_1d3;
				if (exposure_name.equals("EXPOSURE_N_0_1d2"))
					exposure_value = EosEventConstants.EXPOSURE_N_0_1d2;
				if (exposure_name.equals("EXPOSURE_N_0_2d3"))
					exposure_value = EosEventConstants.EXPOSURE_N_0_2d3;
				if (exposure_name.equals("EXPOSURE_N_1_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_N_1_0d0;
				if (exposure_name.equals("EXPOSURE_N_1_1d30"))
					exposure_value = EosEventConstants.EXPOSURE_N_1_1d30;
				if (exposure_name.equals("EXPOSURE_N_1_1d20"))
					exposure_value = EosEventConstants.EXPOSURE_N_1_1d20;
				if (exposure_name.equals("EXPOSURE_N_1_2d30"))
					exposure_value = EosEventConstants.EXPOSURE_N_1_2d30;
				if (exposure_name.equals("EXPOSURE_N_2_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_N_2_0d0;
				if (exposure_name.equals("EXPOSURE_N_2_1d30"))
					exposure_value = EosEventConstants.EXPOSURE_N_2_1d30;
				if (exposure_name.equals("EXPOSURE_N_2_1d20"))
					exposure_value = EosEventConstants.EXPOSURE_N_2_1d20;
				if (exposure_name.equals("EXPOSURE_N_2_2d30"))
					exposure_value = EosEventConstants.EXPOSURE_N_2_2d30;
				if (exposure_name.equals("EXPOSURE_N_3_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_N_3_0d0;
				if (exposure_name.equals("EXPOSURE_N_3_1d30"))
					exposure_value = EosEventConstants.EXPOSURE_N_3_1d30;
				if (exposure_name.equals("EXPOSURE_N_3_2d30"))
					exposure_value = EosEventConstants.EXPOSURE_N_3_2d30;
				if (exposure_name.equals("EXPOSURE_N_4_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_N_4_0d0;
				if (exposure_name.equals("EXPOSURE_N_4_1d30"))
					exposure_value = EosEventConstants.EXPOSURE_N_4_1d30;
				if (exposure_name.equals("EXPOSURE_N_4_2d30"))
					exposure_value = EosEventConstants.EXPOSURE_N_4_2d30;
				if (exposure_name.equals("EXPOSURE_N_5_0d0"))
					exposure_value = EosEventConstants.EXPOSURE_N_5_0d0;				
				if (exposure_name.equals("NK_Exposure_N_5_0"))
					exposure_value = NikonEventConstants.NK_Exposure_N_5_0;
				if (exposure_name.equals("NK_Exposure_N_4_7"))	
					exposure_value = NikonEventConstants.NK_Exposure_N_4_7;
				if (exposure_name.equals("NK_Exposure_N_4_3"))				
					exposure_value = NikonEventConstants.NK_Exposure_N_4_3;
				if (exposure_name.equals("NK_Exposure_N_4_0"))
					exposure_value = NikonEventConstants.NK_Exposure_N_4_0;
				if (exposure_name.equals("NK_Exposure_N_3_7"))
					exposure_value = NikonEventConstants.NK_Exposure_N_3_7;
				if (exposure_name.equals("NK_Exposure_N_3_3"))
					exposure_value = NikonEventConstants.NK_Exposure_N_3_3;
				if (exposure_name.equals("NK_Exposure_N_3_0"))
					exposure_value = NikonEventConstants.NK_Exposure_N_3_0;
				if (exposure_name.equals("NK_Exposure_N_2_7"))
					exposure_value = NikonEventConstants.NK_Exposure_N_2_7;
				if (exposure_name.equals("NK_Exposure_N_2_3"))
					exposure_value = NikonEventConstants.NK_Exposure_N_2_3;
				if (exposure_name.equals("NK_Exposure_N_2_0"))
					exposure_value = NikonEventConstants.NK_Exposure_N_2_0;
				if (exposure_name.equals("NK_Exposure_N_1_7"))				
					exposure_value = NikonEventConstants.NK_Exposure_N_1_7;
				if (exposure_name.equals("NK_Exposure_N_1_3"))			
					exposure_value = NikonEventConstants.NK_Exposure_N_1_3;
				if (exposure_name.equals("NK_Exposure_N_1_0"))
					exposure_value = NikonEventConstants.NK_Exposure_N_1_0;
				if (exposure_name.equals("NK_Exposure_N_0_7"))
					exposure_value = NikonEventConstants.NK_Exposure_N_0_7;
				if (exposure_name.equals("NK_Exposure_N_0_3"))				
					exposure_value = NikonEventConstants.NK_Exposure_N_0_3;
				if (exposure_name.equals("NK_Exposure_0")) 
					exposure_value = NikonEventConstants.NK_Exposure_0;
				if (exposure_name.equals("NK_Exposure_P_0_3"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_0_3;
				if (exposure_name.equals("NK_Exposure_P_0_7"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_0_7;
				if (exposure_name.equals("NK_Exposure_P_1_0"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_1_0;
				if (exposure_name.equals("NK_Exposure_P_1_3"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_1_3;
				if (exposure_name.equals("NK_Exposure_P_1_7"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_1_7;
				if (exposure_name.equals("NK_Exposure_P_2_0"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_2_0;
				if (exposure_name.equals("NK_Exposure_P_2_3"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_2_3;
				if (exposure_name.equals("NK_Exposure_P_2_7"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_2_7;
				if (exposure_name.equals("NK_Exposure_P_3_0"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_3_0;
				if (exposure_name.equals("NK_Exposure_P_3_3"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_3_3;
				if (exposure_name.equals("NK_Exposure_P_3_7"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_3_7;
				if (exposure_name.equals("NK_Exposure_P_4_0"))			
					exposure_value = NikonEventConstants.NK_Exposure_P_4_0;
				if (exposure_name.equals("NK_Exposure_P_4_3"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_4_3;
				if (exposure_name.equals("NK_Exposure_P_4_7"))				
					exposure_value = NikonEventConstants.NK_Exposure_P_4_7;
				if (exposure_name.equals("NK_Exposure_P_5_0"))
					exposure_value = NikonEventConstants.NK_Exposure_P_5_0;
				
				tv3.setText("Set Exposure");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_SETEXPOSURE);
				thread.start();
				
			}
		});	
		
		button11.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String drivemode_name;
				drivemode_name = String.valueOf(drivemode_spinner.getSelectedItem());
				
				if (drivemode_name.equals("driveSingleShot"))
					drivemode_value = EosEventConstants.driveSingleShot;
				if (drivemode_name.equals("driveContinuous"))
					drivemode_value = EosEventConstants.driveContinuous;
				if (drivemode_name.equals("driveContinuousHighSpeed"))
					drivemode_value = EosEventConstants.driveContinuousHighSpeed;
				if (drivemode_name.equals("driveContinuousLowSpeed"))
					drivemode_value = EosEventConstants.driveContinuousLowSpeed;
				if (drivemode_name.equals("driveTimer10Sec"))
					drivemode_value = EosEventConstants.driveTimer10Sec;
				if (drivemode_name.equals("driveTimer2Sec"))
					drivemode_value = EosEventConstants.driveTimer2Sec;
				if (drivemode_name.equals("driveSingleSilent"))
					drivemode_value = EosEventConstants.driveSingleSilent;
				if (drivemode_name.equals("driveContinuousSilent"))
					drivemode_value = EosEventConstants.driveContinuousSilent;
				
				tv3.setText("Set Drive Mode");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_SETDRIVEMODE);
				thread.start();
				
			}
		}); 
		button12.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				clearTV();
				String metering_name;
				metering_name = String.valueOf(metering_spinner.getSelectedItem());
				
				if (metering_name.equals("SpotMetering"))
					metering_value = EosEventConstants.SpotMetering;
				if (metering_name.equals("EvaluativeMetering"))
					metering_value = EosEventConstants.EvaluativeMetering;
				if (metering_name.equals("PartialMetering"))
					metering_value = EosEventConstants.PartialMetering;
				if (metering_name.equals("CenterWeightedAverageMetering"))
					metering_value = EosEventConstants.CenterWeightedAverageMetering;
				
				tv3.setText("Set Metering");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				tv4.setText("Starting thread.");
				thread  = new Thread (r, THREAD_SETMETERING);
				thread.start();
				
			}
		});		
		
		button13.setOnClickListener(new View.OnClickListener() // Select ISO  EOS_DPC_Iso
		{
			@Override
			public void onClick(View v) {
				//clearTV();
				tv1.setText("Init LiveView");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				thread  = new Thread (r, THREAD_INITLIVEVIEW);
				thread.start();
			}
		});
		
		startLiveViewButton.setOnClickListener(new View.OnClickListener() // Select ISO  EOS_DPC_Iso
		{
			@Override
			public void onClick(View v) {
				//clearTV();
				tv1.setText("Start LiveView");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				thread  = new Thread (r, THREAD_STARTLIVEVIEW);
				thread.start();
			}
		});
		
		endLiveViewButton.setOnClickListener(new View.OnClickListener() // Select ISO  EOS_DPC_Iso
		{
			@Override
			public void onClick(View v) {
				//clearTV();
				tv1.setText("Stop LiveView");
				if (bi == null) {
					tv1.setText("Error: bi = null!");
					return;
				}
				thread  = new Thread (r, THREAD_STOPLIVEVIEW);
				thread.start();
			}
		});
		
	////End
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		initDevice (searchDevice ());
	}

	// search connected devices, returns only protocol 0 devices
	public UsbDevice searchDevice () {
		tv1.setText("Search Device");
		UsbDevice device = null;
		for (UsbDevice lDevice :  mUsbManager.getDeviceList().values()) {
//			Log.d(TAG, "Device: " +lDevice.getDeviceName() +" class " +lDevice.getDeviceClass());
			if (lDevice.getDeviceProtocol() == 0) device = lDevice;	  
			tv1.setText("Found Device: " +device.getDeviceName());
		}
		if (device == null) tv1.setText("No device found!");
		return device;
	}


	// inits device, 
	public void initDevice (UsbDevice device) {
		if (device != null){
			Log.d(TAG, "initDevice: "+device.getDeviceName());
			//	        	log ("Device: " +device.getDeviceName());
			try {				
				//Old code bi =  new EosInitiator (device, mUsbManager.openDevice(device));	
				bi = new BaselineInitiator (device, mUsbManager.openDevice(device));
				// Select appropriate deviceInitiator, VIDs see http://www.linux-usb.org/usb.ids
				if (bi.device.getVendorId() == EosInitiator.CANON_VID) {
				    try {
				        bi.getClearStatus();
				        bi.close();
				    } catch (PTPException e) {e.printStackTrace();}
				    Log.d(TAG, "Device is CANON, open EOSInitiator");
					isCanon = true;
					isNikon = false;
				    bi = new EosInitiator (device, mUsbManager.openDevice(device));
				    changeSpinnerToCanon();
				}
				else if (device.getVendorId() == NikonInitiator.NIKON_VID) {
				    try {
				        bi.getClearStatus();
				        bi.close();
				    } catch (PTPException e) {e.printStackTrace();}
				    Log.d(TAG, "Device is Nikon, open NikonInitiator");
					isCanon = false;
					isNikon = true;
				    bi = new NikonInitiator (device, mUsbManager.openDevice(device));
				    changeSpinnerToNikon();
				}	
				
				dispDevice (bi.getDevice());
				bi.openSession();
			
			
			
			
			} catch (PTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log(e.toString());
			}
		}
	}

	public void detachDevice () {
		if (bi != null /*&& mDevice.equals(device)*/) {			
			if (bi.device != null) Log.d(TAG, "detachDevice: " +bi.device.getDeviceName());
			try {
				bi.close();
			} catch (PTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public Response showResponse (Response response) {
		log ("  Type: " + response.getBlockTypeName(response.getBlockType()) +" (Code: " +response.getBlockType() +")\n");   // getU16 (4)
		log ("  Name: " + response.getCodeName(response.getCode())+ ", code: 0x" +Integer.toHexString(response.getCode()) +"\n"); //getU16 (6)
		//			log ("  CodeString:" + response.getCodeString()+ "\n");
		log ("  Length: " + response.getLength()+ " bytes\n");  //getS32 (0)
		log ("  String: " + response.toString());
		return response;
	}



	// receive Broadcasts
	BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "mUsbReceiver  onReceive");
			String action = intent.getAction();
			UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
			if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action) /*|| (device != null)*/) {
				clearTV ();
				tv1.setText("USB_DEVICE_ATTACHED");
				initDevice (device);
			} else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
				tv1.setText("USB_DEVICE_DETACHED");
				if (bi != null && bi.session != null) bi.session.close();
				detachDevice ();
			}	    			        
		}
	};


	@Override
	public void onStop() {
		super.onStop();
		detachDevice ();
	}




	private void dispDevice(UsbDevice device) {
		tv2.setText("N: "+device.getDeviceName() + "\nPID: " +device.getProductId() + " VID: "+ device.getVendorId());
		tv3.setText("Prot: "+device.getDeviceProtocol() +", Cl: "+device.getDeviceClass()+", Subcl: "+device.getDeviceSubclass());
		tv5.setText ("");
	}



	public void clearTV ()
	{
		tv1.setText("Clear");
		tv2.setText("Clear");
		tv3.setText("Clear");
		tv4.setText("Clear");
		tv5.setText("Clear");
		tv6.setText("Clear");	    
	}


	/** opens session, sends EOS_OC_Capture command, closes session
	 * 
	 * @param session
	 * @return
	 */
	public boolean releaseShutter (Session session){
		Log.d(TAG, "Starting releaseShutter");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			try {
				Log.d(TAG, "Start initiateCapture");
				log("Start initiateCapture");
				bi.initiateCapture(0, 0);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}  //releaseShutter
	/*
	public boolean setAppreture (Session session){
		Log.d(TAG, "Starting releaseShutter");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Setting appreture");
				log("Start appreture");
				
				//resp = bi.setDevicePropValueEx (Command.EOS_DPC_ShutterSpeed, EosEventConstants.EosShutterSpeed_3); 
				//resp = bi.setDevicePropValueEx (Command.EOS_DPC_Iso, EosEventConstants.EosISOSPeed1600); 
				resp = bi.setDevicePropValueEx (Command.EOS_DPC_Aperture, EosEventConstants.EosAperture_4);
				//resp = bi.setDevicePropValueEx (Command.EOS_DPC_ExposureCompensation, 0xfc); // sets compensation to -1/2

				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Appreture: ", resp.getCode());	
		}
		return result;
	}  

	public boolean setISO (Session session){
		Log.d(TAG, "Starting Test: setISO");
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		boolean result = session.isActive();
		
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
	        int ret = 0;
	        Response resp = null;
	        
	        synchronized (bi.session) {

	        	try {
	        		resp = bi.transact1(Command.EosSetRemoteMode, null, 1);
	        	} catch (PTPException e) {
	        		// TODO Auto-generated catch block
	        		e.printStackTrace();
	        	}
	        	bi.showResponseCode ("  EosSetRemoteMode 1: ", resp.getCode());
	        	
	        	try {
	        		resp = bi.transact1(Command.EosSetEventMode, null, 1);
	        	} catch (PTPException e) {
	        		// TODO Auto-generated catch block
	        		e.printStackTrace();
	        	}
	        	bi.showResponseCode ("  EosSetEventMode 1: ", resp.getCode());

	        	try {
	        		resp = bi.transact0(Command.EosGetEvent, null);
	        	} catch (PTPException e) {
	        		// TODO Auto-generated catch block
	        		e.printStackTrace();
	        	}
	        	bi.showResponseCode ("  EosGetEvent 0: ", resp.getCode());	        	
	        	

	        	try {
	        		resp = bi.transact1(Command.EosSetRemoteMode, null, 0);
	        		bi.showResponseCode ("  Reset EosSetRemoteMode 0: ", resp.getCode());
	        	} catch (PTPException e) {}

	        }	        
		}
		return result;
	} */
	
//////////////////////////////////////////////
	public boolean setShutter (Session session, int shutter){
		Log.d(TAG, "Set Shutter");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set Shutter");
				log("Set Shutter");
				resp = bi.setShutterSpeed(shutter);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set Shutter: ", resp.getCode());	
		}
		return result;
	} 	
	
//////////////////////////////////////////////
	public boolean setISO (Session session, int isoValue){
		Log.d(TAG, "Set ISO");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set ISO");
				log("Set ISO");
				resp = bi.setISO(isoValue);
				//resp = bi.setISO(NikonEventConstants.NK_ISO_1600);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set ISO: ", resp.getCode());	
		}
		return result;
	} 		
//////////////////////////////////////////////
	public boolean setAperture (Session session, int apertureValue){
		Log.d(TAG, "Set Aperture");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set Aperture");
				log("Set Aperture");
				resp = bi.setAperture(apertureValue);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set Aperture: ", resp.getCode());	
		}
		return result;
	} 
	
//////////////////////////////////////////////
	public boolean moveFocusForward (Session session){
		Log.d(TAG, "Starting moveFocusForward");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Start moveFocusForward");
				log("Start bulb");

				resp = bi.MoveFocus(3);

				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Start moveFocusForward: ", resp.getCode());	
		}
		return result;
	}  	
//////////////////////////////////////////////
	public boolean moveFocusBackward (Session session){
		Log.d(TAG, "Starting moveFocusBackward");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Start moveFocusBackward");
				log("Start bulb");

				resp = bi.MoveFocus(0x8003);

				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Start moveFocusBackward: ", resp.getCode());	
		}
		return result;
	}  	
	//////////////////////
	public boolean setPictureSyle (Session session, int pictureValue){
		Log.d(TAG, "Set setPictureSyle");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set setPictureSyle");
				log("Set setPictureSyle");
				resp = bi.setPictureStyle(pictureValue);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set setPictureSyle: ", resp.getCode());	
		}
		return result;
	} 
		
//////////////////////////////////////////////	
	public boolean setWhiteBalance (Session session, int whitebalanceValue){
		Log.d(TAG, "Set setWhiteBalance");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set setWhiteBalance");
				log("Set setWhiteBalance");
				resp = bi.setWhiteBalance(whitebalanceValue);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set setWhiteBalance: ", resp.getCode());	
		}
		return result;
	} 
		
//////////////////////////////////////////////	
	public boolean setImageQuality (Session session, int imagequalityValue){
		Log.d(TAG, "Set ImageQuality");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set ImageQuality");
				log("Set ImageQuality");
				resp = bi.setImageQuality(imagequalityValue);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set ImageQuality: ", resp.getCode());	
		}
		return result;
	} 
//////////////////////////////////////////////
	public boolean setExposure (Session session, int exposureValue){
		Log.d(TAG, "Set setExposure");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set setExposure");
				log("Set setExposure");
				resp = bi.setExposure(exposureValue);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set setExposure: ", resp.getCode());	
		}
		return result;
	} 
//////////////////////////////////////////////
	public boolean setDriveMode (Session session, int drivemodeValue){
		Log.d(TAG, "Set setDriveMode");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set setDriveMode");
				log("Set setDriveMode");
				resp = bi.setDriveMode(drivemodeValue);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set setDriveMode: ", resp.getCode());	
		}
		return result;
	} 	
//////////////////////////////////////////////
	public boolean setMetering (Session session, int meteringValue){
		Log.d(TAG, "Set setMetering");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Set setMetering");
				log("Set setMetering");
				resp = bi.setMetering(meteringValue);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Set setMetering: ", resp.getCode());	
		}
		return result;
	} 		
//////////////////////////////////////////////		
	public boolean startBulb (Session session){
		Log.d(TAG, "Starting releaseShutter");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Start bulb");
				log("Start bulb");

				//resp = bi.setDevicePropValueEx (Command.EOS_DPC_Iso, EosEventConstants.ISO_1600); 
				//resp = bi.MoveFocus(3);
				resp = bi.setPictureStyle(EosEventConstants.PictureStyle_Standard);
				//resp = bi.startBulbs();
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Start Bulb: ", resp.getCode());	
		}
		return result;
	}  
//////////////////////////////////////////////
	public boolean stopBulb (Session session){
		Log.d(TAG, "Starting stopBulb");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Stop bulb");
				log("Stop bulb");
				//resp = bi.setShutterSpeed(EosEventConstants.SHUTTER_SPEED_1_20);
				//resp = bi.MoveFocus(0x8003);
				resp = bi.setPictureStyle(EosEventConstants.PictureStyle_Standard);
				//resp = bi.stopBulbs();
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Stop Bulb: ", resp.getCode());	
		}
		return result;
	} 
//////////////////////////////////////////////
	public boolean getShutterSpeed (Session session){
		Log.d(TAG, "getShutterSpeed");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "getShutterSpeed");
				log("getShutterSpeed");
				resp = bi.MoveFocus(0x8003);
				//resp = bi.getShutterSpeed();
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("getShutterSpeed: ", resp.getCode());	
		}
		return result;
	} 	
//////////////////////////////////////////////
	public boolean setShutterSpeed (Session session, int speed){
		Log.d(TAG, "Starting stopBulb");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			Response resp = null;
			try {
				Log.d(TAG, "Stop bulb");
				log("Stop bulb");
				resp = bi.setShutterSpeed(speed);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			bi.showResponseCode ("Stop Bulb: ", resp.getCode());	
		}
		return result;
	} 
//////////////////////////////////////////////
	public boolean initLiveview (Session session){
		Log.d(TAG, "Set setMetering");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {

			try {
				Log.d(TAG, "Init Liveview");
				log("Init Liveview");
				bi.setupLiveview();
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
				
		}
		return result;
	} 		
//////////////////////////////////////////////
	public boolean startLiveview (Session session){
		Log.d(TAG, "Set setMetering");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return false;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {

			Log.d(TAG, "Init Liveview");
			bi.getLiveView(liveViewHolder);
			result = true;
				
		}
		return result;
	} 	
//////////////////////////////////////////////
///////////////////////////////////////////
	public boolean testFunction (Session session){

		Log.d(TAG, "Starting releaseShutter");
		boolean result = session.isActive();
		if (bi.device == null) {log("NO DEVICE OPENED"); return result;}
		if (!bi.isSessionActive())
			try {
				bi.openSession();
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
		if (bi.isSessionActive()) {
			
			try {
				Log.d(TAG, "Start initiateCapture3");
				log("Start initiateCapture2");
				//bi.initiateCapture(0, 0);
				
				testInfo = bi.getPropValue(NikonEventConstants.PTP_DPC_ExposureTime);
				result = true;
			} catch (PTPException e) {
				e.printStackTrace();
				result = false;
			}
			
		}
		
		
		return result;
	}  //releaseShutter
	//////////////////////////////////////
	public void changeSpinnerToNikon(){
		
		ArrayAdapter<CharSequence> exposure_adapter = ArrayAdapter.createFromResource(this , 
				R.array.nikon_exposure_list, android.R.layout.simple_spinner_item);
		exposure_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		exposure_spinner.setAdapter(exposure_adapter);
		
		ArrayAdapter<CharSequence> shutter_adapter = ArrayAdapter.createFromResource(this,
		        R.array.nikon_shutter_list, android.R.layout.simple_spinner_item);
		shutter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shutter_spinner.setAdapter(shutter_adapter);
		
		ArrayAdapter<CharSequence> iso_adapter = ArrayAdapter.createFromResource(this,
		        R.array.nikon_iso_list, android.R.layout.simple_spinner_item);
		iso_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		iso_spinner.setAdapter(iso_adapter);
		
		ArrayAdapter<CharSequence> aperture_adapter = ArrayAdapter.createFromResource(this,
		        R.array.nikon_aperture_list, android.R.layout.simple_spinner_item);
		aperture_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		aperture_spinner.setAdapter(aperture_adapter);
		
		ArrayAdapter<CharSequence> whitebalance_adapter = ArrayAdapter.createFromResource(this,
		        R.array.nikon_whitebalance_list, android.R.layout.simple_spinner_item);
		whitebalance_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		whitebalance_spinner.setAdapter(whitebalance_adapter);
		
		
		
		button8.setEnabled(false); picturestyle_spinner.setEnabled(false);
		button11.setEnabled(false); drivemode_spinner.setEnabled(false);
		button12.setEnabled(false); metering_spinner.setEnabled(false);
		ButtonMoveForward.setEnabled(false);ButtonMoveBackward.setEnabled(false);

	}
	
	public void changeSpinnerToCanon(){
		
		ArrayAdapter<CharSequence> shutter_adapter = ArrayAdapter.createFromResource(this,
		        R.array.shutter_list, android.R.layout.simple_spinner_item);
		shutter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shutter_spinner.setAdapter(shutter_adapter);
		
		ArrayAdapter<CharSequence> iso_adapter = ArrayAdapter.createFromResource(this,
		        R.array.iso_list, android.R.layout.simple_spinner_item);
		iso_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		iso_spinner.setAdapter(iso_adapter);
		
		ArrayAdapter<CharSequence> aperture_adapter = ArrayAdapter.createFromResource(this,
		        R.array.aperture_list, android.R.layout.simple_spinner_item);
		aperture_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		aperture_spinner.setAdapter(aperture_adapter);
		
		ArrayAdapter<CharSequence> picturestyle_adapter = ArrayAdapter.createFromResource(this,
		        R.array.picturestyle_list, android.R.layout.simple_spinner_item);
		picturestyle_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		picturestyle_spinner.setAdapter(picturestyle_adapter);
		
		ArrayAdapter<CharSequence> whitebalance_adapter = ArrayAdapter.createFromResource(this,
		        R.array.whitebalance_list, android.R.layout.simple_spinner_item);
		whitebalance_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		whitebalance_spinner.setAdapter(whitebalance_adapter);

		ArrayAdapter<CharSequence> exposure_adapter = ArrayAdapter.createFromResource(this,
		        R.array.exposure_list, android.R.layout.simple_spinner_item);
		exposure_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		exposure_spinner.setAdapter(exposure_adapter);
		
		ArrayAdapter<CharSequence> drivemode_adapter = ArrayAdapter.createFromResource(this,
		        R.array.drivemode_list, android.R.layout.simple_spinner_item);
		drivemode_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		drivemode_spinner.setAdapter(drivemode_adapter);
		
		ArrayAdapter<CharSequence> metering_adapter = ArrayAdapter.createFromResource(this,
		        R.array.metering_list, android.R.layout.simple_spinner_item);
		metering_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		metering_spinner.setAdapter(metering_adapter);
		
		button8.setEnabled(true); picturestyle_spinner.setEnabled(true);
		button11.setEnabled(true); drivemode_spinner.setEnabled(true);
		button12.setEnabled(true); metering_spinner.setEnabled(true);
		ButtonMoveForward.setEnabled(true);ButtonMoveBackward.setEnabled(true);
	}
	
	
	
	/*
	 * status = canon_int_do_control_dialogue (camera,CANON_USB_CONTROL_GET_PARAMS,
                                               0x00, 0, &response, &len);
	 */
	//	 **************** run USB tasks in its own thread **********************

	final Runnable r = new Runnable() {
		public void run() {
//			Log.d(TAG, "running thread " +thread.getName());
			if (thread.getName().equals(THREAD_SHUTTER)) { 
				releaseShutter (bi.session);
			}; 		
			if (thread.getName().equals(THREAD_TEST)) { 
				testFunction(bi.session);
			};
			if (thread.getName().equals(THREAD_STARTBULB)) { 
				startBulb(bi.session);
			};
			if (thread.getName().equals(THREAD_STOPBULB)) { 
				stopBulb(bi.session);
			};
			if (thread.getName().equals(THREAD_SETSHUTTER)) { 
				setShutter(bi.session, shutter_value);
			};		
			if (thread.getName().equals(THREAD_SETISO)) { 
				setISO(bi.session, iso_value);
			};
			if (thread.getName().equals(THREAD_SETAPERTURE)) { 
				setAperture(bi.session, aperture_value);
			};
			if (thread.getName().equals(THREAD_FOCUSFORWARD)) { 
				moveFocusForward(bi.session);
			};
			if (thread.getName().equals(THREAD_FOCUSBACKWARD)) { 
				moveFocusBackward(bi.session);
			};
			if (thread.getName().equals(THREAD_SETPICTURESTYLE)) { 
				setPictureSyle(bi.session, picturestyle_value);
			};
			if (thread.getName().equals(THREAD_SETWHITEBALANCE)) { 
				setWhiteBalance(bi.session,whitebalance_value);
			};
			if (thread.getName().equals(THREAD_SETEXPOSURE)) { 
				setExposure(bi.session, exposure_value);
			};
			if (thread.getName().equals(THREAD_SETDRIVEMODE)) { 
				setDriveMode(bi.session, drivemode_value);
			};		
			
			if (thread.getName().equals(THREAD_SETMETERING)) { 
				setMetering(bi.session, metering_value);
			};
			
			if (thread.getName().equals(THREAD_INITLIVEVIEW)) { 
				initLiveview(bi.session);
				liveViewTurnedOn = true;
			};
			
			if (thread.getName().equals(THREAD_STARTLIVEVIEW)) { 
				while(liveViewTurnedOn)
				{
					startLiveview(bi.session);
				}
			};
			
			if (thread.getName().equals(THREAD_STOPLIVEVIEW)) { 
				liveViewTurnedOn = false;
			};
			
		}
	};

	// show messages
	void log (String s) {
		Message msg = new Message ();
		msg.what = MSG_SHOW_TV2;
		msg.obj = s;
		myHandler.sendMessage(msg);
	}

	void log (int msgWhat, String s) {
		Message msg = new Message ();
		msg.what = msgWhat;
		msg.obj = s;
		myHandler.sendMessage(msg);
	}

	Handler myHandler = new Handler() {  
		public void handleMessage(Message msg) {
			switch (msg.what ){
			case MSG_SHOW_TV2: tv2.append ((String) msg.obj);
			break;
			case MSG_SHOW_TV3: tv3.append ((String) msg.obj);
			break;
			case MSG_SHOW_TV4: tv4.append ((String) msg.obj);
			break;
			case MSG_SHOW_TV5: tv5.append ((String) msg.obj);
			break;
			case MSG_SHOW_TV6: tv6.append ((String) msg.obj);
			break;
			}
		}
	};
		
}
