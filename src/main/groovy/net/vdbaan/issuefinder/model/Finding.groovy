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

import net.vdbaan.issuefinder.parser.NessusParser
import net.vdbaan.issuefinder.util.FindingHtmlViewer

class Finding {
    enum Severity {
        CRITICAL, HIGH, MEDIUM, LOW, INFO, UNKNOWN
    }

    static String CREATE = '''
CREATE TABLE finding (
id           INTEGER GENERATED BY DEFAULT AS IDENTITY,
scanner      VARCHAR(64),
ip           VARCHAR(64),
hostName     VARCHAR(1000),
port         VARCHAR(64),
status       VARCHAR(64),
protocol     VARCHAR(64),
service      VARCHAR(64),
plugin       LONGVARCHAR,
risk         VARCHAR(64),
summary      LONGVARCHAR,
description  LONGVARCHAR,
reference    LONGVARCHAR,
pluginOutput LONGVARCHAR,
solution     LONGVARCHAR,
cvss         FLOAT,
cvssVector   VARCHAR(128),
exploitable  BOOLEAN
);
'''
    static String INSERT = 'INSERT INTO finding (scanner,  ip,  hostName,  port,  status,      protocol,  service,  plugin,  risk,  summary,  description,  reference,  pluginOutput,  solution,  cvss,      cvssVector,  exploitable) ' +
                                      'values (?.scanner,?.ip,?.hostName,?.port,?.portStatus,?.protocol,?.service,?.plugin,?.risk,?.summary,?.description,?.reference,?.pluginOutput,?.solution,?.baseCVSS,?.cvssVector,?.exploitable)'
    static String UPDATE = 'UPDATE finding SET scanner=?.scanner, ip=?.ip, hostName=?.hostName, port=?.port, portStatus=?.portStatus, protocol=?.protocol, service=?.service, plugin=?.plugin, risk=?.risk, ' +
            'summary=?.summary, description=?.description, reference=?.reference, pluginOutput=?.pluginOutput, solution=?.solution, baseCVSS=?.baseCVSS, cvssVector=?.cvssVector, exploitable=?.exploitable WHERE id=?.id'
    static String SELECT = 'SELECT * FROM finding'
    static String COUNT = 'SELECT COUNT(*) FROM finding'
    static String DELETE_ALL = 'DELETE FROM finding'

    int id
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
    String description= ''
    String reference = ''
    String pluginOutput= ''
    String solution= ''
    Float baseCVSS = 0.0
    String cvssVector = ''
    Boolean exploitable = false

    String getRisk() {
        return severity.toString()
    }

    @Override
    String toString() {
        return String.format("scanner:%s, source:%s:%s (%s), plugin:%s, severity:%s cvss:%s, exploitable:%b",
                scanner, ip, port, service, plugin, severity, baseCVSS, exploitable)
    }

    String fullDescription() {
        return """
scanner: ${scanner}
source: ${ip}:${port}
service: ${service}
plugin: ${plugin}
severity: ${severity}
summary: ${summary}
description:${description}
reference: ${reference}
pluginOutput: ${pluginOutput}
cvss: ${baseCVSS}
"""
    }

    String htmlDescription() {
        return FindingHtmlViewer.asHtml(this)
    }


}
