import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GildedRoseTest {
    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @Test
    public void sellInDateDecreases_butQualityCannotBeNegative() {
        GildedRose app = new GildedRose( new Item[]{new Item("foo", 0, 0) });
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("foo", -1, 0));
    }

    @Test
    public void qualityDecreases() {
        GildedRose app = new GildedRose( new Item[]{new Item("foo", 10, 10)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("foo", 9, 9));
    }

    @Test
    public void qualityDecreasesFasterAfterSellInDateExpired() {
        GildedRose app = new GildedRose( new Item[]{new Item("foo", 0, 10)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("foo", -1, 8));
    }

    @Test
    public void item_conjured_decreasesInQuality_twiceTheSpeed() {
        GildedRose app = new GildedRose( new Item[]{new Item("Conjured", 3, 6)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Conjured", 2, 4));
    }

    @Test
    public void item_conjured_decreasesInQuality_twiceTheSpeed_alsoWhenSellInExpired() {
        GildedRose app = new GildedRose( new Item[]{new Item("Conjured", 0, 6)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Conjured", -1, 2));
    }

    @Test
    public void item_AgedBrie_increasesInQuality() {
        GildedRose app = new GildedRose( new Item[]{new Item("Aged Brie", 2, 2)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Aged Brie", 1, 3));
    }

    @Test
    public void item_AgedBrie_increasesInQuality_DoublesWhenOff() {
        GildedRose app = new GildedRose( new Item[]{new Item("Aged Brie", 0, 2)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Aged Brie", -1, 4));
    }

    @Test
    public void item_AgedBrie_cannotGoOver50Quality() {
        GildedRose app = new GildedRose( new Item[]{new Item("Aged Brie", 2, 50)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Aged Brie", 1, 50));
    }

    @Test
    public void item_Sulfuras_neverChanges() {
        GildedRose app = new GildedRose( new Item[]{new Item("Sulfuras, Hand of Ragnaros", 100, 100)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Sulfuras, Hand of Ragnaros", 100, 100));
    }

    @Test
    public void item_BackStagePasses_increasesInQuality_byOneOutside10Days() {
        GildedRose app = new GildedRose( new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 20, 2)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Backstage passes to a TAFKAL80ETC concert", 19, 3));
    }

    @Test
    public void item_BackStagePasses_increasesInQuality_byTwoInside10Days() {
        GildedRose app = new GildedRose( new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 2)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Backstage passes to a TAFKAL80ETC concert", 9, 4));
    }

    @Test
    public void item_BackStagePasses_increasesInQuality_byThreeInside5Days() {
        GildedRose app = new GildedRose( new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 2)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Backstage passes to a TAFKAL80ETC concert", 4, 5));
    }

    @Test
    public void itemBackStagePasses_increasesInQuality_goesToZeroWhenSellInExpires() {
        GildedRose app = new GildedRose( new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)});
        app.updateQuality();
        assertItemEquals(app.items[0], new Item("Backstage passes to a TAFKAL80ETC concert", -1, 0));
    }

    public static void assertItemEquals(Item actual, Item expected) {
        assertEquals(expected.name, actual.name);
        assertEquals(expected.quality, actual.quality);
        assertEquals(expected.sellIn, actual.sellIn);
    }
}
