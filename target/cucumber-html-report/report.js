$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("NayFeatures.feature");
formatter.feature({
  "id": "nayanfeatures",
  "description": "",
  "name": "NayanFeatures",
  "keyword": "Feature",
  "line": 1
});
formatter.before({
  "duration": 8387155,
  "status": "passed"
});
formatter.before({
  "duration": 7139525,
  "status": "passed"
});
formatter.scenario({
  "id": "nayanfeatures;image-comparison",
  "description": "",
  "name": "Image Comparison",
  "keyword": "Scenario",
  "line": 3,
  "type": "scenario"
});
formatter.step({
  "name": "I open a web browser through \"\"",
  "keyword": "Given ",
  "line": 4
});
formatter.step({
  "name": "I enter the url \"\"",
  "keyword": "When ",
  "line": 5
});
formatter.step({
  "name": "I perform Image Comparison",
  "keyword": "Then ",
  "line": 6
});
formatter.match({
  "arguments": [
    {
      "val": "",
      "offset": 30
    }
  ],
  "location": "NayanStepDefinitions.I_open_a_web_browser_through(String)"
});
formatter.result({
  "duration": 69849382,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "",
      "offset": 17
    }
  ],
  "location": "NayanStepDefinitions.I_enter_the_url(String)"
});
formatter.result({
  "duration": 77279,
  "status": "passed"
});
formatter.match({
  "location": "NayanStepDefinitions.i_perform_image_comparison()"
});
formatter.result({
  "duration": 23546,
  "status": "passed"
});
formatter.after({
  "duration": 20587099891,
  "status": "passed"
});
});