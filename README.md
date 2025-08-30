# healthcare-tests speed measurement of Katalon Studio versions

## Environment

- Mac Book Air, 2-18, 1.6GHz Intel Core i5, 17GB memory, macOS Sonoma14.7.6
- used Chrome  139.0.7258.155
- ChromeDriver is updated to the latest using "Tools > Update WebDrivers > Chrome".
- I disabled "Project > Katalon Platform > Integration > Enable Katalon Platform Integration"
- I set "Project > Execution > Default wait for element timeout (in seconds)" to have 30 seconds
- I set "Project > Execution > WebUI > Default Smart Wait" to be Enabled

## Measurement Criteria

- I used https://github.com/katalon-studio-samples/healthcare-tests as testbed
- I checked as many Katalon Studio versions as possible to see if there is any difference in speed
- Measured the time take to run "Test Suites/healthcare-tests - TS_RegressionTest".
- The time taken was found in the top-right corder of the Log Viewer when the test suite finished
- I ran the same test suite at least 3 times for each KS version to collect enough number of sample data.

## Result

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








