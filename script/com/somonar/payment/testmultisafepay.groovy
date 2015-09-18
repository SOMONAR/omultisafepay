
<!--
(C) Copyright 2015 Somonar B.V.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 -->
package com.orrtiz.payment

//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.util.LinkedHashMap;
//import java.util.Locale;
//import java.util.Map;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.ofbiz.base.conversion.JSONConverters.JSONToMap;
import org.ofbiz.base.lang.JSON;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.HttpClient;
import org.ofbiz.base.util.HttpClientException;
import org.ofbiz.base.util.SSLUtil;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.util.EntityUtilProperties;
//import org.ofbiz.entity.Delegator;
//import org.ofbiz.entity.GenericEntityException;
//import org.ofbiz.entity.GenericValue;
//import org.ofbiz.product.store.ProductStoreWorker;

String systemResourceId="omultisafepay"
String resource = "omultisafepay-UiLabels";
String resourceErr = "omultisafepay-ErrorUiLabels";
String commonResource = "CommonUiLabels";
Locale locale = (Locale) context.get("locale");
logPrefix = "in testmultisafepay.groovy: "
Debug.logInfo(logPrefix + " --------------------------",module);

Debug.logInfo(logPrefix + " set generics",module);

String systemResourceId="omultisafepay"
String resource = "omultisafepay-UiLabels";
String resourceErr = "omultisafepay-ErrorUiLabels";
String commonResource = "CommonUiLabels";
Locale locale = (Locale) context.get("locale");
Debug.logInfo(logPrefix + " locale = " + locale,module);

Debug.logInfo(logPrefix + " get configuration variables",module);

String apiKey = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'apiKey.test', delegator);
Debug.logInfo(logPrefix + " apiKey = " + apiKey,module);

String hostProtocol = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'HostProtocol.test', delegator);
Debug.logInfo(logPrefix + " hostProtocol = " + hostProtocol,module);

String hostName = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'HostName.test', delegator);
Debug.logInfo(logPrefix + " hostName = " + hostName,module);

String apiRequest = EntityUtilProperties.getSystemPropertyValue(systemResourceId, 'apiRequest.test', delegator);
Debug.logInfo(logPrefix + " hostProtocol = " + apiRequest,module);

String requestUrl = hostProtocol + '://' + hostName + apiRequest
Debug.logInfo(logPrefix + " requestUrl = " + requestUrl,module);

state = parameters.state
Debug.logInfo(logPrefix + " starting with state = " + state,module);

String typeId = parameters.typeId
Debug.logInfo(logPrefix + " typeId = " + typeId,module);

String orderId = parameters.orderId
Debug.logInfo(logPrefix + " orderId = " + orderId,module);

String currencyUomId = parameters.currencyUomId
Debug.logInfo(logPrefix + " currencyUomId = " + currencyUomId,module);

String amount = parameters.amount
Debug.logInfo(logPrefix + " amount = " + amount,module);

String description = parameters.description
Debug.logInfo(logPrefix + " description = " + description,module);

String jsonString = "{" +
        "\"type\":\"" + typeId +"\" ," +
        "\"order_id\": \"" + orderId + "\" ," +
        "\"currency\":\"" + currencyUomId + "\"," +
        "\"locale\":\"" + locale + "\"," +
        "\"amount\": \"" + amount + "\" ," +
        "\"description\":\"" + description + "\"}";
Debug.logInfo(logPrefix + " jsonString = " + jsonString,module);


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
    Debug.logInfo(logPrefix + " code = " + code ,module);
    Debug.logInfo(logPrefix + " retResponse = " + retResponse ,module);
    JSON jsonObject = JSON.from(retResponse)
    Debug.logInfo(logPrefix + " json = " + jsonObject,module);
    JSONToMap jsonMap = new JSONToMap();
    Map<String, Object> userMap = jsonMap.convert(jsonObject);
    paymentUrl = userMap.data.payment_url
    Debug.logInfo(logPrefix + " paymentUrl = " + paymentUrl,module);
    
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
    Debug.logInfo(logPrefix + " code = " + code ,module);
}

Debug.logInfo(logPrefix + " --------------------------",module);