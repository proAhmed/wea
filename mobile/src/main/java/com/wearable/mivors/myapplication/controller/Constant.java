package com.wearable.mivors.myapplication.controller;

/**
 * Created by mivors on 29/01/17.
 */

public class Constant {

    public static final String URL = "https://ecoadev-test.bi.us2.oraclecloud.com/xmlpserver/services/ExternalReportWSSService?WSDL";
    public static final String METHOD_URL = "<soap:Envelope xmlns:pub=\"http://xmlns.oracle.com/oxp/service/PublicReportService\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Body><pub:getReportSampleData><pub:reportAbsolutePath>/BI Reports/HCM Reports/Final Vaildation HCM Report.xdo</pub:reportAbsolutePath></pub:getReportSampleData></soap:Body></soap:Envelope>";
    public static final String USERNAME = "fin_impl";
    public static final String PASSWORD = "Bazai#2016";
    public static final String NAME_SPACE = "ecoadev-test.bi.us2.oraclecloud.com";
    public static final String METHOD_NAME  = "getReportSampleDataResponse";

    public static final String METHOD_ACTION = "http://xmlns.oracle.com/oxp/service/PublicReportService/ExternalReportWSSService/getReportSampleData";
  //  public static final String MAIN_URL = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/incidents";
    public static final String MAIN_URL = "https://mivors--tst1.custhelp.com/";


}
