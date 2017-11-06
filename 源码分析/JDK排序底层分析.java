/**
 * 排序源码分析
 *
 * 底层技术：二分法，比较器
 * 思路：
 *     Integer[] arr = {e1,e2,....,en,em,....ew};
 *     假设：e1--en为连续区间(可递增，可递减)
 *     ① 判断数组是递增还是递减(根据e2.compareTo(e1)的结果判断前后值大小关系)
 *     ② 得到数组从开始的一段连续递增(假设 e1<e2<...<en,得到[e1,en])或递减(假设 e1>e2>...>en,得到[e1,en])区间
 *     ③ 底层默认递增(升序)，所以如果是e2.compareTo(e1)<0(递减，降序)则会将该区间内的元素位置反转({en,en-1,...,e1})
 *     ④ 得到连续区间(即有序)，剩下的为{em,...,ew},使用二分法为{em,...,ew}这个区间的元素在{e1,en}这个区间找到合适的
 *       位置
 *     ④ {e1,e2,..,n,..en}找到em位置n后，将该位置后的{n+1,...,en}往后移，再将arr[n]=em;
 * 
 * Created by duan2ping on 2017/9/4.
 */

//a：当前数组
//①
ComparableTimSort.sort(a, 0, a.length, null, 0, 0);

//②
static void sort(Object[] a, int lo, int hi, Object[] work, int workBase, int workLen)
// lo：起始位置 0
// hi：终止位置 a.length
// nRemaining：最大下标
int nRemaining  = hi - lo;
// 元素只有一位时直接返回
if (nRemaining < 2)
    return;
// 元素长度小于32时
if (nRemaining < MIN_MERGE) {
    int initRunLen = countRunAndMakeAscending(a, lo, hi); //----> ③
    binarySort(a, lo, hi, lo + initRunLen); //----> ④
    return;
}


//③
/**
 * 获得从0 - n 的一段连续的区间(如果递增则不需要处理(默认升序)，如果递减则将得到的一段区间反转(变成升序))
 */
private static int countRunAndMakeAscending(Object[] a, int lo, int hi) {
    // lo：起始位置 0
    // hi：终止位置 a.length
    // runHi：起始位置+1 1
        int runHi = lo + 1;
        if (runHi == hi)
            return 1;
        // 默认为升序：如果要结果是降序则将重写的比较器方法写成：如果B>A[后面的大于前面的，升序]，则返回<0，
        // 则底层(即下面的if判断)会认为是B<A[降序],则会反转，变成B,A（即实现了升降）；正常的默认升序，利用
        // 这个规则返回相反的值，得到降序
        // A：前面的元素    B：后面的元素
        // B.compareTo(A)<0 ==> B<A ==> 该区间为递减区间(降序)
        if (((Comparable) a[runHi++]).compareTo(a[lo]) < 0) { // Descending
            // 得到lo到hi的一段 后者>前者的长度（即从第一位到第runHi位为递减）
            while (runHi < hi && ((Comparable) a[runHi]).compareTo(a[runHi - 1]) < 0)
                runHi++;
            // 将这递减(降序)区间反转成递增(升序)；也就是利用if的判断，让结果被这个方法反转实现的降序
            reverseRange(a, lo, runHi);
        // 升序
        } else {                              // Ascending
            // 得到lo到hi的一段 后者>前者的长度（即从第一位到第runHi位为递增）
            while (runHi < hi && ((Comparable) a[runHi]).compareTo(a[runHi - 1]) >= 0)
                runHi++;
        }

        return runHi - lo;
    }

④
private static void binarySort(Object[] a, int lo, int hi, int start) {
        // lo：起始位置 0
        // hi：终止位置 a.length
        // initRunLen：从0到n的一段递增或递减的元素连续长度
        // start:lo + initRunLen
        if (start == lo)
            start++;
        //使用二分法查找除了0--initRunLen位置的元素要插入到0--initRunLen的位置
        //例 1，2，3，1，3 ：1,2,3则为0--initRunLen(一段连续递增或递减的区间)
        //现在需要将 最后的2,3在0--initRunLen这个区间找到插入的位置(二分法查找)
        for ( ; start < hi; start++) { 
            // 当前比较器
            Comparable pivot = (Comparable) a[start];
            int left = lo;
            int right = start;
            // 得到当前元素的下标
            while (left < right) {
                // 得到中间下标
                int mid = (left + right) >>> 1;
                // 当前值小于中间值则将right下标缩小成中间下标
                if (pivot.compareTo(a[mid]) < 0)
                    right = mid;
                // 当前值大于中间值则将left下标缩小成中间下标+1
                else
                    left = mid + 1;
            }
            // 连续区间长度 - 后面区间元素插入的位置
            int n = start - left; 
            // 找到插入位置，将元素插入，然后将后面的元素后移
            switch (n) {
                case 2:  a[left + 2] = a[left + 1];
                case 1:  a[left + 1] = a[left];
                         break;
                default: System.arraycopy(a, left, a, left + 1, n);
            }
            // 将插入的位置赋值为当前元素(不连续区间内的值)
            a[left] = pivot;
        }
    }