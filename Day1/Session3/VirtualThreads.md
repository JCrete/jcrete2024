* What is the status of virtual threads in 2024?
* Several participants use/plan to use them in production
* Not replacement for all threads
  * Many misleading blogs out there, e.g. https://www.infoq.com/articles/java-virtual-threads-a-case-study/
* Virtual threads are optimized for concurrent requests that mostly block
  * ...on sockets
  * ...when the number of requests significantly exceeds the number of availabe platform threads
  * ...or to save memory with more modest numbers of concurrent requests
* Support from frameworks is good (Spring Boot, Quarkus etc.)
* Reactive programming style is problematic for business apps
  * virtual threads allow straightforward code 
* Pinning really is observed in the wild, but not hard to detect/fix
* Some participants bemoaned the loss of the thread pool size as a global tuning parameter
* May need better abstractions for rate limiting
* Concern about thread locals that are commonly used in frameworks
* Many (most?) current business apps do not have scalability issues
  * Virtual threads may only bring modest improvements
  * If you aren't currently using reactive, no need to hurry
