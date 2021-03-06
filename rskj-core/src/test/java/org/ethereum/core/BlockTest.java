/*
 * This file is part of RskJ
 * Copyright (C) 2017 RSK Labs Ltd.
 * (derived from ethereumJ library, Copyright (c) 2016 <ether.camp>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.ethereum.core;

import co.rsk.trie.TrieImpl;
import org.ethereum.config.SystemProperties;
import org.ethereum.core.genesis.GenesisLoader;
import org.ethereum.core.genesis.InitialAddressState;
import co.rsk.trie.Trie;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlockTest {

    private static final Logger logger = LoggerFactory.getLogger("test");

    private String GENESIS_STATE_ROOT = "d37ff1381258582a6f793b59a97391324f1a3c555588313d30c8c1dfcc288d32";

    static String TEST_GENESIS =
            "{" +
                    "'0000000000000000000000000000000000000001': { 'wei': '1' }" +
                    "'0000000000000000000000000000000000000002': { 'wei': '1' }" +
                    "'0000000000000000000000000000000000000003': { 'wei': '1' }" +
                    "'0000000000000000000000000000000000000004': { 'wei': '1' }" +
                    "'dbdbdb2cbd23b783741e8d7fcf51e459b497e4a6': { 'wei': '1606938044258990275541962092341162602522202993782792835301376' }" +
                    "'e6716f9544a56c530d868e4bfbacb172315bdead': { 'wei': '1606938044258990275541962092341162602522202993782792835301376' }" +
                    "'b9c015918bdaba24b4ff057a92a3873d6eb201be': { 'wei': '1606938044258990275541962092341162602522202993782792835301376' }" +
                    "'1a26338f0d905e295fccb71fa9ea849ffa12aaf4': { 'wei': '1606938044258990275541962092341162602522202993782792835301376' }" +
                    "'2ef47100e0787b915105fd5e3f4ff6752079d5cb': { 'wei': '1606938044258990275541962092341162602522202993782792835301376' }" +
                    "'cd2a3d9f938e13cd947ec05abc7fe734df8dd826': { 'wei': '1606938044258990275541962092341162602522202993782792835301376' }" +
                    "'6c386a4b26f73c802f34673f7248bb118f97424a': { 'wei': '1606938044258990275541962092341162602522202993782792835301376' }" +
                    "'e4157b34ea9615cfbde6b4fda419828124b70c78': { 'wei': '1606938044258990275541962092341162602522202993782792835301376' }" +
                    "}";

    static {
        TEST_GENESIS = TEST_GENESIS.replace("'", "\"");
    }

    @Test
    public void testPremineFromJSON() throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject genesisMap = (JSONObject) parser.parse(TEST_GENESIS);

        Set keys = genesisMap.keySet();

        Trie state = new TrieImpl(null, true);

        for (Object key : keys) {

            JSONObject val = (JSONObject) genesisMap.get(key);
            String denom = (String) val.keySet().toArray()[0];
            String value = (String) val.values().toArray()[0];

            BigInteger wei = Denomination.valueOf(denom.toUpperCase()).value().multiply(new BigInteger(value));

            AccountState acctState = new AccountState(BigInteger.ZERO, wei);
            state = state.put(Hex.decode(key.toString()), acctState.getEncoded());
        }

        logger.info("root: " + Hex.toHexString(state.getHash()));
        assertEquals(GENESIS_STATE_ROOT, Hex.toHexString(state.getHash()));
    }

}