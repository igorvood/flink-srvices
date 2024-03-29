uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic=dev_ivr__uasp_realtime__input_converter__mortgage__dlq
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic=dev_ivr__uasp_realtime__input_converter__mortgage__uaspdto
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic=dev_ivr__uasp_realtime__mdm_enrichment__mdm_cross_link__status__dlq
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic=dev_ivr__uasp_realtime__input_converter__mdm_cross_link__uaspdto
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic=dev_ivr__uasp_realtime__mdm_enrichment__for_additional_enrichment__uaspdto
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic=dev_ivr__uasp_realtime__input_converter__way4_issuing_operation__uaspdto
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic=dev_ivr__uasp_realtime_way4_mdm_enrichment__uaspdto

uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fieldsList.a1.fromFieldName=is_mortgage
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fieldsList.a1.fromFieldType=Boolean
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fieldsList.a1.isOptionalEnrichValue=true
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fieldsList.a1.toFieldName=is_mortgage
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.inputDataFormat=UaspDtoFormat
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.keySelectorEnrich.isId=true
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.keySelectorMain.isId=true
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.globalEnrichFields.fromFieldName=global_id
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.globalEnrichFields.fromFieldType=String
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.globalEnrichFields.isOptionalEnrichValue=false
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.globalEnrichFields.toFieldName=calculate-mdm_id
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.inputDataFormat=UaspDtoFormat
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.keySelectorEnrich.isId=true
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.keySelectorMain.isId=true
uasp-streaming-mdm-enrichment.savepoint.pref=mdm_enrichment
uasp-streaming-mdm-enrichment.service.name=way4_uasp-streaming-mdm-enrichment
uasp-streaming-mdm-enrichment.sync.parallelism=8

uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.bootstrap.servers=${BOOTSTRAP_SERVERS}
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.security.protocol=SSL
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.key.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.location=C:\\Work\\secret\\kafka-trust.pfx
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.location=C:\\Work\\secret\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.transactional.id=way4
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.transaction.timeout.ms=30000
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.auto.offset.reset=earliest
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.bootstrap.servers=${BOOTSTRAP_SERVERS}
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.fetch.min.bytes=50
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.group.id=way4
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.isolation.level=read_uncommitted
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.security.protocol=SSL
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.session.timeout.ms=72000000
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.ssl.key.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.ssl.keystore.location=C:\\Work\\secret\\kafka-trust.pfx
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.ssl.keystore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.ssl.keystore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.ssl.truststore.location=C:\\Work\\secret\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.ssl.truststore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.CommonEnrichProperty$.fromTopic.cns.ssl.truststore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.bootstrap.servers=${BOOTSTRAP_SERVERS}
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.security.protocol=SSL
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.key.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.location=C:\\Work\\secret\\kafka-trust.pfx
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.location=C:\\Work\\secret\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.transactional.id=way4
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.transaction.timeout.ms=30000
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.auto.offset.reset=earliest
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.bootstrap.servers=${BOOTSTRAP_SERVERS}
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.fetch.min.bytes=50
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.group.id=way4
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.isolation.level=read_uncommitted
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.security.protocol=SSL
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.session.timeout.ms=72000000
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.ssl.key.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.ssl.keystore.location=C:\\Work\\secret\\kafka-trust.pfx
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.ssl.keystore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.ssl.keystore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.ssl.truststore.location=C:\\Work\\secret\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.ssl.truststore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic.cns.ssl.truststore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.bootstrap.servers=${BOOTSTRAP_SERVERS}
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.security.protocol=SSL
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.key.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.location=C:\\Work\\secret\\kafka-trust.pfx
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.keystore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.location=C:\\Work\\secret\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.ssl.truststore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.transactional.id=way4
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.dlq.FlinkSinkProperties$.toTopic.prd.transaction.timeout.ms=30000
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.auto.offset.reset=earliest
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.bootstrap.servers=${BOOTSTRAP_SERVERS}
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.fetch.min.bytes=50
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.group.id=way4
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.isolation.level=read_uncommitted
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.security.protocol=SSL
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.session.timeout.ms=72000000
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.ssl.key.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.ssl.keystore.location=C:\\Work\\secret\\kafka-trust.pfx
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.ssl.keystore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.ssl.keystore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.ssl.truststore.location=C:\\Work\\secret\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.ssl.truststore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic.cns.ssl.truststore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.bootstrap.servers=${BOOTSTRAP_SERVERS}
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.security.protocol=SSL
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.ssl.key.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.ssl.keystore.location=C:\\Work\\secret\\kafka-trust.pfx
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.ssl.keystore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.ssl.keystore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.ssl.truststore.location=C:\\Work\\secret\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.ssl.truststore.password=kafkauasppassword
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.ssl.truststore.type=PKCS12
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.transactional.id=way4
uasp-streaming-mdm-enrichment.enrichOne.MainEnrichProperty$.out.FlinkSinkProperties$.toTopic.prd.transaction.timeout.ms=30000