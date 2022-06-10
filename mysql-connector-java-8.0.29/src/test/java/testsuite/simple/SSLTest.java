/*
 * Copyright (c) 2002, 2021, Oracle and/or its affiliates.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License, version 2.0, as published by the
 * Free Software Foundation.
 *
 * This program is also distributed with certain software (including but not
 * limited to OpenSSL) that is licensed under separate terms, as designated in a
 * particular file or component or in included license documentation. The
 * authors of MySQL hereby grant you an additional permission to link the
 * program and your derivative works with the separately licensed software that
 * they have included with MySQL.
 *
 * Without limiting anything contained in the foregoing, this file, which is
 * part of MySQL Connector/J, is also subject to the Universal FOSS Exception,
 * version 1.0, a copy of which can be found at
 * http://oss.oracle.com/licenses/universal-foss-exception.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License, version 2.0,
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301  USA
 */

package testsuite.simple;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.protocol.a.NativeServerSession;

import testsuite.BaseTestCase;

/**
 * Tests SSL functionality in the driver.
 */
public class SSLTest extends BaseTestCase {
    @BeforeEach
    public void setUp() {
    }

    /**
     * Tests SSL Connection
     * 
     * @throws Exception
     */
    @Test
    public void testConnect() throws Exception {
        assumeTrue((((MysqlConnection) this.conn).getSession().getServerSession().getCapabilities().getCapabilityFlags() & NativeServerSession.CLIENT_SSL) != 0,
                "This test requires server with SSL support.");
        assumeTrue(supportsTLSv1_2(((MysqlConnection) this.conn).getSession().getServerSession().getServerVersion()),
                "This test requires server with TLSv1.2+ support.");
        assumeTrue(supportsTestCertificates(this.stmt),
                "This test requires the server configured with SSL certificates from ConnectorJ/src/test/config/ssl-test-certs");

        System.setProperty("javax.net.debug", "all");

        String dbUrlLocal = dbUrl;
        dbUrlLocal = dbUrlLocal.replaceAll("(?i)useSSL", "deletedProp");
        dbUrlLocal = dbUrlLocal.replaceAll("(?i)sslMode", "deletedProp");
        StringBuilder sslUrl = new StringBuilder(dbUrlLocal);
        if (dbUrl.indexOf("?") == -1) {
            sslUrl.append("?");
        } else {
            sslUrl.append("&");
        }
        sslUrl.append("sslMode=REQUIRED");

        getConnectionWithProps(sslUrl.toString(), "");

        ("<<<<<<<<<<< Look for SSL debug output >>>>>>>>>>>");
    }
}
