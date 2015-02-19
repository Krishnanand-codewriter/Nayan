$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("NayFeatures.feature");
formatter.feature({
  "id": "nayanfeatures",
  "description": "",
  "name": "NayanFeatures",
  "keyword": "Feature",
  "line": 1
});
formatter.before({
  "duration": 13044121,
  "status": "passed"
});
formatter.before({
  "duration": 2946626,
  "status": "passed"
});
formatter.scenario({
  "id": "nayanfeatures;open-browser",
  "description": "",
  "name": "Open browser",
  "keyword": "Scenario",
  "line": 3,
  "type": "scenario"
});
formatter.step({
  "name": "I open a web browser through \"CHROMEDRIVER\"",
  "keyword": "Given ",
  "line": 4
});
formatter.step({
  "name": "I enter the url \"http://www.google.com\"",
  "keyword": "When ",
  "line": 5
});
formatter.step({
  "name": "I should be navigated to the \"Google\" page.",
  "keyword": "Then ",
  "line": 6
});
formatter.step({
  "name": "I take the screenshot as \"Google Home\"",
  "keyword": "And ",
  "line": 7
});
formatter.match({
  "arguments": [
    {
      "val": "CHROMEDRIVER",
      "offset": 30
    }
  ],
  "location": "NayanStepDefinitions.I_open_a_web_browser_through(String)"
});
formatter.result({
  "duration": 1487694799,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "http://www.google.com",
      "offset": 17
    }
  ],
  "location": "NayanStepDefinitions.I_enter_the_url(String)"
});
formatter.result({
  "duration": 7199453810,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Google",
      "offset": 30
    }
  ],
  "location": "NayanStepDefinitions.I_should_be_navigated_to_the_page(String)"
});
formatter.result({
  "duration": 15495698,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Google Home",
      "offset": 26
    }
  ],
  "location": "NayanStepDefinitions.i_take_the_screenshot_as(String)"
});
formatter.result({
  "duration": 184407290,
  "status": "passed"
});
});