package com.ritvi.kaajneeti.testing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.pojo.payment.PaymentPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PayUMoneyIntegration extends AppCompatActivity {
    /**
     * Adding WebView as setContentView
     */
    WebView webView;

    /**
     * Context for Activity
     */
    Context activity;
    /**
     * Order Id
     * To Request for Updating Payment Status if Payment Successfully Done
     */
    int mId; //Getting from Previous Activity
    /**
     * Required Fields
     */
    // Test Variables
    /*
    private String mMerchantKey = "FCyqqZ";
    private String mSalt = "sfBpGA8E";
    private String mBaseURL = "https://test.payu.in";
    */

    // Final Variables
    private String mMerchantKey = "xit8dtfA";
    private String mSalt = "dlr4AbGetK";
    private String mBaseURL = "https://secure.payu.in";


    private String mAction = ""; // For Final URL
    private String mTXNId; // This will create below randomly
    private String mHash; // This will create below randomly
    private String mProductInfo = "Food Items"; //Passing String only
    private String mFirstName; // From Previous Activity
    private String mEmailId; // From Previous Activity
    private double mAmount; // From Previous Activity
    private String mPhone; // From Previous Activity
    private String mServiceProvider = "payu_paisa";
    private String mSuccessUrl = "success.com";
    private String mFailedUrl = "failure.com";
    private String transfer_leader_id = "";
    PaymentPOJO paymentPOJO;

    boolean isFromOrder;
    /**
     * Handler
     */
    Handler mHandler = new Handler();

    /**
     * @param savedInstanceState
     */
    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled", "JavascriptInterface", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);

        /**
         * Setting WebView to Screen
         */
        setContentView(R.layout.activity_pay_umoney_integration);

        /**
         * Creating WebView
         */
        webView = (WebView) findViewById(R.id.webView);

        /**
         * Context Variable
         */
        activity = getApplicationContext();

        /**
         * Actionbar Settings
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
//        // enabling action bar app icon and behaving it as toggle button
//        ab.setHomeButtonEnabled(true);
//        ab.setTitle(getString(R.string.title_activity_online_payment));

        /**
         * Getting Intent Variables...
         */
        paymentPOJO= (PaymentPOJO) getIntent().getSerializableExtra("paymentPOJO");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            this.transfer_leader_id = bundle.getString("transfer_leader_id");
//            mFirstName = bundle.getString("name");
//            mEmailId = bundle.getString("email");
//            mAmount = bundle.getDouble("amount");
//            mPhone = bundle.getString("phone");
//            mId = bundle.getInt("id");

            mFirstName = "sunil";
            mEmailId = "sundroid1993@gmail.com";
            mAmount = 10;
            mPhone = "9876543210";
            mId = 122;

            isFromOrder = bundle.getBoolean("isFromOrder");

            Log.i(TagUtils.getTag(), "" + mFirstName + " : " + mEmailId + " : " + mAmount + " : " + mPhone);

            /**
             * Creating Transaction Id
             */
            Random rand = new Random();
            String randomString = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
            mTXNId = hashCal("SHA-256", randomString).substring(0, 20);

            mAmount = new BigDecimal(mAmount).setScale(0, RoundingMode.UP).intValue();

            /**
             * Creating Hash Key
             */
            mHash = hashCal("SHA-512", mMerchantKey + "|" +
                    mTXNId + "|" +
                    mAmount + "|" +
                    mProductInfo + "|" +
                    mFirstName + "|" +
                    mEmailId + "|||||||||||" +
                    mSalt);

            /**
             * Final Action URL...
             */
            mAction = mBaseURL.concat("/_payment");
            callpaymentSuccessfullAPI();
            /**
             * WebView Client
             */
            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    Toast.makeText(activity, "Oh no! " + error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReceivedSslError(WebView view,
                                               SslErrorHandler handler, SslError error) {
                    Toast.makeText(activity, "SSL Error! " + error, Toast.LENGTH_SHORT).show();
                    handler.proceed();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageFinished(WebView view, String url) {

                    if (url.equals(mSuccessUrl)) {
//                        Intent intent = new Intent(PayUMoneyIntegration.this, PaymentStatusActivity.class);
//                        intent.putExtra("status", true);
//                        intent.putExtra("transaction_id", mTXNId);
//                        intent.putExtra("id", mId);
//                        intent.putExtra("isFromOrder", isFromOrder);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
                        Log.d(TagUtils.getTag(), "txnID:-" + mTXNId + " , id:-" + mId);
                    } else if (url.equals(mFailedUrl)) {
//                        Intent intent = new Intent(PayUMoneyIntegration.this, PaymentStatusActivity.class);
//                        intent.putExtra("status", false);
//                        intent.putExtra("transaction_id", mTXNId);
//                        intent.putExtra("id", mId);
//                        intent.putExtra("isFromOrder", isFromOrder);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);

                        Log.d(TagUtils.getTag(), "txnID:-" + mTXNId + " , id:-" + mId);
                    }
                    super.onPageFinished(view, url);
                }
            });

            webView.setVisibility(View.VISIBLE);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setCacheMode(2);
            webView.getSettings().setDomStorageEnabled(true);
            webView.clearHistory();
            webView.clearCache(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setUseWideViewPort(false);
            webView.getSettings().setLoadWithOverviewMode(false);
            webView.addJavascriptInterface(new PayUJavaScriptInterface(PayUMoneyIntegration.this), "PayUMoney");

            /**
             * Mapping Compulsory Key Value Pairs
             */
            Map<String, String> mapParams = new HashMap<>();

            mapParams.put("key", mMerchantKey);
            mapParams.put("txnid", mTXNId);
            mapParams.put("amount", String.valueOf(mAmount));
            mapParams.put("productinfo", mProductInfo);
            mapParams.put("firstname", mFirstName);
            mapParams.put("email", mEmailId);
            mapParams.put("phone", mPhone);
            mapParams.put("surl", mSuccessUrl);
            mapParams.put("furl", mFailedUrl);
            mapParams.put("hash", mHash);
            mapParams.put("service_provider", mServiceProvider);

            webViewClientPost(webView, mAction, mapParams.entrySet());
        } else {
            Toast.makeText(activity, "Something went wrong, Try again.", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void callpaymentSuccessfullAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("payment_gateway_id",paymentPOJO.getPaymentGatewayId()));
        nameValuePairs.add(new BasicNameValuePair("transaction_id",mTXNId));
        nameValuePairs.add(new BasicNameValuePair("payment_to_user_profile_id",Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("transaction_date", UtilityFunction.getCurrentDate()));
        nameValuePairs.add(new BasicNameValuePair("transaction_amount","10"));
        nameValuePairs.add(new BasicNameValuePair("transaction_shipping_amount","10"));
        nameValuePairs.add(new BasicNameValuePair("transaction_status","1"));
        nameValuePairs.add(new BasicNameValuePair("debit_or_credit","1"));
        nameValuePairs.add(new BasicNameValuePair("comments","Trans Checking"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                Log.d(TagUtils.getTag(),"trans response:-"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_TRANS_API",true).execute(WebServicesUrls.SAVE_PAYMENT_TRANSACTIONS);
    }

    /**
     * Posting Data on PayUMoney Site with Form
     *
     * @param webView
     * @param url
     * @param postData
     */
    public void webViewClientPost(WebView webView, String url,
                                  Collection<Map.Entry<String, String>> postData) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head>");
        sb.append("<body onload='form1.submit()'>");
        sb.append(String.format("<form id='form1' action='%s' method='%s'>", url, "post"));

        for (Map.Entry<String, String> item : postData) {
            sb.append(String.format("<input name='%s' type='hidden' value='%s' />", item.getKey(), item.getValue()));
        }
        sb.append("</form></body></html>");

        Log.d("TagUtils.getTag()", "webViewClientPost called: " + sb.toString());
        webView.loadData(sb.toString(), "text/html", "utf-8");
    }

    /**
     * Hash Key Calculation
     *
     * @param type
     * @param str
     * @return
     */
    public String hashCal(String type, String str) {
        byte[] hashSequence = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashSequence);
            byte messageDigest[] = algorithm.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1)
                    hexString.append("0");
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException NSAE) {
        }
        return hexString.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onPressingBack();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        onPressingBack();
//    }

    /**
     * On Pressing Back
     * Giving Alert...
     */
    private void onPressingBack() {

        final Intent intent;

//        if(isFromOrder)
//            intent = new Intent(PayUMoneyIntegration.this, ProductInCartList.class);
//        else
//            intent = new Intent(PayUMoneyIntegration.this, MainActivity.class);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PayUMoneyActivity.this);
//
//        // Setting Dialog Title
//        alertDialog.setTitle("Warning");
//
//        // Setting Dialog Message
//        alertDialog.setMessage("Do you cancel this transaction?");
//
//        // On pressing Settings button
//        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//                startActivity(intent);
//            }
//        });
//
//        // on pressing cancel button
//        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        // Showing Alert Message
//        alertDialog.show();
    }

    public class PayUJavaScriptInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        PayUJavaScriptInterface(Context c) {
            mContext = c;
        }

        public void success(long id, final String paymentId) {
            mHandler.post(new Runnable() {

                public void run() {
                    mHandler = null;
                    Toast.makeText(PayUMoneyIntegration.this, "Payment Successfully.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}

//
//    String MERCHENT_KEY = "xit8dtfA";
//    String MERCHENT_SALT = "dlr4AbGetK";
//    String MERCHENT_ID = "6175876";
//    String TEST_AUTH_HEADER = "L3hbeVHY7LR6e7dKpy+NWj2n2K5/J9Sdjg65SI6e98w=";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pay_umoney_integration);
//
////        String hashSequence = MERCHENT_KEY + "|txnid|50|salt" + MERCHENT_SALT;
////        String serverCalculatedHash = hashCal("SHA-512", hashSequence);
//
//        Random rand = new Random();
//        String randomString = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
//        String mTXNId = hashCal("SHA-256", randomString).substring(0, 20);
//        String mHash = hashCal("SHA-512", MERCHENT_KEY + "|" +
//                mTXNId + "|" +
//                500 + "|" +
//                "funding" + "|" +
//                "sunil" + "|" +
//                "sundroid@gmail.com" + "|||||||||||" +
//                MERCHENT_SALT);
//
//        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
//                PayUmoneySdkInitializer.PaymentParam.Builder();
//        builder.setAmount(50)                          // Payment amount
//                .setTxnId(mTXNId)                                             // Transaction ID
//                .setPhone("9873738969")                                           // User Phone number
//                .setProductName("Funding")                   // Product Name or description
//                .setFirstName("Sunil")                              // User First name
//                .setEmail("sundroid@gmail.com")                                            // User Email ID
//                .setUdf1("")
//                .setUdf2("")
//                .setUdf3("")
//                .setUdf4("")
//                .setUdf5("")
//                .setsUrl("failure.com")                    // Success URL (surl)
//                .setfUrl("success.com")                     //Failure URL (furl)
//                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
//                .setKey(MERCHENT_KEY)                        // Merchant key
//                .setMerchantId(MERCHENT_ID);             // Merchant ID
//
//
//        PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();
////set the hash
//        paymentParam.setMerchantHash(mHash);
//
//        PayUmoneyFlowManager.startPayUMoneyFlow(
//                paymentParam,
//                this,
//                R.style.AppTheme,
//                false);
//    }
//
//    public static String hashCal(String type, String hashString) {
//        StringBuilder hash = new StringBuilder();
//        MessageDigest messageDigest = null;
//        try {
//            messageDigest = MessageDigest.getInstance(type);
//            messageDigest.update(hashString.getBytes());
//            byte[] mdbytes = messageDigest.digest();
//            for (byte hashByte : mdbytes) {
//                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return hash.toString();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//// Result Code is -1 send from Payumoney activity
//        Log.d(TagUtils.getTag(), "request code " + requestCode + " resultcode " + resultCode);
//        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
//            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
//            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
//                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
////Success Transaction
//                } else {
////Failure Transaction
//                }
//// Response from Payumoney
//                String payuResponse = transactionResponse.getPayuResponse();
//                Log.d(TagUtils.getTag(), "payuResponse:-" + payuResponse);
//// Response from SURl and FURL
//                String merchantResponse = transactionResponse.getTransactionDetails();
//                Log.d(TagUtils.getTag(), "merchantResponse:-" + merchantResponse);
//            }
//        }
//    }
//}
