package com.acorn.testanything.LinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * 链表
 */
public class TestLinkedList {
    public static class ListNode {
        int val; //数据域
        ListNode next; //指针域

        public ListNode() {

        }

        public ListNode(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        head.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);

        // 打印反转前的链表
        ListNode h = head;
        while (null != h) {
            System.out.print(h.getVal() + " ");
            h = h.getNext();
        }
        System.out.println("\n**************************");
        // 调用反转方法
        head = reverse1(head);

        System.out.println("\n**************************");
        // 打印反转后的结果
        while (null != head) {
            System.out.print(head.getVal() + " ");
            head = head.getNext();
        }

//        nthToLast(head,4);
    }

    /**
     * 递归方式翻转链表
     *
     * @param head
     * @return
     */
    public static ListNode reverse1(ListNode head) {
        // head看作是前一结点，head.getNext()是当前结点，reHead是反转后新链表的头结点
        if (head == null || head.getNext() == null) {
            System.out.println("return " + head.getVal());
            return head;// 若为空链或者当前结点在尾结点，则直接还回
        }
        ListNode reHead = reverse1(head.getNext());// 先反转后续节点head.getNext()
        System.out.println("reHead " + reHead.getVal() + "," + head.getVal());
        head.getNext().setNext(head);// 将当前结点的指针域指向前一结点
        head.setNext(null);// 前一结点的指针域令为null;
        return reHead;// 反转后新链表的头结点
    }

    /**
     * 遍历方式翻转链表
     */
    ListNode reverse(ListNode node) {
        ListNode prev = null;
        while (node != null) {
            ListNode tmp = node.next;
            node.next = prev;
            prev = node;
            node = tmp;
        }
        return prev;
    }

    /**
     * 判断链表是否有环
     *
     * @param head
     * @return
     */
    boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow, fast;
        fast = head.next;
        slow = head;
        while (fast != slow) {
            if (fast == null || fast.next == null) {
                return false;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return true;
    }

    /**
     * 链表排序
     *
     * @param head
     * @return
     */
    ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = middleNode(head);
        ListNode right = sortList(mid.next);
        mid.next = null;
        ListNode left = sortList(head);
        return merge(left, right);
    }

    ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    ListNode merge(ListNode n1, ListNode n2) {
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        while (n1 != null && n2 != null) {
            if (n1.val < n2.val) {
                node.next = n1;
                n1 = n1.next;
            } else {
                node.next = n2;
                n2 = n2.next;
            }
            node = node.next;
        }
        if (n1 != null) {
            node.next = n1;
        } else {
            node.next = n2;
        }
        return dummy.next;
    }

    /**
     * 链表相加求和
     *
     * @param l1
     * @param l2
     * @return
     */
    ListNode addLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        ListNode head = new ListNode();
        ListNode point = head;
        int carry = 0;
        while (l1 != null && l2 != null) {
            int sum = carry + l1.val + l2.val;
            point.next = new ListNode(sum % 10);
            carry = sum / 10;
            l1 = l1.next;
            l2 = l2.next;
            point = point.next;
        }
        while (l1 != null) {
            int sum = carry + l1.val;
            point.next = new ListNode(sum % 10);
            carry = sum / 10;
            l1 = l1.next;
            point = point.next;
        }
        while (l2 != null) {
            int sum = carry + l2.val;
            point.next = new ListNode(sum % 10);
            carry = sum / 10;
            l2 = l2.next;
            point = point.next;
        }
        if (carry != 0) {
            point.next = new ListNode(carry);

        }
        return head.next;
    }

    /**
     * 得到链表倒数第n个节点
     *
     * @param head
     * @param n
     * @return
     */
   public static ListNode nthToLast(ListNode head, int n) {
        if (head == null || n < 1) {
            return null;
        }
        ListNode l1 = head;
        ListNode l2 = head;
        for (int i = 0; i < n - 1; i++) {
            if (l2 == null) {
                return null;
            }
            l2 = l2.next;
        }
        while (l2.next != null) {
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1;
    }

    /**
     * 删除链表第N个节点
     *
     * @param head
     * @param n
     * @return
     */
    ListNode deletenthNode(ListNode head, int n) {
        // write your code here
        if (n <= 0) {
            return null;
        }
        ListNode dumy = new ListNode(0);
        dumy.next = head;
        ListNode prdDel = dumy;
        for (int i = 0; i < n; i++) {
            if (head == null) {
                return null;
            }
            head = head.next;
        }
        while (head != null) {
            head = head.next;
            prdDel = prdDel.next;
        }
        prdDel.next = prdDel.next.next;
        return dumy.next;
    }

    /**
     * 删除链表中重复元素
     *
     * @param head
     * @return
     */
    ListNode deleteMuNode(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode node = head;
        while (node.next != null) {
            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return head;

    }

    /**
     * 删除链表重复元素2,去掉重复节点
     *
     * @param head
     * @return
     */
    ListNode deleteMuNode2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        head = dummy;
        while (head.next != null && head.next.next != null) {
            if (head.next.val == head.next.next.val) {
                int val = head.next.val;
                while (head.next.val == val && head.next != null) {
                    head.next = head.next.next;
                }
            } else {
                head = head.next;
            }
        }
        return dummy.next;
    }

//    /**
//     * 旋转链表
//     * @param head
//     * @param k
//     * @return
//     */
//    ListNode rotateRight(ListNode head,int k){
//        if(head ==null){
//            return null;
//        }
//        int length = getLength(head);
//        k = k % length;
//        ListNode dummy = new ListNode(0);
//        dummy.next = head;
//        head = dummy;
//        ListNode tail = dummy;
//        for(int i = 0;i<k;i++){
//            head = head.next;
//        }
//        while(head.next!= null){
//            head = head.next;
//            tail = tail.next;
//        }
//        head.next = dummy.next;
//        dummy.next = tail.next;
//        tail.next = null;
//        return dummy.next;
//    }

    /**
     * 重排链表
     *
     * @param head
     * @return
     */
    ListNode reOrder(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode mid = middleNode(head);
        ListNode tail = reverse(mid.next);
        return mergeIndex(head, tail);
    }

    ListNode mergeIndex(ListNode head1, ListNode head2) {
        int index = 0;
        ListNode dummy = new ListNode(0);
        while (head1 != null && head2 != null) {
            if (index % 2 == 0) {
                dummy.next = head1;
                head1 = head1.next;
            } else {
                dummy.next = head2;
                head2 = head2.next;
            }
            dummy = dummy.next;
            index++;
        }
        if (head1 != null) {
            dummy.next = head1;
        } else {
            dummy.next = head2;
        }
        return dummy;
    }


    /**
     * 链表划分
     *
     * @param head
     * @param x
     * @return
     */
    ListNode partition(ListNode head, int x) {
        if (head == null) {
            return null;
        }
        ListNode left = new ListNode(0);
        ListNode right = new ListNode(0);
        ListNode leftDummy = left;
        ListNode rightDummy = right;
        while (head != null) {
            if (head.val < x) {
                left.next = head;
                left = head;
            } else {
                right.next = head;
                right = head;
            }
            head = head.next;
        }
        left.next = rightDummy.next;
        right.next = null;
        return leftDummy.next;
    }

    /**
     * 翻转链表N到M之间的节点
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    ListNode reverseN2M(ListNode head, int m, int n) {
        if (m >= n || head == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        head = dummy;
        for (int i = 1; i < m; i++) {
            if (head == null) {
                return null;
            }
            head = head.next;
        }
        ListNode pmNode = head;
        ListNode mNode = head.next;
        ListNode nNode = mNode;
        ListNode pnNode = mNode.next;
        for (int i = m; i < n; i++) {
            if (pnNode == null) {
                return null;
            }
            ListNode tmp = pnNode.next;
            pnNode.next = nNode;
            nNode = pnNode;
            pnNode = tmp;
        }
        pmNode.next = nNode;
        mNode.next = pnNode;
        return dummy.next;
    }

    /**
     * 合并k个排序过的链表
     *
     * @param k
     * @return
     */
    ListNode mergeKListNode(ArrayList<ListNode> k) {
        if (k.size() == 0) {
            return null;
        }
        return mergeHelper(k, 0, k.size() - 1);
    }

    ListNode mergeHelper(List<ListNode> lists, int start, int end) {
        if (start == end) {
            return lists.get(start);
        }
        int mid = start + (end - start) / 2;
        ListNode left = mergeHelper(lists, start, mid);
        ListNode right = mergeHelper(lists, mid + 1, end);
        return mergeTwoLists(left, right);
    }

    ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                tail.next = list1;
                tail = tail.next;
                list1 = list1.next;
            } else {
                tail.next = list2;
                tail = list2;
                list2 = list2.next;
            }

        }
        if (list1 != null) {
            tail.next = list1;
        } else {
            tail.next = list2;
        }
        return dummy.next;
    }
}
