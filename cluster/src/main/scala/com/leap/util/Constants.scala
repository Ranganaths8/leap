package com.leap.util

import java.util.concurrent.TimeUnit

object Constants {
  val MASTER_WATCHER = "master_watcher"
  val SINGLETON_MANAGER = "singleton"

  val MASTER_CONFIG = "master_config"
  val WORKER_CONFIG = "worker_config"
  val UI_CONFIG = "leap_ui"
  val LINUX_CONFIG = "linux_config" // linux or Mac

  val MASTER = "master"
  val WORKER = "worker"

  val LEAP_WORKER_SLOTS = "leap.worker.slots"
  val LEAP_EXECUTOR_PROCESS_LAUNCHER = "leap.worker.executor-process-launcher"
  val LEAP_SCHEDULING_SCHEDULER = "leap.scheduling.scheduler-class"
  val LEAP_SCHEDULING_REQUEST = "leap.scheduling.requests"
  val LEAP_TRANSPORT_SERIALIZER = "leap.transport.serializer"
  val LEAP_SERIALIZER_POOL = "leap.serialization-framework"
  val LEAP_SERIALIZERS = "leap.serializers"
  val LEAP_TASK_DISPATCHER = "leap.task-dispatcher"
  val LEAP_CLUSTER_MASTERS = "leap.cluster.masters"
  val LEAP_MASTERCLIENT_TIMEOUT = "leap.masterclient.timeout"
  val LEAP_CLUSTER_EXECUTOR_WORKER_SHARE_SAME_PROCESS =
    "leap.worker.executor-share-same-jvm-as-worker"

  val LEAP_HOME = "leap.home"
  val LEAP_FULL_SCALA_VERSION = "leap.binary-version-with-scala-version"
  val LEAP_HOSTNAME = "leap.hostname"
  val LEAP_APPMASTER_ARGS = "leap.appmaster.vmargs"
  val LEAP_APPMASTER_EXTRA_CLASSPATH = "leap.appmaster.extraClasspath"
  val LEAP_EXECUTOR_ARGS = "leap.executor.vmargs"
  val LEAP_EXECUTOR_EXTRA_CLASSPATH = "leap.executor.extraClasspath"
  val LEAP_LOG_DAEMON_DIR = "leap.log.daemon.dir"
  val LEAP_LOG_APPLICATION_DIR = "leap.log.application.dir"
  val HADOOP_CONF = "hadoopConf"

  // Id used to identity Master JVM process in low level resource manager like YARN.
  // In YARN, it means the container Id.
  val LEAP_MASTER_RESOURCE_MANAGER_CONTAINER_ID =
    "leap.master-resource-manager-container-id"

  // Id used to identity Worker JVM process in low level resource manager like YARN.
  // In YARN, it means the container Id.
  val LEAP_WORKER_RESOURCE_MANAGER_CONTAINER_ID =
    "leap.worker-resource-manager-container-id"

  // true or false
  val LEAP_REMOTE_DEBUG_EXECUTOR_JVM = "leap.remote-debug-executor-jvm"
  val LEAP_REMOTE_DEBUG_PORT = "leap.remote-debug-port"

  // Whether to turn on GC log, true or false
  val LEAP_VERBOSE_GC = "leap.verbose-gc"

  // The time out for Future, like ask.
  // !Important! This global timeout setting will also impact the UI
  // responsive time if set to too big. Please make sure you have
  // enough justification to change this global setting, otherwise
  // please use your local timeout setting instead.
  val FUTURE_TIMEOUT = akka.util.Timeout(15, TimeUnit.SECONDS)

  val LEAP_START_EXECUTOR_SYSTEM_TIMEOUT_MS = "leap.start-executor-system-timeout-ms"

  val APPMASTER_DEFAULT_EXECUTOR_ID = -1

  val NETTY_BUFFER_SIZE = "leap.netty.buffer-size"
  val NETTY_MAX_RETRIES = "leap.netty.max-retries"
  val NETTY_BASE_SLEEP_MS = "leap.netty.base-sleep-ms"
  val NETTY_MAX_SLEEP_MS = "leap.netty.max-sleep-ms"
  val NETTY_MESSAGE_BATCH_SIZE = "leap.netty.message-batch-size"
  val NETTY_FLUSH_CHECK_INTERVAL = "leap.netty.flush-check-interval"
  val NETTY_TCP_HOSTNAME = "akka.remote.netty.tcp.hostname"
  val NETTY_DISPATCHER = "leap.netty.dispatcher"

  val LEAP_USERNAME = "leap.username"
  val LEAP_APPLICATION_ID = "leap.applicationId"
  val LEAP_MASTER_STARTTIME = "leap.master.starttime"
  val LEAP_EXECUTOR_ID = "leap.executorId"
  // Application jar property
  val LEAP_APP_JAR = "leap.app.jar"
  val LEAP_APP_NAME_PREFIX = "leap.app.name.prefix"

  // Where the jar is stored at. It can be a HDFS, or a local disk.
  val LEAP_APP_JAR_STORE_ROOT_PATH = "leap.jarstore.rootpath"

  // Uses java property -Dgearpump.config.file=xxx.conf to set customized configuration
  // Otherwise application.conf in classpath will be loaded
  val LEAP_CUSTOM_CONFIG_FILE = "leap.config.file"

  // Metrics related
  val LEAP_METRIC_ENABLED = "leap.metrics.enabled"
  val LEAP_METRIC_SAMPLE_RATE = "leap.metrics.sample-rate"
  val LEAP_METRIC_REPORT_INTERVAL = "leap.metrics.report-interval-ms"
  val LEAP_METRIC_GRAPHITE_HOST = "leap.metrics.graphite.host"
  val LEAP_METRIC_GRAPHITE_PORT = "leap.metrics.graphite.port"
  val LEAP_METRIC_REPORTER = "leap.metrics.reporter"

  // Retains at max @RETAIN_HISTORY_HOURS history data
  val LEAP_METRIC_RETAIN_HISTORY_DATA_HOURS = "leap.metrics.retainHistoryData.hours"

  // Time interval between two history data points.
  val LEAP_RETAIN_HISTORY_DATA_INTERVAL_MS = "leap.metrics.retainHistoryData.intervalMs"

  // Retains at max @RETAIN_LATEST_SECONDS recent data points
  val LEAP_RETAIN_RECENT_DATA_SECONDS = "leap.metrics.retainRecentData.seconds"

  // time interval between two recent data points.
  val LEAP_RETAIN_RECENT_DATA_INTERVAL_MS = "leap.metrics.retainRecentData.intervalMs"

  // AppMaster will max wait this time until it declare the resource cannot be allocated,
  // and shutdown itself
  val LEAP_RESOURCE_ALLOCATION_TIMEOUT = "leap.resource-allocation-timeout-seconds"

  // Service related
  val LEAP_SERVICE_HTTP = "leap.services.http"
  val LEAP_SERVICE_HOST = "leap.services.host"
  val LEAP_SERVICE_SUPERVISOR_PATH = "leap.services.supervisor-actor-path"
  val LEAP_SERVICE_RENDER_CONFIG_CONCISE = "leap.services.config-render-option-concise"

  // Security related
  val LEAP_KEYTAB_FILE = "leap.keytab.file"
  val LEAP_KERBEROS_PRINCIPAL = "leap.kerberos.principal"

  val LEAP_METRICS_MAX_LIMIT = "leap.metrics.akka.max-limit-on-query"
  val LEAP_METRICS_AGGREGATORS = "leap.metrics.akka.metrics-aggregator-class"

  val LEAP_UI_SECURITY = "leap.ui-security"
  val LEAP_UI_SECURITY_AUTHENTICATION_ENABLED = "leap.ui-security.authentication-enabled"
  val LEAP_UI_AUTHENTICATOR_CLASS = "leap.ui-security.authenticator"
  // OAuth Authentication Factory for UI server.
  val LEAP_UI_OAUTH2_AUTHENTICATOR_ENABLED = "leap.ui-security.oauth2-authenticator-enabled"
  val LEAP_UI_OAUTH2_AUTHENTICATORS = "leap.ui-security.oauth2-authenticators"
  val LEAP_UI_OAUTH2_AUTHENTICATOR_CLASS = "class"
  val LEAP_UI_OAUTH2_AUTHENTICATOR_CALLBACK = "callback"
  val LEAP_UI_OAUTH2_AUTHENTICATOR_CLIENT_ID = "clientid"
  val LEAP_UI_OAUTH2_AUTHENTICATOR_CLIENT_SECRET = "clientsecret"
  val LEAP_UI_OAUTH2_AUTHENTICATOR_DEFAULT_USER_ROLE = "default-userrole"
  val LEAP_UI_OAUTH2_AUTHENTICATOR_AUTHORIZATION_CODE = "code"
  val LEAP_UI_OAUTH2_AUTHENTICATOR_ACCESS_TOKEN = "accesstoken"

  val PREFER_IPV4 = "java.net.preferIPv4Stack"

  val APPLICATION_EXECUTOR_NUMBER = "leap.application.executor-num"

  val APPLICATION_TOTAL_RETRIES = "leap.application.total-retries"

  val AKKA_SCHEDULER_TICK_DURATION = "akka.scheduler.tick-duration"
}
