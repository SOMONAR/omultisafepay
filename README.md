![OFBiz](http://ofbiz.apache.org/images/logo.png "Apache OFBiz")&nbsp;![MultiSafePay](https://www.multisafepay.com/images/multilogo.gif "MultiSafepay")

# omultisafepay
3rd party Payment Provider Integration Solution for MultiSafepay&trade; as a separate and optional hot-deploy component. This component enables you to configure your MultiSafepay integration and provides templates for inclusion in your OFBiz&trade; web store

##Development
Just put the following in the svn:externals properties of the hot-deploy folder of your OFBiz implementation for a checkout:

omultisafepay         https://github.com/ORRTIZ/omultisafepay/trunk

After having updated the hot-deploy folder (to execute the checkout from the repository), you'll need to build OFBiz again (./ant build) and load the seed, seed-initial and  - optionally- demo datasets.

