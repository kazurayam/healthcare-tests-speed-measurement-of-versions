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

Please imagine, a Test Case contains a single call to `WebUI.openBrowser()` and 200 times of calls to other action keywords, then how will the speed ratio become? I belive, the ration will become larger. E.g, 1.50. This means, **Katalon Studio v10 runs slower than v9 and may take twice as long to process.**.


## Conclusion

Katalon Studio v10 runs slower than v9. But I haven't studied the reason of the speed difference yet.



## Appendix

- [list of images](https://kazurayam.github.io/healthcare-tests-speed-measurement-of-versions/)
