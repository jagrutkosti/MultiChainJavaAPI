/*
 * Copyright (C) 2017 Worldline, Inc.
 *
 * MultiChainJavaAPI code distributed under the GPLv3 license, see COPYING file.
 * https://github.com/SimplyUb/MultiChainJavaAPI/blob/master/LICENSE
 *
 */
package multichain.command.builders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringJoiner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import multichain.command.MultichainException;
import multichain.object.Stream;

/**
 * @author Ub - H. MARTEAU
 * @version 1.0
 */
abstract class QueryBuilderCommon {

	private static String CHAIN = "";
	private static boolean header = false;

	protected enum CommandEnum {
		ADDMULTISIGADDRESS,
		ADDNODE,
		APPENDRAWCHANGE,
		APPENDRAWEXCHANGE,
		APPENDROWMETADA,
		CLEARMEMPOOL,
		COMBINEUNPSENT,
		CREATE,
		CREATEFROM,
		CREATEMULTISIG,
		CREATERAWEXCHANGE,
		CREATERAWTRANSACTION,
		DECODERAWEXCHANGE,
		DECODERAWTRANSACTION,
		DISABLERAWTRANSACTION,
		DUMPPRIVKEY,
		GETADDRESSBALANCES,
		GETADDRESSES,
		GETADDRESSTRANSACTION,
		GETASSETBALANCES,
		GETBESTBLOCKHASH,
		GETBLOCK,
		GETBLOCKCHAINPARAMS,
		GETBLOCKCOUNT,
		GETBLOCKHASH,
		GETINFO,
		GETMULTIBALANCES,
		GETNEWADDRESS,
		GETRAWCHANGEADDRESS,
		GETPEERINFO,
		GETRAWTRANSACTION,
		GETSTREAMITEM,
		GETTOTALBALANCES,
		GETTRANSACTION,
		GETTXOUT,
		GETTXOUTDATA,
		GETUNCONFIRMEDBALANCE,
		GETWALLETTRANSACTION,
		GRANT,
		GRANTFROM,
		GRANTWITHMETADATA,
		GRANTWITHMETADATAFROM,
		HELP,
		IMPORTADDRESS,
		IMPORTPRIVKEY,
		ISSUE,
		ISSUEFROM,
		ISSUEMORE,
		ISSUEMOREFROM,
		LISTADDRESSTRANSACTIONS,
		LISTASSETS,
		LISTLOCKUNPSENT,
		LISTPERMISSIONS,
		LISTSTREAMITEMS,
		LISTSTREAMKEYITEMS,
		LISTSTREAMKEYS,
		LISTSTREAMPUBLISHERS,
		LISTSTREAMPUBLISHERITEMS,
		LISTSTREAMS,
		LISTUNSPENT,
		LISTWALLETTRANSACTIONS,
		LOCKUNSPENT,
		PAUSE,
		PING,
		PREPARELOCKUNSPENT,
		PREPARELOCKUNSPENTFROM,
		PUBLISH,
		PUBLISHFROM,
		RESUME,
		REVOKE,
		REVOKEFROM,
		SENDASSETFROM,
		SENDASSETTOADDRESS,
		SENDFROM,
		SENDFROMADDRESS,
		SENDRAWTRANSACTION,
		SENDTOADDRESS,
		SENDWITHMETADATA,
		SENDWITHMETADATAFROM,
		SETLASTBLOCK,
		SIGNMESSAGE,
		SIGNTAWTRANSACTION,
		STOP,
		SUBSCRIBE,
		UNSUBSCRIBE,
		VALIDATEADDRESS,
		VERIFYMESSAGE
	}

	private static String removeHeader(String result) {
		String resultWithoutHeader = "";
		int size = 16 + CHAIN.length();
		int index = 0;
		index = result.indexOf("\"chain_name\":\"" + CHAIN + "\"");
		resultWithoutHeader = resultWithoutHeader.concat(result.substring(index + size));
		return resultWithoutHeader;
	}

	/**
	 *
	 * @param command
	 * @param parameters
	 *
	 * @return
	 *
	 * 		example :
	 *         MultichainQueryBuidlder.executeProcess(MultichainCommand.SENDTOADDRESS,"1EyXuq2JVrj4E3CpM9iNGNSqBpZ2iTPdwGKgvf
	 *         {\"rdcoin\":0.01}"
	 * @throws MultichainException
	 */
	protected static String execute(CommandEnum command, String... parameters) throws MultichainException {

		if (!CHAIN.equals("")) {
			Runtime rt = Runtime.getRuntime();
			Process pr;

			try {
				if (parameters.length > 0) {
					String params = "";
					for (String parameter : parameters) {
						params = params.concat(parameter + " ");
					}
					pr = rt.exec("multichain-cli " + CHAIN + " " + command.toString().toLowerCase() + " " + params);
				} else {
					pr = rt.exec("multichain-cli " + CHAIN + " " + command.toString().toLowerCase());
				}
				//Get the output from both error stream and output stream
				StreamGobbler errorGobbler = new StreamGobbler(pr.getErrorStream());
				StreamGobbler outputGobbler = new StreamGobbler(pr.getInputStream());

				errorGobbler.start();
				outputGobbler.start();

				pr.waitFor();

				errorGobbler.join();
				outputGobbler.join();

				pr.getOutputStream().close();
				if(outputGobbler.output.length() > 0)
					return outputGobbler.output.toString();

				return errorGobbler.output.toString();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				return "";
			}
		} else {
			return "ERROR, CHAIN NAME ARE EMPTY !";
		}
	}


	protected static String formatJson(String value) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(value);
	}

	protected static String formatJson(boolean value) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(value);
	}

	protected static String formatJson(int value) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(value);
	}

	protected static String formatJson(long value) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(value);
	}

	protected static String formatJson(float value) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(value);
	}

	protected static String formatJson(double value) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(value);
	}

	protected static String formatJson(Object value) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(value);
	}

	protected static String formatJson(String[] values) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(values);
	}

	protected static String formatJson(List<Object> values) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		return gson.toJson(values);
	}

	/**
	 * Based on the OS, it formats the list of values into string of array.
	 * @param values the values to be formatted
	 * @return {String} Formatted array of string based on OS
	 */
	protected static String formatStringArrayOS(String[] values){
		String OS = System.getProperty("os.name").toLowerCase();
		if(OS.contains("win")) {
			String valuesParam = "[";
			for(int j = 0; j < values.length; j++) {
				valuesParam += (j != values.length - 1) ?  ("\"\"\"" + values[j] + "\"\"\",") :
						("\"\"\"" + values[j] + "\"\"\"");
			}
			valuesParam += "]";
			return valuesParam;
		} else {
			String valuesParam = "[";
			for(int j = 0; j < values.length; j++) {
				valuesParam += (j != values.length - 1) ?  ("\"" + values[j] + "\",") :
						("\"" + values[j] + "\"");
			}
			valuesParam += "]";
			return valuesParam;
		}
	}

	/**
	 * @return the cHAIN
	 */
	protected static String getCHAIN() {
		return CHAIN;
	}

	/**
	 * @param cHAIN the cHAIN to set
	 */
	protected static void setCHAIN(String cHAIN) {
		CHAIN = cHAIN;
	}

	/**
	 * A stream gobbler class to run in different thread to read the output of the command line execution
	 */
	static class StreamGobbler extends Thread {
		private InputStream is;
		private StringJoiner output = new StringJoiner("\n");

		StreamGobbler(InputStream is) {
			this.is = is;
		}

		public void run() {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String line;
				while ( (line = br.readLine()) != null)
					output.add(line);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

}
