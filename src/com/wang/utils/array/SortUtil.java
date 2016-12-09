package com.wang.utils.array;

/**
 * 排序算法
 */
public class SortUtil {

    /***************************
     * 插入排序
     *****************************/
    //直接插入排序
    private static void direct_insert(int[] array) {
        for (int i = 1; i < array.length; i++) {
            //待插入元素
            int temp = array[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                //将大于temp的往后移动一位
                if (array[j] > temp) {
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            array[j + 1] = temp;
        }

    }

    //二分插入排序
    private static void half_insert(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int left = 0, right = i - 1, middle = 0;
            while (left <= right) {
                middle = (right + left) / 2;
                if (array[middle] < temp)
                    left = middle + 1;
                else
                    right = middle - 1;
            }
            for (int j = i - 1; j >= left; j--) {
                array[j + 1] = array[j];
            }
            if (left != i) {
                array[left] = temp;
            }
        }
    }

    /***************************
     * 选择排序
     *****************************/
    //直接选择排序
    private static void direct_select(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = array[i];
            int index = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    index = j;
                }
            }
            array[index] = array[i];
            array[i] = min;
        }
    }

    /***************************
     * 交换排序
     *****************************/
    // 冒泡排序
    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                //这里-i主要是每遍历一次都把最大的i个数沉到最底下去了，没有必要再替换了
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /***************************
     * 希尔排序
     *****************************/
    public class SheelSort {
        private int[] hs;

        private int[] a;

        public void sort() {
            for (int h : hs) {
                for (int i = h; i < a.length; i++) {
                    int in = i;
                    int inValue = a[i];
                    while (in - h > -1 && a[in - h] > inValue) {
                        a[in] = a[in - h];
                        in = in - h;
                    }
                    a[in] = inValue;
                }
            }
        }

        public int[] getHs() {
            return hs;
        }

        public void setHs(int[] hs) {
            this.hs = hs;
        }

        public int[] getA() {
            return a;
        }

        public void setA(int[] a) {
            this.a = a;
        }
    }

    /***************************
     * 快速排序
     *****************************/
    public class SpeedSort {
        private int[] a;

        public void sort() {
            int begin = 0;
            int end = a.length - 1;
            quickSort(begin, end);
            for (int i : a) {
                System.out.print(i + " ");
            }
        }

        private void quickSort(int begin, int end) {
            if (begin >= end) {

            } else {
                int pivot = a[end];
                int result = getPivot(begin, end, pivot);
                quickSort(begin, result - 1);
                quickSort(result + 1, end);
            }

        }

        private int getPivot(int begin, int end, int pivot) {
            begin = begin - 1;
            int o = end;
            while (true) {
                while (a[++begin] < pivot) {

                }
                while (end > 0 && a[--end] > pivot) {

                }
                if (begin >= end) {
                    break;
                } else {
                    swap(begin, end);
                }
            }
            swap(begin, o);
            return begin;
        }

        private void swap(int begin, int end) {
            int t = a[begin];
            a[begin] = a[end];
            a[end] = t;
        }

        public int[] getA() {
            return a;
        }

        public void setA(int[] a) {
            this.a = a;
        }
    }
}
