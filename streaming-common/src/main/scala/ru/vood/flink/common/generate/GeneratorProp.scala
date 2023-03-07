package ru.vood.flink.common.generate

import ru.vood.flink.common.generate.dto.{CreationEnvProp, Profile, StandDTO}


case class GeneratorProp(creationEnvProp: CreationEnvProp,
                         stand: StandDTO,
                         profile: Profile
                        )
