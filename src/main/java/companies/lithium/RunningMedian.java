package companies.lithium;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;


public class RunningMedian {

  private Double currentMedian;
  //11,4,5,6,2,1,0,9,16
  /*
 11                11
 11,4              7.5        4 |11
 11,4,5             5         4,5|11
 11,4,5,6          5.5       ,4,5|6,11
 11,5,6,6,2         5         2,4,5|6,11
 11,5,6,6,2,1      4.5         1,2,4|5,6,11
 11,5,6,6,2,1,0     4          0,1,2,4|5,6,11




   */
  private PriorityQueue<Double> maxHeap = new PriorityQueue<>();
  private PriorityQueue<Double> minHeap = new PriorityQueue<>(Collections.reverseOrder());

  public static void main(String[] args) {
    RunningMedian rm = new RunningMedian();
    List<Double> list = Arrays.asList(11d, 4d, 5d, 6d, 2d, 1d, 0d, 9d, 16d);
    list.forEach(integer -> {
      rm.offer(integer);
      System.out.println(rm.median());
    });
  }

  private int offer2(Double e) {
    Double max = maxHeap.peek();
    Double min = minHeap.peek();
    Double med = e;
    if (maxHeap.size() > minHeap.size() && max != null) {
      med = max;
    } else if (maxHeap.size() < minHeap.size() && min != null) {
      med = min;
    }

    if (e < med) {
      minHeap.add(e);
    } else {
      maxHeap.add(e);
    }
    return 0;
  }

  private void offer(Double e) {
    int size = maxHeap.size() + minHeap.size();
    if (size == 0) {
      currentMedian = e;
      maxHeap.add(e);
      return;
    }
    if (size == 1) {
      if (e <= currentMedian) {
        minHeap.add(e);
      } else {
        minHeap.add(maxHeap.poll());
        maxHeap.add(e);
      }
      currentMedian = (maxHeap.peek() + minHeap.peek()) / 2;
      return;
    }
    if (e <= currentMedian) {
      minHeap.add(e);
    } else {
      maxHeap.add(e);
    }
    if (Math.abs(maxHeap.size() - minHeap.size()) > 1) {
      if (maxHeap.size() > minHeap.size()) {
        minHeap.add(maxHeap.poll());
      } else {
        maxHeap.add(minHeap.poll());
      }
    }
    if (maxHeap.size() == minHeap.size()) {
        currentMedian = (maxHeap.peek() + minHeap.peek()) / 2;
      } else if (maxHeap.size() > minHeap.size()) {
        currentMedian = maxHeap.peek();
      } else {
        currentMedian = minHeap.peek();
    }
  }

  private Double median() {
    System.out.println("max-"+Arrays.deepToString(maxHeap.toArray()));
    System.out.println("min-"+Arrays.deepToString(minHeap.toArray()));
    return currentMedian;
  }
}
