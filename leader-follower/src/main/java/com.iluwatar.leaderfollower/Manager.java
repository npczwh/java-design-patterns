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
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the Workstation for the leader Follower pattern. Contains a leader and a list of idle
 * workers. Contains a leader who is responsible for receiving work when it arrives. This class also
 * provides a mechanism to set the leader. A worker once he completes his task will add himself back
 * to the station.
 */
public class Manager {

  private Worker leader;
  private List<Worker> workers = new CopyOnWriteArrayList<>();

  public Manager() {

  }

  /**
   * Create workers and set leader.
   */
  public void createWorkers(int numberOfWorkers, TaskSet taskSet, TaskHandler taskHandler) {
    for (int id = 1; id <= numberOfWorkers; id++) {
      Worker worker = new Worker(taskSet, workers, id, this, taskHandler);
      workers.add(worker);
    }
    leader = workers.get(0);
  }

  public Worker getLeader() {
    return leader;
  }

  public void setLeader(Worker leader) {
    this.leader = leader;
  }

  /**
   * Add a worker.
   */
  public void addWorker(Worker worker) {
    if (this.workers.size() <= 0) {
      workers.add(worker);
    }
  }

  public List<Worker> getWorkers() {
    return workers;
  }
}
