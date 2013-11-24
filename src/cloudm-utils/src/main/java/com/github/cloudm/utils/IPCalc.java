package com.github.cloudm.utils;

/**
 * 计算CIDR相关信息
 * 
 * @author andy.wan
 *
 */
public class IPCalc {

	private int baseIPnumeric;
	private int netmaskNumeric;
	private String firstIP;
	private String lastIP;

	/**
	 * Constructor
	 * 
	 * @param IPinCIDRFormat
	 *            IP in CIDR format e.g. 192.168.1.0/24
	 */
	public IPCalc(String IPinCIDRFormat) throws NumberFormatException {

		String[] st = IPinCIDRFormat.split("\\/");
		if (st.length != 2) {
			throw new NumberFormatException("Invalid CIDR format '"
					+ IPinCIDRFormat + "', should be: xx.xx.xx.xx/xx");
		}
		String symbolicIP = st[0];
		String symbolicCIDR = st[1];

		Integer numericCIDR = new Integer(symbolicCIDR);
		if (numericCIDR > 32) {
			throw new NumberFormatException("CIDR can not be greater than 32");
		}

		// Get IP
		st = symbolicIP.split("\\.");
		if (st.length != 4) {
			throw new NumberFormatException("Invalid IP address: " + symbolicIP);
		}

		int i = 24;
		baseIPnumeric = 0;

		for (int n = 0; n < st.length; n++) {
			int value = Integer.parseInt(st[n]);
			if (value != (value & 0xff)) {
				throw new NumberFormatException("Invalid IP address: "
						+ symbolicIP);
			}
			baseIPnumeric += value << i;
			i -= 8;
		}

		// Get netmask
		if (numericCIDR < 8)
			throw new NumberFormatException(
					"Netmask CIDR can not be less than 8");
		netmaskNumeric = 0xffffffff;
		netmaskNumeric = netmaskNumeric << (32 - numericCIDR);
	}

	/**
	 * Get the IP in symbolic form, i.e. xxx.xxx.xxx.xxx
	 * 
	 * @return The reult of convertNumericIpToSymbolic() when passed
	 *         baseIPnumeric
	 */
	public String getIP() {
		return convertNumericIpToSymbolic(baseIPnumeric);
	}

	/**
	 * Converts Numeric version of IP to Symbolic, i.e. xxx.xxx.xxx.xxx
	 * 
	 * @param ip
	 *            IP Address in numeric form
	 * @return the result of sb.toString(), the symbolic IP as a String
	 */
	private String convertNumericIpToSymbolic(Integer ip) {
		StringBuffer sb = new StringBuffer(15);
		for (int shift = 24; shift > 0; shift -= 8) {
			// process 3 bytes, from high order byte down.
			sb.append(Integer.toString((ip >>> shift) & 0xff));
			sb.append('.');
		}
		sb.append(Integer.toString(ip & 0xff));
		return sb.toString();
	}

	/**
	 * Get the net mask in symbolic form, i.e. xxx.xxx.xxx.xxx
	 * 
	 * @return the result of sb.toString(), the symbolic netmask as a String
	 */
	public String getNetmask() {
		StringBuffer sb = new StringBuffer(15);
		for (int shift = 24; shift > 0; shift -= 8) {
			// process 3 bytes, from high order byte down.
			sb.append(Integer.toString((netmaskNumeric >>> shift) & 0xff));
			sb.append('.');
		}
		sb.append(Integer.toString(netmaskNumeric & 0xff));
		return sb.toString();
	}

	/**
	 * Calculates the range of hosts and assigns min and max to firstIP and
	 * lastIP
	 * 
	 */
	public void getHostAddressRange() {

		int numberOfBits;
		for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {
			if ((netmaskNumeric << numberOfBits) == 0)
				break;
		}
		Integer numberOfIPs = 0;
		for (int n = 0; n < (32 - numberOfBits); n++) {
			numberOfIPs = numberOfIPs << 1;
			numberOfIPs = numberOfIPs | 0x01;
		}

		Integer baseIP = baseIPnumeric & netmaskNumeric;
		firstIP = convertNumericIpToSymbolic(baseIP + 1);
		lastIP = convertNumericIpToSymbolic(baseIP + numberOfIPs - 1);
	}

	/**
	 * Returns number of hosts available in given range
	 * 
	 * @return number of hosts
	 */
	public Long getNumberOfHosts() {
		int numberOfBits;
		for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {
			if ((netmaskNumeric << numberOfBits) == 0)
				break;
		}
		Double x = Math.pow(2, (32 - numberOfBits));
		if (x == -1) {
			x = 1D;
		}
		return x.longValue();
	}

	/**
	 * Calculates wildcard mask
	 * 
	 * @return the result of sb.toString(), in this case the wilcard mask in
	 *         symbolic form
	 */

	public String getWildcardMask() {
		Integer wildcardMask = netmaskNumeric ^ 0xffffffff;
		StringBuffer sb = new StringBuffer(15);
		for (int shift = 24; shift > 0; shift -= 8) {
			// process 3 bytes, from high order byte down.
			sb.append(Integer.toString((wildcardMask >>> shift) & 0xff));
			sb.append('.');
		}
		sb.append(Integer.toString(wildcardMask & 0xff));
		return sb.toString();

	}

	/**
	 * Calculates the broadcast address
	 * 
	 * @return the broadcast ip address as a String
	 */
	public String getBroadcastAddress() {
		if (netmaskNumeric == 0xffffffff) {
			return "0.0.0.0";
		}
		int numberOfBits;
		for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {
			if ((netmaskNumeric << numberOfBits) == 0)
				break;
		}
		Integer numberOfIPs = 0;
		for (int n = 0; n < (32 - numberOfBits); n++) {
			numberOfIPs = numberOfIPs << 1;
			numberOfIPs = numberOfIPs | 0x01;
		}
		Integer baseIP = baseIPnumeric & netmaskNumeric;
		Integer ourIP = baseIP + numberOfIPs;
		String ip = convertNumericIpToSymbolic(ourIP);
		return ip;
	}

	/**
	 * @param ipCidr
	 *            IP Address and Netmask in CIDR format (e.g. 192.168.1.0/24)
	 * @return IP Address and Symbolic Netmask to be displayed in GUI
	 */
	/*public String showIpNet(String ipCidr) {

		String st[] = ipCidr.split("\\/");
		String symbolicIP = st[0];

		IPCalcView gui = new IPCalcView();

		String symbolicCIDR = st[1];
		Integer numericCIDR = new Integer(symbolicCIDR);
		if (numericCIDR > 32)
			gui.label2.setText("CIDR can not be greater than 32");
		if (numericCIDR < 8)
			gui.label2.setText("Netmask CIDR can not be less than 8");
		netmaskNumeric = 0xffffffff;
		netmaskNumeric = netmaskNumeric << (32 - numericCIDR);
		String symbolicNet = this.getNetmask();

		return "IP Address: " + symbolicIP + "\n" + "Netmask: " + symbolicNet
				+ "\n";

	} // close showIpCidr
*/
	/**
	 * Displays broadcast
	 * 
	 * @return Broadcast string to be displayed in GUI
	 */
	public String showBroadcast() {
		String broadcast = this.getBroadcastAddress();
		return "Broadcast: " + broadcast + "\n";
	}

	/**
	 * Displays Wildcard
	 * 
	 * @return Wildcard string to be displayed in GUI
	 */
	public String showWildcard() {
		String wildcard = this.getWildcardMask();
		return "Wildcard: " + wildcard + "\n";
	}

	/**
	 * Displays number of hosts
	 * 
	 * @return Number of hosts to be displayed in GUI
	 */
	public String showNumHosts() {
		Long numberOfHosts = this.getNumberOfHosts() - 2;
		return "Number of Hosts in Range: " + numberOfHosts + "\n";
	}

	/**
	 * Displays First and Last available IP
	 * 
	 * @return Min and Max available IP to be displayed in GUI
	 */
	public String showFirstAndLast() {
		this.getHostAddressRange();
		String firstIP = this.firstIP;
		String lastIP = this.lastIP;
		return "First Available Host: " + firstIP + "\n" + "Last Available "
				+ "Host: " + lastIP + "\n";
	}

	/**
	 * 扩展获取�?��的IP地址
	 */
	public String getMinIP(){
		this.getHostAddressRange();
		String minIP=this.firstIP;
		return minIP;
	}
	
	/**
	 * 扩展获取�?��的IP地址
	 */
	public String getMaxIP(){
		this.getHostAddressRange();
		String maxIP=this.lastIP;
		return maxIP;
	}
}
