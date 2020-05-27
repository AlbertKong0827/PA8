import sun.util.resources.cldr.zh.CalendarData_zh_Hans_HK;

import static org.junit.Assert.*;

public class HashTableTest {

    //a random string generator to test the hash table on random inputs
    public static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    @org.junit.Test
    public void insert() {
        HashTable test1 = new HashTable();
        test1.insert("A");
        test1.insert("B");
        test1.insert("C");
        test1.insert("E");
        test1.insert("D");
        assertEquals(20, test1.capacity());
        test1.insert("F");
        assertEquals(40, test1.capacity());
        test1.insert("ok");
        test1.insert("not ok");
        test1.insert("what's up");
        test1.insert("esfoi");
        test1.insert("G");
        test1.insert("hi");
        test1.insert("lol");
        test1.insert("qwerty");
        test1.insert("idk");
        test1.insert("lmao");
        test1.insert("a");
        test1.insert("b");
        test1.insert("how many?");
        assertEquals(true, test1.lookup("esfoi"));
        assertEquals(false, test1.lookup("Z"));

        assertEquals(true, test1.lookup("A"));
        test1.delete("A");
        assertEquals(false, test1.lookup("A"));
        test1.delete("B");
        test1.delete("C");
        test1.delete("E");
        test1.delete("D");
        test1.delete("F");
        test1.delete("ok");
        test1.delete("not ok");
        test1.delete("what's up");
        test1.delete("esfoi");
        test1.delete("G");
        System.out.println(test1.toString());
        assertEquals(false, test1.lookup("A"));
        assertEquals(80, test1.capacity());
        System.out.println(test1.getStatsLog());

        HashTable test2 = new HashTable(10);
        test2.insert("a");
        test2.insert("b");
        test2.insert("c");
        assertEquals(20, test2.capacity());

        HashTable randomTest = new HashTable();
        for (int i = 0; i < 50; i++) {
            String ele = getAlphaNumericString(5);
            randomTest.insert(ele);
        }
        assertEquals(50, randomTest.size());

    }

    @org.junit.Test
    public void delete() {
        HashTable test1 = new HashTable(50);
        test1.insert("a");
        test1.insert("b");
        test1.insert("c");
        test1.insert("d");
        test1.insert("e");
        test1.insert("f");
        test1.insert("g");
        test1.delete("a");
        test1.delete("b");
        test1.delete("c");
        test1.delete("d");
        test1.delete("e");
        test1.delete("f");
        test1.delete("g");
        assertEquals(0, test1.size());

        HashTable test2 = new HashTable();
        test2.insert("a");
        test2.insert("b");
        test2.insert("c");
        test2.delete("a");
        test2.insert("a");
        assertEquals(true, test2.lookup("a"));
        test2.delete("a");
        assertEquals(false, test2.lookup("a"));

        HashTable test3 = new HashTable();
        test3.insert("esofishog");
        test3.insert("frjkgb");
        assertEquals(false, test3.delete("a"));
    }

    @org.junit.Test
    public void lookup() {
        HashTable test1 = new HashTable();
        test1.insert("abcd");
        test1.insert("lke");
        test1.insert("rogn");
        test1.insert("rwn");
        assertEquals(false, test1.lookup("ABCD"));

        HashTable test2 = new HashTable();
        test2.insert("rgkw");
        test2.insert("orgbn");
        assertEquals(true, test2.lookup("rgkw"));
        test2.delete("rgkw");
        assertEquals(false, test2.lookup("rgkw"));

        HashTable test3 = new HashTable();
        test3.insert("bruh");
        test3.insert("bruh2");
        assertEquals(true, test3.lookup("bruh"));
    }

    @org.junit.Test
    public void size() {
        HashTable test1 = new HashTable();
        for (int i = 0; i < 50; i++) {
            String ele = getAlphaNumericString(5);
            test1.insert(ele);
        }
        assertEquals(50, test1.size());

        HashTable test2 = new HashTable(100);
        for (int i = 0; i < 20; i++) {
            String ele = getAlphaNumericString(3);
            test2.insert(ele);
        }
        assertEquals(20, test2.size());

        HashTable test3 = new HashTable();
        test3.insert("a");
        test3.insert("b");
        test3.insert("c");
        test3.delete("b");
        test3.delete("c");
        test3.delete("a");
        assertEquals(0, test3.size());
    }

    @org.junit.Test
    public void capacity() {
        HashTable capacityTest = new HashTable();
        assertEquals(20, capacityTest.capacity());
        capacityTest.insert("a");
        capacityTest.insert("b");
        capacityTest.insert("c");
        capacityTest.insert("d");
        capacityTest.insert("e");
        capacityTest.insert("f");
        assertEquals(40, capacityTest.capacity());
        capacityTest.delete("a");
        capacityTest.delete("b");
        capacityTest.delete("c");
        capacityTest.delete("d");
        capacityTest.delete("e");
        capacityTest.delete("f");
        assertEquals(40, capacityTest.capacity());
    }

    @org.junit.Test
    public void testToString() {
        HashTable test1 = new HashTable(10);
        test1.insert("a");
        test1.insert("grjb");
        assertEquals("| index | table 1 | table 2 |\n" +
                "| 0 | grjb | [NULL] |\n" +
                "| 1 | [NULL] | [NULL] |\n" +
                "| 2 | a | [NULL] |\n" +
                "| 3 | [NULL] | [NULL] |\n" +
                "| 4 | [NULL] | [NULL] |\n", test1.toString());

        test1.insert("lgkn");
        test1.insert("rogen");
        test1.insert("rkjgn");
        assertEquals("| index | table 1 | table 2 |\n" +
                "| 0 | rkjgn | [NULL] |\n" +
                "| 1 | rogen | [NULL] |\n" +
                "| 2 | a | [NULL] |\n" +
                "| 3 | [NULL] | [NULL] |\n" +
                "| 4 | [NULL] | lgkn |\n" +
                "| 5 | [NULL] | [NULL] |\n" +
                "| 6 | [NULL] | [NULL] |\n" +
                "| 7 | [NULL] | [NULL] |\n" +
                "| 8 | [NULL] | grjb |\n" +
                "| 9 | [NULL] | [NULL] |\n", test1.toString());

        HashTable test2 = new HashTable();
        assertEquals("| index | table 1 | table 2 |\n" +
                "| 0 | [NULL] | [NULL] |\n" +
                "| 1 | [NULL] | [NULL] |\n" +
                "| 2 | [NULL] | [NULL] |\n" +
                "| 3 | [NULL] | [NULL] |\n" +
                "| 4 | [NULL] | [NULL] |\n" +
                "| 5 | [NULL] | [NULL] |\n" +
                "| 6 | [NULL] | [NULL] |\n" +
                "| 7 | [NULL] | [NULL] |\n" +
                "| 8 | [NULL] | [NULL] |\n" +
                "| 9 | [NULL] | [NULL] |\n", test2.toString());
    }
}