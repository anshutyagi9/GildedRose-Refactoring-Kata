package com.gildedrose;

import com.gildedrose.domain.Item;

import static com.gildedrose.constants.ApplicationConstants.AGED_BRIE;
import static com.gildedrose.constants.ApplicationConstants.CONJURED;
import static com.gildedrose.constants.ApplicationConstants.BACKSTAGE_PASSES;
import static com.gildedrose.constants.ApplicationConstants.SULFURAS;


class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }


    /**
     * updates the quality of item
     */
    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
        }
    }

    /**
     * updates the quality of item
     * @param item
     */
    private void updateItemQuality(Item item) {
        boolean isExpired = item.sellIn < 1;
        int qualityDegrade = getQualityDegrade(item, isExpired);
        boolean isDegrading = !item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES) && !item.name.equals(SULFURAS);

        if (isDegrading) {
            adjustQuality(item, qualityDegrade);
        }
        if (item.name.equals(AGED_BRIE)) {
            int value = isExpired ? 2 : 1;
            adjustQuality(item, value);
        }
        if (item.name.equals(BACKSTAGE_PASSES)) {
            updateBackStageQuality(item, isExpired);
        }

        if (!item.name.equals(SULFURAS)) {
            item.sellIn = item.sellIn - 1;
        }
    }

    /**
     * updates the quality of back stage item
     * @param item
     * @param isExpired
     */
    private void updateBackStageQuality(Item item, boolean isExpired) {
        adjustQuality(item, 1);
        if (item.sellIn < 11) {
            adjustQuality(item, 1);
        }
        if (item.sellIn < 6) {
            adjustQuality(item, 1);
        }
        if(isExpired){
            item.quality = 0;
        }
    }

    /**
     * get the value by which item quality is degraded
     * @param item
     * @param isExpired
     * @return
     */
    private int getQualityDegrade(Item item, boolean isExpired) {
        int qualityDegrade = item.name.equals(CONJURED)?-2 : -1;
        return isExpired ? qualityDegrade*2 : qualityDegrade;
    }

    /**
     * Adjust the quality if item by value passed
     * @param item
     * @param value
     */
    private void adjustQuality(Item item, int value) {
        int quality = item.quality + value;
        if (quality <= 50 && quality >= 0) {
            item.quality = quality;
        }
    }
}