#!/usr/bin/env bash

function find_idle_profile() {
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" https://colot.site/profile)

    if [ ${RESPONSE_CODE} -ge 400 ]
    then
      CURRENT_PROFILE=real2
    else
      CURRENT_PROFILE=$(curl -s https://colot.site/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"
}

function find_idle_port() {
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}