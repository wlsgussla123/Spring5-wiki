package algo;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	// Observable  Source -> Event/Data -> Observer : Observer를 Observable에 등록. 
	// Observable은 새로운 정보가 발생할 때마다 Observer에 notify한다. (Observer 여러 개 등록 가능)
	
	static class IntObservable extends Observable implements Runnable {
		@Override
		public void run() {
			for(int i=1; i<=10; i++) {
				// 변화가 생겼다는 것을 설정하는 메서드
				// setChaged()를 설정하지 않으면 notify를 실행해도 전파되지 않는다.
				setChanged();
				// 변경 내용을 전파하는 메서드
				notifyObservers(i); // publisher
				// int i = it.next(); 대응관계
			}
		}
	}
	
	public static void main(String args[]) {
		// subscriber (= observer)
		// Observable에게 등록되어 변화가 생기면 전파받는다.
		// update 메서드를 통하여 전파 받은 내용으로 작업을 한다.
		Observer ob = new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				System.out.println(Thread.currentThread().getName() +  " " + arg);
			}
		};
		
		IntObservable io = new IntObservable();
		io.addObserver(ob); // IntObservable이 던지는 이벤트는 ob 옵저버가 받는다.
		
		ExecutorService es = Executors.newSingleThreadExecutor(); // 스레드를 하나 할당 받는다.
		es.execute(io); // Runnable 인터페이스를 구현한 객체를 실행하면 run이 돌아간다.
		// Push 방식으로 observer 패턴을 이용하면 별개의 스레드에서 동작하는 코드를 손쉽게 작성할 수 있다.
		System.out.println("EXIT");
		es.shutdown();
	}
}
