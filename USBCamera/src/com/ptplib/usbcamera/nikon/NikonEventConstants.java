/* Copyright 2010 by Stefano Fornari
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License; or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful;
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not; write to the Free Software
 * Foundation; Inc.; 59 Temple Place; Suite 330; Boston; MA  02111-1307  USA
 */

package com.ptplib.usbcamera.nikon;

/**
 * EOS event codes
 *
 * @author stefano fornari
 */
public interface NikonEventConstants {


	//nikon_effect_modes
	public static final int NK_Night_Vision = 	0x00;
	public static final int NK_Color_sketch = 	0x01;
	public static final int NK_Miniature_effect = 0x02;
	public static final int NK_Selective_color = 0x03;
	public static final int NK_Silhouette = 	0x04;
	public static final int NK_High_key = 	0x05;
	public static final int NK_Low_key = 	0x06;
	
	
	//flash_mode
	public static final int NK_Automatic_Flash = 	0x0001;
	public static final int NK_Flash_off = 		0x0002;
	public static final int NK_Fill_flash = 		0x0003;
	public static final int NK_Red_eye_automatic = 	0x0004;
	public static final int NK_Red_eye_fill = 		0x0005;
	public static final int NK_External_sync = 		0x0006;
			
			
	//nikon_evstep
	public static final int NK_1_d3 = 0;
	public static final int NK_1_d2 = 1;
	
	// focus_metering
	public static final int NK_Centre_spot = 0x0001;
	public static final int NK_Multi_spot = 0x0002;
	public static final int NK_Single_Area = 0x8010;
	public static final int NK_Closest_Subject = 0x8011;
	public static final int NK_Group_Dynamic = 0x8012;
	
	//Nikon capture_mode
	public static final int NK_Single_Shot = 	0x0001;
	public static final int NK_Burst = 		0x0002;
	public static final int NK_Timelapse = 	0x0003;
	public static final int NK_Continuous_Low_Speed = 0x8010;
	public static final int NK_Timer = 		0x8011;
	public static final int NK_Mirror_Up = 	0x8012;
	public static final int NK_Remote = 		0x8013;
	public static final int NK_Quick_Response_Remote = 0x8014; 
	public static final int NK_Delayed_Remote = 	0x8015; 
	public static final int NK_Quiet_Release = 	0x8016; 
	
	//nikon_scenemode
	public static final int NK_Night_landscape =0;
	public static final int NK_Party_Indoor =	1;
	public static final int NK_Beach_Snow =	2;
	public static final int NK_Sunset =		3;
	public static final int NK_Dusk_Dawn =		4;
	public static final int NK_Pet_Portrait =	5;
	public static final int NK_Candlelight =	6;
	public static final int NK_Blossom =		7;
	public static final int NK_Autumn_colors =	8;
	public static final int NK_Food 			= 9;
	public static final int NK_Night_Portrait = 18;

 // Nikon Extension Operation Codes
    public static final int NK_OC_GetProfileAllData						= 0x9006;
    public static final int NK_OC_SendProfileData						= 0x9007;
    public static final int NK_OC_DeleteProfile							= 0x9008;
    public static final int NK_OC_SetProfileData						= 0x9009;
    public static final int NK_OC_AdvancedTransfer						= 0x9010;
    public static final int NK_OC_GetFileInfoInBlock					= 0x9011;
    public static final int NK_OC_Capture								= 0x90C0;
    public static final int NK_OC_SetControlMode						= 0x90C2;
    public static final int NK_OC_CurveDownload							= 0x90C5;
    public static final int NK_OC_CurveUpload							= 0x90C6;
    public static final int NK_OC_CheckEvent							= 0x90C7;
    public static final int NK_OC_DeviceReady							= 0x90C8;
    public static final int NK_OC_CaptureInSDRAM						= 0x90CB;
    public static final int NK_OC_GetDevicePTPIPInfo					= 0x90E0;

    public static final int PTP_OC_NIKON_GetPreviewImg					= 0x9200;
    public static final int PTP_OC_NIKON_StartLiveView					= 0x9201;
    public static final int PTP_OC_NIKON_EndLiveView					= 0x9202;
    public static final int PTP_OC_NIKON_GetLiveViewImg					= 0x9203;
    public static final int PTP_OC_NIKON_MfDrive						= 0x9204;
    public static final int PTP_OC_NIKON_ChangeAfArea					= 0x9205;
    public static final int PTP_OC_NIKON_AfDriveCancel					= 0x9206;


    // Nikon extention response codes
    public static final int NK_RC_HardwareError							= 0xA001;
    public static final int NK_RC_OutOfFocus							= 0xA002;
    public static final int NK_RC_ChangeCameraModeFailed				= 0xA003;
    public static final int NK_RC_InvalidStatus							= 0xA004;
    public static final int NK_RC_SetPropertyNotSupported				= 0xA005;
    public static final int NK_RC_WbResetError							= 0xA006;
    public static final int NK_RC_DustReferenceError					= 0xA007;
    public static final int NK_RC_ShutterSpeedBulb						= 0xA008;
    public static final int NK_RC_MirrorUpSequence						= 0xA009;
    public static final int NK_RC_CameraModeNotAdjustFNumber			= 0xA00A;
    public static final int NK_RC_NotLiveView							= 0xA00B;
    public static final int NK_RC_MfDriveStepEnd						= 0xA00C;
    public static final int NK_RC_MfDriveStepInsufficiency				= 0xA00E;
    public static final int NK_RC_AdvancedTransferCancel				= 0xA022;

    // Nikon extension Event Codes 
    public static final int NK_EC_ObjectAddedInSDRAM					= 0xC101;
    public static final int NK_EC_CaptureOverflow						= 0xC102;
    public static final int NK_EC_AdvancedTransfer						= 0xC103;

    /* Nikon extension device property codes */
    public static final int NK_DPC_ShootingBank							= 0xD010;
    public static final int NK_DPC_ShootingBankNameA 					= 0xD011;
    public static final int NK_DPC_ShootingBankNameB					= 0xD012;
    public static final int NK_DPC_ShootingBankNameC					= 0xD013;
    public static final int NK_DPC_ShootingBankNameD					= 0xD014;
    public static final int NK_DPC_RawCompression						= 0xD016;
    public static final int NK_DPC_WhiteBalanceAutoBias					= 0xD017;
    public static final int NK_DPC_WhiteBalanceTungstenBias				= 0xD018;
    public static final int NK_DPC_WhiteBalanceFluorescentBias			= 0xD019;
    public static final int NK_DPC_WhiteBalanceDaylightBias				= 0xD01A;
    public static final int NK_DPC_WhiteBalanceFlashBias				= 0xD01B;
    public static final int NK_DPC_WhiteBalanceCloudyBias				= 0xD01C;
    public static final int NK_DPC_WhiteBalanceShadeBias				= 0xD01D;
    public static final int NK_DPC_WhiteBalanceColorTemperature			= 0xD01E;
    public static final int NK_DPC_ImageSharpening						= 0xD02A;
    public static final int NK_DPC_ToneCompensation						= 0xD02B;
    public static final int NK_DPC_ColorModel							= 0xD02C;
    public static final int NK_DPC_HueAdjustment						= 0xD02D;
    public static final int NK_DPC_NonCPULensDataFocalLength			= 0xD02E;
    public static final int NK_DPC_NonCPULensDataMaximumAperture		= 0xD02F;
    public static final int NK_DPC_CSMMenuBankSelect					= 0xD040;
    public static final int NK_DPC_MenuBankNameA						= 0xD041;
    public static final int NK_DPC_MenuBankNameB						= 0xD042;
    public static final int NK_DPC_MenuBankNameC						= 0xD043;
    public static final int NK_DPC_MenuBankNameD						= 0xD044;
    public static final int NK_DPC_A1AFCModePriority					= 0xD048;
    public static final int NK_DPC_A2AFSModePriority					= 0xD049;
    public static final int NK_DPC_A3GroupDynamicAF						= 0xD04A;
    public static final int NK_DPC_A4AFActivation						= 0xD04B;
    public static final int NK_DPC_A5FocusAreaIllumManualFocus			= 0xD04C;
    public static final int NK_DPC_FocusAreaIllumContinuous				= 0xD04D;
    public static final int NK_DPC_FocusAreaIllumWhenSelected 			= 0xD04E;
    public static final int NK_DPC_FocusAreaWrap						= 0xD04F;
    public static final int NK_DPC_A7VerticalAFON						= 0xD050;
    public static final int NK_DPC_ISOAuto								= 0xD054;
    public static final int NK_DPC_B2ISOStep							= 0xD055;
    public static final int NK_DPC_EVStep								= 0xD056;
    public static final int NK_DPC_B4ExposureCompEv						= 0xD057;
    public static final int NK_DPC_ExposureCompensation					= 0xD058;
    public static final int NK_DPC_CenterWeightArea						= 0xD059;
    public static final int NK_DPC_AELockMode							= 0xD05E;
    public static final int NK_DPC_AELAFLMode							= 0xD05F;
    public static final int NK_DPC_MeterOff								= 0xD062;
    public static final int NK_DPC_SelfTimer							= 0xD063;
    public static final int NK_DPC_MonitorOff							= 0xD064;
    public static final int NK_DPC_D1ShootingSpeed						= 0xD068;
    public static final int NK_DPC_D2MaximumShots						= 0xD069;
    public static final int NK_DPC_D3ExpDelayMode						= 0xD06A;
    public static final int NK_DPC_LongExposureNoiseReduction			= 0xD06B;
    public static final int NK_DPC_FileNumberSequence					= 0xD06C;
    public static final int NK_DPC_D6ControlPanelFinderRearControl		= 0xD06D;
    public static final int NK_DPC_ControlPanelFinderViewfinder			= 0xD06E;
    public static final int NK_DPC_D7Illumination						= 0xD06F;
    public static final int NK_DPC_E1FlashSyncSpeed						= 0xD074;
    public static final int NK_DPC_FlashShutterSpeed					= 0xD075;
    public static final int NK_DPC_E3AAFlashMode						= 0xD076;
    public static final int NK_DPC_E4ModelingFlash						= 0xD077;
    public static final int NK_DPC_BracketSet							= 0xD078;
    public static final int NK_DPC_E6ManualModeBracketing				= 0xD079;	
    public static final int NK_DPC_BracketOrder							= 0xD07A;
    public static final int NK_DPC_E8AutoBracketSelection				= 0xD07B;
    public static final int NK_DPC_BracketingSet						= 0xD07C;
    public static final int NK_DPC_F1CenterButtonShootingMode			= 0xD080;
    public static final int NK_DPC_CenterButtonPlaybackMode				= 0xD081;
    public static final int NK_DPC_F2Multiselector						= 0xD082;
    public static final int NK_DPC_F3PhotoInfoPlayback					= 0xD083;
    public static final int NK_DPC_F4AssignFuncButton					= 0xD084;
    public static final int NK_DPC_F5CustomizeCommDials					= 0xD085;
    public static final int NK_DPC_ReverseCommandDial					= 0xD086;
    public static final int NK_DPC_ApertureSetting						= 0xD087;
    public static final int NK_DPC_MenusAndPlayback						= 0xD088;
    public static final int NK_DPC_F6ButtonsAndDials					= 0xD089;
    public static final int NK_DPC_NoCFCard								= 0xD08A;
    public static final int NK_DPC_ImageCommentString					= 0xD090;
    public static final int NK_DPC_ImageCommentAttach					= 0xD091;
    public static final int NK_DPC_ImageRotation						= 0xD092;
    public static final int NK_DPC_Bracketing							= 0xD0C0;
    public static final int NK_DPC_ExposureBracketingIntervalDist		= 0xD0C1;
    public static final int NK_DPC_BracketingProgram					= 0xD0C2;
    public static final int NK_DPC_WhiteBalanceBracketStep				= 0xD0C4;
    public static final int NK_DPC_LensID								= 0xD0E0;
    public static final int NK_DPC_FocalLengthMin						= 0xD0E3;
    public static final int NK_DPC_FocalLengthMax						= 0xD0E4;
    public static final int NK_DPC_MaxApAtMinFocalLength				= 0xD0E5;
    public static final int NK_DPC_MaxApAtMaxFocalLength				= 0xD0E6;
    public static final int NK_DPC_ExposureTime							= 0xD100;
    public static final int NK_DPC_ACPower								= 0xD101;
    public static final int NK_DPC_MaximumShots							= 0xD103;
    public static final int NK_DPC_AFLLock								= 0xD104;
    public static final int NK_DPC_AutoExposureLock						= 0xD105;
    public static final int NK_DPC_AutoFocusLock						= 0xD106;
    public static final int NK_DPC_AutofocusLCDTopMode2					= 0xD107;
    public static final int NK_DPC_AutofocusArea						= 0xD108;
    public static final int NK_DPC_LightMeter							= 0xD10A;
    public static final int NK_DPC_CameraOrientation					= 0xD10E;
    public static final int NK_DPC_ExposureApertureLock					= 0xD111;
    public static final int NK_DPC_FlashExposureCompensation			= 0xD126;
    public static final int NK_DPC_OptimizeImage						= 0xD140;
    public static final int NK_DPC_Saturation							= 0xD142;
    public static final int NK_DPC_BeepOff								= 0xD160;
    public static final int NK_DPC_AutofocusMode						= 0xD161;
    public static final int NK_DPC_AFAssist								= 0xD163;
    public static final int NK_DPC_PADVPMode							= 0xD164;
    public static final int NK_DPC_ImageReview							= 0xD165;
    public static final int NK_DPC_AFAreaIllumination					= 0xD166;
    public static final int NK_DPC_FlashMode							= 0xD167;
    public static final int NK_DPC_FlashCommanderMode					= 0xD168;
    public static final int NK_DPC_FlashSign							= 0xD169;
    public static final int NK_DPC_RemoteTimeout						= 0xD16B;
    public static final int NK_DPC_GridDisplay							= 0xD16C;
    public static final int NK_DPC_FlashModeManualPower					= 0xD16D;
    public static final int NK_DPC_FlashModeCommanderPower				= 0xD16E;
    public static final int NK_DPC_CSMMenu								= 0xD180;
    public static final int NK_DPC_BracketingFramesAndSteps				= 0xD190;
    public static final int NK_DPC_LowLight								= 0xD1B0;
    public static final int NK_DPC_FlashOpen							= 0xD1C0;
    public static final int NK_DPC_FlashCharged							= 0xD1C1;
    public static final int PTP_DPC_NIKON_FlashMRepeatValue				= 0xD1D0;
    public static final int PTP_DPC_NIKON_FlashMRepeatCount				= 0xD1D1;
    public static final int PTP_DPC_NIKON_FlashMRepeatInterval			= 0xD1D2;
    public static final int PTP_DPC_NIKON_FlashCommandChannel			= 0xD1D3;
    public static final int PTP_DPC_NIKON_FlashCommandSelfMode			= 0xD1D4;
    public static final int PTP_DPC_NIKON_FlashCommandSelfCompensation	= 0xD1D5;
    public static final int PTP_DPC_NIKON_FlashCommandSelfValue			= 0xD1D6;
    public static final int PTP_DPC_NIKON_FlashCommandAMode				= 0xD1D7;
    public static final int PTP_DPC_NIKON_FlashCommandACompensation		= 0xD1D8;
    public static final int PTP_DPC_NIKON_FlashCommandAValue			= 0xD1D9;
    public static final int PTP_DPC_NIKON_FlashCommandBMode				= 0xD1DA;
    public static final int PTP_DPC_NIKON_FlashCommandBCompensation		= 0xD1DB;
    public static final int PTP_DPC_NIKON_FlashCommandBValue			= 0xD1DC;
    public static final int PTP_DPC_NIKON_ActivePicCtrlItem				= 0xD200;
    public static final int PTP_DPC_NIKON_ChangePicCtrlItem				= 0xD201;
    ///////////////////////////////////////////////////////////////////////////////
    
     
//Nikon focuseMode for 0x005a
public static final int NK_FocusMode_Undefined = 0x0000;
public static final int NK_FocusMode_Manual = 0x0001;
public static final int NK_FocusMode_Automatic = 0x0002; 
public static final int NK_FocusMode_AutomaticMacro = 0x0003;


//0x501c : Focus Metering Mode
public static final int NK_FocusMeteringMode_Undefined  = 0x0000;
public static final int NK_FocusMeteringMode_Center_spot = 0x0001;
public static final int NK_FocusMeteringMode_Multi_spot = 0x0002;


//0x5013 : Still Capture Mode
public static final int NK_StilLCaptureMode_Undefined  = 0x0000 ; 
public static final int NK_StilLCaptureMode_Normal = 0x0001 ;
public static final int NK_StilLCaptureMode_Burst = 0x0002 ;
public static final int NK_StilLCaptureMode_Timelapse = 0x0003 ; 

//0x500e : Exposure Program Mode
public static final int NK_ExposureProgramMode_Undefined = 0x0000; 
public static final int NK_ExposureProgramMode_Manual = 0x0001 ;
public static final int NK_ExposureProgramMode_Automatic = 0x0002 ; 
public static final int NK_ExposureProgramMode_Aperture_Priority = 0x0003 ; 
public static final int NK_ExposureProgramMode_Shutter_Priority = 0x0004 ; 
public static final int NK_ExposureProgramMode_Program_Creative = 0x0005 ;  //(greater depth of field)
public static final int NK_ExposureProgramMode_Program_Action = 0x0006 ;  //(faster shutter speed)
public static final int NK_ExposureProgramMode_Portrait = 0x0007 ; 

 
//0x500c : Flash Mode
public static final int NK_FlashMode_Undefined = 0x0000 ; 
public static final int NK_FlashMode_Auto_flash = 0x0001 ; 
public static final int NK_FlashMode_Flash_off = 0x0002 ; 
public static final int NK_FlashMode_Fill_flash = 0x0003 ; 
public static final int NK_FlashMode_Red_eye_auto = 0x0004 ; 
public static final int NK_FlashMode_Red_eye_fill =0x0005 ; 
public static final int NK_FlashMode_External_flash = 0x0006 ; 

//Exposure Metering Mode 0x500b 
public static final int NK_MeteringMode_Undefined = 0x0000 ; 
public static final int NK_MeteringMode_Average =0x0001 ; 
public static final int NK_MeteringMode_Center_weighted_average =0x0002 ; 
public static final int NK_MeteringMode_Multi_spot =0x0003 ; 
public static final int NK_MeteringMode_Center_spot=0x0004 ; 


//NikonFocusAreaTitles
public static final int NK_FocusArea_x_ = 0x00;
public static final int NK_FocusAreax__ = 0x01;	
public static final int NK_FocusArea__x = 0x02;


//NikonFocusModeTitles

public static final int NK_FocusMode_AF_S = 0x00;	
public static final int NK_FocusMode_AF_C = 0x01;	
public static final int NK_FocusMode_AF_A = 0x02;	
public static final int NK_FocusMode_MF   = 0x04; 


// CompressionTitles

public static final int NK_Compression_BASIC = 0x00;		
public static final int NK_Compression_NORM = 0x01;	
public static final int NK_Compression_FINE = 0x02;	
public static final int NK_Compression_RAW = 0x03;
public static final int NK_Compression_RAW_B = 0x04;	


//ApertureTitles PTP_DPC_FNumber 0x5007;
public static final int NK_Aperture_3_50 = 	0x015E;
public static final int NK_Aperture_4_00 =  0x0190;
public static final int NK_Aperture_4_50 =	0x01C2;
public static final int NK_Aperture_4_80 =	0x01E0;
public static final int NK_Aperture_5    =  0x01F4;
public static final int NK_Aperture_5_60 =	0x0230;
public static final int NK_Aperture_6_30 = 	0x0276;
public static final int NK_Aperture_7_10 =	0x02C6;
public static final int NK_Aperture_8 	=    0x0320;
public static final int NK_Aperture_9  	=    0x0384;
public static final int NK_Aperture_10 	=	0x03E8;
public static final int NK_Aperture_11  =	0x044C;
public static final int NK_Aperture_13  =	0x0514;
public static final int NK_Aperture_14  =	0x0578;
public static final int NK_Aperture_16  =	0x0640;
public static final int NK_Aperture_18  =	0x0708;
public static final int NK_Aperture_20  =	0x07D0;
public static final int NK_Aperture_22  =	0x0898;
public static final int NK_Aperture_25  =	0x09C4;
public static final int NK_Aperture_29  =	0x0B54;
public static final int NK_Aperture_32 = 	0x0C80;
public static final int NK_Aperture_36 = 	0x0E10;


            

//ShutterSpeedTitles PTP_DPC_ExposureTime 0x500D;
public static final int NK_ShutterSpeed_4000 =	0x00000002; 
public static final int NK_ShutterSpeed_3200 =	0x00000003;	 
public static final int NK_ShutterSpeed_2500 =	0x00000004;	 
public static final int NK_ShutterSpeed_2000 =	0x00000005; 
public static final int NK_ShutterSpeed_1600 = 0x00000006;
public static final int NK_ShutterSpeed_1250  = 0x00000008;
public static final int NK_ShutterSpeed_1000  = 0x0000000A;
public static final int NK_ShutterSpeed_800  = 0x0000000C;
public static final int NK_ShutterSpeed_640  = 0x0000000F;
public static final int NK_ShutterSpeed_500  = 0x00000014;
public static final int NK_ShutterSpeed_400  = 0x00000019;
public static final int NK_ShutterSpeed_320  = 0x0000001F;
public static final int NK_ShutterSpeed_250  = 0x00000028;
public static final int NK_ShutterSpeed_200  = 0x00000032;
public static final int NK_ShutterSpeed_160  = 0x0000003E;
public static final int NK_ShutterSpeed_125  = 0x00000050;
public static final int NK_ShutterSpeed_100  = 0x00000064;
public static final int NK_ShutterSpeed_80  = 0x0000007D;
public static final int NK_ShutterSpeed_60  = 0x000000A6;
public static final int NK_ShutterSpeed_50  = 0x000000C8;
public static final int NK_ShutterSpeed_40  = 0x000000FA;
public static final int NK_ShutterSpeed_30  = 0x0000014D;
public static final int NK_ShutterSpeed_25  = 0x00000190;
public static final int NK_ShutterSpeed_20  = 0x000001F4;
public static final int NK_ShutterSpeed_15  = 0x0000029A;
public static final int NK_ShutterSpeed_13  = 0x00000301;
public static final int NK_ShutterSpeed_10  = 0x000003E8;
public static final int NK_ShutterSpeed_8  = 0x000004E2;
public static final int NK_ShutterSpeed_6  = 0x00000682;
public static final int NK_ShutterSpeed_5  = 0x000007D0;
public static final int NK_ShutterSpeed_4  = 0x000009C4;
public static final int NK_ShutterSpeed_3  = 0x00000D05;
public static final int NK_ShutterSpeed_2_5  = 0x00000FA0;
public static final int NK_ShutterSpeed_2  = 0x00001388;
public static final int NK_ShutterSpeed_1_6  = 0x0000186A;
public static final int NK_ShutterSpeed_1_3  = 0x00001E0C;
public static final int NK_ShutterSpeed_1_sec  = 0x00002710;
public static final int NK_ShutterSpeed_1_3_sec  = 0x000032C8;
public static final int NK_ShutterSpeed_1_6_sec  = 0x00003E80;
public static final int NK_ShutterSpeed_2_sec  = 0x00004E20;
public static final int NK_ShutterSpeed_2_5_sec  = 0x000061A8;
public static final int NK_ShutterSpeed_3_sec  = 0x00007530;
public static final int NK_ShutterSpeed_4_sec  = 0x00009C40;
public static final int NK_ShutterSpeed_5_sec  = 0x0000C350;
public static final int NK_ShutterSpeed_6_sec  = 0x0000EA60;
public static final int NK_ShutterSpeed_8_sec  = 0x00013880;
public static final int NK_ShutterSpeed_10_sec  = 0x000186A0;
public static final int NK_ShutterSpeed_13_sec  = 0x0001FBD0;
public static final int NK_ShutterSpeed_15_sec  = 0x000249F0;
public static final int NK_ShutterSpeed_20_sec  = 0x00030D40;
public static final int NK_ShutterSpeed_25_sec  = 0x0003D090;
public static final int NK_ShutterSpeed_30_sec  = 0x000493E0;
public static final int NK_ShutterSpeed_Bulb  = 0xFFFFFFFF;

//IsoTitles PTP_DPC_ExposureIndex 0x500F
public static final int NK_ISO_100 = 0x0064;
public static final int NK_ISO_125 = 0x007D;
public static final int NK_ISO_160 = 0x00A0;
public static final int NK_ISO_200 = 0x00C8;
public static final int NK_ISO_250 = 0x00FA;
public static final int NK_ISO_320 = 0x0140;
public static final int NK_ISO_400 = 0x0190;
public static final int NK_ISO_500 = 0x01F4;
public static final int NK_ISO_640 = 0x0280;
public static final int NK_ISO_800 = 0x0320;
public static final int NK_ISO_1000 = 0x03E8;
public static final int NK_ISO_1250 = 0x04E2;
public static final int NK_ISO_1600 = 0x0640;
public static final int NK_ISO_2000 = 0x07D0;
public static final int NK_ISO_2500 = 0x09C4;
public static final int NK_ISO_3200 = 0x0C80;
public static final int NK_ISO_4000 = 0x0FA0;
public static final int NK_ISO_5000 = 0x1388;
public static final int NK_ISO_6400 = 0x1900;
public static final int NK_ISO_Hi03 = 0x1F40;
public static final int NK_ISO_Hi07 = 0x2710;
public static final int NK_ISO_Hi_1 = 0x3200;
public static final int NK_ISO_Hi_2 = 0x6400;


// Exposure Compensation Title Array, PTP_DPC_ExposureBiasCompensation 0x5010
public static final int NK_Exposure_N_5_0 = 0xEC78;				
public static final int NK_Exposure_N_4_7 = 0xEDC6;				
public static final int NK_Exposure_N_4_3 = 0xEF13;				
public static final int NK_Exposure_N_4_0 = 0xF060;				
public static final int NK_Exposure_N_3_7 = 0xF1AE;				
public static final int NK_Exposure_N_3_3 = 0xF2FB;				
public static final int NK_Exposure_N_3_0 = 0xF448;				
public static final int NK_Exposure_N_2_7 = 0xF596;				
public static final int NK_Exposure_N_2_3 = 0xF6E3;
public static final int NK_Exposure_N_2_0 = 0xF830;
public static final int NK_Exposure_N_1_7 = 0xF97E;				
public static final int NK_Exposure_N_1_3 = 0xFACB;				
public static final int NK_Exposure_N_1_0 = 0xFC18;
public static final int NK_Exposure_N_0_7 = 0xFD66;
public static final int NK_Exposure_N_0_3 = 0xFEB3;				
public static final int NK_Exposure_0 = 0x0000;   
public static final int NK_Exposure_P_0_3 = 0x014D;				
public static final int NK_Exposure_P_0_7 = 0x029A;				
public static final int NK_Exposure_P_1_0 = 0x03E8;				
public static final int NK_Exposure_P_1_3 = 0x0535;				
public static final int NK_Exposure_P_1_7 = 0x0682;				
public static final int NK_Exposure_P_2_0 = 0x07D0;				
public static final int NK_Exposure_P_2_3 = 0x091D;				
public static final int NK_Exposure_P_2_7 = 0x0A6A;				
public static final int NK_Exposure_P_3_0 = 0x0BB8;				
public static final int NK_Exposure_P_3_3 = 0x0D05;				
public static final int NK_Exposure_P_3_7 = 0x0E52;				
public static final int NK_Exposure_P_4_0 = 0x0FA0;			
public static final int NK_Exposure_P_4_3 = 0x10ED;				
public static final int NK_Exposure_P_4_7 = 0x123A;				
public static final int NK_Exposure_P_5_0 = 0x1388;


// White Balance Title Array for 0x5005 PTP_DPC_WhiteBalance
public static final int NK_WhiteBalance_Undefined = 0x0000 ;
public static final int NK_WhiteBalance_Manual = 0x0001 ;
public static final int NK_WhiteBalance_Automatic = 0x0002 ;
public static final int NK_WhiteBalance_One_push_Automatic = 0x0003 ;
public static final int NK_WhiteBalance_Daylight = 0x0004 ;
public static final int NK_WhiteBalance_Fluorescent = 0x0005 ;
public static final int NK_WhiteBalance_Tungsten = 0x0006 ;
public static final int NK_WhiteBalance_Flash_Strobe = 0x0007 ;
public static final int NK_WhiteBalance_Clouds = 0x8010;           // Clouds
public static final int NK_WhiteBalance_Shade = 0x8011;            // Shade
public static final int NK_WhiteBalance_Preset = 0x8013;             // Preset

// Picture Style Title Array
public static final int NK_PictureStyle_User1 = 0x21;	
public static final int NK_PictureStyle_User2 =	0x22;	
public static final int NK_PictureStyle_User3 =	0x23;	
public static final int NK_PictureStyle_Standard =	0x81; 	
public static final int NK_PictureStyle_Portrait =	0x82;	
public static final int NK_PictureStyle_Landscape =	0x83; 	
public static final int NK_PictureStyle_Neutral =	0x84;	
public static final int NK_PictureStyle_Faithful =	0x85; 	
public static final int NK_PictureStyle_Monochrome =	0x86;	

//
public static final int PTP_DPC_Undefined					= 0x5000;
public static final int PTP_DPC_BatteryLevel				= 0x5001; //Yes
public static final int PTP_DPC_FunctionalMode				= 0x5002;
public static final int PTP_DPC_ImageSize					= 0x5003; //Yes
public static final int PTP_DPC_CompressionSetting			= 0x5004; //Yes
public static final int PTP_DPC_WhiteBalance				= 0x5005; //Yes WhiteBalance
public static final int PTP_DPC_RGBGain						= 0x5006;
public static final int PTP_DPC_FNumber						= 0x5007; //Yes Aperture
public static final int PTP_DPC_FocalLength					= 0x5008; //Yes
public static final int PTP_DPC_FocusDistance				= 0x5009;
public static final int PTP_DPC_FocusMode					= 0x500A; //Yes
public static final int PTP_DPC_ExposureMeteringMode		= 0x500B; //Yes
public static final int PTP_DPC_FlashMode					= 0x500C; //Yes
public static final int PTP_DPC_ExposureTime				= 0x500D; //Yes ShutterSpeed
public static final int PTP_DPC_ExposureProgramMode			= 0x500E; //Yes
public static final int PTP_DPC_ExposureIndex				= 0x500F; //Yes ISO
public static final int PTP_DPC_ExposureBiasCompensation	= 0x5010; //Yes Exposure
public static final int PTP_DPC_DateTime					= 0x5011; //Yes
public static final int PTP_DPC_CaptureDelay				= 0x5012;
public static final int PTP_DPC_StillCaptureMode			= 0x5013; //Yes
public static final int PTP_DPC_Contrast					= 0x5014;
public static final int PTP_DPC_Sharpness					= 0x5015;
public static final int PTP_DPC_DigitalZoom					= 0x5016;
public static final int PTP_DPC_EffectMode					= 0x5017;
public static final int PTP_DPC_BurstNumber					= 0x5018; //Yes
public static final int PTP_DPC_BurstInterval				= 0x5019;
public static final int PTP_DPC_TimelapseNumber				= 0x501A;
public static final int PTP_DPC_TimelapseInterval			= 0x501B;
public static final int PTP_DPC_FocusMeteringMode			= 0x501C; //Yes
public static final int PTP_DPC_UploadURL					= 0x501D;
public static final int PTP_DPC_Artist						= 0x501E;
public static final int PTP_DPC_CopyrightInfo				= 0x501F;
	

///////////////////////////////////////////////////////////////////////////////////////////End Of Nikon Commands
    /**
     * Events
     */
    public static final int EosEventRequestGetEvent         = 0xC101;
    public static final int EosEventObjectAddedEx           = 0xC181;
    public static final int EosEventObjectRemoved           = 0xC182;
    public static final int EosEventRequestGetObjectInfoEx  = 0xC183;
    public static final int EosEventStorageStatusChanged    = 0xC184;
    public static final int EosEventStorageInfoChanged      = 0xC185;
    public static final int EosEventRequestObjectTransfer   = 0xC186;
    public static final int EosEventObjectInfoChangedEx     = 0xC187;
    public static final int EosEventObjectContentChanged    = 0xC188;
    public static final int EosEventPropValueChanged        = 0xC189;
    public static final int EosEventAvailListChanged        = 0xC18A;
    public static final int EosEventCameraStatusChanged     = 0xC18B;
    public static final int EosEventWillSoonShutdown        = 0xC18D;
    public static final int EosEventShutdownTimerUpdated    = 0xC18E;
    public static final int EosEventRequestCancelTransfer   = 0xC18F;
    public static final int EosEventRequestObjectTransferDT = 0xC190;
    public static final int EosEventRequestCancelTransferDT = 0xC191;
    public static final int EosEventStoreAdded              = 0xC192;
    public static final int EosEventStoreRemoved            = 0xC193;
    public static final int EosEventBulbExposureTime        = 0xC194;
    public static final int EosEventRecordingTime           = 0xC195;
    public static final int EosEventRequestObjectTransferTS = 0xC1A2;
    public static final int EosEventAfResult                = 0xC1A3;
 
    /* Canon extension device property codes */
    public static final int EosDevicePropBeepMode				= 0xD001;
    public static final int EosDevicePropViewfinderMode			= 0xD003;
    public static final int EosDevicePropImageQuality			= 0xD006;
    public static final int EosDevicePropD007					= 0xD007;
    public static final int EosDevicePropImageSize				= 0xD008;
    public static final int EosDevicePropFlashMode				= 0xD00A;
    public static final int EosDevicePropTvAvSetting			= 0xD00C;
    public static final int EosDevicePropMeteringMode			= 0xD010;
    public static final int EosDevicePropMacroMode				= 0xD011;
    public static final int EosDevicePropFocusingPoint			= 0xD012;
    public static final int EosDevicePropWhiteBalance			= 0xD013;
    public static final int EosDevicePropISOSpeed				= 0xD01C;
    public static final int EosDevicePropAperture				= 0xD01D;
    public static final int EosDevicePropShutterSpeed			= 0xD01E;
    public static final int EosDevicePropExpCompensation		= 0xD01F;
    public static final int EosDevicePropD029					= 0xD029;
    public static final int EosDevicePropZoom					= 0xD02A;
    public static final int EosDevicePropSizeQualityMode		= 0xD02C;
    public static final int EosDevicePropFlashMemory			= 0xD031;
    public static final int EosDevicePropCameraModel			= 0xD032;
    public static final int EosDevicePropCameraOwner			= 0xD033;
    public static final int EosDevicePropUnixTime				= 0xD034;
    public static final int EosDevicePropViewfinderOutput		= 0xD036;
    public static final int EosDevicePropRealImageWidth			= 0xD039;
    public static final int EosDevicePropPhotoEffect			= 0xD040;
    public static final int EosDevicePropAssistLight			= 0xD041;
    public static final int EosDevicePropD045					= 0xD045;

    /*
     * Properties
     */
    public static final int EosPropAperture                = 0xD101;
    public static final int EosPropShutterSpeed            = 0xD102;
    public static final int EosPropISOSpeed                = 0xD103;
    public static final int EosPropExpCompensation         = 0xD104;
    public static final int EosPropAutoExposureMode        = 0xD105;
    public static final int EosPropDriveMode               = 0xD106;
    public static final int EosPropMeteringMode            = 0xD107;
    public static final int EosPropFocusMode               = 0xD108;
    public static final int EosPropWhiteBalance            = 0xD109;
    public static final int EosPropColorTemperature        = 0xD10A;
    public static final int EosPropWhiteBalanceAdjustA     = 0xD10B;
    public static final int EosPropWhiteBalanceAdjustB     = 0xD10C;
    public static final int EosPropWhiteBalanceXA          = 0xD10D;
    public static final int EosPropWhiteBalanceXB          = 0xD10E;
    public static final int EosPropColorSpace              = 0xD10F;
    public static final int EosPropPictureStyle            = 0xD110;
    public static final int EosPropBatteryPower            = 0xD111;
    public static final int EosPropBatterySelect           = 0xD112;
    public static final int EosPropCameraTime              = 0xD113;
    public static final int EosPropOwner                   = 0xD115;
    public static final int EosPropModelID		  		   = 0xD116;
    public static final int EosPropPTPExtensionVersion     = 0xD119;
    public static final int EosPropDPOFVersion             = 0xD11A;
    public static final int EosPropAvailableShots          = 0xD11B;
    public static final int EosPropCaptureDestination      = 0xD11C;
    public static final int EosPropBracketMode             = 0xD11D;
    public static final int EosPropCurrentStorage          = 0xD11E;
    public static final int EosPropCurrentFolder           = 0xD11F;
    public static final int EosPropImageFormat             = 0xD120; /* file setting */
    public static final int EosPropImageFormatCF           = 0xD121; /* file setting CF */
    public static final int EosPropImageFormatSD           = 0xD122; /* file setting SD */
    public static final int EosPropImageFormatExtHD        = 0xD123; /* file setting exthd */
    public static final int EosPropCompressionS            = 0xD130;
    public static final int EosPropCompressionM1           = 0xD131;
    public static final int EosPropCompressionM2           = 0xD132;
    public static final int EosPropCompressionL            = 0xD133;
    public static final int EosPropPCWhiteBalance1         = 0xD140;
    public static final int EosPropPCWhiteBalance2         = 0xD141;
    public static final int EosPropPCWhiteBalance3         = 0xD142;
    public static final int EosPropPCWhiteBalance4         = 0xD143;
    public static final int EosPropPCWhiteBalance5         = 0xD144;
    public static final int EosPropMWhiteBalance           = 0xD145;
    public static final int EosPropPictureStyleStandard    = 0xD150;
    public static final int EosPropPictureStylePortrait    = 0xD151;
    public static final int EosPropPictureStyleLandscape   = 0xD152;
    public static final int EosPropPictureStyleNeutral     = 0xD153;
    public static final int EosPropPictureStyleFaithful    = 0xD154;
    public static final int EosPropPictureStyleMonochrome  = 0xD155;
    public static final int EosPropPictureStyleUserSet1    = 0xD160;
    public static final int EosPropPictureStyleUserSet2    = 0xD161;
    public static final int EosPropPictureStyleUserSet3    = 0xD162;
    public static final int EosPropPictureStyleParam1      = 0xD170;
    public static final int EosPropPictureStyleParam2      = 0xD171;
    public static final int EosPropPictureStyleParam3      = 0xD172;
    public static final int EosPropFlavorLUTParams         = 0xD17F;
    public static final int EosPropCustomFunc1             = 0xD180;
    public static final int EosPropCustomFunc2             = 0xD181;
    public static final int EosPropCustomFunc3             = 0xD182;
    public static final int EosPropCustomFunc4             = 0xD183;
    public static final int EosPropCustomFunc5             = 0xD184;
    public static final int EosPropCustomFunc6             = 0xD185;
    public static final int EosPropCustomFunc7             = 0xD186;
    public static final int EosPropCustomFunc8             = 0xD187;
    public static final int EosPropCustomFunc9             = 0xD188;
    public static final int EosPropCustomFunc10            = 0xD189;
    public static final int EosPropCustomFunc11            = 0xD18A;
    public static final int EosPropCustomFunc12            = 0xD18B;
    public static final int EosPropCustomFunc13            = 0xD18C;
    public static final int EosPropCustomFunc14            = 0xD18D;
    public static final int EosPropCustomFunc15            = 0xD18E;
    public static final int EosPropCustomFunc16            = 0xD18F;
    public static final int EosPropCustomFunc17            = 0xD190;
    public static final int EosPropCustomFunc18            = 0xD191;
    public static final int EosPropCustomFunc19            = 0xD192;
    public static final int EosPropCustomFuncEx            = 0xD1A0;
    public static final int EosPropMyMenu                  = 0xD1A1;
    public static final int EosPropMyMenuList              = 0xD1A2;
    public static final int EosPropWftStatus               = 0xD1A3;
    public static final int EosPropWftInputTransmission    = 0xD1A4;
    public static final int EosPropHDDirectoryStructure    = 0xD1A5;
    public static final int EosPropBatteryInfo             = 0xD1A6;
    public static final int EosPropAdapterInfo             = 0xD1A7;
    public static final int EosPropLensStatus              = 0xD1A8;
    public static final int EosPropQuickReviewTime         = 0xD1A9;
    public static final int EosPropCardExtension           = 0xD1AA;
    public static final int EosPropTempStatus              = 0xD1AB;
    public static final int EosPropShutterCounter          = 0xD1AC;
    public static final int EosPropSpecialOption           = 0xD1AD;
    public static final int EosPropPhotoStudioMode         = 0xD1AE;
    public static final int EosPropSerialNumber            = 0xD1AF;
    public static final int EosPropEVFOutputDevice         = 0xD1B0;
    public static final int EosPropEVFMode                 = 0xD1B1;
    public static final int EosPropDepthOfFieldPreview     = 0xD1B2;
    public static final int EosPropEVFSharpness            = 0xD1B3;
    public static final int EosPropEVFWBMode               = 0xD1B4;
    public static final int EosPropEVFClickWBCoeffs        = 0xD1B5;
    public static final int EosPropEVFColorTemp            = 0xD1B6;
    public static final int EosPropExposureSimMode         = 0xD1B7;
    public static final int EosPropEVFRecordStatus         = 0xD1B8;
    public static final int EosPropLvAfSystem              = 0xD1BA;
    public static final int EosPropMovSize                 = 0xD1BB;
    public static final int EosPropLvViewTypeSelect        = 0xD1BC;
    public static final int EosPropArtist                  = 0xD1D0;
    public static final int EosPropCopyright               = 0xD1D1;
    public static final int EosPropBracketValue            = 0xD1D2;
    public static final int EosPropFocusInfoEx             = 0xD1D3;
    public static final int EosPropDepthOfField            = 0xD1D4;
    public static final int EosPropBrightness              = 0xD1D5;
    public static final int EosPropLensAdjustParams        = 0xD1D6;
    public static final int EosPropEFComp                  = 0xD1D7;
    public static final int EosPropLensName                = 0xD1D8;
    public static final int EosPropAEB                     = 0xD1D9;
    public static final int EosPropStroboSetting           = 0xD1DA;
    public static final int EosPropStroboWirelessSetting   = 0xD1DB;
    public static final int EosPropStroboFiring            = 0xD1DC;
    public static final int EosPropLensID                  = 0xD1DD;

    /**
     * ISO speed
     */
    public static final int ISO_Auto  = 0x00;
	public static final int ISO_50 = 0x40;
	public static final int ISO_100 = 0x48;
	public static final int ISO_125 = 0x4b;
	public static final int ISO_160 = 0x4d;
	public static final int ISO_200 = 0x50;
	public static final int ISO_250 = 0x53;
	public static final int ISO_320 = 0x55;
	public static final int ISO_400 = 0x58;
	public static final int ISO_500 = 0x5b;
	public static final int ISO_640 = 0x5d;
	public static final int ISO_800 = 0x60;
	public static final int ISO_1000 = 0x63;
	public static final int ISO_1250 = 0x65;
	public static final int ISO_1600 = 0x68;
	public static final int ISO_3200 = 0x70;
	

    /**
     * Aperture
     */
	public static final int APERTURE_F1_2 = 0x0d;
	public static final int APERTURE_F1_4 = 0x10;
	public static final int APERTURE_F1_6 = 0x13;
	public static final int APERTURE_F1_8 = 0x15;
	public static final int APERTURE_F2_0 = 0x18;
	public static final int APERTURE_F2_2 = 0x1b;
	public static final int APERTURE_F2_5 = 0x1d;
	public static final int APERTURE_F2_8 = 0x20;
	public static final int APERTURE_F3_2 = 0x23;
	public static final int APERTURE_F3_5 = 0x25;
	public static final int APERTURE_F4_0 = 0x28;
	public static final int APERTURE_F4_5 = 0x2b;
	public static final int APERTURE_F5_0 = 0x2d;
	public static final int APERTURE_F5_6 = 0x30;
	public static final int APERTURE_F6_3 = 0x33;
	public static final int APERTURE_F7_1 = 0x35;
	public static final int APERTURE_F8 = 0x38;
	public static final int APERTURE_F9 = 0x3b;
	public static final int APERTURE_F10 = 0x3d;
	public static final int APERTURE_F11 = 0x40;
	public static final int APERTURE_F13 = 0x43;
	public static final int APERTURE_F14 = 0x45;
	public static final int APERTURE_F16 = 0x48;
	public static final int APERTURE_F18 = 0x4b;
	public static final int APERTURE_F20 = 0x4d;
	public static final int APERTURE_F22 = 0x50;
	public static final int APERTURE_F25 = 0x53;
	public static final int APERTURE_F29 = 0x55;
	public static final int APERTURE_F32 = 0x58;



    public static final int EosAperture_4    = 0x28;
    public static final int EosAperture_4_5  = 0x2B;
    public static final int EosAperture_5    = 0x2D;
    public static final int EosAperture_5_6  = 0x30;
    public static final int EosAperture_6_3  = 0x33;
    public static final int EosAperture_7_1  = 0x35;
    public static final int EosAperture_8    = 0x38;
    public static final int EosAperture_9    = 0x3B;
    public static final int EosAperture_10   = 0x3D;
    public static final int EosAperture_11   = 0x40;
    public static final int EosAperture_13   = 0x43;
    public static final int EosAperture_14   = 0x45;
    public static final int EosAperture_16   = 0x48;
    public static final int EosAperture_18   = 0x4B;
    public static final int EosAperture_20   = 0x4D;
    public static final int EosAperture_22   = 0x50;
    public static final int EosAperture_25   = 0x53;
    public static final int EosAperture_29   = 0x55;
    public static final int EosAperture_32   = 0x58;
    /**
     * Shutter Speed
     */
    //public static final int SHUTTER_SPEED_BULB = 0x04;
    public static final int SHUTTER_SPEED_BULB = 0x0c;
    public static final int SHUTTER_SPEED_30_SEC = 0x10;
    public static final int SHUTTER_SPEED_25_SEC = 0x13;
	public static final int SHUTTER_SPEED_20_SEC = 0x15;
	public static final int SHUTTER_SPEED_15_SEC = 0x18;
	public static final int SHUTTER_SPEED_13_SEC = 0x1b;
	public static final int SHUTTER_SPEED_10_SEC = 0x1d;
	public static final int SHUTTER_SPEED_8_SEC = 0x20;
	public static final int SHUTTER_SPEED_6_SEC = 0x23;
	public static final int SHUTTER_SPEED_5_SEC = 0x25;
	public static final int SHUTTER_SPEED_4_SEC = 0x28;
	public static final int SHUTTER_SPEED_3_2_SEC = 0x2b;
	public static final int SHUTTER_SPEED_2_5_SEC = 0x2d;
	public static final int SHUTTER_SPEED_2_SEC = 0x30;
	public static final int SHUTTER_SPEED_1_6_SEC = 0x32;
	public static final int SHUTTER_SPEED_1_3_SEC = 0x35;
	public static final int SHUTTER_SPEED_1_SEC = 0x38;
	public static final int SHUTTER_SPEED_0_8_SEC = 0x3b;
	public static final int SHUTTER_SPEED_0_6_SEC = 0x3d;
	public static final int SHUTTER_SPEED_0_5_SEC = 0x40;
	public static final int SHUTTER_SPEED_0_4_SEC = 0x43;
	public static final int SHUTTER_SPEED_0_3_SEC = 0x45;
	public static final int SHUTTER_SPEED_1_4 = 0x48;
	public static final int SHUTTER_SPEED_1_5 = 0x4b;
	public static final int SHUTTER_SPEED_1_6 = 0x4d;
	public static final int SHUTTER_SPEED_1_8 = 0x50;
	public static final int SHUTTER_SPEED_1_10 = 0x53;
	public static final int SHUTTER_SPEED_1_13 = 0x55;
	public static final int SHUTTER_SPEED_1_15 = 0x58;
	public static final int SHUTTER_SPEED_1_20 = 0x5b;
	public static final int SHUTTER_SPEED_1_25 = 0x5d;
	public static final int SHUTTER_SPEED_1_30 = 0x60;
	public static final int SHUTTER_SPEED_1_40 = 0x63;
	public static final int SHUTTER_SPEED_1_50 = 0x65;
	public static final int SHUTTER_SPEED_1_60 = 0x68;
	public static final int SHUTTER_SPEED_1_80 = 0x6b;
	public static final int SHUTTER_SPEED_1_100 = 0x6d;
	public static final int SHUTTER_SPEED_1_125 = 0x70;
	public static final int SHUTTER_SPEED_1_160 = 0x73;
	public static final int SHUTTER_SPEED_1_200 = 0x75;
	public static final int SHUTTER_SPEED_1_250 = 0x78;
	public static final int SHUTTER_SPEED_1_320 = 0x7b;
	public static final int SHUTTER_SPEED_1_400 = 0x7d;
	public static final int SHUTTER_SPEED_1_500 = 0x80;
	public static final int SHUTTER_SPEED_1_640 = 0x83;
	public static final int SHUTTER_SPEED_1_800 = 0x85;
	public static final int SHUTTER_SPEED_1_1000 = 0x88;
	public static final int SHUTTER_SPEED_1_1250 = 0x8b;
	public static final int SHUTTER_SPEED_1_1600 = 0x8d;
	public static final int SHUTTER_SPEED_1_2000 = 0x90;
	public static final int SHUTTER_SPEED_1_2500 = 0x93;
	public static final int SHUTTER_SPEED_1_3200 = 0x95;
	public static final int SHUTTER_SPEED_1_4000 = 0x98;
	public static final int SHUTTER_SPEED_1_5000 = 0x9a;
	public static final int SHUTTER_SPEED_1_6400 = 0x9d;
	public static final int SHUTTER_SPEED_1_8000 = 0xA0;
    
	/**
	 * Eos Exposure Compensation
	 */
	public static final int EXPOSURE_P_5_0d0 = 0x28		; //+5
	public static final int EXPOSURE_P_4_2d30 = 0x25	; //+4 2/30				
	public static final int EXPOSURE_P_4_1d30 = 0x23	; //+4 1/30				
	public static final int EXPOSURE_P_4_0d0 = 0x20		; //+4			
	public static final int EXPOSURE_P_3_2d30 = 0x1d	; // +3 2/30;				
	public static final int EXPOSURE_P_3_1d30 = 0x1b	; // +3 1/30;				
	public static final int EXPOSURE_P_3_0d0 = 0x18		; // +3    0;				
	public static final int EXPOSURE_P_2_2d30 = 0x15	; // +2 2/30;				
	public static final int EXPOSURE_P_2_1d20 = 0x14	; // +2 1/20;				
	public static final int EXPOSURE_P_2_1d30 = 0x13	; // +2 1/30;				
	public static final int EXPOSURE_P_2_0d0 = 0x10		; // +2    0;				
	public static final int EXPOSURE_P_1_2d30 = 0x0d	; // +1 2/30;				
	public static final int EXPOSURE_P_1_1d20 = 0x0c	; // +1 1/20;				
	public static final int EXPOSURE_P_1_1d30 = 0x0b	; // +1 1/30;				
	public static final int EXPOSURE_P_1_0d0 = 0x08		; // +1    ;				
	public static final int EXPOSURE_P_0_2d3 = 0x05		; // +2/3  ;				
	public static final int EXPOSURE_P_0_1d2 = 0x04		; // +1/2  ;				
	public static final int EXPOSURE_P_0_1d3 = 0x03		; // +1/3  ;				
	public static final int EXPOSURE_0 = 0x00			; // 0;			
	public static final int EXPOSURE_N_0_1d3 = 0xfd		; // -1/3  ;				
	public static final int EXPOSURE_N_0_1d2 = 0xfc		; // -1/2  ;				
	public static final int EXPOSURE_N_0_2d3 = 0xfb		; // -2/3  ;				
	public static final int EXPOSURE_N_1_0d0 = 0xf8		; // -1    0;				
	public static final int EXPOSURE_N_1_1d30 = 0xf5	; // -1 1/30;				
	public static final int EXPOSURE_N_1_1d20 = 0xf4	; // -1 1/20;				
	public static final int EXPOSURE_N_1_2d30 = 0xf3	; // -1 2/30;				
	public static final int EXPOSURE_N_2_0d0 = 0xf0		; // -2    0;				
	public static final int EXPOSURE_N_2_1d30 = 0xed	; // -2 1/30;				
	public static final int EXPOSURE_N_2_1d20 = 0xec	; // -2 1/20;				
	public static final int EXPOSURE_N_2_2d30 = 0xeb	; // -2 2/30;				
	public static final int EXPOSURE_N_3_0d0 = 0xe8		; // -3    0;			
	public static final int EXPOSURE_N_3_1d30 = 0xe5	; // -3 1/30;			
	public static final int EXPOSURE_N_3_2d30 = 0xe3	; // -3 2/30;			
	public static final int EXPOSURE_N_4_0d0 = 0xe0		; // -4    0;			
	public static final int EXPOSURE_N_4_1d30 = 0xdd	; // -4 1/30;			
	public static final int EXPOSURE_N_4_2d30 = 0xdb	; // -4 2/30;			
	public static final int EXPOSURE_N_5_0d0 = 0xd8		; // -5    0;
	
	/**
	 * Eos White Balance
	 */
	public static final int AutoWhiteBalance = 0;
	public static final int Daylight = 1;
	public static final int Clouds = 2;
	public static final int Tungsteen = 3;
	public static final int Fluoriscent = 4;
	public static final int Strobe = 5;
	public static final int WhitePaper = 6;
	public static final int Shade = 7;
	
    /**
     * User picture style type
     */
    public static final int PictureStyle_User1			   = 0x21;
    public static final int PictureStyle_User2			   = 0x22;
    public static final int PictureStyle_User3			   = 0x23;

    public static final int PictureStyle_Standard    = 0x81;
    public static final int PictureStyle_Portrait    = 0x82;
    public static final int PictureStyle_Landscape   = 0x83;
    public static final int PictureStyle_Neutral     = 0x84;
    public static final int PictureStyle_Faithful    = 0x85;
    public static final int PictureStyle_Monochrome  = 0x86;

    /**
     * User picture style type
     */
    public static final int EosPropPictureStyleUserTypeUser1			   = 0x21;
    public static final int EosPropPictureStyleUserTypeUser2			   = 0x22;
    public static final int EosPropPictureStyleUserTypeUser3			   = 0x23;

    public static final int EosPropPictureStyleUserTypeStandard    = 0x81;
    public static final int EosPropPictureStyleUserTypePortrait    = 0x82;
    public static final int EosPropPictureStyleUserTypeLandscape   = 0x83;
    public static final int EosPropPictureStyleUserTypeNeutral     = 0x84;
    public static final int EosPropPictureStyleUserTypeFaithful    = 0x85;
    public static final int EosPropPictureStyleUserTypeMonochrome  = 0x86;   
    
    
    /**
     * Image formats
     */
    public static final int ImageFormatEXIF_JPEG                       = 0x3801;
    public static final int ImageFormatTIFF_EP                         = 0x3802;
    public static final int ImageFormatFlashPix                        = 0x3803;
    public static final int ImageFormatBMP                             = 0x3804;
    public static final int ImageFormatCIFF                            = 0x3805;
    public static final int ImageFormatUndefined_0x3806                = 0x3806;
    public static final int ImageFormatGIF                             = 0x3807;
    public static final int ImageFormatJFIF                            = 0x3808;
    public static final int ImageFormatPCD                             = 0x3809;
    public static final int ImageFormatPICT                            = 0x380A;
    public static final int ImageFormatPNG                             = 0x380B;
    public static final int ImageFormatUndefined_0x380C                = 0x380C;
    public static final int ImageFormatTIFF                            = 0x380D;
    public static final int ImageFormatTIFF_IT                         = 0x380E;
    public static final int ImageFormatJP2                             = 0x380F;
    public static final int ImageFormatJPX                             = 0x3810;
    /* ptp v1.1 has only DNG new */
    public static final int ImageFormatDNG                             = 0x3811;
    /* Eastman Kodak extension ancillary format */
    public static final int ImageFormatEK_M3U                          = 0xB002;
    /* Canon extension */
    public static final int ImageFormatCANON_CRW                       = 0xB101;
    public static final int ImageFormatCANON_CRW3                      = 0xB103;
    public static final int ImageFormatCANON_MOV                       = 0xB104;
    /* CHDK specific raw mode */
    public static final int ImageFormatCANON_CHDK_CRW                  = 0xB1FF;
    /* MTP extensions */
    public static final int ImageFormatMTP_MediaCard                   = 0xB211;
    public static final int ImageFormatMTP_MediaCardGroup              = 0xb212;
    public static final int ImageFormatMTP_Encounter                   = 0xb213;
    public static final int ImageFormatMTP_EncounterBox                = 0xb214;
    public static final int ImageFormatMTP_M4A                         = 0xb215;
    public static final int ImageFormatMTP_ZUNEUNDEFINED               = 0xb217;
    public static final int ImageFormatMTP_Firmware                    = 0xb802;
    public static final int ImageFormatMTP_WindowsImageFormat          = 0xb881;
    public static final int ImageFormatMTP_UndefinedAudio              = 0xb900;
    public static final int ImageFormatMTP_WMA                         = 0xb901;
    public static final int ImageFormatMTP_OGG                         = 0xb902;
    public static final int ImageFormatMTP_AAC                         = 0xb903;
    public static final int ImageFormatMTP_AudibleCodec                = 0xb904;
    public static final int ImageFormatMTP_FLAC                        = 0xb906;
    public static final int ImageFormatMTP_SamsungPlaylist             = 0xb909;
    public static final int ImageFormatMTP_UndefinedVideo              = 0xb980;
    public static final int ImageFormatMTP_WMV                         = 0xb981;
    public static final int ImageFormatMTP_MP4                         = 0xb982;
    public static final int ImageFormatMTP_MP2                         = 0xb983;
    public static final int ImageFormatMTP_3GP                         = 0xb984;
    public static final int ImageFormatMTP_UndefinedCollection         = 0xba00;
    public static final int ImageFormatMTP_AbstractMultimediaAlbum     = 0xba01;
    public static final int ImageFormatMTP_AbstractImageAlbum          = 0xba02;
    public static final int ImageFormatMTP_AbstractAudioAlbum          = 0xba03;
    public static final int ImageFormatMTP_AbstractVideoAlbum          = 0xba04;
    public static final int ImageFormatMTP_AbstractAudioVideoPlaylist  = 0xba05;
    public static final int ImageFormatMTP_AbstractContactGroup        = 0xba06;
    public static final int ImageFormatMTP_AbstractMessageFolder       = 0xba07;
    public static final int ImageFormatMTP_AbstractChapteredProduction = 0xba08;
    public static final int ImageFormatMTP_AbstractAudioPlaylist       = 0xba09;
    public static final int ImageFormatMTP_AbstractVideoPlaylist       = 0xba0a;
    public static final int ImageFormatMTP_AbstractMediacast           = 0xba0b;
    public static final int ImageFormatMTP_WPLPlaylist                 = 0xba10;
    public static final int ImageFormatMTP_M3UPlaylist                 = 0xba11;
    public static final int ImageFormatMTP_MPLPlaylist                 = 0xba12;
    public static final int ImageFormatMTP_ASXPlaylist                 = 0xba13;
    public static final int ImageFormatMTP_PLSPlaylist                 = 0xba14;
    public static final int ImageFormatMTP_UndefinedDocument           = 0xba80;
    public static final int ImageFormatMTP_AbstractDocument            = 0xba81;
    public static final int ImageFormatMTP_XMLDocument                 = 0xba82;
    public static final int ImageFormatMTP_MSWordDocument              = 0xba83;
    public static final int ImageFormatMTP_MHTCompiledHTMLDocument     = 0xba84;
    public static final int ImageFormatMTP_MSExcelSpreadsheetXLS       = 0xba85;
    public static final int ImageFormatMTP_MSPowerpointPresentationPPT = 0xba86;
    public static final int ImageFormatMTP_UndefinedMessage            = 0xbb00;
    public static final int ImageFormatMTP_AbstractMessage             = 0xbb01;
    public static final int ImageFormatMTP_UndefinedContact            = 0xbb80;
    public static final int ImageFormatMTP_AbstractContact             = 0xbb81;
    public static final int ImageFormatMTP_vCard2                      = 0xbb82;
    public static final int ImageFormatMTP_vCard3                      = 0xbb83;
    public static final int ImageFormatMTP_UndefinedCalendarItem       = 0xbe00;
    public static final int ImageFormatMTP_AbstractCalendarItem        = 0xbe01;
    public static final int ImageFormatMTP_vCalendar1                  = 0xbe02;
    public static final int ImageFormatMTP_vCalendar2                  = 0xbe03;
    public static final int ImageFormatMTP_UndefinedWindowsExecutable  = 0xbe80;
    public static final int ImageFormatMTP_MediaCast                   = 0xbe81;
    public static final int ImageFormatMTP_Section                     = 0xbe82;
    
    public static final int driveSingleShot = 0x0000;
    public static final int driveContinuous = 0x0001;
    public static final int driveContinuousHighSpeed = 0x0004;
    public static final int driveContinuousLowSpeed = 0x0005;
    public static final int driveTimer10Sec = 0x0010;
    public static final int driveTimer2Sec = 0x0010;
    public static final int driveSingleSilent = 0x0013;
    public static final int driveContinuousSilent = 0x0014;
    
    public static final int pictSFine = 0x00000321;
    public static final int pictSNormal = 0x00000221;
    public static final int pictMFine = 0x00000311;
    public static final int pictMNormal = 0x00000211;
    public static final int pictLFine = 0x00000301;
    public static final int pictLNormal = 0x00000201;
    public static final int pictRaw = 0x00000406;
    public static final int pictRawPlusL = 0x00301406;
    
    public static final int centerWeightedMetering = 0;
    public static final int SpotMetering = 1;//Good
    public static final int AverageMetering = 2;
    public static final int EvaluativeMetering = 3; //Good
    public static final int PartialMetering = 4; //Good
    public static final int CenterWeightedAverageMetering = 5;//Good
    public static final int spotMeteringInterlockedWithAFframeMetering = 6;
    public static final int multiSpotMetering = 7;
    
    
    
    
    
}
