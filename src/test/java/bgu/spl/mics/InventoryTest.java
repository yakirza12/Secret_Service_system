package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Inventory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class InventoryTest {
    private Inventory inv;
    @BeforeEach
    public void setUp(){
        inv = Inventory.getInstance();
    }


    /*****VV****/
    @Test public void testLoad() {
        String[] str= {"s1","s2","s3"};
        inv.load(str);
        assertEquals(inv.getGadgets().size(),str.length);
    }

  //**V**//
    @Test public void testGetItem(){
        String s1 = "s1";
        String[] str= {s1};
        assertEquals(false, inv.getItem(s1));
        inv.load(str);
        assertEquals(true, inv.getItem(s1));
    }


}