# Builder Pattern 설명
## Why
- 생성자에 많은 인자가 있는 경우
- 객체 생성 시 선택 인자가 있는 경우(여러 생성자를 갖게 됨)

👉 각 인자가 어떤 값을 나타내는지 어려워 가독성이 안좋아짐
### Sol1. 점층적 생성자 패턴
오버로딩을 통해 여러 생성자를 이용하는 방법

👉 여전히 각 인자가 어떤 값을 나타내는지 몰라 가독성 면에서 단점이 존재
### Sol2. 자바빈 패턴
setter 메소드를 이용해 값을 설정하는 방법(setter 메소드는 this를 리턴)

👉 set**Name**() 과 같은 메소드를 사용해 가독성 문제는 해결되었지만 동시에 해당 객체에 접근할 수 있는 환경이라면?(ex. 멀티스레드 환경) **객체의 일관성이 깨질 수 있음** 또, 각 멤버변수와 객체 자체가 **immutable할 수 없음**
## How
점층적 생성자 패턴의 장점과 자바빈 패턴의 장점을 합쳐보자!
### 흐름
1. 클라이언트는 **필수 인자**를 전달해 _**빌더**_ 객체 생성(생성자 호출)

2. 빌더 개체의 **setter 메소드를 이용해 선택 인자** 전달

3. build() 메소드를 통해 빌더 객체 내부에서 Product 객체를 생성, return
### 효과
- 1, 2로 필수 인자와 선택 인자를 구분해 선택 인자는 **가독성**이 좋은 코드로 넘길 수 있음
- 객체를 생성하는 방식과 객체를 표현하는 방법을 분리해 객체 생성을 유연하게 할 수 있음
- Product 자체의 생성은 build() 함수에서 한 번에 진행되기 때문에 **atomicity, thread-safety** 보장
## What
이와 같이 동작하는 Builder Pattern이란,
> 복잡한 객체를 생성하는 방식 / 객체를 표현하는 방법을 분리해 다양한 생성 절차에서 서로 다른 표현 결과를 만들 수 있게 하는 패턴

즉, 복잡한 객체의 **단계적 생성**에 중점을 둔 패턴임
![](https://images.velog.io/images/cchloe2311/post/32f15f6e-177a-4be0-8e97-0bfc64c831e2/image.png)
### 구현
- Java
    - static nested class로 Builder 클래스 정의
    - newInstance() 매개변수로 필수 인자 전달, setter 메서드로 선택 인자 전달
    - Product(Employee) 객체를 volatile 키워드로 선언해 가시성 보장

- Kotlin
    - named argument를 제공해 굳이 Builder 클래스를 구현/사용하지 않아도 됨
    - Builder 객체를 전달받아 생성자를 호출하는 secondary constructor 정의
    - nested class로 Builder 클래스 구현
    - 필수 인자는 Builder 클래스 생성자를 통해 전달, 선택 인자는 build 메소드의 블럭으로 넘겨줌
    
### 구현 결과
[BuilderPatterm - github.com/cchloe2311](https://github.com/cchloe2311/BuilderPattern)
- Java 결과화면
![](https://images.velog.io/images/cchloe2311/post/df36c8c8-30ff-4af1-b881-c29afe02f32f/image.png)
- Kotlin 결과화면
![](https://images.velog.io/images/cchloe2311/post/f3152171-ea57-41ed-8b41-d0db489ed36d/image.png)

👉 **atomicity, thread-safety** 보장 확인!
# ✍️ ref
https://www.geeksforgeeks.org/builder-pattern-in-java/

https://stackoverflow.com/questions/36140791/how-to-implement-builder-pattern-in-kotlin
