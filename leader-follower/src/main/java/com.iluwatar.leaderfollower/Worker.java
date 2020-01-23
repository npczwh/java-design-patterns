/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.leaderfollower;

import java.util.List;
import java.util.Objects;

public class Worker implements Runnable {

  private final TaskSet taskSet;
  private List<Worker> workers;
  private final long id;
  private final Manager manager;
  private final TaskHandler taskHandler;

  /**
   * Constructor to create a worker which will take work from the work station.
   */
  public Worker(TaskSet queue, List<Worker> workers, long id, Manager manager,
      TaskHandler taskHandler) {
    super();
    this.taskSet = queue;
    this.workers = workers;
    this.id = id;
    this.manager = manager;
    this.taskHandler = taskHandler;
  }

  /**
   * Become the leader, and notify others.
   */
  public void becomeLeader() {
    synchronized (manager) {
      manager.notifyAll();
    }
  }

  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        if (manager.getLeader() != null && !manager.getLeader().equals(this)) {
          synchronized (manager) {
            manager.wait();
          }
        }

        workers.remove(this);
        System.out.println("Leader: " + id);
        Task task = taskSet.getTask();
        if (workers.size() > 0) {
          manager.getWorkers().get(0).becomeLeader();
          manager.setLeader(manager.getWorkers().get(0));
        } else {
          manager.setLeader(null);
        }
        synchronized (manager) {
          manager.notifyAll();
        }
        taskHandler.handleTask(task);
        // Thread.sleep(100);
        System.out.println("The Worker with the ID " + id + " completed the task");
        manager.addWorker(this);
      } catch (InterruptedException e) {
        System.out.println("Worker interuppted");
        return;
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Worker)) {
      return false;
    }
    Worker worker = (Worker) o;
    return id == worker.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
