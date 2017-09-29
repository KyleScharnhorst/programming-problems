package com.rightViewBinaryTree;
import java.util.*;

public class RightViewBinaryTree {

    public static class TreeNode {
        public TreeNode _left = null;
        public TreeNode _right = null;
        public int _value = 0;

        public TreeNode(int value) {
            this._value = value;
        }
    }

    private static void addToQueue(TreeNode node, LinkedList<TreeNode> queue) {
        if(node != null && queue != null) {
            queue.add(node);
        }
    }

    private static TreeNode pullFromQueue(LinkedList<TreeNode> queue, ArrayList<Integer> result) {
        if(queue == null || result == null) {
            throw new IllegalArgumentException("Parameters must not be null.");
        }

        TreeNode next_node = null;
        int queue_size = queue.size();
        if(queue_size > 0) {
            next_node = queue.poll();
            if(queue_size == 1) {
                result.add(next_node._value);
            }
        }

        return next_node;
    }

    public static ArrayList<Integer> getRightView(TreeNode head) {
        ArrayList<Integer> result = new ArrayList<>();
        LinkedList<TreeNode> current_queue = new LinkedList<>();
        LinkedList<TreeNode> next_queue = new LinkedList<>();
        LinkedList<TreeNode> tmp_queue; //used for swapping current and next queue
        TreeNode current_node = head;

        result.add(head._value);

        while(true) {
            addToQueue(current_node._left, next_queue);
            addToQueue(current_node._right, next_queue);

            current_node = pullFromQueue(current_queue, result);

            if(current_node == null) {
                tmp_queue = current_queue;
                current_queue = next_queue;
                next_queue = tmp_queue;
                if( (current_node = pullFromQueue(current_queue, result)) == null) {
                    break;
                }
            }
        }

        return result;
    }

    private static void assertResults(ArrayList<Integer> expected, ArrayList<Integer> actual) {
        if (expected.size() != actual.size()) {
            assert false;
        } else {
            for (int i = 0; i < expected.size(); i++) {
                if(expected.get(i) != actual.get(i)) {
                    System.out.println("Expected: " + expected.get(i) + " does not equal actual: " + actual.get(i));
                    assert false;
                }
            }
        }
    }

    public static void main(String[] args) {
        //create test tree
        TreeNode head = new TreeNode(1);
        head._left = new TreeNode(2);
        head._right = new TreeNode(3);
        head._left._left = new TreeNode(4);
        head._left._right = new TreeNode(5);
        head._right._left = new TreeNode(6);
        head._left._right._left = new TreeNode(7);

        //create expected array
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,3,6,7));

        //call function with test tree
        ArrayList<Integer> actual = getRightView(head);
        assertResults(expected, actual);

        System.out.println("Execution finished.");
    }
}
