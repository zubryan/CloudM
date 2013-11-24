package com.github.cloudm.utils;


/**
 * 常量定义
 * 
 * @author andy。wan
 */
public class Constants {

	public static int ZERO=0;

	public static int OS_API=418;

	public static String TEMPLATE_SUFFIX_NAME=".vm";

	public static final String HEAD_USERNAME = "username";

	public static final String HEAD_PASSWORD = "password";

	public static final String INVOKE_CLUSTER = "cluster";
	
	public static final int AVAILABLE=1;

	public static final int UNAVAILABLE=0;
	
	public static final String DNS_RESOLVE="508";

	public static final String TIMEOUT="408";

	public static final String REFUSED="509";

	public static final String OTHERS="other";

	public static class IAAS{
		public static final String INVOKE_QUERYVNC = "queryVNC";
        public static final String INVOKE_QUERYTENANTQUOTA = "queryTenantQuota";
        public static final String INVOKE_UPDATETENANTQUOTA = "updateTenantQuota";
        public static final String INVOKE_CREATE_TENANT = "createTenant";
        public static final String INVOKE_DELETE_TENANT = "deleteTenant";
        public static final String INVOKE_CREATEVM = "createVM";
		public static final String INVOKE_QUERYVM = "queryVM";
		public static final String INVOKE_DELETEVM = "deleteVM";
		public static final String INVOKE_QUERYALLVM = "queryAllVM";
		public static final String INVOKE_CONFIGVM = "configVM";
		public static final String INVOKE_CHANGESTATE = "changeVMState";
		public static final String INVOKE_BACKUPVM = "backupVM";
		public static final String INVOKE_RECOVERYVM = "recoveryVM";
		public static final String INVOKE_CLONEVM = "cloneVM";
		public static final String INVOKE_CREATEVOLUME = "createVolume";
		public static final String INVOKE_CREATELB = "createLB";
		public static final String INVOKE_CREATEFWGROUPWITHVM = "createFwGroupWithVM";
		public static final String INVOKE_ATTACHVOLUME = "attachVolume";
		public static final String INVOKE_CREATEDNS = "createDNS";
		public static final String INVOKE_DELETEDNS = "deleteDNS";
		public static final String INVOKE_QUERYDNS = "queryDNS";
		public static final String INVOKE_UPDATEDNS = "updateDNS";
		public static final String INVOKE_LISTDNS = "listDNS";
		public static final String INVOKE_PUBLICIP = "publicIP";
		public static final String INVOKE_CREATESUBNETWORK = "createSubNetWork";
		public static final String INVOKE_DETACHVOLUME = "detachVolume";
		public static final String INVOKE_DELETEVOLUME = "deleteVolume";
		public static final String INVOKE_QUERYVOLUME = "queryVolume";
		public static final String INVOKE_LISTVOLUME = "listVolume";
		public static final String INVOKE_PUBLICIPLIST = "publicIPList";
		public static final String INVOKE_DELETEPUBIP = "deletePubIP";
		public static final String INVOKE_BOUNDIP = "boundIP";
		public static final String INVOKE_UNBOUNDIP = "unboundIP";
		public static final String INVOKE_BOUNDLBWITHIP = "boundLBWithIP";
		public static final String INVOKE_REMOVELB = "removeLB";
		public static final String INVOKE_ADDVMWITHLB = "addVmWithLB";
		public static final String INVOKE_DELVMWITHLB = "delVmWithLB";
		public static final String INVOKE_VOLUMEFORMAT = "volumeFormat";
		public static final String INVOKE_PUBLICIPDETAIL = "publicIPDetail";
	}
	
	//reboot, stop, start, suspend, resume, pause, unpause, lock, unlock, rescue, unrescue
	public static final String RESUME = "resume";
	
	public static final String START = "start";

	public static final String REBOOT = "reboot";

	public static final String STOP = "stop";

	public static final String SUSPEND = "suspend";
	
	public static class DNS{
		public static final String VM = "vm";
		public static final String LOAD_BALANCER = "load_balancer";
		public static final String PUBLIC_IP = "public_ip";
	}
	
	public static class CLUSTER{
		public static final String DELETED = "deleted";
	}

	public static class HTTP {
		//method
		public static final String GET="GET";
		public static final String POST="POST";
		public static final String DELETE="DELETE";
		public static final String PUT="PUT";
		public static final String TRACE="TRACE";
		public static final String CONTENT_TYPE="Content-Type";
		// http Content type
		public static final String OCTET_XML_TYPE = "application/xml";
		public static final String OCTET_JSON_TYPE = "application/json";
		public final static String OCTET_STREAM_TYPE = "application/octet-stream";
		public final static String PLAIN_TEXT_TYPE = "text/plain";
		public final static String ANY_TYPE = "*/*";
	}
	
}
