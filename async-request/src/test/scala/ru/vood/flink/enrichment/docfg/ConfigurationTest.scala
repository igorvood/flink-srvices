package ru.vood.flink.enrichment.docfg

import org.scalatest.flatspec.AnyFlatSpec
import ru.vood.flink.common.generate.ConfigurationChecker
import ru.vood.flink.common.generate.dto.Profile
import ru.vood.flink.enrichment.EnrichmentJob
import ru.vood.flink.enrichment.utils.config.MDMEnrichmentPropsModel
import ru.vood.flink.enrichment.utils.config.MDMEnrichmentPropsModel.appPrefixDefaultName

class ConfigurationTest extends AnyFlatSpec {

  private implicit val profiles: List[Profile] = List(
    Profile("way4"),
    //    Profile("profile-tx-step1"),
    //    Profile("profile-tx-step2"),
    //    Profile("prof-tx-case-71"),
    //    Profile("prof-auth"),
    //    Profile("way4-card-agreement"),
    //    Profile("prof-auth-packNM"),
    //    Profile("case-68"),
    //    Profile("case-68_agrement"),
  )

  behavior of "Create and check Configuration"

  ignore should " check configuration is ok" in {
    val models = ConfigurationChecker.createConfiguration(
      filePrefix = "generated-mdm-enrichment",
      profiles = profiles,
      clazz = EnrichmentJob.getClass,
      combiner = MDMEnrichmentPropsModel,
      propertyPrefix = appPrefixDefaultName)

    //Проверка формирования без ошибок то что ленивое
    models.foreach { prp =>
      prp.allEnrichProperty.commonEnrichProperty.foreach(c => println(c.flatProperty))
      prp.allEnrichProperty.globalIdEnrichProperty.foreach(c => println(c.flatProperty))
    }
  }

}
