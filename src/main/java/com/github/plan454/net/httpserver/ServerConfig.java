/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.github.plan454.net.httpserver;

import java.util.logging.Logger;
import java.security.PrivilegedAction;

/**
 * Parameters that users will not likely need to set
 * but are useful for debugging
 */

class ServerConfig {

    static int clockTick;

    static final int DEFAULT_CLOCK_TICK = 10000 ; // 10 sec.

    /* These values must be a reasonable multiple of clockTick */
    static final long DEFAULT_IDLE_INTERVAL = 30 ; // 5 min
    static final int DEFAULT_MAX_IDLE_CONNECTIONS = 200 ;

    static final long DEFAULT_MAX_REQ_TIME = -1; // default: forever
    static final long DEFAULT_MAX_RSP_TIME = -1; // default: forever
    static final long DEFAULT_TIMER_MILLIS = 1000;
    static final int  DEFAULT_MAX_REQ_HEADERS = 200;
    static final long DEFAULT_DRAIN_AMOUNT = 64 * 1024;

    static long idleInterval;
    static long drainAmount;    // max # of bytes to drain from an inputstream
    static int maxIdleConnections;
    // The maximum number of request headers allowable
    private static int maxReqHeaders;
    // max time a request or response is allowed to take
    static long maxReqTime;
    static long maxRspTime;
    static long timerMillis;
    static boolean debug;

    // the value of the TCP_NODELAY socket-level option
    static boolean noDelay;

    static {
        java.security.AccessController.doPrivileged(
            new PrivilegedAction<Void>() {
                @Override
                public Void run () {
                    idleInterval = Long.getLong("idleInterval",
                            DEFAULT_IDLE_INTERVAL) * 1000;

                    clockTick = Integer.getInteger("clockTick",
                            DEFAULT_CLOCK_TICK);

                    maxIdleConnections = Integer.getInteger(
                            "maxIdleConnections",
                            DEFAULT_MAX_IDLE_CONNECTIONS);

                    drainAmount = Long.getLong("drainAmount",
                            DEFAULT_DRAIN_AMOUNT);

                    maxReqHeaders = Integer.getInteger(
                            "maxReqHeaders",
                            DEFAULT_MAX_REQ_HEADERS);

                    maxReqTime = Long.getLong("maxReqTime",
                            DEFAULT_MAX_REQ_TIME);

                    maxRspTime = Long.getLong("maxRspTime",
                            DEFAULT_MAX_RSP_TIME);

                    timerMillis = Long.getLong("timerMillis",
                            DEFAULT_TIMER_MILLIS);

                    debug = Boolean.getBoolean("debug");

                    noDelay = Boolean.getBoolean("nodelay");

                    return null;
                }
            });

    }


    static void checkLegacyProperties (final Logger logger) {

        // legacy properties that are no longer used
        // print a warning to logger if they are set.

        java.security.AccessController.doPrivileged(
            new PrivilegedAction<Void>() {
                public Void run () {
                    if (System.getProperty("readTimeout")
                                                !=null)
                    {
                        logger.warning ("readTimeout "+
                            "property is no longer used. "+
                            "Use maxReqTime instead."
                        );
                    }
                    if (System.getProperty("writeTimeout")
                                                !=null)
                    {
                        logger.warning ("writeTimeout "+
                            "property is no longer used. Use "+
                            "maxRspTime instead."
                        );
                    }
                    if (System.getProperty("selCacheTimeout")
                                                !=null)
                    {
                        logger.warning ("selCacheTimeout "+
                            "property is no longer used."
                        );
                    }
                    return null;
                }
            }
        );
    }

    static boolean debugEnabled () {
        return debug;
    }

    static long getIdleInterval () {
        return idleInterval;
    }

    static int getClockTick () {
        return clockTick;
    }

    static int getMaxIdleConnections () {
        return maxIdleConnections;
    }

    static long getDrainAmount () {
        return drainAmount;
    }

    static int getMaxReqHeaders() {
        return maxReqHeaders;
    }

    static long getMaxReqTime () {
        return maxReqTime;
    }

    static long getMaxRspTime () {
        return maxRspTime;
    }

    static long getTimerMillis () {
        return timerMillis;
    }

    static boolean noDelay() {
        return noDelay;
    }
}
