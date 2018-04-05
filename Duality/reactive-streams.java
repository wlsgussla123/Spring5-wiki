package algo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class PubSub {
	public static void main(String[] args) throws InterruptedException {
		Iterable<Integer> iter = Arrays.asList(1,2,3,4,5);
		
		Publisher p = new Publisher() {
			@Override
			public void subscribe(Subscriber subscriber) {
				Iterator<Integer> it = iter.iterator();
				
				subscriber.onSubscribe(new Subscription() {
					@Override
					public void request(long n) {
						int i = 0;
						try {
							while(i++ < n) {
								if(it.hasNext()) {
									subscriber.onNext(it.next());
								} else {
									subscriber.onComplete();
									break;
								}
							}							
						} catch(RuntimeException e) {
							subscriber.onError(e);
						}							
					}
					
					@Override
					public void cancel() {
						
					}
				});
			}
		};
		
		// 오버라이딩 할 메서드 4가지는 reactive-streams의 프로토콜이다.
		// onSubscribe는 subscribe 하는 즉시 호출해야 한다.
		// onNext는 자유
		// onError | onComplete
		Subscriber<Integer> s = new Subscriber<Integer>() {
			Subscription subscription;
			
			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("onSubscribe");
				this.subscription = subscription;
				this.subscription.request(1);
			}
			
			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("onError");
			}

			@Override
			public void onNext(Integer n) {
				System.out.println("onNext : " + n);
				this.subscription.request(1);
			}

		};

		p.subscribe(s); // subscriber는 publisher를 구독해야 한다.
	}
}
