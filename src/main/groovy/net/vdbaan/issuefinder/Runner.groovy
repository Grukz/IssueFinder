/*
 * Copyright (C) 2017 S. van der Baan

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.vdbaan.issuefinder

import net.vdbaan.issuefinder.config.Config
import net.vdbaan.issuefinder.util.IssueLogger

import java.util.logging.Level
import java.util.logging.Logger

class Runner {
    private final static Logger logger = Logger.getLogger(Runner.class.getCanonicalName())

    static void main(String... args) {
        IssueLogger.setup(args)
        logger.info('starting')
        testJavaFX()
        Config.getInstance().attachShutDownHook()
        if (!args.contains('--reset-config')) {
            logger.info('Resetting the config')
            Config.getInstance().loadConfig()
        }
        Config.getInstance().checkDataDirectory()
        MainAppImpl.startup(args)
    }

    static void testJavaFX() {
        logger.info('testing availability of JavaFX')
        try {
            Class.forName('javafx.stage.Stage')
        } catch (ClassNotFoundException e) {
            logger.severe 'JavaFX8 Missing'
            logger.severe 'Please install JavaFX, for example:'
            logger.severe '- sudo apt-get install openjfx'
            logger.log(Level.FINE, 'Got exception', e)
            System.exit(1)
        }
    }
}
