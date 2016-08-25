<!--- © 2015 Copyright Somonar B.V. 
 <!-- This page is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nd/4.0/">Creative Commons Attribution-NoDerivatives 4.0 International License</a>-->


![OFBiz](http://ofbiz.apache.org/images/logo.png "Apache OFBiz")&nbsp;![MultiSafePay](https://www.multisafepay.com/images/multilogo.gif "MultiSafepay")

# omultisafepay
3rd party Payment Provider Integration Solution for MultiSafepay&trade; as a separate and optional hot-deploy component. This component enables you to configure your MultiSafepay integration and provides templates for inclusion in your OFBiz&trade; web store.

For more information on MultiSafepay, visit [their site, here](http://www.multiSafepay.com).

**MultiSafepay works with following acquirers:**

![American Express](https://www.multisafepay.com/fileadmin/user_upload/amex-teaser.png "American Express")
![BanContact](https://www.multisafepay.com/fileadmin/user_upload/bancontact-teaser.png "BanContact")
![Bank Transfer](https://www.multisafepay.com/fileadmin/_processed_/csm_banktransfer-nw_ebd56e0d6f.png "Bank Transfer")
![Direct Debit](https://www.multisafepay.com/fileadmin/_processed_/csm_direct-debit-nw_4675b19c13.png "Direct Debit")
![GiftCarts](https://www.multisafepay.com/fileadmin/_processed_/csm_giftcards-nw_65d7099f7d.png "Gift Cards")
![GiroPay](https://www.multisafepay.com/fileadmin/user_upload/giropay-teaser.png "GiroPay")
![iDeal](https://www.multisafepay.com/fileadmin/user_upload/ideal-teaser.png "iDeal")&nbsp;
![Klarna](https://cdn.klarna.com/1.0/shared/image/generic/logo/sv_se/basic/blue-black.png?width=200 "Klarna")&nbsp;
![Maestro](https://www.multisafepay.com/fileadmin/user_upload/maestro-teaser.png "Maestro")
![Mastercard](https://www.multisafepay.com/fileadmin/user_upload/mastercard-teaser.png "Mastercard")
![PayAfterDelivery](https://www.multisafepay.com/fileadmin/_processed_/csm_payafterdelivery-nw_357ce0a537.png "Pay After Delivery")
![PayPal](https://www.multisafepay.com/fileadmin/user_upload/paypal-teaser.png "PayPal")
![Sofort Banking](https://www.multisafepay.com/fileadmin/user_upload/sofort-teaser.png "Sofort Banking")
![Visa](https://www.multisafepay.com/fileadmin/user_upload/visa-teaser.png "Visa")

##Countries available
MultiSafepay is available in following countries:
Austria, Belgium, Denmark, Finland, France, Germany, Italy, Netherlands, Norway, Poland, Spain, Sweden, United Kingdom.

##Development
Just put the following in the svn:externals properties of the hot-deploy folder of your OFBiz implementation for a checkout:

omultisafepay         https://github.com/ORRTIZ/omultisafepay/trunk

After having updated the hot-deploy folder (to execute the checkout from the repository), you'll need to build OFBiz again (./ant build) and load the seed, seed-initial and  - optionally- demo datasets.

## Implementation
Instructions on how to implement can be found here: https://github.com/ORRTIZ/omultisafepay/wiki/How-to-implement
##Issues
For an overview of open and closed issue, see: [https://github.com/ORRTIZ/omultisafepay/issues](https://github.com/ORRTIZ/omultisafepay/issues)



## License
© 2015 Copyright Somonar B.V.

<a rel="license" href="http://creativecommons.org/licenses/by-nd/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nd/4.0/88x31.png" /></a><br />This page is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nd/4.0/">Creative Commons Attribution-NoDerivatives 4.0 International License</a>.

