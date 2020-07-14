// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */

// Get the elements with class="column" for images.html page
var elements = document.getElementsByClassName("column");
var i;

function addRandomFact() {
  const facts =
      ['is based in Vancouver, BC 🏔',
       'enjoys fencing, hiking, and skiing! 🤺',
       'is a logistics coordinator for nwPlus, home to the largest hackathon in Western Canada 👩🏻‍💻', 
       'has a goldendoodle named Luna! 🐶',
       'is passionate about empowering girls in tech ✨',
       'recently transferred to computer science 💻',
       'loves Studio Ghibli films and Joe Hisaishi pieces 🎶'];

  // Pick a random fact.
  const fact = facts[Math.floor(Math.random() * facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fact;
}

function setImages() {
  // two images per row
  for (i = 0; i < elements.length; i++) {
    elements[i].style.flex = "15%";
  }
}


/**
 * Fetches the hello greeting from the server and adds it to the DOM.
 */
function getGreeting() {
  console.log('Fetching a greeting!');

  // The fetch() function returns a Promise because the request is asynchronous.
  const responsePromise = fetch('/data');

  // When the request is complete, pass the response into handleResponse().
  responsePromise.then(handleResponse);
}

/**
 * Handles response by converting it to text and passing the result to
 * addGreetingToDom().
 */
function handleResponse(response) {
  console.log('Handling the response.');

  // response.text() returns a Promise, because the response is a stream of
  // content and not a simple variable.
  const textPromise = response.text();

  // When the response is converted to text, pass the result into the
  // addGreetingToDom() function.
  textPromise.then(addGreetingToDom);
}

/** Adds the greeting to the DOM. */
function addGreetingToDom(greeting) {
  console.log('Adding quote to dom: ' + greeting);

  const greetingContainer = document.getElementById('hello-container');
  greetingContainer.innerText = greeting;
}

/** Adds JSON string from server to the DOM. */
function getJsonData() {

    fetch('/data').then(response => response.json()).then((json) => {
      console.log('Fetching JSON string from the server.');
      console.log(json);
      const listElement = document.getElementById('json-string-container');
      listElement.innerHTML = '';
      listElement.appendChild(
        createListElement('First quote: ' + json[0]));
      listElement.appendChild(
        createListElement('Second quote: ' + json[1]));
      listElement.appendChild(
        createListElement('Third quote: ' + json[2]));
    });
}

/** Creates an <li> element containing text for strings */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}