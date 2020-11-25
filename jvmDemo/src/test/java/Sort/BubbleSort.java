package Sort;


//冒泡排序算法
public class BubbleSort {

    public static void main(String[] args) {

        int[] num = {5,-1,3,45,23,-16,25,98,-122,67};

        int temp;
        for (int i = 0; i < num.length - 1 ; i++) {

            for (int j = 0; j < num.length-i-1; j++) {

                if (num[j] > num[j+1]) {
                    temp = num[j];
                    num[j] = num[j+1];
                    num[j+1]=temp;
                }

            }

        }

        for (int i : num) {
            System.out.print(i+" ");
        }

    }

}
