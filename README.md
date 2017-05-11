# asset-Manager-reactv2-test

Selenium WebDriver automation test for asset-manager-reactv2 project

Languages/Libraries/Frameworks used: JAVA

Prerequisites for running the test using Selenium WebDriver are:

    Install JDK (Java development kit) in your system

    Gradle Build Tool
        $ brew install gradle

    GeckoDriver Running test on Firefox
        brew install geckodriver or npm install geckodriver

    chromedriver running the test on Google Chrome
        https://chromedriver.storage.googleapis.com/index.html?path=2.29/
# Install asset-manager-reactv2 
https://github.com/neerajk1208/asset-manager-reactv2

 Start asset-manager-reactv2  server 
 
    $ cd asset-manager-reactv2
 
    $ npm install
 
    $ npm start

# Running the test

    Clone or download  https://github.com/GWOLDE11/asset-Manager-reactv2-test.git

    $ cd asset-Manager-reactv2-test

    # running the test on Firefox

        $ gradle clean test -DbrowserType=firefox

    # running the test on google chrome

        $ gradle clean test -DbrowserType=googlechrome
