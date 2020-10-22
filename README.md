# automation-tests2

[EXTERNAL]

1. Instruction steps detailing the various activities and action required for framework installation and test scripting are as below
Note: Please use homebrew & npm to install all required software’s
a. Check whether brew is installed or not
b. Open terminal and type "which brew"
If no response, install brew by issuing the below command in terminal
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

#Install the Maven & Node using brew
1) brew install Maven
2) brew install node
3) Install Java 1.8 JDK

#Install Appium using brew
4) npm install -g appium
5) npm install wd

Continue this step only if we are using latest OS and Xcode > 7
# only works for iOS 9.
6) brew install libimobiledevice --HEAD # install from HEAD to get important updates
7) brew install ideviceinstaller

#for iOS > 9.0
8) brew install carthage
9) npm install -g iOS-deploy
10) gem install xcpretty
Move the Xcode to Application folder
Run sudo Xcode-select —reset

#Framework Installation
11) Unzip the downloaded folder [knome]
12) Through terminal navigate to the download folder
13) Run mvn install - this will pull all the dependency jars to the .m2 repo [duration depends on bandwidth]
14) Copy from project folder “DeltaFrame/dep/xuggle" dependency to ".m2/repository/xuggle"
15) Copy from project folder "DeltaFrame/dep/boofcv" dependency to ".m2/repository/org/boofcv"
16) Open xcode, create a sample project and launch a simulator (Note the version and device type)
17) Substitute the simulator value to test.properties

Note: Only available versions are to be updated in test.properties.

Appium will be triggered in command line by framework itself.
18) For simulator we would require (.app & bundle id)
19) For real device we would require (.ipa & bundle id, profile of real device configurations )

#Trigger Testing:
22. from the navigated path type ./bin/run-integration-tests.sh -t@google -r -bios -c
23. Refer help for more details ./bin/run-integration-tests.sh -h

#Test Case design [Inspecting element]
• Open appium tool
• Click on mac icon
• Enter the app path in app path location and leave the other setting as it is.
• Launch the appium
• Click on magnifier icon
• Appium inspector tool will open
• Click on any element it will give the xpath of the element

#Framework flow
Create a new feature file and associated step definition and create test scripts with the Appium Inspector
• run-integration-tests.sh
• Driver Factory
• Base Stef definition
• Common Step definition
• Test case step definitions
• Extend report
• Cucumber report

Please feel to connect with me in case of any queries.


Sent from my iPhone
