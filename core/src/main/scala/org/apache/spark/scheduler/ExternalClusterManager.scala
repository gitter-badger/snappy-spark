/*
 * Copyright (c) 2016 SnappyData, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */

package org.apache.spark.scheduler

import org.apache.spark.SparkContext

/**
 * A cluster manager interface to plugin external scheduler.
 *
 */
trait ExternalClusterManager {

  /**
   * Check if this cluster manager instance can create scheduler components
   * for a certain master URL.
   * @param masterURL the master URL
   * @return True if the cluster manager can create scheduler backend/
   */
  def canCreate(masterURL : String): Boolean

  /**
   * Create a task scheduler instance for the given SparkContext
   * @param sc SparkContext
   * @return TaskScheduler that will be responsible for task handling
   */
  def createTaskScheduler (sc: SparkContext): TaskScheduler

  /**
   * Create a scheduler backend for the given SparkContext and scheduler. This is
   * called after task scheduler is created using [[ExternalClusterManager.createTaskScheduler()]].
   * @param sc SparkContext
   * @param scheduler TaskScheduler that will be used with the scheduler backend.
   * @return SchedulerBackend that works with a TaskScheduler
   */
  def createSchedulerBackend (sc: SparkContext, scheduler: TaskScheduler): SchedulerBackend

  /**
   * Initialize task scheduler and backend scheduler. This is called after the
   * scheduler components are created
   * @param scheduler TaskScheduler that will be responsible for task handling
   * @param backend SchedulerBackend that works with a TaskScheduler
   */
  def initialize(scheduler: TaskScheduler, backend: SchedulerBackend): Unit
}
