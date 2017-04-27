/*
 * Copyright (C) 2017 Worldline, Inc.
 *
 * MultiChainJavaAPI code distributed under the GPLv3 license, see COPYING file.
 * https://github.com/SimplyUb/MultiChainJavaAPI/blob/master/LICENSE
 *
 */
package multichain.command;

import multichain.command.builders.QueryBuilderGrant;
import multichain.object.Address;
import multichain.object.Permission;
import multichain.object.formatters.GrantFormatter;

/**
 * @author Ub - H. MARTEAU
 * @version 1.0
 */
public class GrantCommand extends QueryBuilderGrant {

	public static byte CONNECT= QueryBuilderGrant.CONNECT;
	public static byte SEND=QueryBuilderGrant.SEND;
	public static byte RECEIVE=QueryBuilderGrant.RECEIVE;
	public static byte ISSUE=QueryBuilderGrant.ISSUE;
	public static byte MINE=QueryBuilderGrant.MINE;
	public static byte ACTIVATE=QueryBuilderGrant.ACTIVATE;
	public static byte ADMIN=QueryBuilderGrant.ADMIN;
	public static byte CREATE=QueryBuilderGrant.CREATE;

	public static int WALLET = QueryBuilderGrant.WALLET;
	public static int WALLET_ISSUE = QueryBuilderGrant.WALLET_ISSUE;

	/**
	 * Give grant permissions to an address
	 *
	 * grant "address(es)"  "permission(s)" ( native-amount "comment" "comment-to" startblock endblock )
	 *
	 * Grant permission(s) to a given address.
	 *
	 * Arguments:
	 * 1. "address(es)"  (string, required) The multichain addresses to send to (comma delimited)
	 * 2. "permission(s)"  (string, required) Permission strings, comma delimited. Possible values: connect,send,receive,issue,mine,admin,activate
	 * 3. "native-amount"      (numeric, optional)  native currency amount to send. eg 0.1. Default - 0.0
	 * 4. "startblock"      (numeric, optional) Block to apply permissions from (inclusive). Default - 0
	 * 5. "endblock"        (numeric, optional) Block to apply permissions to (exclusive). Default - 4294967295
	 *                              If -1 is specified default value is used.
	 * 6. "comment"     (string, optional) A comment used to store what the transaction is for.
	 *                              This is not part of the transaction, just kept in your wallet.
	 * 7. "comment-to"  (string, optional) A comment to store the name of the person or organization
	 *                              to which you're sending the transaction. This is not part of the
	 *                              transaction, just kept in your wallet.
	 *
	 * Result:
	 * "transactionid"  (string) The transaction id.
	 *
	 * @param address to give permissions
	 * @param permissions byte to give (possible values in  this class, concatenation with | )
	 * @return transactionId of grant action
	 * @throws MultichainException
	 */
	public static String grant(Address address, byte permissions) throws MultichainException {
		Byte b = new Byte(permissions);
		return grant(address.getAddress(), b.intValue());
	}

	/**
	 * {@link #grant(Address, byte)} with permissions in format int
	 * @param address to give permissions
	 * @param permissions int to give (possible values in  this class, concatenation with | )
	 * @return transactionId of grant action
	 * @throws MultichainException
	 */
	public static String grant(Address address, int permissions) throws MultichainException {
		return grant(address.getAddress(), permissions);
	}

	/**
	 * {@link #grant(Address, byte)} with address in format string
	 * @param address String to give permissions
	 * @param permissions byte to give (possible values in  this class, concatenation with | )
	 * @return transactionId of grant action
	 * @throws MultichainException
	 */
	public static String grant(String address, byte permissions) throws MultichainException {
		Byte b = new Byte(permissions);
		return grant(address, b.intValue());
	}

	/**
	 * {@link #grant(Address, byte)} with address in format string and permissions in format int
	 * @param address String to give permissions
	 * @param permissions int to give (possible values in  this class, concatenation with | )
	 * @return transactionId of grant action
	 * @throws MultichainException
	 */
	public static String grant(String address, int permissions) throws MultichainException {
		return executeGrant(address, permissions);
	}

	/**
	 * Grants permissions to addresses From an address
	 *
	 * grantfrom "from-address"  "to-address(es)"  "permission(s)" ( native-amount "comment" "comment-to" startblock endblock )
	 *
	 * Grant permission using specific address.
	 *
	 * Arguments:
	 * 1. "from-address"  (string, required) Address used for grant.
	 * 2. "to-address(es)"  (string, required) The multichain addresses to grant permissions to
	 * 3. "permission(s)"  (string, required) Permission strings, comma delimited. Possible values: connect,send,receive,issue,mine,admin,activate
	 * 4. "native-amount"      (numeric, optional)  native currency amount to send. eg 0.1. Default - 0.0
	 * 5. "startblock"      (numeric, optional) Block to apply permissions from (inclusive). Default - 0
	 * 6. "endblock"        (numeric, optional) Block to apply permissions to (exclusive). Default - 4294967295
	 *                              If -1 is specified default value is used.
	 * 7. "comment"     (string, optional) A comment used to store what the transaction is for.
	 *                              This is not part of the transaction, just kept in your wallet.
	 * 8. "comment-to"  (string, optional) A comment to store the name of the person or organization
	 *                              to which you're sending the transaction. This is not part of the
	 *                              transaction, just kept in your wallet.
	 *
	 * Result:
	 * "transactionid"  (string) The transaction id.
	 *
	 * @param addressFrom String giving permissions
	 * @param address to give permissions
	 * @param permissions byte to give (possible values in  this class, concatenation with | )
	 * @return transactionId of grantFrom action
	 * @throws MultichainException
	 */
	public static String grantFrom(Address addressFrom, Address address, byte permissions) throws MultichainException {
		Byte b = new Byte(permissions);
		return grantFrom(addressFrom.getAddress(), address.getAddress(), b.intValue());
	}

	/**
	 * {@link #grantFrom(Address, byte)} with permissions in format int
	 * @param addressFrom String giving permissions
	 * @param address to give permissions
	 * @param permissions int to give (possible values in  this class, concatenation with | )
	 * @return transactionId of grantFrom action
	 * @throws MultichainException
	 */
	public static String grantFrom(Address addressFrom, Address address, int permissions) throws MultichainException {
		return grantFrom(addressFrom.getAddress(), address.getAddress(), permissions);
	}

	/**
	 * {@link #grantFrom(Address, byte)} with address in format string
	 * @param addressFrom String giving permissions
	 * @param address String to give permissions
	 * @param permissions byte to give (possible values in  this class, concatenation with | )
	 * @return transactionId of grantFrom action
	 * @throws MultichainException
	 */
	public static String grantFrom(String addressFrom, String address, byte permissions) throws MultichainException {
		Byte b = new Byte(permissions);
		return grantFrom(addressFrom, address, b.intValue());
	}

	/**
	 * {@link #grantFrom(Address, byte)} with address in format string and permissions in format int
	 * @param addressFrom String giving permissions
	 * @param address String to give permissions
	 * @param permissions int to give (possible values in  this class, concatenation with | )
	 * @return transactionId of grantFrom action
	 * @throws MultichainException
	 */
	public static String grantFrom(String addressFrom, String address, int permissions) throws MultichainException {
		return executeGrantFrom(addressFrom, address, permissions);
	}

	/**
	 * listpermissions ("permission(s)" "address" verbose)
	 *
	 * Returns list of addresses having one of the specified permissions
	 *
	 * Arguments:
	 * 1. "permission(s)"  (string, optional) Permission strings, comma delimited. Possible values: connect,send,receive,issue,mine,admin,activate. Default: all.
	 * 2. "address"  (string, optional) The addresses to retrieve permissions for. "" or "*" for all addresses
	 * 3. verbose      (boolean, optional, default=false) If true, returns list of pending grants

	 * Gives permissions of an address
	 * @param permissions
	 * @param address to get permissions
	 * @param verbose
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(byte permissions, Address address, boolean verbose) throws MultichainException {
		String stringPermission = executeListPermissions(permissions, address.getAddress(), verbose);

		return GrantFormatter.formatPermission(stringPermission);
	}

	/**
	 * {@link #listPermissions(byte, Address, boolean)} with permissions in int
	 * @param permissions int format
	 * @param address Address
	 * @param verbose boolean
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(int permissions, Address address, boolean verbose) throws MultichainException {
		String stringPermission = executeListPermissions(permissions, address.getAddress(), verbose);

		return GrantFormatter.formatPermission(stringPermission);
	}

	/**
	 * {@link #listPermissions(int, Address, boolean)} with address in format String
	 * @param permissions int format
	 * @param address String
	 * @param verbose boolean
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(int permissions, String address, boolean verbose) throws MultichainException {
		Address returnedAddress = new Address(address);

		return listPermissions(permissions, returnedAddress, verbose);
	}

	/**
	 * {@link #listPermissions(byte, Address, boolean)} with address in format String
	 * @param permissions byte format
	 * @param address String
	 * @param verbose boolean
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(byte permissions, String address, boolean verbose) throws MultichainException {
		Address returnedAddress = new Address(address);

		return listPermissions(permissions, returnedAddress, verbose);
	}


	/**
	 * {@link #listPermissions(byte, Address, boolean)} without verbose
	 * @param permissions byte
	 * @param address to get permissions
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(byte permissions, Address address) throws MultichainException {
		String stringPermission = executeListPermissions(permissions, address.getAddress(), false);

		return GrantFormatter.formatPermission(stringPermission);
	}

	/**
	 * {@link #listPermissions(byte, Address)} with permissions in int
	 * @param permissions int
	 * @param address Address
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(int permissions, Address address) throws MultichainException {
		String stringPermission = executeListPermissions(permissions, address.getAddress(), false);

		return GrantFormatter.formatPermission(stringPermission);
	}

	/**
	 * {@link #listPermissions(int, Address)} with address in format String
	 * @param permissions int
	 * @param address String
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(int permissions, String address) throws MultichainException {
		Address returnedAddress = new Address(address);

		return listPermissions(permissions, returnedAddress, false);
	}

	/**
	 * {@link #listPermissions(byte, Address)} with address in format String
	 * @param permissions byte
	 * @param address String
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(byte permissions, String address) throws MultichainException {
		Address returnedAddress = new Address(address);

		return listPermissions(permissions, returnedAddress, false);
	}

	/**
	 * {@link #listPermissions(byte, Address)} without address
	 * @param permissions byte
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(byte permissions) throws MultichainException {
		String stringPermission = executeListPermissions(permissions, null, false);

		return GrantFormatter.formatPermission(stringPermission);
	}

	/**
	 * {@link #listPermissions(byte)} with permissions in int
	 * @param permissions int
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions(int permissions) throws MultichainException {
		String stringPermission = executeListPermissions(permissions, null, false);

		return GrantFormatter.formatPermission(stringPermission);
	}

	/**
	 * {@link #listPermissions(byte)} without permissions
	 * @return a list of all permissions currently granted to addresses.
	 * @throws MultichainException
	 */
	public static Permission listPermissions() throws MultichainException {
		String stringPermission = executeListPermissions(0, null, false);

		return GrantFormatter.formatPermission(stringPermission);
	}

	/**
	 * Revoke permissions to addresses
	 *
	 * revoke "address(es)" "permission(s)" ( native-amount "comment" "comment-to" )
	 *
	 * Revoke permission from a given address. The amount is a real
	 *
	 * Arguments:
	 * 1. "address(es)"  (string, required) The addresses(es) to revoke permissions from
	 * 2. "permission(s)"  (string, required) Permission strings, comma delimited. Possible values: connect,send,receive,issue,mine,admin
	 * 3. "native-amount"      (numeric, optional) native currency amount to send. eg 0.1. Default - 0
	 * 4. "comment"     (string, optional) A comment used to store what the transaction is for.
	 *                              This is not part of the transaction, just kept in your wallet.
	 * 5. "comment-to"  (string, optional) A comment to store the name of the person or organization
	 *                              to which you're sending the transaction. This is not part of the
	 *                              transaction, just kept in your wallet.
	 *
	 * Result:
	 * "transactionid"  (string) The transaction id.
	 *
	 * @param address
	 * @param permissions
	 *            This permissions will be grant to all addresses who are send
	 *            in parameter
	 * @return the txid of the transaction revoking the permissions
	 * @throws MultichainException
	 */
	protected static String revoke(Address address, byte permissions) throws MultichainException {
		Byte b = new Byte(permissions);
		return revoke(address.getAddress(), b.intValue());
	}

	/**
	 * {@link #revoke(Address, byte)} with permissions in format int
	 * @param address to revoke permissions
	 * @param permissions int to revoke (possible values in  this class, concatenation with | )
	 * @return transactionId of revoke action
	 * @throws MultichainException
	 */
	public static String revoke(Address address, int permissions) throws MultichainException {
		return revoke(address.getAddress(), permissions);
	}

	/**
	 * {@link #revoke(Address, byte)} with address in format string
	 * @param address String to revoke permissions
	 * @param permissions byte to revoke (possible values in  this class, concatenation with | )
	 * @return transactionId of revoke action
	 * @throws MultichainException
	 */
	public static String revoke(String address, byte permissions) throws MultichainException {
		Byte b = new Byte(permissions);
		return revoke(address, b.intValue());
	}

	/**
	 * {@link #revoke(Address, byte)} with address in format string and permissions in format int
	 * @param address String to revoke permissions
	 * @param permissions int to revoke (possible values in  this class, concatenation with | )
	 * @return transactionId of revoke action
	 * @throws MultichainException
	 */
	public static String revoke(String address, int permissions) throws MultichainException {
		return executeRevoke(address, permissions);
	}

	/**
	 * Revoke permissions to addresses From an address
	 *
	 * revokefrom "from-address"  "to-address(es)" "permission(s)" ( native-amount "comment" "comment-to" )
	 *
	 * Revoke permissions using specific address.
	 *
	 * Arguments:
	 * 1. "from-address"  (string, required) Addresses used for revoke.
	 * 2. "to-address(es)"  (string, required) The addresses(es) to revoke permissions from. Comma delimited
	 * 3. "permission(s)"  (string, required) Permission strings, comma delimited. Possible values: connect,send,receive,issue,mine,admin
	 * 4. "native-amount"      (numeric, optional) native currency amount to send. eg 0.1. Default - 0
	 * 5. "comment"     (string, optional) A comment used to store what the transaction is for.
	 *                              This is not part of the transaction, just kept in your wallet.
	 * 6. "comment-to"  (string, optional) A comment to store the name of the person or organization
	 *                              to which you're sending the transaction. This is not part of the
	 *                              transaction, just kept in your wallet.
	 *
	 * Result:
	 * "transactionid"  (string) The transaction id.
	 *
	 *
	 * @param addressFrom address origin
	 * @param address     address destination
	 * @param permissions
	 *            This permissions will be grant to all addresses who are send
	 *            in parameter
	 * @return the txid of the transaction revoking the permissions
	 * @throws MultichainException
	 */
	protected static String revokeFrom(Address addressFrom, Address address, byte permissions) throws MultichainException {
		Byte b = new Byte(permissions);
		return revokeFrom(addressFrom.getAddress(), address.getAddress(), b.intValue());
	}

	/**
	 * {@link #revokeFrom(Address, byte)} with permissions in format int
	 * @param addressFrom address origin
	 * @param address     address destination
	 * @param permissions int to revoke (possible values in  this class, concatenation with | )
	 * @return transactionId of revoke action
	 * @throws MultichainException
	 */
	public static String revokeFrom(Address addressFrom, Address address, int permissions) throws MultichainException {
		return revokeFrom(addressFrom.getAddress(), address.getAddress(), permissions);
	}

	/**
	 * {@link #revokeFrom(Address, byte)} with address in format string
	 * @param addressFrom address origin
	 * @param address     address destination
	 * @param permissions byte to revoke (possible values in  this class, concatenation with | )
	 * @return transactionId of revoke action
	 * @throws MultichainException
	 */
	public static String revokeFrom(String addressFrom, String address, byte permissions) throws MultichainException {
		Byte b = new Byte(permissions);
		return revokeFrom(addressFrom, address, b.intValue());
	}

	/**
	 * {@link #revokeFrom(Address, byte)} with address in format string and permissions in format int
	 * @param addressFrom address origin
	 * @param address     address destination
	 * @param permissions int to revoke (possible values in  this class, concatenation with | )
	 * @return transactionId of revoke action
	 * @throws MultichainException
	 */
	public static String revokeFrom(String addressFrom, String address, int permissions) throws MultichainException {
		return executeRevokeFrom(addressFrom, address, permissions);
	}


}
