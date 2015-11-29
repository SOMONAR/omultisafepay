/*******************************************************************************
 * (C) Copyright 2015 Somonar B.V.
 * Licensed under the Apache License under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.somonar.payment.multisafepay;

//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.util.LinkedHashMap;
//import java.util.Locale;
//import java.util.Map;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.Collection;

import org.ofbiz.base.conversion.JSONConverters.JSONToMap;
import org.ofbiz.base.lang.JSON;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.HttpClient;
import org.ofbiz.base.util.HttpClientException;
import org.ofbiz.base.util.SSLUtil;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtilProperties;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.party.contact.ContactHelper;
//import org.ofbiz.entity.Delegator;
//import org.ofbiz.entity.GenericEntityException;
//import org.ofbiz.entity.GenericValue;
//import org.ofbiz.product.store.ProductStoreWorker;

logPrefix = "in multisafepay.groovy: "
Debug.logInfo(logPrefix + "--------------------------",module);

Debug.logInfo(logPrefix + "set generics",module);

String systemResourceId="omultisafepay"
String resource = "omultisafepay-UiLabels";
String resourceErr = "omultisafepay-ErrorUiLabels";
String commonResource = "CommonUiLabels";
Locale locale = (Locale) context.get("locale");
Debug.logInfo(logPrefix + "locale = " + locale,module);

Debug.logInfo(logPrefix + "get configuration variables",module);

String apiKey = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'apiKey.test', delegator);
Debug.logInfo(logPrefix + "apiKey = " + apiKey,module);

String hostProtocol = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'HostProtocol.test', delegator);
Debug.logInfo(logPrefix + "hostProtocol = " + hostProtocol,module);

String hostName = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'HostName.test', delegator);
Debug.logInfo(logPrefix + "hostName = " + hostName,module);

String apiRequest = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'apiRequest.test', delegator);
Debug.logInfo(logPrefix + "hostProtocol = " + apiRequest,module);

String defaultRedirectUrl = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'defaultRedirectUrl', delegator);
Debug.logInfo(logPrefix + "hostProtocol = " + defaultRedirectUrl ,module);

String defaultNotficationUrl = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'defaultNotficationUrl', delegator);
Debug.logInfo(logPrefix + "hostProtocol = " + defaultNotficationUrl ,module);

String defaultCancelUrl = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'defaultCancelUrl', delegator);
Debug.logInfo(logPrefix + "hostProtocol = " + defaultCancelUrl ,module);

String requestUrl = hostProtocol + '://' + hostName + apiRequest
Debug.logInfo(logPrefix + "requestUrl = " + requestUrl,module);

state = parameters.state
Debug.logInfo(logPrefix + "starting with state = " + state,module);

String typeId = parameters.typeId
Debug.logInfo(logPrefix + "typeId = " + typeId,module);

String orderId = parameters.orderId
Debug.logInfo(logPrefix + "orderId = " + orderId,module);

// get the order header
try {
    orderHeader = from("OrderHeader").where("orderId", orderId).queryOne();
} catch (GenericEntityException e) {
    Debug.logError(e, "Cannot get the order header for order: " + orderId, module);
    request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "MultisafePayEvents.problemsGettingOrderHeader", locale));
    return "error";
}
// get the order total
BigDecimal bd =  new BigDecimal("100.0");
bd.setScale(0);
bd.stripTrailingZeros();
String orderTotalLong = orderHeader.getBigDecimal("grandTotal").multiply(bd).toPlainString();
int index_point = orderTotalLong.indexOf(".");
orderTotalLong = orderTotalLong.substring(0, index_point);
Debug.logInfo(logPrefix + "orderTotalLong = " + orderTotalLong,module);
String orderTotal = orderHeader.getBigDecimal("grandTotal").toPlainString();
Debug.logInfo(logPrefix + "orderTotal = " + orderTotal,module);


// get some details from the order
OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
String currencyUomId = orh.getCurrency()
Debug.logInfo(logPrefix + "currencyUomId = " + currencyUomId,module);

String amount = parameters.amount
Debug.logInfo(logPrefix + "amount = " + amount,module);

String description = parameters.description
Debug.logInfo(logPrefix + "description = " + description,module);

String productStoreId = orh.getProductStoreId()
Debug.logInfo(logPrefix + "productStoreId = " + productStoreId,module);

// get the urls to pass along
notificationUrl = 'notificationUrl';
redirectUrl = 'redirectUrl';
cancelUrl = 'cancelUrl';

//building the payment Options
webSiteUrl = (String) context.get("webSiteUrl");
Debug.logInfo(logPrefix + "webSiteUrl = " + webSiteUrl,module);
String sru = parameters._SERVER_ROOT_URL_;
Debug.logInfo(logPrefix + "_SERVER_ROOT_URL_ = " + sru,module);

String cp = parameters._CONTROL_PATH_;
Debug.logInfo(logPrefix + "_CONTROL_PATH_ = " + cp,module);

notificationUrl = sru + cp +"/" + defaultNotficationUrl;

redirectUrl = sru + cp +"/" + defaultRedirectUrl;

cancelUrl = sru + cp +"/" + defaultCancelUrl;

String jsonPaymentOptions = 
        "{" +
        "\"notification_url\":\"" + notificationUrl +"\" ," +
        "\"redirect_url\":\"" + redirectUrl +"\" ," +
        "\"cancel_url\":\"" + cancelUrl + "\"" +
        "}";
Debug.logInfo(logPrefix + "jsonPaymentOptions = " + jsonPaymentOptions,module);

placingParty = orh.getPlacingParty();
//Debug.logInfo(logPrefix + "placingParty = " + placingParty,module);
billToParty = orh.getBillToParty();
//Debug.logInfo(logPrefix + "billToParty = " + billToParty,module);
// building the customer

// email address
String emailAddress = null;
Collection emCol = ContactHelper.getContactMech(placingParty, "PRIMARY_EMAIL", "EMAIL_ADDRESS", false);
if (UtilValidate.isEmpty(emCol)) {
    emCol = ContactHelper.getContactMech(placingParty, null, "EMAIL_ADDRESS", false);
}
if (!UtilValidate.isEmpty(emCol)) {
    GenericValue emVl = (GenericValue) emCol.iterator().next();
    if (emVl != null) {
        emailAddress = emVl.getString("infoString");
    }
} else {
    emailAddress = "";
}
Debug.logInfo(logPrefix + "emailAddress: " + emailAddress, module);

// shipping address
String address1 = null;
String address2 = null;
String city = null;
String state = null;
String zipCode = null;
String country = null;
Collection adCol = ContactHelper.getContactMech(placingParty, "SHIPPING_LOCATION", "POSTAL_ADDRESS", false);
if (UtilValidate.isEmpty(adCol)) {
    adCol = ContactHelper.getContactMech(placingParty, null, "POSTAL_ADDRESS", false);
}
if (!UtilValidate.isEmpty(adCol)) {
    GenericValue adVl = (GenericValue) adCol.iterator().next();
    if (adVl != null) {
        GenericValue addr = null;
        try {
            addr = adVl.getDelegator().findOne("PostalAddress", UtilMisc.toMap("contactMechId",
                    adVl.getString("contactMechId")), false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        if (addr != null) {
            address1 = addr.getString("address1");
            address2 = addr.getString("address2");
            city = addr.getString("city");
            state = addr.getString("stateProvinceGeoId");
            zipCode = addr.getString("postalCode");
            country = addr.getString("countryGeoId");
            if (address2 == null) {
                address2 = "";
            }
        }
    }
}
Debug.logInfo(logPrefix + "address1: " + address1, module);
Debug.logInfo(logPrefix + "address2: " + address2, module);
Debug.logInfo(logPrefix + "city: " + city, module);
Debug.logInfo(logPrefix + "state: " + state, module);
Debug.logInfo(logPrefix + "zipCode: " + zipCode, module);
Debug.logInfo(logPrefix + "country: " + country, module);

// phone number
String phoneNumber = null;
Collection phCol = ContactHelper.getContactMech(placingParty, "PHONE_HOME", "TELECOM_NUMBER", false);
if (UtilValidate.isEmpty(phCol)) {
    phCol = ContactHelper.getContactMech(placingParty, null, "TELECOM_NUMBER", false);
}
if (!UtilValidate.isEmpty(phCol)) {
    GenericValue phVl = (GenericValue) phCol.iterator().next();
    if (phVl != null) {
        GenericValue tele = null;
        try {
            tele = phVl.getDelegator().findOne("TelecomNumber", UtilMisc.toMap("contactMechId",
                    phVl.getString("contactMechId")), false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        if (tele != null) {
            phoneNumber = ""; // reset the string
            String cc = tele.getString("countryCode");
            String ac = tele.getString("areaCode");
            String nm = tele.getString("contactNumber");
            if (UtilValidate.isNotEmpty(cc)) {
                phoneNumber = phoneNumber + cc + "-";
            }
            if (UtilValidate.isNotEmpty(ac)) {
                phoneNumber = phoneNumber + ac + "-";
            }
            phoneNumber = phoneNumber + nm;
        } else {
            phoneNumber = "";
        }
    }
}
Debug.logInfo(logPrefix + "phoneNumber: " + phoneNumber, module);
String jsonCustomer =
        "{" +
        "\"locale\": \"" + locale + "\" ," +
        "\"first_name\": \"" + placingParty.firstName + "\" ," +
        "\"last_name\": \"" + placingParty.lastName + "\" ," +
        "\"address1\": \"" + address1 + "\" ," +
        "\"address2\": \"" + address2 + "\" ," +
        "\"house_number\": \"" + address1 + "\" ," +
        "\"zip_code\": \"" + zipCode + "\" ," +
        "\"city\": \"" + city + "\" ," +
        "\"state\": \"" + state + "\" ," +
        "\"country\": \"" + country + "\" ," +
        "\"email\": \"" + emailAddress + "\"" +
        "}"
Debug.logInfo(logPrefix + "jsonCustomer = " + jsonCustomer,module);

String webSiteId = orh.getWebSiteId();
Debug.logInfo(logPrefix + "webSiteId = " + webSiteId,module);
googleAccount = from("WebAnalyticsConfig").where("webSiteId", webSiteId, "webAnalyticsTypeId", "GOOGLE_ANALYTICS" ).queryOne()
Debug.logInfo(logPrefix + "googleAccount = " + googleAccount,module);
// building the analytics

    String jsonAnalytics =
        "{" +
        "\"account\": \"" + googleAccount.webAnalyticsCode + "\"" +
        "}"
Debug.logInfo(logPrefix + "jsonAnalytics = " + jsonAnalytics,module);



// building the jsonString
String jsonString = 
        "{" +
        "\"type\":\"" + "redirect" +"\" ," +
        "\"order_id\": \"" + orderId + "\" ," +
        "\"currency\":\"" + currencyUomId + "\" ," +
        "\"locale\":\"" + locale + "\" ," +
        "\"amount\": \"" + orderTotalLong + "\" ," +
        "\"description\": \"" + "test re " + orderId + "\" ," +
        "\"payment_options\": " + jsonPaymentOptions  + " ," +
        "\"customer\": " + jsonCustomer  + " ," +
        "\"google_analytics\": " + jsonAnalytics +
        "}"
Debug.logInfo(logPrefix + "jsonString = " + jsonString,module);


HttpClient http = new HttpClient(requestUrl);
http.setHostVerificationLevel(SSLUtil.HOSTCERT_NO_CHECK);
http.setAllowUntrusted(true);
http.setDebug(true);

Map <String, String> headers = new LinkedHashMap <String, String>();
headers.put("api_key", apiKey);
headers.put("contenttype", "application/json");
headers.put("contentlength", "122");

http.setHeaders(headers);
http.setContentType("application/json");
http.setKeepAlive(true);

String retResponse = null;
try {
    retResponse = http.post(jsonString)
}
catch (Exception e) {
    request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "problemsConnectingWithMultiSafepay", locale));
    response.sendRedirect("test");
    return "error";
}

int code = 0;
try {
    code = http.getResponseCode();
}
catch (HttpClientException e) {
    Debug.logError(e, "multisafepay response code not receiving", module);
    request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "problemsgettingresponsecodeMultiSafepay", locale));
    return "error";
}
if (code == 200){
    Debug.logInfo(logPrefix + "code = " + code ,module);
    Debug.logInfo(logPrefix + "retResponse = " + retResponse ,module);
    JSON jsonObject = JSON.from(retResponse)
    Debug.logInfo(logPrefix + "json = " + jsonObject,module);
    JSONToMap jsonMap = new JSONToMap();
    Map<String, Object> userMap = jsonMap.convert(jsonObject);
    paymentUrl = userMap.data.payment_url
    Debug.logInfo(logPrefix + "paymentUrl = " + paymentUrl,module);
    
    try {
        response.sendRedirect(paymentUrl);
    }
    catch (IOException e) {
        Debug.logError(e, "multisafepay redirect problem", module);
        request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "problemsredirectMultiSafepay", locale));
        return "error";
    }
}
else {
    Debug.logInfo(logPrefix + "code = " + code ,module);
}

Debug.logInfo(logPrefix + "--------------------------",module);
