package com.nissan.com.nissan.cracking.the.code.interview;

/**
 * This file defines a HashTable class. Keys and values in the table are of type String. Keys cannot
 * be null. The default constructor creates a table that initially has 64 locations, but a different
 * initial size can be specified as a parameter to the constructor. The table increases in size if
 * it become more than 3/4 full; to help implement this, a count of the number of items in the has
 * table is kept in an instance variable.
 *
 * @author vybt
 */
public class HashTable {

  public static final double THRESHOT_TO_INCREASE_SIZE = 0.75;

  /**
   * Keys that have the same hash code are placed together in a linked list. This private nested
   * class is used internally to implement linked lists. A List Node hold a (key, value) pair.
   */
  private static class ListNode {
    String key;
    String value;
    ListNode next; // Pointer to next node in the list;
                   // A null marks the end of the list.
  }

  private ListNode[] table; // the hash table, represented as
                            // an array of linked lists.
  private int count; // the number of (key, value) pairs in the hash table.

  /**
   * Create a hash table with an initial size of 64.
   */
  public HashTable() {
    table = new ListNode[64];
  }

  /**
   * Create a hash table with a specified initial size. Precondition: initalSize > 0
   */
  public HashTable(int initialSize) {
    if (initialSize <= 0)
      throw new IllegalArgumentException("Illegal table size");
    table = new ListNode[initialSize];
  }

  /**
   * This method is NOT part of the usual interface for a has table. It is here only to be used for
   * testing purposes, and should be removed before the class is released for general use. This
   * lists the (key,value) pair in each location of the table.
   */
  void dump() {
    System.out.println();
    for (int i = 0; i < table.length; i++) {
      // Print out the location number and the list of
      // key, value pair in this location
      System.out.print(i + ":");
      ListNode list = table[i]; // For traversing linked list number i.
      while (list != null) {
        System.out.print(" (" + list.key + "," + list.value + ")");
        list = list.next;
      }
      System.out.println();
    }
  } // end dump

  /**
   * Associate the specified value with the specified key. Precondition: the key is not null.
   */
  public void put(String key, String value) {

    assert key != null : "The key must be non-null";

    int bucket = hash(key); // Which location should this key be in?

    ListNode list = table[bucket]; // For traversing the linked list
                                   // at the appropriate location
    while (list != null) {
      // Search the nodes in the list, to see if the key already exists.
      if (list.key.equals(key))
        break;
      list = list.next;
    }

    // At this point, the list is either null, or list.key.equals(key)

    if (list != null) {
      // Since the list is not null, we have found the key.
      // Just change the associated value.
      list.value = value;
    } else {
      // Since the list is null, the key is not already in the list.
      // Add a new node at the head of the list to contain the
      // new key and its associated value.
      if (count >= THRESHOT_TO_INCREASE_SIZE * table.length) {
        // The table is becoming too full. Increase its size
        // before adding the new node.
        resize();
        bucket = hash(key); // Recompute hash code, since it
                            // depends on the table size.
      }
      ListNode newNode = new ListNode();
      newNode.key = key;
      newNode.value = value;
      newNode.next = table[bucket];
      table[bucket] = newNode;
      count++; // Count the newly added key
    }
  }

  /**
   * Retrieve a associated value with specific key in the table, If there is any. If not, the value
   * null will be returned.
   * 
   * @param key The key whose associated value we want to find
   * @return the associated value, or null if there is no associated value.
   */
  public String get(String key) {
    int bucket = hash(key); // At what location should the key be?

    ListNode list = table[bucket]; // For traversing the table.
    while (list != null) {
      // Check if the specified key is in the node that
      // list points to. If so, return the associated value.
      if (list.key.equals(key)) {
        return list.value;
      }
      list = list.next; // move to next node in the list
    }

    // If we get to this point, the we have looked at every
    // node in the list without finding the key. Return
    // the value null to indicate that the key is not in the
    // table.
    return null;
  }

  /**
   * Remove the key and its associated value from the table, if the key occurs in the table. If it
   * does not occur, then nothing is done
   */
  public void remove(String key) {
    int bucket = hash(key); // At what location should the key be?

    if (table[bucket] == null) {
      // There is no key in that location, so key does not
      // occur in the table. There is nothing to do, so return.
      return;
    }

    if (table[bucket].equals(key)) {
      // If the key is the first node on the list, then
      // table[bucket] must be changed to eliminate the
      // first node from the list
      table[bucket] = table[bucket].next;
      count--;
      return;
    }

    // We have to remove a node from somewhere in the middle.
    // of the list, or at the end. Use a pointer to traverse
    // the list, looking for a node that contains the specified key,
    // and remove it if it is found.
    ListNode prev = table[bucket]; // the node that precedes curr in the list, Prev.next is always
                                   // equal to curr

    ListNode curr = prev.next; // For traversing the list,
    // Starting from the second node.

    while (curr != null && !curr.key.equals(key)) {
      curr = curr.next;
      prev = curr;
    }

    // If we get to this point, then either the curr is null
    // or curr.key.equal(key). In the latter, case
    // we have to remove key from the list. Do this
    // by making prev.next point to the node after curr,
    // Instead of curr. If curr is null, it means that
    // the key is not found in the table, so there is nothing
    // to do.

    if (curr != null) {
      prev.next = curr.next;
      count--;
    }
  } // end of remove
  
  /**
   * Test whether the specified key has an associated value in the table.
   * @param key The key that we want to search for.
   * @return true if the key exists in the table, false if not
   */
  public boolean containsKey(String key) {
    
    int bucket = hash(key); // In which location should the key be?
    
    ListNode list = table[bucket]; // For traversing the list
    while (list != null) {
      // If we find the key in this node, return true
      
      if (list.key.equals(key)) {
        return true;
      }
      list = list.next;
    }
    
    // If we get to this point, we know that there is no node contains
    // the specified key
    return false;
  }
  
  /**
   * Return the number of key,value pair in the table
   */
  public int size() {
    return count;
  }
  
  /**
   * Compute the hash code for key; key cannot be null
   * The hash code depends on the size of the table
   * as well as the value on the value returned key.hashCode()
   */
  private int hash(Object key) {
    return (Math.abs(key.hashCode())) % table.length;
  }
  
  /**
   * Double the size of the table, and redistribute the
   * key/value pairs to their proper locations in the
   * new table.
   */
  public void resize() {
    ListNode[] newTable = new ListNode[table.length * 2];
    for (int i = 0; i < table.length; i++) {
      // Move all the node in the linked list number i to the table.
      // No new LinkNode created. The existing ListNode for each
      // key,value pair is moved to the newTable. This is done by changing
      // the 'next' pointer in the node and by making a pointer
      // in the new table point to the node.
      ListNode list = table[i]; // For traversing linked list number if
      while (list != null) {
        // Move the node pointed to by list to the new table
        ListNode next = list.next; // this is the next node of the list.
        // remember it, before changing the value of list!
        int hash =  (Math.abs(list.key.hashCode()) % newTable.length); 
        // hash is the hash code of list.key that is
        // appropriate for the new table size. The next two lines
        // add the node pointed to by list 
        // onto to head of the linked list in the new table
        // at position number hash.
        list.next = newTable[hash];
        newTable[hash] = list;
        list = next; // Move on the next node in the OLD table.
      }
    }
    table = newTable; // replace the table with the new table.
  } // end resize()
 
}
