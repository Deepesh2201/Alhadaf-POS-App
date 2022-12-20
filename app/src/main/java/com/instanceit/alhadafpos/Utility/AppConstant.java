package com.instanceit.alhadafpos.Utility;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class AppConstant {

    //server url
    public static final String BASE_URL = "http://development.alhadafrange.com/pos/api/v1.0/";

    public static final String LOGIN_URL = BASE_URL + "login.php";
    public static final String LOGOUT_URL = BASE_URL + "logout.php";
    public static final String DEVICE_URL = BASE_URL + "device.php";
    public static final String MASTER_URL = BASE_URL + "master.php";
    public static final String STORE_ORDER_URL = BASE_URL + "storeorder.php";
    public static final String MEMBERSHIP_ORDER_URL = BASE_URL + "membershiporder.php";
    public static final String MEMBER_URL = BASE_URL + "member.php";
    public static final String RETURN_ITEM_URL = BASE_URL + "returnitem.php";
    public static final String OPERATION_FLOW_URL = BASE_URL + "operationflow.php";
    public static final String USERRIGHTS_URL = BASE_URL + "userrights.php";
    public static final String LANGUAGE_URL = BASE_URL + "language.php";
    public static final String SERVICE_ORDER_URL = BASE_URL + "serviceorder.php";
    public static final String STORE_ISSUE_ITEM_URL = BASE_URL + "storeissueitem.php";
    public static final String STORE_MEMBERSHIP_ORDER_URL = BASE_URL + "membershiporder.php";
    public static final String REPORT_URL = BASE_URL + "report.php";

    public static final String CONSTANT_KEY = "7ed03ebe32559e0b45c915dfa8ad2582";
    public static final String PLATFORM = "5";  // 2-Android, 3-iOS,5-pos
    public static String ISS = "alhadafpos-pos-android";
    public static final String UNQKEY = "uniqkey"; // get from login service

    public static final String KEY = "key";
    public static final String UID = "uid";
    public static final String UTYPEID = "utypeid";
    public static final String PERSONNAME = "personname";
    public static final String CONTACT = "contact";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String FULLNAME = "fullname";
    public static final String ADLOGIN = "adlogin";
    public static final String YEARID = "yearid";
    public static final String YEARNAME = "yearname";
    public static final String ACTIVEYEARID = "activeyearid";
    public static final String UDISCOUNT = "udiscount";


    public static final String STORE_ID = "selected store id";
    public static final String STORE_NAME = "selected store name";
    public static final String CARTARRAY = "cart array";

    public static final String ITEMCARTARRAY = "item cart array";


    public static final String PNRNO = "pnr no";
    public static final String CUS_NAME = "customername";


    public static final String ISSKIP = "return boolean skip or not :  use without login";
    public static final String ISLOGING = "isloging : user logged in or not check";


    public static final String USERLIST = "userlist";
    public static final String TITLE_MENULIST = "menu list title";
    public static final String DASH_ALL_RIGHTS = "alldashright";
    public static final String DASHBOARDLIST = "dashboard user rights list";


    public static final String INTERNET_CONNECTION_MESSAGE = "Make sure network connection is ON.";
    public static final String TIME_OUT = "Parsing error! Please try again after some time!!";
    public static final String GENERIC_ERROR = "Something Went Wrong! Please try Again.";

    public static final String PREFERENCE_NAME = "MY LOGIN";
    public static final String PREFERENCE_KEY_USERINFO = "userinfo";


    public static final String ACTION = "action";
    public static final String ACTION_LISTING = "listing";
    public static final String ACTION_LISTING_RIGHTS = "listing_rights";
    public static final String ACTION_LOGIN = "Login";
    public static final String ACTION_SIGNUP = "Registration";
    public static final String BG_SERVICE_TIME = "backgorund service call time";

    public static final int PERMISSION_SETTING = 12;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    public static final String NOTIFICATION_SHARED_PREF = "notification shared pref";
    public static final String COUNTER_SHARED_PREF = "counter";

    //Menu
//    public static final String TXT_APPMENU_STORE = "Select store";
    //   public static final String TXT_APPMENU_PURCHASE_ORDER = "Create Order";
//    public static final String TXT_APPMENU_PURCHASE_PACKAGE = "Purchase Package";

    //dynamic array
    public static final String TXT_APPMENU_MEMBERSHIP = "Membership";//purchase packages
    public static final String TXT_APPMENU_STORE_ORDER = "Store Order"; //create order
    public static final String TXT_APPMENU_STORE = "Store";// store selection
    public static final String TXT_RANGE_RELEASE = "Range Release";// store selection
    public static final String TXT_APPMENU_ORDER_HISTORY = "Order History";
    public static final String TXT_APPMENU_INVENTARY_RETURN = "Returnable Item";//returnable item
    public static final String TXT_APPMENU_SERVICE_ORDER = "Service Order";//Service Order
    public static final String TXT_APPMENU_LOGOUT = "Log Out";
    public static final String TXT_APPMENU_ISSUE_SERVICE_ORDER = "Issue Service Order";
    public static final String TXT_APPMENU_SALE_REPORT = "Sale Report";
    public static final String TXT_APPMENU_ITEM_SALE_REPORT = "Item Sale Summary Report";
    public static final String TXT_APPMENU_MEMBERSHIP_SALE_REPORT = "Membership Sale Summary Report";


    public static final String ISSELECTEDPRINOP = "printing option selection";
    public static final String printer_id = "printer_id";


    public static final String PAYMENTLIST = "added payment list array";
    public static final String TEMPCART = "tempcart";

    public static final String LAST_ADDED_CUST_CONTACT = "last visited customers contact number";

    public static final String NAME_LOGIN = "user name login";


    //    Bottom navigation position
    public static final int BOTTOM_NAV_HOME = 1;
    public static final int BOTTOM_NAV_TICKET_HISTORY = 2;
    public static final int BOTTOM_NAV_MY_ACCOUNT = 3;
    public static final int BOTTOM_NAV_MORE = 4;

    public static final String ISUPDATENOTNOW = "update not now click handle dialog hide show";

    public static final String UPDATE_SERVICE_ORDER_DATA = "update Service order data";
    public static final String SERVICE_ORDER_ACTION = "update or insert";
    public static final String UPDATE_SERVICE_ORDER_ACTION = "update";
    public static final String INSERT_SERVICE_ORDER_ACTION = "insert";

}