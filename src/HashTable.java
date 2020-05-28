/*
 * Name: Linghang Kong
 * PID: A16127732
 */


/**
 * Hash table
 *
 * @author Linghang Kong
 * @since May 24th
 */
public class HashTable implements IHashTable {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;
    private static final int DEFAULT_CAPACITY = 20;
    private static final double MAX_LOAD_FACTOR = 0.5;
    private static final int DOUBLESIZE = 2;
    private static final int ALPHABET = 27;
    private static final int LEFTSHIFT = 5;
    private static final int RIGHTSHIFT = 27;
    private static final int ASET = 3;
    private static final int EVICT = 2;

    /* Instance variables */
    private String[] table1, table2; // sub-tables
    private int nElems; // size
    private int expand; // number of rehash times
    private double load_F; // loading factor
    private int eviction; // eviction for each element
    private String logResult;

    /**
     * constructor, initialize two empty array with default size 10, total of 20
     */
    public HashTable() {
        this.table1 = new String[DEFAULT_CAPACITY/DOUBLESIZE];
        this.table2 = new String[DEFAULT_CAPACITY/DOUBLESIZE];
        nElems = 0;
        expand = 0;
        eviction = 0;
        logResult = "";
    }

    /**
     * constructor with given capacity
     * @param capacity total capacity of the two arrays, each is a half of it
     */
    public HashTable(int capacity){
        if (capacity<MIN_INIT_CAPACITY){
            throw new IllegalArgumentException();
        }
        int eachCapacity = capacity/DOUBLESIZE;
        this.table1 = new String[eachCapacity];
        this.table2 = new String[eachCapacity];
        nElems = 0;
        expand = 0;
        eviction = 0;
        logResult = "";
    }

    /**
     * insert a string value into the hash table using hash functions
     * @param value value to insert
     * @return true if the value is successfully inserted, false otherwise
     */
    @Override
    public boolean insert(String value) {
        if (value == null){
            throw new NullPointerException();
        }
        double load_factor = (double)(nElems+1)/table1.length;
        load_F = Math.round(load_factor*100);
        load_F = load_F/100;
        //fill in the values for each rehash, load factor and eviction
        logResult += expand+" "+load_F+" "+eviction+" ";
        eviction = 0;
        if (load_factor > MAX_LOAD_FACTOR){
            rehash();
        }
        if (this.lookup(value)){
            return false;
        }

        int place1 = hashOne(value);
        int place2 = hashTwo(value);
        if(table1[place1] == null){
            table1[place1] = value;
        }else{
            eviction += 1;
            if(table2[place2] != null){
                rehash();
            }
            String temp = table1[place1];
            table1[place1] = value;
            table2[place2] = temp;
        }
        nElems++;
        return true;
    }

    /**
     * helper method for insert
     * @param table the table to start hashing
     * @param value the value to insert
     */
    /*private void insertHelper(String[] table, String value){
        int place;
        String[] another;
        if(table == table1){
            place = hashOne(value);
            another = table2;
        }else if(table == table2){
            place = hashTwo(value);
            another = table1;
        }else{
            return;
        }
        if (table[place] == null){
            table[place] = value;
            nElems ++;
        }else{
            String temp = table[place];
            table[place] = value;
            insertHelper(another,temp);
            eviction += 1; //if the space is occupied, cuckoo the value, increment eviction
        }

    }*/


    /**
     * delete a given value
     * @param value value to delete
     * @return true if successfully deleted
     */
    @Override
    public boolean delete(String value) {
        if (value == null){
            throw new NullPointerException();
        }
        int remove1;
        int remove2;
        boolean result = false;
        if(this.lookup(value)){
            remove1 = hashOne(value);
            remove2 = hashTwo(value);
            if (value.equals(table1[remove1])){
                table1[remove1] = null;
                result = true;
            } else if (value.equals(table2[remove2])){
                table2[remove2] = null;
                result = true;
            }
        }
        nElems --;
        return result;
    }

    /**
     * check whether the given value is in the table
     * @param value value to look up
     * @return true if it is in the table, false otherwise
     */
    @Override
    public boolean lookup(String value) {
        if (value == null){
            throw new NullPointerException();
        }
        if(value.equals(table1[hashOne(value)])||value.equals(table2[hashTwo(value)])){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return nElems;
    }

    /**
     *
     * @return the total capacity of the two tables
     */
    @Override
    public int capacity() {
        return table1.length+table2.length;
    }

    /**
     * Get the string representation of the hash table.
     *
     * Format Example:
     * | index | table 1 | table 2 |
     * | 0 | Marina | [NULL] |
     * | 1 | [NULL] | DSC30 |
     * | 2 | [NULL] | [NULL] |
     * | 3 | [NULL] | [NULL] |
     * | 4 | [NULL] | [NULL] |
     *
     * @return string representation
     */
    @Override
    public String toString() {
        String result = "| index | table 1 | table 2 |"+"\n";
        for(int i = 0; i< table1.length; i++){
            String table1Result;
            String table2Result;
            if(table1[i]==null){
                table1Result = "[NULL]";
            }else{
                table1Result = table1[i];
            }
            if(table2[i]==null){
                table2Result = "[NULL]";
            }else{
                table2Result = table2[i];
            }
            result+= "| "+ i +" | "+table1Result+" | "+table2Result+" |"+"\n";
        }
        return result;
    }

    /**
     * Get the rehash stats log.
     *
     * Format Example: 
     * Before rehash # 1: load factor 0.80, 3 evictions.
     * Before rehash # 2: load factor 0.75, 5 evictions.
     *
     * @return rehash stats log
     */
    public String getStatsLog() {

        String[] logArray = logResult.split(" ");
        String result = "";
        int sum = 0;
        for (int i = 0; i<logArray.length; i+=ASET){
            sum += i;
            if (sum == 0){
                result = "";
            }
            if (i+ASET<logArray.length&&logArray[i].compareTo(logArray[i+ASET])<0){
                result += "Before hash # "+logArray[i+ASET]+": load factor "+
                        logArray[i+1]+", "+logArray[i+EVICT]+" evictions." + "\n";
            }
        }
        return result;
    }

    /**
     * double the size of the table, rehash values into the new tables
     */
    private void rehash() {
        expand += 1;
        String[] newTable1 = new String[table1.length*DOUBLESIZE];
        String[] newTable2 = new String[table2.length*DOUBLESIZE];
        String[] temp1 = table1;
        String[] temp2 = table2;
        table1 = newTable1;
        table2 = newTable2;
        nElems = 0;
        int bucket = 0;
        while(bucket < temp1.length){
            if (temp1[bucket]!= null){
                this.insert(temp1[bucket]);
            }
            if (temp2[bucket]!= null){
                this.insert(temp2[bucket]);
            }
            bucket += 1;
        }
    }


    /**
     * hash function for the first table
     * @param value value to be hashed
     * @return the index of the value in the table
     */
    private int hashOne(String value) {
        int hashVal = 0;
        for (int i = 0; i<value.length(); i++){
            int letter = value.charAt(i);
            hashVal = (hashVal*ALPHABET+letter)%table1.length;
        }
        return hashVal;
    }

    /**
     * hash function for the second table
     * @param value value to be hashed
     * @return the index of the value in the table
     */
    private int hashTwo(String value) {
        int hashVal = 0;
        for (int i = 0; i<value.length(); i++){
            int leftShift = hashVal << LEFTSHIFT;
            int rightShift = hashVal >>> RIGHTSHIFT;
            hashVal = Math.abs((leftShift | rightShift)^value.charAt(i));
        }
        return hashVal%table1.length;
    }
}