/**
 *  Light Rule
 *
 *  Copyright 2016 Christopher Rued
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Light Rule",
    namespace: "christor",
    author: "Christopher Rued",
    description: "Turns  alight on and off",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
//    section("Which contact sensor?") {
//        input "contactSensor", "capability.contactSensor", title: "Which contact sensor(s)?", multiple: true
//    }
    section("Which dimmer?") {
    	input "dimmer", "capability.switchLevel", title: "Dimmer", multiple: false
    }
    section("Which contact switches?") {
        input "lights", "capability.switch", title: "Which contact sensor(s)?", multiple: true
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(dimmer, "level", dimmerChanged)
}

def dimmerChanged(evt) {
	toggleLights()
}

def toggleLights() {
	log.debug "================================ ${lights} "
    log.debug "" + dimmer.currentValue("level")
    def i = lights.size() - 1
    while (i >= 0 && lights[i].currentValue("switch") == "on") {
      	lights[i].off()
        i = i - 1
    }
    if (i >= 0) {
	    lights[i].on()
		runIn(dimmer.currentValue("level"), toggleLights)
    }
}

// TODO: implement event handlers

