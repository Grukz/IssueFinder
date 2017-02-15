/*
 *  Copyright (C) 2017  S. van der Baan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package net.vdbaan.issuefinder.util

import net.vdbaan.issuefinder.model.Finding
import groovy.json.JsonSlurper

abstract class Parser {

    def content

    static XmlSlurper xmlslurper
    static JsonSlurper jsonSlurper

    static {
        xmlslurper = new XmlSlurper(false,false,false)
        xmlslurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
        xmlslurper.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        xmlslurper.setErrorHandler(null)
        jsonSlurper = new JsonSlurper()
    }

    static Parser getParser(file) {
        if (NessusParser.identify(file)) return new NessusParser(file)
        if (NMapParser.identify(file)) return new NMapParser(file)
        if (NetsparkerParser.identify(file)) return new NetsparkerParser(file)
        if (NiktoParser.identify(file)) return new NiktoParser(file)
        if (TestSSLParser.identify(file)) return new TestSSLParser(file)

        throw RuntimeException("Parser not found")
    }

    abstract List<Finding> parse()

    static boolean identify(content){ return false}
}
