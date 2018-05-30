package com.astix.Common;

import android.net.Uri;

import java.io.File;

public class CommonInfo
{


	// Its for Live  Path on 194 server for SFA



	public static File imageF_savedInstance=null;
	public static String imageName_savedInstance=null;
	public static String clickedTagPhoto_savedInstance=null;
	public static Uri uriSavedImage_savedInstance=null;

	public static String userDate="0";
	public static String pickerDate="0";

	public static String imei="";
	public static String SalesQuoteId="BLANK";
	public static String quatationFlag="";
	public static String fileContent="";
	public static String prcID="NULL";
	public static String newQuottionID="NULL";
	public static String globalValueOfPaymentStage="0"+"_"+"0"+"_"+"0";

	public static String WebManageDSRUrl="http://103.20.212.194/LTACE/pda/frmIMEImanagement.aspx";
	public static String WebPageUrl="http://103.20.212.194/ltace/Mobile/frmRouteTracking.aspx";

	public static String WebPageUrlDataReport="http://103.20.212.194/ltace/Mobile/fnSalesmanWiseSummaryRpt.aspx";

	public static String WebServicePath="http://103.20.212.194/WebServiceAndroidLTFoodsLive/Service.asmx";
	public static String VersionDownloadPath="http://103.20.212.194/downloads/";
	public static String VersionDownloadAPKName="GTSOFieldOperations.apk";
	//public static String VersionDownloadAPKName="LTACESOSFA.apk"; // this name change according to varun sir mail


	public static String DATABASE_NAME = "DbSFAApp";

	public static int AnyVisit = 0;

	public static int DATABASE_VERSIONID = 67;      // put this field value based on value in table on the server
	public static String AppVersionID = "1.8";   // put this field value based on value in table on the server
	public static int Application_TypeID = 4; //1=Parag Store Mapping,2=Parag SFA Indirect,3=Parag SFA Direct

	public static String OrderSyncPath="http://103.20.212.194/ReadXML_LTFoodsLive/DefaultSOSFA.aspx";

	public static String OrderSyncPathDistributorMap="http://103.20.212.194/ReadXML_LTFoodsLive/DefaultSOSFA.aspx";

	public static String ImageSyncPath="http://103.20.212.194/ReadXML_LTFoodsImagesLive/Default.aspx";

	public static String OrderTextSyncPath="http://103.20.212.194/ReadTxtFileForLTFoodsLive/default.aspx";

	public static String InvoiceSyncPath="http://103.20.212.194/ReadXML_LTFoodInvoiceLive/Default.aspx";

	public static String DistributorSyncPath="http://103.20.212.194/ReadXML_LTFoodsSFADistributionLive/Default.aspx";

	public static String URLImageLinkToViewStoreOverWebProtal="http://103.20.212.194/ltace/Reports/frmPDAImgsLive.aspx";

	public static String OrderSyncPathSO="http://103.20.212.194/ReadXML_LTFoodsLive/DefaultSO.aspx";

	public static String OrderXMLFolder="LTACESFAXml";
	public static String ImagesFolder="LTACESFAImages";
	public static String ImagesFolderServer="LTACESFAImagesServer";
	public static String VideoFolder="VideoLTFOODS";
	public static String TextFileFolder="LTACETextFile";
	public static String InvoiceXMLFolder="LTACEInvoiceXml";
	public static String FinalLatLngJsonFile="LTACESFAFinalLatLngJson";

	public static String DistStockXMLFolder="LTACEDistStockXml";

	public static String AppLatLngJsonFile="LTACESFALatLngJson";

	public static int DistanceRange=3000;
	public static String SalesPersonTodaysTargetMsg="";
	public static final String Preference="LTFoodsPrefrence";
	public static final String AttandancePreference="LTFoodsAttandancePreference";
	public static final String DistributorXMLFolder="LTFoodsDistributorXMLFolder";
	public static int flgAllRoutesData=1;
	public static int flgLTFoodsSOOnlineOffLine=0;
	public static int CoverageAreaNodeID=0;
	public static int CoverageAreaNodeType=0;
	public static int FlgDSRSO=0;

	public static int SalesmanNodeId=0;
	public static int SalesmanNodeType=0;
	public static int flgDataScope=0;
	public static int flgNewStoreORStoreValidation=0;
	public static int DayStartClick=0;











	// Its for Staging  Path on 194 server for SFA




/*


	public static File imageF_savedInstance=null;
	public static String imageName_savedInstance=null;
	public static String clickedTagPhoto_savedInstance=null;
	public static Uri uriSavedImage_savedInstance=null;

	public static String userDate="0";
	public static String pickerDate="0";

	public static String imei="";
	public static String SalesQuoteId="BLANK";
	public static String quatationFlag="";
	public static String fileContent="";
	public static String prcID="NULL";
	public static String newQuottionID="NULL";
	public static String globalValueOfPaymentStage="0"+"_"+"0"+"_"+"0";

    public static String WebManageDSRUrl="http://103.20.212.194/LTACE_Staging/pda/frmIMEImanagement.aspx";
	public static String WebPageUrl="http://103.20.212.194/ltace_staging/Mobile/frmRouteTracking.aspx";

	public static String WebPageUrlDataReport="http://103.20.212.194/ltace_staging/Mobile/fnSalesmanWiseSummaryRpt.aspx";

	public static String WebServicePath="http://103.20.212.194/WebServiceAndroidLTFoodsStaging/Service.asmx";
	public static String VersionDownloadPath="http://103.20.212.194/downloads/";
	public static String VersionDownloadAPKName="GTSOFieldOperationsStaging.apk";
	//public static String VersionDownloadAPKName="LTACESOSFAStaging.apk"; // this name change according to varun sir mail

	public static String DATABASE_NAME = "DbSFAApp";


	public static int AnyVisit = 0;

	public static int DATABASE_VERSIONID = 64;      // put this field value based on value in table on the server
	public static String AppVersionID = "1.8";   // put this field value based on value in table on the server
	public static int Application_TypeID = 4; //1=Parag Store Mapping,2=Parag SFA Indirect,3=Parag SFA Direct

	public static String OrderSyncPath="http://103.20.212.194/ReadXML_LTFoodsStaging/DefaultSOSFA.aspx";

	public static String OrderSyncPathDistributorMap="http://103.20.212.194/ReadXML_LTFoodsStaging/DefaultSODistributorMapping.aspx";

	public static String ImageSyncPath="http://103.20.212.194/ReadXML_LTFoodsImagesStaging/Default.aspx";

	public static String OrderTextSyncPath="http://103.20.212.194/ReadTxtFileForLTFoodsStaging/default.aspx";

	public static String InvoiceSyncPath="http://103.20.212.194/ReadXML_LTFoodInvoiceStaging/Default.aspx";

	public static String DistributorSyncPath="http://103.20.212.194/ReadXML_LTFoodsSFADistributionStaging/Default.aspx";

	public static String URLImageLinkToViewStoreOverWebProtal="http://103.20.212.194/ltace/Reports/frmPDAImgsStaging.aspx";

	public static String OrderSyncPathSO="http://103.20.212.194/ReadXML_LTFoodsStaging/DefaultSO.aspx";

	public static String OrderXMLFolder="LTACESFAXml";
	public static String ImagesFolder="LTACESFAImages";
	public static String TextFileFolder="LTACETextFile";
	public static String InvoiceXMLFolder="LTACEInvoiceXml";
	public static String FinalLatLngJsonFile="LTACESFAFinalLatLngJson";

	public static String DistStockXMLFolder="LTACEDistStockXml";

	public static String AppLatLngJsonFile="LTACESFALatLngJson";

	public static int DistanceRange=3000;
	public static String SalesPersonTodaysTargetMsg="";
	public static final String Preference="LTFoodsPrefrence";
	public static final String AttandancePreference="LTFoodsAttandancePreference";
	public static final String DistributorXMLFolder="LTFoodsDistributorXMLFolder";
	public static int flgAllRoutesData=1;
	public static int flgLTFoodsSOOnlineOffLine=0;
	public static int CoverageAreaNodeID=0;
	public static int CoverageAreaNodeType=0;
	public static int FlgDSRSO=0;

	public static int SalesmanNodeId=0;
	public static int SalesmanNodeType=0;
	public static int flgDataScope=0;
	public static int flgNewStoreORStoreValidation=0;
	public static int DayStartClick=0;

*/





// Its for Test  Path on 194 server for SFA

/*


	public static File imageF_savedInstance=null;
	public static String imageName_savedInstance=null;
	public static String clickedTagPhoto_savedInstance=null;
	public static Uri uriSavedImage_savedInstance=null;

	public static String userDate="0";
	public static String pickerDate="0";

	public static String imei="";
	public static String SalesQuoteId="BLANK";
	public static String quatationFlag="";
	public static String fileContent="";
	public static String prcID="NULL";
	public static String newQuottionID="NULL";
	public static String globalValueOfPaymentStage="0"+"_"+"0"+"_"+"0";

	public static String WebManageDSRUrl="http://103.20.212.194/LTACE_test/pda/frmIMEImanagement.aspx";
	public static String WebPageUrl="http://103.20.212.194/ltace_test/Mobile/frmRouteTracking.aspx";

	public static String WebPageUrlDataReport="http://103.20.212.194/ltace_test/Mobile/fnSalesmanWiseSummaryRpt.aspx";

	public static String WebServicePath="http://103.20.212.194/WebServiceAndroidLTFoodsTest/Service.asmx";
	public static String VersionDownloadPath="http://103.20.212.194/downloads/";
	public static String VersionDownloadAPKName="GTSOFieldOperationsTest.apk";
	//public static String VersionDownloadAPKName="LTACESOSFATest.apk"; // this name change according to varun sir mail

	public static String DATABASE_NAME = "DbSFAApp";
	public static String DATABASE_NAME_SO = "DbSOSFAApp";

	public static int AnyVisit = 0;

	public static int DATABASE_VERSIONID = 50;      // put this field value based on value in table on the server
	public static String AppVersionID = "1.5";   // put this field value based on value in table on the server

	public static int Application_TypeID = 4; //1=Parag Store Mapping,2=Parag SFA Indirect,3=Parag SFA Direct

	public static String OrderSyncPath="http://103.20.212.194/ReadXML_LTFoodsTest/DefaultSOSFA.aspx";

	public static String OrderSyncPathDistributorMap="http://103.20.212.194/ReadXML_LTFoodsTest/DefaultSODistributorMapping.aspx";

	public static String ImageSyncPath="http://103.20.212.194/ReadXML_LTFoodsImagesTest/Default.aspx";

	public static String OrderTextSyncPath="http://103.20.212.194/ReadTxtFileForLTFoodsTest/default.aspx";

	public static String InvoiceSyncPath="http://103.20.212.194/ReadXML_LTFoodInvoiceTest/Default.aspx";

	public static String DistributorSyncPath="http://103.20.212.194/ReadXML_LTFoodsSFADistributionTest/Default.aspx";

	public static String URLImageLinkToViewStoreOverWebProtal="http://103.20.212.194/ltace/Reports/frmPDAImgsTest.aspx";

	public static String OrderSyncPathSO="http://103.20.212.194/ReadXML_LTFoodsTest/DefaultSO.aspx";

	public static String OrderXMLFolder="LTACESFAXml";
	public static String ImagesFolder="LTACESFAImages";
	public static String TextFileFolder="LTACETextFile";
	public static String InvoiceXMLFolder="LTACEInvoiceXml";
	public static String FinalLatLngJsonFile="LTACESFAFinalLatLngJson";

	public static String DistStockXMLFolder="LTACEDistStockXml";

	public static String AppLatLngJsonFile="LTACESFALatLngJson";

	public static int DistanceRange=3000;
	public static String SalesPersonTodaysTargetMsg="";
	public static final String Preference="LTFoodsPrefrence";
	public static final String AttandancePreference="LTFoodsAttandancePreference";
	public static final String DistributorXMLFolder="LTFoodsDistributorXMLFolder";
	public static int flgAllRoutesData=1;
	public static int flgLTFoodsSOOnlineOffLine=0;
	public static int CoverageAreaNodeID=0;
	public static int CoverageAreaNodeType=0;
	public static int FlgDSRSO=0;

	public static int SalesmanNodeId=0;
	public static int SalesmanNodeType=0;
	public static int flgDataScope=0;
	public static int flgNewStoreORStoreValidation=0;
	public static int DayStartClick=0;
*/






	// Its for Development  Path on 194 server for SFA


     /*   public static Uri uriSavedImage_savedInstance=null;

	    public static String userDate="0";
	    public static String pickerDate="0";

	    public static String imei="";
	    public static String SalesQuoteId="BLANK";
	    public static String quatationFlag="";
	    public static String fileContent="";
	    public static File imageF_savedInstance=null;
	    public static String imageName_savedInstance=null;
	    public static String clickedTagPhoto_savedInstance=null;
	    public static String prcID="NULL";
	    public static String newQuottionID="NULL";
	    public static String globalValueOfPaymentStage="0"+"_"+"0"+"_"+"0";

        public static String WebManageDSRUrl="http://103.20.212.194/LTACE_Dev/pda/frmIMEImanagement.aspx";
	    public static String WebPageUrl="http://103.20.212.194/ltace_dev/Mobile/frmRouteTracking.aspx";

	    public static String WebPageUrlDataReport="http://103.20.212.194/ltace_dev/Mobile/fnSalesmanWiseSummaryRpt.aspx";

	    public static String WebServicePath="http://103.20.212.194/WebServiceAndroidLTFoodsDevelopment/Service.asmx";
	    public static String VersionDownloadPath="http://103.20.212.194/downloads/";
		//public static String VersionDownloadAPKName="LTACESOSFADev.apk";
		public static String VersionDownloadAPKName="GTSOFieldOperationsDev.apk";
	   //public static String VersionDownloadAPKName="LTACESOSFADev.apk"; // this name change according to varun sir mail


		public static String DATABASE_NAME = "DbSFAApp";
	    public static String DATABASE_NAME_SO = "DbSOSFAApp";

		public static int AnyVisit = 0;

		public static int DATABASE_VERSIONID = 25;      // put this field value based on value in table on the server
		public static String AppVersionID = "1.2";   // put this field value based on value in table on the server
		public static int Application_TypeID = 4; //1=Parag Store Mapping,2=Parag SFA Indirect,3=Parag SFA Direct

		public static String OrderSyncPath="http://103.20.212.194/ReadXML_LTFoodsDevelopment/DefaultSOSFA.aspx";

	    public static String OrderSyncPathDistributorMap="http://103.20.212.194/ReadXML_LTFoodsDevelopment/DefaultSODistributorMapping.aspx";
		public static String ImageSyncPath="http://103.20.212.194/ReadXML_LTFoodsImagesDevelopment/Default.aspx";

		public static String OrderTextSyncPath="http://103.20.212.194/ReadTxtFileForLTFoodsDevelopment/default.aspx";

		public static String InvoiceSyncPath="http://103.20.212.194/ReadXML_LTFoodInvoiceDevelopment/Default.aspx";

	    public static String DistributorSyncPath="http://103.20.212.194/ReadXML_LTFoodsSFADistributionDevelopment/Default.aspx";

	    public static String URLImageLinkToViewStoreOverWebProtal="http://103.20.212.194/ltace/Reports/frmPDAImgsDev.aspx";

	    public static String OrderSyncPathSO="http://103.20.212.194/ReadXML_LTFoodsDevelopment/DefaultSO.aspx";

		public static String OrderXMLFolder="LTACESFAXml";
		public static String ImagesFolder="LTACESFAImages";
	    public static String ImagesFolderServer="LTACESFAImagesServer";
	    public static String VideoFolder="VideoLTFOODS";
	    public static String TextFileFolder="LTACETextFile";
	    public static String InvoiceXMLFolder="LTACEInvoiceXml";
		public static String FinalLatLngJsonFile="LTACESFAFinalLatLngJson";

        public static String DistStockXMLFolder="LTACEDistStockXml";

		public static String AppLatLngJsonFile="LTACESFALatLngJson";

		public static int DistanceRange=3000;
	    public static String SalesPersonTodaysTargetMsg="";
	    public static final String Preference="LTFoodsPrefrence";
    	public static final String AttandancePreference="LTFoodsAttandancePreference";
	    public static final String DistributorXMLFolder="LTFoodsDistributorXMLFolder";
		public static int flgAllRoutesData=1;
    	public static int flgLTFoodsSOOnlineOffLine=0;
	    public static int CoverageAreaNodeID=0;
	    public static int CoverageAreaNodeType=0;
	    public static int FlgDSRSO=0;

	    public static int SalesmanNodeId=0;
	    public static int SalesmanNodeType=0;
	    public static int flgDataScope=0;
        public static int flgNewStoreORStoreValidation=0;
	    public static int DayStartClick=0;
*/


	// Its for Test Release  Path on 194 server for SFA


	/*public static Uri uriSavedImage_savedInstance=null;

	public static String userDate="0";
	public static String pickerDate="0";

	public static String imei="";
	public static String SalesQuoteId="BLANK";
	public static String quatationFlag="";
	public static String fileContent="";
	public static File imageF_savedInstance=null;
	public static String imageName_savedInstance=null;
	public static String clickedTagPhoto_savedInstance=null;
	public static String prcID="NULL";
	public static String newQuottionID="NULL";
	public static String globalValueOfPaymentStage="0"+"_"+"0"+"_"+"0";

	public static String WebManageDSRUrl="http://103.20.212.194/ltace_testRelease/pda/frmIMEImanagement.aspx";
	public static String WebPageUrl="http://103.20.212.194/ltace_testRelease/Mobile/frmRouteTracking.aspx";

	public static String WebPageUrlDataReport="http://103.20.212.194/ltace_testrelease/Mobile/fnSalesmanWiseSummaryRpt.aspx";


	public static String WebServicePath="http://103.20.212.194/WebServiceAndroidLTFoodsTestRelease/Service.asmx";
	public static String VersionDownloadPath="http://103.20.212.194/downloads/";
	//public static String VersionDownloadAPKName="LTACESOSFADev.apk";
	public static String VersionDownloadAPKName="GTSOFieldOperationsTestRelease.apk";
	//public static String VersionDownloadAPKName="LTACESOSFADev.apk"; // this name change according to varun sir mail


	public static String DATABASE_NAME = "DbSFAApp";
	public static String DATABASE_NAME_SO = "DbSOSFAApp";

	public static int AnyVisit = 0;

	public static int DATABASE_VERSIONID = 78;      // put this field value based on value in table on the server
	public static String AppVersionID = "1.15";   // put this field value based on value in table on the server
	public static int Application_TypeID = 4; //1=Parag Store Mapping,2=Parag SFA Indirect,3=Parag SFA Direct

	public static String OrderSyncPath="http://103.20.212.194/ReadXML_LTFoodsTestRelease/DefaultSOSFA.aspx";

	public static String OrderSyncPathDistributorMap="http://103.20.212.194/ReadXML_LTFoodsTestRelease/DefaultSODistributorMapping.aspx";
	public static String ImageSyncPath="http://103.20.212.194/ReadXML_LTFoodsImagesTestRelease/Default.aspx";

	public static String OrderTextSyncPath="http://103.20.212.194/ReadTxtFileForLTFoodsTestRelease/default.aspx";

	public static String InvoiceSyncPath="http://103.20.212.194/ReadXML_LTFoodInvoiceTestRelease/Default.aspx";

	public static String DistributorSyncPath="http://103.20.212.194/ReadXML_LTFoodsSFADistributionTestRelease/Default.aspx";

	public static String URLImageLinkToViewStoreOverWebProtal="http://103.20.212.194/ltace_TestRelease/Reports/frmPDAImgsDev.aspx";

	public static String OrderSyncPathSO="http://103.20.212.194/ReadXML_LTFoodsTestRelease/DefaultSO.aspx";

	public static String OrderXMLFolder="LTACESFAXml";
	public static String ImagesFolder="LTACESFAImages";
	public static String ImagesFolderServer="LTACESFAImagesServer";
	public static String VideoFolder="VideoLTFOODS";
	public static String TextFileFolder="LTACETextFile";
	public static String InvoiceXMLFolder="LTACEInvoiceXml";
	public static String FinalLatLngJsonFile="LTACESFAFinalLatLngJson";

	public static String DistStockXMLFolder="LTACEDistStockXml";

	public static String AppLatLngJsonFile="LTACESFALatLngJson";

	public static int DistanceRange=3000;
	public static String SalesPersonTodaysTargetMsg="";
	public static final String Preference="LTFoodsPrefrence";
	public static final String AttandancePreference="LTFoodsAttandancePreference";
	public static final String DistributorXMLFolder="LTFoodsDistributorXMLFolder";
	public static int flgAllRoutesData=1;
	public static int flgLTFoodsSOOnlineOffLine=0;
	public static int CoverageAreaNodeID=0;
	public static int CoverageAreaNodeType=0;
	public static int FlgDSRSO=0;

	public static int SalesmanNodeId=0;
	public static int SalesmanNodeType=0;
	public static int flgDataScope=0;
	public static int flgNewStoreORStoreValidation=0;
	public static int DayStartClick=0;*/


}
