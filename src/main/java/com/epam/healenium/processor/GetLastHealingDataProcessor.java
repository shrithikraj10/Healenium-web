package com.epam.healenium.processor;

import com.epam.healenium.model.LastHealingDataDto;
import com.epam.healenium.model.Locator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Get Last Healing Data processor to heal element
 */
@Slf4j
public class GetLastHealingDataProcessor extends BaseProcessor {

    public GetLastHealingDataProcessor(BaseProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public boolean validate() {
        return context.getNoSuchElementException() != null || "findElements".equals(context.getAction());
    }

    @Override
    public void execute() {
        String currentUrl = engine.getCurrentUrl();
        LastHealingDataDto lastHealingDataDto = restClient.getLastHealingData(
                context.getPageAwareBy().getBy(), currentUrl)
                .orElse(new LastHealingDataDto().setPaths(new ArrayList<>()));
        context.setLastHealingData(lastHealingDataDto);
        context.setCurrentUrl(currentUrl);
        Locator userLocator = restClient.getMapper().byToLocator(context.getPageAwareBy().getBy());
        context.setUserLocator(userLocator);
    }
}
