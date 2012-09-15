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

package com.ptplib.usbcamera.eos;

/**
 * EOS event codes
 *
 * @author stefano fornari
 */
public interface EosEventConstants {
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

    
	public static final int PTP_OC_CANON_EOS_Zoom				= 0x9158;
	public static final int PTP_OC_CANON_EOS_ZoomPosition		= 0x9159;
	public static final int PTP_OC_CANON_EOS_DoAf				= 0x9154;
    
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
