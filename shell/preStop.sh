#!/bin/sh
ps -ef|grep app.jar |grep -v grep|awk '{print $1}'|xargs kill -15