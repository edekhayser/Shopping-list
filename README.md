# Shopping-list

To setup Android Studio:

"<h2>Getting Started</h2>

Google Glass runs a modified version of Android, and so much of the code and tools you will use are the same.

Install Android Studio by following the instructions in <a href="http://www.raywenderlich.com/78574/android-tutorial-for-beginners-part-1">this Android tutorial</a>. 

After the IDE installs, the main menu will come up. Right now, Android Studio has not downloaded all the tools needed for Glass development. 

To download these tools, press <em>Configure > SDK Manager</em>. Under <em>Android 4.4.2 (API 19)</em>, choose <em>SDK Platform</em> and <em>Glass Development Kit Preview</em>. Everything else can be deselected.

Press <em>Install 2 packages...</em> and accept the license for each tool. Once the downloading finishes, close out of the SDK manager.”

To run the project:

"<h2>Running your Glass Activity</h2>

Before you can actually run your activity on your Glass, you first need to enable testing. On your Glass, go to <em>Settings > Device Info > Turn on debug</em>. This will let you test apps on your Glass.

Plug your Google Glass into your computer's USB port. You will hear a dinging noise come from the Glass to confirm that the Glass is connected properly.

In Android Studio, press the Run button on the toolbar, which is the right arrow to the left of the bug icon.

An "Edit Configuration" screen will appear. On this screen, make sure that your settings are the same as below:

<ul>
	<li><em>Module:</em> app</li>
	<li><em>Package:</em> Deploy default APK</li>
	<li><em>Activity:</em> Launch default Activity</li>
	<li><em>Target Device:</em> USB Device</li>
</ul>

Press Run. If a pop-up appears saying "Configuration is still incorrect", select <em>Continue Anyway</em>.

Android Studio should build and run the app on your device. To see the app in action, tap your Glass to wake it up, tap again to open up the list of apps, and tap one last time on the item named "Shopping List". Your app is now running on your Google Glass!"
