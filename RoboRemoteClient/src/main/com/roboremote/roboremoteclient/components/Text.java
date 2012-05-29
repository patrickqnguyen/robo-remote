/*
 * Copyright (c) 2012, Groupon, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * Neither the name of GROUPON nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.</div>
 */

package com.roboremote.roboremoteclient.components;

import com.roboremote.roboremoteclient.Client;
import com.roboremote.roboremoteclient.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.*;

public class Text {
    private static final Logger logger = LoggerFactory.getLogger("test");

    public static boolean exists(String label) throws Exception {
        return exists(label, 10);
    }

    public static boolean exists(String label, int timeout) throws Exception {
        logger.info("Attempting to find text: {}", label);

        if (! Solo.waitForText(label, 1, timeout, false)) {
            logger.warn("Could not find text: {}", label);
            return false;
        }
        
        JSONArray retVal = Client.map("solo", "getText", label, true);

        if (retVal.length() == 0) {
            logger.warn("Could not find text: {}", label);
            return false;
        }

        // make sure the text is visible
        if (! Solo.isVisible(retVal.getString(0))) {
            logger.warn("Found text but it is not visible: {}", label);
            return false;
        }

        logger.info("Found text: {}", label);
        return true;
    }
}