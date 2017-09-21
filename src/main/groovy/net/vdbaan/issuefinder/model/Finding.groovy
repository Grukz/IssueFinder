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

package net.vdbaan.issuefinder.model


class Finding {
    enum Severity {
        CRITICAL(5), HIGH(4), MEDIUM(3), LOW(2), INFO(1), UNKNOWN(0)
        int value

        Severity(int value) {
            this.value = value
        }
    }

    String scanner = ''
    String ip = ''
    String hostName = ''
    String port = ''
    String portStatus = ''
    String protocol = ''
    String service = ''
    String plugin = ''
    Severity severity = Severity.UNKNOWN
    String summary = ''
    String baseCVSS = '0.0'
    Boolean exploitable = false

    @Override
    String toString() {
        return String.format("scanner:%s, source:%s:%s (%s), plugin:%s, risk:%s cvss:%s, exploitable:%b",
                scanner, ip, port, service, plugin, severity, baseCVSS, exploitable)
    }

    String fullDescription() {
        return String.format("scanner: %s\nsource: %s:%s\nservice: %s\nplugin: %s\nrisk: %s\n" +
                "=============================================\n" + "summary:\n%s\n",
                scanner, ip, port, service, plugin, severity, summary)
    }

    String htmlDescription() {
        return String.format("scanner: %s<br/>source: %s:%s (%s/%s)<br/>hostname: %s<br/>service: %s<br/>plugin: %s<br/>risk: %s<br/>" +
                "<hr/>" + "summary:<br/>%s",
                scanner, ip, port, protocol, portStatus, hostName,service, plugin, severity, summary.replace('\n', '<br/>'))
    }
}
