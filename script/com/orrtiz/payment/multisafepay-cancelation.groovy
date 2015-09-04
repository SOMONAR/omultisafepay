
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

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;

String systemResourceId="omultisafepay"
String resource = "omultisafepay-UiLabels";
String resourceErr = "omultisafepay-ErrorUiLabels";
String commonResource = "CommonUiLabels";
Locale locale = (Locale) context.get("locale");

logPrefix = "in " + systemResourceId + "-cancel.groovy: "

Debug.logInfo(logPrefix + " --------------------------",module);

request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "MultisafePayEvents.PaymentTransactionCanceled", locale));
//set the returned transactionid to the orderId
orderId = parameters.transactionid
request.setAttribute("orderId", orderId);

Debug.logInfo(logPrefix + " --------------------------",module);