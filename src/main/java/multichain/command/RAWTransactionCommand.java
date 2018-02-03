/*
 * Copyright (C) 2017 Worldline, Inc.
 *
 * MultiChainJavaAPI code distributed under the GPLv3 license, see COPYING file.
 * https://github.com/SimplyUb/MultiChainJavaAPI/blob/master/LICENSE
 *
 */
package multichain.command;

import java.util.List;

import multichain.command.builders.QueryBuilderRAWTransaction;
import multichain.command.tools.MultichainTestParameter;
import multichain.object.Address;
import multichain.object.AddressBalanceAsset;
import multichain.object.SignRawTransactionOut;
import multichain.object.TransactionRAW;
import multichain.object.formatters.RAWTransactionFormatter;
import multichain.object.queryobjects.TxIdVout;

/**
 * @author Ub - H. MARTEAU
 * @version 1.0
 */
public class RAWTransactionCommand extends QueryBuilderRAWTransaction {
	/**
	 * appendrawchange "hexstring" address ( native-fee )
	 *
	 * Appends change output to raw transaction, containing any remaining assets / native currency in the inputs that are not already sent to other outputs.
	 *
	 * Arguments:
	 * 1. "hexstring"    (string, required) The hex string of the raw transaction)
	 * 2. "address"      (string, required) The address to send the change to.
	 * 3. "native-fee"   (numeric, optional) Native currency value deducted from that amount so it becomes a transaction fee. Default - calculated automatically
	 *
	 * Result:
	 * "transaction"            (string) hex string of the transaction
	 *
	 * Adds a change output to the raw transaction in hexstring given by a previous call to createrawtransaction
	 * @param hexString
	 * @param address
	 * @return
	 * @throws MultichainException
	 */
	public static String appendRawChange(String hexString, String address) throws MultichainException {
		return executeAppendRawChange(hexString, address);
	}

	public static String appendRawChange(String hexString, Address address) throws MultichainException {
		if (address == null) {
			throw new MultichainException("address", "is null");
		}

		return executeAppendRawChange(hexString, address.getAddress());
	}

	/**
	 *
	 * appendrawmetadata tx-hex data-hex
	 *
	 * Appends new OP_RETURN output to existing raw transaction
	 * Returns hex-encoded raw transaction.
	 *
	 * Arguments:
	 * 1. "tx-hex"      (string, required) The transaction hex string
	 * 2. "data-hex"    (string, required) Data hex string
	 * or
	 * 2. "issue-details"    (object, required) A json object with issue metadata
	 *     {
	 *       "name" : asset-name             (string,optional) Asset name
	 *       "multiple" : n                  (numeric,optional, default 1) Number of raw units in one displayed unit
	 *       "open" : true|false             (boolean, optional, default false) True if follow-on issues are allowed
	 *       "details" :                     (object, optional)  a json object with custom fields
	 *         {
	 *           "param-name": "param-value"   (strings, required) The key is the parameter name, the value is parameter value
	 *           ,...
	 *         }
	 * or
	 * 2. "issuemore-details"    (object, required) A json object with issuemore metadata
	 *     {
	 *       "details" :                     (object, optional)  a json object with custom fields
	 *         {
	 *           "param-name": "param-value"   (strings, required) The key is the parameter name, the value is parameter value
	 *           ,...
	 *         }
	 *     }
	 *
	 * Result:
	 * {
	 *   "hex": "value",        (string) The raw transaction with appended data output (hex-encoded string)
	 * }
	 *
	 * Adds a metadata output (using an OP_RETURN) to the raw transaction in tx-hex given by a previous call to createrawtransaction
	 * @param txHex
	 * @param dataHex
	 * @return
	 * @throws MultichainException
	 */
	public static String appendRawMetaData(String txHex, String dataHex) throws MultichainException {
		return executeAppendRawMetaData(txHex, dataHex);
	}

	/**
	 * Creates a transaction spending the specified inputs, sending to the given addresses
	 *
	 * createrawtransaction [{"txid":"id","vout":n},...] {"address":amount,...}
	 *
	 * Create a transaction spending the given inputs and sending to the given addresses.
	 * Returns hex-encoded raw transaction.
	 * Note that the transaction's inputs are not signed, and
	 * it is not stored in the wallet or transmitted to the network.
	 *
	 * Arguments:
	 * 1. "transactions"        (string, required) A json array of json objects
	 *      [
	 *        {
	 *          "txid":"id",  (string, required) The transaction id
	 *          "vout":n        (numeric, required) The output number
	 *        }
	 *        ,...
	 *      ]
	 * 2. "addresses"           (string, required) a json object with addresses as keys and amounts as values
	 *     {
	 *       "address":
	 *         x.xxx              (numeric, required) The key is the address, the value is the native currency amount
	 *           or
	 *         {                  (object) A json object of assets to send
	 *           "asset-identifier" : asset-quantity
	 *           ,...
	 *         }
	 *           or
	 *         {                  (object) A json object describing new asset issue
	 *           "issue" :
	 *             {
	 *               "raw" : n                      (numeric, required) The asset total amount in raw units
	 *               ,...
	 *             }
	 *           ,...
	 *         }
	 *           or
	 *         {                  (object) A json object describing follow-on asset issue
	 *           "issuemore" :
	 *             {
	 *               "asset" : "asset-identifier"   (string, required) Asset identifier - one of the following: issue txid. asset reference, asset name.
	 *               "raw" : n                      (numeric, required) The asset total amount in raw units
	 *               ,...
	 *             }
	 *           ,...
	 *         }
	 *           or
	 *         {                  (object) A json object describing permission change
	 *           "permissions" :
	 *             {
	 *               "type" : "permission(s)"    (string,required) Permission strings, comma delimited. Possible values: connect,send,receive,issue,mine,admin,activate
	 *               "startblock"                (numeric, optional) Block to apply permissions from (inclusive). Default - 0
	 *               "endblock"                  (numeric, optional) Block to apply permissions to (exclusive). Default - 4294967295
	 *               "timestamp"                 (numeric, optional) This helps resolve conflicts between permissions assigned by the same administrator. Default - current time
	 *               ,...
	 *             }
	 *           ,...
	 *         }
	 *       ,...
	 *     }
	 *
	 * Result:
	 * "transaction"            (string) hex string of the transaction
	 *
	 * @return
	 * @throws MultichainException
	 */
	public static String createRawTransaction(List<TxIdVout> inputs, List<AddressBalanceAsset> addessAssets) throws MultichainException {
		return executeCreateRawTransaction(inputs, addessAssets);
	}

	/**
	 * Create a raw transaction from specified address
	 * @param blockchainAddress the from address
	 * @param asset if any asset is being transferred, the raw form i.e. '{"1...adfafdsaf":{"asset0":2000}}'
	 * @param streamItem if publishing to a stream, the raw form i.e.
	 *                      '[{"for":"stream0","key":"key0","data":"45787465726e616c20697320736166657374"}]'
	 * @return hexidecimal blob as String
	 * @throws MultichainException
	 */
	public static String createRawSendFrom(String blockchainAddress, String asset, String streamItem) throws MultichainException {
		if(asset == null || asset.isEmpty()) {
			asset = "'{}'";
		}
		return executeCreateRawSendFrom(blockchainAddress, asset, streamItem);
	}

	/**
	 *
	 * decoderawtransaction "hexstring"
	 *
	 * Return a JSON object representing the serialized, hex-encoded transaction.
	 *
	 * Arguments:
	 * 1. "hex"      (string, required) The transaction hex string
	 *
	 * Result:
	 * {
	 *   "txid" : "id",        (string) The transaction id
	 *   "version" : n,          (numeric) The version
	 *   "locktime" : ttt,       (numeric) The lock time
	 *   "vin" : [               (array of json objects)
	 *      {
	 *        "txid": "id",    (string) The transaction id
	 *        "vout": n,         (numeric) The output number
	 *        "scriptSig": {     (json object) The script
	 *          "asm": "asm",  (string) asm
	 *          "hex": "hex"   (string) hex
	 *        },
	 *        "sequence": n     (numeric) The script sequence number
	 *      }
	 *      ,...
	 *   ],
	 *   "vout" : [             (array of json objects)
	 *      {
	 *        "value" : x.xxx,            (numeric) The value in btc
	 *        "n" : n,                    (numeric) index
	 *        "scriptPubKey" : {          (json object)
	 *          "asm" : "asm",          (string) the asm
	 *          "hex" : "hex",          (string) the hex
	 *          "reqSigs" : n,            (numeric) The required sigs
	 *          "type" : "pubkeyhash",  (string) The type, eg 'pubkeyhash'
	 *          "addresses" : [           (json array of string)
	 *            "12tvKAXCxZjSmdNbao16dKXC8tRWfcF5oc"   (string) address
	 *            ,...
	 *          ]
	 *        }
	 *      }
	 *      ,...
 	 * ],
	 * }
	 *
	 * @param hex
	 * @return
	 * @throws MultichainException
	 */
	public static TransactionRAW decodeRawTransaction(String hex) throws MultichainException {
		TransactionRAW transactionRAW = new TransactionRAW();

		String stringTransactionRAW = executeDecodeRawTransaction(hex);
		transactionRAW = RAWTransactionFormatter.formatTransactionRAW(stringTransactionRAW);

		return transactionRAW;
	}


	/**
	 * getrawchangeaddress
	 *
	 * Returns a new  address, for receiving change.
	 * This is for use with raw transactions, NOT normal use.
	 *
	 * Result:
	 * "address"    (string) The address
	 *
	 * @return String address
	 * @throws MultichainException
	 */
	public static String getRawChangeAddress() throws MultichainException {
		return executeGetRawChangeAddress();
	}

	/**
	 * getrawtransaction "txid" ( verbose )
	 *
	 * NOTE: By default this function only works sometimes. This is when the tx is in the mempool
	 * or there is an unspent output in the utxo for this transaction. To make it always work,
	 * you need to maintain a transaction index, using the -txindex command line option.
	 *
	 * Return the raw transaction data.
	 *
	 * If verbose=0, returns a string that is serialized, hex-encoded data for 'txid'.
	 * If verbose is non-zero, returns an Object with information about 'txid'.
	 *
	 * Arguments:
	 * 1. "txid"      (string, required) The transaction id
	 * 2. verbose       (numeric, optional, default=0) If 0, return a string, other return a json object
	 *
	 * Result (if verbose is not set or set to 0):
	 * "data"      (string) The serialized, hex-encoded data for 'txid'
	 *
	 * Result (if verbose > 0):
	 * {
	 *   "hex" : "data",       (string) The serialized, hex-encoded data for 'txid'
	 *   "txid" : "id",        (string) The transaction id (same as provided)
	 *   "version" : n,          (numeric) The version
	 *   "locktime" : ttt,       (numeric) The lock time
	 *   "vin" : [               (array of json objects)
	 *      {
	 *        "txid": "id",    (string) The transaction id
	 *        "vout": n,         (numeric)
	 *        "scriptSig": {     (json object) The script
	 *          "asm": "asm",  (string) asm
	 *          "hex": "hex"   (string) hex
	 *        },
	 *        "sequence": n      (numeric) The script sequence number
	 *      }
	 *      ,...
	 *   ],
	 *   "vout" : [              (array of json objects)
	 *      {
	 *        "value" : x.xxx,            (numeric) The value in btc
	 *        "n" : n,                    (numeric) index
	 *        "scriptPubKey" : {          (json object)
	 *          "asm" : "asm",          (string) the asm
	 *          "hex" : "hex",          (string) the hex
	 *          "reqSigs" : n,            (numeric) The required sigs
	 *          "type" : "pubkeyhash",  (string) The type, eg 'pubkeyhash'
	 *          "addresses" : [           (json array of string)
	 *            "address"        (string) address
	 *            ,...
	 *          ]
	 *        }
	 *      }
	 *      ,...
	 *   ],
	 *   "blockhash" : "hash",   (string) the block hash
	 *   "confirmations" : n,      (numeric) The confirmations
	 *   "time" : ttt,             (numeric) The transaction time in seconds since epoch (Jan 1 1970 GMT)
	 *   "blocktime" : ttt         (numeric) The block time in seconds since epoch (Jan 1 1970 GMT)
	 * }
	 *
	 * @param txid
	 * @param verbose (0 : false / 1 : true)
	 * @return
	 * @throws MultichainException
	 */
	public static TransactionRAW getRawTransaction(String txid, int verbose) throws MultichainException {
		String stringTransactionRAW = executeGetRawTransaction(txid,verbose);

		return RAWTransactionFormatter.formatTransactionRAW(stringTransactionRAW);

	}

	/**
	 * {@link #getRawTransaction(String, int) in verbose mode}
	 *
	 * @param txid
	 * @return
	 * @throws MultichainException
	 */
	public static TransactionRAW getRAWTransactionWithDetail(String txid) throws MultichainException {
		String stringTransactionRAW = executeGetRawTransaction(txid,1);

		return RAWTransactionFormatter.formatTransactionRAW(stringTransactionRAW);
	}

	/**
	 * {@link #getRawTransaction(String, int) in non-verbose mode}
	 *
	 * @param txid
	 * @return
	 * @throws MultichainException
	 */
	public static TransactionRAW getRAWTransactionWithoutDetail(String txid) throws MultichainException {
		String stringTransactionRAW = executeGetRawTransaction(txid,1);

		return RAWTransactionFormatter.formatTransactionRAW(stringTransactionRAW);
	}

	/**
	 *
	 * sendrawtransaction "hexstring" ( allowhighfees )
	 *
	 * Submits raw transaction (serialized, hex-encoded) to local node and network.
	 *
	 * Also see createrawtransaction and signrawtransaction calls.
	 *
	 * Arguments:
	 * 1. "hexstring"    (string, required) The hex string of the raw transaction)
	 * 2. allowhighfees    (boolean, optional, default=false) Allow high fees
	 *
	 * Result:
	 * "hex"             (string) The transaction hash in hex
	 *
	 * Validates the raw transaction in hexstring and transmits it to the network, returning the txid.
	 * @param hexString
	 * @return
	 * @throws MultichainException
	 */
	public static String sendRawTransaction(String hexString) throws MultichainException {
		return executeSendRawTransaction(hexString);
	}

	/**
	 *
	 * signrawtransaction "hexstring" ( [{"txid":"id","vout":n,"scriptPubKey":"hex","redeemScript":"hex"},...] ["privatekey1",...] sighashtype )
	 *
	 * Sign inputs for raw transaction (serialized, hex-encoded).
	 * The second optional argument (may be null) is an array of previous transaction outputs that
	 * this transaction depends on but may not yet be in the block chain.
	 * The third optional argument (may be null) is an array of base58-encoded private
	 * keys that, if given, will be the only keys used to sign the transaction.
	 *
	 *
	 * Arguments:
	 * 1. "hexstring"     (string, required) The transaction hex string
	 * 2. "prevtxs"       (string, optional) An json array of previous dependent transaction outputs
	 *      [               (json array of json objects, or 'null' if none provided)
	 *        {
	 *          "txid":"id",             (string, required) The transaction id
	 *          "vout":n,                  (numeric, required) The output number
	 *          "scriptPubKey": "hex",   (string, required) script key
	 *          "redeemScript": "hex"    (string, required for P2SH) redeem script
	 *        }
	 *        ,...
	 *     ]
	 * 3. "privatekeys"     (string, optional) A json array of base58-encoded private keys for signing
	 *     [                  (json array of strings, or 'null' if none provided)
	 *       "privatekey"   (string) private key in base58-encoding
	 *       ,...
	 *     ]
	 * 4. "sighashtype"     (string, optional, default=ALL) The signature hash type. Must be one of
	 *        "ALL"
	 *        "NONE"
	 *        "SINGLE"
	 *        "ALL|ANYONECANPAY"
	 *        "NONE|ANYONECANPAY"
	 *        "SINGLE|ANYONECANPAY"
	 *
	 * Result:
	 * {
	 *   "hex": "value",   (string) The raw transaction with signature(s) (hex-encoded string)
	 *   "complete": n       (numeric) if transaction has a complete set of signature (0 if not)
	 * }
	 *
	 * Signs the raw transaction in hexstring, often provided by a previous call to createrawtransaction and (optionally) appendrawmetadata. 	 * @param hexString
	 * @return
	 * @throws MultichainException
	 */
	public static String signRawTransaction(String hexString) throws MultichainException {
		return executeSignRawTransaction(hexString);
	}

	/**
	 * Sign the transaction hex string using specified private key to get bigger hexadecimal blob output
	 * @param hexString the hex string to sign
	 * @param privKey the key with which to sign
	 * @return SignRawTransactionOut object
	 * @throws MultichainException
	 */
	public static SignRawTransactionOut signRawTransactionWithPrivKey(String hexString, String privKey) throws MultichainException {
		String hexOut = executeSignRawTransactionWithPrivKey(hexString, privKey);
		return RAWTransactionFormatter.formatSignTransactionOut(hexOut);
	}
}
