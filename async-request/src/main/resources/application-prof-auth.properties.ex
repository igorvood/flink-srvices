mdm-enrichment.sync.parallelism=8

mdm-enrichment.service.name=mdm-enrichment-prof-auth
mdm-enrichment.enrichOne.MainEnrichProperty$.cns.group.id=mdm-enrichment-prof-auth_3

#mdm-enrichment.enrichOne.MainEnrichProperty$.cns.bootstrap.servers=d5uasp-apc003lk.corp.dev.vtb:9092,d5uasp-apc003lk.corp.dev.vtb:9092,d5uasp-apc004lk.corp.dev.vtb:9092,d5uasp-apc018lk.corp.dev.vtb:9092,d5uasp-apc025lk.corp.dev.vtb:9092
#mdm-enrichment.enrichOne.MainEnrichProperty$.cns.security.protocol=SSL

mdm-enrichment.enrichOne.MainEnrichProperty$.cns.bootstrap.servers=172.20.219.153:9092,172.20.219.153:9091
mdm-enrichment.enrichOne.MainEnrichProperty$.cns.security.protocol=PLAINTEXT
mdm-enrichment.enrichOne.MainEnrichProperty$.cns.ssl.key.password=kafkauasppassword
mdm-enrichment.enrichOne.MainEnrichProperty$.cns.ssl.keystore.password=kafkauasppassword
mdm-enrichment.enrichOne.MainEnrichProperty$.cns.ssl.truststore.password=kafkauasppassword
mdm-enrichment.enrichOne.MainEnrichProperty$.cns.ssl.keystore.location=C:\\Work\\SSL\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
mdm-enrichment.enrichOne.MainEnrichProperty$.cns.ssl.truststore.location=C:\\Work\\SSL\\kafka-trust.pfx


mdm-enrichment.producer.property.bootstrap.servers=172.20.219.153:9092,172.20.219.153:9091
mdm-enrichment.producer.property.security.protocol=PLAINTEXT

mdm-enrichment.producer.property.ssl.key.password=kafkauasppassword
mdm-enrichment.producer.property.ssl.keystore.password=kafkauasppassword
mdm-enrichment.producer.property.ssl.truststore.password=kafkauasppassword
mdm-enrichment.producer.property.ssl.keystore.location=C:\\Work\\SSL\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
mdm-enrichment.producer.property.ssl.truststore.location=C:\\Work\\SSL\\kafka-trust.pfx

mdm-enrichment.producer.property.transactional.id=enrichment_prof_auth

mdm-enrichment.enrichOne.MainEnrichProperty$.fromTopic=dev_bevents_streaming_input_convertor_profile_auth_uaspdto
mdm-enrichment.enrichOne.MainEnrichProperty$.toTopicName=dev_bevents_card_agreement_enrich_out_uaspdto
mdm-enrichment.enrichOne.MainEnrichProperty$.dlqTopic=dev_bevents_card_agreement_enrich_dlq

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fromTopic=dev__dko_uasp__card_agreement_converted
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.dlqTopic=dev_rto_batch_ca_customer_card_uaspdto__dlq

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.keySelectorMain.isId=false
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.keySelectorMain.mapType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.keySelectorMain.mapKey=card_number_sha_256
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.keySelectorEnrich.isId=true
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.inputDataFormat=UaspDtoFormat

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.globalEnrichFields.fromFieldName=mdm_id
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.globalEnrichFields.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.globalEnrichFields.toFieldName=calculate-mdm_id
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.globalEnrichFields.isOptionalEnrichValue=false

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a01.fromFieldName=mask_card_number
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a01.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a01.toFieldName=MASK_CARD_NUMBER
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a01.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a02.fromFieldName=customer_id
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a02.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a02.toFieldName=customer_id
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a02.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a03.fromFieldName=source_system_cd
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a03.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a03.toFieldName=source_system_cd
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a03.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a04.fromFieldName=pos_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a04.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a04.toFieldName=pos_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a04.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a05.fromFieldName=account_num
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a05.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a05.toFieldName=account_num
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a05.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a06.fromFieldName=is_virtual_card_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a06.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a06.toFieldName=is_virtual_card_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a06.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a07.fromFieldName=card_expiration_dt
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a07.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a07.toFieldName=card_expiration_dt
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a07.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a08.fromFieldName=payment_system_desc
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a08.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a08.toFieldName=payment_system_desc
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a08.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a09.fromFieldName=card_type_cd
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a09.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a09.toFieldName=card_type_cd
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a09.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a10.fromFieldName=salary_serv_pack_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a10.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a10.toFieldName=salary_serv_pack_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a10.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a11.fromFieldName=salary_project_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a11.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a11.toFieldName=salary_project_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a11.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a12.fromFieldName=salary_account_scheme_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a12.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a12.toFieldName=salary_account_scheme_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a12.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a13.fromFieldName=salary_card_type_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a13.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a13.toFieldName=salary_card_type_flg
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a13.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a14.fromFieldName=contract_card_type_cd
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a14.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a14.toFieldName=contract_card_type_cd
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a14.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a15.fromFieldName=hash_card_number
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a15.fromFieldType=String
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a15.toFieldName=hash_card_number
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.fieldsList.a15.isOptionalEnrichValue=true

mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.auto.offset.reset=earliest
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.bootstrap.servers=172.20.219.153:9092,172.20.219.153:9091
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.security.protocol=PLAINTEXT
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.ssl.key.password=kafkauasppassword
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.ssl.keystore.password=kafkauasppassword
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.ssl.truststore.password=kafkauasppassword
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.ssl.keystore.location=C:\\Work\\SSL\\APD00.13.01-USBP-kafka-cluster-uasp.pfx
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.ssl.truststore.location=C:\\Work\\SSL\\kafka-trust.pfx
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.isolation.level=read_uncommitted
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.ssl.keystore.type=PKCS12
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.ssl.truststore.type=PKCS12
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.session.timeout.ms=72000000
mdm-enrichment.enrichOne.GlobalIdEnrichProperty$.cns.fetch.min.bytes=50