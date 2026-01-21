package com.kirisamey.tconguns.materials.dynamics;

import com.ibm.icu.impl.Pair;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public abstract class DynamicMaterialStatsGeneratorBase<TMaterialStats extends IMaterialStats, TContext> {
    public void append(Map<MaterialId, Map<MaterialStatsId, IMaterialStats>> map) {
        log.info("<{}> Start calculating mat...", getName());

        Map<MaterialId, Map<MaterialStatsId, IMaterialStats>> statsPatch = map.entrySet().stream().map(entry -> {
            return getContext(entry.getKey(), entry.getValue())
                    .map(ctx -> new ContextInfos<>(entry.getKey(), entry.getValue(), ctx));
        }).filter(Optional::isPresent).collect(Collectors.toMap(
                info -> info.get().matId,
                infoOpt -> {
                    var info = infoOpt.get();
                    var stat = calculateStat(info.context);
                    return Stream.concat(
                            info.statsMap.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())),
                            Stream.of(Pair.of(getMatId(), stat))
                    ).collect(Collectors.toMap(
                            p -> p.first,
                            p -> p.second
                    ));
                }
        ));

        map.putAll(statsPatch);

        log.info("<{}> Calculate mat done.", getName());
    }

    protected abstract String getName();

    protected abstract MaterialStatsId getMatId();

    protected abstract Optional<TContext> getContext(MaterialId mat, Map<MaterialStatsId, IMaterialStats> stats);

    protected abstract @NotNull TMaterialStats calculateStat(TContext context);

    private record ContextInfos<TContext>(MaterialId matId, Map<MaterialStatsId, IMaterialStats> statsMap,
                                          TContext context) {
    }
}
