 If you get following exception during the execution of the application,

  ```  
javax.net.ssl.SSLHandshakeException: 
   sun.security.validator.ValidatorException: PKIX path building failed: 
   sun.security.provider.certpath.SunCertPathBuilderException: 
   unable to find valid certification path to requested target
 
Caused by: sun.security.validator.ValidatorException: 
   PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: 
   unable to find valid certification path to requested target
 
Caused by: sun.security.provider.certpath.SunCertPathBuilderException: 
   unable to find valid certification path to requested target
  ```

 The reason is that, we are working with QRadar and Resilient that have self signed certificates, which are absent in your local keystore.
 
 So inorder to solve this, please follow the below steps:
 
  * Download the code from this [link](https://github.com/azakordonets/InstallCert/blob/master/src/InstallCert.java) and name it as `InstallCert.java`.
  * Open a command terminal and navigate to the `InstallCert.java` on your local system and compile using `javac InstallCert.java`.
  * Now get a self signed certificate for QRadar using the command `java InstallCert [YOUR-QRADAR-IPADDRESS]`
    ```
    For eg: java InstallCert 192.168.xxx.xxx
    ```
  * Now get a self signed certificate for Resilient using the command `java InstallCert [YOUR-RESILIENT-IPADDRESS]`
    ```
    For eg: java InstallCert 192.168.xxx.xxx
    ```
   * This will generate a `jssecacerts` file. Cut and paste this file into `C:\<location> \Java\jre1.8.0_181\lib\security\`.
   * The application should work fine now.
