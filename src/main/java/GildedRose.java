class GildedRose {

    Item[] items;

    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String CONJURED = "Conjured";

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            updateItemQuality(item);
        }
    }

    private void updateItemQuality(Item item) {
        int qualityDegrade = item.name.equals(CONJURED)?-2 : -1;
        boolean isDegrading = !item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL) && !item.name.equals(SULFURAS_HAND_OF_RAGNAROS);
        if (isDegrading) {
            adjustQuality(item, qualityDegrade);
        }
        if (item.name.equals(AGED_BRIE)) {
            adjustQuality(item, 1);
        }
        backStageQualityAdjust(item);

        if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            item.sellIn = item.sellIn - 1;
        }

        if (item.sellIn < 0) {
            if(isDegrading){
                adjustQuality(item, qualityDegrade);
            }
            if (item.name.equals(AGED_BRIE)) {
                adjustQuality(item, 1);
            } else if(item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL)){
                    item.quality = item.quality - item.quality;
            }
        }
    }

    private void backStageQualityAdjust(Item item) {
        if (item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL)) {
            adjustQuality(item, 1);
            if (item.sellIn < 11) {
                int value = 1;
                adjustQuality(item, value);
            }
            if (item.sellIn < 6) {
                adjustQuality(item, 1);
            }
        }
    }

    private void adjustQuality(Item item, int value) {
        int quality = item.quality + value;
        if (quality <= 50 && quality >= 0) {
            item.quality = quality;
        }
    }
}