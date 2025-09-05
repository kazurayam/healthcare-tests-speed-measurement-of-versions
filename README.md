# healthcare-tests speed measurement of Katalon Studio versions

# Problem to solve

In August 2025 when Katalon Studio v10.3.x is current, in the Katalon Community,
people started discussing that the latest version of Katalon Studio runs slower than before.

- https://forum.katalon.com/t/katalon-10-3-1-smart-wait-is-now-much-much-slower/180887

I thought that I should find which exact version of Katalon Studio got slower.
Nobody has measured it. So I will do that.

## Environment

- using a machine Mac Book Air, 2-18, 1.6GHz Intel Core i5, 17GB memory, macOS Sonoma14.7.6
- used Chrome browser v139.0.7258.155
- ChromeDriver is updated to the latest using "Tools > Update WebDrivers > Chrome"
- I disabled "Project > Katalon Platform > Integration > Enable Katalon Platform Integration"
- I set "Project > Execution > Default wait for element timeout (in seconds)" to have 30 seconds
- I set "Project > Execution > WebUI > Default Smart Wait" to be Enabled

## Measurement Criteria

- I used https://github.com/katalon-studio-samples/healthcare-tests as testbed
- I will check as many Katalon Studio versions as possible to measure the time taken to run the [Test Suites/healthcare-tests - TS_RegressionTest](https://github.com/katalon-studio-samples/healthcare-tests/blob/master/Test%20Suites/healthcare-tests%20-%20TS_RegressionTest.ts).
- The value of "time taken" was found in the top-right corder of the Log Viewer when the test suite finished

![time taken](https://kazurayam.github.io/healthcare-tests-speed-measurement-of-versions/images/TimeTakenInTheLogViewer.png)

## Result

### Comparing versions

I ran the "Test Suites/healthcare-tests -TS_RegressionTest" at least 3 times for each KS version. This is meant to collect enough number of samples.

|KS version| time taken |
|----------|------------|
|10.3.1    | 1min 21s   |
|10.3.1    | 1min 21s   |
|10.3.1    | 1min 24s   |
|10.3.0    | 1min 27s   |
|10.3.0    | 1min 26s   |
|10.3.0    | 1min 32s   |
|10.3.0    | 1min 32s   |
|10.2.4    | 85.948s = 1min 25.948s |
|10.2.4    | 89.028s = 1min 29.028s |
|10.2.4    | 84.740s = 1min 24.740s |
|10.0.0    | 84.270s = 1min 24.270s |
|10.0.0    | 84.270s = 1min 24.270s |
|10.0.0    | 85.543s = 1min 25.534s |
|10.0.0    | 85.285s = 1min 25.285s |
| ^^^^^^^^ | ^^^^^^^^^^ |
|9.7.6     | 72.118s = 1min 12.118s |
|9.7.6     | 71.211s = 1min 11.211s |
|9.7.6     | 69.830s = 1min 09.830s |
|9.0.0     | 75.949s = 1min 15.949s |
|9.0.0     | 72.187s = 1min 12.187s |
|9.0.0     | 73.694s = 1min 13.794s |
|8.6.9     | 77.127s = 1min 17.127s |
|8.6.9     | 76.216s = 1min 16.216s |
|8.6.9     | 73.872s = 1min 13.872s |

I found a clear leap between v9.7.6 and v10.0.0. Katalon Studio v10.0.0 runs slower than the previous version.

### Comparing v9.7.6 and v10.0.0 in detail

Let's look at the log of 2 versions in detail:

![9.7.6](https://kazurayam.github.io/healthcare-tests-speed-measurement-of-versions/images/9.7.6.png)

![10.0.0](https://kazurayam.github.io/healthcare-tests-speed-measurement-of-versions/images/10.0.0.png)

With v9.7.6, the test suite took 69.830s. With v10.0.0, it took 85.285s.

`69.830 : 85.285 = 1 : 1.23`

How much significant this time ratio 1.23 is?

We should look at the time taken for each individual steps.

The step `WebUI.openBrowser(G_SiteURL)` took 10 or 11 seconds. In both of v9.7.6 and v10.0.0, `openBrowser` took long time. No big difference.

On the contrary, the step `setText(findTestObject("Page_Login/txt_UserName", Username))` took 0.908s in v9.7.6; the same step took 1.525s in v10.0.0. Concerning this step, v10.0.0 ran far slower than v9.7.6.

The test suite calls `WebUI.openBrowser()` 3 times, which took 10s * 3 = 30s.

So I can calcurate how long v9.7.6 and v10.0.0 took while subtracting the time taken for `openBrowser`.

`69.830 - 30 : 85.285 - 30 = 39.830 : 55.285 = 1.375`

The v9.7.6-v10.0.0 speed ratio now gets larger: 1.23 -> 1.375

The [`Test Cases/Main Test Cases/TC1_Verify Successful Login`](https://github.com/katalon-studio-samples/healthcare-tests/blob/master/Scripts/Main%20Test%20Cases/TC1_Verify%20Successful%20Login/Script1482850539026.groovy) contains a single call to `WebUI.openBrowser()` and 5 times of calls to other action keywords (click, setText, verifyElementPresent). This test case script is a small one; not typical. Users would have much larger scripts with hundreds of calls to action keywords.

Please imagine, a Test Case contains a single call to `WebUI.openBrowser()` and 200 times of calls to other action keywords, then how will the speed ratio become? I belive, the ration will become larger. E.g, 1.50.


## Conclusion

Katalon Studio v10 runs slower than v9. But I haven't studied the reason of the speed difference yet.



## Appendix

- [list of images](https://kazurayam.github.io/healthcare-tests-speed-measurement-of-versions/)

## Experiement once more

At the end of Aug 2025, I wrote a report ["Katalon Studio v10.0.0 runs slower than v9.7.6"](https://forum.katalon.com/t/katalon-studio-v10-0-0-runs-slower-than-v9-7-6-a-measurement-report/181372). After that, I got more questions:

1. [@petr.brezina suggested](https://forum.katalon.com/t/katalon-studio-v10-0-0-runs-slower-than-v9-7-6-a-measurement-report/181372/11) that Katalon Studio v10.3.1 runs slower on Windows than Mac. Is it a fact?
2. The Smart Wait feature --- how is it influencive to the processing time?

So I carried out one more time of performance examination of Katalon Studio versions. Let me report the result.

I used my Windows 11 laptop with 16GB memory. An ordinary consumer model, nothing special. I used my Mac Book Air as I used for my previous report.

|Version|Mac,Enabled    |Mac,Disabled    |Windows,Enabled |Windows,Disabled|
|-------|---------------|----------------|----------------|----------------|
|10.3.1 |90.513s   |75.3s      |303.127s       |245.666s       |
|10.3.1 |<span style="color:gray;">81s</span>|127.940s|215.290s |195.372s |
|10.3.1 |<span style="color:gray;">84s</span>|103.769s |231.69s  |289.160s |
|10.3.0 |<span style="color:gray;">87s</span>|
|10.3.0 |<span style="color:gray;">86s</span>|
|10.3.0 |<span style="color:gray;">92s</span>|
|10.2.4 |<span style="color:gray;">85.948s</span>|
|10.2.4 |<span style="color:gray;">89.028s</span>|
|10.2.4 |<span style="color:gray;">84.740s</span>|
|10.1.0 |140.429s |103.269s |286.798s |204.320s |
|10.1.0 |262.338s |96.156s  |198.859s |190.299s |
|10.0.1 |93.490s  |70.772s |215.824s |220.112s |
|10.0.1 |91.688s  |75.884s |259.310s |182.832s |
|10.0.1 |115.054s |75.266s |236.365s  |171.646|
|10.0.0 |<span style="color:gray;">84.270s</span> |69.879s |71.553s |50.526s |
|10.0.0 |<span style="color:gray;">84.270s</span> |122.530s |68.438s |55.171s |
|10.0.0 |<span style="color:gray;">85.543s</span> |131.014s |69.725s |52.215s |
|9.7.6  |<span style="color:gray;">72.118s</span>|118.641s |57.579s |54.850s |
|9.7.6  |<span style="color:gray;">71.211s</span>|88.561s|57.579s |54.850s |
|9.7.6  |<span style="color:gray;">69.830s</span>|133.136s|55.282s |50.213s |
|9.0.0  |<span style="color:gray;">75.949s</span>|
|9.0.0  |<span style="color:gray;">72.187s</span>|
|9.0.0  |<span style="color:gray;">73.694s</span>|

The result was suprising to me.

1. The latest Katalon Studio v10.3.1 runs twice or three times slower on Windows than on Mac.

2. I wrote that v10.0.0 runs slower than v9.7.6. But I was not correct. The speed difference between v10.0.0 and v9.7.6 is not so significant. The newer versions sinice v10.0.1 runs far slower.

3. Clearly v10.0.1 got slower than v10.0.0. I infer there must be a change in v10.0.1 that caused the performance issue.

4. SmartWait seems to affect the turnaround time of the test. However, I don't see a straightforward rule about whether the test will be faster or slower.

I would recommend youto switch from Windows to Mac to run Katalon Studio tests.

Perhaps, Katalon developers work on Mac, not on Windows. So they don't know the fact.

### Looking into the steps

Let me look into the test steps:

- v10.3.1 Mac
![10.3.1_Mac](https://kazurayam.github.io/healthcare-tests-speed-measurement-of-versions/images/10.3.1_Mac.png)


- v10.3.1 Windows
![10.3.1_Windows](https://kazurayam.github.io/healthcare-tests-speed-measurement-of-versions/images/10.3.1_Windows.png)

The step of `WebUI.openBrowser(G_SiteURL)` performed differently. It took 12.2 seconds on Mac, 40.4 seconds on Windows.

Is my Windwos laptop is connected to the network with a poor WiFi device which performed slowly? Or, Katalon Studio worked slowly on Windows for `WebUI.openBrowser`? I have no clear idea at the moment.

Chrome 139.0.7258.155
Firefox 142.0.1
Edge 139.0.3405.125

|KS Version|Mac,Chrome|Mac,Firefox|Mac,Edge |Windows,Chrome|Windows,Firefox|Windows,Edge|
|10.3.1    |75.561s   |81.658s    | 91.776s |171.96s       |
|10.3.1    |72.549s   |82.431s    | 90.863s |162.19s       |
|10.3.1    |75.27s    |96.900s    | 177.90s |173.80s       |



