---
layout: pattern
title: Leader/Followers
folder: leader-followers
permalink: /patterns/leader-followers/
categories: Concurrency
tags:
 - Performance
---

## Intent
The Leader/Followers pattern provides a concurrency model where multiple 
threads can efficiently de-multiplex events and dispatch event handlers 
that process I/O handles shared by the threads.

## Class diagram
![Leader/Followers class diagram](./etc/leader-followers.png)

## Applicability
Use Leader-Followers pattern when

* a system possesses following characteristics:
  * the system must perform tasks in response to external events that occur asynchronously, like hardware interrupts in OS
  * it is inefficient to dedicate separate thread of control to perform synchronous I/O for each external source of event
  * the higher level tasks in the system can be simplified significantly if I/O is performed synchronously.
* one or more tasks in a system must run in a single thread of control, while other tasks may benefit from multi-threading.

## Real world examples

* [ACE Thread Pool Reactor framework](https://www.dre.vanderbilt.edu/~schmidt/PDF/HPL.pdf)
* [JAWS](http://www.dre.vanderbilt.edu/~schmidt/PDF/PDCP.pdf)
* [Real-time CORBA](http://www.dre.vanderbilt.edu/~schmidt/PDF/RTS.pdf)

## Credits

* [Douglas C. Schmidt and Carlos O’Ryan - Leader/Followers](http://www.kircher-schwanninger.de/michael/publications/lf.pdf)
