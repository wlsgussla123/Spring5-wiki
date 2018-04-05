package algo;

import java.util.Iterator;

public class Main {
	public static void main(String args[]) {
		Iterable<Integer> iter = () -> new Iterator<Integer>() {
			int i = 0;
			final static int MAX = 10;

			@Override
			public Integer next() {
				// TODO Auto-generated method stub
				return ++i;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return i < MAX;
			}
		};
		
		for(Integer i : iter) {
			System.out.println(i);
		}
		
		for(Iterator<Integer> it = iter.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}
}
