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
![Maestro](https://www.multisafepay.com/fileadmin/user_upload/maestro-teaser.png "Maestro")
![Mastercard](https://www.multisafepay.com/fileadmin/user_upload/mastercard-teaser.png "Mastercard")
![PayAfterDelivery](https://www.multisafepay.com/fileadmin/_processed_/csm_payafterdelivery-nw_357ce0a537.png "Pay After Delivery")
![PayPal](https://www.multisafepay.com/fileadmin/user_upload/paypal-teaser.png "PayPal")
![Sofort Banking](https://www.multisafepay.com/fileadmin/user_upload/sofort-teaser.png "Sofort Banking")
![Visa](https://www.multisafepay.com/fileadmin/user_upload/visa-teaser.png "Visa")

##Development
Just put the following in the svn:externals properties of the hot-deploy folder of your OFBiz implementation for a checkout:

omultisafepay         https://github.com/ORRTIZ/omultisafepay/trunk

After having updated the hot-deploy folder (to execute the checkout from the repository), you'll need to build OFBiz again (./ant build) and load the seed, seed-initial and  - optionally- demo datasets.

